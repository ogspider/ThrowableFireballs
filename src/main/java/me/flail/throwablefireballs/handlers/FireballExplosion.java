package me.flail.throwablefireballs.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.throwablefireballs.ThrowableFireballs;
import me.flail.throwablefireballs.tools.Tools;

public class FireballExplosion extends Tools implements Listener {

	private static final String fbMetadata = "HolyBalls";
	private ThrowableFireballs plugin;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void fireballExplode(ProjectileHitEvent event) {
		if (Objects.isNull(event.getHitBlock())) return;

		Entity entity = event.getEntity();

		if (entity instanceof Fireball) {
			plugin = JavaPlugin.getPlugin(ThrowableFireballs.class);
			FileConfiguration config = plugin.conf;

			boolean doesNaturalDamage = config.getBoolean("NaturalExplosion", true);
			if (!doesNaturalDamage) {
				int power = config.getInt("FireballExplosionPower", 0);
				boolean doesFire = config.getBoolean("FireballSetFire");

				Fireball fireball = (Fireball) entity;
				if (!fireball.hasMetadata(fbMetadata)) return;

				fireball.setYield(0); // Prevents the fireball from causing its own explosion damage

				new Tools().setKnockback(fireball, power * 1.4);

				if (plugin.isWorldGuard && !plugin.worldguard.canDamageBlock(event.getHitBlock().getLocation())) {
					fireball.setIsIncendiary(false);
					return;
				}

				Location fbLoc = event.getHitBlock().getLocation();
				World fbWorld = fireball.getWorld();

				if (power > 0) {
					fbWorld.createExplosion(fbLoc, power, doesFire, true, fireball);
				}

				if (!fireball.getPassengers().isEmpty()) {
					List<Entity> passengers = fireball.getPassengers();
					if (passengers.get(0) instanceof ArmorStand) {
						ArmorStand aStand = (ArmorStand) passengers.get(0);
						ItemStack item = aStand.getEquipment().getHelmet();
						aStand.remove();

						Item droppedItem = fbWorld.dropItemNaturally(fbLoc, item);
						droppedItem.setPickupDelay(Integer.MAX_VALUE);
						droppedItem.setCustomName(new Tools().chat(config.getString("FireballName", "&6Fireball")));
						droppedItem.setCustomNameVisible(true);

						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> droppedItem.remove(), 32L);
					}
				}
			}
		}
	}

	@EventHandler
	public void blockBoom(EntityExplodeEvent event) {
		Entity e = event.getEntity();
		if (e.hasMetadata(fbMetadata) || (e.getCustomName() != null && e.getCustomName().equals(fbMetadata)) || e.hasMetadata("Fireballed")) {
			List<String> immuneBlocks = new ArrayList<>(plugin.immuneBlocks);
			List<String> immuneKeys = new ArrayList<>(plugin.immuneBlockKeys);
			immuneBlocks.replaceAll(String::toUpperCase);

			for (Block block : event.blockList()) {
				String type = block.getType().toString();
				if (immuneBlocks.contains(type) || immuneKeys.stream().anyMatch(type::contains)) {
					event.blockList().remove(block);
				}
			}
		}
	}

	@EventHandler
	public void blockBoom(BlockExplodeEvent event) {
		List<String> immuneBlocks = plugin.immuneBlocks;
		List<String> immuneKeys = plugin.immuneBlockKeys;
		immuneBlocks.replaceAll(String::toUpperCase);

		for (Block block : event.blockList()) {
			String type = block.getType().toString().toUpperCase();
			if (immuneBlocks.contains(type) || immuneKeys.stream().anyMatch(type::contains)) {
				event.blockList().remove(block);
			}
		}
	}
}

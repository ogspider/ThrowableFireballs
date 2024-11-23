package me.flail.throwablefireballs.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.flail.throwablefireballs.ThrowableFireballs;

public class FireballDamage implements Listener {

	private ThrowableFireballs plugin = ThrowableFireballs.getPlugin(ThrowableFireballs.class);

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFireballDamage(EntityDamageByEntityEvent e) {
		// Adjust the damage based on the fireball settings
		e.setDamage(fireballDamage(e.getEntity(), e.getDamager(), e.getCause(), e.getDamage()));
	}

	public double fireballDamage(Entity damaged, Entity damager, DamageCause cause, double baseDamage) {

		FileConfiguration config = plugin.conf;

		// Check if natural explosion damage is used
		boolean doesNaturalDamage = config.getBoolean("NaturalExplosion");

		if (!doesNaturalDamage) {
			// If the damage is caused by an explosion or projectile (fireball)
			if (cause.equals(DamageCause.BLOCK_EXPLOSION) || cause.equals(DamageCause.ENTITY_EXPLOSION)
					|| cause.equals(DamageCause.PROJECTILE)) {

				String fbName = damager.getCustomName();

				// Check if the fireball is a "HolyBall"
				if (((fbName != null) && fbName.equals("HolyBalls")) || damager.hasMetadata("HolyBalls")) {
					// If the WorldGuard protection is active and blocking damage, return 0 damage
					if (plugin.isWorldGuard && !plugin.worldguard.canDamageEntity(damaged.getLocation())) {
						return 0;
					}

					// Return the configured fireball damage value
					return config.getDouble("FireballDamage", 2.0);
				}
			}
		}

		// If no special conditions apply, return the base damage
		return baseDamage;
	}
}

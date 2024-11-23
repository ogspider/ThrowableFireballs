package me.flail.throwablefireballs.handlers;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import me.flail.throwablefireballs.tools.Tools;

public class FireballCraft extends Tools implements Listener {

	@EventHandler
	public void onCraft(CraftItemEvent event) {
		HumanEntity e = event.getWhoClicked();

		// Ensure the event is triggered by a player and the item is a fireball
		if (e instanceof Player && !((Player) e).hasPermission("fireballs.craft")
				&& event.getRecipe().getResult().equals(new FireballItem().fireball())) {

			// Deny the crafting and close the inventory
			event.setResult(Result.DENY);
			e.closeInventory();

			// Send the player a message if they don't have permission to craft the fireball
			e.sendMessage(chat(plugin.conf.getString("NoCraftPermission")));
		}
	}
}

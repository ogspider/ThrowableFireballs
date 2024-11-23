/*
 * Copyright (C) 2018 FlailoftheLord
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package me.flail.throwablefireballs.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.throwablefireballs.ThrowableFireballs;
import me.flail.throwablefireballs.tools.Tools;

public class FireballItem extends Tools {

	private ThrowableFireballs plugin = ThrowableFireballs.getPlugin(ThrowableFireballs.class);

	public ItemStack fireball() {

		FileConfiguration config = plugin.conf;

		Material fireballType = Material.matchMaterial(config.get("FireballItem").toString().replaceAll("[^A-Za-z\\_]", ""));

		if (fireballType == null) {
			console("&c the &fFireballItem &coption in your configuration is invalid, please check it's a valid Minecraft Material.");
			fireballType = Material.FIRE_CHARGE;
		}

		String fbName = config.getString("FireballName");

		List<String> fbLoreList = config.getStringList("Lore");

		ArrayList<String> fbLore = new ArrayList<>();

		for (String l : fbLoreList) {
			fbLore.add(chat(l));
		}

		ItemStack fb = new ItemStack(fireballType);

		ItemMeta fbMeta = fb.getItemMeta();

		fbMeta.setLore(fbLore);
		fbMeta.setDisplayName(chat(fbName));
		fbMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		fb.setItemMeta(fbMeta);

		return this.addTag(fb);

	}

}
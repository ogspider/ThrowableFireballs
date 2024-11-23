package me.flail.throwablefireballs.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import me.flail.throwablefireballs.tools.Tools;

public class Config extends Tools {

	/**
	 * Get the configuration file.
	 *
	 * @return config file formatted as {@link FileConfiguration}
	 */
	public FileConfiguration get() {
		plugin.reloadConfig();
		return plugin.getConfig();
	}

	public void setup() {
		var options = options();
		var comments = comments();
		var config = this.get();

		config.options().setHeader(Arrays.asList(confHeader));

		for (String s : options.keySet())
			if (!config.contains(s))
				config.set(s, options.get(s));

		for (String s : comments.keySet())
			if (config.contains(s))
				config.setComments(s, Arrays.asList(comments.get(s)));

		plugin.saveConfig();
		plugin.reloadConfig();
	}

	private String[] confHeader = {
			"-------------------------------------------------------------------\r\n",
			"==================================================================#\r\n",
			"                                                                  #\r\n",
			"                 Plugin by FlailoftheLord.                        #\r\n",
			"        -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                   #\r\n",
			"   ______               __        _____                           #\r\n",
			"   |       |           /  \\         |        |                   #\r\n",
			"   |__     |          /____\\        |        |                   #\r\n",
			"   |       |         /      \\       |        |                   #\r\n",
			"   |       |_____   /        \\    __|__      |______             #\r\n",
			"                                                                  #\r\n",
			"==================================================================#\r\n",
			"-------------------------------------------------------------------\r\n",
			"\r\n" };

	/**
	 * Gets a list of comments (aligned numerically with {@link #options()})
	 *
	 * @return the entire list of comments as strings
	 */
	public static Map<String, String[]> comments() {
		Map<String, String[]> list = new HashMap<>();

		// Add comment for Fireball Speed
		list.put("FireballSpeed", new String[] { "Sets the speed of the fireball when thrown. Default is 1.0. Modify this to adjust the speed." });

		list.put("NaturalExplosion",
				new String[] { "Whether the fireball does a natural, Ghast-Initiated explosion." });
		list.put("FireballExplosionPower", new String[] { "Sets the Explosion power of fireballs on impact." });
		list.put("ImmuneBlocks", new String[] { "Blocks that are immune to explosions caused by custom fireballs." });
		list.put("ImmuneBlockKeywords",
				new String[] { "Keywords for Immune blocks, all material types containing these words will be whitelisted." });
		list.put("FireballDamage", new String[] { "Damage dealt to entities in explosion radius" });
		list.put("FireballSetFire", new String[] { "Whether the explosion sets blocks on fire" });
		list.put("AllowOffhandThrowing", new String[] { "Enables/Disables throwing fireballs from the offhand slot" });
		list.put("UseFirecharge", new String[] { "Use vanilla Fire Charges?" });
		list.put("MaxJumpHeight", new String[] { "Soft-limit height that entities are thrown when caught in an explosion" });
		list.put("Gamemodes", new String[] { "Fireball throwing is enabled/disabled (true/false) in the following gamemodes" });
		list.put("NoThrowZones", new String[] { "Worlds in which fireballs cannot be used." });
		list.put("NoThrowZoneMessage", new String[] { "Message sent when using in a NoThrowZone." });
		list.put("Prefix", new String[] { "Plugin messaging prefix for customization." });
		list.put("ReloadMessage", new String[] { "Sent when reloading the plugin via command." });
		list.put("NoPermissionMessage", new String[] { "Sent when command sender doesn't have permission to execute a command." });
		list.put("NoCraftPermission", new String[] { "Sent when the player trying to craft the item may not." });
		list.put("CooldownMessageEnabled",
				new String[] { "If Enabled, notifies the player when the fireball they're trying to throw is on cooldown." });
		list.put("CooldownMessage",
				new String[] { "Cooldown Message this message is sent to player when above verbose: is set to true\r\n",
						" use the placeholder %cooldown% for the cooldown time in seconds" });
		list.put("Cooldown", new String[] { "Cooldown (in seconds) between throws; set to 0 to disable." });
		list.put("FireballItem", new String[] { "Display item used for the custom fireball recipe." });
		list.put("FireballName", new String[] { "Name of the custom fireball." });
		list.put("Lore", new String[] { "Set as many lines as you'd like :)" });
		list.put("CraftingRecipe",
				new String[] { " - - -\r\n", " Heres the crafting recipe for the Fireballs!\r\n",
						" Set the AmountGiven to any number to set the amount of the item given when you craft it.\r\n",
						" Each line in the Pattern section is one row in the crafting table (3 x 3)\r\n",
						" Change the Letters to anything between 'A' and 'I' then define which material type each\r\n",
						" letter represents in the Materials section below\r\n", " You can get a full list of Item names here:\r\n",
						"    https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html\r\n", " - - -\r\n",
						" The plugin will give you a warning in the console if one of the items is invalid." });

		return list;
	}

	/**
	 * Gets a full list of the most recent config options.
	 *
	 * @return the entire list of options, set as strings.
	 */
	public static Map<String, Object> options() {
		Map<String, Object> list = new LinkedHashMap<>();

		// Add Fireball Speed option
		list.put("FireballSpeed", 1.0); // Default speed value (1.0)

		// Other existing options...
		list.put("NaturalExplosion", Boolean.FALSE);
		list.put("FireballExplosionPower", 2);
		list.put("ImmuneBlocks", new String[] { "chest", "trapped_chest" });
		list.put("ImmuneBlockKeywords", new String[] { "stone" });
		list.put("FireballDamage", 3.5);
		list.put("FireballSetFire", Boolean.TRUE);
		list.put("AllowOffhandThrowing", Boolean.FALSE);
		list.put("UseFirecharge", Boolean.FALSE);
		list.put("MaxJumpHeight", 8);
		list.put("Gamemodes.Adventure", Boolean.TRUE);
		list.put("Gamemodes.Creative", Boolean.TRUE);
		list.put("Gamemodes.Spectator", Boolean.FALSE);
		list.put("Gamemodes.Survival", Boolean.TRUE);
		list.put("NoThrowZones", new String[] { "world_the_end", "example_world" });
		list.put("NoThrowZoneMessage", "%prefix% &cYou're not allowed to throw fireballs in this world!");
		list.put("Prefix", "&8(&6Fireballs&8)");
		list.put("ReloadMessage", "%prefix% &aconfig file successfully reloaded,"
				+ " &amake sure to restart the server if changing the fireball recipe!");
		list.put("NoPermissionMessage", "%prefix% &cYou do not have permission to use this");
		list.put("NoCraftPermission", "%prefix% &cYou don't have permission to craft this.");
		list.put("CooldownMessageEnabled", Boolean.TRUE);
		list.put("CooldownMessage", "%prefix% &cYou must wait %cooldown% seconds before throwing this");
		list.put("Cooldown", 1);
		list.put("FireballItem", "FIRE_CHARGE");
		list.put("FireballName", "&6Fireball");
		list.put("Lore", new String[] { "&7right click to throw", "&7grief extreme ;)" });
		list.put("CraftingRecipe.AmountGiven", 3);
		list.put("CraftingRecipe.Pattern.1", "ABA");
		list.put("CraftingRecipe.Pattern.2", "BCB");
		list.put("CraftingRecipe.Pattern.3", "ABA");
		list.put("CraftingRecipe.Materials.A", "GUNPOWDER");
		list.put("CraftingRecipe.Materials.B", "FIREWORK_STAR");
		list.put("CraftingRecipe.Materials.C", "GHAST_TEAR");
		list.put("CraftingRecipe.Materials.D", " ");
		list.put("CraftingRecipe.Materials.E", " ");
		list.put("CraftingRecipe.Materials.F", " ");
		list.put("CraftingRecipe.Materials.G", " ");
		list.put("CraftingRecipe.Materials.H", " ");
		list.put("CraftingRecipe.Materials.I", " ");

		return list;
	}
}

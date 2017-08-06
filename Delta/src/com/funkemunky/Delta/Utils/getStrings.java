package com.funkemunky.Delta.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class getStrings {
	
	public static String getPrefix() {
		return ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "Delta" + ChatColor.DARK_GRAY + "] ";
	}
	public static String getUsage(String usage) {
		return ChatColor.RED + "Usage> " + ChatColor.GRAY + usage;
	}
	
	public static List<String> getMOTD(Player player) {
		List<String> motd = new ArrayList<String>();
		motd.add("" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "-------------------------");
		motd.add("");
		motd.add(ChatColor.translateAlternateColorCodes('&', "&7Welcome back to Delta &c" + player.getName()  + "&7!"));
		motd.add("");
		motd.add(ChatColor.translateAlternateColorCodes('&', "&c&lWebsite: &7&ohttp://deltapots.com"));
		motd.add(ChatColor.translateAlternateColorCodes('&', "&c&lTeamSpeak: &7&odeltapots.com"));
		motd.add(ChatColor.translateAlternateColorCodes('&', "&c&lDonate: &7&ots.deltapots.com"));
		motd.add("");
		motd.add("" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "-------------------------");
		return motd;
	}
	public static String getError(String error) {
		return ChatColor.RED + "ERROR> " + ChatColor.GRAY + error;
	}
	public static String getInDev(Boolean dev) {
		if(dev == false) return ChatColor.RED + "Sorry, this feature is currently in development!"; else
		return ChatColor.RED + "Sorry, this feature is currently in development! Message funkemunky for information.";
	}
	
	public static String getNoPermission(String group) {
		return ChatColor.translateAlternateColorCodes('&', "&cPermissions&8> " + "&7You need " + group + " for this feature!");
	}
	
	public static String STAFF_CHAT_FORMAT() {
		return ChatColor.translateAlternateColorCodes('&', "&8[&b&lSC&8] &9<player> &b<message>");
	}
	@SuppressWarnings("null")
	public static List<String> MAX_PLAYERS_KICK() {
		List<String> reasons = null;
		reasons.add(ChatColor.translateAlternateColorCodes('&', "&cDelta's max capacity has been reached!!"));
		reasons.add("");
		reasons.add(ChatColor.translateAlternateColorCodes('&', "&7If you want to be able to play on a full server, donate to Delta at"));
		reasons.add("");
		reasons.add(ChatColor.translateAlternateColorCodes('&', "&b&nhttp://shop.deltapvp.com"));
		return reasons;
		
	}
	
	public static String getString(String prefix, String msg) {
		return ChatColor.translateAlternateColorCodes('&', "&c" + prefix + "&8> &7" + msg);
	}
 
}

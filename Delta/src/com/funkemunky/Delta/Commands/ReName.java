package com.funkemunky.Delta.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.funkemunky.Delta.Utils.getStrings;


public class ReName implements CommandExecutor {
	
    public String getUsage(final String label) {
        return String.valueOf('/') + label + ' ' + "revive <playerName>";
    }

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("rename")) {
			if(p.hasPermission("delta.rename")) {
				if(args.length < 1) {
					p.sendMessage(getStrings.getUsage("/rename <name>"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c- &7You should use &e&&7's for colors!"));
				} else {
					ItemStack hand = p.getItemInHand();
					if(hand.getType() != Material.AIR) {
						String name = "";
						 for(int i = 0; i != args.length; i++)
							 name += args[i] + " ";
						
						ItemMeta meta = hand.getItemMeta();
						meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r" + name));
						hand.setItemMeta(meta);
						
						p.sendMessage(getStrings.getString("Rename", "&7Your &c" + hand.getType().toString().toLowerCase().replaceAll("_", " ") + " &7was successfully renamed to &r" + name + "&7!"));
					} else {
						p.sendMessage(getStrings.getError("You cannot rename Air!"));
					}
				}
			} else {
				p.sendMessage(getStrings.getNoPermission("Gold"));
			}
		}
		return false;
	}

}

package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Utils.getStrings;

public class Clear implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this feature!");
		} else {
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("clearinventory")) {
				if(p.hasPermission("delta.clear")) {
					if(args.length == 0) {
						p.sendMessage(ChatColor.GRAY + "Your inventory has just been cleared!");
						p.getInventory().clear();
					} else {
						if(args.length == 1) {
							if(p.hasPermission("delta.clear.others")) {
								Player online = Bukkit.getPlayerExact(args[0]);
								if(online != null) {
									Player target = Bukkit.getPlayer(args[0]);
									p.sendMessage(ChatColor.GRAY + "You just cleared " + ChatColor.RED + target.getName() + ChatColor.GRAY + "'s inventory!");
									target.getInventory().clear();
									target.sendMessage(ChatColor.GRAY + "Your inventory was cleared by " + ChatColor.RED + p.getName() + ChatColor.GRAY + "!");
								} else {
									p.sendMessage(ChatColor.GRAY + "The player " + ChatColor.RED + args[0] + ChatColor.GRAY + " is not online!");
								}
							} else {
								p.sendMessage(ChatColor.GRAY + "You need Moderator for this feature!");
							}
						}
					}
				} else {
					p.sendMessage(getStrings.getNoPermission("Builder"));
				}
			}
	    }
		return false;
	}

}

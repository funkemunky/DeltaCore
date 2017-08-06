package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Core;
import com.funkemunky.Delta.Utils.getStrings;

public class Kill implements CommandExecutor {
	
	Core core;
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (!(sender instanceof Player)) {
             sender.sendMessage(ChatColor.RED + "Only players can do this.");
             return true;
        }
		if(cmd.getName().equalsIgnoreCase("kill")) {
			if(p.hasPermission("delta.kill")) {
				if(args.length == 0) {
					p.sendMessage(getStrings.getError("Please specify a player to obliterate!"));
				} else {
					if(args.length == 1) {
						Player online = Bukkit.getPlayerExact(args[0]);
						
						if(online == null) {
							p.sendMessage(getStrings.getError("That player is not online!"));
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							target.setHealth(0);
							target.sendMessage(ChatColor.RED + p.getName() + ChatColor.GRAY + " has obliterated you!");
						}
					}
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("quit")) {
			if(core.getConfig().getBoolean("Practice") == true) {
				p.setHealth(0);
				p.sendMessage(ChatColor.GRAY + "You successfully quit!");
			} else {
				return false;
			}
		}
		return false;
	}

}

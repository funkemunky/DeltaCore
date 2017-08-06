package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Utils.getStrings;

public class Hacker implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("hacker")) {
			if(p.hasPermission("delta.hacker")) {
				if(args.length < 1 || args.length > 1) {
					p.sendMessage(getStrings.getUsage("/hacker <player>"));
				}
				if(args.length == 1) {
					Player online = Bukkit.getPlayerExact(args[0]);
					
					if(online != null) {
						Player target = Bukkit.getPlayer(args[0]);
						Location loc = target.getLocation();
						loc.setY(target.getLocation().getY() + 100);
						target.setHealth(0);
						Bukkit.dispatchCommand(sender, "ban " + target.getName() + " Cheating");
						Bukkit.broadcastMessage(getStrings.getPrefix() + ChatColor.RED + target.getName() + ChatColor.GRAY + " has been hacked by " + ChatColor.RED + p.getName() + ChatColor.GRAY + "!");
					}
				}
			} else {
				p.sendMessage(ChatColor.GRAY + "Now this feature is a secret and can only be used by professionals buddy boi!");
			}
		}
		return false;
	}

	

}

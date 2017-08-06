package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Utils.getStrings;

public class Heal implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("heal")) {
			if(p.hasPermission("delta.heal")) {
				if(args.length == 0) {
					p.setHealth(20);
					p.setFoodLevel(20);
					p.sendMessage(ChatColor.GRAY + "You have been successfully healed!");
				} else {
					if(args.length == 1) {
						if(p.hasPermission("delta.heal.others")) {
							Player online = Bukkit.getPlayerExact(args[0]);
							if(online != null) {
								Player target = Bukkit.getPlayer(args[0]);
								target.setHealth(20);
								target.setFoodLevel(20);
								p.sendMessage(ChatColor.GRAY + "You successfully healed " + ChatColor.RED + target.getName() + ChatColor.GRAY + "!");
								target.sendMessage(ChatColor.GRAY + "You were healed by " + ChatColor.RED + p.getName() + ChatColor.GRAY + "!");
							}
						} else {
							p.sendMessage(ChatColor.GRAY + "You need Admin for this feature!");
						}
					}
				}
			} else {
				p.sendMessage(getStrings.getNoPermission("V.Moderator"));
			}
		}
		return false;
	}

}

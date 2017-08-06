package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Utils.getStrings;

import net.md_5.bungee.api.ChatColor;

public class StaffChat implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("staffchat")) {
			if(p.hasPermission("delta.staff")) {
				if(args.length == 0) {
					p.sendMessage(getStrings.getError("Not enough arguments! You need to put in a message."));
				}
				String message = "";
				for(int i = 0; i != args.length; i++)
					message += args[i] + " ";
					
				if(args[0] == null) {
					p.sendMessage(getStrings.getError("You need to put in a message to send!"));
				} else {
					Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', "&b[SC] &c" + p.getName() + " &7" + message), "delta.staff");
				}
			} else {
				p.sendMessage(getStrings.getNoPermission("Trainee"));
			}
		}
		return false;
	}

}

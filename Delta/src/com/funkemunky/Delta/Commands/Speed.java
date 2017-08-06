package com.funkemunky.Delta.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Utils.getStrings;

public class Speed implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("speed")) {
			if(p.hasPermission("delta.speed")) {
				if(args.length == 0 || args.length > 2) {
					p.sendMessage(getStrings.getUsage("/speed <int>"));
				} else {
					if(args.length == 1) {
						float speed = Float.valueOf(args[0]);
						if(speed > 1) {
							p.sendMessage(getStrings.getError("You cannot use numerals over 1! Use decimals."));
						} else {
							if(!p.isFlying()) {
								p.setWalkSpeed(speed);
								p.sendMessage(getStrings.getString("Speed", "&7Walk speed set to &o" + args[0] + "&7!"));
							} else {
								p.setFlySpeed(speed);
								p.sendMessage(getStrings.getString("Speed", "&7Flight speed set to &o" + args[0] + "&7!"));
							}
						}
					}
				}
			} else {
				p.sendMessage(ChatColor.GRAY + "You need Moderator for this feature!");
			}
		}
		return false;
	}

}

package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Utils.getStrings;

import net.md_5.bungee.api.ChatColor;

public class Funkemunky implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("funkemunky")) {
			if(p.getUniqueId() == Bukkit.getOfflinePlayer("funkemunky").getUniqueId()) {
				for(String reason : getStrings.MAX_PLAYERS_KICK()) {
					p.kickPlayer(reason);
				}
			} else {
				p.sendMessage(ChatColor.GRAY + "This command is funkemunky's and is used for testing. This command may be the thing to kill your hot sister...");
			}
		}
		return false;
	}

}

package com.funkemunky.Delta.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Help implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do this.");
            return true;
        }
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("help")) {
			if(!p.hasPermission("bukkit.command.help")) {
				p.sendMessage("" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-------------------------");
				p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "Delta" + ChatColor.WHITE + ChatColor.BOLD + "Help");
				p.sendMessage("");
				p.sendMessage("" + ChatColor.AQUA + ChatColor.BOLD + "Forums: " + ChatColor.GRAY + "http://deltaclan.ml");
				p.sendMessage("" + ChatColor.AQUA + ChatColor.BOLD + "TeamSpeak: " + ChatColor.GRAY + "deltaclanofficial.ml" );
				p.sendMessage("" + ChatColor.AQUA + ChatColor.BOLD + "Donate " + ChatColor.GRAY + "/donate");
				p.sendMessage("");
				p.sendMessage(ChatColor.GRAY + "Do " + ChatColor.RED + "/report" + ChatColor.GRAY + " to report hackers!");
				p.sendMessage(ChatColor.GRAY + "Do " + ChatColor.RED + "/request" + ChatColor.GRAY + " to request staff!");
				p.sendMessage("" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-------------------------");
			}
			
		}
		return false;
	}

}

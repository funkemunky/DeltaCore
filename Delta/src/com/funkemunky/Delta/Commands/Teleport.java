package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Handlers.MatchSpectateHandler;
import com.funkemunky.Delta.Utils.EntityHider;
import com.funkemunky.Delta.Utils.getStrings;;

public class Teleport implements CommandExecutor {
	
	MatchSpectateHandler spec;
	EntityHider hider;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do this.");
            return true;
        }
		if(cmd.getName().equalsIgnoreCase("tphere")) {
			if(!p.hasPermission("delta.teleport")) {
				p.sendMessage(ChatColor.GRAY + "You need TrialMod to use this feature!");
			} else {
				if(args.length == 0) {
					p.sendMessage(getStrings.getUsage("/tphere <player>"));
				} else {
					if(args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);
						Player online = Bukkit.getPlayerExact(args[0]);
						if(online == null) {
							p.sendMessage(getStrings.getError("That player is not online!"));
							return false;
						} else {
							target.teleport(p.getLocation());
							p.sendMessage(ChatColor.GRAY + "Teleported " + ChatColor.RED + target.getName() + ChatColor.GRAY + " to you!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Too many arguments!");
					}
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("teleport")) {
			if(!p.hasPermission("delta.teleport")) {
				p.sendMessage(getStrings.getNoPermission("Trainee"));
			} else {
				if(args.length == 0) {
					p.sendMessage(getStrings.getUsage("/teleport <player> [player]"));
				} else {
					if(args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);
						Player online = Bukkit.getPlayerExact(args[0]);
						if(online == null) {
							p.sendMessage(getStrings.getError("That player is not online!"));
							return false;
						}
						p.teleport(target.getLocation());
						p.sendMessage(ChatColor.GRAY + "Teleported to " + ChatColor.RED + target.getName() + ChatColor.GRAY + "!");
					} else {
						if(args.length == 2) {
							Player target1 = Bukkit.getPlayer(args[0]);
							Player target2 = Bukkit.getPlayer(args[1]);
							Player online1 = Bukkit.getPlayerExact(args[0]);
							Player online2 = Bukkit.getPlayerExact(args[1]);
							
							if(online2 == null || online1 == null) {
								p.sendMessage(ChatColor.RED + "One of the players you entered are not online!");
								return true;
							}
							
							target1.teleport(target2.getLocation());
							p.sendMessage(ChatColor.GRAY + "Teleported " + ChatColor.RED + target1.getName() + ChatColor.GRAY + " to " + ChatColor.RED + target2.getName() + ChatColor.GRAY + "!");
						}
					}
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("tppos")) {
			if(args.length == 0) {
				p.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/" + cmd.getName() + " [player] (player)");
			} else {
				if(args.length < 3) {
					p.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/" + cmd.getName() + " [x] [y] [z] (player)");
					return false;
				}
				if(args.length == 3) {
					int x = Integer.parseInt(args[0]);
					int y = Integer.parseInt(args[1]);
					int z = Integer.parseInt(args[2]);
					
					Location loc = new Location(p.getWorld(), x, y, z);
					
					p.teleport(loc);
					p.sendMessage(ChatColor.GRAY + "Teleported you to the specified location!");
				} else {
					if(args.length == 4) {
						int x = Integer.parseInt(args[0]);
						int y = Integer.parseInt(args[1]);
						int z = Integer.parseInt(args[2]);
						Player target = Bukkit.getPlayer(args[3]);
						Player online = Bukkit.getPlayerExact(args[3]);
						
						if(online == null) {
							p.sendMessage(ChatColor.RED + "The player '" + args[3] + "' is not online!");
							return false;
						}
						
						Location loc = new Location(target.getWorld(), x, y, z);
						
						target.teleport(loc);
						p.sendMessage(ChatColor.GRAY + "Teleported " + ChatColor.RED + target.getName() + ChatColor.GRAY + " to the specified location!");
					}
				}
			}
		}
		return false;
	}

}

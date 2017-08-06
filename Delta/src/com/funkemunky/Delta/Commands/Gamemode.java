package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Utils.getStrings;

public class Gamemode implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do this.");
            return true;
        }
		if(cmd.getName().equalsIgnoreCase("gamemode")) {
			if(p.hasPermission("delta.gamemode")) {
				if(args.length == 0) {
					p.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/" + cmd.getName() + " [args]");
				} else {
					if((args[0].equalsIgnoreCase("survival"))
							|| (args[0].equalsIgnoreCase("s")) 
							|| (args[0].equalsIgnoreCase("0")) 
							|| (args[0].equalsIgnoreCase("one"))) {
						if(args.length == 1) {
							p.setGameMode(GameMode.SURVIVAL);
							p.sendMessage(getStrings.getString("Gamemode", "Gamemode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
						} else {
							if(args.length == 2) {
								Player target = Bukkit.getPlayer(args[1]);
								Player online = Bukkit.getPlayerExact(args[1]);
								if(online == null) {
									p.sendMessage(getStrings.getError("The player you entered is not online!"));
								} else {
									target.setGameMode(GameMode.SURVIVAL);
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + "&7's gamemode set to &c" + p.getGameMode().toString().toLowerCase() + "&7!"));
									target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Gamemode set to &c" + p.getGameMode().toString().toLowerCase() + "&7!"));
								}
							} else {
								p.sendMessage(getStrings.getError("Too many arguments!"));
							}
						}
					} else {
						if((args[0].equalsIgnoreCase("creative"))
								|| (args[0].equalsIgnoreCase("c")) 
								|| (args[0].equalsIgnoreCase("1")) 
								|| (args[0].equalsIgnoreCase("one"))) {
							if(args.length == 1) {
								p.setGameMode(GameMode.CREATIVE);
								p.sendMessage(getStrings.getString("Gamemode", "Gamemode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
							} else {
								if(args.length == 2) {
									Player target = Bukkit.getPlayer(args[1]);
									Player online = Bukkit.getPlayerExact(args[1]);
									if(online == null) {
										p.sendMessage(getStrings.getError("The player you entered is not online!"));
									} else {
										target.setGameMode(GameMode.CREATIVE);
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + "&7's gamemode set to &c" + p.getGameMode().toString().toLowerCase() + "&7!"));
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Gamemode set to &c" + p.getGameMode().toString().toLowerCase() + "&7!"));
									}
								} else {
									p.sendMessage(getStrings.getError("Too many arguments!"));
								}
							}
						} else {
							if((args[0].equalsIgnoreCase("adventure"))
									|| (args[0].equalsIgnoreCase("a")) 
									|| (args[0].equalsIgnoreCase("2")) 
									|| (args[0].equalsIgnoreCase("two"))) {
								if(args.length == 1) {
									p.setGameMode(GameMode.ADVENTURE);
									p.sendMessage(getStrings.getString("Gamemode", "Gamemode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
								} else {
									if(args.length == 2) {
										Player target = Bukkit.getPlayer(args[1]);
										Player online = Bukkit.getPlayerExact(args[1]);
										if(online == null) {
											p.sendMessage(getStrings.getError("The player you entered is not online!"));
										} else {
											target.setGameMode(GameMode.ADVENTURE);
											p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + "&7's gamemode set to &c" + p.getGameMode().toString().toLowerCase() + "&7!"));
											target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Gamemode set to &c" + p.getGameMode().toString().toLowerCase() + "&7!"));
										}
									} else {
										p.sendMessage(getStrings.getError("Too many arguments!"));
									}
								}
							} else {
								if(args.length == 1) p.sendMessage(getStrings.getError("The argument '" + args[0] + "' does not exist!")); else {
									p.sendMessage(getStrings.getError("Unknown error occured!"));
								}
								
							}
						}
					}
				}
			} else {
				p.sendMessage(getStrings.getNoPermission("Administrator"));
			}
		}
		if(cmd.getName().equalsIgnoreCase("survival")) {
			if(p.hasPermission("delta.gamemode")) {
				if(args.length == 0) {
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage(getStrings.getString("Gamemode", "Gamemode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
				} else {
					if(args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);
						Player online = Bukkit.getPlayerExact(args[0]);
						
						if(online == null) {
							p.sendMessage(getStrings.getError("The player you entered is not online!"));
						} else {
							target.setGameMode(GameMode.SURVIVAL);
							p.sendMessage(getStrings.getString("Gamemode", target.getName() + "'s mode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
							target.sendMessage(getStrings.getString("Gamemode", "Gamemode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
						}
					}
				}
			} else {
				p.sendMessage(getStrings.getNoPermission("Administrator"));
			}
		}
		if(cmd.getName().equalsIgnoreCase("creative")) {
			if(p.hasPermission("delta.gamemode")) {
				if(args.length == 0) {
					p.setGameMode(GameMode.CREATIVE);
					p.sendMessage(getStrings.getString("Gamemode", "Gamemode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
				} else {
					if(args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);
						Player online = Bukkit.getPlayerExact(args[0]);
						
						if(online == null) {
							p.sendMessage(getStrings.getError("The player you entered is not online!"));
						} else {
							target.setGameMode(GameMode.CREATIVE);
							p.sendMessage(getStrings.getString("Gamemode", target.getName() + "'s mode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
							target.sendMessage(getStrings.getString("Gamemode", "Gamemode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
						}
					}
				}
			} else {
				p.sendMessage(getStrings.getNoPermission("Administrator"));
			}
		}
		if(cmd.getName().equalsIgnoreCase("adventure")) {
			if(p.hasPermission("delta.gamemode")) {
				if(args.length == 0) {
					p.setGameMode(GameMode.ADVENTURE);
					p.sendMessage(getStrings.getString("Gamemode", "Gamemode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
				} else {
					if(args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);
						Player online = Bukkit.getPlayerExact(args[0]);
						
						if(online == null) {
							p.sendMessage(getStrings.getError("The player you entered is not online!"));
						} else {
							target.setGameMode(GameMode.ADVENTURE);
							p.sendMessage(getStrings.getString("Gamemode", target.getName() + "'s mode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
							target.sendMessage(getStrings.getString("Gamemode", "Gamemode set to &o" + p.getGameMode().toString().toLowerCase() + "&7!"));
						}
					}
				}
			} else {
				p.sendMessage(getStrings.getNoPermission("Administrator"));
			}
		}
		return false;
	}

}

package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Utils.getStrings;

public class Fly implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this feature!");
		} else {
			if(cmd.getName().equalsIgnoreCase("fly")) {
				if(p.hasPermission("delta.flight")) {
					if(args.length == 0) {
						if(p.getAllowFlight() == true) {
							p.setAllowFlight(false);
							p.setFlying(false);
							p.setNoDamageTicks(60);
							p.sendMessage(getStrings.getString("Flight", "Flight set to &o" + p.getAllowFlight() + "&7!"));
						} else {
							if(p.getAllowFlight() == false) {
								p.setAllowFlight(true);
								p.sendMessage(getStrings.getString("Flight", "Flight set to &o" + p.getAllowFlight() + "&7!"));
							}
						}
					}
					if(args.length == 1) {
						if(!p.hasPermission("delta.flight.others")) {
							p.sendMessage(getStrings.getNoPermission("Administrator"));
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							Player online = Bukkit.getPlayerExact(args[0]);
							if(online == null) {
								p.sendMessage(getStrings.getError("That player is not online!"));
							} else {
								if(target.getAllowFlight() == true) {
									target.setAllowFlight(false);
									target.setFlying(false);
									target.setNoDamageTicks(60);
									target.sendMessage(getStrings.getString("Flight", "Flight set to &o" + p.getAllowFlight() + "&7!"));
									p.sendMessage(getStrings.getString("Flight", target.getName() + "'s flight set to &o" + target.getAllowFlight() + "&7!"));
								} else {
									if(p.getAllowFlight() == false) {
										p.setAllowFlight(true);
										target.sendMessage(getStrings.getString("Flight", "Flight set to &o" + p.getAllowFlight() + "&7!"));
										p.sendMessage(getStrings.getString("Flight", target.getName() + "'s flight set to &o" + target.getAllowFlight() + "&7!"));
									}
								}
							}
						}
					} else
					if(args.length > 1) {
						p.sendMessage(getStrings.getError("Too many arguments!"));
					}
				} else {
					p.sendMessage(getStrings.getNoPermission("Trainee"));
				}
			}
		}
		return false;
	}
}

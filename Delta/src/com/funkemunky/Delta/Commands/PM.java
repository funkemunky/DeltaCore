package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Core;
import com.funkemunky.Delta.Utils.getStrings;

public class PM implements CommandExecutor {
	
	private Core core;
	public PM(Core core) {
		this.core = core;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("message")) {
            if(args.length == 0 || args.length == 1) {
            	p.sendMessage(getStrings.getUsage("/<cmd> [player] [msg]"));
            } else {
            	if(!(Bukkit.getPlayerExact(args[0]) == null)) {
                    Player target = Bukkit.getPlayer(args[0]);
                	if(core.PMsToggle.get(target.getUniqueId()) == null) {
                		core.PMsToggle.put(target.getUniqueId(), true);
                        
                		String message = "";
                        
                        core.lastSentMessages.put(p.getUniqueId(), target.getUniqueId());
                        core.lastSentMessages.put(target.getUniqueId(), p.getUniqueId());

                        for(int i = 1; i != args.length; i++)
                        	
                            message += args[i] + " ";

                        sender.sendMessage(ChatColor.GRAY + "(To " + ChatColor.ITALIC + target.getName() + ChatColor.GRAY + ") " + ChatColor.AQUA + message);
                        target.sendMessage(ChatColor.GRAY + "(From " + ChatColor.ITALIC + sender.getName() + ChatColor.GRAY + ") " + ChatColor.AQUA + message);
                        if(core.PMNoises.get(target.getUniqueId()) == null) {
                        	core.PMNoises.put(target.getUniqueId(), true);
                        	target.playSound(target.getLocation(), Sound.NOTE_PLING, 1, 40);
                        } else {
                        	if(core.PMNoises.get(target.getUniqueId()) == true) {
                        		target.playSound(target.getLocation(), Sound.NOTE_PLING, 1, 40);
                        	}
                        }
                	} else {
                		if(core.PMsToggle.get(target.getUniqueId()) == false) {
                			sender.sendMessage(ChatColor.GRAY + "That player has PMs toggled off!");
                		}
                		if(core.PMsToggle.get(target.getUniqueId()) == true) {
                			String message = "";
                            
                            core.lastSentMessages.put(p.getUniqueId(), target.getUniqueId());
                            core.lastSentMessages.put(target.getUniqueId(), p.getUniqueId());

                            for(int i = 1; i != args.length; i++)
                            	
                                message += args[i] + " ";

                            sender.sendMessage(ChatColor.GRAY + "(To " + ChatColor.ITALIC + target.getName() + ChatColor.GRAY + ") " + ChatColor.AQUA + message);
                            target.sendMessage(ChatColor.GRAY + "(From " + ChatColor.ITALIC + sender.getName() + ChatColor.GRAY + ") " + ChatColor.AQUA + message);
                            if(core.PMNoises.get(target.getUniqueId()) == null) {
                            	core.PMNoises.put(target.getUniqueId(), true);
                            	target.playSound(target.getLocation(), Sound.NOTE_PLING, 1, 40);
                            } else {
                            	if(core.PMNoises.get(target.getUniqueId()) == true) {
                            		target.playSound(target.getLocation(), Sound.NOTE_PLING, 1, 40);
                            	}
                            }
                		}
                	}
                } else if(Bukkit.getPlayerExact(args[0]) == null) {
                    sender.sendMessage(ChatColor.RED + "The player you tried to message is not online!");
                }

                return true;
            }
        }
		if(cmd.getName().equals("reply")) {
			if(!(core.lastSentMessages.get(p.getUniqueId()) == null)) {
				if(args.length == 0) {
					p.sendMessage(getStrings.getUsage("/" + cmd.getName() + " [msg]"));
				}
				Player target = Bukkit.getPlayer(core.lastSentMessages.get(p.getUniqueId()));
				if(!target.isOnline()) {
					sender.sendMessage(ChatColor.GRAY + "You have no one to reply to...");
				} else {
					if(core.PMsToggle.get(target.getUniqueId()) == null) {
						core.PMsToggle.put(target.getUniqueId(), true);
						 String message = "";
						 for(int i = 0; i != args.length; i++)
			                	
			                    message += args[i] + " ";

						 sender.sendMessage(ChatColor.GRAY + "(To " + ChatColor.ITALIC + target.getName() + ChatColor.GRAY + ") " + ChatColor.AQUA + message);
			             target.sendMessage(ChatColor.GRAY + "(From " + ChatColor.ITALIC + sender.getName() + ChatColor.GRAY + ") " + ChatColor.AQUA + message);
			             if(core.PMNoises.get(target.getUniqueId()) == null) {
			            	 core.PMNoises.put(target.getUniqueId(), true);
	                        target.playSound(target.getLocation(), Sound.NOTE_PLING, 1, 40);
	                     } else {
	                        if(core.PMNoises.get(target.getUniqueId()) == true) {
	                        	target.playSound(target.getLocation(), Sound.NOTE_PLING, 1, 40);
	                        }
	                     }
					} else {
						if(core.PMsToggle.get(target.getUniqueId()) == true) {
							 String message = "";
							 for(int i = 0; i != args.length; i++)
				                	
				                    message += args[i] + " ";

							 sender.sendMessage(ChatColor.GRAY + "(To " + ChatColor.ITALIC + target.getName() + ChatColor.GRAY + ") " + ChatColor.AQUA + message);
				             target.sendMessage(ChatColor.GRAY + "(From " + ChatColor.ITALIC + sender.getName() + ChatColor.GRAY + ") " + ChatColor.AQUA + message);
				             if(core.PMNoises.get(target.getUniqueId()) == null) {
		                        core.PMNoises.put(target.getUniqueId(), true);
		                        target.playSound(target.getLocation(), Sound.NOTE_PLING, 1, 40);
		                     } else {
		                        if(core.PMNoises.get(target.getUniqueId()) == true) {
		                        	target.playSound(target.getLocation(), Sound.NOTE_PLING, 1, 40);
		                        }
		                     }
						}
						if(core.PMsToggle.get(target.getUniqueId()) == false) {
	            			sender.sendMessage(ChatColor.GRAY + "That player has PMs toggled off!");
	            		}
					}
				}
			} else {
				sender.sendMessage(ChatColor.GRAY + "You have no one to reply to...");
			}
		}
		return false;
	}

}

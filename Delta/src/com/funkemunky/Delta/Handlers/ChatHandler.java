package com.funkemunky.Delta.Handlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.funkemunky.Delta.Core;
import com.funkemunky.Delta.Utils.getStrings;


public class ChatHandler implements Listener, CommandExecutor {
	
	private Core core;
	public ChatHandler(Core core) {
		this.core = core;
	}
	
	private ArrayList<Boolean> isChatMuted = new ArrayList<Boolean>();
	private HashMap<String, Integer> chatSlowTime = new HashMap<String, Integer>();
	
	private HashMap<Player, Integer> cooldownTime;
    private HashMap<Player, BukkitRunnable> cooldownTask;
	 
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChat(PlayerChatEvent e) {
		if(isChatMuted.contains(true)) {
				Bukkit.broadcastMessage("Test complete!");
				e.setCancelled(true);
				e.getPlayer().sendMessage(getStrings.getString("Chat", "Your message was not sent since chat is muted!"));
		}
		if(chatSlowTime.containsKey("Slow")) {
			if(!e.getPlayer().hasPermission("delta.staff")) {
				if(!cooldownTime.containsKey(e.getPlayer())) {
					cooldownTime.put(e.getPlayer(), chatSlowTime.get("Slow"));
					cooldownTask.put(e.getPlayer(), new BukkitRunnable() {
	                	 public void run() {
	                		 cooldownTime.put(e.getPlayer(), cooldownTime.get(e.getPlayer()) - 1);
	                         if (cooldownTime.get(e.getPlayer()) == 0) {
	                        	 cooldownTime.remove(e.getPlayer());
	                        	 cooldownTask.remove(e.getPlayer());
	                                 cancel();
	                         }
	                	 }
	                 });
					cooldownTask.get(e.getPlayer()).runTaskTimer((Plugin) core, 20, 20);
				} else {
					e.setCancelled(true);
					e.getPlayer().sendMessage(getStrings.getString("Chat", "You must wait &b" + cooldownTime.get(e.getPlayer()) + "seconds &7before you can speak again."));
				}
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this feature!");
			return true;
		}
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("chat")) {
			if(p.hasPermission("delta.staff")) {
				if(args.length == 0) {
					p.sendMessage("" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "---------------------------");
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cChat Management Help:"));
					p.sendMessage("");
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/chat [mute/unmute]"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/chat [clear]"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/chat [slow] [{time-in-seconds}/off]"));
					p.sendMessage("" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "---------------------------");
				} else {
					if(args[0].equalsIgnoreCase("clear")) {
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cChat&8> &7A staff member cleared the chat!"));
					}
					if(args[0].equalsIgnoreCase("mute")) {
						if(isChatMuted.contains(true)) {
							p.sendMessage(getStrings.getError("Chat is already muted!"));
						} else {
							isChatMuted.add(true);
							Bukkit.broadcastMessage(getStrings.getString("Chat", "Chat was muted by &b" + p.getName() + "&7!"));
						}
					}
					if(args[0].equalsIgnoreCase("unmute")) {
						if(isChatMuted.contains(true)) {
							isChatMuted.remove(true);
							Bukkit.broadcastMessage(getStrings.getString("Chat", "Chat was unmuted!"));
						} else {
							p.sendMessage(getStrings.getError("Chat is not muted!"));
						}
					}
					if(args[0].equalsIgnoreCase("slow")) {
						if(args.length == 1) {
							p.sendMessage(getStrings.getError("Not enough arguments! Do /chat for help."));
						} else {
							if(args.length == 2) {
								if(args[1].equalsIgnoreCase("off")) {
									if(!chatSlowTime.containsKey("Slow")) {
										 p.sendMessage(getStrings.getError("Chat-slow is already turned off!"));
									} else {
										chatSlowTime.remove("Slow");
										Bukkit.broadcastMessage(getStrings.getString("Chat", "Chat slow was turned off!"));
									}
								} else {
									int seconds = Integer.parseInt(args[1]);
									if(seconds == 0) {
										p.sendMessage(getStrings.getError("You cannot set chatslow to 0! If you are trying to turn chat slow off, do /chat slow off instead!"));
										return true;
									} 
									chatSlowTime.put("Slow", seconds);
									Bukkit.broadcastMessage(getStrings.getString("Chat", "Chat was slowed to &b" + seconds + " seconds &7!"));
								}
							}
						}
					}
				}
			} else {
				p.sendMessage(getStrings.getNoPermission("Trainee"));
			}
		}
		return false;
	}

}

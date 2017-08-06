package com.funkemunky.Delta.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.funkemunky.Delta.Core;


public class ChatHandler implements Listener {

	private Core core;
	public HashMap<String, Boolean> chathandler = new HashMap<String, Boolean>();
	public HashMap<String, Double> numberamount = new HashMap<String, Double>();
	public ArrayList<String> chatdelay = new ArrayList<String>();
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(chathandler.get("mutechat") == true) {
			if(!e.getPlayer().hasPermission("delta.staff")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(getStrings.getPrefix() + ChatColor.GRAY  + "The chat is currently muted.");
			}
		}
		if(chathandler.get("slowchat") == true) {
			if(!e.getPlayer().hasPermission("delta.staff")) {
				chatdelay.add(e.getPlayer().getUniqueId().toString());
				Bukkit.getScheduler().scheduleSyncDelayedTask(core, new Runnable() {
					public void run() {
						chatdelay.remove(e.getPlayer().getUniqueId().toString());
					}
				}, numberamount.get("chatdelay").intValue());
			}
		}
		if(chatdelay.contains(e.getPlayer().getUniqueId().toString())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(getStrings.getPrefix() + ChatColor.GRAY + "Since chat slow is enabled, you cannot chat for " + ChatColor.RED + numberamount.get("chatdelay") + ChatColor.GRAY + "s.");
		}
	}

}

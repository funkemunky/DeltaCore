package com.funkemunky.Delta.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatFormat implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if(!p.hasPermission("delta.staff")) {
		e.setFormat(PermissionsEx.getUser(p).getPrefix().replaceAll("&", "§") +  PermissionsEx.getUser(p).getSuffix().replaceAll("&", "§") + ChatColor.GRAY + p.getName() + " " + ChatColor.WHITE + e.getMessage());
		} else {
			if(p.hasPermission("delta.staff")) {
				e.setFormat(PermissionsEx.getUser(p).getPrefix().replaceAll("&", "§") + PermissionsEx.getUser(p).getSuffix().replaceAll("&", "§") + ChatColor.GRAY + p.getDisplayName() +  " " + ChatColor.AQUA + e.getMessage());
			}
		}
	}
}

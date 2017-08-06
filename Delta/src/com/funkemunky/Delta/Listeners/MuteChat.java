package com.funkemunky.Delta.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.funkemunky.Delta.Core;
import com.funkemunky.Delta.Utils.getStrings;

public class MuteChat implements Listener {
	
	private Core core;
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if(core.chat.get("Muted") == true) {
			e.setCancelled(true);
			p.sendMessage(getStrings.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&7You cannot speak since the chat is disabled!"));
		}
	}


}

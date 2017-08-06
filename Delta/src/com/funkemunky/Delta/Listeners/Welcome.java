package com.funkemunky.Delta.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.funkemunky.Delta.Core;
import com.funkemunky.Delta.Utils.getStrings;

public class Welcome implements Listener {
	
	@SuppressWarnings("unused")
	private Core core;
	public Welcome(Core core) {
		this.core = core;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onFirstJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPlayedBefore()) {
			Bukkit.broadcastMessage(getStrings.getPrefix() + ChatColor.GREEN + "Welcome to Delta " + ChatColor.RED + p.getName() + ChatColor.GREEN + "!");
		} else {
			for(String motd : getStrings.getMOTD(p)) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', motd));
			}
		}
		e.setJoinMessage(null);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
	
}

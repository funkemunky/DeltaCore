package com.funkemunky.Delta.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.funkemunky.Delta.Utils.getStrings;

public class FullServer implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(Bukkit.getOnlinePlayers().length <= 150) {
			if(p.hasPermission("delta.donor")) {
				for(String reason : getStrings.MAX_PLAYERS_KICK()) {
					p.kickPlayer(reason);
				}
			}
		}
	}

}

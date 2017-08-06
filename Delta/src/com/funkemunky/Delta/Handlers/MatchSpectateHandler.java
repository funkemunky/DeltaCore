package com.funkemunky.Delta.Handlers;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.funkemunky.Delta.Core;

public class MatchSpectateHandler implements Listener {
	
	@SuppressWarnings("unused")
	private Core core;
	public MatchSpectateHandler(Core core) {
		this.core = core;
	}
	
	public HashMap<Player, Player> combat = new HashMap<Player, Player>();

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player damager = (Player) e.getDamager();
			Player damaged = (Player) e.getEntity();
			combat.remove(damager);
			combat.remove(damaged);
			combat.put(damager, damaged);
			combat.put(damaged, damager);
		}
	}

}

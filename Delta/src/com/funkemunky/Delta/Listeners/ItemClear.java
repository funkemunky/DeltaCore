package com.funkemunky.Delta.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import com.funkemunky.Delta.Core;


public class ItemClear implements Listener {

	@SuppressWarnings("unused")
	private Core core;
	public ItemClear(Core core) {
		this.core = core;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onItemDrop(PlayerDropItemEvent e) {
		e.getItemDrop().remove();
	}
}

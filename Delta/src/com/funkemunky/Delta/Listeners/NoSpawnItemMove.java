package com.funkemunky.Delta.Listeners;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.funkemunky.Delta.Core;

public class NoSpawnItemMove implements Listener {
	
	@SuppressWarnings("unused")
	private Core core;
	public NoSpawnItemMove(Core core) {
		this.core = core;
	}

	public boolean isInRect(Player player, Location loc1, Location loc2)
	{
	    double[] dim = new double[2];
	 
	    dim[0] = loc1.getX();
	    dim[1] = loc2.getX();
	    Arrays.sort(dim);
	    if(player.getLocation().getX() > dim[1] || player.getLocation().getX() < dim[0])
	        return false;
	 
	    dim[0] = loc1.getZ();
	    dim[1] = loc2.getZ();
	    Arrays.sort(dim);
	    if(player.getLocation().getZ() > dim[1] || player.getLocation().getZ() < dim[0])
	        return false;
	 
	    dim[0] = loc1.getY();
	    dim[1] = loc2.getY();
	    Arrays.sort(dim);
	    if(player.getLocation().getY() > dim[1] || player.getLocation().getY() < dim[0])
	        return false;
	 
	    return true;
	}
	
	@EventHandler
	public void onPlayerItemDrop(PlayerDropItemEvent e) {
		Location one = new Location(Bukkit.getWorld("Practice"), 780, 17, -1434);
		Location two = new Location(Bukkit.getWorld("Practice"), 860, 30, -1546);
		
		if(isInRect(e.getPlayer(), one, two)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		Location one = new Location(Bukkit.getWorld("Practice"), 780, 17, -1434);
		Location two = new Location(Bukkit.getWorld("Practice"), 860, 30, -1546);
		
		if(isInRect((Player) e.getWhoClicked(), one, two)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Location one = new Location(Bukkit.getWorld("Practice"), 780, 17, -1434);
		Location two = new Location(Bukkit.getWorld("Practice"), 860, 30, -1546);
		
		if(isInRect((Player) e.getWhoClicked(), one, two)) {
			e.setCancelled(true);
		}
	}

}

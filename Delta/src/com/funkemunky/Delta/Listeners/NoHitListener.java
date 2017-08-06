package com.funkemunky.Delta.Listeners;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NoHitListener implements Listener {
	
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
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		Player p = (Player) e.getEntity();
		Player dm = (Player) e.getDamager();
			Location one = new Location(Bukkit.getWorld("Practice"), 780, 17, -1447);
			Location two = new Location(Bukkit.getWorld("Practice"), 860, 30, -1546);
			if(isInRect(p, one, two)) {
				e.setCancelled(true);
			}
			if(isInRect(dm, one, two)) {
				e.setCancelled(true);
			}
	}

}

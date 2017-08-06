package com.funkemunky.Delta.Listeners;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import com.funkemunky.Delta.Core;

public class DoubleJump implements Listener {
	
	private Core core;
	public DoubleJump(Core core) {
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
	public void onFlightToggle(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		
		if(p.getGameMode() == GameMode.CREATIVE) return;
		
		if(!p.hasPermission("delta.staff")) {
			e.setCancelled(true);
			p.setAllowFlight(false);
			p.setFlying(false);
			p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 10, 2);
			p.setVelocity(p.getLocation().getDirection().multiply(1.09).setY(1));
		}
	}
	
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		Location one = new Location(Bukkit.getWorld("Practice"), 780, 17, -1447);
		Location two = new Location(Bukkit.getWorld("Practice"), 860, 30, -1546);
		
		if((p.getGameMode()!=GameMode.CREATIVE)
				&&(p.getLocation().subtract(0, 1, 0).getBlock().getType()!=Material.AIR)
				&&(!p.isFlying())
				&&(p.hasPermission("delta.doublejump"))
				&&(isInRect(p, one, two) == true)
				&&(!p.hasPermission("delta.staff")))
			if(core.doublejump.get(p.getUniqueId()) == null) {
				p.setAllowFlight(true);
			} else {
				if(core.doublejump.get(p.getUniqueId()) == true) {
					p.setAllowFlight(true);
				}
			}
			
	}

}

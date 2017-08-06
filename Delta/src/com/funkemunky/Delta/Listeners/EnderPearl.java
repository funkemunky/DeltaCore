package com.funkemunky.Delta.Listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.funkemunky.Delta.Core;
import com.funkemunky.Delta.Utils.getStrings;

public class EnderPearl implements Listener {
	
	private Core core;
	public EnderPearl(Core core) {
		this.core = core;
	}

	HashMap<UUID, BukkitRunnable> enderpearlTask = new HashMap<UUID, BukkitRunnable>();
	HashMap<UUID, Integer> enderpearlTime = new HashMap<UUID, Integer>();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.ENDER_PEARL) {
				if(enderpearlTime.containsKey(e.getPlayer().getUniqueId())) {
					e.setCancelled(true);
					ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL, 1);
	         	    ItemMeta enderpearlmeta = enderpearl.getItemMeta();
	         	    enderpearlmeta.setDisplayName(e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName());
	         	    enderpearl.setItemMeta(enderpearlmeta);
	            	e.getPlayer().sendMessage(getStrings.getString("Enderpearl", "You cannot pearl for " + ChatColor.AQUA + enderpearlTime.get(e.getPlayer().getUniqueId()) +  "s"  + ChatColor.GRAY + "!"));
				}
				if(!enderpearlTime.containsKey(e.getPlayer().getUniqueId())) {
					enderpearlTime.put(e.getPlayer().getUniqueId(), 15);
					enderpearlTask.put(e.getPlayer().getUniqueId(), new BukkitRunnable() {
	                    public void run() {
	                    	    ItemStack enderpearl = e.getPlayer().getInventory().getItemInHand();
	                    	    ItemMeta enderpearlmeta = enderpearl.getItemMeta();
	                    	    if(enderpearl.getType() == Material.ENDER_PEARL) {
	                    	    	enderpearlmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lEnderPearl Cooldown: &c" + enderpearlTime.get(e.getPlayer().getUniqueId()) + "s"));
	                    	    	enderpearl.setItemMeta(enderpearlmeta);
	                    	    }
	                            enderpearlTime.put(e.getPlayer().getUniqueId(), enderpearlTime.get(e.getPlayer().getUniqueId()) - 1);
	                            if (enderpearlTime.get(e.getPlayer().getUniqueId()) == 0) {
	                                    enderpearlTime.remove(e.getPlayer().getUniqueId());
	                                    if(enderpearl.getType() == Material.ENDER_PEARL) {
	                            	    	enderpearlmeta.setDisplayName(ChatColor.WHITE + "Ender Pearl");
	                            	    	enderpearl.setItemMeta(enderpearlmeta);
	                            	    }
	                                    enderpearlTask.remove(e.getPlayer().getUniqueId());
	                                    cancel();
	                            }
	                    }
					});
	           
	            enderpearlTask.get(e.getPlayer().getUniqueId()).runTaskTimer(core, 20, 20);
				}
			}
		}
	}

}

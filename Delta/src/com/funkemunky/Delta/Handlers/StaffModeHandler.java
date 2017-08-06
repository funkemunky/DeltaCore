package com.funkemunky.Delta.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.funkemunky.Delta.Core;
import com.funkemunky.Delta.Utils.getStrings;

public class StaffModeHandler implements CommandExecutor, Listener {

	private Core core;
	public StaffModeHandler(Core core) {
		this.core = core;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(core.inStaffMode.contains(e.getPlayer().getUniqueId())) {
				if(e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR) {
					Bukkit.dispatchCommand(e.getPlayer(), "vanish");
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(core.inStaffMode.contains(e.getWhoClicked().getUniqueId())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		if(core.inStaffMode.contains(e.getWhoClicked().getUniqueId())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerItemDrop(PlayerDropItemEvent e) {
		if(core.inStaffMode.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerInteractEnt(PlayerInteractEntityEvent e) {
		if(e.getRightClicked() instanceof Player) {
			if(e.getPlayer().hasPermission("delta.staff")) {
			    if(e.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD) {
		    		Player rightclick = (Player) e.getRightClicked();
			    	Bukkit.dispatchCommand(e.getPlayer(), "freeze " + rightclick.getName());
		        }
			    if(e.getPlayer().getItemInHand().getType() == Material.CHEST) {
			    	Player rightclick = (Player) e.getRightClicked();
			    	Bukkit.dispatchCommand(e.getPlayer(), "invsee " + rightclick.getName());
			    }
		    }
	    }
	}
	
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("staff")) {
				if(p.hasPermission("delta.staff")) {
					if(!core.inStaffMode.contains(p.getUniqueId())) {
						ItemStack vanish = new ItemStack(Material.NETHER_STAR);
						ItemMeta vanishmeta = vanish.getItemMeta();
						vanishmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aToggle Vanish"));
						vanish.setItemMeta(vanishmeta);
						core.inStaffMode.add(p.getUniqueId());
						ItemStack freeze = new ItemStack(Material.BLAZE_ROD);
						ItemMeta freezemeta = freeze.getItemMeta();
						freezemeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Freeze"));
						freeze.setItemMeta(freezemeta);
						core.inventory.put(p.getUniqueId(), p.getInventory().getContents());
						ItemStack inspect = new ItemStack(Material.CHEST);
						ItemMeta inspectmeta = inspect.getItemMeta();
						inspectmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aInspect"));
						inspect.setItemMeta(inspectmeta);
						p.getInventory().clear();
						p.getInventory().setItem(0, freeze);
						p.getInventory().setItem(4, inspect);
						p.getInventory().setItem(8, vanish);
						p.setGameMode(GameMode.CREATIVE);
						p.sendMessage(getStrings.getString("Staff", "You have entered staffmode."));
					} else {
						p.getInventory().clear();
						p.getInventory().setContents(core.inventory.get(p.getUniqueId()));
						core.inStaffMode.remove(p.getUniqueId());
						p.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(getStrings.getString("Staff", "You are no longer in staff mode."));
					}
				} else {
					p.sendMessage(getStrings.getNoPermission("Trainee"));
				}
			}
		} else {
			sender.sendMessage("Only players can use this feature!");
		}
		return false;
	}

}

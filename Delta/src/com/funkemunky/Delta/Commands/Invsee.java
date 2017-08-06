package com.funkemunky.Delta.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.funkemunky.Delta.Core;
import com.funkemunky.Delta.Utils.getStrings;


public class Invsee implements CommandExecutor, Listener { 
	
	@SuppressWarnings("unused")
	private Core core;
	public Invsee(Core core) {
		this.core = core;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getInventory().getName().endsWith("'s Inventory")) {
			if(e.getSlot() == 48 || e.getSlot() == 49 || e.getSlot() == 50) {
				e.setCancelled(true);
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("invsee")) {
				if(p.hasPermission("delta.staff")) {
					if(args.length == 0) {
						p.sendMessage(getStrings.getUsage("/invsee <player>"));
					} else {
						if(args.length == 1) {
							Player online = Bukkit.getPlayerExact(args[0]);
							if(online != null) {
								Player target = Bukkit.getPlayer(args[0]);
								Inventory playerinv = Bukkit.createInventory(null, 54, target.getName() + "'s Inventory");
								
								for (ItemStack item : target.getInventory()) {
	                                if (item != null) playerinv.addItem(item);
	                            }
								Damageable d = (Player) target;
								ItemStack health = new ItemStack(Material.SPECKLED_MELON);
								ItemMeta healthmeta = health.getItemMeta();
								healthmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c" + d.getHealth()));
								health.setItemMeta(healthmeta);
								ItemStack food = new ItemStack(Material.COOKED_BEEF);
								ItemMeta foodmeta = health.getItemMeta();
								foodmeta.setDisplayName(ChatColor.GOLD + "" +  p.getFoodLevel());
								food.setItemMeta(foodmeta);
								ItemStack clearinv = new ItemStack(Material.ANVIL);
								ItemMeta clearinvmeta = clearinv.getItemMeta();
								clearinvmeta.setDisplayName(ChatColor.GREEN + "Clear " + target.getName() + "'s inventory");
								clearinv.setItemMeta(clearinvmeta);
								playerinv.setItem(48, health);
								playerinv.setItem(50, food);
								
								p.openInventory(playerinv);
							} else {
								p.sendMessage(getStrings.getError("Player is this online!"));
							}
						}
					}
				}
			}
		} else {
			sender.sendMessage("Only player can use this feature!");
		}
		return false;
	}

}

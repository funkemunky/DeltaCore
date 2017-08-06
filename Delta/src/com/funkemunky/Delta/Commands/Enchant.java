package com.funkemunky.Delta.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.funkemunky.Delta.Utils.Enchantments;
import com.funkemunky.Delta.Utils.getStrings;

public class Enchant implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this feature!");
		} else {
			Player p = (Player) sender; 
			if(cmd.getName().equalsIgnoreCase("enchant")) {
				if(p.hasPermission("delta.enchant")) {
					if(args.length < 2) {
						p.sendMessage(getStrings.getUsage("/enchant <enchantment> <level>"));
					}
					if(args.length == 2) {
						int level = Integer.parseInt(args[1]);
						Enchantment enchantment = Enchantments.getByName(args[0]);
						ItemStack hand = p.getItemInHand();
						if(level == 0) {
							hand.removeEnchantment(enchantment);
							p.sendMessage(getStrings.getString("Enchant", "&7Removed &c" + Enchantments.getEnchantmentName(enchantment) + " &7from your &c" + hand.getType().name().replaceAll("_", " ").toLowerCase() + "&7!"));
						} else {
							hand.addUnsafeEnchantment(enchantment, level);
							p.sendMessage(getStrings.getString("Enchant", "Enchanted your " + ChatColor.RED + hand.getType().name().replaceAll("_", " ").toLowerCase() + ChatColor.GRAY + " with " + ChatColor.RED + Enchantments.getEnchantmentName(enchantment).toLowerCase() + ChatColor.GRAY + "!"));
						}
					}
				} else {
					p.sendMessage(getStrings.getNoPermission("Administrator"));
				}
			}
		}
		return false;
	}


}

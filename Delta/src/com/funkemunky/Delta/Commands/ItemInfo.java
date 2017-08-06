package com.funkemunky.Delta.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemInfo implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do this.");
            return true;
        }
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("iteminfo")) {
			if(p.hasPermission("delta.iteminfo")) {
				ItemStack hand = p.getItemInHand();
			    Material itemtype = hand.getType();
			    String itlc = itemtype.toString().toLowerCase();
				String cap = itlc.toString().substring(0, 1).toUpperCase() + itlc.toString().substring(1);
				p.sendMessage("");
				p.sendMessage(ChatColor.RED + cap + ":");
				p.sendMessage(ChatColor.GRAY + "ItemName: " + ChatColor.RED + itemtype);
				p.sendMessage(ChatColor.GRAY + "ItemID: " + ChatColor.RED + itemtype.getId() + ":" + hand.getData().getData());
				p.sendMessage("");
			} else {
				p.sendMessage(ChatColor.GRAY + "You need Moderator for this command!");
			}
		}
		
		return false;
	}

}

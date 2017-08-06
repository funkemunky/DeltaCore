package com.funkemunky.Delta.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Spawner implements CommandExecutor {
	
	 public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
         if (!(sender instanceof Player)) {
                 sender.sendMessage(ChatColor.RED + "Only players can do this.");
                 return true;
         }
        
         Player p = (Player) sender;
        
         if (cmd.getName().equalsIgnoreCase("spawner")) {
                if(p.hasPermission("delta.spawner")) {
                	 if (args.length == 0) {
                         p.sendMessage(ChatColor.RED + "You must specify the mob type!");
                         return true;
                 }
                
                 EntityType type;
                
                 try {
                         type = EntityType.valueOf(args[0].toUpperCase());
                 }
                 catch (Exception e) {
                         p.sendMessage(ChatColor.RED + "That is not valid input!");
                         return true;
                 }
                
                 @SuppressWarnings("deprecation")
                 Block b = p.getTargetBlock(null, 10);
                
                 if (b == null) {
                         p.sendMessage(ChatColor.RED + "You aren't looking at a block!");
                         return true;
                 }
                
                 if (!b.getType().equals(Material.MOB_SPAWNER)) {
                         p.sendMessage(ChatColor.RED + "That isn't a mob spawner!");
                         return true;
                 }
                
                 CreatureSpawner s = (CreatureSpawner) b.getState();
                 s.setSpawnedType(type);
                 s.update();
                 p.sendMessage(ChatColor.GRAY + "That spawner is now for " + ChatColor.RED + type.toString().toLowerCase() + "s" + ChatColor.GRAY + "!");
                } else {
                	p.sendMessage(ChatColor.GRAY + "You need Admin for this command!");
                }
         }
        
         return true;
 }

}

package com.funkemunky.Delta.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
 
public class Vanish implements CommandExecutor, Listener {
       
        public static ArrayList<Player> vanished = new ArrayList<Player>();
 
        @SuppressWarnings("deprecation")
		public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
               
                if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "You cannot vanish!");
                        return true;
                }
               
                Player p = (Player) sender;
               
                if (cmd.getName().equalsIgnoreCase("vanish")) {
                      if(p.hasPermission("delta.vanish")) {
                          if (!vanished.contains(p)) {
                              for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                                      pl.hidePlayer(p);
                                      if(pl.hasPermission("delta.staff")) {
                                    	  pl.canSee(p);
                                      }
                              }
                              vanished.add(p);
                              p.sendMessage(ChatColor.GRAY + "You are now vanished!");
                              return true;
                      }
                      else {
                              for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                                      pl.showPlayer(p);
                              }
                              vanished.remove(p);
                              p.sendMessage(ChatColor.GRAY + "You are now unvanished!");
                              return true;
                      }
                      }
                }
                return true;
        }
       
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
                for (Player p : vanished) {
                        e.getPlayer().hidePlayer(p);
                }
        }
       
        @EventHandler
        public void onPlayerLeave(PlayerQuitEvent e) {
                vanished.remove(e.getPlayer());
        }
}
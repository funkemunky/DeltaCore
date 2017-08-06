package com.funkemunky.Delta.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.funkemunky.Delta.Core;

public class Toggle implements CommandExecutor {
	
	private Core core;
	public Toggle(Core core) {
		this.core = core;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("togglepms")) {
				if(core.PMsToggle.get(p.getUniqueId()) == null) {
					core.PMsToggle.put(p.getUniqueId(), false);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You toggled PMs &coff&7!"));
				} else {
					if(core.PMsToggle.get(p.getUniqueId()) == false) {
						core.PMsToggle.put(p.getUniqueId(), true);
						p.sendMessage(ChatColor.GRAY + "You toggled PMs on!");
					} else {
						if(core.PMsToggle.get(p.getUniqueId()) == true) {
							core.PMsToggle.put(p.getUniqueId(), false);
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You toggled PMs &coff&7!"));
						}
					}
				}
		}
		if(cmd.getName().equalsIgnoreCase("noises")) {
			if(core.PMNoises.get(p.getUniqueId()) == null) {
				core.PMNoises.put(p.getUniqueId(), false);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You toggled PM noises &coff&7!"));
			} else {
				if(core.PMNoises.get(p.getUniqueId()) == false) {
					core.PMNoises.put(p.getUniqueId(), true);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You toggled PMs &con&7!"));
				} else {
					if(core.PMNoises.get(p.getUniqueId()) == true) {
						core.PMNoises.put(p.getUniqueId(), false);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You toggled PM noises &coff&7!"));
					}
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("doublejump")) {
			if(p.hasPermission("delta.doublejump")) {
				if(core.doublejump.get(p.getUniqueId()) == null) {
					core.doublejump.put(p.getUniqueId(), false);
					p.sendMessage(ChatColor.GRAY + "You can no longer double jump in spawn!");
				} else {
					if(core.doublejump.get(p.getUniqueId()) == false) {
						core.doublejump.put(p.getUniqueId(), true);
						p.sendMessage(ChatColor.GRAY + "You can now double jump in spawn!");
					} else {
						if(core.doublejump.get(p.getUniqueId()) == true) {
							core.doublejump.put(p.getUniqueId(), false);
							p.sendMessage(ChatColor.GRAY + "You can no longer double jump in spawn!");
						}
				    }
				}
			} else {
				p.sendMessage(ChatColor.GRAY + "You need to purchase Delta rank to use this feature!");
			}
		}
		return false;
	}

}

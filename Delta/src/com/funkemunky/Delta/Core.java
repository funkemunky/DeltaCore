package com.funkemunky.Delta;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.funkemunky.Delta.Commands.Clear;
import com.funkemunky.Delta.Commands.Enchant;
import com.funkemunky.Delta.Commands.Fly;
import com.funkemunky.Delta.Commands.Funkemunky;
import com.funkemunky.Delta.Commands.Gamemode;
import com.funkemunky.Delta.Commands.Hacker;
import com.funkemunky.Delta.Commands.Heal;
import com.funkemunky.Delta.Commands.Invsee;
// import com.funkemunky.Delta.Commands.Help;
import com.funkemunky.Delta.Commands.ItemInfo;
import com.funkemunky.Delta.Commands.Kill;
import com.funkemunky.Delta.Commands.PM;
import com.funkemunky.Delta.Commands.ReName;
import com.funkemunky.Delta.Commands.Spawner;
import com.funkemunky.Delta.Commands.Speed;
import com.funkemunky.Delta.Commands.StaffChat;
import com.funkemunky.Delta.Commands.Teleport;
import com.funkemunky.Delta.Commands.Toggle;
import com.funkemunky.Delta.Commands.Vanish;
import com.funkemunky.Delta.Handlers.ChatHandler;
import com.funkemunky.Delta.Handlers.MatchSpectateHandler;
import com.funkemunky.Delta.Handlers.StaffModeHandler;
import com.funkemunky.Delta.Listeners.ChatFormat;
import com.funkemunky.Delta.Listeners.DoubleJump;
import com.funkemunky.Delta.Listeners.EnderPearl;
import com.funkemunky.Delta.Listeners.ItemClear;
import com.funkemunky.Delta.Listeners.NoHitListener;
import com.funkemunky.Delta.Listeners.NoSpawnItemMove;
import com.funkemunky.Delta.Listeners.Welcome;
import com.funkemunky.Delta.Utils.MySQL;
import com.funkemunky.Delta.Utils.SimpleScoreboard;
import com.funkemunky.Delta.Utils.getStrings;

import net.milkbowl.vault.permission.Permission;

public class Core extends JavaPlugin implements Listener {
	
	public HashMap<UUID, UUID> lastSentMessages = new HashMap<UUID, UUID>();
	public HashMap<UUID, Boolean> PMsToggle = new HashMap<UUID, Boolean>();
	public HashMap<UUID, Boolean> PMNoises = new HashMap<UUID, Boolean>();
	public HashMap<UUID, Boolean> doublejump = new HashMap<UUID, Boolean>();
	public HashMap<String, Boolean> chat = new HashMap<String, Boolean>();
	public ArrayList<UUID> hacker = new ArrayList<UUID>();
	public ArrayList<UUID> inStaffMode = new ArrayList<UUID>();
	public HashMap<UUID, ItemStack[]> inventory = new HashMap<UUID, ItemStack[]>();
	
	private List<String> players = new ArrayList<String>();
	
	public Permission perms;
	
	HashMap<UUID, Location> freezelocation = new HashMap<UUID, Location>();
	
	public Random r;
    public HashMap<Player, Integer> cooldownTime;
    public HashMap<Player, BukkitRunnable> cooldownTask;
    
    private MySQL mysql;
	
	public File file = new File(getDataFolder(), "config.yml");
	public FileConfiguration stats;
	public File statsFile = new File(getDataFolder() + File.separator + "Data", "dbs.yml");
	
	ArrayList<UUID> isFrozen = new ArrayList<UUID>();
	
	 private boolean setupPermissions()
	    {
	        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	        if (permissionProvider != null) {
	            perms = permissionProvider.getProvider();
	        }
	        return (perms != null);
	    }

	//Freeze Listener
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent e) {
		if(isFrozen.contains(e.getPlayer().getUniqueId())) {
			e.getPlayer().teleport(freezelocation.get(e.getPlayer().getUniqueId()));
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamage(EntityDamageByEntityEvent e)  {
		
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(isFrozen.contains(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(isFrozen.contains(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if(players.contains(p.getName())) {
			players.remove(p.getName());
		}
		e.setQuitMessage(null);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
	    SimpleScoreboard scoreboard = new SimpleScoreboard("§c§lDelta§f§lPractice §7§l(BETA)");
	    scoreboard.send(p);
	    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	    	@SuppressWarnings({ "deprecation", "unused" })
			public void run() {
	   		 if(!inStaffMode.contains(p.getUniqueId())) {
				 if(!p.hasPermission("delta.staff")) {
					    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&c&7&m&l------------------"), 8);
						int online = Bukkit.getOnlinePlayers().length;
						scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lPlayers: &7" + String.valueOf(online)), 7);
						scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lPing: &f" + getPing(p) + "ms"), 6);
						scoreboard.add("   ", 5);
						scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lRank"), 4);
					    if(perms.getPrimaryGroup(p).equalsIgnoreCase("Owner") || perms.getPrimaryGroup(p).equalsIgnoreCase("Manager")) {
					    	scoreboard.add(ChatColor.GOLD + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.RED + "Wow, you gotta lot o' power.", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Admin") || perms.getPrimaryGroup(p).equalsIgnoreCase("Director")) {
					    	scoreboard.add(ChatColor.RED + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GOLD + "Get to work you carrot!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Moderator") || perms.getPrimaryGroup(p).equalsIgnoreCase("V.Moderator")) {
					    	scoreboard.add(ChatColor.LIGHT_PURPLE + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GOLD + "Get to work you carrot!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("TrialMod") || perms.getPrimaryGroup(p).equalsIgnoreCase("Trainee")) {
					    	scoreboard.add(ChatColor.BLUE + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GREEN + "Report to you mentor!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Premium") || perms.getPrimaryGroup(p).equalsIgnoreCase("Delta")) {
					    	scoreboard.add(ChatColor.GREEN + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GRAY + "Thanks for donating!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Famous") || perms.getPrimaryGroup(p).equalsIgnoreCase("Media")) {
					    	scoreboard.add(ChatColor.LIGHT_PURPLE + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GREEN + "Thanks for spreading the word!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("default") || perms.getPrimaryGroup(p).equalsIgnoreCase("beta")){
					    	scoreboard.add(ChatColor.GRAY + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.RED + "Donate with /shop!",2);
					    } else {
					    	scoreboard.add(ChatColor.GRAY + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(""+ ChatColor.RED + ChatColor.ITALIC + "No msg set.",  2);
					    }
					    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&7&m&l------------------"), 1);
						scoreboard.update();
				 } else {
					    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&c&7&m&l------------------"), 9);
						int online = Bukkit.getOnlinePlayers().length;
						scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lStaffMode: &cfalse"), 8);
						scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lPlayers: &7" + String.valueOf(online)), 7);
						scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lPing: &f" + getPing(p) + "ms"), 6);
						scoreboard.add("   ", 5);
						scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lRank"), 4);
					    if(perms.getPrimaryGroup(p).equalsIgnoreCase("Owner") || perms.getPrimaryGroup(p).equalsIgnoreCase("Manager")) {
					    	scoreboard.add(ChatColor.GOLD + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.RED + "Wow, you gotta lot o' power.", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Admin") || perms.getPrimaryGroup(p).equalsIgnoreCase("Director")) {
					    	scoreboard.add(ChatColor.RED + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GOLD + "Get to work you carrot!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Moderator") || perms.getPrimaryGroup(p).equalsIgnoreCase("V.Moderator")) {
					    	scoreboard.add(ChatColor.LIGHT_PURPLE + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GOLD + "Get to work you carrot!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("TrialMod") || perms.getPrimaryGroup(p).equalsIgnoreCase("Trainee")) {
					    	scoreboard.add(ChatColor.BLUE + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GREEN + "Report to you mentor!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Premium") || perms.getPrimaryGroup(p).equalsIgnoreCase("Delta")) {
					    	scoreboard.add(ChatColor.GREEN + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GRAY + "Thanks for donating!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Famous") || perms.getPrimaryGroup(p).equalsIgnoreCase("Media")) {
					    	scoreboard.add(ChatColor.LIGHT_PURPLE + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.GREEN + "Thanks for spreading the word!", 2);
					    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("default") || perms.getPrimaryGroup(p).equalsIgnoreCase("beta")){
					    	scoreboard.add(ChatColor.GRAY + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(ChatColor.RED + "Donate with /shop!",2);
					    } else {
					    	scoreboard.add(ChatColor.GRAY + perms.getPrimaryGroup(p), 3);
					    	scoreboard.add(""+ ChatColor.RED + ChatColor.ITALIC + "No msg set.",  2);
					    }
					    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&7&m&l------------------"), 1);
						scoreboard.update();
				 }
			 } else {
				   scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&c&7&m&l------------------"), 9);
					int online = Bukkit.getOnlinePlayers().length;
					scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lStaffMode: &atrue"), 8);
					if(Vanish.vanished.contains(p)) {
						scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lVanished: &atrue"), 7);
					} else {
						scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lVanished: &cfalse"), 7);
					}
					List<String> players = new ArrayList<String>();

					for(Player player : Bukkit.getOnlinePlayers()){
					    if(player.hasPermission("delta.staff")) {
					        players.add(player.getName());
					    }
					}
					scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lOnline Staff: &7" + players.size()), 6);
					scoreboard.add("   ", 5);
					scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&b&lRank"), 4);
				    if(perms.getPrimaryGroup(p).equalsIgnoreCase("Owner") || perms.getPrimaryGroup(p).equalsIgnoreCase("Manager")) {
				    	scoreboard.add(ChatColor.GOLD + perms.getPrimaryGroup(p), 3);
				    	scoreboard.add(ChatColor.RED + "Wow, you gotta lot o' power.", 2);
				    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Admin") || perms.getPrimaryGroup(p).equalsIgnoreCase("Director")) {
				    	scoreboard.add(ChatColor.RED + perms.getPrimaryGroup(p), 3);
				    	scoreboard.add(ChatColor.GOLD + "Get to work you carrot!", 2);
				    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Moderator") || perms.getPrimaryGroup(p).equalsIgnoreCase("V.Moderator")) {
				    	scoreboard.add(ChatColor.LIGHT_PURPLE + perms.getPrimaryGroup(p), 3);
				    	scoreboard.add(ChatColor.GOLD + "Get to work you carrot!", 2);
				    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("TrialMod") || perms.getPrimaryGroup(p).equalsIgnoreCase("Trainee")) {
				    	scoreboard.add(ChatColor.BLUE + perms.getPrimaryGroup(p), 3);
				    	scoreboard.add(ChatColor.GREEN + "Make sure to report to your mentor!", 2);
				    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Premium") || perms.getPrimaryGroup(p).equalsIgnoreCase("Delta")) {
				    	scoreboard.add(ChatColor.GREEN + perms.getPrimaryGroup(p), 3);
				    	scoreboard.add(ChatColor.GRAY + "Thanks for donating!", 2);
				    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("Famous") || perms.getPrimaryGroup(p).equalsIgnoreCase("Media")) {
				    	scoreboard.add(ChatColor.LIGHT_PURPLE + perms.getPrimaryGroup(p), 3);
				    	scoreboard.add(ChatColor.GREEN + "Thanks for spreading the word!", 2);
				    } else if(perms.getPrimaryGroup(p).equalsIgnoreCase("default") || perms.getPrimaryGroup(p).equalsIgnoreCase("beta")){
				    	scoreboard.add(ChatColor.GRAY + perms.getPrimaryGroup(p), 3);
				    	scoreboard.add(ChatColor.RED + "Donate with /shop",2);
				    } else {
				    	scoreboard.add(ChatColor.GRAY + perms.getPrimaryGroup(p), 3);
				    	scoreboard.add(""+ ChatColor.RED + ChatColor.ITALIC + "No msg set.",  2);
				    }
				    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&7&m&l------------------"), 1);
					scoreboard.update();
			 }
	    	}
	    }, 0, 6);
	}
	
	public void reloadDbs() {
	    if (statsFile == null) {
	    statsFile = new File(getDataFolder(), "dbs.yml");
	    }
	    stats = YamlConfiguration.loadConfiguration(statsFile);
	    Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(this.getResource("dbs.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        stats.setDefaults(defConfig);
	    }
	}
	public FileConfiguration getDbs() {
	    if (stats == null) {
	        reloadDbs();
	    }
	    return stats;
	}
	public void saveDbs() {
	    if (stats == null || statsFile == null) {
	        return;
	    }
	    try {
	        getDbs().save(statsFile);
	    } catch (IOException ex) {
	        getLogger().log(Level.SEVERE, "Could not save config to " + statsFile, ex);
	    }
	}
	
	public void saveDefaultDbs() {
		 if (statsFile == null) {
		        statsFile = new File(getDataFolder(), "dbs.yml");
		    }
		    if (!statsFile.exists()) {   
		    	getLogger().log(Level.WARNING, "No previous dbs file was found! Creating one now...");
		         saveResource("dbs.yml", false);
		         getLogger().log(Level.INFO, "dbs file was created!");
		     }
	}
	
	public Set<String> getPermission()
	{
		if (getDbs().isConfigurationSection("Permissions."))
		{
			return getDbs().getConfigurationSection("Permissions.").getKeys(false);
		}
		return new HashSet<String>();
	}
	
	public Set<String> getDeathbanTime()
	{
		if (getDbs().isConfigurationSection("Permissions." + getPermission() + ".Time"))
		{
			return getDbs().getConfigurationSection("Permissions." + getPermission() + ".Time").getKeys(false);
		}
		return new HashSet<String>();
	}
	
	public void onEnable() {
		
		//Start-up
		getLogger().info("Started plugin successfully!");
		
		//Commands
		getCommand("message").setExecutor(new PM(this));
		getCommand("reply").setExecutor(new PM(this));
		getCommand("togglepms").setExecutor(new Toggle(this));
		getCommand("noises").setExecutor(new Toggle(this));
	    getCommand("doublejump").setExecutor(new Toggle(this));
		getCommand("fly").setExecutor(new Fly());
		getCommand("tphere").setExecutor(new Teleport());
		getCommand("teleport").setExecutor( new Teleport());
		getCommand("tppos").setExecutor(new Teleport());
		getCommand("gamemode").setExecutor( new Gamemode());
		getCommand("survival").setExecutor(new Gamemode());
		getCommand("creative").setExecutor(new Gamemode());
		getCommand("adventure").setExecutor(new Gamemode());
		getCommand("iteminfo").setExecutor(new ItemInfo());
		// getCommand("help").setExecutor(new Help());
		getCommand("spawner").setExecutor(new Spawner());
		getCommand("kill").setExecutor(new Kill());
		getCommand("quit").setExecutor(new Kill());
		getCommand("clearinventory").setExecutor(new Clear());
		getCommand("enchant").setExecutor(new Enchant());
		getCommand("heal").setExecutor(new Heal());
		getCommand("hacker").setExecutor(new Hacker());
		getCommand("rename").setExecutor(new ReName());
		getCommand("speed").setExecutor(new Speed());
		getCommand("staffchat").setExecutor(new StaffChat());
		getCommand("vanish").setExecutor(new Vanish());
		getCommand("funkemunky").setExecutor(new Funkemunky());
		getCommand("staff").setExecutor(new StaffModeHandler(this));
		getCommand("invsee").setExecutor(new Invsee(this));
		getCommand("chat").setExecutor(new ChatHandler(this));
		
		//Listeners
		Bukkit.getPluginManager().registerEvents(new DoubleJump(this), this);
		Bukkit.getPluginManager().registerEvents(new ChatFormat(), this);
		Bukkit.getPluginManager().registerEvents(new NoHitListener(), this);
		Bukkit.getPluginManager().registerEvents(new Welcome(this), this);
		Bukkit.getPluginManager().registerEvents(new Vanish(), this);
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new Invsee(this), this);
		Bukkit.getPluginManager().registerEvents(new EnderPearl(this), this);
		Bukkit.getPluginManager().registerEvents(new ItemClear(this), this);
		Bukkit.getPluginManager().registerEvents(new MatchSpectateHandler(this), this);
		Bukkit.getPluginManager().registerEvents(new StaffModeHandler(this), this);
		Bukkit.getPluginManager().registerEvents(new NoSpawnItemMove(this), this);
		Bukkit.getPluginManager().registerEvents(new ChatHandler(this), this);
		
		//Config
		
		if(!file.exists()) {
			getLogger().log(Level.CONFIG, "Config does not exist! Creating one now...");
			
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
		
		//Other files
		saveDefaultDbs();
		
		//Cooldown for Reports
		cooldownTime = new HashMap<Player, Integer>();
        cooldownTask = new HashMap<Player, BukkitRunnable>();
        r = new Random();
        
        //MySQL Stuff
        if(getConfig().getBoolean("MySQL.Enabled") == true) {
        	mysql = new MySQL(getConfig().getString("MySQL.IP"), getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"), getConfig().getString("MySQL.Name"));
            mysql.createUsers();
        } else {
        	getLogger().log(Level.INFO, "MySQL disabled. Blackling will not work!");
        }
        
        //Vault
        setupPermissions();
       
	}
	
	 @EventHandler
     public void onPlayerLogin(PlayerLoginEvent e) {
            if(mysql.isPlayerBanned(e.getPlayer()) == true) {
            	 String reason = mysql.getBannedReason(e.getPlayer());
                 String staff = mysql.getStaff(e.getPlayer());
                 String kickreason = "&7You are &cblacklisted &7from Delta &cpermanently&7! \n&f\n&7Reason: &c%reason%\n&7Staff: &c%staff%\n&f\n&7Appeal at &b&ndeltapvp.net&7!".replaceAll("\\n", "\n");
                       	 if (reason != null) {
                             e.disallow(Result.KICK_BANNED, ChatColor.translateAlternateColorCodes('&', kickreason.replaceAll("%reason%", reason).replaceAll("%staff%", staff)));
                          } else {
                        	  e.disallow(Result.KICK_BANNED, ChatColor.translateAlternateColorCodes('&', kickreason.replaceAll("%reaso%", "No Reason Specified").replaceAll("%staff%", staff)));
                          }
            }
     }
	
	 public int getPing(Player who){
	     return ((CraftPlayer)who).getHandle().ping;
	 }
	 
	 @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		 if(label.equalsIgnoreCase("broadcast")) {
			 if(sender.hasPermission("delta.admin")) {
				 if(args.length == 0) {
					 sender.sendMessage("Not enough arguments!");
					 return true;
				 }
				 String message = "";
					for(int i = 0; i != args.length; i++)
						message += args[i] + " ";
			     Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
					 
			 }
		 }
		 if (cmd.getName().equalsIgnoreCase("blacklist")) {
			 if(sender.hasPermission("delta.admin")) {
				 if (args.length < 2) {
                     sender.sendMessage(getStrings.getUsage("/blacklist <player> <reason>"));
                     return true;
             }
             Player player = Bukkit.getServer().getPlayer(args[0]);
            
             StringBuilder reasonBuilder = new StringBuilder();
             for (int i = 1; i < args.length; i++) {
                     reasonBuilder.append(args[i]).append(" ");
             }
            
             String reason = reasonBuilder.toString();
            
             String staff = sender.getName();
             String kickreason = "&7You are &cblacklisted &7from Delta &cpermanently&7! \n&f\n&7Reason: &c%reason%\n&7Staff: &c%staff%\n&f\n&7Appeal at &b&ndeltapvp.net&7!".replaceAll("\\n", "\n");
            	 if (player != null) {
                  	 if (reason != null) {
                           player.kickPlayer(ChatColor.translateAlternateColorCodes('&', kickreason.replaceAll("%reason%", reason).replaceAll("%staff%", staff)));
                        } else {
                      	  player.kickPlayer(ChatColor.translateAlternateColorCodes('&', kickreason.replaceAll("%reason%", "No Reason Specified").replaceAll("%staff%", staff)));
                        }
                   }
             if(sender instanceof Player) {
            	 Player staff1 = (Player) sender;
            	 mysql.banPlayer(args[0], reason, staff1.getName());
            	 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7The player &c" + args[0] + " &7has been successfully blacklisted!"));
             } else {
            	 mysql.banPlayer(args[0], reason, "Console");
            	 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7The player &c" + args[0] + " &7has been successfully blacklisted!"));
             }
			 } else {
				 sender.sendMessage(getStrings.getNoPermission("Administrator"));
			 }
         }
		 if(cmd.getName().equalsIgnoreCase("unblacklist")) {
			 if(sender.hasPermission("delta.manager")) {
				 if(args.length < 1) {
					 sender.sendMessage(getStrings.getUsage("/unblacklist <player>"));
					 return true;
				 }
				 
				 mysql.unbanPlayer(args[0]);
				 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7The player &c" + args[0] + " &7has been successfully unblacklisted!"));
			 } else {
				 sender.sendMessage(getStrings.getNoPermission("Manager"));
			 }
		 }
		 if(cmd.getName().equalsIgnoreCase("delta")) {
				if(args.length == 0) {
					 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getStrings.getPrefix() + "&7DeltaCore v" + getDescription().getVersion() + " &7by funkemunky &7is fully operational!"));
					 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c- &7If you happen to find any bugs, report it on the forums!"));
				} else {
					if(args[0].equalsIgnoreCase("reload")) {
						if(sender.hasPermission("delta.admin")) {
							sender.sendMessage(getStrings.getString("Delta", "Reloading delta..."));
							reloadConfig();
							reloadDbs();
					        if(getConfig().getBoolean("MySQL.Enabled") == true) {
					        	mysql = new MySQL(getConfig().getString("MySQL.IP"), getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"), getConfig().getString("MySQL.Name"));
					            mysql.createUsers();
					        } else {
					        	getLogger().log(Level.INFO, "MySQL disabled. Blackling will not work!");
					        }
							sender.sendMessage(getStrings.getString("Delta", "&aSuccesfully reloaded!"));
						} else {
						    sender.sendMessage(getStrings.getNoPermission("Administrator"));
						}
					}
				}
			 }
		 if(sender instanceof Player) {
			 Player p = (Player) sender;
				if(cmd.getName().equalsIgnoreCase("uuid")) {
					if(p.hasPermission("delta.uuid")) {
						if(args.length == 0) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Your UUID: &c" + p.getUniqueId().toString()));
						} else {
							if(args.length == 1) {
								Player online = Bukkit.getPlayerExact(args[0]);
								if(online == null) {
									OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
									try {
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + "&7's UUID: &c" + target.getUniqueId().toString()));
									} catch(NullPointerException e) {
										p.sendMessage(getStrings.getError("The player you entered does not exist!"));
										e.printStackTrace();
									}
								} else {
									Player target = Bukkit.getPlayer(args[0]);
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + "&7's UUID: &c" + target.getUniqueId().toString()));
								}
							}
						}
					} else {
						p.sendMessage(getStrings.getNoPermission("Admin"));
					}
				}
				if(cmd.getName().equalsIgnoreCase("report")) {
					if(args.length < 2) {
						if(args.length == 1) {
							Player online = Bukkit.getPlayerExact(args[0]);
							if(online == null) {
								p.sendMessage(getStrings.getError("The player '" + args[0] + "' is not online!"));
							} else 
								p.sendMessage(getStrings.getUsage("/report <player> <reason>"));
						} else 
							p.sendMessage(getStrings.getUsage("/report <player> <reason>"));
					} else {
						Player online = Bukkit.getPlayerExact(args[0]);
						if(online != null) {
							Player reported = Bukkit.getPlayer(args[0]);
							String report = "";
							
							for(int i = 1; i != args.length; i++)
			                	
			                    report += args[i] + " ";
							if(report.length() > 24) {
								p.sendMessage(getStrings.getError("Your report exceeds 24 characters! Try making it shorter."));
							} else {
								if (!cooldownTime.containsKey(p)) {
									Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',"&8&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="), "delta.report.receive");
									Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',""), "delta.report.receive");
									Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',"&7[&c&lDaedalus&7] &aA player has made a report!"), "delta.report.receive");
									Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',""), "delta.report.receive");
									Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',"&7Player: &c" + p.getName()), "delta.report.receive");
									Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',"&7Reason: &c" + report), "delta.report.receive");
									Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',"&7Reported: &c" + reported.getName()), "delta.report.receive");
									Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',""), "delta.report.receive");
									Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',"&8&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="), "delta.report.receive");
									p.sendMessage(getStrings.getString("Reports", "Your report has been sent and will be answered immediately!"));
									 cooldownTime.put(p, 30);
				                        cooldownTask.put(p, new BukkitRunnable() {
				                                public void run() {
				                                        cooldownTime.put(p, cooldownTime.get(p) - 1);
				                                        if (cooldownTime.get(p) == 0) {
				                                                cooldownTime.remove(p);
				                                                cooldownTask.remove(p);
				                                                cancel();
				                                        }
				                                }
				                        });
				                       
				                        cooldownTask.get(p).runTaskTimer(this, 20, 20);
		                        } else {
		                        	p.sendMessage(getStrings.getError("You have to wait "+ ChatColor.AQUA + cooldownTime.get(p) + "s" + ChatColor.GRAY + " before you can make another report!"));
		                        }
							}
						} else {
							p.sendMessage(getStrings.getError("The player '" + args[0] + "' is not online!"));
						}
					}
				}
				if(cmd.getName().equalsIgnoreCase("freeze")) {
					if(p.hasPermission("delta.staff")) {
						if(args.length == 0) {
							p.sendMessage(getStrings.getUsage("/freeze <player>"));
						} else {
							if(args.length == 1) {
								Player online = Bukkit.getPlayerExact(args[0]);
								if(online == null) {
									p.sendMessage(getStrings.getError("The person you entered to freeze is not online!"));
								} else {
									Player target = Bukkit.getPlayer(args[0]);
									if(!isFrozen.contains(target.getUniqueId())) {
										isFrozen.add(target.getUniqueId());
										p.sendMessage(getStrings.getString("Freeze", "You successfully froze &o" + target.getName() + "&7!"));
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------------------------"));
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You have been &c&lFROZEN&7!"));
										target.sendMessage("");
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Remember, &c&lLOGOUT = BAN&7!"));
										target.sendMessage("");
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Please join &cts.deltapvp.com&7! You have &c5 MINUTES&7!"));
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Download TeamSpeak here: &3http://teamspeak.com"));
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------------------"));
										freezelocation.put(target.getUniqueId(), target.getLocation());
										
									} else {
										isFrozen.remove(target.getUniqueId());
										target.sendMessage(getStrings.getString("Freeze", "You were unfrozen!"));
										p.sendMessage(getStrings.getString("Freeze", "You successfully unfroze &o" + target.getName() + "&7!"));
										freezelocation.remove(target.getUniqueId());
									}
								}
							}
						}
					} else {
						p.sendMessage(getStrings.getNoPermission("Trainee"));
					}
				}
			} else {
				sender.sendMessage("This feature is for players only!");
			}
		 
		 return false;
	 }

}

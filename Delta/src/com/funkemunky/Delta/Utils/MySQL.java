package com.funkemunky.Delta.Utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
 
public class MySQL {
       
    private Connection connection;
 
    public MySQL(String ip, String userName, String password, String db) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + password);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
   
    public String getBannedReason(Player p) {
        try {
                PreparedStatement statement = connection.prepareStatement("select reason from users where uuid='" + p.getUniqueId() + "'");
                ResultSet result = statement.executeQuery();
               
                if (result.next()) {
                        return result.getString("reason");
                } else {
                        return null;
                }
        } catch (Exception e) {
                e.printStackTrace();
                return "[[Failed to connect]]";
        }
    }
    
    public void createUsers() {
    	try {
            if(tableExist("users") == false) {
            	String table = "CREATE TABLE users (uuid VARCHAR(64), reason VARCHAR(64), staff VARCHAR(16));";
				PreparedStatement statement = connection.prepareStatement(table);
				statement.executeUpdate();
				statement.close();
			}
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    public boolean tableExist(String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) { 
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }
    
    public String getStaff(Player p) {
    	try {
            PreparedStatement statement = connection.prepareStatement("select reason from users where uuid='" + p.getUniqueId() + "'");
            ResultSet result = statement.executeQuery();
           
            if (result.next()) {
                    return result.getString("staff");
            } else {
                    return "Unknown";
            }
    } catch (Exception e) {
            e.printStackTrace();
            return "[[Failed to connect]]";
    }
    }
    
    public boolean isPlayerBanned(Player p) {
    	try {
    		PreparedStatement statement = connection.prepareStatement("select reason from users where uuid='" + p.getUniqueId() + "'");
    		ResultSet result = statement.executeQuery();
    		if(result.next()) {
    			return true;
    		} else {
    			return false;
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
   
    public void banPlayer(String player, String reason, String staff) {
        try {
        	if(Bukkit.getPlayerExact(player) == null) {
        		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
        		PreparedStatement statement = connection.prepareStatement("insert into users (uuid, reason, staff)\nvalues ('" + p.getUniqueId() + "', '" + reason + "', '" + staff + "');");
                statement.executeUpdate();
                statement.close();
        	} else {
        		Player p = Bukkit.getPlayer(player);
        		PreparedStatement statement = connection.prepareStatement("insert into users (uuid, reason, staff)\nvalues ('" + p.getUniqueId() + "', '" + reason + "', '" + staff + "');");
                statement.executeUpdate();
                statement.close();
        	}
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public void unbanPlayer(String player) {
    	try {
    		if(Bukkit.getPlayerExact(player) == null) {
        		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
        		PreparedStatement statement = connection.prepareStatement("delete from users where uuid='" + p.getUniqueId() +"';");
                statement.executeUpdate();
                statement.close();
        	} else {
        		Player p = Bukkit.getPlayer(player);
        		PreparedStatement statement = connection.prepareStatement("delete from users where uuid='" + p.getUniqueId() +"';");
                statement.executeUpdate();
                statement.close();
        	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
}


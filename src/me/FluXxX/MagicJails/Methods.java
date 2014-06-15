package me.FluXxX.MagicJails;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Methods 
{
	private String prefix;
	private MagicJails plugin;
	
	public void jailPlayer(Player player, String jailName, Player jailed)
	{
		prefix = plugin.getPrefix();
		if (isJailed(jailed) == true)
		{
			player.sendMessage(prefix + ChatColor.RED + "Player is already jailed!");
		}
		else
		{
			final Location jail = new Location((World) plugin.jails.getConfigurationSection("Jails." + jailName + String.valueOf("World")), plugin.jails.getInt("Jails." + jailName + String.valueOf("X")), plugin.jails.getInt("Jails." + jailName + String.valueOf("Y")), plugin.jails.getInt("Jails." + jailName + String.valueOf("Z")));
			final Location playerLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
			jailed.teleport(jail);
			plugin.jailedPlayers.set("Players." + jailed, true);
			plugin.jailedPlayers.set("Players." + jailed + ".Jail", jailName);
			plugin.jailedPlayers.set("Players." + jailed + ".OldLoc", playerLoc);
			plugin.jailedPlayers.set("Players." + jailed + ".Inventory", player.getInventory().getContents());
			plugin.jailedPlayers.set("Players." + jailed + ".Helmet", player.getInventory().getHelmet());
			plugin.jailedPlayers.set("Players." + jailed + ".Chestplate", player.getInventory().getChestplate());
			plugin.jailedPlayers.set("Players." + jailed + ".Leggings", player.getInventory().getLeggings());
			plugin.jailedPlayers.set("Players." + jailed + ".Boots", player.getInventory().getBoots());
			jailed.getInventory().clear();
			plugin.saveYamls();
			jailed.sendMessage(ChatColor.RED + "You have been jailed by: " + player);
			player.sendMessage(prefix + ChatColor.GREEN + "You successfully jailed: " + jailed);
		}
	}
	
	public void unjailPlayer(Player player, Player jailed)
	{
		if (isJailed(jailed) == true)
		{
			final Location playerLoc = (Location) plugin.jailedPlayers.getConfigurationSection("Players." + jailed + ".Oldloc");
			jailed.teleport(playerLoc);
			jailed.getInventory().addItem(plugin.jailedPlayers.getItemStack("Players." + jailed + ".Inventory"));
			jailed.getInventory().setHelmet(plugin.jailedPlayers.getItemStack("Players." + jailed + ".Helmet"));
			jailed.getInventory().setChestplate(plugin.jailedPlayers.getItemStack("Players." + jailed + ".Chestplate"));
			jailed.getInventory().setLeggings(plugin.jailedPlayers.getItemStack("Players." + jailed + ".Leggings"));
			jailed.getInventory().setBoots(plugin.jailedPlayers.getItemStack("Players." + jailed + ".Boots"));
			plugin.jailedPlayers.set("Players." + jailed, null);
			plugin.jailedPlayers.set("Players." + jailed + ".Jail", null);
			plugin.jailedPlayers.set("Players." + jailed + ".OldLoc", null);
			plugin.jailedPlayers.set("Players." + jailed + ".Inventory", null);
			plugin.jailedPlayers.set("Players." + jailed + ".Helmet", null);
			plugin.jailedPlayers.set("Players." + jailed + ".Chestplate", null);
			plugin.jailedPlayers.set("Players." + jailed + ".Leggings", null);
			plugin.jailedPlayers.set("Players." + jailed + ".Boots", null);
			plugin.saveYamls();
			jailed.sendMessage(ChatColor.GREEN + "You have been unjailed!");
			player.sendMessage(prefix + ChatColor.GREEN + "You unjailed:" + jailed);
		}
	}
	
	public boolean isJailed(Player player)
	{
		if (plugin.jailedPlayers.isConfigurationSection("Players." + player))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public void createJail(String name, double d, double e, double f, World jailWorld, Player player)
	{
		if (plugin.jails.isConfigurationSection("Jails." + name))
		{
			player.sendMessage(prefix + ChatColor.RED + "Jail already exists by that name!");
		}
		else
		{
			plugin.jails.createSection("Jails." + name);
			plugin.jails.set("Jails." + name + ".World", String.valueOf(jailWorld));
			plugin.jails.set("Jails." + name + ".X", String.valueOf(d));
			plugin.jails.set("Jails." + name + ".Y", String.valueOf(e));
			plugin.jails.set("Jails." + name + ".Z", String.valueOf(f));
			player.sendMessage(prefix + ChatColor.GREEN + "Successfully created jail: " + name + "!");
		}
	}
	
	public void deleteJail(String name, Player player)
	{
		if (plugin.jails.isConfigurationSection("Jails." + name))
		{
			player.sendMessage(prefix + ChatColor.GREEN + "Successfully deleted jail: " + name + "!");
			plugin.jails.set("Jails." + name, null);
			plugin.jails.set("Jails." + name + ".World", null);
			plugin.jails.set("Jails." + name + ".X", null);
			plugin.jails.set("Jails." + name + ".Y", null);
			plugin.jails.set("Jails." + name + ".Z", null);
			
		}
		else
		{
			player.sendMessage(prefix + ChatColor.RED + "Jail does not exist by that name!");
		}
	}
	
	@SuppressWarnings("null")
	public String[] listJails()
	{
		String[] listJails = null;
		int i = 0;
		for(String key: plugin.jails.getConfigurationSection("Jails").getKeys(false))
		{
			listJails[i] = key;
			i++;
		}
		
		return listJails;
	}
	
	public void onPlayerRespawn(PlayerRespawnEvent e)
	{
		Player player = e.getPlayer();
		if(isJailed(player) == true)
		{
			String jailName = String.valueOf(plugin.jailedPlayers.getConfigurationSection("Players." + player + ".Jail"));
			final Location jail = new Location((World) plugin.jails.getConfigurationSection("Jails." + jailName + String.valueOf("World")), plugin.jails.getInt("Jails." + jailName + String.valueOf("X")), plugin.jails.getInt("Jails." + jailName + String.valueOf("Y")), plugin.jails.getInt("Jails." + jailName + String.valueOf("Z")));
			player.teleport(jail);
		}
	}

}

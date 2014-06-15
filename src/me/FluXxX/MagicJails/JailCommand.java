package me.FluXxX.MagicJails;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.FluXxX.MagicJails.Methods;

public class JailCommand implements CommandExecutor 
{
	private String noPerm;
	private String prefix;
	Methods method = new Methods();
	public JailCommand(MagicJails plugin)
	{
		plugin = new MagicJails().getInstance();
		prefix = plugin.getPrefix();
		noPerm = plugin.getNoPerm();			
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player player = (Player) cs;
			// /MagicJails
			if (label.equalsIgnoreCase("MagicJails"))
			{
				if(player.hasPermission("MagicJails.use"))
				{
					if(args.length == 0)
					{
						player.sendMessage(prefix + ChatColor.AQUA + "MAGICJAILS BITCH! Lock them bitches up tight!");
					}
					if (args.length == 1)
					{
						// List
						if (args[0].equalsIgnoreCase("List"))
						{
							player.sendMessage(prefix + ChatColor.GOLD + "Jail list: " + ChatColor.AQUA + method.listJails());
						}
					}
					if(args.length == 2)
					{
						// Create <Jail>
						if(args[0].equalsIgnoreCase("Create"))
						{
							if(args[1].isEmpty())
							{
								player.sendMessage(prefix + ChatColor.RED + "You need to name the jail you're creating!");
							}
							else
							{
								method.createJail(args[1], player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getWorld(), player);
							}
						}
						// Delete <Jail>
						if(args[0].equalsIgnoreCase("Delete"))
						{
							if(args[1].isEmpty())
							{
								player.sendMessage(prefix + ChatColor.RED + "You need to select a jail to delete!");
							}
							else
							{
								method.deleteJail(args[1], player);
							}
						
						}
					}
					if(args.length == 3)
					{
						// Jail <Player>
						if(args[0].equalsIgnoreCase("Jail"))
						{
							if(args[1].isEmpty())
							{
								player.sendMessage(prefix + ChatColor.RED + "You need to choose a player to jail");
							}
							else
							{
								if (args[1].equalsIgnoreCase(String.valueOf(Bukkit.getServer().getPlayer(args[1]))))
								{
									Player jailor = player;
									Player jailee = Bukkit.getServer().getPlayer(args[1]);
									method.jailPlayer(jailor, args[2], jailee);
								}
								else
								{
									player.sendMessage(prefix + ChatColor.RED + "The player could not be found!");
								}
							}
						}
						// Unjail <Player>
						if(args[0].equalsIgnoreCase("Unjail"))
						{
							if(args[1].isEmpty())
							{
								player.sendMessage(prefix + ChatColor.RED + "You need to choose a player to unjail");
							}
							else
							{
								if (args[1].equalsIgnoreCase(String.valueOf(Bukkit.getServer().getPlayer(args[1]))))
								{
									Player jailor = player;
									Player jailee = Bukkit.getServer().getPlayer(args[1]);
									method.unjailPlayer(jailor, jailee);
								}
								else
								{
									player.sendMessage(prefix + ChatColor.RED + "The player could not be found!");
								}
							}
						}
					}
				}
				else
				{
					player.sendMessage(noPerm);
				}
			}
		}
		return false;
	}

}

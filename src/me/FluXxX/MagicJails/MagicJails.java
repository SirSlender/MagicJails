package me.FluXxX.MagicJails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import me.FluXxX.MagicJails.JailCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicJails extends JavaPlugin implements Listener 
{
	private String noPerm;
	private String prefix;
	Logger log = Bukkit.getLogger();
	File configFile;
	File jailsFile;
	File jplayersFile;
	FileConfiguration config;
	FileConfiguration jails;
	FileConfiguration jailedPlayers;
	
	@Override
	public void onEnable()
	{
		try {
			firstRun();
    	    configFile = new File(getDataFolder(), "config.yml");
    	    jailsFile =  new File(getDataFolder(), "jails.yml");
    	    jplayersFile = new File(getDataFolder(), "jailedplayers.yml");
			prefix = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("Prefix"));
			noPerm = prefix + ChatColor.RED + "You don't have permission to perform this action!";
			this.saveDefaultConfig();
			this.getCommand("").setExecutor(new JailCommand(this)); //Set the cmd in the quotes
			loadYamls();
			log.info(prefix + "MagicJails successfully enabled!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.severe(prefix + "MagicJails could not be enabled!");
			e.printStackTrace();
			this.setEnabled(false);
		}
		
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public MagicJails getInstance()
	{
		return this;
	}
	public String getPrefix()
	{
		return prefix;
	}
	public String getNoPerm()
	{
		return noPerm;
	}
	
    public void saveYamls() {
        try {
            config.save(configFile);
            jails.save(jailsFile);
            jailedPlayers.save(jplayersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadYamls() {
        try {
            config.load(configFile);
            jails.load(jailsFile);
            jailedPlayers.load(jplayersFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
   private void firstRun() throws Exception {
        if(!configFile.exists()){
            configFile.getParentFile().mkdirs();
            copy(getResource("config.yml"), configFile);
        }
        if(!jailsFile.exists())
        {
        	jailsFile.getParentFile().mkdirs();
        	copy(getResource("jails.yml"), jailsFile);
        }
        if(!jplayersFile.exists())
        {
        	jailsFile.getParentFile().mkdirs();
        	copy(getResource("jailedplayers.yml"), jplayersFile);
        }
        
	}
   
   private void copy(InputStream in, File file) {
       try {
           OutputStream out = new FileOutputStream(file);
           byte[] buf = new byte[1024];
           int len;
           while((len=in.read(buf))>0){
               out.write(buf,0,len);
           }
           out.close();
           in.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
	
}

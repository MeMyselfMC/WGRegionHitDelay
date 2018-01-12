package io.memyself.wgrhd;

import java.io.File;

import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

import io.memyself.wgrhd.bstats.MetricsLite;

public class WGRHD
  extends JavaPlugin {
	
	@Override
	public void onEnable() {
		if(!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
			
			reloadConfig();
		}
		new Utilities(this);
		
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		
		getCommand("wgrhd").setExecutor(new CommandManager(this));
		
		if(getConfig().getBoolean("options.metrics")) {
			if(getConfig().getBoolean("options.debug")) getLogger().info("[DEBUG] Will be attempting to submit statistics to bStats.org.");
			
			new MetricsLite(this);
		}
		
		getLogger().info("WGRegionHitDelay v" + getDescription().getVersion() + " has been enabled!");
	}
	
	@Override
	public void onDisable() {
		if(getConfig().getBoolean("options.reset-on-unload")) {
			for(Player player : getServer().getOnlinePlayers()) {
				if(player.getMaximumNoDamageTicks() != 20) player.setMaximumNoDamageTicks(20);
			}
			
			if(getConfig().getBoolean("options.debug")) getLogger().info("[DEBUG] Everyone's hit delay has been reset to the vanilla default.");
		}
		
		getLogger().info("WGRegionHitDelay v" + getDescription().getVersion() + " has been disabled.");
	}
	
}
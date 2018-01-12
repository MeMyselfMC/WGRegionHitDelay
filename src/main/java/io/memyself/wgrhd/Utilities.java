package io.memyself.wgrhd;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.ChatColor;

import org.bukkit.entity.Player;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.Vector;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import static com.sk89q.worldguard.bukkit.BukkitUtil.toVector;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Utilities {
	
	private static WGRHD wgrhd;
	
	public Utilities(WGRHD instance) {
		wgrhd = instance;
	}
	
	public static void notify(Player player) {
		if(wgrhd.getConfig().getString("locale.hit-delay-change-notification") != null && !wgrhd.getConfig().getString("locale.hit-delay-change-notification").isEmpty()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', wgrhd.getConfig().getString("locale.hit-delay-change-notification").replaceAll("%hit_delay%", String.valueOf(player.getMaximumNoDamageTicks()))));
		}
		
		if(wgrhd.getConfig().getString("options.notifications.sound") != null && !wgrhd.getConfig().getString("options.notifications.sound").isEmpty()) {
			try {
				player.playSound(player.getLocation(), Sound.valueOf(wgrhd.getConfig().getString("options.notifications.sound")), 1, 1.0F);
			} catch(IllegalArgumentException ex) {
				wgrhd.getLogger().warning("Invalid notification sound value given in config.yml!");
			}
		}
	}
	
	private static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
		
		if(plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			wgrhd.getLogger().warning("WorldGuard is not loaded or has been disabled! WGRegionHitDelay will not function until WorldGuard is enabled.");
			
			return null;
		} else {
			return (WorldGuardPlugin) plugin;
		}
	}
	
	private static ApplicableRegionSet getApplicableRegions(Location loc) {
		if(getWorldGuard() != null) {
			RegionManager manager = getWorldGuard().getRegionManager(loc.getWorld());
			
			Vector v = toVector(loc);
			ApplicableRegionSet regions = manager.getApplicableRegions(v);
			
			return regions;
		} else {
			return null;
		}
	}
	
	public static ProtectedRegion getHighestPriorityRegion(Location loc) {
		ApplicableRegionSet regions = getApplicableRegions(loc);
		
		if(regions != null) {
			if(regions.size() > 0) {
				return regions.getRegions().iterator().next();
			} else if(getWorldGuard().getRegionManager(loc.getWorld()).hasRegion("__global__")) {
				return getWorldGuard().getRegionManager(loc.getWorld()).getRegion("__global__");
			} else return null;
		} else return null;
	}
	
}
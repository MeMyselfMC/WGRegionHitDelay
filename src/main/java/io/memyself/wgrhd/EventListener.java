package io.memyself.wgrhd;

import org.bukkit.Location;

import org.bukkit.entity.Player;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class EventListener
  implements Listener {
	
	private WGRHD wgrhd;
	
	public EventListener(WGRHD instance) {
		wgrhd = instance;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		ProtectedRegion spawnRegion = Utilities.getHighestPriorityRegion(player.getLocation());
		
		if(spawnRegion != null) {
			if(wgrhd.getConfig().isSet("regions." + player.getWorld().getName() + "." + spawnRegion.getId())) {
				String updatedHitDelay = wgrhd.getConfig().getString("regions." + player.getWorld().getName() + "." + spawnRegion.getId());
				
				if(updatedHitDelay.matches("^[0-9]+$")) {
					if(player.getMaximumNoDamageTicks() != Integer.valueOf(updatedHitDelay)) {
						player.setMaximumNoDamageTicks(Integer.valueOf(updatedHitDelay));
						
						if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && player.hasPermission("wgrhd.notify")) Utilities.notify(player);
					}
				} else wgrhd.getLogger().warning("Invalid hit delay value given for the " + spawnRegion.getId() + " region (" + player.getWorld().getName() + ") in config.yml!");
			} else {
				if(event.getPlayer().getMaximumNoDamageTicks() != 20) {
					player.setMaximumNoDamageTicks(20);
					
					if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && event.getPlayer().hasPermission("wgrhd.notify")) Utilities.notify(event.getPlayer());
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		Location locationFrom = event.getFrom().getBlock().getLocation();
		Location locationTo = event.getTo().getBlock().getLocation();
		
		if(!locationTo.equals(locationFrom)) {
			ProtectedRegion regionFrom = Utilities.getHighestPriorityRegion(locationFrom);
			ProtectedRegion regionTo = Utilities.getHighestPriorityRegion(locationTo);
			
			if(regionTo != null) {
				if(regionFrom != null) {
					if(!regionTo.getId().equals(regionFrom.getId())) {
						Player player = event.getPlayer();
						
						if(wgrhd.getConfig().isSet("regions." + locationTo.getWorld().getName() + "." + regionTo.getId())) {
							String regionToHitDelay = wgrhd.getConfig().getString("regions." + locationTo.getWorld().getName() + "." + regionTo.getId());
							
							if(regionToHitDelay.matches("^[0-9]+$")) {
								if(player.getMaximumNoDamageTicks() != Integer.valueOf(regionToHitDelay)) {
									player.setMaximumNoDamageTicks(Integer.valueOf(regionToHitDelay));
									
									if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && player.hasPermission("wgrhd.notify")) Utilities.notify(player);
								}
							} else wgrhd.getLogger().warning("Invalid hit delay value given for the " + regionTo.getId() + " region (" + locationTo.getWorld().getName() + ") in config.yml!");
						} else {
							if(player.getMaximumNoDamageTicks() != 20) {
								player.setMaximumNoDamageTicks(20);
								
								if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && player.hasPermission("wgrhd.notify")) Utilities.notify(player);
							}
						}
					}
				} else {
					Player player = event.getPlayer();
					
					if(wgrhd.getConfig().isSet("regions." + locationTo.getWorld().getName() + "." + regionTo.getId())) {
						String regionToHitDelay = wgrhd.getConfig().getString("regions." + locationTo.getWorld().getName() + "." + regionTo.getId());
						
						if(regionToHitDelay.matches("^[0-9]+$")) {
							if(player.getMaximumNoDamageTicks() != Integer.valueOf(regionToHitDelay)) {
								player.setMaximumNoDamageTicks(Integer.valueOf(regionToHitDelay));
								
								if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && player.hasPermission("wgrhd.notify")) Utilities.notify(player);
							}
						} else wgrhd.getLogger().warning("Invalid hit delay value given for the " + regionTo.getId() + " region (" + locationTo.getWorld().getName() + ") in config.yml!");
					}
				}
			} else {
				if(event.getPlayer().getMaximumNoDamageTicks() != 20) {
					event.getPlayer().setMaximumNoDamageTicks(20);
					
					if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && event.getPlayer().hasPermission("wgrhd.notify")) Utilities.notify(event.getPlayer());
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Location locationFrom = event.getFrom().getBlock().getLocation();
		Location locationTo = event.getTo().getBlock().getLocation();
		
		if(!locationTo.equals(locationFrom)) {
			ProtectedRegion regionFrom = Utilities.getHighestPriorityRegion(locationFrom);
			ProtectedRegion regionTo = Utilities.getHighestPriorityRegion(locationTo);
			
			if(regionTo != null) {
				if(regionFrom != null) {
					if(!regionTo.getId().equals(regionFrom.getId()) || !locationTo.getWorld().getName().equals(locationFrom.getWorld().getName())) {
						Player player = event.getPlayer();
						
						if(wgrhd.getConfig().isSet("regions." + locationTo.getWorld().getName() + "." + regionTo.getId())) {
							String regionToHitDelay = wgrhd.getConfig().getString("regions." + locationTo.getWorld().getName() + "." + regionTo.getId());
							
							if(regionToHitDelay.matches("^[0-9]+$")) {
								if(player.getMaximumNoDamageTicks() != Integer.valueOf(regionToHitDelay)) {
									player.setMaximumNoDamageTicks(Integer.valueOf(regionToHitDelay));
									
									if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && player.hasPermission("wgrhd.notify")) Utilities.notify(player);
								}
							} else wgrhd.getLogger().warning("Invalid hit delay value given for the " + regionTo.getId() + " region (" + locationTo.getWorld().getName() + ") in config.yml!");
						} else {
							if(player.getMaximumNoDamageTicks() != 20) {
								player.setMaximumNoDamageTicks(20);
								
								if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && player.hasPermission("wgrhd.notify")) Utilities.notify(player);
							}
						}
					}
				} else {
					Player player = event.getPlayer();
					
					if(wgrhd.getConfig().isSet("regions." + locationTo.getWorld().getName() + "." + regionTo.getId())) {
						String regionToHitDelay = wgrhd.getConfig().getString("regions." + locationTo.getWorld().getName() + "." + regionTo.getId());
						
						if(regionToHitDelay.matches("^[0-9]+$")) {
							if(player.getMaximumNoDamageTicks() != Integer.valueOf(regionToHitDelay)) {
								player.setMaximumNoDamageTicks(Integer.valueOf(regionToHitDelay));
								
								if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && player.hasPermission("wgrhd.notify")) Utilities.notify(player);
							}
						} else wgrhd.getLogger().warning("Invalid hit delay value given for the " + regionTo.getId() + " region (" + locationTo.getWorld().getName() + ") in config.yml!");
					}
				}
			} else {
				if(event.getPlayer().getMaximumNoDamageTicks() != 20) {
					event.getPlayer().setMaximumNoDamageTicks(20);
					
					if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && event.getPlayer().hasPermission("wgrhd.notify")) Utilities.notify(event.getPlayer());
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		ProtectedRegion respawnRegion = Utilities.getHighestPriorityRegion(event.getRespawnLocation());
		
		if(respawnRegion != null) {
			if(wgrhd.getConfig().isSet("regions." + event.getRespawnLocation().getWorld().getName() + "." + respawnRegion.getId())) {
				String updatedHitDelay = wgrhd.getConfig().getString("regions." + event.getRespawnLocation().getWorld().getName() + "." + respawnRegion.getId());
				
				if(updatedHitDelay.matches("^[0-9]+$")) {
					if(player.getMaximumNoDamageTicks() != Integer.valueOf(updatedHitDelay)) {
						player.setMaximumNoDamageTicks(Integer.valueOf(updatedHitDelay));
						
						if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && player.hasPermission("wgrhd.notify")) Utilities.notify(player);
					}
				} else wgrhd.getLogger().warning("Invalid hit delay value given for the " + respawnRegion.getId() + " region (" + event.getRespawnLocation().getWorld().getName() + ") in config.yml!");
			} else {
				if(player.getMaximumNoDamageTicks() != 20) {
					player.setMaximumNoDamageTicks(20);
					
					if(wgrhd.getConfig().getBoolean("options.notifications.enabled") && player.hasPermission("wgrhd.notify")) Utilities.notify(player);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKick(PlayerKickEvent event) {
		if(event.getPlayer().getMaximumNoDamageTicks() != 20) event.getPlayer().setMaximumNoDamageTicks(20);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(event.getPlayer().getMaximumNoDamageTicks() != 20) event.getPlayer().setMaximumNoDamageTicks(20);
	}
	
}
package io.memyself.wgrhd;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

public class CommandManager
  implements CommandExecutor {
	
	private WGRHD wgrhd;
	
	public CommandManager(WGRHD instance) {
		wgrhd = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("wgregionhitdelay")) {
			if(args.length < 2) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("reload")) {
						if(sender.hasPermission("wgrhd.reload")) {
							if(!new File(wgrhd.getDataFolder(), "config.yml").exists()) {
								wgrhd.saveDefaultConfig();
							}
							wgrhd.reloadConfig();
							
							for(Player player : Bukkit.getOnlinePlayers()) {
								if(Utilities.getHighestPriorityRegion(player.getLocation()) != null) {
									if(wgrhd.getConfig().isSet("regions." + player.getWorld().getName() + "." + Utilities.getHighestPriorityRegion(player.getLocation()).getId())) {
										String updatedHitDelay = wgrhd.getConfig().getString("regions." + player.getWorld().getName() + "." + Utilities.getHighestPriorityRegion(player.getLocation()).getId());
										
										if(updatedHitDelay.matches("^[0-9]+$")) {
											if(player.getMaximumNoDamageTicks() != Integer.valueOf(updatedHitDelay)) {
												player.setMaximumNoDamageTicks(Integer.valueOf(updatedHitDelay));
											}
										}
									} else {
										if(player.getMaximumNoDamageTicks() != 20) {
											player.setMaximumNoDamageTicks(20);
										}
									}
								}
							}
							
							if(wgrhd.getConfig().getBoolean("options.debug")) wgrhd.getLogger().info("[DEBUG] Configuration reloaded!");
							
							if(wgrhd.getConfig().getString("locale.config-reloaded") != null && !wgrhd.getConfig().getString("locale.config-reloaded").isEmpty()) {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', wgrhd.getConfig().getString("locale.config-reloaded")));
							}
						} else {
							if(wgrhd.getConfig().getString("locale.error.no-permission") != null && !wgrhd.getConfig().getString("locale.error.no-permission").isEmpty()) {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', wgrhd.getConfig().getString("locale.error.no-permission")));
							}
						}
					} else {
						if(wgrhd.getConfig().getString("locale.error.invalid-arguments") != null && !wgrhd.getConfig().getString("locale.error.invalid-arguments").isEmpty()) {
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', wgrhd.getConfig().getString("locale.error.invalid-arguments")));
						}
					}
				} else {
					if(wgrhd.getConfig().getString("locale.command-usage") != null && !wgrhd.getConfig().getString("locale.command-usage").isEmpty()) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', wgrhd.getConfig().getString("locale.command-usage")));
					}
				}
			} else {
				if(wgrhd.getConfig().getString("locale.error.invalid-arguments") != null && !wgrhd.getConfig().getString("locale.error.invalid-arguments").isEmpty()) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', wgrhd.getConfig().getString("locale.error.invalid-arguments")));
				}
			}
		}
		return true;
	}
	
}
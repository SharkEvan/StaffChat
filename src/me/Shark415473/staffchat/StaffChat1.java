package me.Shark415473.staffchat;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
		
	public class StaffChat1 extends JavaPlugin implements Listener {
	
		 private String prefix = getConfig().getString("Prefix");
		 
		 private ArrayList<String> staff = new ArrayList<String>();
		 
		 @Override
		 public void onEnable(){
			 Bukkit.getLogger().info("StaffChat has been enabled!");
			 Bukkit.getServer().getPluginManager().registerEvents(this, this);
				getConfig().options().copyDefaults(true);
				saveConfig();
		 }
		 
		 public void onDisable(){
			 Bukkit.getLogger().info("StaffChat has been disabled!") ;
		 }
		 	@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				String noPerm = getConfig().getString("No Permission");
				
				if(label.equalsIgnoreCase("sc") || label.equalsIgnoreCase("staffchat")) {
				if(!(sender.hasPermission("evan.staffchat"))){
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
					return true;
				}
				Player p = (Player) sender;
				if(!staff.contains(p.getName())){
					staff.add(p.getName());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&aSTAFFCHAT&6] -> §7StaffChat toggled &aON!"));
					return true;
				}else if (staff.contains(p.getName())){
					staff.remove(p.getName());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&aSTAFFCHAT&6] -> §7StaffChat is &cOFF!"));
					return true;
				}
				}
				
				return true;
			}
		 
		 
		 @EventHandler
		 public void onChat(AsyncPlayerChatEvent e){
			 Player p = e.getPlayer();
			 if(staff.contains(p.getName())){
				 e.setCancelled(true);
				 for(Player pl : Bukkit.getOnlinePlayers()){
					 if(pl.hasPermission("evan.staffchat.recieve")){
						 String format = getConfig().getString("ChatFormat");
						 format = format.replaceAll("%player%", p.getName());
						 format = format.replaceAll("%displayname%", p.getDisplayName());
						 format = format.replaceAll("%prefix%", prefix);
						 format = format.replaceAll("%message%", e.getMessage());
						 pl.sendMessage(ChatColor.translateAlternateColorCodes('&', format));
					 }
				 }
			 }	 
		 }
			 
		 }


package com.craftilandia.unsafeenchantedtable;


import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin implements Listener {
static HashMap<String, Block> enchanted = new HashMap<String, Block>();
@Override
public void onEnable() {
getServer().getPluginManager().registerEvents(this, this);
saveDefaultConfig();
super.onEnable();}
	
@EventHandler
public void nouse(PlayerInteractEvent e){
	if(e.getClickedBlock() != null) {
		if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
			Material b = e.getClickedBlock().getType();
			if ((b == Material.ENCHANTMENT_TABLE)){
				enchanted.put("encanto",e.getClickedBlock().getRelative(BlockFace.SELF));}}}}
	@EventHandler
	public void encanto(InventoryClickEvent e){
		if(e.getRawSlot() == 0&&e.getInventory().getName().equals("Enchant")){
			Player p = (Player)e.getWhoClicked();
			Block block = enchanted.get("encanto");
			int b=0;
	        for(int i=0;i<3;i++){
	        	if(block.getRelative(-2,i,-2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(-1,i,-2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(0,i,-2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(+1,i,-2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(+2,i,-2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(+2,i,-1).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(+2,i,0).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(+2,i,+1).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(+2,i,+2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(+1,i,+2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(0,i,+2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(-1,i,+2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(-2,i,+2).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(-2,i,+1).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(-2,i,0).getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block.getRelative(-2,i,-1).getType().equals(Material.BOOKSHELF)){b++;}
	        }
	        if(b<15){return;}
	        if(p.getExpToLevel() <b){
				p.sendMessage("§a[Unsafe Enchanted Table] §4Nesesitas minimo "+b+" Lv de exp.");//quitar msg luego
				return;
			}
if( e.getCurrentItem().getType().name().equals("DIAMOND_SWORD")){		
			ItemStack espada = new ItemStack(Material.DIAMOND_SWORD);
			espada.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, b);
			p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_BLAST, 2, 1);
			e.getView().setItem(0, espada);
			int teniaexp=p.getLevel();
		    p.setTotalExperience(0);
		    p.setExp(0);
		    p.setLevel(teniaexp-b);
		    p.sendMessage("§a[Unsafe Enchanted Table] §bSword enchanted with lots of damage.");
		    return;
}
}}}

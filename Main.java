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
super.onEnable();
	}
	
@EventHandler
public void nouse(PlayerInteractEvent e){
	if(e.getClickedBlock() != null) {
		if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
			Material b = e.getClickedBlock().getType();
			if ((b == Material.ENCHANTMENT_TABLE)){
				e.getPlayer().sendMessage("work at here");
				enchanted.put("encanto",e.getClickedBlock().getRelative(BlockFace.SELF));
			}
		}
	}
}
	@EventHandler
	public void encanto(InventoryClickEvent e){
		if(e.getRawSlot() == 0&&e.getInventory().getName().equals("Enchant")){
			Player p = (Player)e.getWhoClicked();
			
			//Block block = Bukkit.getWorld("world").getBlockAt(2, 1, 2);
			Block block = enchanted.get("encanto");
			p.sendMessage(""+enchanted.toString());
			int b=0;
	        for(int i=0;i<3;i++){
	        	Block block01 = block.getRelative(-2,i,-2);
	        	Block block02 = block.getRelative(-1,i,-2);
	        	Block block03 = block.getRelative(0,i,-2);
	        	Block block04 = block.getRelative(+1,i,-2);
	        	Block block05 = block.getRelative(+2,i,-2);
	        	Block block06 = block.getRelative(+2,i,-1);
	        	Block block07 = block.getRelative(+2,i,0);
	        	Block block08 = block.getRelative(+2,i,+1);
	        	Block block09 = block.getRelative(+2,i,+2);
	        	Block block10 = block.getRelative(+1,i,+2);
	        	Block block11 = block.getRelative(0,i,+2);
	        	Block block12 = block.getRelative(-1,i,+2);
	        	Block block13 = block.getRelative(-2,i,+2);
	        	Block block14 = block.getRelative(-2,i,+1);
	        	Block block15 = block.getRelative(-2,i,0);
	        	Block block16 = block.getRelative(-2,i,-1);

	        	if(block01.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block02.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block03.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block04.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block05.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block06.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block07.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block08.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block09.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block10.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block11.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block12.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block13.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block14.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block15.getType().equals(Material.BOOKSHELF)){b++;}
	        	if(block16.getType().equals(Material.BOOKSHELF)){b++;}
	        }
			if( e.getCurrentItem().getType().name().equals("DIAMOND_SWORD")){
				p.sendMessage(e.getCurrentItem().getEnchantments().toString());
			if(p.getExpToLevel() <b){
				p.sendMessage("§a[Unsafe Enchanted Table] §4Nesesitas minimo "+b+" niveles.");//quitar msg luego
				return;
			}
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
	}}}


}

package com.craftilandia.unsafeenchantedtable;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CarrosListener implements Listener {
	static HashMap<String, Integer> duracion = new HashMap<String, Integer>();


	@EventHandler
	public void enreload(PluginEnableEvent e) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			duracion.put(p.getName(),0 + Bukkit.getPluginManager().getPlugin("Ender_Enchanted_Table").getConfig().getInt("players." + p.getName()));
		}
	}

	@EventHandler
	public void entrando(PlayerJoinEvent e) {
		duracion.put(e.getPlayer().getName(),0 + Bukkit.getPluginManager().getPlugin("Ender_Enchanted_Table").getConfig().getInt("players." + e.getPlayer().getName()));
	}

	@EventHandler
	public void saliendo(PlayerQuitEvent e) {
		Bukkit.getPluginManager()
				.getPlugin("Ender_Enchanted_Table")
				.getConfig()
				.set("players." + e.getPlayer().getName(),
						duracion.get(e.getPlayer().getName()));
		Bukkit.getPluginManager().getPlugin("Ender_Enchanted_Table").saveConfig();
	}

	@EventHandler
	public void nombrando(EntityTameEvent e) {
		if (e.getEntity() instanceof Horse) {
			Horse caballo = (Horse) e.getEntity();
			if (caballo.getVariant().name().equals("HORSE")) {
				caballo.setCustomName("§aCaballo de " + e.getOwner().getName());
			}
			if (caballo.getVariant().name().equals("MULE")) {
				caballo.setCustomName("§aMula de " + e.getOwner().getName());
			}
			if (caballo.getVariant().name().equals("DONKEY")) {
				caballo.setCustomName("§aBurro de " + e.getOwner().getName());
			}
			caballo.setCustomNameVisible(true);// el pig podria ser ... xD
		}
	}

	@EventHandler
	public void moviendo(PlayerMoveEvent e) {
		if (e.getPlayer().isInsideVehicle()
				&& e.getTo().getChunk() != e.getFrom().getChunk()
				&& e.getPlayer().getVehicle().getType()
						.equals(EntityType.HORSE)) {
			Horse caballo = (Horse) e.getPlayer().getVehicle();
			if (caballo.getInventory() != null) {
				if (caballo.getInventory().getArmor() != null) {
					duracion.put(e.getPlayer().getName(), duracion.get(e.getPlayer().getName()) + 1);
					e.getPlayer().sendMessage(""+duracion.get(e.getPlayer().getName()));
					if (caballo.getInventory().contains(Material.DIAMOND_BARDING) && duracion.get(e.getPlayer().getName()) >= 
							Bukkit.getPluginManager().getPlugin("Ender_Enchanted_Table").getConfig().getInt("duradiamante")	
									&& !caballo.getInventory().getArmor().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
						caballo.getInventory().setArmor(null);
						e.getPlayer()
								.sendMessage(
										"§a[Ender Enchanted Table] §4Your horse armor has been break.");
						e.getPlayer()
								.getWorld()
								.playSound(e.getPlayer().getLocation(),
										Sound.EXPLODE, 2, 1);
						duracion.put(e.getPlayer().getName(), 0);
						//duracion.replace(e.getPlayer().getName(), 1);
						caballo.removePotionEffect(PotionEffectType.SPEED);
						return;
					}
					if (caballo.getInventory().contains(Material.IRON_BARDING)
							&& duracion.get(e.getPlayer().getName()) >= Bukkit
									.getPluginManager().getPlugin("Ender_Enchanted_Table")
									.getConfig().getInt("durametal")
							&& !caballo.getInventory().getArmor().getItemMeta()
									.hasEnchant(Enchantment.DURABILITY)) {

						caballo.getInventory().setArmor(null);
						e.getPlayer()
								.sendMessage(
										"§a[Ender Enchanted Table] §4Your horse armor has been break.");
						e.getPlayer()
								.getWorld()
								.playSound(e.getPlayer().getLocation(),
										Sound.EXPLODE, 2, 1);
						duracion.put(e.getPlayer().getName(), 0);
						caballo.removePotionEffect(PotionEffectType.SPEED);
						return;
					}
					if (caballo.getInventory().contains(Material.GOLD_BARDING)
							&& duracion.get(e.getPlayer().getName()) >= Bukkit
									.getPluginManager().getPlugin("Ender_Enchanted_Table")
									.getConfig().getInt("duraoro")
							&& !caballo.getInventory().getArmor().getItemMeta()
									.hasEnchant(Enchantment.DURABILITY)) {
						caballo.getInventory().setArmor(null);
						e.getPlayer()
								.sendMessage(
										"§a[Ender Enchanted Table] §4Your horse armor has been break.");
						e.getPlayer()
								.getWorld()
								.playSound(e.getPlayer().getLocation(),
										Sound.EXPLODE, 2, 1);
						duracion.put(e.getPlayer().getName(), 0);
						caballo.removePotionEffect(PotionEffectType.SPEED);
						return;
					}

					// EL ENCANTO DE LA ARMADURA PODRIA SER LA GASOLINA DE CARRO
					// QUE SE ACABA SE QUITA EL ENCANTO Y LA ARMADURA SIN
					// ENCANTO
					// TRUENA MUY PRONTO Xd
					if (caballo.getInventory().getArmor().getItemMeta() != null) {
						if (caballo.getInventory().contains(Material.IRON_BARDING) && duracion.get(e.getPlayer().getName()) >= Bukkit
								.getPluginManager().getPlugin("Ender_Enchanted_Table")
								.getConfig().getInt("durametal")
								* caballo
										.getInventory()
										.getArmor()
										.getItemMeta()
										.getEnchantLevel(Enchantment.DURABILITY)
								&& caballo.getInventory().getArmor()
										.getItemMeta()
										.hasEnchant(Enchantment.DURABILITY)) {
							
								caballo.getInventory()
										.setArmor(
												new ItemStack(
														Material.IRON_BARDING));
							
							e.getPlayer()
									.sendMessage(
											"§a[Ender Enchanted Table] §4Your armor is about to be break, reenchant it to use it more time.");
							e.getPlayer()
									.getWorld()
									.playSound(e.getPlayer().getLocation(),
											Sound.EAT, 1, 1);
							duracion.put(e.getPlayer().getName(), 0);
							caballo.removePotionEffect(PotionEffectType.SPEED);
							return;
						}
						
						if (caballo.getInventory().contains(Material.GOLD_BARDING) && duracion.get(e.getPlayer().getName()) >= Bukkit
								.getPluginManager().getPlugin("Ender_Enchanted_Table")
								.getConfig().getInt("duraoro")
								* caballo
										.getInventory()
										.getArmor()
										.getItemMeta()
										.getEnchantLevel(Enchantment.DURABILITY)
								&& caballo.getInventory().getArmor()
										.getItemMeta()
										.hasEnchant(Enchantment.DURABILITY)) {
							
								caballo.getInventory()
										.setArmor(
												new ItemStack(
														Material.GOLD_BARDING));
							
							e.getPlayer()
									.sendMessage(
											"§a[Ender Enchanted Table] §4Your armor is about to be break, reenchant it to use it more time.");
							e.getPlayer()
									.getWorld()
									.playSound(e.getPlayer().getLocation(),
											Sound.EAT, 1, 1);
							duracion.put(e.getPlayer().getName(), 0);
							caballo.removePotionEffect(PotionEffectType.SPEED);
							return;
						}
						
						if (caballo.getInventory().contains(Material.DIAMOND_BARDING) && duracion.get(e.getPlayer().getName()) >= Bukkit
								.getPluginManager().getPlugin("Ender_Enchanted_Table")
								.getConfig().getInt("duradiamante")
								* caballo
										.getInventory()
										.getArmor()
										.getItemMeta()
										.getEnchantLevel(Enchantment.DURABILITY)
								&& caballo.getInventory().getArmor()
										.getItemMeta()
										.hasEnchant(Enchantment.DURABILITY)) {
							
								caballo.getInventory()
										.setArmor(
												new ItemStack(
														Material.DIAMOND_BARDING));
							
							e.getPlayer().sendMessage("§a[Ender Enchanted Table] §4Your armor is about to be break, reenchant it to use it more time.");
							//Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tellraw "+ e.getPlayer().getName()+ " ['',{'text':'Ver Lista de Gasolineras','clickEvent':{'action':'run_command','value':'/anbuauto list'},'hoverEvent':{'action':'show_text','value':{'text':'','extra':[{'text':'Click para Ver lista\nCosto 50 Coins por TP','color':'aqua'}]}}}]");
							// hara una busqueda de la estacion mas cercana
							e.getPlayer()
									.getWorld()
									.playSound(e.getPlayer().getLocation(),
											Sound.EAT, 1, 1);
							duracion.put(e.getPlayer().getName(), 0);
							caballo.removePotionEffect(PotionEffectType.SPEED);
							return;
						}
						
						
						if (caballo.getInventory().getArmor().getItemMeta()
								.hasEnchant(Enchantment.THORNS)) {
							List<Entity> atacados = caballo.getNearbyEntities(
									5, 5, 5);
							for (int i = 0; atacados.size() > i; i++) {
								if (!atacados.get(i).isInsideVehicle()
										&& atacados.get(i) instanceof LivingEntity) {
									atacados.get(i).setFireTicks(150);
								}
							}
						}
					}
					if (!caballo.hasPotionEffect(PotionEffectType.SPEED)) {
						if (caballo.getVariant().name().equals("MULE")
								&& caballo.getInventory().contains(
										Material.DIAMOND_BARDING)) {
							caballo.addPotionEffect(new PotionEffect(
									PotionEffectType.SPEED, 1800, 3), true);
						}
						if (caballo.getVariant().name().equals("HORSE")
								&& caballo.getInventory().contains(
										Material.DIAMOND_BARDING)) {
							caballo.addPotionEffect(new PotionEffect(
									PotionEffectType.SPEED, 1800, 8), true);
						}
						if (caballo.getVariant().name().equals("DONKEY")
								&& caballo.getInventory().contains(
										Material.DIAMOND_BARDING)) {
							caballo.addPotionEffect(new PotionEffect(
									PotionEffectType.SPEED, 1800, 3), true);
						}
						if (caballo.getVariant().name().equals("MULE")
								&& caballo.getInventory().contains(
										Material.GOLD_BARDING)) {
							caballo.addPotionEffect(new PotionEffect(
									PotionEffectType.SPEED, 1800, 2), true);
						}
						if (caballo.getVariant().name().equals("HORSE")
								&& caballo.getInventory().contains(
										Material.GOLD_BARDING)) {
							caballo.addPotionEffect(new PotionEffect(
									PotionEffectType.SPEED, 1800, 4), true);
						}
						if (caballo.getVariant().name().equals("DONKEY")
								&& caballo.getInventory().contains(
										Material.GOLD_BARDING)) {
							caballo.addPotionEffect(new PotionEffect(
									PotionEffectType.SPEED, 1800, 2), true);
						}
						if (caballo.getVariant().name().equals("MULE")
								&& caballo.getInventory().contains(
										Material.IRON_BARDING)) {
							caballo.addPotionEffect(new PotionEffect(
									PotionEffectType.SPEED, 1800, 1), true);
						}
						if (caballo.getVariant().name().equals("HORSE")
								&& caballo.getInventory().contains(
										Material.IRON_BARDING)) {
							caballo.addPotionEffect(new PotionEffect(
									PotionEffectType.SPEED, 1800, 2), true);
						}
						if (caballo.getVariant().name().equals("DONKEY")
								&& caballo.getInventory().contains(
										Material.IRON_BARDING)) {
							caballo.addPotionEffect(new PotionEffect(
									PotionEffectType.SPEED, 1800, 1), true);
						}
					}

				}
			}
		}
	}


	@EventHandler
	public void encanto(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof Horse) {
			Horse caballo = (Horse) e.getInventory().getHolder();
			Player p = (Player) caballo.getPassenger();
			if (e.getRawSlot() == 1) {
				if (e.getCurrentItem().getType().equals(Material.IRON_BARDING)
						|| e.getCurrentItem().getType()
								.equals(Material.GOLD_BARDING)
						|| e.getCurrentItem().getType()
								.equals(Material.DIAMOND_BARDING)) {
					p.sendMessage("§a[Ender Enchanted Table] §4You remove the armor.");
					caballo.removePotionEffect(PotionEffectType.SPEED);
				}
			}
			if (e.getRawSlot() == 0) {
				if (e.getCurrentItem().getType().equals(Material.SADDLE)) {
					p.sendMessage("§a[Ender Enchanted Table] §4You remove the saddle.");
				}
			}

		}
		// agregar efecto de fuego a la de espinas

		if (e.getRawSlot() == 0 && e.getInventory().getName().equals("Enchant")){
		if(e.getCurrentItem().isSimilar(
				new ItemStack(Material.GOLD_BARDING))||e.getCurrentItem().isSimilar(
						new ItemStack(Material.IRON_BARDING))||e.getCurrentItem().isSimilar(
								new ItemStack(Material.DIAMOND_BARDING))||e.getCurrentItem().hasItemMeta()) {
			Player p = (Player) e.getWhoClicked();
			//Jugador j = Almacen.getInstancia().getJugador(p.getName());
			//if (j == null)
				//return;
			if(!p.isOp()){
			//if (Permisos.tiene(p, "anbu.chat.operador") || p.isOp()) {
				p.sendMessage("§a[Ender Enchanted Table] §4Only players with permission can enchant horse armors.");
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				if(!e.getCurrentItem().getItemMeta().getDisplayName().contains("Turbina")){return;}
				ItemStack armor2 = null;
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals("Turbina de Oro")) {
					armor2 = new ItemStack(Material.GOLD_BARDING);
					ItemMeta armormeta = armor2.getItemMeta();
					armormeta.setDisplayName("Turbina de Oro");
					armor2.setItemMeta(armormeta);
					armor2.addUnsafeEnchantment(Enchantment.THORNS, 2);
					armor2.addUnsafeEnchantment(
							Enchantment.DURABILITY,
							e.getCurrentItem().getItemMeta()
									.getEnchantLevel(Enchantment.DURABILITY) + 1);
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND,
							2, 1);
					e.getView().setItem(0, armor2);
					p.sendMessage("§a[Ender Enchanted Table] §bArmor enchanted with thorns");
					p.sendMessage("§a[Ender Enchanted Table] §bArmor durability has increase");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals("Turbina de Metal")) {
					armor2 = new ItemStack(Material.IRON_BARDING);
					ItemMeta armormeta = armor2.getItemMeta();
					armormeta.setDisplayName("Turbina de Metal");
					armor2.setItemMeta(armormeta);
					armor2.addUnsafeEnchantment(Enchantment.THORNS, 2);
					armor2.addUnsafeEnchantment(
							Enchantment.DURABILITY,
							e.getCurrentItem().getItemMeta()
									.getEnchantLevel(Enchantment.DURABILITY) + 1);
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND,
							2, 1);
					e.getView().setItem(0, armor2);
					p.sendMessage("§a[Ender Enchanted Table] §bArmor enchanted with thorns");
					p.sendMessage("§a[Ender Enchanted Table] §bArmor durability has increase");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals("Turbina de Diamante")) {
					armor2 = new ItemStack(Material.DIAMOND_BARDING);
					ItemMeta armormeta = armor2.getItemMeta();
					armormeta.setDisplayName("Turbina de Diamante");
					armor2.setItemMeta(armormeta);
					armor2.addUnsafeEnchantment(Enchantment.THORNS, 1);
					armor2.addUnsafeEnchantment(
							Enchantment.DURABILITY,
							e.getCurrentItem().getItemMeta()
									.getEnchantLevel(Enchantment.DURABILITY) + 1);
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND,
							2, 1);
					e.getView().setItem(0, armor2);
					p.sendMessage("§a[Ender Enchanted Table] §bArmor enchanted with thorns");
					p.sendMessage("§a[Ender Enchanted Table] §bArmor durability has increase");
				}
				return;
			}
			if (e.getCurrentItem().isSimilar(
					new ItemStack(Material.GOLD_BARDING))
					|| e.getCurrentItem().isSimilar(
							new ItemStack(Material.IRON_BARDING))
					|| e.getCurrentItem().isSimilar(
							new ItemStack(Material.DIAMOND_BARDING))) {
				
				if (!e.getCurrentItem().hasItemMeta()) {
					ItemStack armor = new ItemStack(Material.GOLD_BARDING);
					ItemMeta armormeta = armor.getItemMeta();
					if (e.getCurrentItem().isSimilar(
							new ItemStack(Material.GOLD_BARDING))) {
						armor = new ItemStack(Material.GOLD_BARDING);
						armormeta.setDisplayName("Turbina de Oro");
					}
					if (e.getCurrentItem().isSimilar(
							new ItemStack(Material.IRON_BARDING))) {
						armor = new ItemStack(Material.IRON_BARDING);
						armormeta.setDisplayName("Turbina de Metal");
					}
					if (e.getCurrentItem().isSimilar(
							new ItemStack(Material.DIAMOND_BARDING))) {
						armor = new ItemStack(Material.DIAMOND_BARDING);
						armormeta.setDisplayName("Turbina de Diamante");
					}
					armor.setItemMeta(armormeta);
					armor.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND,
							2, 1);
					e.getView().setItem(0, armor);
					p.sendMessage("§a[Ender Enchanted Table] §bArmor has been enchanted with durability");
				}
			}
		}}

	}
}

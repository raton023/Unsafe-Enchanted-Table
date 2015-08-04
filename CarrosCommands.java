package com.craftilandia.unsafeenchantedtable;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class CarrosCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String arg2, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (command.getName().equalsIgnoreCase("myhorse")) {

					List<Entity> entities = p.getNearbyEntities(60, 5, 60);
					for (int i = 0; i < entities.size(); i++) {
						if (entities.get(i) instanceof Horse) {
							Horse caballo = (Horse) entities.get(i);
							if (caballo.getOwner() != null) {
								if (caballo.getOwner().getName()
										.equals(p.getName())) {
									entities.get(i).teleport(p.getLocation());
								
							}
						}
					}
				}
				return true;
			}
		}
		return false;
	}

}

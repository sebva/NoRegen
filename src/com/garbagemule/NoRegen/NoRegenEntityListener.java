package com.garbagemule.NoRegen;

import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class NoRegenEntityListener extends EntityListener {

	private NoRegen plugin;
	
	public NoRegenEntityListener(NoRegen plugin) {
		super();
		this.plugin = plugin;
	}
	
	@Override
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if(event.getRegainReason() == RegainReason.REGEN) {
			if(this.plugin.worldList.contains(event.getEntity().getWorld()))
				event.setCancelled(true);
		}
	}

}

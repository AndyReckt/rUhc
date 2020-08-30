package com.thesevenq.uhc.utilties.spectator;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class SpectatorData {

	private ItemStack[] contents;
	private ItemStack[] armor;
	private GameMode gameMode;
	
	public SpectatorData() { }
}

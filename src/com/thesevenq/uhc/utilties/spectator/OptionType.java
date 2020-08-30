package com.thesevenq.uhc.utilties.spectator;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
public enum OptionType {

	DAMAGE("Damage", new ArrayList<>()),
	PLACE("Place", new ArrayList<>()),
	BREAK("Break", new ArrayList<>()),
	PICKUP("Pickup", new ArrayList<>()),
	INTERACT("Interact", new ArrayList<>()),
	CHEST("Chest", new ArrayList<>());
	
	@Setter private String name;
	@Setter private ArrayList<UUID> players;
	
	OptionType(String name, ArrayList<UUID> players) {
		this.name = name;
		this.players = players;
	}
}

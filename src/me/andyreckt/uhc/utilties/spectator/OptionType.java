package me.andyreckt.uhc.utilties.spectator;


import java.util.ArrayList;
import java.util.UUID;


public enum OptionType {
	DAMAGE("Damage", new ArrayList<>()),
	PLACE("Place", new ArrayList<>()),
	BREAK("Break", new ArrayList<>()),
	PICKUP("Pickup", new ArrayList<>()),
	INTERACT("Interact", new ArrayList<>()),
	CHEST("Chest", new ArrayList<>());

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<UUID> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<UUID> players) {
		this.players = players;
	}

	private String name;
	 private ArrayList<UUID> players;
	
	OptionType(String name, ArrayList<UUID> players) {
		this.name = name;
		this.players = players;
	}
}

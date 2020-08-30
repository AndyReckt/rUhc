package com.thesevenq.uhc.utilties;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.Map.Entry;

public class ActionMessage {
	
	private List<AMText> Text;

	public ActionMessage() {
		this.Text = new ArrayList<AMText>();
	}

	public AMText addText(String Message) {
		AMText Text = new AMText(Color.translate(Message));
		
		this.Text.add(Text);
		
		return Text;
	}

	public String getFormattedMessage() {
		String Chat = "[\"\",";
		
		for(AMText Text : this.Text) {
			Chat = Chat + Text.getFormattedMessage() + ",";
		}
		
		Chat = Chat.substring(0, Chat.length() - 1);
		Chat = Chat + "]";
		return Chat;
	}

	public void sendToPlayer(Player Player) {
		IChatBaseComponent base = IChatBaseComponent.ChatSerializer.a(getFormattedMessage());
		
		PacketPlayOutChat packet = new PacketPlayOutChat(base, (byte) 1);
		
		((CraftPlayer) Player).getHandle().playerConnection.sendPacket(packet);
	}

	public static enum ClickableType {
		RunCommand("run_command"), SuggestCommand("suggest_command"), OpenURL("open_url");

		public String Action;

		private ClickableType(String Action) {
			this.Action = Action;
		}
	}

	public class AMText {
		private String Message;
		private Map<String, Entry<String, String>> Modifiers;

		public AMText(String Text) {
			this.Message = "";
			this.Modifiers = new HashMap<>();
			this.Message = Text;
		}

		public String getMessage() {
			return this.Message;
		}

		public String getFormattedMessage() {
			String Chat = "{\"text\":\"" + this.Message + "\"";

			for(String Event : this.Modifiers.keySet()) {
				Entry<String, String> Modifier = this.Modifiers.get(Event);
				Chat = Chat + ",\"" + Event + "\":{\"action\":\"" + (String) Modifier.getKey() + "\",\"value\":" + (String) Modifier.getValue() + "}";
			}

			Chat = Chat + "}";
			return Chat;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public AMText addHoverText(String... Text) {
			String Value = "";

			if(Text.length == 1) {
				Value = "{\"text\":\"" + Text[0] + "\"}";
			} else {
				Value = "{\"text\":\"\",\"extra\":[";

				for(String Message : Text) {
					Value = Value + "{\"text\":\"" + Message + "\"},";
				}

				Value = Value.substring(0, Value.length() - 1);
				Value = Value + "]}";
			}

			Object Values = new AbstractMap.SimpleEntry("show_text", Value);
			this.Modifiers.put("hoverEvent", (Entry<String, String>) Values);
			return this;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public AMText addHoverItem(org.bukkit.inventory.ItemStack Item) {
			String Value = CraftItemStack.asNMSCopy(Item).getTag().toString();

			Entry<String, String> Values = new AbstractMap.SimpleEntry("show_item", Value);

			this.Modifiers.put("hoverEvent", Values);
			return this;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public AMText setClickEvent(ActionMessage.ClickableType Type, String Value) {
			String Key = Type.Action;

			Entry<String, String> Values = new AbstractMap.SimpleEntry(Key, "\"" + Value + "\"");
			
			this.Modifiers.put("clickEvent", Values);
			return this;
		}
	}
}

package com.thesevenq.uhc.config;

import com.thesevenq.uhc.managers.OptionManager;
import lombok.Getter;
import lombok.Setter;
import com.thesevenq.uhc.managers.OptionManager;

@Getter
@Setter
public class Option {

	private int defaultValue;
	private int finalValue;
	private String name;
	private String desc;
	private boolean isBoolean;
	public int value;
	private int[] translatedValues;

	public Option(String name, String desc, int defaultValue, int... translatedValues) {
		this.name = name;
		this.desc = desc;
		
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		
		this.isBoolean = translatedValues.length == 0 ? true : false;
		this.finalValue = isBoolean ? 1 : translatedValues.length - 1;
		this.translatedValues = translatedValues;
		
		OptionManager.getOptions().add(this);
	}

	public void nextValue() {
		if(isFinal()) {
			value = 0;
			return;
		}
		
		value++;
	}

	public boolean isFinal() {
		return value == finalValue;
	}

	public int translateValue(int i) {
		return translatedValues[i];
	}

	public int getTranslatedValue() {
		return translateValue(value);
	}

	public String allTransValues() {
		StringBuilder sb = new StringBuilder();
		
		for(int i : translatedValues) {
			sb.append(i + " ");
		}
		
		return sb.toString();
	}

	public boolean isEnabled() {
		return defaultValue == 1 ? true : false;
	}

	public String defaultFormatted() {
		return isBoolean ? defaultValue == 0 ? "Disabled" : "Enabled" : this.translateValue(this.defaultValue) + "";
	}

}

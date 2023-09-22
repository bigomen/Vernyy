package com.luxfacta.vernyy.api.shared;

public class MaskUtils {
	
	private MaskUtils() {
		
	}
	
	public static String removeMascara(String value) {
		if (value == null)
			return value;
		
		return value.replaceAll("[^0-9]", "");
	}

}

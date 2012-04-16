package org.openmrs.module.plm;

public class Utility {
	public static <T> T as(Class<T> clazz, Object o){
		if(clazz.isInstance(o)){
			return clazz.cast(o);
		}
		return null;
	}

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().equals("");
	}
}

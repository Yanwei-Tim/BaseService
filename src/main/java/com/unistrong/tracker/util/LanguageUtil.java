package com.unistrong.tracker.util;

public class LanguageUtil {

	
	private static final ThreadLocal<Language> languageThreadLocal = new ThreadLocal<Language>(); 
	
	public static void set(Language lan) {
		 languageThreadLocal.set(lan);
    }
    
    public static void unset() {
    	languageThreadLocal.remove();
    }
    
    public static Language get() {
    	return languageThreadLocal.get();
    }
}

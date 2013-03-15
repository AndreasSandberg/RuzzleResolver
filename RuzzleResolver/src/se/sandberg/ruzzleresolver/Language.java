package se.sandberg.ruzzleresolver;

import java.util.Locale;

public enum Language {

	SWEDISH(new Locale("sv").getDisplayLanguage(Locale.getDefault()), "swedish.txt"), 
	ENGLISH(Locale.ENGLISH.getDisplayLanguage(Locale.getDefault()), "brit-a-z.txt");
	
	private String languageName;
	private String fileName;
	
	private Language(String languageName, String fileName) {
		this.languageName = languageName;
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getLanguageName() {
		return languageName;
	}

	
}

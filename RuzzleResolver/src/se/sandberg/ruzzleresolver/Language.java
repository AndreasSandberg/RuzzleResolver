package se.sandberg.ruzzleresolver;

import java.util.Locale;

public enum Language {

	ENGLISH(Locale.ENGLISH.getDisplayLanguage(Locale.getDefault()) + " (UK)", "brit-a-z.txt"),
	ENGLISH_US(Locale.ENGLISH.getDisplayLanguage(Locale.getDefault())+ " (US)", "yawl-0.3.2.txt"),
	SWEDISH(new Locale("sv").getDisplayLanguage(Locale.getDefault()), "swedish.txt"), 
	NORWEGIAN(new Locale("no").getDisplayLanguage(Locale.getDefault()), "NSF-ordlisten.txt"),
	DANISH(new Locale("da").getDisplayLanguage(Locale.getDefault()), "RO2012.txt");

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

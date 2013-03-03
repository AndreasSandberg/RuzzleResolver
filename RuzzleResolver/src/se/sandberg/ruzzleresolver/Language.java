package se.sandberg.ruzzleresolver;

public enum Language {
	
	SWEDISH("Svenska", "swedish.txt"), ENGLISH("Engelska", "brit-a-z.txt");
	
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

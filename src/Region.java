
public enum Region {
	BR("BR"), EUNE("EUNE"), EUW("EUW"), KR("KR"), LAN("LAN"), LAS("LAS"), NA("NA"), OCE("OCE"), RU("RU"), TR("TR");
	
	private final String name;
	
	Region(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}

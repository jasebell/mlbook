package chapter2;

import java.util.HashMap;
import java.util.Map;

public class CountryHashMap {

	private Map<String, String> countries = new HashMap<String, String>();
	
	public CountryHashMap() {
		countries.put("Ireland", "IE");
		countries.put("Eire", "IE");
		countries.put("Republic of Ireland", "IE");
		countries.put("Northern Ireland", "UK");
		countries.put("England", "UK");
		// you could add more or generate from a database.
	}
	
	public String getCountryCode(String country) {
		return countries.get(country);
	}
	
	public static void main(String[] args) {
		CountryHashMap chm = new CountryHashMap();
		System.out.println(chm.getCountryCode("Ireland"));
		System.out.println(chm.getCountryCode("Northern Ireland"));
	}
}

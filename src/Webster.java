import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class Webster {
	private static String url = "";
	private static String thesaurusKey = "d16d548c-b53b-4079-ad04-e44860c3bffa";
	private static String dictionaryKey = "d6e743a0-6fb4-4ccc-9096-bdcde56948f7";
	
	public static void main(String[] args){
		Webster webster = new Webster();
		String query = webster.buildThesuarusQuery("something");
		
		System.out.println("Reading XML ...");
		webster.getXMLFromUrl(query);
		System.out.println("XML Read");
	}
	
	public String buildThesuarusQuery(String word){
		String query = "http://www.dictionaryapi.com/api/v1/references/thesaurus/xml/" + word + "?key=" + thesaurusKey; 
		return query;
	}
	
	public void getXMLFromUrl(String strUrl){
		URL url;
		BufferedReader in;
		StringBuilder xml = new StringBuilder();
		try {
			url = new URL(strUrl);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null)
				xml.append(inputLine);
				System.out.println(inputLine);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("XML NOT READ");
		}
	}
}

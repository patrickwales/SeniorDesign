import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class Webster {
	private static String thesaurusKey = "d16d548c-b53b-4079-ad04-e44860c3bffa";
	private static String dictionaryKey = "d6e743a0-6fb4-4ccc-9096-bdcde56948f7";
	
	public static void main(String[] args){
		ArrayList<String> synonyms = Webster.getSynonyms("word");
		
		// print each synonym
		for(String syn : synonyms){
			System.out.print(syn + ", ");
		}
	}
	
	public static ArrayList<String> getSynonyms(String word){
		// Build a webster query
		String query = buildThesaurusQuery(word);
		
		// Get string xml from url query
		String xmlStr = getStrXMLFromUrl(query);
		
		// Convert string xml to xml document
		Document xmlDoc  = stringToXML(xmlStr);
		
		// Get synonyms
		return getTagContents("syn", xmlDoc);
	}
	
	public static String buildThesaurusQuery(String word){
		String query = "http://www.dictionaryapi.com/api/v1/references/thesaurus/xml/" + word + "?key=" + thesaurusKey; 
		return query;
	}
	
	public static String getStrXMLFromUrl(String strUrl){
		URL url;
		BufferedReader in;
		StringBuilder xml = new StringBuilder();
		try {
			url = new URL(strUrl);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null){
				xml.append(inputLine);
				System.out.println(inputLine);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("XML NOT READ");
		}
		return xml.toString();
	}
	
	public static Document stringToXML(String xmlStr){
		try{
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlStr));
			
			Document doc = db.parse(is);
			return doc;
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("XML string failed to convert to xml.");
		}
		return null;
	}
	
	public static ArrayList<String> getTagContents(String tagName, Document xml){
		// Get node list that matches tagName
		NodeList nodes = xml.getElementsByTagName(tagName);
		ArrayList<String> tagContents = new ArrayList<String>();
		
		for(int i = 0; i < nodes.getLength(); i++){
			Element element = (Element) nodes.item(i);
			NodeList list = element.getChildNodes();
			for(int j = 0; j < list.getLength(); j++){
				System.out.println("Syn " + j + " : " + list.item(j).getNodeValue());
				
				// TODO Delimit / clean node values
				if(list.item(j).getNodeValue() != null){
					String[] words = list.item(j).getNodeValue().split(",");
					List<String> wordList = Arrays.asList(words); 
					tagContents.addAll(wordList);
				}
			}
		}
		
		return tagContents;
	}
	
}

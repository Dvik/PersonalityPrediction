/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personality_prediction;
//import com.sun.javafx.Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
/**
 *
 * @author somya
 */

/**
 * Interface to the LIWC dictionary, implementing patterns for each LIWC category
 * based on the LIWC.CAT file.
 *  
 * @author Francois Mairesse, <a href=http://www.mairesse.co.uk
 *         target=_top>http://www.mairesse.co.uk</a>
 * @version 1.01
 */
public class LIWCDictionary {

	/** Mapping associating LIWC features to regular expression patterns. */
	private Map<String,Pattern> map;
	
	/**
	 * Loads dictionary from LIWC dictionary tab-delimited text file (with
	 * variable names as first row). Each word category is converted into a
	 * regular expression that is a disjunction of all its members.
	 * 
	 * @param catFile
	 *            dictionary file, it should be pointing to the LIWC.CAT file of
	 *            the Linguistic Inquiry and Word Count software (Pennebaker &
	 *            Francis, 2001).
	 */
	public LIWCDictionary(File catFile) {
		try {
			map = loadLIWCDictionary(catFile);
			System.err.println("LIWC dictionary loaded ("
					+ map.size() + " lexical categories)");

		} catch (IOException e) {
			System.err.println("Error: file " + catFile + " doesn't exist");
			e.printStackTrace();
			System.exit(1);
		} catch (NullPointerException e) {
			System.err.println("Error: LIWC dicitonary file " + catFile + " doesn't have the right format");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Loads dictionary from LIWC dictionary tab-delimited text file (with
	 * variable names as first row). Each word category is converted into a
	 * regular expression that is a disjunction of all its members.
	 * 
	 * @param dicFile
	 *            dictionary file, it should be pointing to the LIWC.CAT file of
	 *            the Linguistic Inquiry and Word Count software (Pennebaker &
	 *            Francis, 2001).
	 * @return hashtable associating each category with a regular expression
	 *         (Pattern object) matching each word.
	 */
	private Map<String,Pattern> loadLIWCDictionary(File dicFile) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(dicFile));
		String line;

		Map<String,Pattern> wordLists = new LinkedHashMap<String,Pattern>();
		String currentVariable = "";
		String catRegex = "";
		int word_count = 0;

		while ((line = reader.readLine()) != null) {

			// if encounter new variable
			if (line.matches("\\t[\\w ]+")) {
				// add full regex to database
				if (!catRegex.equals("")) {
					catRegex = catRegex.substring(0, catRegex.length() - 1);
					catRegex = "(" + catRegex + ")";
					catRegex = catRegex.replaceAll("\\*", "[\\\\w']*");
					wordLists.put(currentVariable, Pattern.compile(catRegex));
				}
				// update variable
				currentVariable = line.split("\t")[1];
				catRegex = "";

			} else if (line.matches("\t\t.+ \\(\\d+\\)")) {
				word_count++;
				String newPattern = line.split("\\s+")[1].toLowerCase();
				catRegex += "\\b" + newPattern + "\\b|";
			}
		}
		//  add last regex to database
		if (!catRegex.equals("")) {
			catRegex = catRegex.substring(0, catRegex.length() - 1);
			catRegex = "(" + catRegex + ")";
			catRegex = catRegex.replaceAll("\\*", "[\\\\w']*");
			wordLists.put(currentVariable, Pattern.compile(catRegex));
		}

		reader.close();

		// System.err.println(word_count + " words and " + wordLists.size() +"
		// categories loaded in LIWC dictionary");
		return wordLists;
	}
	
	
	public Map<String,Double> getJustCounts(String text, boolean absoluteCounts) {

		Map<String,Double> counts = new LinkedHashMap<String, Double>(map.size());
		String[] words = tokenize(text);
		String[] sentences = splitSentences(text);
		
		System.err.println("Input text splitted into " + words.length
				+ " words and " + sentences.length + " sentences");
		
		// word count (NOT A PROPER FEATURE)
		if (absoluteCounts) { counts.put("WC", new Double(words.length)); }
		counts.put("WPS", new Double(sentences.length));
		
		// type token ratio, words with more than 6 letters, abbreviations,
		// emoticons, numbers
		int sixletters = 0;
		int numbers = 0;
		for (int i = 0; i < words.length; i++) {
			String word = words[i].toLowerCase();
			if (word.length() > 6) {
				sixletters++;
			}

			if (word.matches("-?[,\\d+]*\\.?\\d+")) {
				numbers++;
			}
		}
		
		Set<String> types = new LinkedHashSet<String>(Arrays.asList(words));
		counts.put("UNIQUE", new Double(types.size()));
		counts.put("SIXLTR", new Double(sixletters));
		// abbreviations
		int abbrev = StringUtils.countMatches("\\w\\.(\\w\\.)+", text);
		counts.put("ABBREVIATIONS", new Double(abbrev));
		// emoticons
		int emoticons = StringUtils
				.countMatches("[:;8%]-[\\)\\(\\@\\[\\]\\|]+", text);
		counts.put("EMOTICONS", new Double(emoticons));
		// text ending with a question mark
		int qmarks = StringUtils.countMatches("\\w\\s*\\?", text);
		counts.put("QMARKS", new Double(qmarks));
		// punctuation
		int period = StringUtils.countMatches("\\.", text);
		counts.put("PERIOD", new Double(period));
		int comma = StringUtils.countMatches(",", text);
		counts.put("COMMA", new Double(comma));
		int colon = StringUtils.countMatches(":", text);
		counts.put("COLON", new Double(colon));
		int semicolon = StringUtils.countMatches(";", text);
		counts.put("SEMIC", new Double(semicolon));
		int qmark = StringUtils.countMatches("\\?", text);
		counts.put("QMARK", new Double(qmark));
		int exclam = StringUtils.countMatches("!", text);
		counts.put("EXCLAM", new Double(exclam));
		int dash = StringUtils.countMatches("-", text);
                
		counts.put("DASH", new Double(dash));
		int quote = StringUtils.countMatches("\"", text);
		counts.put("QUOTE", new Double(quote));
		int apostr = StringUtils.countMatches("'", text);
		counts.put("APOSTRO", new Double(apostr));
		int parent = StringUtils.countMatches("[\\(\\[{]", text);
		counts.put("PARENTH", new Double(parent));
		int otherp = StringUtils.countMatches("[^\\w\\d\\s\\.:;\\?!\"'\\(\\{\\[,-]",
				text);
		counts.put("OTHERP", new Double(otherp));
		int allp = period + comma + colon + semicolon + qmark + exclam + dash
				+ quote + apostr + parent + otherp;
		counts.put("ALLPCT", new Double(allp));

		// PATTERN MATCHING

		// store word in dic
		boolean[] indic = new boolean[words.length];
		for (int i = 0; i < indic.length; i++) {
			indic[i] = false;
		}

		// first get all lexical counts
		for (String cat: map.keySet()) {

			// add entry to output hash
			Pattern catRegex = map.get(cat);
			int catCount = 0;

			for (int i = 0; i < words.length; i++) {

				String word = words[i].toLowerCase();
				Matcher m = catRegex.matcher(word);
				while (m.find()) {
					catCount++;
					indic[i] = true;
				}
			}
			counts.put(cat, new Double(catCount));
		}

		// put ratio of words matched
		int wordsMatched = 0;
		for (int i = 0; i < indic.length; i++) {
			if (indic[i]) {
				wordsMatched++;
			}
		}
		counts.put("DIC", new Double(wordsMatched));
		// add numerical numbers
		double nonNumeric = ((Double) counts.get("NUMBERS")).doubleValue();
		counts.put("NUMBERS", new Double(nonNumeric + numbers));               
		return counts;
	}
	
	/**
	 * Splits a text into words separated by non-word characters.
	 * 
	 * @param text text to tokenize.
	 * @return an array of words.
	 */
	public static String[] tokenize(String text) {
		
		String words_only = text.replaceAll("\\W+\\s*", " ").replaceAll(
				"\\s+$", "").replaceAll("^\\s+", "");
		String[] words = words_only.split("\\s+");
		return words;
	}
	
	
	
	/**
	 * Splits a text into sentences separated by a dot, exclamation point or question mark.
	 * 
	 * @param text text to tokenize.
	 * @return an array of sentences.
	 */
	public static String[] splitSentences(String text) {
	//return text.split("\\s*[\\.!\\?]+\\s+");
		return text.split("\\s*[\\.!\\?]+\\S+");
                
	}
        
        
        public  void Display_Dictionary_Map(){
            Iterator<Map.Entry<String, Pattern>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Pattern> entry = entries.next();
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
        }
}//end of class
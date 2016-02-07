package spellcheck.service.impl;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import spellcheck.service.SpellCheckService;

public class SpellCheckServiceImpl implements SpellCheckService{
	
	private static SpellChecker spellChecker = null;
	
	
	static{
		try {
			Directory directory = FSDirectory.open(Paths.get(System.getProperty("user.home") + "/indexes"));
			spellChecker = new SpellChecker(directory);
			spellChecker.indexDictionary(new PlainTextDictionary(Paths.get(System.getProperty("user.home") + "/dictionary.txt")),new IndexWriterConfig(new StandardAnalyzer()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] suggestWords(String key) throws IOException{
		return spellChecker.suggestSimilar(key, 5);
	}
	
	public static void main(String[] args) throws IOException{
		String[] keys = {"Hwllo"};
		for (String key : keys) {
			String[] resultSet = suggestWords(key);
			for (String result : resultSet) {
				System.out.println(result);
			}
		}
	}
	
}

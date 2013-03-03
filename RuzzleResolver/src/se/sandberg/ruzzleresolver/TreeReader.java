package se.sandberg.ruzzleresolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;

public class TreeReader {

	private CharacterTree tree;
	private static final Charset charset = Charset.forName("ISO8859-1");

	public TreeReader(final InputStream assetInputStream, final String[][] characters) throws IOException {
		tree = new CharacterTree();
		readFile(assetInputStream, characters);
	}


	public CharacterTree getTree() {
		return tree;
	}


	private void readFile(InputStream assetInputStream, String[][] characters) throws IOException {
		//Only read words containing the characters on the game field.
		HashSet<Character> gameCharacters = new HashSet<Character>();
		for(String[] characterArr : characters){
			for(String character : characterArr){
				gameCharacters.add(character.toCharArray()[0]);
			}
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(assetInputStream, charset));
		String line = null;
		outer : while((line = reader.readLine()) != null){
			for(Character c : line.toCharArray()){
				if(!gameCharacters.contains(c)){
					continue outer;
				}
			}
			tree.add(line);
		}
	}

}

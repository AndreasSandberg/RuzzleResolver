package se.sandberg.ruzzleresolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TreeReader {

	private CharacterTree tree;

	public TreeReader(final InputStream assetInputStream) {
		tree = new CharacterTree();

		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new Runnable() {
			@Override
			public void run() {
				try {
					readFile(assetInputStream);
				} catch (IOException e) {
					//TODO: something clever...
				}
			}
		});
	}


	public CharacterTree getTree() {
		return tree;
	}


	private void readFile(InputStream assetInputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(assetInputStream));
		String s = null;

		while((s = reader.readLine()) != null){
			//The game field has 16 characters
			if(s.length() > 16){
				continue;
			}
			s = s.toUpperCase(new Locale("sv"));
			tree.add(s);
		}
	}

}

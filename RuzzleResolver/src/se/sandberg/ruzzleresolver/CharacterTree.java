package se.sandberg.ruzzleresolver;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class CharacterTree is a representation of a simple tree containing character and boolean pairs.
 * 
 * @author Andreas Sandberg
 */
public class CharacterTree {

	private Node root = new Node();

	/**
	 * Adds the word to this tree.
	 *
	 * @param word the word to add.
	 */
	public void add(String word) {
		Node node = this.root;
		for (int i = 0; i < word.length(); i++ ) {
			char c = word.charAt(i);
			if (!node.children.containsKey(c) ){
				Node newNode = new Node();
				node.children.put(c, newNode);
				node = newNode;
			}else{
				node = node.children.get(c);
			}
		}
		node.isWord = true;
	}

	/**
	 * Gets the word from the tree if present.
	 *
	 * @param word the word to retrieve.
	 * @return the node if present or <code>null</code> if missing.
	 */
	public Node get(String word){
		Node node = this.root;
		for (int i = 0; i < word.length(); i++){
			node = node.children.get(word.charAt(i));
			if(node == null){
				return null;
			}
		}
		return node;
	}

	
	static class Node {
		
		private boolean isWord;
		private Map<Character, Node> children = new HashMap<Character, Node>();
		
		public boolean isWord() {
			return isWord;
		}
	}
}


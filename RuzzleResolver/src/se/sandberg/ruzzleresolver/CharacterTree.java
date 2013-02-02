package se.sandberg.ruzzleresolver;

import java.util.HashMap;
import java.util.Map;

public class CharacterTree {

	private Node root = new Node();

	//Must add end condition (when word ends) (boolean)
	public void add(String s) {
		Node node = this.root;
		for (int i = 0; i < s.length(); i++ ) {
			char c = s.charAt(i);
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

	public boolean isWord(String s){
		Node node = this.root;
		for (int i = 0; i < s.length(); i++){
			node = node.children.get(s.charAt(i));
			if(node == null){
				return false;
			}
		}
		return node.isWord;
	}

	public boolean contains(String s) {
		Node node = this.root;
		for (int i = 0; i < s.length(); i++){
			node = node.children.get(s.charAt(i));
			if(node == null){
				return false;
			}
		}
		return true;
	}

	private static class Node {
		public boolean isWord;
		public Map<Character, Node> children = new HashMap<Character, Node>();
	}
}


package se.sandberg.ruzzleresolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import se.sandberg.ruzzleresolver.activities.ResultsActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class WordFinder extends AsyncTask<String[][], Void, HashMap<String, List<CharIndex>>>{

	private HashMap<String, List<CharIndex>> foundWords = new HashMap<String, List<CharIndex>>();
	private ProgressDialog dialog;
	private final Context context;
	private InputStream assetInputStream;
	private CharacterTree tree;
	private String[][] game;

	public WordFinder(Context context, InputStream assetInputStream) {
		super();
		this.context = context;
		this.assetInputStream = assetInputStream;

	}

	@Override
	protected HashMap<String, List<CharIndex>> doInBackground(String[][]... arg0) {
		game = arg0[0];
		try {
			TreeReader treeReader = new TreeReader(assetInputStream, game);
			tree = treeReader.getTree();
		} catch (IOException e) {
			//TODO Better error handling...
			return new HashMap<String, List<CharIndex>>();
		}

		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				nextChar(game, j, i, "", new int[4][4], 0);
			}
		}

		return foundWords;
	}


	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(context);
		dialog.setMessage("Letar efter ord...");
		dialog.setIndeterminate(true);
		dialog.show();
		super.onPreExecute();
	} 


	@Override
	protected void onPostExecute( HashMap<String, List<CharIndex>> result) {
		super.onPostExecute(result);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}

		Intent intent = new Intent(context, ResultsActivity.class);
		intent.putExtra(ResultsActivity.class.getPackage().toString()+".result", result);
		intent.putExtra(ResultsActivity.class.getPackage().toString()+".game", new GamePlan(game));
		context.startActivity(intent);
	}

	private String nextChar(String[][] game, int indexVertical, int indexHorisontal, String soFar, int [][] visited, int characterNumber){

		//This is the end condition, the tree does not have any word (or sub word) with these characters.
		if(soFar.length() > 0 && tree.get(soFar) == null){
			return soFar;
		}
		
		//If the word ha not been found before and it is a word, add it (and the coordinates to the result map)
		if(!foundWords.containsKey(soFar) && tree.get(soFar).isWord()){
			foundWords.put(soFar, createCharIndexList(visited));
		}
		
		//Set the character (coordinate) as visited, note the the character number is used to specify the order of the found characters.
		visited[indexVertical][indexHorisontal] = ++characterNumber;
		
		//Visit all (unvisited) neighbors.
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				//The character we just visited.
				if(i == 0 && j == 0){
					continue;
				}
				//Don't go outside the game field.
				if(indexVertical+i < 0 || indexVertical+i > 3){
					continue;
				}
				//Don't go outside the game field.
				if(indexHorisontal+j < 0 || indexHorisontal+j > 3){
					continue;
				}
				
				//Zero means unvisited.
				if(visited[indexVertical+i][indexHorisontal+j] == 0){
					nextChar(game, indexVertical+i, indexHorisontal+j, soFar+game[indexVertical][indexHorisontal], copy(visited), characterNumber);
				}
			}			
		}
		return soFar;
	}

	private List<CharIndex> createCharIndexList(int[][] visited) {

		List<CharIndex> result = new ArrayList<CharIndex>();
		for(int indexVertical = 0; indexVertical < 4; indexVertical++){
			for(int indexHorisontal = 0; indexHorisontal < 4; indexHorisontal++){
				if(visited[indexVertical][indexHorisontal] > 0){
					//Add the character coordinate and the order to the result object.
					result.add(new CharIndex(indexVertical, indexHorisontal, visited[indexVertical][indexHorisontal]));
				}
			}
		}
		
		//The order is important when the path is to be painted in the PathActivity.
		Collections.sort(result, new Comparator<CharIndex>() {
			@Override
			public int compare(CharIndex o1, CharIndex o2) {
				return Integer.valueOf(o1.getVisitedNumber()).compareTo(o2.getVisitedNumber());
			}
		});

		return result;
	}

	//Copy the integer arrays as fast as possible.
	private int[][] copy(int[][] source){
		int[][] result = new int[4][4];
		for(int i = 0; i < result.length; i++){
			System.arraycopy(source[i], 0, result[i], 0, 4);
		}
		return result;
	}

}

package se.sandberg.ruzzleresolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class WordFinder extends AsyncTask<String[][], Void, ArrayList<String>>{

	private ArrayList<String> foundWords = new ArrayList<String>();
	private ProgressDialog dialog;
	private final Context context;
	private InputStream assetInputStream;
	private CharacterTree tree;
	
	public WordFinder(Context context, InputStream assetInputStream) {
		super();
		this.context = context;
		this.assetInputStream = assetInputStream;

	}

	@Override
	protected ArrayList<String> doInBackground(String[][]... arg0) {
		String[][] game = arg0[0];
		try {
			TreeReader treeReader = new TreeReader(assetInputStream, game);
			tree = treeReader.getTree();
		} catch (IOException e) {
			//TODO Better error handling...
			return new ArrayList<String>();
		}
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				nextChar(game, j, i, "", new boolean[4][4]);
			}
		}
		Collections.sort(foundWords, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o2.length() - o1.length();
			}
		});

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
	protected void onPostExecute(ArrayList<String> result) {
		super.onPostExecute(result);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}

		Intent intent = new Intent(context, ResultsActivity.class);
		intent.putStringArrayListExtra(ResultsActivity.class.getPackage().toString()+".result", result);
		context.startActivity(intent);
	}
	
	
	
	private String nextChar(String[][] game, int indexVertical, int indexHorisontal, String soFar, boolean [][] visited){
		
		if(soFar.length() > 0 && tree.get(soFar) == null){
			return soFar;
		}
		
		if(!foundWords.contains(soFar) && tree.get(soFar).isWord()){
			foundWords.add(soFar);
		}

		visited[indexVertical][indexHorisontal] = true;

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
				if(!visited[indexVertical+i][indexHorisontal+j]){
					nextChar(game, indexVertical+i, indexHorisontal+j, soFar+game[indexVertical][indexHorisontal], copy(visited));
				}
			}			
		}
		return soFar;
	}

	private boolean[][] copy(boolean[][] source){
		boolean[][] result = new boolean[4][4];
		for(int i = 0; i < result.length; i++){
			System.arraycopy(source[i], 0, result[i], 0, 4);
		}
		
		return result;

	}




}

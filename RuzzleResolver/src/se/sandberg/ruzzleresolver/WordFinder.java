package se.sandberg.ruzzleresolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.StartElementListener;

public class WordFinder extends AsyncTask<String[][], Void, List<String>>{

	private ArrayList<String> foundWords = new ArrayList<String>();
	private ProgressDialog dialog;
	private final Context context;
	private CharacterTree tree;
	
	public WordFinder(InputStream assetInputStream, Context context) throws IOException {
		super();
		this.context = context;
		tree = new CharacterTree();
		readFile(assetInputStream);
	}

	@Override
	protected List<String> doInBackground(String[][]... arg0) {

		String[][] game = arg0[0];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				nextChar(game, j, i, "", new boolean[4][4]);
			}
		}
		Collections.sort(foundWords, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
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
	protected void onPostExecute(List<String> result) {
		super.onPostExecute(result);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}

		ResultsActivity resultsActivity = new ResultsActivity(result);
		//resultsActivity.startActivity(new Intent());
		
		/*AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if(result.isEmpty()){
			builder.setMessage("Inget resultat").setTitle("Resultat");
		}else{
			builder.setMessage(result.get(0) + " " + result.size()).setTitle("Resultat");
			
		}
		builder.create().show();*/
	}
	
	
	
	private String nextChar(String[][] game, int indexV, int indexH, String soFar, boolean [][] visited){
		
		if(soFar.length() > 0 && tree.get(soFar) == null){
			return soFar;
		}
		
		if(!foundWords.contains(soFar) && tree.get(soFar).isWord()){
			foundWords.add(soFar);
		}

		visited[indexV][indexH] = true;

		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(i == 0 && j == 0){
					continue;
				}
				if(indexV+i < 0 || indexV+i > 3){
					continue;
				}
				if(indexH+j < 0 || indexH+j > 3){
					continue;
				}
				if(!visited[indexV+i][indexH+j]){
					nextChar(game, indexV+i, indexH+j, soFar+game[indexV][indexH], copy(visited));
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

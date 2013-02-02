package se.sandberg.ruzzleresolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class WordFinder extends AsyncTask<String[][], Void, List<String>>{

	private static final HashSet<String> vocals = new HashSet<String>();
	private HashSet<String> words = new HashSet<String>();
	private ArrayList<String> foundWords = new ArrayList<String>();
	private ProgressDialog dialog;
	private final Context context;
	
	public WordFinder(InputStream assetInputStream, Context context) throws IOException {
		super();
		this.context = context;
		readFile(assetInputStream);
		vocals.add("a");
		vocals.add("e");
		vocals.add("i");
		vocals.add("o");
		vocals.add("u");
		vocals.add("y");
		vocals.add("å");
		vocals.add("ä");
		vocals.add("ö");
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

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if(result.isEmpty()){
			builder.setMessage("Inget resultat").setTitle("Resultat");
			
		}else{
			builder.setMessage(result.get(0)).setTitle("Resultat");
			
		}
		builder.create().show();
	}
	
	private String nextChar(String[][] game, int indexV, int indexH, String soFar, boolean [][] visited){
		
		if(endWithTwoVocalsOrFourNonVocals(soFar)){
			return soFar;
		}

		if(words.contains(soFar) && !foundWords.contains(soFar)){
			foundWords.add(soFar);
		}

		visited[indexV][indexH] = true;
		if(indexV < 3 && !visited[indexV+1][indexH]){
			nextChar(game, indexV+1, indexH, soFar+game[indexV][indexH], copy(visited));
		}
		if(indexH < 3 && !visited[indexV][indexH+1]){
			nextChar(game, indexV, indexH+1, soFar+game[indexV][indexH], copy(visited));
		}
		if(indexV != 0 && !visited[indexV-1][indexH]){
			nextChar(game, indexV-1, indexH, soFar+game[indexV][indexH], copy(visited));
		}
		if(indexH != 0 && !visited[indexV][indexH-1]){
			nextChar(game, indexV, indexH-1, soFar+game[indexV][indexH], copy(visited));
		}
		if(indexV < 3 && indexH < 3 && !visited[indexV+1][indexH+1]){
			nextChar(game, indexV+1, indexH+1, soFar+game[indexV][indexH], copy(visited));
		}

		if(indexV != 0 && indexH < 3 && !visited[indexV-1][indexH+1]){
			nextChar(game, indexV-1, indexH+1, soFar+game[indexV][indexH], copy(visited));
		}

		if(indexV < 3  && indexH != 0 && !visited[indexV+1][indexH-1]){
			nextChar(game, indexV+1, indexH-1, soFar+game[indexV][indexH], copy(visited));
		}

		if(indexV != 0 && indexH != 0 && !visited[indexV-1][indexH-1]){
			nextChar(game, indexV-1, indexH-1, soFar+game[indexV][indexH], copy(visited));
		}


		return soFar;
	}

	//Flaggstång (four non vocals in between two vocals "ggst")
	private boolean endWithTwoVocalsOrFourNonVocals(String soFar) {
		int length = soFar.length();
		if(length < 3){
			return false;
		}else{
			String last = soFar.substring(length-2, length-1);
			String lastLast = soFar.substring(length-3, length-2);
			if(vocals.contains(last) && vocals.contains(lastLast)){
				return true;
			}
			if(length >= 5){
				String lastLastLast = soFar.substring(length-4, length-3);
				String lastLastLastLast = soFar.substring(length-5, length-4);
				if(!vocals.contains(last) 
						&& !vocals.contains(lastLast)
						&& !vocals.contains(lastLastLast)
						&& !vocals.contains(lastLastLastLast)){
					return true;
				}	
			}
		}
		return false;
	}

	private boolean[][] copy(boolean[][] source){
		boolean[][] result = new boolean[4][4];
		System.arraycopy(source, 0, result, 0, 4);
		return result;

	}

	private void readFile(InputStream assetInputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(assetInputStream));
		String s = null;
		while((s = reader.readLine()) != null){
			words.add(s.toLowerCase(new Locale("sv")));

		}
	}


}

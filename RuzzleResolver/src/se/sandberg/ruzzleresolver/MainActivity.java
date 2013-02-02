package se.sandberg.ruzzleresolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private WordFinder wordFinder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {

			InputStream assetInputStream = getAssets().open("swedish.txt");
			wordFinder = new WordFinder(assetInputStream, this);

		} catch (IOException e) {
			createDialog(e.getMessage(), "Fel");

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void findWords(View view) {
		String[][] game = new String [4][4];
		for(int i = 1; i < 5; i++){
			for(int j = 1; j < 5; j++){
				int editTextId = getResources().getIdentifier("editText" + (j + (i-1)*4), "id", "se.sandberg.ruzzleresolver");
				EditText characterEdit = (EditText) findViewById(editTextId);
				game[i-1][j-1] = characterEdit.getText().toString().toLowerCase(new Locale("sv"));
			}
		}
		wordFinder.execute(game);
	}

	private void createDialog(String message, String title){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setMessage(message).setTitle(title);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

}

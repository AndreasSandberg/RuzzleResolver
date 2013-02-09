package se.sandberg.ruzzleresolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	    window.getDecorView().getBackground().setDither(true);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	public void findWords(View view) throws IOException {
		String[][] game = new String [4][4];
		for(int i = 1; i < 5; i++){
			for(int j = 1; j < 5; j++){
				int editTextId = getResources().getIdentifier("editText" + (j + (i-1)*4), "id", "se.sandberg.ruzzleresolver");
				EditText characterEdit = (EditText) findViewById(editTextId);
				game[i-1][j-1] = characterEdit.getText().toString().toUpperCase(new Locale("sv"));
			}
		}
		InputStream assetInputStream = getAssets().open("swedish.txt");
		WordFinder wordFinder = new WordFinder(this, assetInputStream);
		wordFinder.execute(game);
	}
}

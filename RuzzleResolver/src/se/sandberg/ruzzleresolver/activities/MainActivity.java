package se.sandberg.ruzzleresolver.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import se.sandberg.ruzzleresolver.R;
import se.sandberg.ruzzleresolver.WordFinder;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

/**
 * The Class MainActivity is the application start activity.
 */
public class MainActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onAttachedToWindow()
	 */
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
		window.getDecorView().getBackground().setDither(true);
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	/**
	 * Find words.
	 *
	 * @param view the view
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void findWords(View view) throws IOException {
		String[][] game = new String [4][4];
		for(int i = 1; i < 5; i++){
			for(int j = 1; j < 5; j++){
				int editTextId = getResources().getIdentifier("editText" + (j + (i-1)*4), "id", "se.sandberg.ruzzleresolver");
				EditText characterEdit = (EditText) findViewById(editTextId);
				String character = characterEdit.getText().toString().toUpperCase(new Locale("sv"));
				if(character == null || character.length() != 1){
					createDialog("Du måste ange en bokstav i varje ruta.", "Inmatningsfel");
					return;
				}
				game[i-1][j-1] = character;
			}
		}
		InputStream assetInputStream = getAssets().open("swedish.txt");
		WordFinder wordFinder = new WordFinder(this, assetInputStream);
		wordFinder.execute(game);
	}
	
	/**
	 * Creates the dialog.
	 *
	 * @param message the message
	 * @param title the title
	 */
	private void createDialog(String message, String title){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setMessage(message).setTitle(title);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}

package se.sandberg.ruzzleresolver.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import se.sandberg.ruzzleresolver.Language;
import se.sandberg.ruzzleresolver.R;
import se.sandberg.ruzzleresolver.WordFinder;
import se.sandberg.ruzzleresolver.activities.listeners.EditTextListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

/**
 * The Class MainActivity is the application start activity.
 * 
 * @author Andreas Sandberg
 */
public class MainActivity extends Activity {

	public static final String LANGUAGE_SETTING_NAME = "RuzzleLanguage";
	private SharedPreferences preferences;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = getPreferences(MODE_PRIVATE);
		setContentView(R.layout.activity_main);
		registerEditTextListeners();
	}

	private void registerEditTextListeners() {
		ViewGroup layoutGroup = (ViewGroup)findViewById(getResources().getIdentifier("layoutGroup", "id", "se.sandberg.ruzzleresolver"));
		for(int i = 0;  i < layoutGroup.getChildCount()-1; i++){
			View editText = layoutGroup.getChildAt(i);
			View toFocus = layoutGroup.getChildAt(i+1);
			if(editText instanceof EditText && toFocus instanceof EditText){
				((EditText)editText).addTextChangedListener(new EditTextListener((EditText)toFocus));
			}
		}
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		for(Language lang : Language.values()){
			menu.add(Menu.NONE, lang.ordinal(), Menu.NONE, lang.getLanguageName());
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Language lang = Language.values()[item.getItemId()];
		Editor editor = preferences.edit();
		editor.putString(LANGUAGE_SETTING_NAME, lang.name());
		if(!editor.commit()){
			createDialog("Unable to save language setting", "Error");
		}

		return super.onOptionsItemSelected(item);
	}

	public void clear(View view) {
		ViewGroup layoutGroup = (ViewGroup)findViewById(getResources().getIdentifier("layoutGroup", "id", "se.sandberg.ruzzleresolver"));
		for(int i = 0;  i < layoutGroup.getChildCount(); i++){
			View childAt = layoutGroup.getChildAt(i);
			if(childAt instanceof EditText && childAt != null){
				((EditText)childAt).setText("");
				if(i == 0){
					childAt.requestFocus();
				}
			}
		}
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
					createDialog(getResources().getString(R.string.input_error_text), getResources().getString(R.string.input_error_title));
					return;
				}
				game[i-1][j-1] = character;
			}
		}

		Language selectedLang = Language.valueOf(preferences.getString(LANGUAGE_SETTING_NAME, getResources().getString(R.string.default_language)));
		InputStream assetInputStream = getAssets().open(selectedLang.getFileName());
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

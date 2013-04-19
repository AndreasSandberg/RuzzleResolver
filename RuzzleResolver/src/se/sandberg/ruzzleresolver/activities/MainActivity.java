package se.sandberg.ruzzleresolver.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import se.sandberg.ruzzleresolver.Language;
import se.sandberg.ruzzleresolver.R;
import se.sandberg.ruzzleresolver.WordFinder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

	private final class EditTextListener implements TextWatcher {
		private int id; 
		public EditTextListener(int id) {
			this.id = id;
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			//Don't move is character was removed.
			if(s == null || s.length() == 0){
				return;
			}
			int editTextId = getResources().getIdentifier("editText" + id, "id", "se.sandberg.ruzzleresolver");
			EditText characterEdit = (EditText) findViewById(editTextId);
			String character = characterEdit.getText().toString();
			if(character == null  || character.length() == 0){
				characterEdit.requestFocus();	
			}
		}

		@Override
		public void afterTextChanged(Editable s) {}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	}

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
		for(int i = 1; i < 5; i++){
			for(int j = 1; j < 5; j++){
				if(i == 4 && j == 4){
					return;
				}
				int index = (j + (i-1)*4);
				int editTextId = getResources().getIdentifier("editText" + index, "id", "se.sandberg.ruzzleresolver");
				EditText characterEdit = (EditText) findViewById(editTextId);
				if(characterEdit != null){
					characterEdit.addTextChangedListener(new EditTextListener(index+1));

				}
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
			}
		}
		/*for(int i = 1; i < 5; i++){
			for(int j = 1; j < 5; j++){
				int index = (j + (i-1)*4);
				int editTextId = getResources().getIdentifier("editText" + index, "id", "se.sandberg.ruzzleresolver");
				EditText characterEdit = (EditText) findViewById(editTextId);
				if(characterEdit != null){
					characterEdit.setText("");
				}
			}
		}*/
		if(view != null){
			view.clearFocus();
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

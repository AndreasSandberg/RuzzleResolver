package se.sandberg.ruzzleresolver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

public class PathActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path);
		String word = getIntent().getExtras().getString(this.getClass().getPackage().toString()+".result");
		String gamePlan = getIntent().getExtras().getString(this.getClass().getPackage().toString()+".game");
		int editTextIndex = 0;
		for(char character : gamePlan.toCharArray()){
			editTextIndex++;
			String characterAsString = String.valueOf(character);
			//TODO Also filter characters not in path...
			if(!word.contains(characterAsString)){
				continue;
			}
			int editTextId = getResources().getIdentifier("editText" + editTextIndex, "id", "se.sandberg.ruzzleresolver");
			EditText characterEdit = (EditText) findViewById(editTextId);
			characterEdit.setText(characterAsString);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

}

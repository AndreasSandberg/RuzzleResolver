package se.sandberg.ruzzleresolver.activities;

import java.util.ArrayList;

import se.sandberg.ruzzleresolver.CharIndex;
import se.sandberg.ruzzleresolver.GamePlan;
import se.sandberg.ruzzleresolver.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

public class PathActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path);
		@SuppressWarnings("unchecked")
		ArrayList<CharIndex> indexes = (ArrayList<CharIndex>) getIntent().getExtras().getSerializable(this.getClass().getPackage().toString()+".result");
		String[][] game = ((GamePlan) getIntent().getExtras().getParcelable(this.getClass().getPackage().toString()+".game")).getGame();
		
		for(CharIndex charIndex : indexes){
			int indexHorisontal = charIndex.getIndexHorisontal();
			int indexVertical = charIndex.getIndexVertical();
			String character = game[indexVertical][indexHorisontal];
			
			int editTextId = getResources().getIdentifier("editText" + ((indexHorisontal + indexVertical*4)+1), "id", "se.sandberg.ruzzleresolver");
			EditText characterEdit = (EditText) findViewById(editTextId);
			characterEdit.setBackgroundResource(R.drawable.yellow_rounded_edittext);
			characterEdit.setText(character);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
}

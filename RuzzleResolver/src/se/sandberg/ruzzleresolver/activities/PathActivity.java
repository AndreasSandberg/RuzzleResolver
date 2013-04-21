package se.sandberg.ruzzleresolver.activities;

import java.util.ArrayList;
import java.util.List;

import se.sandberg.ruzzleresolver.CharIndex;
import se.sandberg.ruzzleresolver.GamePlan;
import se.sandberg.ruzzleresolver.R;
import se.sandberg.ruzzleresolver.views.LineView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class PathActivity extends Activity {

	boolean drawCompleted = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path);

		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.pathLayout);
		//Set a pre draw listener which will be called when the layout pass is completed and the view is drawn
		relativeLayout.getViewTreeObserver().addOnPreDrawListener(
				new ViewTreeObserver.OnPreDrawListener() {
					
					@Override
					public boolean onPreDraw() {
						if(drawCompleted){
							return true;
						}
						@SuppressWarnings("unchecked")
						ArrayList<CharIndex> indexes = (ArrayList<CharIndex>) getIntent().getExtras().getSerializable(this.getClass().getPackage().toString()+".result");
						String[][] game = ((GamePlan) getIntent().getExtras().getParcelable(this.getClass().getPackage().toString()+".game")).getGame();

						//final float[] coordinates = new float [(indexes.size()-1)*4];
						List<Float> coordinates = new ArrayList<Float>();

						for(int i = 0; i < indexes.size(); i++){
							CharIndex charIndex = indexes.get(i);
							int indexHorisontal = charIndex.getIndexHorisontal();
							int indexVertical = charIndex.getIndexVertical();
							String character = game[indexVertical][indexHorisontal];

							int editTextId = getResources().getIdentifier("editText" + ((indexHorisontal + indexVertical*4)+1), "id", "se.sandberg.ruzzleresolver");
							EditText characterEdit = (EditText) findViewById(editTextId);
							characterEdit.setBackgroundResource(R.drawable.yellow_rounded_edittext);
							characterEdit.setText(character);

							//No need to extract coordinates when only one character is visible
							if(indexes.size() == 1){
								continue;
							}
							//Extract the coordinates for the edittext to draw lines between the characters.
							boolean firstOrLast = i == 0 || i == indexes.size() - 1;
							extractCoordinates(coordinates, characterEdit, firstOrLast);
						}

						//No need to add lines between characters if one or less characters to show.
						if(!coordinates.isEmpty()){
							RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.pathLayout);
							relativeLayout.addView(new LineView(getApplicationContext(), toArray(coordinates)));
						}
						
						drawCompleted = true;
						return true;
					}
				});
	}
	
	private void extractCoordinates(List<Float> coordinates, EditText characterEdit, boolean firstOrLast) {
		int[] editTextCoordinates = new int[2];
		characterEdit.getLocationOnScreen(editTextCoordinates);
		coordinates.add((float) editTextCoordinates[0] + characterEdit.getWidth() / 2);
		coordinates.add((float) editTextCoordinates[1]);

		//First and last character only needs one coordinate pair
		if(!firstOrLast){
			coordinates.add((float) editTextCoordinates[0] + characterEdit.getWidth() / 2);
			coordinates.add((float) editTextCoordinates[1]);
		}
	}

	private float[] toArray(List<Float> floats){
		float[] result = new float[floats.size()];
		for(int i = 0; i < floats.size(); i++){
			result[i] = floats.get(i);
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
}

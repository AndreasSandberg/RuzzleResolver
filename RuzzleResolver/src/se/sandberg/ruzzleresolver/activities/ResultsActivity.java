package se.sandberg.ruzzleresolver.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import se.sandberg.ruzzleresolver.CharIndex;
import se.sandberg.ruzzleresolver.GamePlan;
import se.sandberg.ruzzleresolver.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultsActivity extends ListActivity {

	private HashMap<String, ArrayList<CharIndex>> results;
	private List<String> wordsSorted = new ArrayList<String>();

	private GamePlan game;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		results = (HashMap<String, ArrayList<CharIndex>>) getIntent().getExtras().getSerializable(ResultsActivity.class.getPackage().toString()+".result");
		game = (GamePlan) getIntent().getExtras().getParcelable(ResultsActivity.class.getPackage().toString()+".game");


		setContentView(R.layout.activity_results);
		wordsSorted.addAll(results.keySet());
		Collections.sort(wordsSorted, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o2.length() - o1.length();
			}
		});
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordsSorted));
		setTitle("Antal ord: " + wordsSorted.size());

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, PathActivity.class);
		intent.putExtra(this.getClass().getPackage().toString()+".result", results.get(wordsSorted.get(position)));
		intent.putExtra(this.getClass().getPackage().toString()+".game", game);
		this.startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}	


}

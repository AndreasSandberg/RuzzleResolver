package se.sandberg.ruzzleresolver;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultsActivity extends ListActivity {

	private ArrayList<String> results;
	private String game;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		results = getIntent().getExtras().getStringArrayList(ResultsActivity.class.getPackage().toString()+".result");
		game = getIntent().getExtras().getString(ResultsActivity.class.getPackage().toString()+".game");
		setContentView(R.layout.activity_results);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
		setTitle("Antal ord: " + results.size());
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(this, PathActivity.class);
		intent.putExtra(PathActivity.class.getPackage().toString()+".result", results.get(position));
		intent.putExtra(PathActivity.class.getPackage().toString()+".game", game);
		this.startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}	
	
	
}

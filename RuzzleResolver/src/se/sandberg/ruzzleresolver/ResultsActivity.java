package se.sandberg.ruzzleresolver;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class ResultsActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<String> results = getIntent().getExtras().getStringArrayList(ResultsActivity.class.getPackage().toString()+".result");
		setContentView(R.layout.activity_results);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	
}

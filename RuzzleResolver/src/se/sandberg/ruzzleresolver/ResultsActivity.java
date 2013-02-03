package se.sandberg.ruzzleresolver;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class ResultsActivity extends ListActivity {

	private List<String> results;
	
	public ResultsActivity(List<String> results) {
		super();
		this.results = results;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
        setListAdapter(new ArrayAdapter<String>(this, 
             android.R.layout.simple_list_item_1, results));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_results, menu);
		return true;
	}

}

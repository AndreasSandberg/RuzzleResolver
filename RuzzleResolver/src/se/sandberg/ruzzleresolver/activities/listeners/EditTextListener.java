package se.sandberg.ruzzleresolver.activities.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextListener implements TextWatcher {
	private EditText toFocus;
	
	public EditTextListener(EditText toFocus) {
		this.toFocus = toFocus;
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Don't move is character was removed.
		if(s == null || s.length() == 0){
			return;
		}
		toFocus.requestFocus();
	}

	@Override
	public void afterTextChanged(Editable s) {}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
}
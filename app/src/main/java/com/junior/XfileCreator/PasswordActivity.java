package com.junior.XfileCreator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class PasswordActivity extends AppCompatActivity {
	EditText editText;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	Button button;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.password_layout);
		editText = findViewById(R.id.getPassEd);
		button = findViewById(R.id.savePassBtb);
		
		sharedPreferences = getSharedPreferences("sys", MODE_PRIVATE);
		editor = sharedPreferences.edit();
		editText.requestFocus();
		
		editText.setHint("Set you new Password here");
				
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				editor.putString("pass", editText.getText().toString());
				editor.apply();
				editText.clearFocus();
				Toast.makeText(getApplicationContext(),"Password saved",Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
	}
}
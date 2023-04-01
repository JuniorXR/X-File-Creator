package com.junior.XfileCreator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PasswordConfirmActivity extends AppCompatActivity {
	EditText editText;
	Button button;
	SharedPreferences sharedPreferences;
	int n;

	void toast(CharSequence cs) {
		Toast.makeText(getApplicationContext(), cs, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.password_layout);
		editText = findViewById(R.id.getPassEd);
		button = findViewById(R.id.savePassBtb);
		button.setText("confirm");

		n = getIntent().getIntExtra("data", 3);

		sharedPreferences = getSharedPreferences("sys", MODE_PRIVATE);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					if (editText.getText().toString().equals(sharedPreferences.getString("pass", null))) {
						if (n == 0) {
							load2();
						} else if (n == 1) {
							load1();
						} else {
							toast("Somthing went wrong…");
						}
					} else {
						Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	void load1() {
		Intent intent = new Intent(PasswordConfirmActivity.this, Output.class);
		intent.putExtra("dataFN", getIntent().getExtras().getString("dataFN"));
		startActivity(intent);
	}

	void load2() {
		startActivity(new Intent(PasswordConfirmActivity.this, PasswordActivity.class));
	}
}
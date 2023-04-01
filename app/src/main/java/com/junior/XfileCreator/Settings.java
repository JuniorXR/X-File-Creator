package com.junior.XfileCreator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;
import java.util.Collection;

public class Settings extends AppCompatActivity {
	void toast(String s) {
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}

	ListView listView;
	View mv;

	SharedPreferences my;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.setting_activity);
		listView = findViewById(R.id.settingListView);

		my = getSharedPreferences("sys", MODE_PRIVATE);
		SharedPreferences.Editor ed = my.edit();
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Theme");
		arrayList.add("Set Password");
		setTitle("Settings");

		//ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, myArray);
		listView.setAdapter(new CustomAdapter(getApplicationContext(), arrayList));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				switch (arg2) {
				case 0:
					new MaterialAlertDialogBuilder(Settings.this).setTitle("Theme")
							.setItems(R.array.theme_list, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									switch (arg1) {
									case 0:
										AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
										ed.putBoolean("theme_setting", false);
										ed.apply();
										break;
									case 1:
										AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
										ed.putBoolean("theme_setting", true);
										ed.apply();
										break;
									}
								}
							}).show();
					break;
				case 1:
					if (my.contains("pass")) {
						startActivity(new Intent(Settings.this, PasswordConfirmActivity.class).putExtra("data", 0));
		
					} else {
						Intent i = new Intent(Settings.this, PasswordActivity.class);
						startActivity(i);
					}
					break;
				}
			}
		});
	}
}
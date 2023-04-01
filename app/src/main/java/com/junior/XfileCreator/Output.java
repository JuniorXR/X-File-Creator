package com.junior.XfileCreator;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Locale;

public class Output extends AppCompatActivity {
	public void toast(CharSequence chars) {
		Toast.makeText(getApplicationContext(), chars, Toast.LENGTH_SHORT).show();
	}

	Toolbar toolbar;
	File file;
	Button button, btn;
	EditText editText;
	TextView tv;

	TextToSpeech tts;

	SharedPreferences my;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity2);
		toolbar = findViewById(R.id.toolbarid);
		tv = findViewById(R.id.title);
		button = findViewById(R.id.act2SaveBtn);
		btn = findViewById(R.id.rid);
		editText = findViewById(R.id.ed);
		setSupportActionBar(toolbar);
		editText.setTypeface(Typeface.DEFAULT_BOLD);
		tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int arg0) {
				if (arg0 != TextToSpeech.ERROR) {
					tts.setLanguage(Locale.US);
				}
			}
		});

		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} catch (Exception e) {
			finish();
		}

		file = new File(getIntent().getExtras().getString("dataFN"));
		editText.setText(TextEncryption.Decrypt(FileHelp.readFile(file)));
		editText.setHorizontallyScrolling(true);
		tv.setText(file.getName().replace(".data", ""));
		//	tv.setSelected(true);
		my = getSharedPreferences("sys", MODE_PRIVATE);
		if (my.getBoolean("theme_setting", false)) {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		}

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					Files.deleteIfExists(file.toPath());
					FileWriter fileWriter = new FileWriter(file, true);
					fileWriter.append(TextEncryption.Encrypt(editText.getText().toString()));
					fileWriter.close();
					toast("file content has updated!");
				} catch (Exception e) {
					toast("file cannot be modify!");
				}
			}
		});
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!editText.getText().toString().isEmpty()) {
					tts.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
				} else {
					tts.speak("Nothing found to read…", TextToSpeech.QUEUE_FLUSH, null);
				}
			}
		});

	}
}
package com.junior.XfileCreator;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.ViewCompat;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.junior.XfileCreator.FileHelp;
import com.junior.XfileCreator.FileList;
import com.junior.XfileCreator.Output;
import com.junior.XfileCreator.TextEncryption;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.PermissionRequest;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
	public void toast(CharSequence chars) {
		Toast.makeText(getApplicationContext(), chars, Toast.LENGTH_SHORT).show();
	}

	String nameOfFile, FILE_PATH;
	File file;
	FileWriter fileWriter;
	File myDir;

	SharedPreferences my;

	TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		Dexter.withContext(MainActivity.this)
				.withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
				.withListener(new MultiplePermissionsListener() {
					@Override
					public void onPermissionsChecked(MultiplePermissionsReport arg0) {

						EditText editText = (EditText) findViewById(R.id.editText);
						EditText fileName = (EditText) findViewById(R.id.getNameOfFile);
						
						Button button = (Button) findViewById(R.id.createBtn);
						Button button1 = (Button) findViewById(R.id.outPutBtn);
						Button button2 = (Button) findViewById(R.id.filesViewId);
						Button button3 = (Button) findViewById(R.id.readItId);
						myDir = getExternalCacheDir();
						editText.setHorizontallyScrolling(true);
						fileName.setHorizontallyScrolling(true);
						editText.setHorizontalScrollBarEnabled(true);
						editText.setTypeface(Typeface.DEFAULT_BOLD);
						tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
							@Override
							public void onInit(int arg0) {
								if (arg0 != TextToSpeech.ERROR) {
									tts.setLanguage(Locale.US);
								}
							}
						});

						my = getSharedPreferences("sys", MODE_PRIVATE);

						if (my.getBoolean("theme_setting", false)) {
							setTheme();
						} else {
							getWindow().setStatusBarColor(R.color.white);
							fileName.setBackground(getResources().getDrawable(R.drawable.ic_edit_text_black));
						}
						fileName.requestFocus();
						button2.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								Intent intent = new Intent(MainActivity.this, FileList.class);
								hideIMM(true, editText);
								startActivity(intent);
							}
						});
						button1.setVisibility(View.INVISIBLE);

						button.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								try {
									nameOfFile = fileName.getText().toString();
									if (nameOfFile.equals("/s")) {
										hideIMM(true, fileName);
										startActivity(new Intent(MainActivity.this, Settings.class));

									} else {
										if (nameOfFile.isEmpty()) {
											fileName.requestFocus();
											InputMethodManager imm = (InputMethodManager) getSystemService(
													INPUT_METHOD_SERVICE);
											imm.showSoftInput(fileName, InputMethodManager.SHOW_FORCED);
										} else {
											InputMethodManager imm = (InputMethodManager) getSystemService(
													INPUT_METHOD_SERVICE);
											imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
											for (File n : getExternalCacheDir().listFiles()) {
												if (n.getName().equals(nameOfFile)) {
													n.delete();
												}
											}
											file = new File(myDir.getPath() + "/" + nameOfFile + ".data");
											FILE_PATH = file.getPath();
											if (file.exists()) {
												try {
													Files.deleteIfExists(file.toPath());
												} catch (Exception e) {
												}
												FileWriter fw = new FileWriter(file, true);
												fw.append(TextEncryption.Encrypt(editText.getText().toString()));
												fw.close();
												toast("file content has updated…");
												button1.setVisibility(View.VISIBLE);
											} else {
												try {
													Files.deleteIfExists(file.toPath());
													fileWriter = new FileWriter(file, true);
													fileWriter.write(
															TextEncryption.Encrypt(editText.getText().toString()));
													fileWriter.close();
													toast(nameOfFile + " saved");
													button1.setVisibility(View.VISIBLE);
												} catch (Exception e) {
												}
											}
										}
									}
								} catch (Exception e) {
									if (fileName.getText().toString().isEmpty()) {
										toast("File name is empty!");

										InputMethodManager imm = (InputMethodManager) getSystemService(
												INPUT_METHOD_SERVICE);
										imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
									} else {
										e.printStackTrace();
										Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT)
												.show();
									}
								}
							}
						});
						button1.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								try {
									if (!FILE_PATH.isEmpty()) {
										Intent intent = new Intent(MainActivity.this, Output.class);
										intent.putExtra("dataFN", FILE_PATH);
										startActivity(intent);
									} else {
										toast("File path not found!");
									}
								} catch (Exception e) {
									toast(e.toString());
								}
							}
						});
						button3.setOnClickListener(new View.OnClickListener() {
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

					@Override
					public void onPermissionRationaleShouldBeShown(List<PermissionRequest> arg0, PermissionToken arg1) {
						arg1.continuePermissionRequest();
					}
				}).check();
	}

	void setTheme() {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
	}

	void hideIMM(boolean b, View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		if (b) {
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			v.clearFocus();
		}
	}
}
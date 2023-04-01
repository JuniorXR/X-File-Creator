package com.junior.XfileCreator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileList extends AppCompatActivity {
	ListView listView;
	ArrayList<File> fileArrayList;
	private String ftod;

	SharedPreferences my;
	RewardedAd rewardedAd;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.file_list);
		listView = findViewById(R.id.listViewId);
		setTitle("Files");

		MobileAds.initialize(FileList.this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
				RewardedAd.load(getApplicationContext(), "ca-app-pub-5623140250050351/2948186216",
						new AdRequest.Builder().build(), new RewardedAdLoadCallback() {

							@Override
							public void onAdLoaded(RewardedAd arg0) {
								super.onAdLoaded(arg0);
								arg0.setFullScreenContentCallback(new FullScreenContentCallback() {

									@Override
									public void onAdShowedFullScreenContent() {
										super.onAdShowedFullScreenContent();
									}

									@Override
									public void onAdImpression() {
										super.onAdImpression();
									}

									@Override
									public void onAdFailedToShowFullScreenContent(AdError arg0) {
										super.onAdFailedToShowFullScreenContent(arg0);
									}

									@Override
									public void onAdDismissedFullScreenContent() {
										super.onAdDismissedFullScreenContent();
									}

									@Override
									public void onAdClicked() {
										super.onAdClicked();
									}
								});
								arg0.show(FileList.this, new OnUserEarnedRewardListener() {
									@Override
									public void onUserEarnedReward(RewardItem arg0) {
									}
								});
							}

							@Override
							public void onAdFailedToLoad(LoadAdError arg0) {
								super.onAdFailedToLoad(arg0);
								//Toast.makeText(getApplicationContext(), arg0.toString(), Toast.LENGTH_SHORT).show();
							}
						});
			}
		});

		my = getSharedPreferences("sys", MODE_PRIVATE);
		if (my.getBoolean("theme_setting", false)) {
			setTheme();
		}

		fileArrayList = FileHelp.listOfFile(getExternalCacheDir());
		/*	ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
					FileHelp.getSystemFileNames(FileHelp.listOfFile(getExternalCacheDir())));*/
		listView.setAdapter(new CustomAdapter(getApplicationContext(),
				FileHelp.getSystemFileNames(FileHelp.listOfFile(getExternalCacheDir()))));

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (my.contains("pass")) {
					Intent intent = new Intent(FileList.this, PasswordConfirmActivity.class);
					intent.putExtra("data", 1);
					intent.putExtra("dataFN", fileArrayList.get(arg2).toString());
					startActivity(intent);
				} else {
					startActivity(new Intent(FileList.this, PasswordActivity.class));
				}
			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				AlertDialog.Builder builder = new AlertDialog.Builder(FileList.this);
				builder.setTitle("Think Again…!");
				builder.setMessage("Do yout want to delete " + fileArrayList.get(arg2).getName() + " file?");
				builder.setCancelable(true);
				builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.cancel();
						arg0.dismiss();
					}
				});
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						ftod = fileArrayList.get(arg2).getName();
						try {
							Files.deleteIfExists(fileArrayList.get(arg2).toPath());
							listView.setAdapter(new CustomAdapter(getApplicationContext(),
									FileHelp.getSystemFileNames(FileHelp.listOfFile(getExternalCacheDir()))));
							fileArrayList = FileHelp.listOfFile(getExternalCacheDir());
							Toast.makeText(getApplicationContext(), ftod + " is Deleted.", Toast.LENGTH_SHORT).show();
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "File not exixts", Toast.LENGTH_SHORT).show();
						}
					}
				});
				builder.create().show();
				return true;
			}
		});
	}

	void setTheme() {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
	}
}
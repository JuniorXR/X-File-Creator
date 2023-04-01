package com.junior.XfileCreator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileHelp {
	
	private ArrayList<File> fileArrayList;
	
	public static ArrayList<File> listOfFile(File file){
		ArrayList<File> arrayList = new ArrayList<>();
		for(File f:file.listFiles()){
			if(f.isDirectory()&&!f.isHidden()){
				arrayList.addAll(listOfFile(f));
			}else{
				if(f.getName().endsWith(".data")&&f.isFile())
				arrayList.add(f);
			}
		}
		return arrayList;
	}
	
	public static ArrayList<String> getSystemFileNames(ArrayList<File> arrayList) {
		ArrayList<String> fileArrayList = new ArrayList<>();
		for(File f:arrayList){
			fileArrayList.add(f.getName().replace(".data",""));
		}
		return fileArrayList;
	}
	
	
	public static String readFile(File f) {
		try {
			return new String(Files.readAllBytes(f.toPath()));
		} catch (Exception e) {
			return String.valueOf("Exception :"+e.toString());
		}
	}
}
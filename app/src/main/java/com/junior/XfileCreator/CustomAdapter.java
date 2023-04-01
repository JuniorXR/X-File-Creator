package com.junior.XfileCreator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private Context mContext;
	private String[] strs;
	private ArrayList<String> arrayList = new ArrayList<String>();
	TextView textView;

	CustomAdapter(Context c, ArrayList<String> arg1) {
		this.arrayList.addAll(arg1);
		this.mContext = c;
	}

	CustomAdapter(Context c, String[] ss) {
		this.mContext = c;
		this.strs = ss;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	public int getStringCound() {
		return strs.length;
	}

	@Override
	public String getItem(int position) {
		return arrayList.get(position);
	}

	public String getStringItem(int position) {
		return strs[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		convertView = layoutInflater.inflate(R.layout.custom_text_view, parent, false);
		textView = convertView.findViewById(R.id.custom_simple_list_item_1);
		if (strs!=null) {
			textView.setText(strs[position]);
		} else {
			textView.setText(getItem(position));
		}
		return convertView;
	}
}
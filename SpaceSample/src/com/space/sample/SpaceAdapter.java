package com.space.sample;

import java.util.ArrayList;

import com.example.spacesample.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpaceAdapter extends ArrayAdapter<SpaceData> {

	Context context;
	public SpaceAdapter(Context ctx, int resourceId, ArrayList<SpaceData> data) {
		super(ctx, resourceId, data);
		context = ctx;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		if(row == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.single_row, null);
		}
		
		TextView titleTextView = (TextView) row.findViewById(R.id.spacedata_title_textview);
		TextView textTextView = (TextView) row.findViewById(R.id.spacedata_text_textview);;
		TextView dateCreatedTextView = (TextView) row.findViewById(R.id.spacedata_datecreated_textview);
		
		titleTextView.setText(getItem(position).getTitle());
		textTextView.setText(getItem(position).getText());
		dateCreatedTextView.setText(getItem(position).getCreatedDate().toString());
		
		return row;
	}
}

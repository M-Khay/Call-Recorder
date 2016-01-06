package com.premium.callrecorderproplus;



import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter  {
String [] dlist;
int playPause;
Context context;
public static int pos;
static int positionForCallLog;
public MyAdapter(String [] dlist ,int playPause,  Context context) {
	// TODO Auto-generated constructor stub
	this.dlist=dlist;
	this.playPause=playPause;
	this.context = context;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		try{
			Log.e("length of list","hg"+dlist.length);
		
		return dlist.length;
		}catch(NullPointerException n)
		{
			Log.e("List Length", "List Is EMpty");
			
		}
		return 0;
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	View view = convertView;
	ViewHolder holder= new ViewHolder();
	pos=position;
	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	//if(view == null)
	{
	view = inflater.inflate(R.layout.custom_list, null);
	holder.nameNumber = (TextView) view.findViewById(R.id.name_number);
	holder.nameNumber.setText(dlist[position]);
	holder.nameNumber.setTag(position);
	holder.nameNumber.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			int pos=Integer.parseInt(v.getTag().toString());
			// TODO Auto-generated method stub
			/*File f = new File(RecordService.DEFAULT_STORAGE_LOCATION + "/"
					+ dlist[pos].toString());
			//if(CallLog.actionBarThread.is)
			Log.e("Name of file is"," "+f.getName());
			if (f != null) {
				Intent viewIntent = new Intent(Intent.ACTION_VIEW);
				viewIntent.setDataAndType(Uri.fromFile(f), "audio/*");
				context.startActivity(Intent.createChooser(viewIntent, null));	
				Log.e("list is ","sdfa"+v.getTag().toString());
			}
			*/
			Intent i = new Intent(context,SelectorActivity.class);
			i.putExtra("list",dlist);
			
			i.putExtra("list_position", Integer.parseInt(v.getTag().toString()));
			context.startActivity(i);
		}
	});

	
	Log.e("list is ",dlist[position]);
	holder.playPause = (ImageButton) view.findViewById(R.id.play_pause);
	holder.playPause.setTag(position);
	holder.playPause.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.e("list is ","sdfa");
			// TODO Auto-generated method stub
			int pos=Integer.parseInt(v.getTag().toString());
			File f = new File(RecordService.DEFAULT_STORAGE_LOCATION + "/"
					+ dlist[pos].toString());
	
			
			if (f != null) {
				Intent viewIntent = new Intent(Intent.ACTION_VIEW);
				viewIntent.setDataAndType(Uri.fromFile(f), "audio/*");
				context.startActivity(Intent.createChooser(viewIntent, null));	
				
				
		}
		}
	});
	holder.playPause.setImageResource(playPause);
		view.setTag(position);
		
	}
	/*else{
		//holder = view.getTag();
	}*/
	return view;

	}
class ViewHolder
{
	TextView nameNumber;
	ImageButton playPause;
	CheckBox cb;
}


}

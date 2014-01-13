package com.example.project;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BoxAdapter extends BaseAdapter {
	  Context ctx;
	  LayoutInflater lInflater;
	  ArrayList<Alarm> objects;

	  BoxAdapter(Context context, ArrayList<Alarm> products) {
	    ctx = context;
	    objects = products;
	    lInflater = (LayoutInflater) ctx
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  }
	  
	// ���-�� ���������
	  @Override
	  public int getCount() {
	    return objects.size();
	  }
	  
	  // ������� �� �������
	  @Override
	  public Object getItem(int position) {
	    return objects.get(position);
	  }

	  // id �� �������
	  @Override
	  public long getItemId(int position) {
	    return position;
	  }
	  
	// ����� ������
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    // ���������� ���������, �� �� ������������ view
	    View view = convertView;
	    if (view == null) {
	      view = lInflater.inflate(R.layout.item, parent, false);
	    }

	    Alarm a = getAlarm(position);

	    // ��������� View � ������ ������ ������� �� �������: ������������, ����
	    // � ��������
	    //((TextView) view.findViewById(R.id.tvDescr)).setText(a.name);
	    ((TextView) view.findViewById(R.id.tvAlarmTime)).setText(a.hour + ":" + a.minute);
	    ((TextView) view.findViewById(R.id.tvDaysOfWeek)).setText(a.days + "");

	    CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
	    // ����������� �������� ����������
	    cbBuy.setOnCheckedChangeListener(myCheckChangList);
	    // ����� �������
	    cbBuy.setTag(position);
	    // ��������� ������� �� �������: � ������� ��� ���
	    cbBuy.setChecked(a.box);
	    return view;
	  }
	  
	  // ��������� �� �������
	  Alarm getAlarm(int position) {
	    return ((Alarm) getItem(position));
	  }
	  
	  // ������ ���������� �����������
	  ArrayList<Alarm> getBox() {
	    ArrayList<Alarm> box = new ArrayList<Alarm>();
	    for (Alarm a : objects) {
	      // ���� ��������� ������� ��������
	      if (a.box)
	        box.add(a);
	    }
	    return box;
	  }
	  
	  // ���������� ��� ���������
	  OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
	    public void onCheckedChanged(CompoundButton buttonView,
	        boolean isChecked) {
	      // ������ ������ ���������� (� ������� ��� ���)
	    	getAlarm((Integer) buttonView.getTag()).box = isChecked;
	    }
	  };
	  
}
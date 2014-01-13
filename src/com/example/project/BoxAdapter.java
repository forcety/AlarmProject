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
	  
	// кол-во элементов
	  @Override
	  public int getCount() {
	    return objects.size();
	  }
	  
	  // элемент по позиции
	  @Override
	  public Object getItem(int position) {
	    return objects.get(position);
	  }

	  // id по позиции
	  @Override
	  public long getItemId(int position) {
	    return position;
	  }
	  
	// пункт списка
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    // используем созданные, но не используемые view
	    View view = convertView;
	    if (view == null) {
	      view = lInflater.inflate(R.layout.item, parent, false);
	    }

	    Alarm a = getAlarm(position);

	    // заполняем View в пункте списка данными из товаров: наименование, цена
	    // и картинка
	    //((TextView) view.findViewById(R.id.tvDescr)).setText(a.name);
	    ((TextView) view.findViewById(R.id.tvAlarmTime)).setText(a.hour + ":" + a.minute);
	    ((TextView) view.findViewById(R.id.tvDaysOfWeek)).setText(a.days + "");

	    CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
	    // присваиваем чекбоксу обработчик
	    cbBuy.setOnCheckedChangeListener(myCheckChangList);
	    // пишем позицию
	    cbBuy.setTag(position);
	    // заполняем данными из товаров: в корзине или нет
	    cbBuy.setChecked(a.box);
	    return view;
	  }
	  
	  // будильник по позиции
	  Alarm getAlarm(int position) {
	    return ((Alarm) getItem(position));
	  }
	  
	  // список отмеченных будильников
	  ArrayList<Alarm> getBox() {
	    ArrayList<Alarm> box = new ArrayList<Alarm>();
	    for (Alarm a : objects) {
	      // если будильник отмечен активным
	      if (a.box)
	        box.add(a);
	    }
	    return box;
	  }
	  
	  // обработчик для чекбоксов
	  OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
	    public void onCheckedChanged(CompoundButton buttonView,
	        boolean isChecked) {
	      // меняем данные будильника (в активен или нет)
	    	getAlarm((Integer) buttonView.getTag()).box = isChecked;
	    }
	  };
	  
}
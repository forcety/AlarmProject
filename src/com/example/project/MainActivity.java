package com.example.project;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	 ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	 BoxAdapter boxAdapter;
	 Button btnAddAlarm;
	 
	 private static final int CM_DELETE_ID = 1;  // дл€ контекстного меню пункт удалить
	 private static final int CM_UPDATE_ID = 2;  // дл€ контекстного меню пункт изменить
	 
	// чтобы пон€ть как перешли в форму AlarmActivity
	 public static Boolean isAddClicked = false;  // перешли по нажатию кнопки ƒобавить будильник
	 public static Boolean isUpdateClicked = false; // перешли по нажатию »зменить будильник
	 
	 public static int position = 0;  // позици€ выбранного пункта меню

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		btnAddAlarm.setOnClickListener(this);
		
		// создаем адаптер
	   // fillData();
	    boxAdapter = new BoxAdapter(this, alarms);
	    
	    // настраиваем список
	    ListView lvMain = (ListView) findViewById(R.id.lvMain);
	    lvMain.setAdapter(boxAdapter);
	    
	    
	    registerForContextMenu(lvMain);	 
   
	    // уведомл€ем, что данные изменились
	    boxAdapter.notifyDataSetChanged();
	    
	}
	
	  @Override
	  protected void onResume() {
	    super.onResume();
	    Log.d("TAG", "MainActivity: onResume()");
	    MainActivity.isUpdateClicked = false;  // сбрасываем значение глоб переменной
	  }
	  
	  
	  @Override
	  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		  
		  //super.onCreateContextMenu(menu, v, menuInfo);
		  
		  AdapterContextMenuInfo aMenuInfo = (AdapterContextMenuInfo) menuInfo;
		  
		  // ѕолучаем позицию элемента в списке
		  position = aMenuInfo.position;
			
		  // ѕолучаем данные элемента списка
		  Alarm data =(Alarm)boxAdapter.getItem(aMenuInfo.position);
			
		  
		  menu.setHeaderTitle(String.format("%02d:%02d", data.hour, data.minute));
		  menu.add(0, CM_DELETE_ID, 0, "”далить запись");
		  menu.add(0, CM_UPDATE_ID, 1, "»зменить запись");
	  }
	  
	  @Override
	  public boolean onContextItemSelected(MenuItem item) {
		  
	    if (item.getItemId() == CM_DELETE_ID) {
	      // получаем инфу о пункте списка
	      AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
	      // удал€ем Map из коллекции, использу€ позицию пункта в списке
	      alarms.remove(acmi.position);
	      // уведомл€ем, что данные изменились
	      boxAdapter.notifyDataSetChanged();
	      return true;
	    }
	    
	    if (item.getItemId() == CM_UPDATE_ID) {
	    	isUpdateClicked = true;
		    // получаем инфу о пункте списка
		    AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
		    
		    Alarm data =(Alarm)boxAdapter.getItem(acmi.position);
		    
		    Intent intent = new Intent(this, AlarmActivity.class); 
		   	    
		    
		    // передаем все пол€ в форму AlarmActivity
		    intent.putExtra("name", data.name);
		    intent.putExtra("hour", data.hour);
		    intent.putExtra("minute", data.minute);
		    intent.putExtra("days", data.days);
		    intent.putExtra("box", data.box);
			  
		    //startActivity(intent);
		    
		    //Intent intent2 = new Intent(this, AlarmActivity.class);
		    startActivityForResult(intent, 2);
		    
		    return true;
	    }
	    return super.onContextItemSelected(item);
	  }
	  
	  
	@Override
	// Ќажали добавить будильник
	  public void onClick(View v) {

	    Intent intent = new Intent(this, AlarmActivity.class);
	    startActivityForResult(intent, 1);
	  }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Toast.makeText(this, "requestCode = " + requestCode, Toast.LENGTH_SHORT).show();
		
		 // запишем в лог значени€ requestCode и resultCode
	    Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
	    
	    if (data == null) {return;}
	    
	 // если пришло ќ 
	    if (resultCode == RESULT_OK) {
	    	
	    	// добавление будильника
	    	if (requestCode == 1) {
			    String alarmName = data.getStringExtra("alarmName");
			    String alarmTime = data.getStringExtra("alarmTime");
			    String alarmDays = data.getStringExtra("alarmDays");
			    
		
			    String strHour = alarmTime.substring(0,2);  // часы
			    String strMinute = alarmTime.substring(3,5);  // минуты
			    
			    if (alarmDays.equals("Ќе выбрано"))
			    	alarms.add(new Alarm(alarmName, Integer.parseInt(strHour), Integer.parseInt(strMinute), "", true));
			    else
			    	alarms.add(new Alarm(alarmName, Integer.parseInt(strHour), Integer.parseInt(strMinute), alarmDays, true));
			    
			    // уведомл€ем, что данные изменились
			    boxAdapter.notifyDataSetChanged();
	    	}
	    	// изменение будильника
	    	if (requestCode == 2) {
			    String alarmName = data.getStringExtra("alarmName");
			    String alarmTime = data.getStringExtra("alarmTime");
			    String alarmDays = data.getStringExtra("alarmDays");
			    
		
			    String strHour = alarmTime.substring(0,2);  // часы
			    String strMinute = alarmTime.substring(3,5);  // минуты
			    
			    Toast.makeText(this, "alarms.size() = " + alarms.size(), Toast.LENGTH_SHORT).show();
			    
			    Alarm alarm = alarms.get(position);  // просто инициируем любыми данными
			    alarm.name = alarmName;
			    alarm.hour = Integer.parseInt(strHour);
			    alarm.minute = Integer.parseInt(strMinute);
			    alarm.days = alarmDays;
			    alarm.box = true;
			    
			    alarms.set(position, alarm);  // заменим старую запись на новую
			    
			    // уведомл€ем, что данные изменились
			    boxAdapter.notifyDataSetChanged();
	    	}
	    
		    // если вернулось не ќ 
	    } else {
	      Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
	    }

	  }
	
	// генерируем данные дл€ адаптера
	void fillData() {
		alarms.add(new Alarm("Ѕудильник 1", 18, 32, "пн, вт", true));  
		alarms.add(new Alarm("Ѕудильник 2", 10, 8, "вс", true));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

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
	 
	 private static final int CM_DELETE_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		btnAddAlarm.setOnClickListener(this);
		
		// создаем адаптер
	    fillData();
	    boxAdapter = new BoxAdapter(this, alarms);
	    
	    // настраиваем список
	    ListView lvMain = (ListView) findViewById(R.id.lvMain);
	    lvMain.setAdapter(boxAdapter);
	}
/* наработки по контекстному меню
	  @Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
	      ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, CM_DELETE_ID, 0, "”далить запись");
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
	    return super.onContextItemSelected(item);
	  }
	  
*/	  
	@Override
	// Ќажали добавить будильник
	  public void onClick(View v) {
	    // выводим сообщение
	    Toast.makeText(this, "«ачем вы нажали добавить будильник?", Toast.LENGTH_SHORT).show(); 
	    
	    Intent intent = new Intent(this, AlarmActivity.class);
	    startActivityForResult(intent, 1);
	  }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (data == null) {return;}
	    String alarmName = data.getStringExtra("alarmName");
	    String alarmTime = data.getStringExtra("alarmTime");
	    String alarmDays = data.getStringExtra("alarmDays");
	    

	    String strHour = alarmTime.substring(0,2);  // часы
	    String strMinute = alarmTime.substring(3,5);  // минуты
	    
	    alarms.add(new Alarm(alarmName, Integer.parseInt(strHour), Integer.parseInt(strMinute), "пн, вт", true));
	    
	    
	    // уведомл€ем, что данные изменились
	    boxAdapter.notifyDataSetChanged();
	    
	    /* старое обновление ListView
	    // уведомл€ем, что данные изменились
	    boxAdapter = new BoxAdapter(this, alarms);
	    
	    // настраиваем список
	    ListView lvMain = (ListView) findViewById(R.id.lvMain);
	    lvMain.setAdapter(boxAdapter);
	    */
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

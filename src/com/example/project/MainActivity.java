package com.example.project;

import java.util.ArrayList;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	 ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	 BoxAdapter boxAdapter;
	 Button btnAddAlarm;

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

	
	@Override
	  public void onClick(View v) {
	    // выводим сообщение
	    Toast.makeText(this, "Зачем вы нажали?", Toast.LENGTH_SHORT).show(); 
	    
	    Intent intent = new Intent(this, AlarmActivity.class);
	    startActivityForResult(intent, 1);
	  }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (data == null) {return;}
	    String name = data.getStringExtra("name");
	   // tvName.setText("Your name is " + name);
	  }
	
	// генерируем данные для адаптера
	void fillData() {
		alarms.add(new Alarm("Будильник 1", 18, 32, "пн, вт", true));  
		alarms.add(new Alarm("Будильник 2", 10, 18, "вс", true));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

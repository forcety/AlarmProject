package com.example.project;

import java.util.ArrayList;
import java.util.Calendar;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
	
	 public static ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	 BoxAdapter boxAdapter;
	 Button btnAddAlarm;
	 
	 private static final int CM_DELETE_ID = 1;  // ��� ������������ ���� ����� �������
	 private static final int CM_UPDATE_ID = 2;  // ��� ������������ ���� ����� ��������
	 
	// ����� ������ ��� ������� � ����� AlarmActivity
	 public static Boolean isAddClicked = false;  // ������� �� ������� ������ �������� ���������
	 public static Boolean isUpdateClicked = false; // ������� �� ������� �������� ���������
	 
	 public static int position = 0;  // ������� ���������� ������ ����
	 
	 // ��� ������ ����������
	 public static NotificationManager nm;
	 public static AlarmManager am;
	 public static Intent intent1;
	 public static Intent intent2;
	 public static PendingIntent pIntent1;
	 public static PendingIntent pIntent2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		btnAddAlarm.setOnClickListener(this);
		
		// ������� �������
	   // fillData();
	    boxAdapter = new BoxAdapter(this, alarms);
	    
	    // ����������� ������
	    ListView lvMain = (ListView) findViewById(R.id.lvMain);
	    lvMain.setAdapter(boxAdapter);
	    
	    
	    registerForContextMenu(lvMain);	 
   
	    // ����������, ��� ������ ����������
	    boxAdapter.notifyDataSetChanged();
	    
	    // ��� ������ ����������
	    nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    am = (AlarmManager) getSystemService(ALARM_SERVICE);
	    
	    intent1 = createIntent("action 1", "extra 1");
	    pIntent1 = PendingIntent.getBroadcast(this, 0, intent1, 0);

	    intent2 = createIntent("action 2", "extra 2");
	    pIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0);
	}
	
	  @Override
	  protected void onResume() {
	    super.onResume();
	    Log.d("TAG", "MainActivity: onResume()");
	    MainActivity.isUpdateClicked = false;  // ���������� �������� ���� ����������
	  }
	  
	  
	  
	  Intent createIntent(String action, String extra) {
		    Intent intent = new Intent(this, Receiver.class);
		    intent.setAction(action);
		    intent.putExtra("extra", extra);
		    return intent;
		  }
	  
	  
	  @Override
	  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		  
		  //super.onCreateContextMenu(menu, v, menuInfo);
		  
		  AdapterContextMenuInfo aMenuInfo = (AdapterContextMenuInfo) menuInfo;
		  
		  // �������� ������� �������� � ������
		  position = aMenuInfo.position;
			
		  // �������� ������ �������� ������
		  Alarm data =(Alarm)boxAdapter.getItem(aMenuInfo.position);
			
		  
		  menu.setHeaderTitle(String.format("%02d:%02d", data.hour, data.minute));
		  menu.add(0, CM_DELETE_ID, 0, "������� ������");
		  menu.add(0, CM_UPDATE_ID, 1, "�������� ������");
	  }
	  
	  @Override
	  public boolean onContextItemSelected(MenuItem item) {
		  
	    if (item.getItemId() == CM_DELETE_ID) {
	      // �������� ���� � ������ ������
	      AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
	      // ������� Map �� ���������, ��������� ������� ������ � ������
	      alarms.remove(acmi.position);
	      // ����������, ��� ������ ����������
	      boxAdapter.notifyDataSetChanged();
	      return true;
	    }
	    
	    if (item.getItemId() == CM_UPDATE_ID) {
	    	isUpdateClicked = true;
		    // �������� ���� � ������ ������
		    AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
		    
		    Alarm data =(Alarm)boxAdapter.getItem(acmi.position);
		    
		    Intent intent = new Intent(this, AlarmActivity.class); 
		   	    
		    
		    // �������� ��� ���� � ����� AlarmActivity
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
	// ������ �������� ���������
	  public void onClick(View v) {

	    Intent intent = new Intent(this, AlarmActivity.class);
	    startActivityForResult(intent, 1);
	  }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Toast.makeText(this, "requestCode = " + requestCode, Toast.LENGTH_SHORT).show();
		
		 // ������� � ��� �������� requestCode � resultCode
	    Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
	    
	    if (data == null) {return;}
	    
	 // ���� ������ ��
	    if (resultCode == RESULT_OK) {
	    	
	    	// ���������� ����������
	    	if (requestCode == 1) {
			    String alarmName = data.getStringExtra("alarmName");
			    String alarmTime = data.getStringExtra("alarmTime");
			    String alarmDays = data.getStringExtra("alarmDays");
			    
		
			    String strHour = alarmTime.substring(0,2);  // ����
			    String strMinute = alarmTime.substring(3,5);  // ������
			    
			    if (alarmDays.equals("�� �������"))
			    	alarms.add(new Alarm(alarmName, Integer.parseInt(strHour), Integer.parseInt(strMinute), "", true));
			    else
			    	alarms.add(new Alarm(alarmName, Integer.parseInt(strHour), Integer.parseInt(strMinute), alarmDays, true));
			    
			    // ����������, ��� ������ ����������
			    boxAdapter.notifyDataSetChanged();
			    
			    Log.d("Log", "start");
			    am.set(AlarmManager.RTC, System.currentTimeMillis() + 12000, pIntent1);
			    
			    
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strHour));
				calendar.set(Calendar.MINUTE, Integer.parseInt(strMinute));
				calendar.set(Calendar.SECOND, 0);
				
				
				
				//    am.setRepeating(AlarmManager.ELAPSED_REALTIME,
				//       SystemClock.elapsedRealtime() + 8000, 10000, pIntent2);
				
			    am.setRepeating(AlarmManager.RTC_WAKEUP, 
			    	    calendar.getTimeInMillis(), 10000, pIntent2);
	    	}
	    	// ��������� ����������
	    	if (requestCode == 2) {
			    String alarmName = data.getStringExtra("alarmName");
			    String alarmTime = data.getStringExtra("alarmTime");
			    String alarmDays = data.getStringExtra("alarmDays");
			    
		
			    String strHour = alarmTime.substring(0,2);  // ����
			    String strMinute = alarmTime.substring(3,5);  // ������
			    
			    Toast.makeText(this, "alarms.size() = " + alarms.size(), Toast.LENGTH_SHORT).show();
			    
			    Alarm alarm = alarms.get(position);  // ������ ���������� ������ �������
			    alarm.name = alarmName;
			    alarm.hour = Integer.parseInt(strHour);
			    alarm.minute = Integer.parseInt(strMinute);
			    alarm.days = alarmDays;
			    alarm.box = true;
			    
			    alarms.set(position, alarm);  // ������� ������ ������ �� �����
			    
			    // ����������, ��� ������ ����������
			    boxAdapter.notifyDataSetChanged();
	    	}
	    
		    // ���� ��������� �� ��
	    } else {
	      Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
	    }

	  }
	
	// ���������� ������ ��� ��������
	void fillData() {
		alarms.add(new Alarm("��������� 1", 18, 32, "��, ��", true));  
		alarms.add(new Alarm("��������� 2", 10, 8, "��", true));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

package com.example.project;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	
	 ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	 BoxAdapter boxAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// ������� �������
	    fillData();
	    boxAdapter = new BoxAdapter(this, alarms);
	    
	    // ����������� ������
	    ListView lvMain = (ListView) findViewById(R.id.lvMain);
	    lvMain.setAdapter(boxAdapter);
	}
	
	  // ���������� ������ ��� ��������
	  void fillData() {
		 alarms.add(new Alarm("��������� 1", 18, 32, "��, ��", true));  
		 alarms.add(new Alarm("��������� 2", 10, 18, "��", true));
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

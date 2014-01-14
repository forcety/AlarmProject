package com.example.project;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlarmActivity extends Activity implements OnClickListener {

	  EditText etAlarmName;
	  TextView tvAlarmTime;
	  TextView tvAlarmDays;
	  Button btnOK;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.alarm);
	    
	    etAlarmName = (EditText) findViewById(R.id.etAlarmName);
	    tvAlarmTime  = (TextView) findViewById(R.id.tvAlarmTime);
	    tvAlarmDays = (TextView) findViewById(R.id.tvAlarmDays);
	    
	    btnOK = (Button) findViewById(R.id.btnOK);
	    btnOK.setOnClickListener(this);
	  }

	  @Override
	  public void onClick(View v) {
	    Intent intent = new Intent();
	    //intent.putExtra("name", etName.getText().toString());
	    setResult(RESULT_OK, intent);
	    finish();
	  }

}

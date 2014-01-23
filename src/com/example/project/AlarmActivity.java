package com.example.project;


import java.util.Calendar;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmActivity extends Activity implements OnClickListener {

	  EditText etAlarmName;
	  TextView tvAlarmTime;
	  TextView tvAlarmDays;
	  Button btnOK;
	  
	  int DIALOG_TIME = 1;
	  int DIALOG_DAYS = 2;
	  int myHour = 14;
	  int myMinute = 35;
	  String mydays = "";
	  
	  
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
	    
	    // если попали на эту форму по нажатию на Изменить будильник
	    if (MainActivity.isUpdateClicked){
	    	
	    	Intent intent = getIntent();
	        
	        int myHour = intent.getIntExtra("hour", 0);
	        int myMinute = intent.getIntExtra("minute",0);
	        mydays = intent.getStringExtra("days");
	        
	        tvAlarmTime.setText(String.format("%02d:%02d", myHour, myMinute));
	        tvAlarmDays.setText(mydays);
	        
	    }
	   
	    // если перешли на эту форму через кнопку Добавить будильник
	    else {
		    //ставим текущее время
		    Calendar calendar = Calendar.getInstance();
		    myHour = calendar.get(Calendar.HOUR_OF_DAY);
		    myMinute = calendar.get(Calendar.MINUTE);
	        tvAlarmTime.setText(String.format("%02d:%02d", myHour, myMinute));
	    }
        
        
	  }
	  
	  @Override
	  protected void onResume() {
	    super.onResume();
	    //mydays = "";
	  }
	  
	  public void onAlarmTimeClick(View view) {
	      showDialog(DIALOG_TIME);
	    }
	  
	  public void onAlarmDaysClick(View view) {
	      showDialog(DIALOG_DAYS);
	    }
	  
	  
	  
	  protected Dialog onCreateDialog(int id) {
		  if (id == DIALOG_TIME) {
		      TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
		      return tpd;
		  }
		  
		  if (id == DIALOG_DAYS) {
			  
			  final boolean[] mCheckedItems = { false, false, false, false, false, false, false };
	          final String[] checkDays = { "понедельник", "вторник", "среда", "четверг", "пятница", "суббота", "воскресенье" };
	          final String[] checkDaysShort = { "пн", "вт", "ср", "чт", "пт", "сб", "вс" };
	          
			  // если перешли на эту форму через кнопку изменить будильник
	          // отмечаем флажками дни недели, которые использовались для данного будильника
			  if (MainActivity.isUpdateClicked){ 
				  String [] array = TextUtils.split(mydays, ",");
				    
				    for (int i = 0; i < checkDaysShort.length; i++) {
						for (int j = 0; j < array.length; j++) {
							if (checkDaysShort[i].equals(array[j]))
								mCheckedItems[i] = true;
						}
					} 
			  }
			  

	          AlertDialog.Builder builder = new AlertDialog.Builder(this);
	          
	          builder.setTitle("Дни недели")
              .setCancelable(false)

              .setMultiChoiceItems(checkDays, mCheckedItems,
                      new DialogInterface.OnMultiChoiceClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog,
                                  int which, boolean isChecked) {
                              mCheckedItems[which] = isChecked;
                          }
                      })

              // Добавляем кнопки
              .setPositiveButton("Готово",
                      new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog,
                                  int id) {
                        	  mydays = "";  // очищаем переменную дни недели
                              StringBuilder state = new StringBuilder();
                              for (int i = 0; i < checkDays.length; i++) {
                                  state.append("" + checkDays[i]);
                                  if (mCheckedItems[i]){
                                	  mydays = mydays + checkDaysShort[i] + ","; 
                                      state.append(" выбран\n");
                                  }    
                                  else
                                      state.append(" не выбран\n");
                              }
                              if (mydays.length() != 0)
                            	  tvAlarmDays.setText(mydays.substring(0,mydays.length() - 1));  // выводим без последней запятой в строке
                              else
                            	  tvAlarmDays.setText("");
                              Toast.makeText(getApplicationContext(),
                                      state.toString(), Toast.LENGTH_LONG)
                                      .show();
                          }
                      })

              .setNegativeButton("Отмена",
                      new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog,
                                  int id) {
                        	  mydays = "";  // очищаем переменную дни недели
                              dialog.cancel();
                              
                          }
                      });
	          return builder.create();
		  }
		  return super.onCreateDialog(id);
	  }

	  
	  
	  OnTimeSetListener myCallBack = new OnTimeSetListener() {
	      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	          myHour = hourOfDay;
	          myMinute = minute; 
	          tvAlarmTime.setText(String.format("%02d:%02d", myHour, myMinute));// ЧЧ:ММ - формат времени
	        	  
	      }
	   };
	      
	   // кнопка ОК
	  @Override
	  public void onClick(View v) {
		  
		// если перешли на эту форму через кнопку добавить будильник
		if (!MainActivity.isUpdateClicked){ 
			
			//Toast.makeText(this, "!isUpdateClicked", Toast.LENGTH_SHORT).show();
			
		    Intent intent = new Intent();
		    intent.putExtra("alarmName", etAlarmName.getText().toString());
		    intent.putExtra("alarmTime", tvAlarmTime.getText().toString());
		    intent.putExtra("alarmDays", tvAlarmDays.getText().toString());
		    
		    
		    setResult(RESULT_OK, intent);
		    finish();
		}
		// если попали на эту форму по нажатию на Изменить будильник
	    if (MainActivity.isUpdateClicked){
	    	
	    	Toast.makeText(this, "isUpdateClicked, позиция = " + MainActivity.position, Toast.LENGTH_SHORT).show();
	    	
		    Intent intent2 = new Intent();
		    intent2.putExtra("alarmName", etAlarmName.getText().toString());
		    intent2.putExtra("alarmTime", tvAlarmTime.getText().toString());
		    intent2.putExtra("alarmDays", tvAlarmDays.getText().toString());
		    
		    setResult(RESULT_OK, intent2);
		    finish();
	    }
	    
	  }

}

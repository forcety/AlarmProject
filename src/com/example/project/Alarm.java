package com.example.project;

public class Alarm {
	  
	  String name;
	  int hour;
	  int minute;
	  String days;
	  boolean box;
	  

	  Alarm(String _describe, int _hour, int _minute, String _days, boolean _box) {
	    name = _describe;
	    hour = _hour;
	    minute = _minute;
	    days = _days;
	    box = _box;
	  }
	}
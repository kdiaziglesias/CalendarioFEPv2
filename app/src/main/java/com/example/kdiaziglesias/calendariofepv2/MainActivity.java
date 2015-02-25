package com.example.kdiaziglesias.calendariofepv2;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.Calendar;
import java.util.TimeZone;
import android.content.Intent;

import  java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button insertButton;
    long calenderID;
    long eventID;
//private WebView mWebView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertButton=(Button)this.findViewById(R.id.buttonInsertEvent);
        insertButton.setOnClickListener(this);
        //mWebView= (WebView) findViewById(R.id.activity_main_webview);

      /*  WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.fegapi.org/");
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebViewClient(new MyAppWebViewClient());*/

    }

    /*public void onBackPressed(){

        if(mWebView.canGoBack()){

            mWebView.goBack();

        }else {

            super.onBackPressed();

        }

    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        addEvent();
        addEventUsingIntent();
        addAttendee();
        addReminder();

    }

    private void addEvent(){
        calenderID = getCalenderID();
        ContentValues eventValues= new ContentValues();
        eventValues.put(CalendarContract.Events.CALENDAR_ID,calenderID);
        eventValues.put(CalendarContract.Events.TITLE,"Competiciones");
        eventValues.put(CalendarContract.Events.DESCRIPTION,"Prueba del Calendario");
        eventValues.put(CalendarContract.Events.DTSTART,Calendar.getInstance().getTimeInMillis());
        eventValues.put(CalendarContract.Events.DTEND,Calendar.getInstance().getTimeInMillis());
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE,TimeZone.getDefault().toString());

        Uri eventUri = this.getContentResolver().insert(CalendarContract.Events.CONTENT_URI,eventValues);
        eventID= ContentUris.parseId(eventUri);

    }


    public long getCalenderID(){
        Cursor cur = null;
        try{

            cur = this.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,null,null,null,null);
            if(cur.moveToFirst()){

                return cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));

            }

        }catch (Exception e){
            e.printStackTrace();

        }finally {

            if(cur != null){

                cur.close();

            }


        }
        return -1L;

    }


    private void addEventUsingIntent(){

        calenderID=getCalenderID();
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.DTSTART,Calendar.getInstance().getTimeInMillis())
                .putExtra(CalendarContract.Events.DTEND,Calendar.getInstance().getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE,"Competiciones2")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Calendario Oficial");
        startActivity(intent);


    }

    private void addAttendee(){

        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Attendees.ATTENDEE_NAME,"Kevin Diaz");
        cv.put(CalendarContract.Attendees.ATTENDEE_EMAIL,"quixoskdi@gmail.com");
        cv.put(CalendarContract.Attendees.EVENT_ID,eventID);
        cv.put(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, CalendarContract.Attendees.RELATIONSHIP_ATTENDEE);
        cv.put(CalendarContract.Attendees.ATTENDEE_STATUS, CalendarContract.Attendees.ATTENDEE_STATUS_INVITED);
        cv.put(CalendarContract.Attendees.ATTENDEE_TYPE, CalendarContract.Attendees.TYPE_OPTIONAL);

        this.getContentResolver().insert(CalendarContract.Attendees.CONTENT_URI,cv);
    }

    private void addReminder(){

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES,15);
        values.put(CalendarContract.Reminders.EVENT_ID,eventID);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);

        this.getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI,values);

    }




}

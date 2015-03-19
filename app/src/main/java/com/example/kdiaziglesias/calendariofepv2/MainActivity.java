package com.example.kdiaziglesias.calendariofepv2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button insertButton;
    long calenderID;
    long eventID;
    //Button AttendeeButton;
   // Button RemiderButton;
    //private WebView mWebView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertButton=(Button)this.findViewById(R.id.buttonInsertEvent);
        insertButton.setOnClickListener(this);
       /* AttendeeButton=(Button)this.findViewById(R.id.buttonInsertAttendee);
        AttendeeButton.setOnClickListener(this);
        RemiderButton=(Button)this.findViewById(R.id.buttonInsertReminder);
        RemiderButton.setOnClickListener(this);*/
        Button miboton = (Button) findViewById(R.id.Bfegapi);

        miboton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento = new Intent(MainActivity.this,ActividadSegunda.class);
                startActivityForResult(intento,1);
            }
        });


        Button Esboton = (Button) findViewById(R.id.Brfep);

        Esboton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento2;
                intento2 = new Intent(MainActivity.this,Pagina_Refp.class);
                startActivityForResult(intento2,1);

            }
        });

        Button batencion = (Button) findViewById(R.id.buttonInsertAttendee);
        Button brecodar = (Button) findViewById(R.id.buttonInsertReminder);

        batencion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            showToast("En Proceso....");

            }
        });

        brecodar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showToast("En Proceso....");

            }
        });

        Button juego = (Button) findViewById(R.id.bjuego);

        juego.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento3 = new Intent(MainActivity.this,JuegodeColocacion.class);
                startActivityForResult(intento3,1);


            }
        });


    }








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

    public void showToast(String msg){

        Context contexto = getApplicationContext();
        int duracion = Toast.LENGTH_SHORT;
        Toast tostada = Toast.makeText(contexto,msg,duracion);
        tostada.show();
    }


}

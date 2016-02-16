package grapen.se.notificationagenda;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import grapen.se.notificationagenda.calendar.CalendarEvent;
import grapen.se.notificationagenda.calendar.CalendarRepository;
import grapen.se.notificationagenda.calendar.androidcalendar.AndroidCalendarEvent;
import grapen.se.notificationagenda.calendar.androidcalendar.AndroidCalendarRepository;

public class TestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
    protected void onResume() {
        super.onResume();

        CalendarRepository calendarRepository = new AndroidCalendarRepository(getContentResolver());
        ArrayList<CalendarEvent> events = calendarRepository.findAllEvents();
        for (CalendarEvent event : events) {
            Log.d("OLA", "Event: " + event.getDisplayName());
        }
        Log.d("Event", "Done");
    }
}
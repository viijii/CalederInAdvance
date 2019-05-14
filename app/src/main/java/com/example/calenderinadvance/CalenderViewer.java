package com.example.calenderinadvance;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.calenderinadvance.webservices.API;
import com.example.calenderinadvance.webservices.RestClient;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalenderViewer extends AppCompatActivity {

    CalendarPickerView calendar;
    Date d;
    String currentDate,s;
    CalenderResponse calenderResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_viewer);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today)
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_next:
                ArrayList<Date> selectedDates = (ArrayList<Date>)calendar
                        .getSelectedDates();
                ArrayList<String> dateStringList = new ArrayList<>();

                for (Date date : selectedDates) {
                    String dateStr = String.valueOf(date);
                    dateStringList.add(dateStr);
                    Log.d( "TAG", "onOptionsItemSelected: rrr" +dateStr);
                }

                String[] mStringArray = new String[dateStringList.size()];
                mStringArray = dateStringList.toArray(mStringArray);



                for(int i=0; i< mStringArray.length;i++){
                    String str = mStringArray[i];
                    SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                    try {
                        d = sdf.parse(str);
                        // Log.d( "TAG", "onCreate: dddd"+d );

                    } catch (ParseException ex) {
                        Log.v("Exception", ex.getLocalizedMessage());
                    }


                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    currentDate=dateFormat.format(d);
                    Log.d("TAG", "onOptionsItemSelected: currentDate"+currentDate);
                    Toast.makeText( CalenderViewer.this, currentDate, Toast.LENGTH_SHORT ).show();
                    System.out.println(currentDate);

                   calender();
                }


                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void calender() {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                    client( okHttpClient ).
                    addConverterFactory( GsonConverterFactory
                            .create() ).build();
            API api = RestClient.client.create( API.class );
            Log.d("TAG", "calender:raj "+currentDate);
            Call<CalenderResponse> call=api.calender2(currentDate);

            call.enqueue( new Callback<CalenderResponse>() {
                @Override
                public void onResponse(Call<CalenderResponse> call,
                                       Response<CalenderResponse> response) {
//                    Log.d( "TAG", "onClick: 78" +s);
                    //calenderResponse=new CalenderResponse();
                    calenderResponse= response.body();
                    Log.d("TAG", "onResponse: "+calenderResponse);
                   //Log.d( "TAG", "onClick:status " +calenderResponse.getStatus());
                  //Log.d( "TAG", "onClick:msg" +calenderResponse.getMessage());
                   /* if (calenderResponse.getStatus().equalsIgnoreCase( "true" )) {
                        Toast.makeText( CalenderViewer.this, "date has been sent", Toast.LENGTH_SHORT ).show();
                    } else {
                        Toast.makeText( CalenderViewer.this, "Failed", Toast.LENGTH_SHORT ).show();
                    }*/
                }

                @Override
                public void onFailure(Call<CalenderResponse> call, Throwable t) {


                    Log.d( " TAG", "onFailure:fail" + t.getMessage());

                    //
                }


            } );


        } catch (Exception e) {
            System.out.print( "Exception e" + e );
            Log.d("TAG", "calender:exception "+e.getMessage());


        }
    }
}
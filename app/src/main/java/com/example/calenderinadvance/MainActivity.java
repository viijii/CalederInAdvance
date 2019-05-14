package com.example.calenderinadvance;


import android.app.DatePickerDialog;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calenderinadvance.webservices.API;
import com.example.calenderinadvance.webservices.RestClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.calenderinadvance.CalenderAdapter.DATE_DIALOG_ID;

public class MainActivity extends AppCompatActivity {
    CalenderResponse samerideResponse;
    RecyclerView recyclerView;
    int mYear, mMonth, mDay;
    String sam;
    ArrayList<CalenderResponse.data> data = new ArrayList<>();

    CalenderAdapter history_ride_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        getTodayRides();

    }


    public void getTodayRides() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                client(okHttpClient).
                addConverterFactory(GsonConverterFactory
                        .create()).build();
        API api = RestClient.client.create(API.class);


        Call<CalenderResponse> call = api.calender1("111");


        call.enqueue(new Callback<CalenderResponse>() {
            @Override
            public void onResponse(Call<CalenderResponse> call, Response<CalenderResponse> response) {

                samerideResponse = response.body();

                try {

                    if (samerideResponse.getStatus().equalsIgnoreCase("true")) {

                        for (int i = 0; i < samerideResponse.getData().length; i++) {
                            data.add(samerideResponse.getData()[i]);
                        }
                    }

                    setListView();
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<CalenderResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG);
            }
        });
    }

    public void setListView() {


        history_ride_adapter = new CalenderAdapter(data, MainActivity.this, getApplicationContext());
        recyclerView.setAdapter(history_ride_adapter);

    }


    public String date() {
        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();

                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "yyyy/MM/dd"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                 sam=sdf.format(myCalendar.getTime());
              //  leaveson.setText(sdf.format(myCalendar.getTime()));
                Log.d("TAG", "onDateSet: sdf"+sam);
                mDay = selectedday;
                mMonth = selectedmonth;
                mYear = selectedyear;
            }
        }, mYear, mMonth, mDay);
        //mDatePicker.setTitle("Select date");

        mDatePicker.show();
        return sam;
  }

    }






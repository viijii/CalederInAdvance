package com.example.calenderinadvance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.autofill.Dataset;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;


public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.EMSHolder> {

    SharedPreferences sharedpreferences;
    CalenderViewer calenderView;
    int FADE_DURATION = 100;
    ArrayList<CalenderResponse.data> data;
    ArrayList<CalenderResponse.data> dataFilter;

    String Calenderdata;

    Context context;
    MainActivity mainActivity;
    int Totaldays = 30;
    int mYear, mMonth, mDay;
    String s,sam, s1="";
    static final int DATE_DIALOG_ID = 0;

Context c;
    int globalPosition; //used fo
    /* int completedRides;
     completedRides=Totaldays-(Integer.parseInt(s));
         Log.d("TAG", "onBindViewHolder: completedrides"+Integer.parseInt(s));
        Log.d("TAG", "onBindViewHolder: completedrides"+completedRides);*/
    CalenderResponse calenderResponse;
    Calendar mcurrentDate;

    public CalenderAdapter(ArrayList<CalenderResponse.data> data, MainActivity mainActivity, Context context) {
        this.context = context;
        this.data = data;


        this.dataFilter = new ArrayList<CalenderResponse.data>();
        this.dataFilter.addAll(data);

        this.mainActivity = mainActivity;
    }


    @Override
    public EMSHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender,
                parent, false);

        return new EMSHolder(itemView);
    }

    public void onBindViewHolder(final EMSHolder holder, final int position) {
        setScaleAnimation(holder.itemView);


        //  holder.nSeats.setText(data.get(position).);
        s = data.get(position).getSubscribedDays();
        Log.d("TAG", "onBindViewHolder: s" + s);


        holder.subscribedrides.setText(s);
        holder.source.setText(data.get(position).getSource());
        holder.destination.setText(data.get(position).getDestination());
        holder.totalAmount.setText(data.get(position).getTotalAmount());
        // holder.crides.setText(completedRides);
        holder.rideson.setText(data.get(position).getStartDate());

        c= holder.ll1.getContext();

        Log.d(TAG, "onBindViewHolder: cccc"+c);


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CalenderViewer.class);
                context.startActivity(i);
            }
        });

        holder.calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.leaveson.setText(showPicker(c));


            }
        });

            }


    private String showPicker(Context context) {

        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();

                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "yyyy/MM/dd"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                sam=sdf.format(myCalendar.getTime());

                Log.d("TAG", "onDateSet: sdf"+sam);
                mDay = selectedday;
                mMonth = selectedmonth;
                mYear = selectedyear;

                s1="1";
            }
        }, mYear, mMonth, mDay);
        //mDatePicker.setTitle("Select date");

        mDatePicker.show();
return sam;

    }


    private void setScaleAnimation(View view)
    {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


    public String date() {
        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
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

    public static class EMSHolder extends RecyclerView.ViewHolder
    {
int ref;
LinearLayout ll1;
        TextView source,destination,totalAmount,subscribedrides,rideson,button,crides,calender,leaveson;

        public EMSHolder(View v)
        {
            super(v);
            subscribedrides=(TextView) v.findViewById(R.id.srides);
            source  =(TextView) v.findViewById(R.id.source);
            destination=(TextView) v.findViewById(R.id.destination);
            totalAmount=(TextView) v.findViewById(R.id.amount);
            rideson=(TextView) v.findViewById(R.id.ridesOn);
            crides=(TextView)v.findViewById(R.id.crides);
            calender=v.findViewById(R.id.calendar);
            leaveson=v.findViewById(R.id.leavesOn);
            button=(TextView)v.findViewById(R.id.leaves);
ll1 = v.findViewById(R.id.ll1);



        }
    }

    public int getItemCount() {
        return  data.size();
    }
    public Object getItem(int i) {
        return i;
    }
    public long getItemId(int i) {
        return i;
    }


}

package com.example.calenderinadvance.webservices;



import com.example.calenderinadvance.CalenderResponse;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("calender1.php")
    Call<CalenderResponse> calender1(@Query("pid") String pid);

    @GET("calender2.php")
    Call<CalenderResponse> calender2(@Query("unusedDates") Object unusedDates);






}

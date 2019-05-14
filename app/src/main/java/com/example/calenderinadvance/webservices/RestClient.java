package com.example.calenderinadvance.webservices;


import retrofit2.Retrofit;

public class RestClient {

    public static final String baseUrl = "http://www.toobworks.com/sameride/";

    public static Retrofit client = new Retrofit.Builder().baseUrl(baseUrl).build();

}

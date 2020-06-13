package com.example.covid19_test.Pojo.Common;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiesClient {

        private static Retrofit retrofit=null;
        public static final String BASE_URL="https://corona.lmao.ninja/v2/";
        public static Retrofit getRetrofit(){
            if(retrofit==null){
                retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }


    }




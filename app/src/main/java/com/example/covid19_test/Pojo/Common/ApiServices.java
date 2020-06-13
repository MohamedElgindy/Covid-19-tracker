package com.example.covid19_test.Pojo.Common;



import com.example.covid19_test.Pojo.All.All;
import com.example.covid19_test.Pojo.countries.Countries;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("all")
    public Call <All> getAll();

    @GET("countries/{countryName}?yesterday=true")
    Call<Countries> get_Countries(@Path("countryName") String countryName);

//
//    @GET("countries/{countryId}")
//    Call<CountryInfo> get_CountriesInfo(@Path("countryId") String countryId);

    @GET("countries")
    public Call <List<Countries>> getCountriesInfo(
            @Query("sort") String order

    );


}

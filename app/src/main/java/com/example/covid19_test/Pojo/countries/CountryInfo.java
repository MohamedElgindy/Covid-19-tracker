
package com.example.covid19_test.Pojo.countries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryInfo {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("iso2")
    @Expose
    private String iso2;
    @SerializedName("iso3")
    @Expose
    private String iso3;
//    @SerializedName("lat")
//    @Expose
//    private Integer lat;
//    @SerializedName("long")
//    @Expose
//    private Integer _long;
    @SerializedName("flag")
    @Expose
    private String flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

//    public Integer getLat() {
//        return lat;
//    }
//
//    public void setLat(Integer lat) {
//        this.lat = lat;
//    }
//
//    public Integer getLong() {
//        return _long;
//    }
//
//    public void setLong(Integer _long) {
//        this._long = _long;
//    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}

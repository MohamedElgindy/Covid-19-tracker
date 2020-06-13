
package com.example.covid19_test.Pojo.All;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class All {

    @SerializedName("cases")
    @Expose
    private Integer cases;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("active")
    @Expose
    private Integer active;

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public String getUpdated() {
        return updated;
    }

//    public void setUpdated(Integer updated) {
//        this.updated = updated;
//    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

}

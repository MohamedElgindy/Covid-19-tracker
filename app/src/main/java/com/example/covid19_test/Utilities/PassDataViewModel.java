package com.example.covid19_test.Utilities;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PassDataViewModel extends ViewModel {
    /**
     * Live Data Instance
     */
    private MutableLiveData<String> mCountryName = new MutableLiveData<>();

    public void setCountryName(String countryName) {
        mCountryName.setValue(countryName);
    }
    public LiveData<String> getCountryName() {
        return mCountryName;
    }


//     more...
//    private MutableLiveData<String> mMoreName = new MutableLiveData<>();
//    public void setMoreName(String moreName) {
//        mMoreName.setValue(moreName);
//    }
//    public LiveData<String> getMoreName() {
//        return mMoreName;
//    }


}

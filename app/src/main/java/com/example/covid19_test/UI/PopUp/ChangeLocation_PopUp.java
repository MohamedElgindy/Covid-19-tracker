package com.example.covid19_test.UI.PopUp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.covid19_test.Pojo.Common.ApiServices;
import com.example.covid19_test.Pojo.Common.WebServiesClient;
import com.example.covid19_test.UI.Main.MainActivity;
import com.example.covid19_test.R;
import com.example.covid19_test.Pojo.countries.Countries;
import com.example.covid19_test.Utilities.Custom_ViewDialog;
import com.example.covid19_test.Utilities.Utilities;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeLocation_PopUp extends Activity {


    Spinner mSpinner;
    Button doneButton ;
    int height,width ;
    Custom_ViewDialog ChangeLocation_ViewDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_location__pop_up);

        ChangeLocation_ViewDialog  = new Custom_ViewDialog(this);
        ChangeLocation_ViewDialog.showDialog();

        // used to define dimension of PopUp
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

         width = dm.widthPixels;
         height = dm.heightPixels;
        getWindow().setLayout((int)(width * .7 ), (int)(height * .4));
        callApi();
    }

    private void callApi() {
        // Call to get all Country Name
        ApiServices apiServices = WebServiesClient.getRetrofit().create(ApiServices.class);
        Call<List<Countries>> call = apiServices.getCountriesInfo("");
        call.enqueue(new Callback<List<Countries>>() {
            @Override
            public void onResponse(Call<List<Countries>> call, Response<List<Countries>> response) {
                List<Countries> spinnerTitles = new ArrayList<>();
                spinnerTitles = response.body();

                // Spinner of popUp
                mSpinner = findViewById(R.id.Spinner_Flag) ;
                // to limit the height of spinner dropdown
                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    // my code
                    Custom_Spinner_Adapter mCustomAdapter = new Custom_Spinner_Adapter(ChangeLocation_PopUp.this, spinnerTitles);
                    mSpinner.setAdapter(mCustomAdapter);
                    // Get private mPopup member variable and try cast to ListPopupWindow
                    android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(mSpinner);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    popupWindow.setHeight((int) (height * .3));


                }
                catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
                    // silently fail...
                    e.printStackTrace();
                }
                Custom_Spinner_Adapter mCustomAdapter = new Custom_Spinner_Adapter(ChangeLocation_PopUp.this, spinnerTitles);
                mSpinner.setAdapter(mCustomAdapter);

                // make every item in spinner Clickable
                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    boolean FirstLoad =true;

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // use it to privent toast show in the first time of open popUp
                        if(FirstLoad)
                        {
                            FirstLoad = false;
                            return;
                        }

                        Countries clickedItem = (Countries) parent.getItemAtPosition(position);
                        final String clickedCountryName = clickedItem.getCountry();
                        Toast.makeText(ChangeLocation_PopUp.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();


                        // Done Button to start intent and close popUp
                        doneButton = findViewById(R.id.Done_popUp);
                        doneButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ChangeLocation_PopUp.this, MainActivity.class);
                                intent.putExtra("EXTRA_COUNTRY_NAME", clickedCountryName);
                                startActivity(intent);

                            }
                        });
                     }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Countries>> call, Throwable t) {

                ChangeLocation_ViewDialog.hideDialog();
                // check if the problem of response is internet
                if (t instanceof IOException) {

                    call.clone().enqueue(this);
                    //hide NoInternetBar when Connect to internet
                    Utilities.endTopBar();
                }
            }
        });
    }

}

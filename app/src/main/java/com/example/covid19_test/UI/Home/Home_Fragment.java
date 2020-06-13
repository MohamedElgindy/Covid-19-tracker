package com.example.covid19_test.UI.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid19_test.Pojo.All.All;
import com.example.covid19_test.Pojo.Common.ApiServices;
import com.example.covid19_test.Pojo.Common.WebServiesClient;
import com.example.covid19_test.Utilities.PassDataViewModel;
import com.example.covid19_test.UI.PopUp.ChangeLocation_PopUp;
import com.example.covid19_test.R;
import com.example.covid19_test.Pojo.countries.Countries;
import com.example.covid19_test.Utilities.Custom_ViewDialog;
import com.example.covid19_test.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home_Fragment extends Fragment {

    View view ;
    private SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss" );

    private TextView death , recouvery , confirmed , mConfirmedCountrey , mRecouveryCountry , mDeathCountry
            , mChaneLocation , mSelectedCountry , mTopCasesDate , mTopCasesCountiryName ,
            mTopCasesNumber  , mLastUpdate;

    private ImageView mTopCouniryFlag;


    Custom_ViewDialog Home_viewDialog;
    // sharedPreference Class Object
    private Countery_Save mCounterySave;

    // Name of Country that Stored in sharedPreference
    private String mCountryName ;

    PassDataViewModel passDataViewModel;


    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Home_viewDialog = new Custom_ViewDialog(getActivity());
        passDataViewModel = ViewModelProviders.of(requireActivity()).get(PassDataViewModel.class);
        Home_viewDialog.showDialog();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_, container, false);

        initViews();
        callApi(getActivity());

        // Click on Change Location to open popUp
        mChaneLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity() , ChangeLocation_PopUp.class);
                startActivity(i);
            }
        });

        return view;

    }


    private void initViews() {
        recouvery =view.findViewById(R.id.Details_recovered);
        death = view.findViewById(R.id.Details_death);
        confirmed = view.findViewById(R.id.Details_Confirmed);
        mConfirmedCountrey = view.findViewById(R.id.Confirmed_countury);
        mDeathCountry = view.findViewById(R.id.death_countries);
        mRecouveryCountry = view.findViewById(R.id.recoverd_countries);
        mTopCasesNumber = view.findViewById(R.id.topCases_TotalCases);
        mTopCasesDate = view.findViewById(R.id.topCases_day);
        mTopCasesCountiryName = view.findViewById(R.id.topCases_countryname);
        mTopCouniryFlag = view.findViewById(R.id.topCases_Flag);
        mChaneLocation = view.findViewById(R.id.Change_location);
        mSelectedCountry = view.findViewById(R.id.selected_country_name);
        mLastUpdate = view.findViewById(R.id.date_test);

    }

    private void callApi(final Activity activity) {
        // Call of All Statics of All Countries
        ApiServices apiServices = WebServiesClient.getRetrofit().create(ApiServices.class);
        Call<All> call = apiServices.getAll();
        call.enqueue(new Callback<All>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<All> call, Response<All> response) {
                Log.e("Success", String.valueOf(response.body().getRecovered()));

                confirmed.setText(Utilities.DecimalFormatter(response.body().getCases()));
                death.setText(Utilities.DecimalFormatter(response.body().getDeaths()));
                recouvery.setText(Utilities.DecimalFormatter(response.body().getRecovered()));

                // set the last Update time and date of cases
                Long LastUpdate = Long.valueOf(response.body().getUpdated());
                String dateString = formatter.format(new Date(LastUpdate));
                mLastUpdate.setText(String.format("Last Update \n%s", dateString));
                Home_viewDialog.hideDialog();
            }


            @Override
            public void onFailure(Call<All> call, Throwable t) {
                Utilities.TopBar(activity , "NO Internet Connection");
                Home_viewDialog.hideDialog();
                // check if the problem of response is internet
                if (t instanceof IOException) {
                    // display NoInternetBar
                    call.clone().enqueue(this);
                    //hide NoInternetBar when Connect to internet
                    Utilities.endTopBar();
                }
                // anther server problem
                else {Utilities.TopBar(getActivity() , "Try Again Later"); }

            }

        });

        // Use of Shared Class
        mCounterySave = new Countery_Save(getActivity());
        // Store the name in Shared in variable
        mCountryName = mCounterySave.getCountry_shared();
        // intent that response from intent in PopUp
        String sessionId = getActivity().getIntent().getStringExtra("EXTRA_COUNTRY_NAME");

        // chick if that the first time of open the App
        if (sessionId == null)
        {
            sessionId = mCountryName ;

        }


        //Call of Selected country
        Call<Countries> call_1 =apiServices.get_Countries(sessionId);
        call_1.enqueue(new Callback<Countries>() {
            @Override
            public void onResponse(Call<Countries> call, Response<Countries> response) {
                mSelectedCountry.setText(response.body().getCountry() + " Cases");
                String Cases = Utilities.DecimalFormatter(response.body().getCases());
                String Death = Utilities.DecimalFormatter(response.body().getDeaths());
                String Recouverd = Utilities.DecimalFormatter(response.body().getRecovered());
                mConfirmedCountrey.setText(Cases);
                mDeathCountry.setText(Death);
                mRecouveryCountry.setText(Recouverd);
                // Shared Function to save Selected country
                mCounterySave.SaveCountiry_shared(response.body().getCountry());

            }

            @Override
            public void onFailure(Call<Countries> call, Throwable t) {

                Home_viewDialog.hideDialog();
                // check if the problem of response is internet
                if (t instanceof IOException) {
                    // display NoInternetBar
 //                   Utilities.TopBar(activity, "NO Internet Connection");
                    call.clone().enqueue(this);
                    //hide NoInternetBar when Connect to internet
                    Utilities.endTopBar();
                }
                // anther server problem
                else {Utilities.TopBar(getActivity() , "Try Again Later"); }
            }
        });

        // Top Countiry in Cases
        Call<List<Countries>> call3 = apiServices.getCountriesInfo("cases");
        call3.enqueue(new Callback<List<Countries>>() {
            @Override
            public void onResponse(Call<List<Countries>> call, Response<List<Countries>> response) {
                String  topCountiry , topTotalCases ;String topCasesFlag ;

                topCountiry = response.body().get(0).getCountry();
                topTotalCases = Utilities.DecimalFormatter(response.body().get(0).getCases());
                topCasesFlag = response.body().get(0).getCountryInfo().getFlag();
               // format the date of top country
                String date = new SimpleDateFormat("E, dd MMM yyyy").format(Calendar.getInstance().getTime());
                mTopCasesDate.setText(date);
                mTopCasesNumber.setText(topTotalCases + " Case");
                mTopCasesCountiryName.setText(topCountiry);
                Picasso.get()
                        .load(topCasesFlag)
                        .resize(70, 40)
                        .centerCrop()
                        .into(mTopCouniryFlag);
            }

            @Override
            public void onFailure(Call<List<Countries>> call, Throwable t) {
                Home_viewDialog.hideDialog();
//                Utilities.TopBar(getActivity() , "NO Internet Connection");

                // check if the problem of response is internet
                if (t instanceof IOException) {
                    // display NoInternetBar
                    call.clone().enqueue(this);
                    //hide NoInternetBar when Connect to internet
                    Utilities.endTopBar();
                }
                // anther server problem
                else {Utilities.TopBar(getActivity() , "Try Again Later"); }
            }
        });

    }
}

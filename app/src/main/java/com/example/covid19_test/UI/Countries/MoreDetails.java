package com.example.covid19_test.UI.Countries;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid19_test.Pojo.Common.ApiServices;
import com.example.covid19_test.Pojo.Common.WebServiesClient;
import com.example.covid19_test.Pojo.countries.Countries;
import com.example.covid19_test.R;
import com.example.covid19_test.Utilities.PassDataViewModel;
import com.example.covid19_test.Utilities.Custom_ViewDialog;
import com.example.covid19_test.Utilities.Utilities;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.gson.reflect.TypeToken.get;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreDetails extends Fragment {

    View view;
    private PassDataViewModel passDataViewModel;
    String selectedCountry ;
    Custom_ViewDialog MoreDetailsViewDialog;
    private TextView mCountryName , mConfirmedCases ,mDeath , mRecovered , mTodayConfirmed , mTodayDeath , mActiveCases , mMildCondition , mCriticalCases
                , mClosedCases , mClosedRecovered , mCloseDeath ,  mDeathPerM , mCasesPerM  , mTestNumber , mTestPerM;
    private ImageView mCountryFlag;

    private PieChart mRecoveryRatePieChart , mDeathRatePieChart;

    private String CountryName , ConfirmedCases , DeathCases , RecoveredCases , TodayConfirmed , TodayDeath
            , ActiveCases , MildCondition , CriticalCases , ClosedCases , CountryFlag , DeathPerM , CasesPerM , TestNumber , TestPerM;


    public MoreDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passDataViewModel = ViewModelProviders.of(requireActivity()).get(PassDataViewModel.class);
        MoreDetailsViewDialog = new Custom_ViewDialog(getActivity());
        MoreDetailsViewDialog.showDialog();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more_details, container, false);
        intiViews();
        passDataViewModel.getCountryName().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    selectedCountry = s;
                }
            }
        });

        callApi(selectedCountry);
        return view ;
    }

    private void intiViews() {

        mCountryName = view.findViewById(R.id.Details_CountryName);
        mConfirmedCases = view.findViewById(R.id.Details_Confirmed);
        mDeath = view.findViewById(R.id.Details_death);
        mRecovered = view.findViewById(R.id.Details_recovered);
        mTodayConfirmed = view.findViewById(R.id.TodayConfremed);
        mTodayDeath = view.findViewById(R.id.TodayDeath);
        mActiveCases = view.findViewById(R.id.Details_ActiveCases);
        mMildCondition = view.findViewById(R.id.Details_MildCondations);
        mCriticalCases = view.findViewById(R.id.Ditails_Criticla);
        mClosedCases = view.findViewById(R.id.ClosedCases);
        mClosedRecovered = view.findViewById(R.id.Details_Closed_Recouverd);
        mCloseDeath = view.findViewById(R.id.Details_ClosedDeath);
        mCountryFlag = view.findViewById(R.id.Details_Flag);
        mCasesPerM = view.findViewById(R.id.CasePerM_TV);
        mDeathPerM = view.findViewById(R.id.DeathPerM_TV);
        mTestNumber = view.findViewById(R.id.Details_Test);
        mTestPerM = view.findViewById(R.id.Details_TestPerM);
        mRecoveryRatePieChart = view.findViewById(R.id.RecoveryRate_PieChart);
        mDeathRatePieChart = view.findViewById(R.id.DeathRate_PieChart);
    }

    private void callApi(String Country) {
        ApiServices apiServices = WebServiesClient.getRetrofit().create(ApiServices.class);
        Call<Countries> call = apiServices.get_Countries(Country);
        call.enqueue(new Callback<Countries>() {
            @Override
            public void onResponse(Call<Countries> call, Response<Countries> response) {
                int NumberOfRecovered = response.body().getRecovered();
                int NumberOfCases = response.body().getCases();
                int NumberOfDeath = response.body().getDeaths();
                double MildConditions = response.body().getActive() - response.body().getCritical();
                double closedCases = NumberOfRecovered + NumberOfDeath;
                CountryFlag = response.body().getCountryInfo().getFlag();

                 CountryName = response.body().getCountry();
                 ConfirmedCases = Utilities.DecimalFormatter(NumberOfCases);
                 DeathCases = Utilities.DecimalFormatter(NumberOfDeath);
                 RecoveredCases = Utilities.DecimalFormatter(NumberOfRecovered);
                 TodayConfirmed = Utilities.DecimalFormatter(response.body().getTodayCases());
                 TodayDeath = Utilities.DecimalFormatter(response.body().getTodayDeaths());
                 ActiveCases = Utilities.DecimalFormatter(response.body().getActive());
                 CriticalCases = Utilities.DecimalFormatter(response.body().getCritical());
                 MildCondition = Utilities.DecimalFormatter(MildConditions);
                 ClosedCases = Utilities.DecimalFormatter(closedCases);
                 DeathPerM = Utilities.DecimalFormatter(response.body().getDeathsPerOneMillion());
                 CasesPerM = Utilities.DecimalFormatter(response.body().getCasesPerOneMillion());
                 TestNumber = Utilities.DecimalFormatter(response.body().getTests());
                 TestPerM = Utilities.DecimalFormatter(response.body().getTestsPerOneMillion());
                Picasso.get()
                        .load(CountryFlag)
                        .resize(90, 60)
                        .centerCrop()
                        .into(mCountryFlag);
                mCountryName.setText(CountryName);
                mConfirmedCases.setText(ConfirmedCases);
                mDeath.setText(DeathCases);
                mRecovered.setText(RecoveredCases);
                mTodayConfirmed.setText(String.format("+ %s newCases", TodayConfirmed));
                mTodayDeath.setText(String.format("+ %s newDeath", TodayDeath));
                mActiveCases.setText(ActiveCases);
                mMildCondition.setText(String.format("%s(%s%%)", MildCondition, (int)percent(MildConditions, response.body().getActive())));
                mCriticalCases.setText(String.format("%s(%s%%)", CriticalCases, (int)percent(response.body().getCritical(), response.body().getActive())));
                mClosedCases.setText(ClosedCases);
                mClosedRecovered.setText(String.format("%s(%s%%)", RecoveredCases, (int)percent(NumberOfRecovered, closedCases)));
                mCloseDeath.setText(String.format("%s(%s%%)", DeathCases, (int)percent(NumberOfDeath, closedCases)));
                mDeathPerM.setText(DeathPerM);
                mCasesPerM.setText(CasesPerM);
                mTestNumber.setText(TestNumber);
                mTestPerM.setText(TestPerM);

                // Draw Charts
                DrawPieChart(mRecoveryRatePieChart , NumberOfCases
                     , NumberOfRecovered  ,R.color.blue ," Of Total Cases"
                                , Color.GREEN );
                DrawPieChart(mDeathRatePieChart , NumberOfCases
                        , NumberOfDeath  ,R.color.red ," Of Total Cases"
                        ,android.graphics.Color.RED );

                MoreDetailsViewDialog.hideDialog();
            }

            @Override
            public void onFailure(Call<Countries> call, Throwable t) {
                // hide loading dialog
                MoreDetailsViewDialog.hideDialog();

                // check if the problem of response is internet
                if (t instanceof IOException) {

                    // display NoInternetBar
                    Utilities.TopBar(getActivity() , "NO Internet Connection");
                    call.clone().enqueue(this);
                    //hide NoInternetBar when Connect to internet
                    Utilities.endTopBar();
                }
                // anther server problem
                else {Utilities.TopBar(getActivity() , "Try Again Later"); }

            }

        });
    }

    static double percent(double RecoveredDeathCases, double NumberOfCases)
    {
        double result ;
        result = (RecoveredDeathCases * 100/ NumberOfCases);

        return Math.round(result);
    }

    void DrawPieChart (PieChart pieChart , int NumberOfCases , int RecoveredDeathCases  , int Color , String CenterText ,
                       int CenterColor)
    {
        ArrayList<PieEntry> yvalues = new ArrayList<>();
        yvalues.add(new PieEntry(NumberOfCases - RecoveredDeathCases ));
        yvalues.add(new PieEntry(RecoveredDeathCases ));
        PieDataSet dataSet = new PieDataSet(yvalues, "");
        int percent = (int) percent(RecoveredDeathCases , NumberOfCases);
        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        pieChart.setData(data);
        dataSet.setSliceSpace(4);
        pieChart.setHoleRadius(85f);
        dataSet.setColors(new int[]{R.color.LightningGray , Color}  , getActivity());
        SpannableString ss1=  new SpannableString(String.valueOf(percent) +"% " + "\n" + CenterText);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,4, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(CenterColor), 0, 4, 0);// set color
        pieChart.setCenterText(ss1);
        pieChart.setCenterTextSize(14);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);
        pieChart.animateXY(1400, 1400);
    }







}

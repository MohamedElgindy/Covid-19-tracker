package com.example.covid19_test.UI.Countries;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.covid19_test.Pojo.Common.ApiServices;
import com.example.covid19_test.Pojo.Common.WebServiesClient;
import com.example.covid19_test.Pojo.countries.Countries;
import com.example.covid19_test.R;
import com.example.covid19_test.Utilities.PassDataViewModel;
import com.example.covid19_test.Utilities.Custom_ViewDialog;
import com.example.covid19_test.Utilities.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Countries_Fragment extends Fragment  implements Countiry_Adapter.onCountryListener , AdapterView.OnItemSelectedListener {

    View view ;
    ArrayList<Countries> mCountriesList = new ArrayList<>();
    RecyclerView mCountryRV;
    Countiry_Adapter mCountryAdapter;
    EditText Search_bar;
    Spinner mSortSpinner ;
    String spinnerSlectedItem;
    Custom_ViewDialog Countries_ViewDialog;
    private PassDataViewModel passDataViewModel;

    //Custom_ViewDialog countriesViewDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passDataViewModel = ViewModelProviders.of(requireActivity()).get(PassDataViewModel.class);
        Countries_ViewDialog = new Custom_ViewDialog(getActivity());
        Countries_ViewDialog.showDialog();
    }

    public Countries_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSlectedItem = parent.getItemAtPosition(position).toString();
        CallApi(getActivity());
        //custom_viewDialog.hideDialog();


    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_countries, container, false);
        initViews();
      //  Custom_ViewDialog.showDialog();

        // SpinnerCode
        // Creating an Array Adapter to populate the spinner with the data in the string resources
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),R.array.SortBy
                ,android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortSpinner.setAdapter(spinnerAdapter);
        mSortSpinner.setOnItemSelectedListener(this);

        // SearchBar code
        Search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null){
                filter(s.toString());}
            }
        });

        return view;
    }

    private void initViews() {
        mCountryRV =view.findViewById(R.id.Country_rv);
        Search_bar = view.findViewById(R.id.Search_ET);
        mSortSpinner = view.findViewById(R.id.SortSpinner);

    }


    private void CallApi(final Activity activity) {
        ApiServices apiServices = WebServiesClient.getRetrofit().create(ApiServices.class);
        Call<List<Countries>> call = apiServices.getCountriesInfo(spinnerSlectedItem);
        call.enqueue(new Callback<List<Countries>>() {
            @Override
            public void onResponse(Call<List<Countries>> call, Response<List<Countries>> response) {
                mCountriesList = (ArrayList<Countries>) response.body();
                // Recycler code
                mCountryAdapter = new Countiry_Adapter(getActivity() , mCountriesList, Countries_Fragment.this );
                mCountryRV.setAdapter(mCountryAdapter);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity() ,RecyclerView.VERTICAL,false);
                mCountryRV.setLayoutManager(layoutManager);
                Countries_ViewDialog.hideDialog();

            }


            @Override
            public void onFailure(Call<List<Countries>> call, Throwable t) {
               // hide loading dialog
                Countries_ViewDialog.hideDialog();

                // check if the problem of response is internet
                if (t instanceof IOException) {
                    Utilities.TopBar(activity, "NO Internet Connection");

                    call.clone().enqueue(this);
                    //hide NoInternetBar when Connect to internet
                    Utilities.endTopBar();
                }else {Utilities.TopBar(getActivity() , "Try Again Later");}


            }
        });

    }


    // Filter code
    private void filter(String text) {
        ArrayList<Countries> filteredList = new ArrayList<>();
        for (Countries item : mCountriesList) {
            if (item.getCountry().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (!filteredList.isEmpty()){
        mCountryAdapter.filterList(filteredList);}
    }



    @Override
    public void selectedCountry(Countries countries) {
        // receive data from country when user click on it and pass it and open anther Fragment
        passDataViewModel.setCountryName(countries.getCountry());
        Search_bar.setText(null);


        MoreDetails nextFrag= new MoreDetails();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.Frame_Container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }



}

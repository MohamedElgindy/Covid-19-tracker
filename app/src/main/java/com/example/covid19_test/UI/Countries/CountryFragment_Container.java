package com.example.covid19_test.UI.Countries;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid19_test.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryFragment_Container extends Fragment {

    View view;
    public CountryFragment_Container() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_country__container, container, false);
        // open Countries Fragment as default
        Countries_Fragment countries_fragment = new Countries_Fragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.Frame_Container, countries_fragment, "findThisFragment")
                .commit();
        return view;
    }


}



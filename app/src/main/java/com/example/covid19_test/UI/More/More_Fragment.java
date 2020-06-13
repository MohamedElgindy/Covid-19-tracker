package com.example.covid19_test.UI.More;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.icu.text.DateTimePatternGenerator;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covid19_test.R;
import com.example.covid19_test.UI.Countries.Countiry_Adapter;
import com.example.covid19_test.UI.Countries.Countries_Fragment;
import com.example.covid19_test.UI.Home.Countery_Save;
import com.example.covid19_test.UI.Main.MainActivity;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class More_Fragment extends Fragment  {

    View view;

    public More_Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more, container, false);
        return view;
    }

}

package com.example.covid19_test.UI.Countries;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.covid19_test.Pojo.countries.Countries;
import com.example.covid19_test.R;
import com.example.covid19_test.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Countiry_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Countries> Countries = new ArrayList<>();
    Activity activity;
    onCountryListener mOnCountryLister;

    public Countiry_Adapter(Activity activity, List<Countries> Countries , onCountryListener mOnClickListener) {
        this.activity = activity;
        this.Countries = Countries;
        this.mOnCountryLister =  mOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countiry_item, parent, false);
        Covid_ViewHolder Covid_ViewHolder = new Covid_ViewHolder(view , mOnCountryLister);
        return Covid_ViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            //to split the name of countries that have tall name
            String message = Countries.get(position).getCountry().replaceAll(" ", "\n");

            ((Covid_ViewHolder) holder).Cases.setText(Utilities.DecimalFormatter(Countries.get(position).getCases()));
            ((Covid_ViewHolder) holder).deaths.setText(Utilities.DecimalFormatter(Countries.get(position).getDeaths()));
            ((Covid_ViewHolder) holder).recovered.setText(Utilities.DecimalFormatter(Countries.get(position).getRecovered()));
            ((Covid_ViewHolder) holder).Country_Name.setText(message);
            Picasso.get()
                    .load(Countries.get(position).getCountryInfo().getFlag())
                    .resize(80, 60)
                    .centerCrop()
                    .into(((Covid_ViewHolder) holder).CountryFlag);
    }

    @Override
    public int getItemCount() {
        return Countries.size();
    }

    // function of searching
    public void filterList(ArrayList<Countries> filteredList) {
        Countries = filteredList;
        notifyDataSetChanged();
    }

    class Covid_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Cases, deaths, recovered, Country_Name;
        ImageView CountryFlag;
        onCountryListener mOnCountryListener;
        public Covid_ViewHolder(@NonNull View itemView, onCountryListener mOnCountryListener) {
            super(itemView);
            Cases = itemView.findViewById(R.id.Caese_rv);
            deaths = itemView.findViewById(R.id.Death_rv);
            recovered = itemView.findViewById(R.id.recoverd_rv);
            Country_Name = itemView.findViewById(R.id.Countriey_Name_rv);
            CountryFlag = itemView.findViewById(R.id.Countriey_Flag_rv);
            this.mOnCountryListener = mOnCountryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnCountryListener.selectedCountry(Countries.get(getAdapterPosition()));
        }
    }
    // interface to make items in recycler clickable
    public interface onCountryListener {
        void selectedCountry(Countries countries);
    }
}




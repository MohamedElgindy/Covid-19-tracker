package com.example.covid19_test.UI.PopUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.covid19_test.R;
import com.example.covid19_test.Pojo.countries.Countries;
import com.squareup.picasso.Picasso;

import java.util.List;



public class Custom_Spinner_Adapter extends ArrayAdapter<Countries> {

    public Custom_Spinner_Adapter(Context context, List<Countries> countryList) {
        super(context, 0, countryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_spinner_row, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.Countriey_Flag);
        TextView textViewName = convertView.findViewById(R.id.Countriey_Name);

        Countries currentItem = getItem(position);

        if (currentItem != null) {
            Picasso.get()
                    .load(currentItem.getCountryInfo().getFlag())
                    .resize(80, 50)
                    .centerCrop()
                    .into(imageViewFlag);
            textViewName.setText(currentItem.getCountry());

        }

        return convertView;
    }
}

package com.example.covid19_test.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.covid19_test.R;
import com.onurkagan.ksnack_lib.Animations.Fade;
import com.onurkagan.ksnack_lib.MinimalKSnack.MinimalKSnack;
import com.onurkagan.ksnack_lib.MinimalKSnack.MinimalKSnackStyle;

import java.text.DecimalFormat;

 public final class Utilities {

    static final DecimalFormat decimalFormat = new DecimalFormat("###,###");
    static MinimalKSnack minimalKSnack;

    public static String DecimalFormatter(double decimalNumber)
    {
    return String.valueOf(decimalFormat.format(decimalNumber));
    }

    public static void TopBar( Activity activity , String message)
    {
       minimalKSnack = new MinimalKSnack(activity);
       minimalKSnack.setMessage(message)
               .setStyle(MinimalKSnackStyle.STYLE_SUCCESS)
               .setBackgroundColor(R.color.red)
               .setAnimation(Fade.In.getAnimation(), Fade.Out.getAnimation()) // show and hide animations
               .show();
    }
    public static void endTopBar()
    {minimalKSnack.dismiss();
    }



 }

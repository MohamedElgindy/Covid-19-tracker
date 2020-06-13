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

public class Custom_ViewDialog {
    Activity activity;
    Dialog dialog;
    public Custom_ViewDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {
        dialog  = new Dialog(activity);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView gifImageView = dialog.findViewById(R.id.imageView2);
        Glide.with(activity)
                .load(R.drawable.stayhome_loading)
                .placeholder(R.drawable.stayhome_loading)
                .centerCrop()
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(gifImageView));

        dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }
}

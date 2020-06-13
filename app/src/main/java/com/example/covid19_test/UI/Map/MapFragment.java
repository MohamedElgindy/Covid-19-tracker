package com.example.covid19_test.UI.Map;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19_test.R;
import com.example.covid19_test.Utilities.Custom_ViewDialog;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements HandleBackPress {

    private View view;
    private WebView mCovidWebView;
    TextView mNoInternetMessage;
    Button mRetry;
    Custom_ViewDialog Map_ViewDialog;
    // to check if the view is already loaded don't load it again
    int loaded;

    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        initViews();

        Map_ViewDialog = new Custom_ViewDialog(getActivity());
        loaded = 0;
        return view;
    }

    private void initViews() {
        mNoInternetMessage = view.findViewById(R.id.NoInternet_TV);
        mRetry = view.findViewById(R.id.Retry_button);
        mCovidWebView = view.findViewById(R.id.webview);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            // animate here

            if (loaded == 0){
            LoadView(getActivity());
            }
            mRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoadView(getActivity());
                }
            });
        }else{
            // fragment is no longer visible
        }
    }

     void LoadView(Activity activity){
        loaded = 1;
        WebSettings settings = mCovidWebView.getSettings();
        mCovidWebView.clearCache(true);
        settings.setJavaScriptEnabled(true);
        mCovidWebView.setWebViewClient(new WebViewClient());
        mCovidWebView.loadUrl("https://www.bing.com/covid");
        if (!checkInternetConnection(activity)) {
            mNoInternetMessage.setVisibility(View.VISIBLE);
            mRetry.setVisibility(View.VISIBLE);
            mCovidWebView.setVisibility(View.INVISIBLE);
        } else {
            mRetry.setVisibility(View.INVISIBLE);
            mNoInternetMessage.setVisibility(View.INVISIBLE);
            mCovidWebView.setVisibility(View.VISIBLE);
            mCovidWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    Log.d("WebView", "onPageStarted " + url);
                    Map_ViewDialog.showDialog();

                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.d("WebView", "onPageFinished " + url);
                    Map_ViewDialog.hideDialog();
                }
            });

        }

    }

    @Override
    public boolean onBackPressed() {
        if (mCovidWebView.canGoBack()) {
            mCovidWebView.goBack();
            return true;
        } else {
            return false;
        }

    }

    public static boolean checkInternetConnection(Activity activity) {

        ConnectivityManager con_manager = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected());
    }

//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            // load data here
//        }else{
//            // fragment is no longer visible
//        }
//    }

}

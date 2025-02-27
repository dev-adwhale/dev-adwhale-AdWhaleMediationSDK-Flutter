package com.example.adwhale_flutter_sample;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import net.adwhale.sdk.mediation.ads.AdWhaleMediationInterstitialAd;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationInterstitialAdListener;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import net.adwhale.sdk.mediation.ads.AdWhaleMediationInterstitialAd;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationInterstitialAdListener;


public class FlutterInterstitialAdLoader {
    private static final String TAG = "FlutterInterstitialAdLoader";
    @NonNull
    private final Context context;

    public FlutterInterstitialAdLoader(@NonNull Context context) {
        this.context = context;
    }

    /**
     * Load an interstitial ad.
     */
    public AdWhaleMediationInterstitialAd loadInterstitial(
            @NonNull String appCode,
            @NonNull AdWhaleMediationInterstitialAdListener loadCallback) {

        if(loadCallback == null) {
            Log.e(TAG, "loadCallback is null.");
        }
        if(!(loadCallback instanceof AdWhaleMediationInterstitialAdListener)){
            Log.e(TAG, "loadCallback is not type AdwhaleMediationInterstitialAdListener.");
        }
        AdWhaleMediationInterstitialAd interstitialAd = new AdWhaleMediationInterstitialAd(appCode);
        interstitialAd.setAdWhaleMediationInterstitialAdListener(loadCallback);
        interstitialAd.loadAd();

        return interstitialAd;
    }
}

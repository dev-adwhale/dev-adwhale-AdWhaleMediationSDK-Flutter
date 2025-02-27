package com.example.adwhale_flutter_sample;

import android.util.Log;

import androidx.annotation.NonNull;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationAdView;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationAdViewListener;

public class FlutterBannerAdListener implements AdWhaleMediationAdViewListener {
    protected final int adId;
    @NonNull
    protected final AdInstanceManager manager;
    private static final String TAG = "FlutterBannerAd";
    FlutterBannerAdListener(int adId, @NonNull AdInstanceManager manager) {
        this.adId = adId;
        this.manager = manager;
    }

    @Override
    public void onAdLoaded() {
        Log.d(TAG, "FlutterBannerAd onAdLoaded");
        manager.onAdLoaded(adId);
    }

    @Override
    public void onAdLoadFailed(int i, String s) {
        Log.d(TAG, "FlutterBannerAd onAdLoadFailed");
        manager.onAdLoadFailed(adId, i, s);
    }

    @Override
    public void onAdClicked() {
        Log.d(TAG, "FlutterBannerAd onAdClicked");
        manager.onAdClicked(adId);
    }

}

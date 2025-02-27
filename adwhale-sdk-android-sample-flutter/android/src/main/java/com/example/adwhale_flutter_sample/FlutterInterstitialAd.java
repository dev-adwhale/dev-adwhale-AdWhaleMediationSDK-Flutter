package com.example.adwhale_flutter_sample;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.adwhale.sdk.mediation.ads.AdWhaleMediationInterstitialAd;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationInterstitialAdListener;

import java.lang.ref.WeakReference;

public class FlutterInterstitialAd extends FlutterAd.FlutterOverlayAd {
    private static final String TAG = "FlutterInterstitialAd";

    @NonNull
    private final AdInstanceManager manager;

    @NonNull
    private final String appCode;

    @Nullable
    private AdWhaleMediationInterstitialAd ad;
    @NonNull
    private final FlutterInterstitialAdLoader flutterInterstitialAdLoader;

    public FlutterInterstitialAd(
            int adId,
            @NonNull AdInstanceManager manager,
            @NonNull String appCode,
            @NonNull FlutterInterstitialAdLoader flutterInterstitialAdLoader) {
        super(adId);
        Log.d(TAG, "FlutterInterstitialAd()");
        this.manager = manager;
        this.appCode = appCode;
        this.flutterInterstitialAdLoader = flutterInterstitialAdLoader;
    }

    @Override
    void load() {
        Log.d(TAG, "FlutterInterstitialAd load()");
        Log.d(TAG, "FlutterInterstitialAd load() appCode : " + appCode);
        ad = flutterInterstitialAdLoader.loadInterstitial(appCode,
                new DelegatingInterstitialAdLoadCallback(this));
    }


    public void onInterstitialAdLoaded() {
        Log.d(TAG, "FlutterInterstitialAd onAdLoaded()");
        manager.onInterstitialAdLoaded(adId);
    }

    public void onInterstitialAdLoadFailed(int errorCode, String errorMessage) {
        Log.d(TAG, "FlutterInterstitialAd onAdLoadFailed()");
        manager.onInterstitialAdLoadFailed(adId, errorCode, errorMessage);
    }

    public void onInterstitialAdShowed() {
        Log.d(TAG, "FlutterInterstitialAd onAdShowed()");
        manager.onInterstitialAdShowed(adId);
    }
    public void onInterstitialAdClicked() {
        Log.d(TAG, "FlutterInterstitialAd onAdClicked()");
        manager.onInterstitialAdClicked(adId);
    }

    public void onInterstitialAdShowFailed(int errorCode, String errorMessage) {
        Log.d(TAG, "FlutterInterstitialAd onAdShowFailed()");
        manager.onInterstitialAdShowFailed(adId, errorCode, errorMessage);
    }

    public void onInterstitialAdClosed() {
        Log.d(TAG, "FlutterInterstitialAd onAdClosed()");
        manager.onInterstitialAdClosed(adId);
    }

    @Override
    void dispose() {
        ad = null;
    }

    @Override
    void show() {
        if (ad == null) {
            Log.e(TAG, "Error showing interstitial - the interstitial ad wasn't loaded yet.");
            return;
        }
        if (manager.getActivity() == null) {
            Log.e(TAG, "Tried to show interstitial before activity was bound to the plugin.");
            return;
        }
        ad.showAd();
    }


    /**
     * An InterstitialAdLoadCallback that just forwards events to a delegate.
     */
    private static final class DelegatingInterstitialAdLoadCallback implements AdWhaleMediationInterstitialAdListener {

        private final WeakReference<FlutterInterstitialAd> delegate;

        DelegatingInterstitialAdLoadCallback(FlutterInterstitialAd delegate) {
            this.delegate = new WeakReference<>(delegate);
            Log.d(TAG, "DelegatingInterstitialAdLoadCallback()");
        }

        @Override
        public void onAdLoaded() {
            Log.d(TAG, "DelegatingInterstitialAdLoadCallback onAdLoaded()");
            if(delegate.get() != null){
                delegate.get().onInterstitialAdLoaded();
            }
        }

        @Override
        public void onAdLoadFailed(int errorCode, String errorMessage) {
            Log.d(TAG, "DelegatingInterstitialAdLoadCallback onAdLoadFailed()");
            if(delegate.get() != null){
                delegate.get().onInterstitialAdLoadFailed(errorCode, errorMessage);
            }
        }

        @Override
        public void onAdShowed() {
            Log.d(TAG, "DelegatingInterstitialAdLoadCallback onAdShowed()");
            if(delegate.get() != null){
                delegate.get().onInterstitialAdShowed();
            }
        }

        @Override
        public void onAdClicked() {
            Log.d(TAG, "DelegatingInterstitialAdLoadCallback onAdClicked()");
            if(delegate.get() != null){
                delegate.get().onInterstitialAdClicked();
            }
        }

        @Override
        public void onAdShowFailed(int errorCode, String errorMessage) {
            Log.d(TAG, "DelegatingInterstitialAdLoadCallback onAdShowFailed()");
            if(delegate.get() != null){
                delegate.get().onInterstitialAdShowFailed(errorCode, errorMessage);
            }
        }

        @Override
        public void onAdClosed() {
            Log.d(TAG, "DelegatingInterstitialAdLoadCallback onAdClosed()");
            if(delegate.get() != null){
                delegate.get().onInterstitialAdClosed();
            }
        }
    }
}


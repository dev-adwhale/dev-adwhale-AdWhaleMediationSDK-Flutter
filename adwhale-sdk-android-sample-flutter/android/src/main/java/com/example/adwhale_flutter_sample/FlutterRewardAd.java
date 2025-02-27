package com.example.adwhale_flutter_sample;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.adwhale.sdk.mediation.ads.AdWhaleMediationFullScreenContentCallback;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationRewardAd;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationRewardItem;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationRewardedAdLoadCallback;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationUserEarnedRewardListener;

import java.lang.ref.WeakReference;

public class FlutterRewardAd extends FlutterAd.FlutterOverlayAd{

    private static final String TAG = "FlutterRewardAd";

    @NonNull
    private final AdInstanceManager manager;

    @NonNull
    private final String appCode;

    @Nullable
    private AdWhaleMediationRewardAd ad;
    @NonNull
    private final FlutterRewardAdLoader flutterRewardAdLoader;

    public FlutterRewardAd(
            int adId,
            @NonNull AdInstanceManager manager,
            @NonNull String appCode,
            @NonNull FlutterRewardAdLoader flutterRewardAdLoader) {
        super(adId);
        Log.d(TAG, "FlutterRewardAdLoader()");
        this.manager = manager;
        this.appCode = appCode;
        this.flutterRewardAdLoader = flutterRewardAdLoader;
    }
    @Override
    void load() {
        Log.d(TAG, "FlutterRewardAd load()");
        Log.d(TAG, "FlutterRewardAd load() appCode : " + appCode);
        ad = flutterRewardAdLoader.loadReward(appCode,
                new DelegatingRewardAdFullScreenContentCallback(this), new DelegatingRewardAdLoadCallback(this));
    }

    public void onRewardAdLoaded() {
        Log.d(TAG, "FlutterRewardAd onAdLoaded()");
        manager.onRewardAdLoaded(adId);
    }

    public void onRewardAdFailedToLoad(int errorCode, String errorMessage) {
        Log.d(TAG, "FlutterRewardAd onAdFailedToLoad()");
        manager.onRewardAdFailedToLoad(adId, errorCode, errorMessage);
    }

    public void onUserRewarded(int i, String s){
        Log.d(TAG, "FlutterRewardAd.onUserRewarded()");
        manager.onUserRewarded(adId, i,s);
    }

    public void onRewardAdClicked() {
        Log.d(TAG, "FlutterRewardAd onAdClicked()");
        manager.onRewardAdClicked(adId);
    }
    public void onRewardAdDismissed() {
        Log.d(TAG, "FlutterRewardAd onAdDismissed()");
        manager.onRewardAdDismissed(adId);
    }
    public void onRewardFailedToShow(int errorCode, String errorMessage) {
        Log.d(TAG, "FlutterRewardAd onFailedToShow()");
        manager.onRewardFailedToShow(adId, errorCode, errorMessage);
    }
    public void onRewardAdShowed() {
        Log.d(TAG, "FlutterRewardAd onAdShowed()");
        manager.onRewardAdShowed(adId);
    }

    @Override
    void show() {
        if (ad == null) {
            Log.e(TAG, "Error showing reward - the reward ad wasn't loaded yet.");
            return;
        }
        if (manager.getActivity() == null) {
            Log.e(TAG, "Tried to show reward before activity was bound to the plugin.");
            return;
        }
        ad.showAd(new DelegatingRewardAdUserEarnedCallback(this));
    }

    @Override
    void dispose() {
        ad = null;
    }

    private static final class DelegatingRewardAdUserEarnedCallback implements AdWhaleMediationUserEarnedRewardListener{

        private final WeakReference<FlutterRewardAd> delegate;

        DelegatingRewardAdUserEarnedCallback(FlutterRewardAd delegate) {
            this.delegate = new WeakReference<>(delegate);
            Log.d(TAG, "DelegatingRewardAdUserEarnedCallback()");
        }

        @Override
        public void onUserRewarded(AdWhaleMediationRewardItem adWhaleMediationRewardItem) {
            Log.d(TAG, "DelegatingRewardAdUserEarnedCallback.onUserRewarded()");
            Log.d(TAG, "DelegatingRewardAdUserEarnedCallback.onUserRewarded() amount : " + adWhaleMediationRewardItem.getRewardAmount());
            Log.d(TAG, "DelegatingRewardAdUserEarnedCallback.onUserRewarded() type : " + adWhaleMediationRewardItem.getRewardType());
            if(delegate.get() != null){
                delegate.get().onUserRewarded(adWhaleMediationRewardItem.getRewardAmount(), adWhaleMediationRewardItem.getRewardType());
            }
        }
    }
    private static final class DelegatingRewardAdFullScreenContentCallback implements AdWhaleMediationFullScreenContentCallback{

        private final WeakReference<FlutterRewardAd> delegate;

        DelegatingRewardAdFullScreenContentCallback(FlutterRewardAd delegate) {
            this.delegate = new WeakReference<>(delegate);
            Log.d(TAG, "DelegatingRewardAdFullScreenContentCallback()");
        }

        @Override
        public void onAdClicked() {
            Log.d(TAG, "DelegatingRewardAdFullScreenContentCallback.onAdClicked()");
            if(delegate.get() != null){
                delegate.get().onRewardAdClicked();
            }
        }

        @Override
        public void onAdDismissed() {
            Log.d(TAG, "DelegatingRewardAdFullScreenContentCallback.onAdDismissed()");
            if(delegate.get() != null){
                delegate.get().onRewardAdDismissed();
            }
        }

        @Override
        public void onFailedToShow(int i, String s) {
            Log.d(TAG, "DelegatingRewardAdFullScreenContentCallback.onFailedToShow()");
            if(delegate.get() != null){
                delegate.get().onRewardFailedToShow(i,s);
            }
        }

        @Override
        public void onAdShowed() {
            Log.d(TAG, "DelegatingRewardAdFullScreenContentCallback.onAdShowed()");
            if(delegate.get() != null){
                delegate.get().onRewardAdShowed();
            }
        }

    }
    private static final class DelegatingRewardAdLoadCallback implements AdWhaleMediationRewardedAdLoadCallback{

        private final WeakReference<FlutterRewardAd> delegate;

        DelegatingRewardAdLoadCallback(FlutterRewardAd delegate) {
            this.delegate = new WeakReference<>(delegate);
            Log.d(TAG, "DelegatingRewardAdAdLoadCallback()");
        }
        @Override
        public void onAdLoaded(AdWhaleMediationRewardAd adWhaleMediationRewardAd, String s) {
            Log.d(TAG, "DelegatingRewardAdLoadCallback.onAdLoaded()");
            if(delegate.get() != null){
                delegate.get().onRewardAdLoaded();
            }
        }

        @Override
        public void onAdFailedToLoad(int i, String s) {
            Log.d(TAG, "DelegatingRewardAdLoadCallback.onAdFailedToLoad()");
            if(delegate.get() != null){
                delegate.get().onRewardAdFailedToLoad(i, s);
            }
        }

    }

}

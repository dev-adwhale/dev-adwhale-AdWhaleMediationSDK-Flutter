package com.example.adwhale_flutter_sample;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import net.adwhale.sdk.mediation.ads.AdWhaleMediationFullScreenContentCallback;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationRewardAd;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationRewardedAdLoadCallback;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationUserEarnedRewardListener;

public class FlutterRewardAdLoader {

    private static final String TAG = "FlutterInterstitialAdLoader";
    @NonNull
    private final Context context;

    public FlutterRewardAdLoader(@NonNull Context context) {
        this.context = context;
    }

    /**
     * Load an interstitial ad.
     */
    public AdWhaleMediationRewardAd loadReward(
            @NonNull String appCode,
            @NonNull AdWhaleMediationFullScreenContentCallback showCallback,
            @NonNull AdWhaleMediationRewardedAdLoadCallback loadCallback) {

        if(loadCallback == null) {
            Log.e(TAG, "loadCallback is null.");
        }
        if(showCallback == null) {
            Log.e(TAG, "loadCallback is null.");
        }
        if(!(showCallback instanceof AdWhaleMediationFullScreenContentCallback)){
            Log.e(TAG, "loadCallback is not type AdWhaleMediationFullScreenContentCallback.");
        }
        if(!(loadCallback instanceof AdWhaleMediationRewardedAdLoadCallback)){
            Log.e(TAG, "loadCallback is not type AdwhaleMediationInterstitialAdListener.");
        }

        AdWhaleMediationRewardAd rewardAd = new AdWhaleMediationRewardAd(appCode);
        rewardAd.setAdWhaleMediationFullScreenContentCallback(showCallback);
        rewardAd.loadAd(loadCallback);

        return rewardAd;
    }

}

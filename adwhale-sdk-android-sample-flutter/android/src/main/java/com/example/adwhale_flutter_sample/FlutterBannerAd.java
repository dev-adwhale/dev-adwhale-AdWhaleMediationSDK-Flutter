package com.example.adwhale_flutter_sample;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.adwhale.sdk.mediation.ads.ADWHALE_AD_SIZE;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationAdView;
import net.adwhale.sdk.mediation.ads.AdWhaleMediationAdViewListener;

import io.flutter.plugin.platform.PlatformView;

public class FlutterBannerAd extends FlutterAd {
    private static final String TAG = "FlutterBannerAd";
    @NonNull
    private final Context context;
    @NonNull
    private final RelativeLayout rootView;
    @NonNull
    private final AdInstanceManager manager;
    @NonNull
    private final FlutterAdInfo adInfo;
    @NonNull
    private final BannerAdCreator bannerAdCreator;
    @Nullable
    private AdWhaleMediationAdView adWhaleMediationAdView;

    public FlutterBannerAd(
            int adId,
            @NonNull Context context,
            @NonNull AdInstanceManager manager,
            @NonNull FlutterAdInfo adInfo,
            @NonNull BannerAdCreator bannerAdCreator) {
        super(adId);
        this.context = context;
        this.manager = manager;
        this.adInfo = adInfo;
        this.bannerAdCreator = bannerAdCreator;
        this.rootView = new RelativeLayout(context);
    }

    @Override
    void load() {
        clearView();
        adWhaleMediationAdView = bannerAdCreator.createAdView();
        if (adWhaleMediationAdView == null) {
            Log.e(TAG, "createAdView() = null");
            return;
        }

        adWhaleMediationAdView.setPlacementUid(adInfo.getAppCode());
        Log.d(TAG, "FlutterBannerAd load()");
        Log.d(TAG, "FlutterBannerAd load() appCode : " + adInfo.getAppCode());

        ADWHALE_AD_SIZE adSize;
        int width = adInfo.getBannerSizeWidth();
        int height = adInfo.getBannerSizeHeight();
        if (width == 320 && height == 50) {
            adSize = ADWHALE_AD_SIZE.BANNER320x50;
        } else if (width == 320 && height == 100) {
            adSize = ADWHALE_AD_SIZE.BANNER320x100;
        } else if (width == 300 && height == 250) {
            adSize = ADWHALE_AD_SIZE.BANNER300x250;
        } else if (width == 250 && height == 250) {
            adSize = ADWHALE_AD_SIZE.BANNER250x250;
        } else {
            adSize = ADWHALE_AD_SIZE.BANNER320x50;
        }
        adWhaleMediationAdView.setAdwhaleAdSize(adSize);
        adWhaleMediationAdView.setAdWhaleMediationAdViewListener(new FlutterBannerAdListener(adId, manager));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rootView.addView(adWhaleMediationAdView, params);

        if (adWhaleMediationAdView != null) {
            adWhaleMediationAdView.loadAd();
        }
        rootView.invalidate();
        rootView.requestLayout();
    }

    @Nullable
    @Override
    PlatformView getPlatformView() {
        if (adWhaleMediationAdView == null) {
            return null;
        }
        return new FlutterPlatformView(rootView);
    }

    @Override
    void dispose() {
        clearView();
    }

    void clearView() {
        if (adWhaleMediationAdView != null) {
            adWhaleMediationAdView.destroy();
            rootView.removeView(adWhaleMediationAdView);
            adWhaleMediationAdView = null;
        }
    }
}
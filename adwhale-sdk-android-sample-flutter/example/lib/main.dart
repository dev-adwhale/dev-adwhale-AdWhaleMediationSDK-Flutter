import 'dart:developer';
import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';
import 'package:adwhale_flutter_sample/adwhale_mobile_ads.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  MobileAds.instance.initialize();
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);
  
  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  BannerAd? _banner;
  InterstitialAd? _interstitialAd;
  RewardAd? _rewardAd;
  
  @override
  void initState() {
    super.initState();
    // 앱 시작 시에는 배너 광고를 로드하지 않습니다.
  }

  /// 전달받은 width와 height 값을 AdInfo에 전달하여 BannerAd를 생성 및 로드
  void _createBannerWithSize(int width, int height) {
    String placementUid = "발급받은 placement uid 값";
    print('FlutterBannerAd load() appcode : $placementUid');
    print('FlutterCreating BannerAd with size ${width}x${height}');
    _banner = BannerAd(
      listener: BannerAdListener(
        onReceiveAd: (ad) {
          print('FlutterBannerAd onReceiveAd for size ${width}x${height} received');
          print('FlutterBannerAd onReceiveAd for size ${width}x${height} received');
          setState(() {}); // 광고 로드 성공 시 UI 갱신
        },
        onFailedToReceiveAd: (ad, errorCode, errorMessage) {
          print(
              'BannerAd onFailedToReceiveAd for size ${width}x${height}: errorCode: $errorCode, errorMessage: $errorMessage');
          ad.dispose();
          setState(() {
            _banner = null;
          });
        },
        onCloseLandingScreen: (ad) {
          print('FlutterBannerAd onCloseLandingScreen for size ${width}x${height}');
        },
        onShowLandingScreen: (ad) {
          print('FlutterBannerAd onShowLandingScreen for size ${width}x${height}');
        },
      ),
      // AdInfo에 배너 사이즈 정보를 전달합니다.
      adInfo: AdInfo(placementUid, BannerHeightEnum.adaptive, width, height),
    )..load();
    print('FlutterBannerAd load() called for size ${width}x${height}');

  }
  
  /// 기존 배너를 제거한 후, 지정한 사이즈의 새 배너 광고를 호출합니다.
  void _showBannerAd(int width, int height) {
    print('FlutterBannerAd _showBannerAd() called for size ${width}x${height}');
    if (_banner != null) {
      print('FlutterDisposing existing banner');
      _banner!.dispose();
      _banner = null;
    }
    setState(() {}); // UI에 기존 배너 제거 반영
    _createBannerWithSize(width, height);
  }

  void _createReward() {
    print('FlutterRewardAd _createReward()');
    String placementUid = "발급받은 placement uid 값";
    print('FlutterRewardAd _createReward() appcode : $placementUid');
    _rewardAd = RewardAd(
      appCode: placementUid,
      adRewardLoadCallback: RewardAdLoadCallback(
        onRewardAdLoaded: () {
          print('FlutterRewardAd onAdLoaded');
          _rewardAd!.show();
        },
        onRewardAdFailedToLoad: (errorCode, errorMessage) {
          print('FlutterRewardAd onAdFailedToLoad: errorCode: $errorCode, errorMessage: $errorMessage');
          _rewardAd = null;
        },
        onUserRewarded: (amount, type) {
          print('FlutterRewardAd onUserRewarded');
          print('FlutterRewardAd onUserRewarded: amount: $amount, type: $type');
        },
        onRewardAdClicked: () {
          print('FlutterRewardAd onAdClicked');
        },
        onRewardAdShowed: () {
          print('FlutterRewardAd onAdShowed');
          _rewardAd = null;
        },
        onRewardFailedToShow: (String errorCode, String errorMessage) {
          print('FlutterRewardAd onFailedToShow: errorCode: $errorCode, errorMessage: $errorMessage');
        },
        onRewardAdDismissed: () {
          print('FlutterRewardAd onDismissed');
        },
      ),
    );
    _rewardAd!.load();
  }

  void _createInterstitialAd() {
    print('FlutterInterstitialAd _createInterstitialAd()');
    String placementUid = "발급받은 placement uid 값";
    print('FlutterInterstitialAd _createInterstitialAd() appcode : $placementUid');
    _interstitialAd = InterstitialAd(
      appCode: placementUid,
      adLoadCallback: InterstitialAdLoadCallback(
        onInterstitialAdLoaded: () {
          print('FlutterInterstitialAd onAdLoaded');
          _interstitialAd!.show();
        },
        onInterstitialAdLoadFailed: (errorCode, errorMessage) {
          print('FlutterInterstitialAd onAdLoadFailed: $errorMessage');
          _interstitialAd = null;
        },
        onInterstitialAdShowed: () {
          print('FlutterInterstitialAd onAdShowed');
        },
        onInterstitialAdShowFailed: (errorCode, errorMessage) {
          print('FlutterInterstitialAd onAdShowFailed: $errorMessage');
          _interstitialAd = null;
        },
        onInterstitialAdClosed: () {
          print('FlutterInterstitialAd onAdClosed');
        },
        onInterstitialAdClicked: () {
          print('FlutterInterstitialAd onAdClicked');
        },

      ),
    );
    _interstitialAd?.load();
  }
  
  void _showRewardedAd() {
    print('FlutterRewardAd _showRewardedAd()');
    _createReward();
  }
  
  void _showInterstitialAd() {
    print('FlutterInterstitialAd _showInterstitialAd()');
    _createInterstitialAd();
  }
  
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('AdWhale Flutter Sample App')),
        body: Column(
          children: [
            Expanded(
              child: Container(
                color: Colors.amber,
                child: SingleChildScrollView(
                  child: Center(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        ElevatedButton(
                          onPressed: _showInterstitialAd,
                          child: const Text('전면 광고 호출'),
                        ),
                        const SizedBox(height: 16),
                        ElevatedButton(
                          onPressed: _showRewardedAd,
                          child: const Text('리워드 광고 호출'),
                        ),
                        const SizedBox(height: 16),
                        // 버튼 클릭 시 미리 정해진 사이즈로 배너 광고 호출
                        ElevatedButton(
                          onPressed: () => _showBannerAd(320, 50),
                          child: const Text('배너 320×50 생성'),
                        ),
                        const SizedBox(height: 16),
                        ElevatedButton(
                          onPressed: () => _showBannerAd(320, 100),
                          child: const Text('배너 320×100 생성'),
                        ),
                        const SizedBox(height: 16),
                        ElevatedButton(
                          onPressed: () => _showBannerAd(300, 250),
                          child: const Text('배너 300×250 생성'),
                        ),
                        const SizedBox(height: 16),
                        ElevatedButton(
                          onPressed: () => _showBannerAd(250, 250),
                          child: const Text('배너 250×250 생성'),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ),
            // 배너 광고가 로드되면 하단에 표시
            _banner == null
                ? const SizedBox.shrink()
                : SizedBox(
                    height: _banner!.bannerSizeHeight.toDouble(),
                    child: AdWidget(
                      key: ValueKey(_banner!.hashCode),
                      ad: _banner!,
                    ),
                  ),
          ],
        ),
      ),
    );
  }
  
  @override
  void dispose() {
    _banner?.dispose();
    super.dispose();
  }
}

import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:adwhale_flutter_sample/adwhale_flutter_sample_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelAdwhaleFlutterSample platform = MethodChannelAdwhaleFlutterSample();
  const MethodChannel channel = MethodChannel('adwhale_flutter_sample');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}

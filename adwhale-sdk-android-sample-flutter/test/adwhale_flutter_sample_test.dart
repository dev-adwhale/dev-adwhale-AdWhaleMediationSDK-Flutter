import 'package:flutter_test/flutter_test.dart';
import 'package:adwhale_flutter_sample/adwhale_flutter_sample.dart';
import 'package:adwhale_flutter_sample/adwhale_flutter_sample_platform_interface.dart';
import 'package:adwhale_flutter_sample/adwhale_flutter_sample_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockAdwhaleFlutterSamplePlatform
    with MockPlatformInterfaceMixin
    implements AdwhaleFlutterSamplePlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final AdwhaleFlutterSamplePlatform initialPlatform = AdwhaleFlutterSamplePlatform.instance;

  test('$MethodChannelAdwhaleFlutterSample is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelAdwhaleFlutterSample>());
  });

  test('getPlatformVersion', () async {
    AdwhaleFlutterSample adwhaleFlutterSamplePlugin = AdwhaleFlutterSample();
    MockAdwhaleFlutterSamplePlatform fakePlatform = MockAdwhaleFlutterSamplePlatform();
    AdwhaleFlutterSamplePlatform.instance = fakePlatform;

    expect(await adwhaleFlutterSamplePlugin.getPlatformVersion(), '42');
  });
}

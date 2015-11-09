#RN-Geolocation

**Disclaimer:** This package is based on code from [Corentin Smith](https://gist.github.com/cosmith). All java files were originally written by him. Kudos to you.

RN-Geolocation is a native module for React Native, that exposes the GoogleApi geolocation. It can be used to determine the users location. This repository contains an example of the code (see last section of this read me), but the package can also be installed on its own.

## Installing rn-geolocation in your own project

To use it do:
```
npm install --save rn-geolocation
```

In your settings.gradle replace ```include ':app'``` with:
```
include 'rn-geolocation',':app'
project(':rn-geolocation').projectDir = new File(rootProject.projectDir, '../node_modules/rn-geolocation/android')
```
In your android/app/build.gradle add:
```
dependencies {
  ...
  compile project(':rn-geolocation')
}
```
And finally register the package in your MainActivity.java
```
...
import com.facebook.soloader.SoLoader;
import com.tiagojdferreira.RNGeolocationPackage; // <-- import

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new RNGeolocationPackage()) // <-- import
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        mReactRootView.startReactApplication(mReactInstanceManager, "testGeolocation", null);

        setContentView(mReactRootView);
    }
    ...
}

```

## Running the example

First, clone this repo and go to the example folder and install the project:

```
git clone https://github.com/Tiagojdferreira/rn-geolocation.git
cd rn-geolocation/example
npm install
```

npm install may take a while, as react is being installed.
Once it is done, to run the app on device, do:

```
adb reverse tcp:8081 tcp:8081
react-native run-android
```

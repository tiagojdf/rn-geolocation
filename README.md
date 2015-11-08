RN-Geolocation is a native module for React Native, that exposes the GoogleApi geolocation.

To use it do:
```
npm install --save rn-geolocation
```

In your settings.grade add:
```
include 'rn-geolocation',':app'
project(':rn-geolocation').projectDir = new File(rootProject.projectDir, '../rn-geolocation')
```
In your android/app/build.grade add:
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

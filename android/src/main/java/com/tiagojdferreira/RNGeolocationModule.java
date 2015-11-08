package com.tiagojdferreira;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by corentin on 10/29/15.
 */
public class RNGeolocationModule extends ReactContextBaseJavaModule {
    private GoogleApiClient mGoogleApiClient;

    public RNGeolocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNGeolocationManager";
    }

    /**
     * @param timeout
     * @param maximumAge
     * @param enableHighAccuracy true -> PRIORITY_HIGH_ACCURACY
     *                           false -> PRIORITY_BALANCED_POWER_ACCURACY
     * @param onSuccess
     * @param onError
     */
    @SuppressWarnings("unused")
    @ReactMethod
    public void getCurrentPosition(final Integer timeout,
                                   final Integer maximumAge,
                                   final Boolean enableHighAccuracy,
                                   final Callback onSuccess,
                                   final Callback onError) {
        if (onSuccess == null) {
            Log.e(getName(), "no success callback");
            return;
        }
        if (onError == null) {
            Log.e(getName(), "no error callback");
            return;
        }

        if (mGoogleApiClient == null) {
            Log.d(getName(), "no mGoogleApiClient");
            mGoogleApiClient = new GoogleApiClient.Builder(getReactApplicationContext())
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle connectionHint) {
                            Log.d(getName(), "onConnected");
                            _getLastKnownLocation(onSuccess, onError, timeout,
                                    maximumAge, enableHighAccuracy);
                        }

                        @Override
                        public void onConnectionSuspended(int cause) {
                            Log.e(getName(), "onConnectionSuspended: " + cause);
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.e(getName(), "onConnectionFailed");
                            onError.invoke();
                            mGoogleApiClient.disconnect();
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
        else if (mGoogleApiClient.isConnected()) {
            Log.d(getName(), "mGoogleApiClient is connected");
            _getLastKnownLocation(onSuccess, onError, timeout, maximumAge, enableHighAccuracy);
        }
        else {
            Log.d(getName(), "mGoogleApiClient is connecting");
            mGoogleApiClient.connect();
        }
    }

    private void _getLastKnownLocation(final Callback onSuccess,
                                       final Callback onError,
                                       Integer timeout,
                                       Integer maximumAge,
                                       Boolean enableHighAccuracy) {

        Log.d(getName(), "tiago: calling _getLastKnownLocation");
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.e(getName(), "lastLocation: " + lastLocation);

        if (lastLocation == null) {
            Log.e(getName(), "lastLocation null");
            onError.invoke();
            return;
        }

        Log.e(getName(), "lastLocation found: " + lastLocation.toString());

        WritableNativeMap result = new WritableNativeMap();
        WritableNativeMap coords = new WritableNativeMap();

        coords.putDouble("latitude", lastLocation.getLatitude());
        coords.putDouble("longitude", lastLocation.getLongitude());
        result.putMap("coords", coords);

        onSuccess.invoke(result);
    }

}

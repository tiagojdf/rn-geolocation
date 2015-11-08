/**
* @providesModule rn-geolocation
*/

"use strict";

var Platform = require("Platform");

if (Platform.OS === "android") {
    var Manager = require("react-native").NativeModules.RNGeolocationManager;
    var rn-geolocation = {
        getCurrentPosition: function (onSuccess, onError, options) {
            Manager.getCurrentPosition(options.timeout, options.maximumAge, options.enableHighAccuracy,
                onSuccess, onError);
        },
    };
} else {
    var rn-geolocation = require("Geolocation");
}

module.exports = rn-geolocation;

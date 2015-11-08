/**
* @providesModule rnGeolocation
*/

"use strict";

var Platform = require("Platform");

if (Platform.OS === "android") {
    var Manager = require("react-native").NativeModules.RNGeolocationManager;
    var rnGeolocation = {
        getCurrentPosition: function (onSuccess, onError, options) {
            Manager.getCurrentPosition(options.timeout, options.maximumAge, options.enableHighAccuracy,
                onSuccess, onError);
        },
    };
} else {
    var rnGeolocation = require("Geolocation");
}

module.exports = rnGeolocation;

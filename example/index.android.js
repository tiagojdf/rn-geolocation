/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React = require('react-native');
var rnGeolocation = require('rn-geolocation');
var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
} = React;

var testGeolocation = React.createClass({
  getInitialState: function() {
    return {
      region: {
        longitude: {},
        latitude: {},
      }
    };
  },

  componentDidMount: function() {
    rnGeolocation.getCurrentPosition(
      (position) => this.setState({
        region: {
          longitude: position.coords.longitude,
          latitude: position.coords.latitude,
          latitudeDelta: 0,
          longitudeDelta: 0,
        }
      }),
      (error) => console.log('This is the error ',error),
      {enableHighAccuracy: true, timeout: 20000, maximumAge: 1000}
    );
  },
  render: function() {
    return (
      <View style={styles.container}>
        <Text>
          <Text style={styles.title}>Current position: </Text>
          {`latitude: ${this.state.region.latitude}, longitude: ${this.state.region.longitude}`}
        </Text>
      </View>
    );
  }
});

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  title: {
    fontWeight: '500',
  },
});

AppRegistry.registerComponent('testGeolocation', () => testGeolocation);

#!/usr/bin/env node

// This script can switch network location

var path = require('path');
var fs = require('fs');

// wait for signal {
    process.stdin.resume();
    process.on('SIGINT', function () {
        console.log('Got a SIGINT. Goodbye cruel world');
        process.exit(0);
    });
// }

// run command in js
//
var child_process = require('child_process');


child_process.exec('networksetup -getcurrentlocation', function (err, stdout, stderr) {
    console.log('Current location: ' + stdout);
});

child_process.exec('networksetup -listlocations', function (err, stdout, stderr) {
    if (err !== null) {
        console.log('exec error: ' + error);
    }
    console.log('Locations List: ');
    var locations = stdout.split('\n')
    for (var loc in locations) {
        if (locations[loc].length != 0) {
            console.log('  ' + loc + '  ' + locations[loc])
        }
    }
    console.log('Select Location Index: (0 or 1)');

    process.stdin.resume();
    process.stdin.setEncoding('utf8');
    process.stdin.on('data', function(data) {
        var index = Number(data);
        if (index < locations.length) {
            console.log(locations[index]);
            child_process.exec('sudo networksetup -switchtolocation '+locations[index], function (err, stdout, stderr) {});
            process.exit(0);
        } else {
            console.log('Error Index\nSelect Location Index: (0 or 1)');
        }
    });
});


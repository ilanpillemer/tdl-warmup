'use strict';

var TDL = require('tdl-client');
var publish = TDL.ClientActions.publish;
var stop = TDL.ClientActions.stop;

var App = require('./app');

// STEP 1. Start the "sum_numbers" challenge from the remote web interface

// ~~~~~~~~~ Setup ~~~~~~~~~

// STEP 2. Set the hostname and email
const HOSTNAME = 'server_hostname';
const EMAIL = 'your_email';

// STEP 3. Run the client in trial mode
const I_AM_READY = false;
/**
 * ~~~  How to run in trial mode~~~
 *
 * From command line:
 *    If you want to trial run: npm start
 *
 * From IDE:
 *    Just Run this class from IDE.
 */
function main(args) {
    var valueFromCommandline = args.length > 0 && args[0] == "ready";
    var ready = I_AM_READY;
    if (valueFromCommandline) { ready = true }

    console.log("Ready ? = %s", ready);

    startClient(ready)
}

// STEP 4. Read the instructions provided on screen. They are also saved as a file under the "challenges" folder.

function startClient(ready) {
    var client = new TDL.Client({hostname: HOSTNAME, uniqueId: EMAIL});

    var rules = new TDL.ProcessingRules();
    rules.on('display_description').call(displayAndSaveDescription).then(publish());
    // STEP 5. Uncomment the following line to register the sum method and run again
    // rules.on('sum').call(App.sum).then(publishIf(ready));

    // STEP 6. Run the test (npm test) and see it fail
    // STEP 7. Fix the sum method implementation in app.js

    client.goLiveWith(rules)
}

function publishIf(ready) {
    if (ready) {
        return publish();
    } else {
        return stop();
    }
}

// STEP 8. If your are satisfied with the implementation, run the client in live mode !
/**
 * ~~~  How to run in live mode~~~
 *
 * From command line:
 *    If you are ready to go live:  npm start ready
 *
 * From IDE:
 *    Set the I_AM_READY variable to "true" and run this file
 */

// STEP 9. Go to the web interface and mark the challenge as done


//~~~~~~~ Provided implementations ~~~~~~~~~~~~~~

function displayAndSaveDescription(label, description) {
    console.log('Starting round: %s', label);
    console.log(description);

    var fs = require('fs');
    var outputFile = require('path').resolve(__dirname, '../challenges')+"/"+label+".txt";
    fs.writeFileSync(outputFile, description);
    console.log("Challenge description saved to file: "+outputFile+".");

    return 'OK'
}

// ~~~~~~~~~ Run ~~~~~~~~~

var argsWithoutScriptName = process.argv.slice(2, process.argv.length);
main(argsWithoutScriptName);


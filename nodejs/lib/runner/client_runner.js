'use strict';

var TDL = require('tdl-client');
var publish = TDL.ClientActions.publish;

var RunnerActions = require('./runner_actions');
var sum = require('./../solutions/sum');
var hello = require('./../solutions/hello');
var fizz_buzz = require('./../solutions/fizz_buzz');

// ~~~~~~~~~ Setup ~~~~~~~~~

function startClient(args, options) {
    if (options === undefined) options = {};
    if (options.email === undefined) throw new Error("Email not specified.");
    if (options.hostname === undefined) options.hostname = "run.befaster.io";
    if (options.actionIfNoArgs === undefined) options.actionIfNoArgs = RunnerActions.testConnectivity;

    var valueFromArgs = extractActionFrom(args);
    var runnerAction = valueFromArgs === undefined ? options.actionIfNoArgs : valueFromArgs;
    console.log("Chosen action is: %s", runnerAction.name);

    var client = new TDL.Client({hostname: options.hostname , uniqueId: options.email});

    var rules = new TDL.ProcessingRules();
    rules.on('display_description').call(displayAndSaveDescription).then(publish());
    rules.on('sum').call(sum).then(runnerAction.clientAction);
    rules.on('hello').call(hello).then(runnerAction.clientAction);
    rules.on('fizz_buzz').call(fizz_buzz).then(runnerAction.clientAction);

    client.goLiveWith(rules)
}

function extractActionFrom(args) {
    var firstArg = args.length > 0 ? args[0] : "";

    return RunnerActions.all.filter(function(action) {
        return action.name.toLowerCase() === firstArg.toLowerCase();
    }).shift()
}


//~~~~~~~ Provided implementations ~~~~~~~~~~~~~~

function displayAndSaveDescription(label, description) {
    console.log('Starting round: %s', label);
    console.log(description);

    var fs = require('fs');
    var outputFile = require('path').resolve(__dirname, '../../challenges')+"/"+label+".txt";
    fs.writeFileSync(outputFile, description);
    console.log("Challenge description saved to file: "+outputFile+".");

    return 'OK'
}

module.exports = startClient;
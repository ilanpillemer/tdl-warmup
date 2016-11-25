
var RunnerActions = require('./runner/runner_actions');
var startClient = require('./runner/client_runner');


// If you are ready to go live:  npm start ready


var args = process.argv.slice(2, process.argv.length);
startClient(args, {
    email: 'your_email_here',
    hostname: 'hostname_goes_here',
    actionIfNoArgs: RunnerActions.testConnectivity
});
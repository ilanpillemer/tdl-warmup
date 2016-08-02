var App = require('../lib/app');

exports['compute sum'] = function (test) {
    test.equal(App.sum(1, 1), 2);
    test.done();
};

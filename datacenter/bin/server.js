#!/usr/bin/env node

var express = require('express');
var app = express();

var allowCrossDomain = function (req, res, next) {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
    next();
};

app.use(express.bodyParser());
app.use(allowCrossDomain);


var allPatients = [];

app.get('/patients', function (request, response) {
    response.send(JSON.stringify(allPatients));
});

app.post('/patients', function (request, response) {
    console.log("-> new request");
    console.log(request.body);

    var allPatientsFromClient = request.body.filter(function (patient) {
        return patient.id != undefined;
    });
    var allPatientsMerged = allPatientsFromClient;

    allPatients.forEach(function (existingPatient) {
        if (!allPatientsMerged.some(function (mergedPatient) {
            return existingPatient.id == mergedPatient.id;
        })) {
            allPatientsMerged.push(existingPatient);
        }
    });

    allPatients = allPatientsMerged;
    console.log("<- sending response");
    console.log(allPatients);
    response.send(JSON.stringify(allPatients));
});

// For test ONLY
app.get('/patients/clear', function (request, response) {
    allPatients = [];
    response.send('all patients in server are gone');
});

app.listen(8001);
console.log('Listening on port 8001');

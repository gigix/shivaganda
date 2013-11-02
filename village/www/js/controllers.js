'use strict';

var shivagandaControllers = angular.module('shivagandaControllers', []);

shivagandaControllers.controller('PatientListCtrl', function PatientListCtrl($scope, $http, $location) {
    $scope.patients = loadAllPatients();

    $scope.sync = function () {
        $http.post('http://localhost:8001/patients', $scope.patients).
            success(function (data, status, headers, config) {
                saveAllPatients(data);
                $location.path('/');
            }).
            error(function (data, status, headers, config) {
                alert('error: ' + data);
            });
    };

    $scope.reset = function () {
        localStorage.clear();
    };
});

shivagandaControllers.controller('PatientNewCtrl', function PatientNewCtrl() {

});

shivagandaControllers.controller('PatientCreateCtrl', function PatientCreateCtrl($scope, $location) {
    $scope.save = function () {
        var allPatients = loadAllPatients();
        var newPatient = $scope.patient;
        newPatient.id = new Date().getTime();
        allPatients.push(newPatient);
        saveAllPatients(allPatients);
        $location.path('/');
    };
});

shivagandaControllers.controller('PatientDetailCtrl', function PatientDetailCtrl($scope) {

});

function loadAllPatients() {
    var allPatients;
    try {
        allPatients = JSON.parse(localStorage.patients);
    } catch (err) {
        allPatients = [];
    }
    return allPatients;
}

function saveAllPatients(allPatients) {
    localStorage.patients = JSON.stringify(allPatients);
}
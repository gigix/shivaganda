'use strict';

var shivagandaControllers = angular.module('shivagandaControllers', []);

shivagandaControllers.controller('PatientListCtrl', function PatientListCtrl($scope) {
    $scope.patients = loadAllPatients();

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
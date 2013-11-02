'use strict';

//var datacenterDomain = 'http://localhost:8001';
var datacenterDomain = 'http://shivaganda-datacenter.nodejitsu.com';

var shivagandaVillageControllers = angular.module('shivagandaVillageControllers', []);

shivagandaVillageControllers.controller('PatientListCtrl', function PatientListCtrl($scope, $http, $location) {
    $scope.patients = loadAllPatients();

    $scope.sync = function () {
        $http.post(datacenterDomain + '/patients', loadAllPatients()).
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

shivagandaVillageControllers.controller('PatientNewCtrl', function PatientNewCtrl() {

});

shivagandaVillageControllers.controller('PatientCreateCtrl', function PatientCreateCtrl($scope, $location) {
    $scope.save = function () {
        var allPatients = loadAllPatients();
        var newPatient = $scope.patient;
        newPatient.id = new Date().getTime();
        allPatients.push(newPatient);
        saveAllPatients(allPatients);
        $location.path('/');
    };
});

shivagandaVillageControllers.controller('PatientDetailCtrl', function PatientDetailCtrl($scope) {

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
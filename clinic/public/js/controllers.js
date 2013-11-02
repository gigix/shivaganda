'use strict';

var datacenterDomain = 'http://shivaganda-datacenter.nodejitsu.com';

var shivagandaClinicControllers = angular.module('shivagandaClinicControllers', []);

shivagandaClinicControllers.controller('HomeCtrl', function HomeCtrl() {

});

shivagandaClinicControllers.controller('PatientListCtrl', function PatientListCtrl($scope, $http) {
    $http.get(datacenterDomain + '/patients').
        success(function (data) {
            $scope.patients = data;
        });
});

shivagandaClinicControllers.controller('PatientEditCtrl',
    function PatientEditCtrl($scope, $routeParams, $http, $location) {
    $http.get(datacenterDomain + '/patients').
        success(function (data) {
            var patientsWithSameId = data.filter(function (patient) {
                return patient.id == $routeParams.patientId;
            });
            $scope.patient = patientsWithSameId[0];
        });

    $scope.update = function () {
        $http.post(datacenterDomain + '/patients', [$scope.patient]).
            success(function() {
                $location.path('/patients');
            });
    };
});
'use strict';

var shivagandaVillageApp = angular.module('shivagandaVillageApp', [
    'ngRoute',
    'shivagandaVillageControllers'
]);

shivagandaVillageApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/patients', {
                templateUrl: 'partials/patient-list.html',
                controller: 'PatientListCtrl'
            }).
            when('/patients/new', {
                templateUrl: 'partials/patient-new.html',
                controller: 'PatientNewCtrl'
            }).
            when('/patients/:patientId', {
                templateUrl: 'partials/patient-detail.html',
                controller: 'PatientDetailCtrl'
            }).
            otherwise({
                redirectTo: '/patients'
            });
    }
]);

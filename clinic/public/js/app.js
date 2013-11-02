'use strict';

var shivagandaClinicApp = angular.module('shivagandaClinicApp', [
    'ngRoute',
    'shivagandaClinicControllers'
]);

shivagandaClinicApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/index', {
                templateUrl: 'partials/home.html',
                controller: 'HomeCtrl'
            }).
            when('/patients', {
                templateUrl: 'partials/patient-list.html',
                controller: 'PatientListCtrl'
            }).
            when('/patients/:patientId/edit', {
                templateUrl: 'partials/patient-edit.html',
                controller: 'PatientEditCtrl'
            }).
            otherwise({
                redirectTo: '/index'
            });
    }
]);
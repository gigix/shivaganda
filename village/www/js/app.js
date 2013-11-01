'use strict';

var shivagandaApp = angular.module('shivagandaApp', [
    'ngRoute',
    'shivagandaControllers'
]);

shivagandaApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/patients', {
                templateUrl: 'partials/patient-list.html',
                controller: 'PatientListCtrl'
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
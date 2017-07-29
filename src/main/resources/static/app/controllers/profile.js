angular.module('JWTDemoApp')
// Creating the Angular Controller
    .controller('ProfileController', function($http, $scope, AuthService) {
        $scope.user = AuthService.user;
    });

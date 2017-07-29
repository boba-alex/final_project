angular.module('JWTDemoApp')
// Creating the Angular Controller
    .controller('InstructionsController', function($http, $scope, AuthService) {
        $scope.user = AuthService.user;
        $scope.buttonText = 'Update0';
        var init = function(appUser) {
            $http.get('api/instructions').success(function(res) {
                $scope.users = res;

                $scope.userForm.$setPristine();
                $scope.message='';
                $scope.appUser = appUser;
                $scope.buttonText = 'Update1';

            }).error(function(error) {
                $scope.message = error.message;
            });
        };
        $scope.initEdit = function(appUser) {
            $scope.appUser = appUser;
            $scope.message='';
            $scope.buttonText = 'Update2';
        };
        var editUser = function(){
            $http.put('api/instructions', $scope.appUser).success(function(res) {
                $scope.appUser = null;
                $scope.confirmPassword = null;
                $scope.userForm.$setPristine();
                $scope.message = "Editting Success";
                init();
            }).error(function(error) {
                $scope.message =error.message;
            });
        };
        $scope.submit = function() {
            editUser();
        };
        init();

    });

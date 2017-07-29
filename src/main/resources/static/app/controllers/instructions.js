angular.module('JWTDemoApp')
// Creating the Angular Controller
    .controller('InstructionsController', function($http, $scope, AuthService) {
        $scope.user = AuthService.user;
        $scope.buttonText = 'Create';

        var init = function(instruction) {
            $http.get('api/instructions').success(function(res) {
                $scope.users = res;

                $scope.userForm.$setPristine();
                $scope.message='';
                $scope.instruction = instruction;
                $scope.buttonText = 'Create';
            }).error(function(error) {
                $scope.message = error.message;
            });
        };
        $scope.initEdit = function(instruction) {
            $scope.instruction = instruction;
            $scope.message='';
            $scope.buttonText = 'Create';
        };
        var editUser = function(){
            $http.put('api/instructions', $scope.instruction).success(function(res) {
                $scope.instruction = null;
                $scope.confirmPassword = null;
                $scope.userForm.$setPristine();
                $scope.message = "Editting Success";
                init();
            }).error(function(error) {
                $scope.message = error.message;
            });
        };
        $scope.submit = function() {
            editUser();
        };
        init();

    });

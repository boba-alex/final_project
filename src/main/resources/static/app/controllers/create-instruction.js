angular.module('JWTDemoApp')
// Creating the Angular Controller
    .controller('CreateInstructionController', function($http, $scope, AuthService) {
        $scope.user = AuthService.user;
        $scope.buttonText = 'CREATE';

        var init = function(instruction) {
            $http.get('api/create-instruction').success(function(res) {
                $scope.users = res;

                $scope.userForm.$setPristine();
                $scope.message='';
                $scope.instruction = instruction;
                $scope.buttonText = 'CREATE';
            }).error(function(error) {
                $scope.message = error.message;
            });
        };
        $scope.initEdit = function(instruction) {
            $scope.instruction = instruction;
            $scope.message='';
            $scope.buttonText = 'CREATE';
        };
        var editUser = function(){
            $http.put('api/create-instruction', $scope.instruction).success(function(res) {
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

angular.module('JWTDemoApp')
// Creating the Angular Controller
    .controller('InstructionsController', function($http, $scope, AuthService) {
        var edit = false;
        $scope.buttonText = 'Create';

        var init = function() {
            $http.get('instructions' ).success(function(res) {
                $scope.instructions = res;
                $scope.message='';
                $scope.appUser = null;
                $scope.buttonText = 'Create';

            }).error(function(error) {
                $scope.message = error.message;
            });
        };
        $scope.initEdit = function(instruction) {
            edit = true;
            $scope.instruction = instruction;
            $scope.message='';
            $scope.buttonText = 'Update';
        };
        $scope.initAddUser = function() {
            edit = false;
            $scope.instruction = null;

            $scope.message='';
            $scope.buttonText = 'Create';
        };
        $scope.deleteUser = function(instruction) {
            $http.delete('instructions/'+instruction.id).success(function(res) {
                $scope.deleteMessage ="Success!";
                init();
            }).error(function(error) {
                $scope.deleteMessage = error.message;
            });
        };
        var editUser = function(){
            $http.put('instructions', $scope.instruction).success(function(res) {
                $scope.instruction = null;
                $scope.confirmPassword = null;
                $scope.userForm.$setPristine();
                $scope.message = "Editting Success";
                init();
            }).error(function(error) {
                $scope.message =error.message;
            });
        };
        var addUser = function(){
            $http.post('instructions', $scope.instruction).success(function(res) {
                $scope.instruction = null;
                $scope.confirmPassword = null;
                $scope.userForm.$setPristine();
                $scope.message = "User Created";
                init();
            }).error(function(error) {
                $scope.message = error.message;
            });
        };
        $scope.submit = function() {
            if(edit){
                editUser();
            }else{
                addUser();
            }
        };
        init();

    });

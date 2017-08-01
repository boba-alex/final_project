angular.module('JWTDemoApp')
// Creating the Angular Controller
    .controller('StepController', function ($http, $scope, AuthService) {
        var edit = false;
        $scope.buttonText = 'Create';
        $scope.nameurl = 'Submit';

        //For everything else
        var init = function () {
            var currentLocation = window.location.toString().split('/')[5] + ' '
                + window.location.toString().split('/')[7];
            $http.get('/step/' + currentLocation).success(function (res) {
                $scope.step = res;

                $scope.message = '';
                $scope.appUser = null;
                $scope.buttonText = 'Create';

            }).error(function (error) {
                $scope.message = error.message;
            });
        };
        $scope.initEdit = function (instruction) {
            edit = true;
            $scope.instruction = instruction;
            $scope.message = '';
            $scope.buttonText = 'Update';
        };
        $scope.initAddUser = function () {
            edit = false;
            $scope.instruction = null;

            $scope.message = '';
            $scope.buttonText = 'Create';
        };
        $scope.deleteUser = function (instruction) {
            $http.delete('step/' + instruction.id).success(function (res) {
                $scope.deleteMessage = "Success!";
                init();
            }).error(function (error) {
                $scope.deleteMessage = error.message;
            });
        };
        var editUser = function () {
            $http.put('step', $scope.instruction).success(function (res) {
                $scope.instruction = null;
                $scope.confirmPassword = null;
                $scope.userForm.$setPristine();
                $scope.message = "Editting Success";
                init();
            }).error(function (error) {
                $scope.message = error.message;
            });
        };
        var addUser = function () {
            $http.post('step', $scope.instruction).success(function (res) {
                $scope.instruction = null;
                $scope.confirmPassword = null;
                $scope.userForm.$setPristine();
                $scope.message = "User Created";
                init();
            }).error(function (error) {
                $scope.message = error.message;
            });
        };
        $scope.submit = function () {
            if (edit) {
                editUser();
            } else {
                addUser();
            }
        };
        init();

    });

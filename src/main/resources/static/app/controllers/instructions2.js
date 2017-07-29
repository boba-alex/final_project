angular.module('JWTDemoApp')
// Creating the Angular Controller
    .controller('Instructions2Controller', function($http, $scope, AuthService) {
        $scope.user = AuthService.user;
        $scope.buttonText = 'Update0';

        // $scope.username = 'sdf' ;
        // $scope.creatorName = 'dfg';
        // $scope.name = "oooo";


        //$scope.instruction.creatorName = 'sdfb';
        //$scope.instruction.name = 'safv';
        //$scope.instruction.username = 'xcbb';



        var init = function(instruction) {
            $http.get('api/instructions2').success(function(res) {
                $scope.users = res;

                $scope.userForm.$setPristine();
                $scope.message='';
                $scope.instruction = instruction;
                $scope.buttonText = 'Update1';

                // var element2 = document.getElementById('name');
                // element2 = 'klkl'
                // $scope.name = "oooo";
                // $scope.creatorName = "uda";
                // $scope.instruction.creatorName = 'sdfb';
                // $scope.username = 'sdf' ;
                // $scope.instruction.name = 'safv';
                // $scope.instruction.username = 'xcbb';

            }).error(function(error) {
                $scope.message = error.message;
            });
        };
        $scope.initEdit = function(instruction) {
            $scope.instruction = instruction;
            $scope.message='';
            $scope.buttonText = 'Update2';

            // var element2 = document.getElementById('name');
            // element2 = 'klkl'
            // $scope.name = "oooo";
            // $scope.creatorName = "uda";
            // $scope.instruction.creatorName = 'sdfb';
            // $scope.username = 'sdf' ;
            // $scope.instruction.name = 'safv';
            // $scope.instruction.username = 'xcbb';
        };
        var editUser = function(){
            $http.put('api/instructions2', $scope.instruction).success(function(res) {
                $scope.instruction = null;
                $scope.confirmPassword = null;
                $scope.userForm.$setPristine();
                $scope.message = "Editting Success";

                // var element2 = document.getElementById('name');
                // element2 = 'klkl'
                // $scope.name = "oooo";
                // $scope.creatorName = "uda";
                // // $scope.instruction.creatorName = 'sdfb';
                // $scope.username = 'sdf' ;
                // // $scope.instruction.name = 'safv';
                // // $scope.instruction.username = 'xcbb';
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

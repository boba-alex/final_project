angular.module('JWTDemoApp').config(function ($stateProvider, $urlRouterProvider) {

    // the ui router will redirect if a invalid state has come.
    $urlRouterProvider.otherwise('/page-not-found');
    // parent view - navigation state

    $stateProvider.state('nav', {
        abstract: true,
        url: '',
        views: {
            'nav@': {
                templateUrl: 'app/views/nav.html',
                controller: 'NavController'
            }
        }
    }).state('login', {
        parent: 'nav',
        url: '/login',
        views: {
            'content@': {
                templateUrl: 'app/views/login.html',
                controller: 'LoginController'
            }
        }
    }).state('users', {
        parent: 'nav',
        url: '/users',
        data: {
            role: 'ADMIN'
        },
        views: {
            'content@': {
                templateUrl: 'app/views/users.html',
                controller: 'UsersController',
            }
        }
    }).state('home', {
        parent: 'nav',
        url: '/',
        views: {
            'content@': {
                templateUrl: 'app/views/home.html',
                controller: 'HomeController'
            }
        }
    }).state('page-not-found', {
        parent: 'nav',
        url: '/page-not-found',
        views: {
            'content@': {
                templateUrl: 'app/views/page-not-found.html',
                controller: 'PageNotFoundController'
            }
        }
    }).state('access-denied', {
        parent: 'nav',
        url: '/access-denied',
        views: {
            'content@': {
                templateUrl: 'app/views/access-denied.html',
                controller: 'AccessDeniedController'
            }
        }
    }).state('register', {
        parent: 'nav',
        url: '/register',
        views: {
            'content@': {
                templateUrl: 'app/views/register.html',
                controller: 'RegisterController'
            }
        }
    }).state('profile', {//for profile
        parent: 'nav',
        url: '/profile',
        views: {
            'content@': {
                templateUrl: 'app/views/profile.html',
                controller: 'ProfileController'
            }
        }
    }).state('create-instruction', {
        parent: 'nav',
        url: '/create-instruction',
        data: {
            role: 'ADMIN' & 'USER' //facepalm
            // role : 'ADMIN' //how user?
        },
        views: {
            'content@': {
                templateUrl: 'app/views/create-instruction.html',
                controller: 'CreateInstructionController'
            }
        }
    }).state('instructions', {
        parent: 'nav',
        url: '/instructions',
        views: {
            'content@': {
                templateUrl: 'app/views/instructions.html',
                controller: 'InstructionsController'
            }
        }
    }).state('view-thread', {
        parent: 'nav',
        url: '/view-thread/{id}',
        views: {
            'content@': {
                templateUrl: 'app/views/view-thread.html',
                controller: 'View-threadController'
            }
        }
    }).state('step', {//1
        parent: 'nav',
        url: '/view-thread/:contID/step/:cxvID',
        views: {
            'content@': {
                templateUrl: 'app/views/step.html',
                controller: 'StepController'
            }
        }
    }).state('view-profile', {//1
        parent: 'nav',
        url: '/view-profile/{id}',
        views: {
            'content@': {
                templateUrl: 'app/views/view-profile.html',
                controller: 'View-profileController'
            }
        }
    });

});


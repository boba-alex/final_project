// Подключаем модуль translate
//var app = angular.module('app', ['pascalprecht.translate'])
angular.module('JWTDemoApp')
    .config(function ($translateProvider) {
        // Загружаем переводы в модуль:
        // Английский
        $translateProvider.translations('en', {
            REGISTER: 'Register',
            INSTRUCTIONS: 'Instructions',
            PROFILE: 'Profile',
            USERS: 'Users',
            CREATE_INSTRUCTIONS: 'Create instructions',
            LOGOUT: 'Logout'
        });
        // Русский
        $translateProvider.translations('ru', {
            REGISTER: 'Регистрация',
            INSTRUCTIONS: 'Инструкции',
            PROFILE: 'Профиль',
            USERS: 'Пользователи',
            CREATE_INSTRUCTIONS: 'Создать инструкции',
            LOGOUT: 'Выйти'
        });
        // Устанавливаем язык по умолчанию
        $translateProvider.preferredLanguage('en');
    })

    // Добавляем ссылку на $translate в контроллер
    .controller('mainCtrl', function( $scope, $translate ){
        // Переключатель языков:
        $scope.changeLng = function( lang ) {
            $translate.use( lang );
            console.log($translate);
        };
        // Проверка текущего языка:
        $scope.currentLng = function( lang ) {
            return $translate.use() == lang;
        };
    });
// Подключаем модуль translate
//var app = angular.module('app', ['pascalprecht.translate'])
angular.module('JWTDemoApp')
    .config(function ($translateProvider) {
        // Загружаем переводы в модуль:
        // Английский
        $translateProvider.translations('en', {
            BUTTON_LANG_EN: 'En',
            BUTTON_LANG_RU: 'Ru',
            REGISTER: 'Register',
            INSTRUCTIONS: 'Instructions',
            PROFILE: 'Profile',
            USERS: 'Users',
            CREATE_INSTRUCTIONS: 'Create instructions',
            LOGOUT: 'Logout',
            BUTTON_CREATE_USER: 'Create',
            BUTTON_UPDATE_USER: 'Update',
            BUTTON_NEW_USER: 'New user'
        });
        // Русский
        $translateProvider.translations('ru', {
            BUTTON_LANG_EN: 'Ан',
            BUTTON_LANG_RU: 'Ру',
            REGISTER: 'Регистрация',
            INSTRUCTIONS: 'Инструкции',
            PROFILE: 'Профиль',
            USERS: 'Пользователи',
            CREATE_INSTRUCTIONS: 'Создать инструкции',
            LOGOUT: 'Выйти',
            BUTTON_CREATE_USER: 'Создать',
            BUTTON_UPDATE_USER: 'Обновить',
            BUTTON_NEW_USER: 'Новый пользователь'
        });
        // Устанавливаем язык по умолчанию
        $translateProvider.preferredLanguage('en');
    })

    // Добавляем ссылку на $translate в контроллер
    .controller('TranslateController', function( $scope, $translate ){
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
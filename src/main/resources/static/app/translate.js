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
            HI: 'Hi',
            LOGOUT: 'Logout',
            //users
            USER: 'User',
            NAME: 'Name',
            USERNAME: 'Username',
            ROLE: 'Role',
            PASSWORD: 'Password',
            BUTTON_CREATE_USER: 'Create',
            BUTTON_UPDATE_USER: 'Update',
            USERS: 'Users',
            BUTTON_NEW_USER: 'New user',
            //create-instruction
            ADD_INSTRUCTION: 'Add instruction',
            CREATE: 'Create',
            UPDATE: 'Update',
            CREATOR: 'Creator',
            NAME_OF_INSTRUCTION: 'Name of instruction',
            TAGS_OF_INSTRUCTION: 'Tags of instruction',
            CATEGORY_OF_INSTRUCTION: 'Category of instruction',
            //instructions
            NEW_INSTRUCTION: 'New instruction',
            NUMBER_OF_STEPS: 'Number of steps',
            NUMBER_OF_COMMENTS: 'Number of comments',
            DATE_OF_CREATION: 'Date of creation'

        });
        // Русский
        $translateProvider.translations('ru', {
            REGISTER: 'Регистрация',
            INSTRUCTIONS: 'Инструкции',
            PROFILE: 'Профиль',
            USERS: 'Пользователи',
            CREATE_INSTRUCTIONS: 'Создать инструкции',
            HI: 'Привет',
            LOGOUT: 'Выйти',
            //users
            USER: 'Пользователь',
            NAME: 'Имя',
            USERNAME: 'Логин',
            ROLE: 'Роль',
            PASSWORD: 'Пароль',
            BUTTON_CREATE_USER: 'Создать',
            BUTTON_UPDATE_USER: 'Обновить',
            USERS: 'Пользователи',
            BUTTON_NEW_USER: 'Новый пользователь',
            //create-instruction
            ADD_INSTRUCTION: 'Добавить инструкцию',
            CREATE: 'Создать',
            UPDATE: 'Обновить',
            CREATOR: 'Создатель',
            NAME_OF_INSTRUCTION: 'Название инструкции',
            TAGS_OF_INSTRUCTION: 'Тэги инструкции',
            CATEGORY_OF_INSTRUCTION: 'Категория инструкции',
            //instructions
            NEW_INSTRUCTION: 'Новая инструкция',
            NUMBER_OF_STEPS: 'Количество шагов',
            NUMBER_OF_COMMENTS: 'Количество комментариев',
            DATE_OF_CREATION: 'Дата создания'
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
Application blog

$ - обозначение командной строки.

Настройки для локального тестирования:
В файле application.properties установить значение профиля (dev или test).
Если выбрать профиль "dev", то работа с БД PostgreSql.
Для развертывания локальной БД PostgreSql в докер-контейнере запустить файл (предварительно установить Docker).
   $ docker-dir/docker-compose up -d

Сборка проекта (команда Gradle)
    $ gradle clean build

При локальном запуске набрать в браузере: 
    localhost://blog
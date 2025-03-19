Application blog

$ - обозначение командной строки.

Настройки для локального тестирования:
В файле application.properties установить значение профиля (profilePostgres или profileH2).
Если выбрать профиль "profilePostgres", то работа с БД PostgreSql.
Для развертывания локальной БД PostgreSql в докер-контейнере запустить файл (предварительно установить Docker).
   $ docker-dir/docker-compose up -d

Развернуть сервер Tomcat.
   Будем считать, что развернули в папке tomcat 

Сборка проекта (команда Maven)
    $ mvn clean package
    Скопировать target/appblog.war в папку сервера tomcat/webapps

Запустить сервер tomcat 
    $ bin/startup

При локальном запуске набрать в браузере: 
    localhost://appblog/blog
# Проект TODO list

## Описание приложения

Приложение TODO list представляет собой редактируемый список задач.

Пользователю приложения предоставляется возможность:

1. Ознакомиться со списком всех задач;
2. Ознакомиться со списком выполненных задач;
3. Ознакомиться со списком не выполненных задач;
4. Добавлять задачи;
5. Редактировать задачи;
6. Изменять статус задач;
7. Удалять задачи.

## Используемый стек технологий

1. Java 17
2. Spring boot 2.5.2
3. Thymeleaf
4. Hibernate 5.7.11
5. Lombok 1.18.22
6. Liquibase 4.15.0
7. PostgreSQL 14

## Для запуска проекта понадобятся

1. JDK 17
2. Apache Maven 3.8.5
3. PostgreSQL 14
4. Web browser

## Запуск приложения

1. Открыть диалоговое окно комбинацией клавиш Win+R;
2. В диалоговом окне прописать cmd и нажать OK;
3. Подключиться к PostgreSQL командой ```psql --username=USER_NAME``` и ввести пароль;
4. Создать базу данных cinema командой ```create database cinema```;
5. Подключиться к базе данных командой ```\c cinema```;
6. Выйти из терминала psql командой ```\q```;
8. Перейти в корневую папку проекта;
9. Запустить приложение командой ```mvn spring-boot:run```;
10. Перейти по ссылке http://localhost:8080/cinemaSessions в браузере.

## Интерфейс сайта

### Регистрация

![jmap1](img/registration.png)

### Авторизация

![jmap1](img/authorization.png)

### Главная страница (все задачи)

![jmap1](img/homePage.png)

### Выполненные задачи

![jmap1](img/completed.png)

### Не выполненные задачи

![jmap1](img/notCompleted.png)

### Добавить задачу

![jmap1](img/add.png)

### Подробное описание задачи

![jmap1](img/description.png)

### Изменение состояние задачи

![jmap1](img/done.png)

### Редактирование задачи

![jmap1](img/eddit.png)

### Удаление задачи

![jmap1](img/deleted.png)

## Контакты

Telegram: @smith_serg
Email: kuznetsov.s.i@bk.ru







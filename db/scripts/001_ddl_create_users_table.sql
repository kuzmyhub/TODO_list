CREATE TABLE IF NOT EXISTS users (
   id SERIAL PRIMARY KEY,
   name VARCHAR NOT NULL,
   login VARCHAR UNIQUE NOT NULL,
   password VARCHAR NOT NULL
);

COMMENT ON TABLE todo_users IS 'Данные зарегистрированных пользователей';
COMMENT ON COLUMN todo_users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN todo_users.name IS 'Имя пользователя';
COMMENT ON COLUMN todo_users.login IS 'Логин пользователя для авторизации';
COMMENT ON COLUMN todo_users.password IS 'Пароль пользователя для авторизации';
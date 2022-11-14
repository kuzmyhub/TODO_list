CREATE TABLE IF NOT EXISTS users (
   id SERIAL PRIMARY KEY,
   name VARCHAR NOT NULL,
   login VARCHAR UNIQUE NOT NULL,
   password VARCHAR NOT NULL
);

COMMENT ON TABLE users IS 'Данные зарегистрированных пользователей';
COMMENT ON COLUMN users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.name IS 'Имя пользователя';
COMMENT ON COLUMN users.login IS 'Логин пользователя для авторизации';
COMMENT ON COLUMN users.password IS 'Пароль пользователя для авторизации';
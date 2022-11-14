CREATE TABLE IF NOT EXISTS tasks (
   id SERIAL PRIMARY KEY,
   description TEXT NOT NULL,
   created TIMESTAMP NOT NULL,
   done BOOLEAN NOT NULL,
   user_id INT NOT NULL REFERENCES users(id)
);

COMMENT ON TABLE tasks IS 'Созданные пользователями задачи';
COMMENT ON COLUMN tasks.id IS 'Идентификатор задачи';
COMMENT ON COLUMN tasks.description IS 'Описание задачи';
COMMENT ON COLUMN tasks.done IS 'Статус задача выполнена/не выполнена';
COMMENT ON COLUMN tasks.user_id IS 'Идентификатор пользоватля';
COMMENT ON COLUMN tasks.created IS 'Время создания задачи';
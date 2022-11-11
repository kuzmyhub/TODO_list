COMMENT ON TABLE todo_users IS 'Данные зарегистрированных пользователей';
COMMENT ON COLUMN todo_users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN todo_users.name IS 'Имя пользователя';
COMMENT ON COLUMN todo_users.login IS 'Логин пользователя для авторизации';
COMMENT ON COLUMN todo_users.password IS 'Пароль пользователя для авторизации';
COMMENT ON COLUMN todo_users.utc IS 'Смещение времени по всемирному координированному времени';

COMMENT ON TABLE tasks IS 'Созданные пользователями задачи';
COMMENT ON COLUMN tasks.id IS 'Идентификатор задачи';
COMMENT ON COLUMN tasks.description IS 'Описание задачи';
COMMENT ON COLUMN tasks.done IS 'Статус задача выполнена/не выполнена';
COMMENT ON COLUMN tasks.user_id IS 'Идентификатор пользоватля';
COMMENT ON COLUMN tasks.priority_id IS 'Идентификатор приоритета задачи';
COMMENT ON COLUMN tasks.created IS 'Время создания задачи';

COMMENT ON TABLE priorities IS 'Список приоритета задач';
COMMENT ON COLUMN priorities.id IS 'Идентификатор приоритета';
COMMENT ON COLUMN priorities.name IS 'Название приоритета';
COMMENT ON COLUMN priorities.position IS 'Позиция приоритета: 1 - наивысший приоритет';

COMMENT ON TABLE categories IS 'Список категорий задач';
COMMENT ON COLUMN categories.id IS 'Идентификатор категории';
COMMENT ON COLUMN categories.id IS 'Название категории';

COMMENT ON TABLE tasks_kategories IS 'Список сопоставлений задач и категорий';
COMMENT ON COLUMN tasks_kategories.id IS 'Идентификатор сопоставления';
COMMENT ON COLUMN tasks_kategories.task_id IS 'Идентификатор задачи';
COMMENT ON COLUMN tasks_kategories.category_id IS 'Идентификатор категории';

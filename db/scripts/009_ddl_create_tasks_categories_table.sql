CREATE TABLE IF NOT EXISTS tasks_categories(
id SERIAL PRIMARY KEY,
task_id INT REFERENCES tasks(id),
category_id INT REFERENCES categories(id)
);

COMMENT ON TABLE tasks_categories IS 'Список сопоставлений задач и категорий';
COMMENT ON COLUMN tasks_categories.id IS 'Идентификатор сопоставления';
COMMENT ON COLUMN tasks_categories.task_id IS 'Идентификатор задачи';
COMMENT ON COLUMN tasks_categories.category_id IS 'Идентификатор категории';

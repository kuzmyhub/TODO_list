CREATE TABLE IF NOT EXISTS tasks_categories(
id SERIAL PRIMARY KEY,
task_id INT REFERENCES tasks(id),
category_id INT REFERENCES categories(id)
);

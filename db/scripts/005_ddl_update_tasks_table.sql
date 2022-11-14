ALTER TABLE tasks ADD COLUMN priority_id INT REFERENCES priorities(id);

COMMENT ON COLUMN tasks.priority_id IS 'Идентификатор приоритета задачи';
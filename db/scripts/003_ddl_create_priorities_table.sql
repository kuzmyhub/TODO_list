CREATE TABLE IF NOT EXISTS priorities (
   id SERIAL PRIMARY KEY,
   name TEXT UNIQUE NOT NULL,
   position INT
);

COMMENT ON TABLE priorities IS 'Список приоритета задач';
COMMENT ON COLUMN priorities.id IS 'Идентификатор приоритета';
COMMENT ON COLUMN priorities.name IS 'Название приоритета';
COMMENT ON COLUMN priorities.position IS 'Позиция приоритета: 1 - наивысший приоритет';
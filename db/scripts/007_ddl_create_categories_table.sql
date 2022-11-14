CREATE TABLE IF NOT EXISTS categories(
id SERIAL PRIMARY KEY,
name VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE categories IS 'Список категорий задач';
COMMENT ON COLUMN categories.id IS 'Идентификатор категории';
COMMENT ON COLUMN categories.name IS 'Название категории';
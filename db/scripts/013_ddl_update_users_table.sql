ALTER TABLE users ADD COLUMN utc VARCHAR;

COMMENT ON COLUMN todo_users.utc IS 'Смещение времени в соответствии с геолокацией пользователя';
CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   description TEXT NOT NULL,
   created TIMESTAMP NOT NULL,
   done BOOLEAN NOT NULL,
   user_id INT NOT NULL REFERENCES users(id)
);
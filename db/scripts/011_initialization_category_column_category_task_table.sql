UPDATE tasks_categories SET category_id = (SELECT id FROM categories WHERE name = 'other');
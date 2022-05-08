CREATE TABLE food_items (
    food_item_id SERIAL primary key,
    name TEXT NOT NULL,
    quantity int NOT NULL,
    type TEXT NOT NULL,
    modified_date TIMESTAMP,
    creation_date TIMESTAMP
);
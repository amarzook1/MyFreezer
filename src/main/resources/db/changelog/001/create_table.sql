CREATE TABLE freezer_storage (
    freezer_storage_item_id SERIAL primary key,
    name TEXT NOT NULL,
    quantity int NOT NULL,
    type TEXT NOT NULL,
    modified_date TIMESTAMP,
    creation_date TIMESTAMP NOT NULL
);
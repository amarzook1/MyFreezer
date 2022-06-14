DELETE FROM public.freezer_storage
WHERE freezer_storage_item_id = 10;
INSERT INTO public.freezer_storage VALUES
(10, 'Mango', 10, 'Fruit' ,current_timestamp, current_timestamp);
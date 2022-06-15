DELETE FROM public.freezer_storage
WHERE freezer_storage_item_id IN (10,11,12);
INSERT INTO public.freezer_storage VALUES
(10, 'Mango', 10, 'Fruit' ,'2022-06-14', '2022-06-14'),
(11, 'Apple', 3, 'Fruit' ,'2022-06-14', '2022-06-14'),
(12, 'Ice Cream', 7, 'Dessert' ,'2022-06-14', '2022-06-14');
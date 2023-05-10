-- insert dummy data into the resource_1 table
INSERT INTO resource_1 (emb_field1, emb_field2)
VALUES (1, 100),
       (0, 200),
       (1, 300);

-- insert dummy data into the resource1_collections table
INSERT INTO resource1_collections (resource1_id, collections)
VALUES (1, 'collection1'),
       (1, 'collection2'),
       (2, 'collection2'),
       (3, 'collection1');

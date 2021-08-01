CREATE TABLE expenditures (id BIGINT AUTO_INCREMENT, spending_date DATE,product_or_service VARCHAR(100),cost INT,person_id BIGINT,PRIMARY KEY (id), FOREIGN KEY (person_id) REFERENCES persons(id));

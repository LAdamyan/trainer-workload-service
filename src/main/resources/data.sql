INSERT INTO trainer (username, first_name, last_name, is_active) VALUES
                                                                     ('trainer1', 'John', 'Doe', TRUE),
                                                                     ('trainer2', 'Jane', 'Smith', TRUE),
                                                                     ('trainer3', 'Alice', 'Johnson', FALSE);
INSERT INTO trainer_yearly_hours (trainer_username, training_year, training_month,  total_hours) VALUES
                                                                    ('trainer1', 2025, 'JANUARY', 10),
                                                                    ('trainer1', 2025, 'FEBRUARY', 15),
                                                                    ('trainer2', 2025, 'JANUARY', 20),
                                                                    ('trainer3', 2025, 'MARCH', 0);
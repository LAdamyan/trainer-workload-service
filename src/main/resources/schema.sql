DROP TABLE IF EXISTS trainer CASCADE;
CREATE TABLE trainer (
                         username VARCHAR(50) PRIMARY KEY,       -- Username as the primary key
                         first_name VARCHAR(50) NOT NULL,        -- Trainer's first name
                         last_name VARCHAR(50) NOT NULL,         -- Trainer's last name
                         is_active BOOLEAN DEFAULT TRUE          -- Indicates if the trainer is active
);

-- Create the trainer_yearly_hours table
DROP TABLE IF EXISTS trainer_yearly_hours;
CREATE TABLE trainer_yearly_hours (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,   -- Unique ID for the record
                                      trainer_username VARCHAR(50) NOT NULL, -- Foreign key to trainer table
                                      training_year INT NOT NULL,             -- Updated column name to avoid "year" conflict
                                      training_month VARCHAR(20) NOT NULL,    -- Updated column name for better clarity
                                      total_hours INT DEFAULT 0,             -- Total training hours for the month
                                      FOREIGN KEY (trainer_username) REFERENCES trainer (username) ON DELETE CASCADE
);
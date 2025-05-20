-- Clean up (safe for repeated execution)
DROP TABLE IF EXISTS `account_transactions`;
DROP TABLE IF EXISTS `accounts`;
DROP TABLE IF EXISTS `loans`;
DROP TABLE IF EXISTS `cards`;
DROP TABLE IF EXISTS `notice_details`;
DROP TABLE IF EXISTS `contact_messages`;
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `customer`;

-- Table creation
CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(20) NOT NULL,
  `pwd` varchar(500) NOT NULL,
  `role` varchar(100) NOT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
);

DROP INDEX IF EXISTS `uniq_email` ON `customer`;
ALTER TABLE `customer` ADD UNIQUE KEY `uniq_email` (`email`);

INSERT IGNORE INTO `customer` (`customer_id`, `name`, `email`, `mobile_number`, `pwd`, `role`, `create_dt`)
VALUES (1, 'Happy', 'happy@example.com', '5334122365',
        '{bcrypt}$2a$12$88.f6upbBvy0okEa7OfHFuorV29qeK.sVbB9VQ6J6dWM1bW6Qef8m', 'admin', CURDATE());

CREATE TABLE IF NOT EXISTS `accounts` (
  `customer_id` int NOT NULL,
  `account_number` int NOT NULL,
  `account_type` varchar(100) NOT NULL,
  `branch_address` varchar(200) NOT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`account_number`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `accounts_customer_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
);

INSERT IGNORE INTO `accounts` (`customer_id`, `account_number`, `account_type`, `branch_address`, `create_dt`)
VALUES (1, 1865764534, 'Savings', '123 Main Street, New York', CURDATE());

CREATE TABLE IF NOT EXISTS `account_transactions` (
  `transaction_id` varchar(200) NOT NULL,
  `account_number` int NOT NULL,
  `customer_id` int NOT NULL,
  `transaction_dt` date NOT NULL,
  `transaction_summary` varchar(200) NOT NULL,
  `transaction_type` varchar(100) NOT NULL,
  `transaction_amt` int NOT NULL,
  `closing_balance` int NOT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `customer_id` (`customer_id`),
  KEY `account_number` (`account_number`),
  CONSTRAINT `tx_account_fk` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`account_number`) ON DELETE CASCADE,
  CONSTRAINT `tx_customer_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
);

-- Sample transactions (new UUID each time)
-- Avoid duplication: usually we would skip if same summary/date exists
INSERT INTO `account_transactions`
(`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,
 `transaction_amt`, `closing_balance`, `create_dt`)
VALUES
(UUID(), 1865764534, 1, CURDATE() - INTERVAL 7 DAY, 'Coffee Shop', 'Withdrawal', 30, 34500, CURDATE() - INTERVAL 7 DAY),
(UUID(), 1865764534, 1, CURDATE() - INTERVAL 6 DAY, 'Uber', 'Withdrawal', 100, 34400, CURDATE() - INTERVAL 6 DAY),
(UUID(), 1865764534, 1, CURDATE() - INTERVAL 5 DAY, 'Self Deposit', 'Deposit', 500, 34900, CURDATE() - INTERVAL 5 DAY),
(UUID(), 1865764534, 1, CURDATE() - INTERVAL 4 DAY, 'Ebay', 'Withdrawal', 600, 34300, CURDATE() - INTERVAL 4 DAY),
(UUID(), 1865764534, 1, CURDATE() - INTERVAL 2 DAY, 'OnlineTransfer', 'Deposit', 700, 35000, CURDATE() - INTERVAL 2 DAY),
(UUID(), 1865764534, 1, CURDATE() - INTERVAL 1 DAY, 'Amazon.com', 'Withdrawal', 100, 34900, CURDATE() - INTERVAL 1 DAY);

CREATE TABLE IF NOT EXISTS `loans` (
  `loan_number` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `start_dt` date NOT NULL,
  `loan_type` varchar(100) NOT NULL,
  `total_loan` int NOT NULL,
  `amount_paid` int NOT NULL,
  `outstanding_amount` int NOT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`loan_number`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `loans_customer_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
);

INSERT IGNORE INTO `loans` (`customer_id`, `start_dt`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `create_dt`)
VALUES 
(1, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13'),
(1, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06'),
(1, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14'),
(1, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');

CREATE TABLE IF NOT EXISTS `cards` (
  `card_id` int NOT NULL AUTO_INCREMENT,
  `card_number` varchar(100) NOT NULL,
  `customer_id` int NOT NULL,
  `card_type` varchar(100) NOT NULL,
  `total_limit` int NOT NULL,
  `amount_used` int NOT NULL,
  `available_amount` int NOT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`card_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `cards_customer_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
);

INSERT IGNORE INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `create_dt`)
VALUES
('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, CURDATE()),
('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, CURDATE()),
('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, CURDATE());

CREATE TABLE IF NOT EXISTS `notice_details` (
  `notice_id` int NOT NULL AUTO_INCREMENT,
  `notice_summary` varchar(200) NOT NULL,
  `notice_details` varchar(500) NOT NULL,
  `notic_beg_dt` date NOT NULL,
  `notic_end_dt` date DEFAULT NULL,
  `create_dt` date DEFAULT NULL,
  `update_dt` date DEFAULT NULL,
  PRIMARY KEY (`notice_id`)
);

-- Simplified using INSERT IGNORE, assuming no duplication on summary
INSERT IGNORE INTO `notice_details` (`notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
VALUES
('Home Loan Interest rates reduced',
 'Home loan interest rates are reduced as per the goverment guidelines. The updated rates will be effective immediately',
 CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), NULL),
('Net Banking Offers',
 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher',
 CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), NULL),
('Mobile App Downtime',
 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities',
 CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), NULL),
('E Auction notice',
 'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction',
 CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), NULL),
('Launch of Millennia Cards',
 'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase',
 CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), NULL),
('COVID-19 Insurance',
 'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details',
 CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), NULL);

CREATE TABLE IF NOT EXISTS `contact_messages` (
  `contact_id` varchar(50) NOT NULL,
  `contact_name` varchar(50) NOT NULL,
  `contact_email` varchar(100) NOT NULL,
  `subject` varchar(500) NOT NULL,
  `message` varchar(2000) NOT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
);

CREATE TABLE `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `authorities_customer_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
);

DROP INDEX IF EXISTS `uniq_customer_role` ON `authorities`;
ALTER TABLE `authorities` ADD UNIQUE KEY `uniq_customer_role` (`customer_id`, `name`);

INSERT IGNORE INTO `authorities` (`customer_id`, `name`)
VALUES
(1, 'ROLE_USER'),
(1, 'ROLE_ADMIN');

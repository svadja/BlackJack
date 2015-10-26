INSERT INTO login_details (  id, username, password , account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES  ( 1, 'dealer', 'sasa',  true, true, true, true);
INSERT INTO login_authorities (  id, username, authority ) VALUES  ( 1, 'dealer', 'ROLE_DEALER');

INSERT INTO login_details (  id, username, password , account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES  ( 2, 'sasa', 'sasa',  true, true, true, true);
INSERT INTO login_authorities (  id, username, authority ) VALUES  ( 2, 'sasa', 'ROLE_USER');

INSERT INTO accounts (  id, username, a_number,  amount) VALUES  ( 1, 'sasa', '307307307', 30);
INSERT INTO account_transactions (  id, account_id, amount, tr_type) VALUES  ( 1, 1, 30, 'DEPOSIT');

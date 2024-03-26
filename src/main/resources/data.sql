INSERT INTO users (username, email, password, alias, description, role, profile_picture) VALUES
('john_doe', 'john@example.com', '$2a$10$n7v9.qKPWgvpfSFESerP7ecXEDIIx35ORQNtHYsGqertQKIHILaMa', 'JohnD', 'Hello, I am John!', 'USER', 'john_profile.jpg'),
('jane_smith', 'jane@example.com', '$2a$10$3AbvE7cIYazTZjaX2q.YnumqO0ZhxflQDMcptWQMvssQ89xLt2u6C', 'JaneS', 'Nice to meet you all!', 'USER', 'jane_profile.jpg'),
('sam_wilson', 'sam@example.com', '$2a$10$Cetv53n9L0FSuTRfa2ZTEef7bXxYmOnuwiSXCWBH9307UTdmGr39y', 'SamW', 'Just a regular guy.', 'USER', 'sam_profile.jpg'),
('Daniel', 'daniel1@gmail.com', '$2a$10$Pwr9PEvjq5LRHweHOcasoOSI84switTEXMRmRr1/165QBevF.5OXK', 'Daniel1', 'XD.', 'ADMIN', 'default_profile_picture.png');

--Random like_count for demonstration purposes
INSERT INTO posts (user_id, content, like_count) VALUES
(4, 'Just testing the waters here.', ROUND(RANDOM() * 100)),
(2, 'Hello, everyone! How are you all doing today?', ROUND(RANDOM() * 10000)),
(3, 'Testing, testing, 1, 2, 3...', ROUND(RANDOM() * 1000)),
(4, 'Hey there! Whats up?', ROUND(RANDOM() * 10000)),
(1, 'Im new here, nice to meet you all!', ROUND(RANDOM() * 100)),
(2, 'Anyone up for a chat?', ROUND(RANDOM() * 10000)),
(3, 'Just sharing a random thought.', ROUND(RANDOM() * 100000)),
(4, 'Checking in! Hows everyone day going?', ROUND(RANDOM() * 100000)),
(1, 'First time posting here. Excited to be part of the community!', ROUND(RANDOM() * 10)),
(2, 'Whats the latest gossip?', ROUND(RANDOM() * 100)),
(3, 'Thinking about what to have for dinner...', ROUND(RANDOM() * 100)),
(4, 'Feeling productive today!', ROUND(RANDOM() * 1000000)),
(1, 'Just wanted to say hi to everyone!', ROUND(RANDOM() * 10)),
(2, 'Looking forward to some interesting conversations!', ROUND(RANDOM() * 100)),
(3, 'Its a beautiful day outside. Enjoying the sunshine!', ROUND(RANDOM() * 10000));

INSERT INTO liked_posts (user_id, post_id) VALUES
(1, 2),(1, 4),(2, 1),(3, 3),(4, 5), (4, 1), (4,2)
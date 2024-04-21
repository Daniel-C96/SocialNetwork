INSERT INTO users (username, email, password, alias, description, role, profile_picture) VALUES
('john_doe', 'john@example.com', '$2a$10$n7v9.qKPWgvpfSFESerP7ecXEDIIx35ORQNtHYsGqertQKIHILaMa', 'JohnD', 'Hello, I am John!', 'USER', 'https://social-network-danielc.s3.amazonaws.com/uploads/profile-pictures/default_profile_picture.png'),
('jane_smith', 'jane@example.com', '$2a$10$3AbvE7cIYazTZjaX2q.YnumqO0ZhxflQDMcptWQMvssQ89xLt2u6C', 'JaneS', 'Nice to meet you all!', 'USER', 'https://social-network-danielc.s3.amazonaws.com/uploads/profile-pictures/default_profile_picture.png'),
('sam_wilson', 'sam@example.com', '$2a$10$Cetv53n9L0FSuTRfa2ZTEef7bXxYmOnuwiSXCWBH9307UTdmGr39y', 'SamW', 'Just a regular guy.', 'USER', 'https://social-network-danielc.s3.amazonaws.com/uploads/profile-pictures/default_profile_picture.png'),
('Daniel', 'daniel1@gmail.com', '$2a$10$Pwr9PEvjq5LRHweHOcasoOSI84switTEXMRmRr1/165QBevF.5OXK', 'Daniel1', 'Main account.', 'ADMIN', 'https://social-network-danielc.s3.amazonaws.com/uploads/profile-pictures/dog.png');

--Random like_count for demonstration purposes
INSERT INTO posts (user_id, content, like_count, created_at) VALUES
(4, 'Just testing the waters here.', ROUND(RANDOM() * 100), '2024-04-02 08:10:00'),
(2, 'Hello, everyone! How are you all doing today?', ROUND(RANDOM() * 10000), '2024-04-02 08:11:00'),
(3, 'Testing, testing, 1, 2, 3...', ROUND(RANDOM() * 1000), '2024-04-02 08:12:00'),
(4, 'Hey there! Whats up?', ROUND(RANDOM() * 10000), '2024-04-02 08:13:00'),
(1, 'Im new here, nice to meet you all!', ROUND(RANDOM() * 100), '2024-04-02 08:14:00'),
(2, 'Anyone up for a chat?', ROUND(RANDOM() * 10000), '2024-04-02 08:15:00'),
(3, 'Just sharing a random thought.', ROUND(RANDOM() * 100000), '2024-04-02 08:16:00'),
(4, 'Checking in! Hows everyone day going?', ROUND(RANDOM() * 100000), '2024-04-02 08:17:00'),
(1, 'First time posting here. Excited to be part of the community!', ROUND(RANDOM() * 10), '2024-04-02 08:18:00'),
(2, 'Whats the latest gossip?', ROUND(RANDOM() * 100), '2024-04-02 08:19:00'),
(3, 'Thinking about what to have for dinner...', ROUND(RANDOM() * 100), '2024-04-02 08:20:00'),
(4, 'Feeling productive today!', ROUND(RANDOM() * 1000000), '2024-04-02 08:21:00'),
(1, 'Just wanted to say hi to everyone!', ROUND(RANDOM() * 10), '2024-04-02 08:22:00'),
(2, 'Looking forward to some interesting conversations!', ROUND(RANDOM() * 100), '2024-04-02 08:23:00'),
(3, 'Its a beautiful day outside. Enjoying the sunshine!', ROUND(RANDOM() * 10000), '2024-04-02 08:24:00');

INSERT INTO liked_posts (user_id, post_id) VALUES
(1, 2),(1, 4),(2, 1),(3, 3),(4, 5), (4, 1), (4,2);


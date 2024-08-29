DROP TABLE IF EXISTS notification_log;
DROP TABLE IF EXISTS child_register_request;
DROP TABLE IF EXISTS experience_log;
DROP TABLE IF EXISTS egg_trade_log;
DROP TABLE IF EXISTS egg_sell_board;
DROP TABLE IF EXISTS hold_special_egg;
DROP TABLE IF EXISTS special_egg;
DROP TABLE IF EXISTS egg_count;
DROP TABLE IF EXISTS egg;
DROP TABLE IF EXISTS egg_destroy_log;
DROP TABLE IF EXISTS selected_quiz_keyword;
DROP TABLE IF EXISTS between_of_day_quiz_solve_log;
DROP TABLE IF EXISTS quiz_solve;
DROP TABLE IF EXISTS financial_quiz;
DROP TABLE IF EXISTS quiz_keyword;
DROP TABLE IF EXISTS promise_ticket;
DROP TABLE IF EXISTS child_savings_status_by_period;
DROP TABLE IF EXISTS mission;
DROP TABLE IF EXISTS month_auto_transfer_rule;
DROP TABLE IF EXISTS week_auto_transfer_rule;
DROP TABLE IF EXISTS auto_transfer_rule;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS bank_code;
DROP TABLE IF EXISTS fcm;
DROP TABLE IF EXISTS promise;
DROP TABLE IF EXISTS child;
DROP TABLE IF EXISTS parent;
DROP TABLE IF EXISTS temporary_user;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS level_assets;
DROP TABLE IF EXISTS prefix_sum_exp;



create table prefix_sum_exp
(
    level          SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    prefix_sum_exp INT      NOT NULL
);

create table level_assets
(
    level     SMALLINT PRIMARY KEY,
    asset_url TEXT NOT NULL,
    FOREIGN KEY (level) REFERENCES prefix_sum_exp (level)
);

create table temporary_user
(
    temporary_user_id INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email             VARCHAR(40) NOT NULL,
    name              VARCHAR(52) NOT NULL,
    code              CHAR(36)    NOT NULL
);

create table user
(
    user_id     INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        CHAR(1)     NULL,
    email       VARCHAR(40) NOT NULL,
    name        VARCHAR(52) NOT NULL,
    nickname    VARCHAR(30) NULL,
    birthday    DATE        NULL,
    user_gender CHAR(1)     NULL,
    is_deleted  BOOL        NULL
);

create table parent
(
    user_id INT NOT NULL PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);


create table child
(
    user_id     INT NOT NULL PRIMARY KEY,
    parent_id   INT NULL,
    current_exp INT NOT NULL,
    max_exp     INT NOT NULL,
    goal_money  INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (parent_id) REFERENCES parent (user_id)
);

create table experience_log
(
    experience_log_id INT AUTO_INCREMENT PRIMARY KEY,
    child_id          INT          NOT NULL,
    type              CHAR(10)     NOT NULL,
    description       VARCHAR(100) NOT NULL,
    created_at        DATETIME     NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);



create table fcm
(
    fcm_id  INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT          NOT NULL,
    token   VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);

create table notification_log
(
    notification_log_id    INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id                INT         NOT NULL,
    notification_title     VARCHAR(40) NOT NULL,
    notification_body      VARCHAR(80) NOT NULL,
    notification_type      VARCHAR(30) NOT NULL,
    notification_target_id VARCHAR(30), -- 닉네임도 적용됨. 바디에 저장되어 전송될 예정.
    published_at           DATETIME    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);

create table bank_code
(
    bank_code_id CHAR(3)     NOT NULL PRIMARY KEY,
    bank_name    VARCHAR(20) NOT NULL
);

create table account
(
    account_id     INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id        INT         NOT NULL,
    bank_code_id   CHAR(3)     NOT NULL,
    account_type   CHAR(1)     NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    account_name   VARCHAR(28) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (bank_code_id) REFERENCES bank_code (bank_code_id)
);

create table auto_transfer_rule
(
    auto_transfer_rule_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    receive_account_id    INT NOT NULL,
    send_account_id       INT NOT NULL,
    balance               INT NOT NULL,
    sender_id             INT NOT NULL,
    receiver_id           INT NOT NULL,
    FOREIGN KEY (receive_account_id) REFERENCES account (account_id),
    FOREIGN KEY (send_account_id) REFERENCES account (account_id),
    FOREIGN KEY (sender_id) REFERENCES user (user_id),
    FOREIGN KEY (receiver_id) REFERENCES user (user_id)
);

create table week_auto_transfer_rule
(
    auto_transfer_rule_id INT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    day                   TINYINT NOT NULL,
    FOREIGN KEY (auto_transfer_rule_id) REFERENCES auto_transfer_rule (auto_transfer_rule_id)
);

create table month_auto_transfer_rule
(
    auto_transfer_rule_id INT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    day                   TINYINT NOT NULL,
    week                  TINYINT NOT NULL,
    FOREIGN KEY (auto_transfer_rule_id) REFERENCES auto_transfer_rule (auto_transfer_rule_id)
);

create table mission
(
    mission_id          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id            INT          NOT NULL,
    mission_description VARCHAR(100) NOT NULL,
    is_finished         BOOL         NOT NULL,
    mission_start_at    DATETIME     NOT NULL,
    mission_end_at      DATETIME     NOT NULL,
    mission_finished_at DATETIME     NULL,
    mission_level       CHAR(1)      NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table child_savings_status_by_period
(
    period_id       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    child_id        INT                NOT NULL,
    recursion_money INT                NOT NULL,
    goal_money      INT                NOT NULL,
    start_date      DATE               NOT NULL,
    end_date        DATE               NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

----------------------------------------------------------------------
-- 약속권
create table promise_ticket
(
    promise_ticket_id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id          INT          NOT NULL,
    published_at      DATETIME     NOT NULL,
    used_at           DATETIME     NULL,
    requested_at      DATETIME     NULL,
    image_url         VARCHAR(255) NULL,
    description       VARCHAR(255) NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table quiz_keyword
(
    quiz_keyword_id INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    keyword         VARCHAR(40) NOT NULL
);

create table financial_quiz
(
    financial_quiz_id INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description       TEXT NOT NULL,
    answer            BOOL NOT NULL,
    quiz_keyword_id   INT  NOT NULL,
    child_id          INT  NOT NULL,
    created_at        Date NOT NULL,
    quiz_explanation  TEXT NOT NULL,
    FOREIGN KEY (quiz_keyword_id) REFERENCES quiz_keyword (quiz_keyword_id),
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table quiz_solve
(
    quiz_solve_id     INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    financial_quiz_id INT  NOT NULL,
    child_id          INT  NOT NULL,
    is_correct        BOOL NULL,
    corrected_at      DATE NULL,
    FOREIGN KEY (financial_quiz_id) REFERENCES financial_quiz (financial_quiz_id),
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table between_of_day_quiz_solve_log
(
    log_id     INT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id   INT     NOT NULL,
    started_at DATE    NOT NULL,
    end_at     DATE    NOT NULL,
    count      TINYINT NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table selected_quiz_keyword
(
    selected_quiz_keyword_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id                 INT NOT NULL,
    quiz_keyword_id          INT NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id),
    FOREIGN KEY (quiz_keyword_id) REFERENCES quiz_keyword (quiz_keyword_id)
);

create table egg_count
(
    egg_count_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id     INT NOT NULL,
    egg_count    INT NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table egg_destroy_log
(
    egg_destroy_log_id INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id           INT      NOT NULL,
    created_at         DATETIME NOT NULL,
    destroyed_at       DATETIME,
    hit_count          SMALLINT NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table special_egg
(
    special_egg_id INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(40) NOT NULL,
    probability    FLOAT       NOT NULL,
    image_src      TEXT        NOT NULL
);


create table hold_special_egg
(
    hold_special_egg_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id            INT NOT NULL,
    special_egg_id      INT NOT NULL,
    egg_count           INT NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id),
    FOREIGN KEY (special_egg_id) REFERENCES special_egg (special_egg_id)
);


create table egg_sell_board
(
    egg_sell_board_id  INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id           INT      NOT NULL,
    special_egg_id     INT      NOT NULL,
    wrote_at           DATETIME NOT NULL,
    egg_price_per_once INT      NOT NULL,
    sell_count         INT      NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id),
    FOREIGN KEY (special_egg_id) REFERENCES special_egg (special_egg_id)
);


create table egg_trade_log
(
    egg_trade_log_id   INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    seller_id          INT      NOT NULL,
    buyer_id           INT      NOT NULL,
    traded_at          DATETIME NOT NULL,
    special_egg_id     INT      NOT NULL,
    egg_stock_count    INT      NOT NULL,
    egg_price_per_once INT      NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES child (user_id),
    FOREIGN KEY (buyer_id) REFERENCES child (user_id),
    FOREIGN KEY (special_egg_id) REFERENCES special_egg (special_egg_id)
);

create table child_register_request
(
    child_register_request_id INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id                  INT      NOT NULL,
    parent_id                 INT      NOT NULL,
    created_at                DATETIME NOT NULL,
    state                     CHAR(10) NOT NULL,

    FOREIGN KEY (child_id) REFERENCES child (user_id),
    FOREIGN KEY (parent_id) REFERENCES parent (user_id)
);


INSERT INTO user(type, email, name, nickname, birthday, user_gender, is_deleted)
VALUES ('c', 'khj745700@naver.com', '김현진', '흑염룡', '2020-05-05', 'm', false),
       ('p', 'yuseung0429@naver.com', '이유승', '현진맘', '1998-04-29', 'f', false),
       ('c', 'yoha6865@naver.com', '최요하', '요하땽', '2020-03-21', 'm', false),
       ('c', 'altys31@gmail.com', '양규현', '규효니', '2017-02-20', 'm', false),
       ('p', 'he12569@naver.com', '조성우', '요하빠', '1996-04-21', 'm', false);

INSERT INTO parent(user_id)
VALUES (2), (5);

INSERT INTO child(user_id, parent_id, current_exp, max_exp, goal_money) VALUES (1, 2, 0, 0, 0),
                                                                               (3, 5, 132, 132, 0),
                                                                               (4, 5, 345, 345, 0);

INSERT INTO promise_ticket(promise_ticket_id, child_id, published_at, used_at, requested_at, image_url, description)
VALUES (1, 1, "2024-08-26T00:00:00", NULL, NULL, NULL, NULL);

INSERT INTO quiz_keyword(keyword)
VALUES ('은행 대출 연체'),
       ('펀드'),
       ('신용카드'),
       ('대포 통장'),
       ('핀테크'),
       ('환율'),
       ('지폐'),
       ('찢어진 돈'),
       ('폐기된 돈');



INSERT INTO special_egg(name, probability, image_src)
VALUES ('다이아몬드 계란', 0.1, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/diamond-egg.png'),
       ('우주 계란', 0.1, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/universe-egg.png'),
       ('바다 계란', 0.1, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/sea-egg.png'),
       ('반짝이는 계란', 0.1, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/diamond-egg2.png'),
       ('점박이 계란', 0.1, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/dotted-egg.png'),
       ('불타는 계란', 0.1, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/fire-egg.png'),
       ('투명 유리 계란', 0.1, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/glass-egg.png'),
       ('황금 계란', 0.1, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/gold-egg.png'),
       ('오팔 계란', 0.05, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/opal-egg.png'),
       ('선물 계란', 0.05, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/present-egg.png'),
       ('토끼 계란', 0.05, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/rabbit-egg.png'),
       ('쏠쏠하이 계란', 0.05, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/solsolhigh-egg.png');




-- Experience Log
INSERT INTO experience_log(child_id, type, description, created_at) VALUES
                                                                        (1, 'MISSION1', '미션(하)을 수행했습니다!', '2024-08-29 14:22:00'),
                                                                        (3, 'QUIZ', '퀴즈를 풀었습니다!', '2024-08-29 11:10:20'),
                                                                        (4, 'MISSION2', '미션(중)을 수행했습니다!', '2024-08-29 09:15:45'),
                                                                        (1, 'QUIZ', '퀴즈를 풀었습니다!', '2024-08-29 10:45:12'),
                                                                        (3, 'MISSION3', '미션(상)을 수행했습니다!', '2024-08-29 16:30:00'),
                                                                        (4, 'QUIZ', '퀴즈를 풀었습니다!', '2024-08-29 18:20:00'),
                                                                        (1, 'MISSION3', '미션(상)을 수행했습니다!', '2024-08-29 08:00:00'),
                                                                        (3, 'QUIZ', '퀴즈를 풀었습니다!', '2024-08-29 14:45:00'),
                                                                        (4, 'MISSION2', '미션(중)을 수행했습니다!', '2024-08-29 12:30:00'),
                                                                        (1, 'MISSION3', '미션(상)을 수행했습니다!', '2024-08-29 13:50:00');



-- Mission
INSERT INTO mission(child_id, mission_description, is_finished, mission_start_at, mission_end_at, mission_level) VALUES
                                                                                                                     (1, '수학 숙제 완료하기', false, '2024-07-01 09:00:00', '2024-09-09 23:59:59', '1'),
                                                                                                                     (1, '방 청소하기', false, '2024-07-08 09:00:00', '2024-09-09 23:59:59', '2'),
                                                                                                                     (1, '책 읽기', false, '2024-07-15 09:00:00', '2024-09-09 23:59:59', '3'),
                                                                                                                     (3, '일기 쓰기', false, '2024-07-02 09:00:00', '2024-09-09 23:59:59', '3'),
                                                                                                                     (3, '체력 운동하기', false, '2024-07-09 09:00:00', '2024-09-19 23:59:59', '2'),
                                                                                                                     (3, '나무 심기', false, '2024-07-16 09:00:00', '2024-09-22 23:59:59', '1'),
                                                                                                                     (4, '노래 배우기', false, '2024-07-03 09:00:00', '2024-09-09 23:59:59', '1'),
                                                                                                                     (4, '설거지하기', false, '2024-07-10 09:00:00', '2024-09-16 23:59:59', '2'),
                                                                                                                     (4, '일기 쓰기', false, '2024-07-17 09:00:00', '2024-09-23 23:59:59', '3'),
                                                                                                                     (1, '부모님과 함께 놀러가기', false, '2024-07-04 09:00:00', '2024-09-10 23:59:59', '3');

-- Financial Quiz
INSERT INTO financial_quiz(description, answer, quiz_keyword_id, child_id, created_at, quiz_explanation) VALUES
                                                                                                             ('이자가 무엇일까요?', true, 1, 1, '2024-07-01', 'A number representing a person\'s creditworthiness.'),
                                                                                                             ('이 문제는 무엇일까요?', true, 2, 1, '2024-07-02', 'Annual Percentage Rate.'),
                                                                                                             ('이자가 무엇일까요?', true, 3, 1, '2024-07-03', 'Interest on interest.'),
                                                                                                             ('이자가 무엇일까요?', false, 4, 3, '2024-07-04', 'Exchange-Traded Fund.'),
                                                                                                             ('이자가 무엇일까요?', true, 5, 3, '2024-07-05', 'A loan for buying a house.'),
                                                                                                             ('이자가 무엇일까요?', true, 6, 3, '2024-07-06', 'Increase in prices over time.'),
                                                                                                             ('이자가 무엇일까요?', false, 7, 4, '2024-07-07', 'A plan for income and expenses.'),
                                                                                                             ('이자가 무엇일까요?', true, 8, 4, '2024-07-08', 'Account for saving money.'),
                                                                                                             ('이자가 무엇일까요?', true, 9, 4, '2024-07-09', 'A fixed income instrument.'),
                                                                                                             ('이자가 무엇일까요?', true, 1, 1, '2024-07-10', 'Ownership in a company.');

-- Quiz Solve
INSERT INTO quiz_solve(financial_quiz_id, child_id, is_correct, corrected_at) VALUES
                                                                                  (1, 1, true, '2024-07-01'),
                                                                                  (2, 1, false, '2024-07-02'),
                                                                                  (3, 1, true, '2024-07-03'),
                                                                                  (4, 3, false, '2024-07-04'),
                                                                                  (5, 3, true, '2024-07-05'),
                                                                                  (6, 3, false, '2024-07-06'),
                                                                                  (7, 4, true, '2024-07-07'),
                                                                                  (8, 4, false, '2024-07-08'),
                                                                                  (9, 4, true, '2024-07-09'),
                                                                                  (10, 1, true, '2024-07-10');

-- Between of Day Quiz Solve Log
INSERT INTO between_of_day_quiz_solve_log(child_id, started_at, end_at, count) VALUES
                                                                                   (1, '2024-08-25', '2024-08-30', 6),
                                                                                   (3, '2024-08-25', '2024-08-30', 6),
                                                                                   (4, '2024-08-25', '2024-08-30', 6);

-- Selected Quiz Keyword
INSERT INTO selected_quiz_keyword(child_id, quiz_keyword_id) VALUES
                                                                 (1, 1), (1, 2), (1, 3), (3, 1), (3, 4), (3, 5),
                                                                 (4, 1), (4, 6), (4, 7), (1, 8);

-- Egg Count
INSERT INTO egg_count(child_id, egg_count) VALUES
                                               (1, 10), (3, 20), (4, 15);


-- Hold Special Egg
INSERT INTO hold_special_egg(child_id, special_egg_id, egg_count) VALUES
                                                                      (1, 1, 5), (1, 2, 3), (1, 3, 2), (3, 1, 1),
                                                                      (3, 4, 6), (3, 5, 4), (4, 1, 3), (4, 6, 7),
                                                                      (4, 7, 2), (1, 8, 9);

-- Egg Sell Board
INSERT INTO egg_sell_board(child_id, special_egg_id, wrote_at, egg_price_per_once, sell_count) VALUES
                                                                                                   (1, 1, '2024-07-01 08:00:00', 1, 10),
                                                                                                   (3, 2, '2024-07-02 09:00:00', 3, 5),
                                                                                                   (4, 3, '2024-07-03 10:00:00', 2, 7),
                                                                                                   (1, 4, '2024-07-04 11:00:00', 2, 6),
                                                                                                   (3, 5, '2024-07-05 12:00:00', 4, 4),
                                                                                                   (4, 6, '2024-07-06 13:00:00', 6, 8),
                                                                                                   (1, 7, '2024-07-07 14:00:00', 2, 3),
                                                                                                   (3, 8, '2024-07-08 15:00:00', 3, 9),
                                                                                                   (4, 9, '2024-07-09 16:00:00', 5, 2),
                                                                                                   (1, 10, '2024-07-10 17:00:00', 1, 1);

-- Egg Trade Log
INSERT INTO egg_trade_log(seller_id, buyer_id, traded_at, special_egg_id, egg_stock_count, egg_price_per_once) VALUES
                                                                                                                   (1, 3, '2024-07-01 08:00:00', 1, 10, 2),
                                                                                                                   (3, 4, '2024-07-02 09:00:00', 2, 5, 3),
                                                                                                                   (4, 1, '2024-07-03 10:00:00', 3, 7, 7),
                                                                                                                   (1, 3, '2024-07-04 11:00:00', 4, 6, 2),
                                                                                                                   (3, 4, '2024-07-05 12:00:00', 5, 4, 5),
                                                                                                                   (4, 1, '2024-07-06 13:00:00', 6, 8, 3),
                                                                                                                   (1, 3, '2024-07-07 14:00:00', 7, 3, 6),
                                                                                                                   (3, 4, '2024-07-08 15:00:00', 8, 9, 3),
                                                                                                                   (4, 1, '2024-07-09 16:00:00', 9, 2, 6),
                                                                                                                   (1, 3, '2024-07-10 17:00:00', 2, 1, 9);

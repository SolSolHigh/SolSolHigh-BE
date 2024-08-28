DROP TABLE IF EXISTS child_register_alarm;
DROP TABLE IF EXISTS alarm;
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
DROP TABLE IF EXISTS promise_ticket_used_log;
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
DROP TABLE IF EXISTS prefix_sum_exp;


create table prefix_sum_exp
(
    level          SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    prefix_sum_exp INT      NOT NULL
);

create table temporary_user
(
    temporary_user_id INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email             VARCHAR(40) NOT NULL,
    name              VARCHAR(52) NOT NULL
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



create table fcm
(
    fcm_id  INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT          NOT NULL,
    token   VARCHAR(255) NOT NULL,
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
    promise_ticket_id    INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id             INT      NOT NULL,
    promise_published_at DATETIME NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table promise_ticket_used_log
(
    promise_ticket_used_log_id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    promise_ticket_id          INT          NOT NULL,
    image_url                  TEXT,
    description                VARCHAR(255) NOT NULL,
    promise_used_at            DATETIME     NOT NULL,
    FOREIGN KEY (promise_ticket_id) REFERENCES promise_ticket (promise_ticket_id)
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

create table alarm
(
    alarm_id    INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sender_id   INT      NOT NULL,
    receiver_id INT      NOT NULL,
    created_at  DATETIME NOT NULL,
    is_deleted  BOOL     NOT NULL,
    type        CHAR(1)  NULL,
    FOREIGN KEY (sender_id) REFERENCES user (user_id),
    FOREIGN KEY (receiver_id) REFERENCES user (user_id)
);

create table child_register_alarm
(
    alarm_id INT      NOT NULL PRIMARY KEY,
    state    CHAR(10) NOT NULL,
    FOREIGN KEY (alarm_id) REFERENCES alarm (alarm_id)
);


INSERT INTO user(user_id, type, email, name, nickname, birthday, user_gender, is_deleted)
VALUES (1, 'c', 'khj745700@naver.com', '김현진', '흑염룡', '2020-05-05', 'm', false),
       (2, 'p', 'yuseung0429@naver.com', '이유승', '현진맘', '1998-04-29', 'f', false),
       (3, 'c', 'test@test.test', '테스트', '테스트계정', '2020-05-05', 'm', false);

INSERT INTO parent(user_id)
VALUES (2);

INSERT INTO child(user_id, parent_id, current_exp, max_exp, goal_money)
VALUES (1, 2, 0, 0, 0),
       (3, 2, 0, 0, 0);


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

INSERT INTO selected_quiz_keyword(child_id, quiz_keyword_id)
VALUES (1, 1);


INSERT INTO special_egg(name, probability, image_src)
VALUES ('다이아몬드 계란', 0.01, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/diamond-egg.png'),
       ('특별한 다이아몬드 계란', 0.03, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/diamond-egg2.png'),
       ('점박이 계란', 0.3, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/dotted-egg.png'),
       ('불타는 계란', 0.09, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/fire-egg.png'),
       ('투명 유리 계란', 0.07, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/glass-egg.png'),
       ('황금 계란', 0.05, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/gold-egg.png'),
       ('오팔 계란', 0.05, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/opal-egg.png'),
       ('선물 계란', 0.2, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/present-egg.png'),
       ('토끼 계란', 0.2, 'https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/eggs/rabbit-egg.png');

INSERT INTO hold_special_egg(child_id, special_egg_id, egg_count)
VALUES (1, 1, 1);

INSERT INTO egg_trade_log(seller_id, buyer_id, traded_at, special_egg_id, egg_stock_count, egg_price_per_once)
VALUES (1, 3, '2023-05-05', 1, 0, 1);
INSERT INTO egg_trade_log(seller_id, buyer_id, traded_at, special_egg_id, egg_stock_count, egg_price_per_once)
VALUES (1, 3, '2023-05-08', 1, 0, 2);

INSERT INTO egg_sell_board(child_id, special_egg_id, wrote_at, egg_price_per_once, sell_count)
VALUES (3, 2, '2024-05-06', 1, 3);
INSERT INTO egg_sell_board(child_id, special_egg_id, wrote_at, egg_price_per_once, sell_count)
VALUES (3, 2, '2024-05-07', 1, 2);

INSERT INTO egg_count(child_id, egg_count)
VALUES (1, 1000);

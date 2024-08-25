DROP TABLE IF EXISTS egg_trade_log;
DROP TABLE IF EXISTS egg_sell_board;
DROP TABLE IF EXISTS hold_special_egg;
DROP TABLE IF EXISTS special_egg;
DROP TABLE IF EXISTS egg;
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
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS prefix_sum_exp;


create table prefix_sum_exp
(
    level          SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    prefix_sum_exp INT      NOT NULL
);

create table user
(
    user_id              INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type                 CHAR(1) NULL,
    email                VARCHAR(40) NOT NULL,
    password             VARCHAR(255) NULL,
    name                 VARCHAR(52) NOT NULL,
    nickname             VARCHAR(30) NULL,
    birthday             DATE NULL,
    user_gender          CHAR(1) NULL,
    is_sign_up_completed BOOL        NOT NULL
);

create table parent
(
    user_id INT NOT NULL PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);


create table child
(
    user_id     INT NOT NULL PRIMARY KEY,
    parent_id   INT NOT NULL,
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
    mission_finished_at DATETIME NULL,
    mission_level       CHAR(1) NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table child_savings_status_by_period
(
    period_id       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    child_id        INT  NOT NULL,
    recursion_money INT  NOT NULL,
    goal_money      INT  NOT NULL,
    start_date      DATE NOT NULL,
    end_date        DATE NOT NULL,
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
    financial_quiz_id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description       VARCHAR(200) NOT NULL,
    answer            BOOL         NOT NULL,
    quiz_keyword_id   INT          NOT NULL,
    child_id          INT          NOT NULL,
    created_at        Date         NOT NULL,
    FOREIGN KEY (quiz_keyword_id) REFERENCES quiz_keyword (quiz_keyword_id),
    FOREIGN KEY (child_id) REFERENCES child (user_id)
);

create table quiz_solve
(
    quiz_solve_id     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    financial_quiz_id INT NOT NULL,
    child_id          INT NOT NULL,
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



create table egg
(
    egg_id       INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id     INT      NOT NULL,
    created_at   DATETIME NOT NULL,
    destroyed_at DATETIME NOT NULL,
    hit_count    SMALLINT NOT NULL,
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
    egg_sell_board_id   INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    child_id            INT      NOT NULL,
    hold_special_egg_id INT      NOT NULL,
    wrote_at            DATETIME NOT NULL,
    FOREIGN KEY (child_id) REFERENCES child (user_id),
    FOREIGN KEY (hold_special_egg_id) REFERENCES hold_special_egg (hold_special_egg_id)
);


create table egg_trade_log
(
    egg_trade_log_id INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    seller_id        INT      NOT NULL,
    buyer_id         INT      NOT NULL,
    traded_at        DATETIME NOT NULL,
    special_egg_id   INT      NOT NULL,
    count            INT      NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES child (user_id),
    FOREIGN KEY (buyer_id) REFERENCES child (user_id),
    FOREIGN KEY (special_egg_id) REFERENCES special_egg (special_egg_id)
);

INSERT INTO quiz_keyword(quiz_keyword_id, keyword) VALUES (1, '통장'),
                                                          (2, '이자'),
                                                          (3, '대출'),
                                                          (4, '대출 연체'),
                                                          (5, '펀드'),
                                                          (6, '신용카드'),
                                                          (7, '대포통장'),
                                                          (8, '핀테크'),
                                                          (9, '환율'),
                                                          (10, '찢어진 돈');

INSERT INTO user(user_id, type, email, password, name, nickname, birthday, user_gender, is_sign_up_completed) VALUES
    (1, 'p', 'test1@test.test', '1234', '테스트용 부모', '테스트 부모 계정', '1997-09-09', 'm', true),
    (2, 'c', 'test2@test.test', '1234', '테스트용 아이', '테스트 아이 계정', '2019-06-24', 'm', true);

INSERT INTO parent(user_id) VALUES (1);

INSERT INTO child(user_id, parent_id, current_exp, max_exp, goal_money) values (2,  1, 0, 0, 0);

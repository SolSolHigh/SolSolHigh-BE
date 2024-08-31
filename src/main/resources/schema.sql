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
DROP TABLE IF EXISTS mission;
DROP TABLE IF EXISTS demand_deposit;
DROP TABLE IF EXISTS deposit;
DROP TABLE IF EXISTS saving;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS fcm;
DROP TABLE IF EXISTS promise;
DROP TABLE IF EXISTS child;
DROP TABLE IF EXISTS parent;
DROP TABLE IF EXISTS temporary_user;
DROP TABLE IF EXISTS master_bank_member;
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
    user_id              INT NOT NULL PRIMARY KEY,
    parent_id            INT NULL,
    current_exp          INT NOT NULL,
    max_exp              INT NOT NULL,
    deposit_goal_money   INT NOT NULL,
    deposit_reward_money INT NOT NULL,
    saving_reward_money  INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (parent_id) REFERENCES parent (user_id)
);

create table master_bank_member
(
    user_id  INT         NOT NULL PRIMARY KEY,
    user_key varchar(60) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
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

create table account
(
    account_id INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    INT         NOT NULL,
    type       CHAR(1)     NOT NULL,
    account_no VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);

create table deposit
(
    account_id INT NOT NULL PRIMARY KEY,
    FOREIGN KEY (account_id) REFERENCES account (account_id)
);

create table saving
(
    account_id INT NOT NULL PRIMARY KEY,
    FOREIGN KEY (account_id) REFERENCES account (account_id)
);

create table demand_deposit
(
    account_id INT NOT NULL PRIMARY KEY,
    FOREIGN KEY (account_id) REFERENCES account (account_id)
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
VALUES (1),
       (2),
       (5);

INSERT INTO child(user_id, parent_id, current_exp, max_exp, deposit_goal_money, deposit_reward_money,
                  saving_reward_money)
VALUES (3, 5, 132, 132, 0, 0, 0),
       (4, 5, 345, 345, 0, 0, 0);

INSERT INTO master_bank_member(user_id, user_key)
VALUES (1, 'd7201dd7-6acb-4e09-9084-92c8a1e153f3'),
       (2, '0f096f78-c0aa-4f61-9e5b-99a3a7e463b6'),
       (3, 'b027eff3-1192-44bb-a025-6ee462608577'),
       (4, 'ad218099-f7ae-4d42-95e0-ddb31f66254a'),
       (5, 'c800771b-71f6-41ad-9a24-5a610834e2ad');

INSERT INTO promise_ticket(child_id, published_at, used_at, requested_at, image_url, description)
VALUES (3, '2024-08-29T00:00:00', '2024-08-30T00:00:00', '2024-08-30T06:00:00', NULL, '가족들과 함께 요하땽이랑 에버랜드 논러 가기로 했잖아!');


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
INSERT INTO experience_log(child_id, type, description, created_at)
VALUES (3, 'MISSION1', '미션(하)을 수행했습니다!', '2024-08-30 14:22:00'),
       (4, 'QUIZ', '퀴즈를 풀었습니다!', '2024-08-30 15:10:20'),
       (3, 'MISSION2', '미션(중)을 수행했습니다!', '2024-08-30 16:15:45'),
       (4, 'QUIZ', '퀴즈를 풀었습니다!', '2024-08-30 17:45:12'),
       (3, 'MISSION3', '미션(상)을 수행했습니다!', '2024-08-30 18:30:00'),
       (4, 'QUIZ', '퀴즈를 풀었습니다!', '2024-08-30 18:40:00'),
       (3, 'MISSION3', '미션(상)을 수행했습니다!', '2024-08-31 08:00:00'),
       (4, 'QUIZ', '퀴즈를 풀었습니다!', '2024-08-31 09:45:00'),
       (3, 'MISSION2', '미션(중)을 수행했습니다!', '2024-08-31 12:30:00'),
       (4, 'MISSION3', '미션(상)을 수행했습니다!', '2024-08-31 13:50:00');


-- Mission
INSERT INTO mission(child_id, mission_description, is_finished, mission_start_at, mission_end_at, mission_level)
VALUES (4, '수학 숙제 완료하기', true, '2024-07-01 09:00:00', '2024-09-09 23:59:59', '1'),
       (4, '방 청소하기', true, '2024-07-08 09:00:00', '2024-09-09 23:59:59', '2'),
       (4, '책 읽기', true, '2024-07-15 09:00:00', '2024-09-09 23:59:59', '3'),
       (4, '일기 쓰기', true, '2024-07-02 09:00:00', '2024-09-09 23:59:59', '3'),
       (4, '체력 운동하기', true, '2024-07-09 09:00:00', '2024-09-19 23:59:59', '2'),
       (4, '나무 심기', true, '2024-07-16 09:00:00', '2024-09-22 23:59:59', '1'),
       (4, '노래 배우기', false, '2024-07-03 09:00:00', '2024-09-09 23:59:59', '1'),
       (3, '영어 숙제 완료하기', true, '2024-07-01 09:00:00', '2024-09-09 23:59:59', '1'),
       (3, '설거지하기', true, '2024-07-08 09:00:00', '2024-09-09 23:59:59', '2'),
       (3, '독서감상문 쓰기', false, '2024-07-15 09:00:00', '2024-09-09 23:59:59', '3'),
       (3, '일기 쓰기', true, '2024-07-02 09:00:00', '2024-09-09 23:59:59', '3'),
       (3, '줄넘기하기', true, '2024-07-09 09:00:00', '2024-09-19 23:59:59', '2'),
       (3, '햄스터 밥주기', true, '2024-07-16 09:00:00', '2024-09-22 23:59:59', '1'),
       (3, '태권도 학원가기', true, '2024-07-03 09:00:00', '2024-09-09 23:59:59', '1');
#        (4, '설거지하기', false, '2024-07-10 09:00:00', '2024-09-16 23:59:59', '2'),
#        (4, '일기 쓰기', false, '2024-07-17 09:00:00', '2024-09-23 23:59:59', '3'),
#        (1, '부모님과 함께 놀러가기', false, '2024-07-04 09:00:00', '2024-09-10 23:59:59', '3');

-- 규현이에게 할당된 키워드
INSERT INTO selected_quiz_keyword (child_id, quiz_keyword_id)
SELECT 4, quiz_keyword_id
FROM quiz_keyword
ORDER BY RAND()
LIMIT 3;

-- 요하에게 할당된 키워드
INSERT INTO selected_quiz_keyword (child_id, quiz_keyword_id)
SELECT 3, quiz_keyword_id
FROM quiz_keyword
ORDER BY RAND()
LIMIT 3;

-- 규현이(7세)용 퀴즈 20개 생성
INSERT INTO financial_quiz (description, answer, quiz_keyword_id, child_id, created_at, quiz_explanation)
VALUES ('은행 대출을 받으면 이자를 꼭 갚아야 할까요?', true, 1, 4, CURDATE() - INTERVAL 20 DAY, '이자는 빌린 돈에 대한 대가로 반드시 갚아야 합니다.'),
       ('신용카드로 물건을 사면, 돈을 나중에 갚아야 할까요?', true, 3, 4, CURDATE() - INTERVAL 19 DAY, '신용카드는 돈을 빌려 쓰는 것이므로 나중에 갚아야 합니다.'),
       ('핀테크는 전자 금융 서비스와 관련이 있을까요?', true, 5, 4, CURDATE() - INTERVAL 18 DAY,
        '핀테크는 금융(Finance)과 기술(Technology)을 결합한 서비스입니다.'),
       ('환율은 외국 돈과 관련이 있을까요?', true, 6, 4, CURDATE() - INTERVAL 17 DAY, '환율은 한 나라의 돈과 다른 나라의 돈을 교환할 때 사용하는 비율입니다.'),
       ('대포 통장은 범죄에 사용될 수 있을까요?', true, 4, 4, CURDATE() - INTERVAL 16 DAY, '대포 통장은 보통 불법적인 목적에 사용됩니다.'),
       ('펀드는 다양한 사람들의 돈을 모아 투자하는 방법일까요?', true, 2, 4, CURDATE() - INTERVAL 15 DAY,
        '펀드는 많은 사람들의 돈을 모아 다양한 자산에 투자하는 방법입니다.'),
       ('은행에서 돈을 빌리면 어떻게 될까요?', true, 1, 4, CURDATE() - INTERVAL 14 DAY, '은행에서 돈을 빌리면 이자를 갚아야 합니다.'),
       ('신용카드는 돈을 바로 내지 않아도 될까요?', true, 3, 4, CURDATE() - INTERVAL 13 DAY, '신용카드는 사용 후 일정 기간 후에 갚는 방식입니다.'),
       ('펀드는 위험이 있을까요?', true, 2, 4, CURDATE() - INTERVAL 12 DAY, '펀드는 투자이므로 손실의 위험이 있습니다.'),
       ('은행 대출을 받을 때 이자율은 중요할까요?', true, 1, 4, CURDATE() - INTERVAL 11 DAY, '이자율이 높을수록 갚아야 할 돈이 많아집니다.'),
       ('대포 통장을 만들면 법에 위반될까요?', true, 4, 4, CURDATE() - INTERVAL 10 DAY, '대포 통장은 불법으로 법에 위반됩니다.'),
       ('핀테크는 미래의 은행 역할을 할 수 있을까요?', true, 5, 4, CURDATE() - INTERVAL 9 DAY,
        '핀테크는 빠르고 편리한 금융 서비스를 제공하여 미래의 은행 역할을 할 수 있습니다.'),
       ('환율은 매일 바뀔까요?', true, 6, 4, CURDATE() - INTERVAL 8 DAY, '환율은 경제 상황에 따라 매일 변동할 수 있습니다.'),
       ('은행에 돈을 맡기면 안전할까요?', true, 1, 4, CURDATE() - INTERVAL 7 DAY, '은행은 돈을 안전하게 보관할 수 있는 장소입니다.'),
       ('대출을 받을 때 상환 기간은 중요할까요?', true, 1, 4, CURDATE() - INTERVAL 6 DAY, '상환 기간이 길면 이자가 많이 쌓일 수 있습니다.'),
       ('펀드를 관리하는 사람은 펀드 매니저 일까요?', true, 2, 4, CURDATE() - INTERVAL 5 DAY, '펀드는 전문 투자자가 관리합니다.'),
       ('신용카드를 잃어버리면 신고해야 할까요?', true, 3, 4, CURDATE() - INTERVAL 4 DAY, '신용카드를 잃어버리면 즉시 신고해야 합니다.'),
       ('대포 통장은 위험할까요?', true, 4, 4, CURDATE() - INTERVAL 3 DAY, '대포 통장은 범죄에 사용될 수 있어 위험합니다.'),
       ('핀테크를 사용하면 편리할까요?', true, 5, 4, CURDATE() - INTERVAL 2 DAY, '핀테크는 온라인에서 편리하게 금융 서비스를 사용할 수 있습니다.'),
       ('환율이 오르면 수입품 가격이 어떻게 될까요?', true, 6, 4, CURDATE() - INTERVAL 1 DAY, '환율이 오르면 수입품 가격이 상승할 수 있습니다.');

-- 요하(4세)용 퀴즈 13개 생성
INSERT INTO financial_quiz (description, answer, quiz_keyword_id, child_id, created_at, quiz_explanation)
VALUES ('돈은 어디서 나올까요? 집에서 찍어낼 수 있을까요?', false, 7, 3, CURDATE() - INTERVAL 13 DAY, '돈은 은행에서 만들어지는 것이며, 집에서 만들 수 없습니다.'),
       ('찢어진 돈은 사용할 수 있을까요?', false, 8, 3, CURDATE() - INTERVAL 12 DAY, '찢어진 돈은 은행에서 교환해야 사용할 수 있습니다.'),
       ('폐기된 돈은 다시 사용할 수 있을까요?', false, 9, 3, CURDATE() - INTERVAL 11 DAY, '폐기된 돈은 더 이상 사용할 수 없습니다.'),
       ('핀테크는 어린이도 사용할 수 있을까요?', true, 5, 3, CURDATE() - INTERVAL 10 DAY, '핀테크 서비스는 어린이도 사용할 수 있는 서비스가 있습니다.'),
       ('신용카드는 어린이도 사용할 수 있을까요?', false, 3, 3, CURDATE() - INTERVAL 9 DAY, '신용카드는 보통 어른만 사용할 수 있습니다.'),
       ('은행에서 돈을 맡기면 이자를 받을 수 있을까요?', true, 1, 3, CURDATE() - INTERVAL 8 DAY, '은행에 돈을 맡기면 이자를 받을 수 있습니다.'),
       ('돈은 종이나 금속으로 만들어질까요?', true, 7, 3, CURDATE() - INTERVAL 7 DAY, '돈은 종이나 금속으로 만들어집니다.'),
       ('폐기된 돈은 사용할 수 없을까요?', true, 9, 3, CURDATE() - INTERVAL 6 DAY, '폐기된 돈은 가치가 없어졌기 때문에 사용할 수 없습니다.'),
       ('은행 대출은 누구나 받을 수 있을까요?', false, 1, 3, CURDATE() - INTERVAL 5 DAY, '은행 대출은 신용이 있는 사람만 받을 수 있습니다.'),
       ('찢어진 돈은 은행에서 교환할 수 있을까요?', true, 8, 3, CURDATE() - INTERVAL 4 DAY, '찢어진 돈은 은행에서 교환할 수 있습니다.'),
       ('핀테크는 금융과 기술을 결합한 서비스일까요?', true, 5, 3, CURDATE() - INTERVAL 3 DAY, '핀테크는 금융과 기술을 결합한 서비스입니다.'),
       ('신용카드를 사용할 때 조심해야 할 것은 지출일까요?', true, 3, 3, CURDATE() - INTERVAL 2 DAY, '신용카드를 사용할 때는 과도한 지출을 조심해야 합니다.'),
       ('은행은 돈을 금고에 보관할까요?', true, 7, 3, CURDATE() - INTERVAL 1 DAY, '은행은 돈을 안전한 금고에 보관합니다.');


-- 규현이 20일치 퀴즈 풀이 내역
INSERT INTO quiz_solve (financial_quiz_id, child_id, is_correct, corrected_at)
VALUES (1, 4, false, CURDATE() - INTERVAL (20) DAY),
       (2, 4, false, CURDATE() - INTERVAL (19) DAY),
       (3, 4, true, CURDATE() - INTERVAL (18) DAY),
       (4, 4, true, CURDATE() - INTERVAL (17) DAY),
       (5, 4, true, CURDATE() - INTERVAL (16) DAY),
       (6, 4, false, CURDATE() - INTERVAL (15) DAY),
       (7, 4, true, CURDATE() - INTERVAL (14) DAY),
       (8, 4, true, CURDATE() - INTERVAL (13) DAY),
       (9, 4, true, CURDATE() - INTERVAL (12) DAY),
       (10, 4, true, CURDATE() - INTERVAL (11) DAY),
       (11, 4, false, CURDATE() - INTERVAL (10) DAY),
       (12, 4, true, CURDATE() - INTERVAL (9) DAY),
       (13, 4, false, CURDATE() - INTERVAL (8) DAY),
       (14, 4, false, CURDATE() - INTERVAL (7) DAY),
       (15, 4, true, CURDATE() - INTERVAL (6) DAY),
       (16, 4, true, CURDATE() - INTERVAL (5) DAY),
       (17, 4, true, CURDATE() - INTERVAL (4) DAY),
       (18, 4, true, CURDATE() - INTERVAL (3) DAY),
       (19, 4, true, CURDATE() - INTERVAL (2) DAY),
       (20, 4, true, CURDATE() - INTERVAL (1) DAY);


-- 요하 13일치 퀴즈 풀이 내역
INSERT INTO quiz_solve (financial_quiz_id, child_id, is_correct, corrected_at)
VALUES (21, 3, false, CURDATE() - INTERVAL (20) DAY),
       (22, 3, false, CURDATE() - INTERVAL (19) DAY),
       (23, 3, true, CURDATE() - INTERVAL (18) DAY),
       (24, 3, true, CURDATE() - INTERVAL (17) DAY),
       (25, 3, true, CURDATE() - INTERVAL (16) DAY),
       (26, 3, false, CURDATE() - INTERVAL (15) DAY),
       (27, 3, true, CURDATE() - INTERVAL (14) DAY),
       (28, 3, true, CURDATE() - INTERVAL (13) DAY),
       (29, 3, true, CURDATE() - INTERVAL (12) DAY),
       (30, 3, true, CURDATE() - INTERVAL (11) DAY),
       (31, 3, false, CURDATE() - INTERVAL (10) DAY),
       (32, 3, true, CURDATE() - INTERVAL (9) DAY),
       (33, 3, false, CURDATE() - INTERVAL (8) DAY);

-- 규현이의 6일간 퀴즈 풀이 로그 (스트릭)
INSERT
INTO between_of_day_quiz_solve_log (child_id, started_at, end_at, count)
VALUES (4, CURDATE() - INTERVAL 6 DAY, CURDATE(), 6);

# -- Financial Quiz
# INSERT INTO financial_quiz(description, answer, quiz_keyword_id, child_id, created_at, quiz_explanation)
# VALUES ('이자가 무엇일까요?', true, 1, 1, '2024-07-01', 'A number representing a person\'s creditworthiness.'),
#        ('이 문제는 무엇일까요?', true, 2, 1, '2024-07-02', 'Annual Percentage Rate.'),
#        ('이자가 무엇일까요2?', true, 3, 1, '2024-07-03', 'Interest on interest.'),
#        ('해커톤 우승은 누구죠?', false, 4, 3, '2024-07-04', '해커톤 우승은 구황작물입니다.'),
#        ('이자가 무엇일까요4?', true, 5, 3, '2024-07-05', 'A loan for buying a house.'),
#        ('이자가 무엇일까요5?', true, 6, 3, '2024-07-06', 'Increase in prices over time.'),
#        ('이자가 무엇일까요6?', false, 7, 4, '2024-07-07', 'A plan for income and expenses.'),
#        ('이자가 무엇일까요7?', true, 8, 4, '2024-07-08', 'Account for saving money.'),
#        ('이자가 무엇일까요8?', true, 9, 4, '2024-07-09', 'A fixed income instrument.'),
#        ('이자가 무엇일까요9?', true, 1, 1, '2024-07-10', 'Ownership in a company.');
#
# -- Quiz Solve
# INSERT INTO quiz_solve(financial_quiz_id, child_id, is_correct, corrected_at)
# VALUES (1, 1, true, '2024-07-01'),
#        (2, 1, false, '2024-07-02'),
#        (3, 1, true, '2024-07-03'),
#        (4, 3, false, '2024-07-04'),
#        (5, 3, true, '2024-07-05'),
#        (6, 3, false, '2024-07-06'),
#        (7, 4, true, '2024-07-07'),
#        (8, 4, false, '2024-07-08'),
#        (9, 4, true, '2024-07-09'),
#        (10, 1, true, '2024-07-10');
#
# -- Between of Day Quiz Solve Log
# INSERT INTO between_of_day_quiz_solve_log(child_id, started_at, end_at, count)
# VALUES (3, '2024-08-25', '2024-08-31', 6),
#        (4, '2024-08-25', '2024-08-31', 6);
#
# -- Selected Quiz Keyword
# INSERT INTO selected_quiz_keyword(child_id, quiz_keyword_id)
# VALUES (3, 1),
#        (3, 4),
#        (3, 5),
#        (4, 1),
#        (4, 6),
#        (4, 7);

-- Egg Count
INSERT INTO egg_count(child_id, egg_count)
VALUES (4, 10);

INSERT INTO egg_destroy_log(child_id, created_at, destroyed_at, hit_count)
VALUES (4, CURDATE(), CURDATE(), 100),
       (4, CURDATE(), CURDATE(), 100),
       (4, CURDATE(), CURDATE(), 100),
       (4, CURDATE(), CURDATE(), 100),
       (4, CURDATE(), CURDATE(), 100);
#        (4, CURDATE(), CURDATE(), 100),
#        (4, CURDATE(), CURDATE(), 100),
#        (4, CURDATE(), CURDATE(), 100),
#        (4, CURDATE(), CURDATE(), 100),

-- Hold Special Egg
INSERT INTO hold_special_egg(child_id, special_egg_id, egg_count)
VALUES (4, 1, 1),
       (4, 2, 1),
       (4, 3, 1),
       (4, 6, 1),
       (4, 7, 1),
       (4, 8, 1),
       (4, 10, 1),
       (4, 11, 1),
       (4, 12, 1);

-- Egg Sell Board
INSERT INTO egg_sell_board(child_id, special_egg_id, wrote_at, egg_price_per_once, sell_count)
VALUES (3, 1, '2024-07-01 08:00:00', 1, 5),
       (3, 2, '2024-07-02 09:00:00', 1, 5),
       (3, 4, '2024-07-03 10:00:00', 1, 5),
       (3, 9, '2024-07-04 11:00:00', 1, 3),
       (3, 3, '2024-07-05 12:00:00', 1, 3),
       (3, 12, '2024-07-06 13:00:00', 1, 3),
       (3, 7, '2024-07-07 14:00:00', 1, 3),
       (3, 5, '2024-07-08 15:00:00', 1, 2),
       (3, 11, '2024-07-09 16:00:00', 1, 2),
       (3, 10, '2024-07-10 17:00:00', 1, 2);

INSERT INTO egg_trade_log(seller_id, buyer_id, traded_at, special_egg_id, egg_stock_count, egg_price_per_once)
VALUES (4, 3, '2024-08-29 08:00:00', 1, 1, 5),
       (4, 3, '2024-08-29 08:00:00', 2, 1, 5),
       (4, 3, '2024-08-29 08:00:00', 4, 1, 5),
       (4, 3, '2024-08-29 08:00:00', 9, 1, 3),
       (4, 3, '2024-08-29 08:00:00', 3, 1, 3),
       (4, 3, '2024-08-29 08:00:00', 12, 1, 3),
       (4, 3, '2024-08-29 08:00:00', 7, 1, 3),
       (4, 3, '2024-08-29 08:00:00', 5, 1, 2),
       (4, 3, '2024-08-29 08:00:00', 11, 1, 2),
       (4, 3, '2024-08-29 08:00:00', 10, 1, 2),

       (4, 3, '2024-08-28 08:00:00', 1, 1, 6),
       (4, 3, '2024-08-28 08:00:00', 2, 1, 6),
       (4, 3, '2024-08-28 08:00:00', 4, 1, 6),
       (4, 3, '2024-08-28 08:00:00', 9, 1, 4),
       (4, 3, '2024-08-28 08:00:00', 3, 1, 4),
       (4, 3, '2024-08-28 08:00:00', 12, 1, 4),
       (4, 3, '2024-08-28 08:00:00', 7, 1, 4),
       (4, 3, '2024-08-28 08:00:00', 5, 1, 3),
       (4, 3, '2024-08-28 08:00:00', 11, 1, 3),
       (4, 3, '2024-08-28 08:00:00', 10, 1, 3),

       (4, 3, '2024-08-27 08:00:00', 1, 1, 4),
       (4, 3, '2024-08-27 08:00:00', 2, 1, 4),
       (4, 3, '2024-08-27 08:00:00', 4, 1, 4),
       (4, 3, '2024-08-27 08:00:00', 9, 1, 2),
       (4, 3, '2024-08-27 08:00:00', 3, 1, 2),
       (4, 3, '2024-08-27 08:00:00', 12, 1, 2),
       (4, 3, '2024-08-27 08:00:00', 7, 1, 2),
       (4, 3, '2024-08-27 08:00:00', 5, 1, 1),
       (4, 3, '2024-08-27 08:00:00', 11, 1, 1),
       (4, 3, '2024-08-27 08:00:00', 10, 1, 1);


INSERT INTO account(account_id, user_id, type, account_no)
VALUES (1, 5, "a", "0884283034654375"),
       (2, 4, "a", "0883927112362781"),
       (3, 4, "b", "0884355137682027"),
       (4, 3, "a", "0881939882744862"),
       (5, 3, "b", "0882096701635347");

INSERT INTO deposit(account_id)
VALUES (3),
       (5);

INSERT INTO demand_deposit(account_id)
VALUES (1),
       (2),
       (4);

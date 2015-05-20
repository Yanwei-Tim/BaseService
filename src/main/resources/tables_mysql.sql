
    drop table if exists us_alarm;

    drop table if exists us_alarm_history;

    drop table if exists us_device;

    drop table if exists us_device_alarm;

    drop table if exists us_instruct;

    drop table if exists us_position;

    drop table if exists us_report;

    drop table if exists us_spot;

    drop table if exists us_third_account;

    drop table if exists us_user;

    drop table if exists us_user_device;

    drop table if exists us_user_token;

    create table us_alarm (
        f_id bigint not null auto_increment,
        f_device_sn varchar(28),
        f_info varchar(255),
        f_lat double precision,
        f_lng double precision,
        f_read integer,
        f_time bigint,
        f_type integer,
        primary key (f_id)
    );

    create table us_alarm_history (
        f_id bigint not null auto_increment,
        f_addr varchar(255),
        f_device_sn varchar(28),
        f_info varchar(255),
        f_lat double precision,
        f_lng double precision,
        f_read integer,
        f_time bigint,
        f_type integer,
        primary key (f_id)
    );

    create table us_device (
        f_sn varchar(28) not null,
        f_age integer,
        f_battery integer,
        f_begin bigint,
        f_car varchar(255),
        f_email varchar(255),
        f_end bigint,
        f_fence blob,
        f_fence_switch integer,
        f_flow float,
        f_gender integer,
        f_hardware varchar(255),
        f_height integer,
        f_icon varchar(255),
        f_moveing_switch integer,
        f_name varchar(255),
        f_phone varchar(255),
        f_sos_num varchar(255),
        f_speed_threshold integer,
        f_speeding_switch integer,
        f_stamp bigint,
        f_tick integer,
        f_type integer,
        f_weight integer,
        primary key (f_sn)
    );

    create table us_device_alarm (
        f_device_sn varchar(28) not null,
        f_alarms blob,
        primary key (f_device_sn)
    );

    create table us_instruct (
        f_id varchar(255) not null,
        f_content varchar(255),
        f_device_sn varchar(255),
        f_num integer,
        f_original varchar(255),
        f_reply integer,
        f_type integer,
        primary key (f_id)
    );

    create table us_position (
        f_device_sn varchar(28) not null,
        f_alarm integer,
        f_battery integer,
        f_direction float,
        f_flow float,
        f_info varchar(255),
        f_lat double precision,
        f_lng double precision,
        f_mode varchar(1),
        f_receive bigint,
        f_speed double precision,
        f_status integer,
        f_stayed integer,
        primary key (f_device_sn)
    );

    create table us_report (
        f_id varchar(255) not null,
        f_day bigint,
        f_device_sn varchar(255),
        f_distance double precision,
        f_distance10 double precision,
        f_distance30 double precision,
        f_distance36 double precision,
        f_distance61 double precision,
        f_speeding integer,
        f_stop float,
        primary key (f_id)
    );

    create table us_spot (
        f_device_sn varchar(28) not null,
        f_receive bigint not null,
        f_direction float,
        f_distance double precision,
        f_info varchar(255),
        f_lat double precision,
        f_lng double precision,
        f_mode varchar(1),
        f_speed double precision,
        f_stayed integer,
        f_systime bigint,
        primary key (f_device_sn, f_receive)
    );

    create table us_third_account (
        f_id bigint not null auto_increment,
        f_account varchar(255),
        f_type varchar(255),
        f_user_id integer,
        primary key (f_id)
    );

    create table us_user (
        f_id bigint not null auto_increment,
        f_email varchar(255),
        f_name varchar(255) not null,
        f_password varchar(255),
        f_phone varchar(255),
        f_qq_id varchar(255),
        f_rr_id varchar(255),
        f_sina_id varchar(255),
        primary key (f_id)
    );

    create table us_user_device (
        f_id bigint not null auto_increment,
        f_device_sn varchar(255),
        f_user_id bigint,
        primary key (f_id)
    );

    create table us_user_token (
        f_user_id bigint not null,
        f_expire bigint,
        f_token varchar(255),
        primary key (f_user_id)
    );
    
    create table us_share (
        f_id bigint not null auto_increment,
        f_device_sn varchar(512) not null,
        f_location_time varchar(512),
        f_begin bigint not null,
        f_end bigint not null,
        f_user_id bigint not null,
        f_privacy_type integer not null,
        f_publish bigint,
        f_act bigint,
        f_expire bigint,
        f_content_type integer not null,
        primary key (f_id)
    );
    
    create table us_hardware (
        f_id bigint not null auto_increment,
        f_name varchar(20) not null,
        f_type smallint(2) not null,
        f_manufacturers varchar(50) not null,
        f_support int not null,
        primary key (f_id)
    );
    
    create table us_sell (
        f_id bigint not null auto_increment,
        f_hardware_name varchar(20) not null,
        f_where varchar(255) not null,
        f_count int not null,
        f_time bigint not null,
        primary key (f_id)
    );
    
        create table us_research (
        f_user_id bigint not null comment '用户编号',
        f_status smallint(2) not null comment '完成状态',
        f_topic_ids varchar(128) comment '已完成的调查问卷题目编号',
        f_time bigint not null comment '时间戳',
        primary key (f_user_id)
    );
    
        create table us_research_topic (
        f_id bigint not null comment '编号',
        f_name varchar(255) not null comment '题目',
        f_content varchar(512) not null comment '内容',
        f_type int not null comment '类型',
        primary key (f_id)
    );
    
    create table us_research_answers (
        f_id bigint not null comment '编号',
        f_user_id bigint not null comment '用户编号',
        f_topic_id bigint not null comment '题目编号',
        f_result varchar(64) not null comment '答案',
        primary key (f_id)
    );
    
    create table us_function_set (
        f_id bigint not null comment '编号',
        f_name varchar(32) not null comment '名称',
        primary key (f_id)
    );

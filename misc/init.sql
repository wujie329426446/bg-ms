DROP
DATABASE IF EXISTS bg_ms;
CREATE
DATABASE bg_ms CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE
bg_ms;


--
-- Table structure for table `sys_log`
--
DROP TABLE IF EXISTS `sys_log`;
create table sys_log
(
    id                varchar(32)   not null comment '主键',
    trace_id          varchar(32)   null comment '日志链路ID',
    request_time      varchar(30)   null comment '请求时间',
    request_url       varchar(1000) null comment '全路径',
    permission_code   varchar(200)  null comment '权限编码',
    log_name          varchar(200)  null comment '日志名称',
    request_method    varchar(10)   null comment '请求方式，GET/POST',
    content_type      varchar(200)  null comment '内容类型',
    is_request_body   tinyint(1)    null comment '是否是JSON请求映射参数',
    token             varchar(256)  null comment 'token',
    module_name       varchar(100)  null comment '模块名称',
    class_name        varchar(200)  null comment 'controller类名称',
    method_name       varchar(200)  null comment 'controller方法名称',
    request_param     text          null comment '请求参数',
    user_id           varchar(32)   null comment '用户ID',
    user_name         varchar(100)  null comment '用户名',
    request_ip        varchar(15)   null comment '请求ip',
    ip_country        varchar(100)  null comment 'IP国家',
    ip_province       varchar(100)  null comment 'IP省份',
    ip_city           varchar(100)  null comment 'IP城市',
    ip_area_desc      varchar(100)  null comment 'IP区域描述',
    ip_isp            varchar(100)  null comment 'IP运营商',
    log_type          int           default 0 not null comment '0:其它,1:新增,2:修改,3:删除,4:详情查询,5:所有列表,6:分页列表,7:其它查询,8:上传文件',
    response_time     varchar(100)  null comment '响应时间',
    response_success  tinyint(1)    null comment '0:失败,1:成功',
    response_code     int           null comment '响应结果状态码',
    response_message  text          null comment '响应结果消息',
    response_data     text          null comment '响应数据',
    exception_name    varchar(200)  null comment '异常类名称',
    exception_message text          null comment '异常信息',
    diff_time         bigint        null comment '耗时，单位：毫秒',
    diff_time_desc    varchar(100)  null comment '耗时描述',
    referer           varchar(1000) null comment '请求来源地址',
    origin            varchar(1000) null comment '请求来源服务名',
    source_type       varchar(100)  null comment '请求来源类型',
    is_mobile         tinyint(1)    null comment '是否手机 0：否，1：是',
    platform_name     varchar(100)  null comment '平台名称',
    browser_name      varchar(100)  null comment '浏览器名称',
    user_agent        varchar(1000) null comment '用户环境',
    remark            varchar(512)  null comment '备注',
    create_time       datetime      default null comment '创建时间',
    update_time       datetime      default null comment '修改时间',
    primary key (`id`) using btree
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='操作日志类';
--
-- Dumping index for table `sys_log`
--
create index sys_log_log_type_index on sys_log (log_type);
create index sys_log_module_name_index on sys_log (module_name);
create index sys_log_request_ip_index on sys_log (request_ip);
create index sys_log_response_success_index on sys_log (response_success);
create index sys_log_trace_id_index on sys_log (trace_id);
create index sys_log_username_index on sys_log (user_name);


--
-- Table structure for table `sys_log_login`
--
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE sys_log_login
(
    id         varchar(32)  not null comment '日志ID',
    user_id    varchar(32)  default null comment '用户ID',
    account    varchar(128) default null comment '登陆账号',
    login_type varchar(32)  default null comment '登陆类型',
    os         varchar(64)  default null comment '操作系统',
    browser    varchar(64)  default null comment '浏览器类型',
    ip         varchar(64)  default null comment '登录IP地址',
    location   varchar(64)  default null comment '登录地点',
    login_time datetime     default null comment '登录时间',
    success    tinyint(1)   default null comment '是否成功',
    message    varchar(128) default null comment '返回消息',
    primary key (`id`) using btree
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='登陆日志表';
--
-- Dumping index for table `sys_log_login`
--
create index sys_log_login_user_id_index on sys_log_login (user_id);
create index sys_log_login_account_index on sys_log_login (account);
create index sys_log_login_login_type_index on sys_log_login (login_type);
create index sys_log_login_success_index on sys_log_login (success);



--
-- Table structure for table `sys_dept`
--
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`            varchar(32)         not null comment '主键',
    `dept_name`     varchar(32)         not null comment '部门名称',
    `dept_code`     varchar(32)         not null comment '部门编码',
    `parent_id`     varchar(32)         default null comment '父id',
    `level`         int                 default null comment '部门层级',
    `level_code`    varchar(512)        default null comment '层级编码，部门编码|部门编码|部门编码',
    `sort`          int                 not null default '0' comment '排序',
    `version`       int                 not null default '0' comment '版本',
    `create_time`   datetime            default current_timestamp comment '创建时间',
    `creator_id`    varchar(32)         default null comment '创建人id',
    `creator_name`  varchar(50)         default null comment '创建人姓名',
    `update_time`   datetime            default current_timestamp comment '修改时间',
    `update_id`     varchar(32)         default null comment '更新人id',
    `update_name`   varchar(50)         default null comment '更新人姓名',
    `remark`        varchar(512)        default null comment '备注',
    `status`        tinyint(1)          not null default '1' comment '状态，0：禁用，1：启用',
    `deleted`       tinyint(1)          default '0' comment '是否删除，0否，1是',
    primary key (`id`) using btree
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='部门';
--
-- Dumping index for table `sys_dept`
--
create index sys_dept_dept_name_index on sys_dept (dept_name);
create index sys_dept_status_index on sys_dept (status);
create index sys_dept_parent_id_index on sys_dept (parent_id);
create index sys_dept_deleted_index on sys_dept (deleted);
--
-- Dumping data for table `sys_dept`
--
-- INSERT INTO `sys_dept` (`id`, `dept_name`, `parent_id`, `level`, `status`, `sort`, `remark`, `version`, `create_time`, `update_time`)
-- VALUES (1, '管理组', null, null, 1, 1, '顶级部门', 0, null, null);


--
-- Table structure for table `sys_menu`
--
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`            varchar(32)         not null comment '主键',
    `menu_name`     varchar(32)         default null comment '菜单名称',
    `parent_id`     varchar(32)         default null comment '父id',
    `route_path`    varchar(200)        default null comment '路径',
    `code`          varchar(100)        default null comment '唯一编码',
    `icon`          varchar(100)        default null comment '图标',
    `type`          int                 not null comment '类型，0：目录，1：菜单，2：按钮',
    `level`         int                 not null comment '层级，1：第一级，2：第二级，n：第n级',
    `sort`          int                 not null default '0' comment '排序',
    `component`     varchar(255)        default null comment '组件',
    `is_show`       int                 default null,
    `keep_alive`    int                 default null,
    `is_ext`        int                 default null,
    `frame`         int                 default null,
    `version`       int                 not null default '0' comment '版本',
    `create_time`   datetime            default current_timestamp comment '创建时间',
    `creator_id`    varchar(32)         default null comment '创建人id',
    `creator_name`  varchar(50)         default null comment '创建人姓名',
    `update_time`   datetime            default current_timestamp comment '修改时间',
    `update_id`     varchar(32)         default null comment '更新人id',
    `update_name`   varchar(50)         default null comment '更新人姓名',
    `remark`        varchar(512)        default null comment '备注',
    `status`        tinyint(1)          not null default '1' comment '状态，0：禁用，1：启用',
    `deleted`       tinyint(1)          default '0' comment '是否删除，0否，1是',
    primary key (`id`) using btree
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='系统权限';
--
-- Dumping index for table `sys_menu`
--
create index sys_menu_name_index on sys_menu (menu_name);
create index sys_menu_parent_id_index on sys_menu (parent_id);
create index sys_menu_status_index on sys_menu (status);
create index sys_menu_deleted_index on sys_menu (deleted);
--
-- Dumping data for table `sys_menu`
--
-- 系统管理
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('d48019c64b9011eea1093a31d6d59109', '系统管理', NULL, 'system', NULL, 'ri:admin-line', 1, 1, 1, 999, NULL, 0, NULL, 1, 1, 0, NULL);
-- 用户管理
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('3145915e4b9111eea1093a31d6d59109', '用户管理', 'd48019c64b9011eea1093a31d6d59109', 'user', 'sys:user:management', NULL, 2, 2, 1, 1, NULL, 0, '/sys/account/index', 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('dbd16a714b9111eea1093a31d6d59109', '用户新增', '3145915e4b9111eea1093a31d6d59109', NULL, 'sys:user:add', NULL, 3, 3, 1, 1, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('e47b1a384b9111eea1093a31d6d59109', '用户修改', '3145915e4b9111eea1093a31d6d59109', NULL, 'sys:user:update', NULL, 3, 3, 1, 2, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('ee27cf3c4b9111eea1093a31d6d59109', '用户删除', '3145915e4b9111eea1093a31d6d59109', NULL, 'sys:user:delete', NULL, 3, 3, 1, 3, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('f5cfccbf4b9111eea1093a31d6d59109', '用户详情', '3145915e4b9111eea1093a31d6d59109', NULL, 'sys:user:info', NULL, 3, 3, 1, 4, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('02b965f64b9211eea1093a31d6d59109', '用户分页列表', '3145915e4b9111eea1093a31d6d59109', NULL, 'sys:user:page', NULL, 3, 3, 1, 5, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('0949ec224b9211eea1093a31d6d59109', '用户修改密码', '3145915e4b9111eea1093a31d6d59109', NULL, 'sys:user:update:password', NULL, 3, 3, 1, 6, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('1ef635574b9211eea1093a31d6d59109', '用户修改头像', '3145915e4b9111eea1093a31d6d59109', NULL, 'sys:user:update:head', NULL, 3, 3, 1, 7, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('26b4c02b4b9211eea1093a31d6d59109', '用户重置密码', '3145915e4b9111eea1093a31d6d59109', NULL, 'sys:user:reset:password', NULL, 3, 3, 1, 8, NULL, 0, NULL, 1, 1, 0, NULL);
-- 角色管理
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('598f87f64b9111eea1093a31d6d59109', '角色管理', 'd48019c64b9011eea1093a31d6d59109', 'role', 'sys:role:management', NULL, 2, 2, 1, 2, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('3238eff24b9211eea1093a31d6d59109', '角色新增', '598f87f64b9111eea1093a31d6d59109', NULL, 'sys:role:add', NULL, 3, 3, 1, 1, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('39a400e64b9211eea1093a31d6d59109', '角色修改', '598f87f64b9111eea1093a31d6d59109', NULL, 'sys:role:update', NULL, 3, 3, 1, 2, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('409427cd4b9211eea1093a31d6d59109', '角色删除', '598f87f64b9111eea1093a31d6d59109', NULL, 'sys:role:delete', NULL, 3, 3, 1, 3, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('4755c9aa4b9211eea1093a31d6d59109', '角色详情', '598f87f64b9111eea1093a31d6d59109', NULL, 'sys:role:info', NULL, 3, 3, 1, 4, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('4f410ca14b9211eea1093a31d6d59109', '角色分页列表', '598f87f64b9111eea1093a31d6d59109', NULL, 'sys:role:page', NULL, 3, 3, 1, 5, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('55d4960d4b9211eea1093a31d6d59109', '角色列表', '598f87f64b9111eea1093a31d6d59109', NULL, 'sys:role:list', NULL, 3, 3, 1, 6, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('5deb7d764b9211eea1093a31d6d59109', '角色权限ID列表', '598f87f64b9111eea1093a31d6d59109', NULL, 'sys:permission:three-ids-by-role-id', NULL, 3, 3, 1, 7, NULL, 0, NULL, 1, 1, 0, NULL);
-- 权限管理
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('8a6da18a4b9111eea1093a31d6d59109', '权限管理', 'd48019c64b9011eea1093a31d6d59109', 'permission', 'sys:permission:management', NULL, 2, 2, 1, 3, NULL, 0, '/sys/menu/index', 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('66879d214b9211eea1093a31d6d59109', '权限新增', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:add', NULL, 3, 3, 1, 1, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('6e03ce8a4b9211eea1093a31d6d59109', '权限修改', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:update', NULL, 3, 3, 1, 2, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('74910fc74b9211eea1093a31d6d59109', '权限删除', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:delete', NULL, 3, 3, 1, 3, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('7bd216c14b9211eea1093a31d6d59109', '权限详情', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:info', NULL, 3, 3, 1, 4, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('8394c7374b9211eea1093a31d6d59109', '权限分页列表', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:page', NULL, 3, 3, 1, 5, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('8b4e4fd94b9211eea1093a31d6d59109', '权限所有列表', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:all:menu:list', NULL, 3, 3, 1, 6, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('924e7e284b9211eea1093a31d6d59109', '权限所有树形列表', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:all:menu:tree', NULL, 3, 3, 1, 7, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('9acce4f44b9211eea1093a31d6d59109', '权限用户列表', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:menu:list', NULL, 3, 3, 1, 8, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('a5b3b42b4b9211eea1093a31d6d59109', '权限用户树形列表', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:menu:tree', NULL, 3, 3, 1, 9, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('ad51cd784b9211eea1093a31d6d59109', '权限用户代码列表', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:permission:codes', NULL, 3, 3, 1, 19, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('b56008294b9211eea1093a31d6d59109', '角色权限修改', '8a6da18a4b9111eea1093a31d6d59109', NULL, 'sys:role-permission:update', NULL, 3, 3, 1, 12, NULL, 0, NULL, 1, 1, 0, NULL);
-- 部门管理
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('b2fae1bd4b9111eea1093a31d6d59109', '部门管理', 'd48019c64b9011eea1093a31d6d59109', 'department', 'sys:department:management', NULL, 2, 2, 1, 4, NULL, 0, '/sys/dept/index', 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('bc2aeabe4b9211eea1093a31d6d59109', '部门新增', 'b2fae1bd4b9111eea1093a31d6d59109', NULL, 'sys:department:add', NULL, 3, 3, 1, 1, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('c27bc9984b9211eea1093a31d6d59109', '部门修改', 'b2fae1bd4b9111eea1093a31d6d59109', NULL, 'sys:department:update', NULL, 3, 3, 1, 2, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('c8f1e52c4b9211eea1093a31d6d59109', '部门删除', 'b2fae1bd4b9111eea1093a31d6d59109', NULL, 'sys:department:delete', NULL, 3, 3, 1, 3, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('d00a77cf4b9211eea1093a31d6d59109', '部门详情', 'b2fae1bd4b9111eea1093a31d6d59109', NULL, 'sys:department:info', NULL, 3, 3, 1, 4, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('d73b03fb4b9211eea1093a31d6d59109', '部门分页列表', 'b2fae1bd4b9111eea1093a31d6d59109', NULL, 'sys:department:page', NULL, 3, 3, 1, 5, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('ddc5b7984b9211eea1093a31d6d59109', '部门列表', 'b2fae1bd4b9111eea1093a31d6d59109', NULL, 'sys:department:list', NULL, 3, 3, 1, 6, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('e794f0ec4b9211eea1093a31d6d59109', '部门树形列表', 'b2fae1bd4b9111eea1093a31d6d59109', NULL, 'sys:department:tree', NULL, 3, 3, 1, 7, NULL, 0, NULL, 1, 1, 0, NULL);
-- 日志管理
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('c51f57d94b9111eea1093a31d6d59109', '日志管理', 'd48019c64b9011eea1093a31d6d59109', 'log', 'sys:log:manager', NULL, 2, 2, 1, 5, NULL, 0, NULL, 0, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('fad4e2304b9211eea1093a31d6d59109', '操作日志列表', 'c51f57d94b9111eea1093a31d6d59109', NULL, 'sys:operation:log:page', NULL, 3, 3, 1, 1, NULL, 0, NULL, 1, 1, 0, NULL);
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `route_path`, `code`, `icon`, `type`, `level`, `status`, `sort`, `remark`, `version`, `component`, `is_show`, `keep_alive`, `is_ext`, `frame`)
VALUES ('03a85ada4b9311eea1093a31d6d59109', '登录日志列表', 'c51f57d94b9111eea1093a31d6d59109', NULL, 'sys:login:log:page', NULL, 3, 3, 1, 2, NULL, 0, NULL, 1, 1, 0, NULL);


--
-- Table structure for table `sys_role`
--
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`            varchar(32)         not null comment '主键id',
    `role_name`     varchar(32)         not null comment '角色名称',
    `role_code`     varchar(100)        default null comment '角色唯一编码',
    `type`          int                 default null comment '角色类型',
    `version`       int                 not null default '0' comment '版本',
    `create_time`   datetime            default current_timestamp comment '创建时间',
    `creator_id`    varchar(32)         default null comment '创建人id',
    `creator_name`  varchar(50)         default null comment '创建人姓名',
    `update_time`   datetime            default current_timestamp comment '修改时间',
    `update_id`     varchar(32)         default null comment '更新人id',
    `update_name`   varchar(50)         default null comment '更新人姓名',
    `remark`        varchar(512)        default null comment '备注',
    `status`        tinyint(1)          not null default '1' comment '状态，0：禁用，1：启用',
    `deleted`       tinyint(1)          default '0' comment '是否删除，0否，1是',
    primary key (`id`) using btree
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='系统角色';
--
-- Dumping index for table `sys_role`
--
create index sys_role_role_name_index on sys_role (role_name);
create index sys_role_status_index on sys_role (status);
create index sys_role_deleted_index on sys_role (deleted);
--
-- Dumping data for table `sys_role`
--
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `type`, `status`, `description`, `version`)
VALUES ('db3b2e464b8f11eea1093a31d6d59109', '管理员', 'admin', NULL, 1, '管理员', 0);


--
-- Table structure for table `sys_role_menu`
--
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`            varchar(32)         not null comment '主键',
    `role_id`       varchar(32)         not null comment '角色id',
    `permission_id` varchar(32)         not null comment '权限id',
    `version`       int                 not null default '0' comment '版本',
    `create_time`   datetime            default current_timestamp comment '创建时间',
    `creator_id`    varchar(32)         default null comment '创建人id',
    `creator_name`  varchar(50)         default null comment '创建人姓名',
    `update_time`   datetime            default current_timestamp comment '修改时间',
    `update_id`     varchar(32)         default null comment '更新人id',
    `update_name`   varchar(50)         default null comment '更新人姓名',
    `remark`        varchar(512)        default null comment '备注',
    `status`        tinyint(1)          not null default '1' comment '状态，0：禁用，1：启用',
    `deleted`       tinyint(1)          default '0' comment '是否删除，0否，1是',
    primary key (`id`) using btree
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='角色权限关系';
--
-- Dumping index for table `sys_role`
--
create index sys_role_menu_role_id_index on sys_role_menu (role_id);
create index sys_role_menu_permission_id_index on sys_role_menu (permission_id);
create index sys_role_menu_status_index on sys_role_menu (status);
create index sys_role_menu_deleted_index on sys_role_menu (deleted);
--
-- Dumping data for table `sys_role_menu`
--
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('dfeabc184b8d11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'd48019c64b9011eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('d23345c44b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '3145915e4b9111eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('d7ad583f4b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'dbd16a714b9111eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('dbfa8bdc4b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'e47b1a384b9111eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('e14105fd4b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'ee27cf3c4b9111eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('e580cda64b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'f5cfccbf4b9111eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('e93109da4b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '02b965f64b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('ed0658014b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '0949ec224b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('f0da49fb4b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '1ef635574b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('f42d68274b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '26b4c02b4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('f94e5c4b4b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '598f87f64b9111eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('fd7043c04b8e11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '8a6da18a4b9111eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('01c0f89c4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'b2fae1bd4b9111eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('052276c14b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'c51f57d94b9111eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('08a737064b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '3238eff24b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('0ba452294b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '39a400e64b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('0f6465574b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '409427cd4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('126f8ac54b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '4755c9aa4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('1598e6cb4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '4f410ca14b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('1a7f459c4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '55d4960d4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('1e0629554b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '5deb7d764b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('211572514b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '66879d214b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('2435eea34b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '6e03ce8a4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('275c388a4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '74910fc74b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('2a3e0de74b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '7bd216c14b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('2d5bf01f4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '8394c7374b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('32d95d8a4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '8b4e4fd94b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('36b847cc4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '924e7e284b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('45f006564b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '9acce4f44b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('69534f224b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'a5b3b42b4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('6fecc5224b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'ad51cd784b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('5329bca54b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'b56008294b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('76da2e7d4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'bc2aeabe4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('79f703b84b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'c27bc9984b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('7cfd952f4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'c8f1e52c4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('a3e2e5dd4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'd00a77cf4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('a7bd708a4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'd73b03fb4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('aab280b94b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'ddc5b7984b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('bf0e92fe4b8f11eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'e794f0ec4b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('f4f4e62c4b9211eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', 'fad4e2304b9211eea1093a31d6d59109', 1, 'init', 0);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `permission_id`, `status`, `remark`, `version`)
VALUES ('f84b1bcc4b9211eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109', '03a85ada4b9311eea1093a31d6d59109', 1, 'init', 0);


--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`            varchar(32)         not null comment '主键id',
    `dept_id`       varchar(32)         default null,
    `username`      varchar(32)         not null comment '用户名',
    `realname`      varchar(32)         default null comment '真名',
    `nickname`      varchar(32)         default null comment '昵称',
    `email`         varchar(45)         default null,
    `phone`         varchar(20)         default null comment '手机号码',
    `gender`        tinyint             default '0' comment '性别{0=保密, 1=男, 2=女}',
    `avatar`        varchar(255)        default null comment '头像',
    `password`      varchar(64)         not null,
    `login_ip`      varchar(128)        default null comment '最后登录ip',
    `login_date`    datetime            default null comment '最后登录时间',
    `version`       int                 not null default '0' comment '版本',
    `create_time`   datetime            default current_timestamp comment '创建时间',
    `creator_id`    varchar(32)         default null comment '创建人id',
    `creator_name`  varchar(50)         default null comment '创建人姓名',
    `update_time`   datetime            default current_timestamp comment '修改时间',
    `update_id`     varchar(32)         default null comment '更新人id',
    `update_name`   varchar(50)         default null comment '更新人姓名',
    `remark`        varchar(512)        default null comment '备注',
    `status`        tinyint(1)          not null default '1' comment '状态，0：禁用，1：启用',
    `deleted`       tinyint(1)          default '0' comment '是否删除，0否，1是',
    primary key (`id`) using btree
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='系统用户类';
--
-- Dumping index for table `sys_user`
--
create index sys_user_dept_id_index on sys_user (dept_id);
create index sys_user_username_index on sys_user (username);
create index sys_user_status_index on sys_user (status);
create index sys_user_deleted_index on sys_user (deleted);
--
-- Dumping data for table `sys_user`
--
INSERT INTO `sys_user` (`id`, `dept_id`, `username`, `realname`, `nickname`, `email`, `phone`, `gender`, `avatar`, `password`, `status`, `deleted`, `login_ip`, `login_date`, `remark`, `creator_name`, `update_name`)
VALUES ('34c6f9034b9011eea1093a31d6d59109', NULL, 'bg', NULL, NULL, '329426446@qq.com', NULL, 0, '', '$2a$10$QclgMfXBXPoXX6BEJxNyz.aARYqwyOwCnikxWiRK0v4c8zR9/kuQu', 1, 0, '127.0.0.1', '2023-06-26 15:13:43', 'init', 'init', 'init');


--
-- Table structure for table `sys_user_role`
--
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`            varchar(32)         not null comment '主键',
    `user_id`       varchar(32)         not null comment '用户编号',
    `role_id`       varchar(32)         not null comment '角色编号',
    `version`       int                 not null default '0' comment '版本',
    `create_time`   datetime            default current_timestamp comment '创建时间',
    `creator_id`    varchar(32)         default null comment '创建人id',
    `creator_name`  varchar(50)         default null comment '创建人姓名',
    `update_time`   datetime            default current_timestamp comment '修改时间',
    `update_id`     varchar(32)         default null comment '更新人id',
    `update_name`   varchar(50)         default null comment '更新人姓名',
    `remark`        varchar(512)        default null comment '备注',
    `status`        tinyint(1)          not null default '1' comment '状态，0：禁用，1：启用',
    `deleted`       tinyint(1)          default '0' comment '是否删除，0否，1是',
    primary key (`id`) using btree
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='用户角色表';
--
-- Dumping index for table `sys_user_role`
--
create index sys_user_role_user_id_index on sys_user_role (user_id);
create index sys_user_role_role_id_index on sys_user_role (role_id);
create index sys_user_role_status_index on sys_user_role (status);
create index sys_user_role_deleted_index on sys_user_role (deleted);
--
-- Dumping data for table `sys_user_role`
--
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`)
VALUES ('78e5f8154b9011eea1093a31d6d59109', '34c6f9034b9011eea1093a31d6d59109', 'db3b2e464b8f11eea1093a31d6d59109');
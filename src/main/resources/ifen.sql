-- 文章
CREATE TABLE `ifen_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL COMMENT '文章标题',
  `image` varchar(1024) NOT NULL COMMENT '图片url',
  `summary` varchar(1024) NOT NULL COMMENT '摘要',
  `content` text NOT NULL COMMENT '文章正文',
  `author` varchar(64) NOT NULL COMMENT '作者',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者id使用的模板id',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新日期',
  `version` tinyint(4) NOT NULL DEFAULT '0' COMMENT '版本号',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位：0-未删除；1-已删除。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 评论
CREATE TABLE `ifen_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(1024) NOT NULL COMMENT '评论内容',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `creator_id` bigint(20) NOT NULL COMMENT '创建人id',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新日期',
  `version` tinyint(4) NOT NULL DEFAULT '0' COMMENT '版本号',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位：0-未删除；1-已删除。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 点赞
CREATE TABLE `ifen_support` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `object_id` bigint(20) NOT NULL COMMENT '点赞对象id',
  `creator_id` bigint(20) NOT NULL COMMENT '点赞人id',
  `type` tinyint(4) NOT NULL COMMENT '点赞类型：0 - 文章；1 - 评论。',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新日期',
  `version` tinyint(4) NOT NULL DEFAULT '0' COMMENT '版本号',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位：0-未删除；1-已删除。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 图片
CREATE TABLE `ifen_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(10) NOT NULL COMMENT '图片描述',
  `url` varchar(1024) NOT NULL COMMENT '图片url',
  `creator_id` bigint(20) NOT NULL COMMENT '点赞人id',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新日期',
  `version` tinyint(4) NOT NULL DEFAULT '0' COMMENT '版本号',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位：0-未删除；1-已删除。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户
CREATE TABLE `ifen_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source` tinyint(4) NOT NULL COMMENT '用户来源：0 - qq；',
  `openid` varchar(64) NOT NULL COMMENT '应用openid',
  `nickname` varchar(64) NOT NULL COMMENT '昵称',
  `gender` varchar(16) COMMENT '性别：男 or 女',
  `province` varchar(16) COMMENT '所在省',
  `city` varchar(16) COMMENT '所在城市',
  `year` tinyint(4) COMMENT '出生年',
  `figureurl` varchar(256) COMMENT '头像url',
  `figureurl_qq` varchar(256) COMMENT 'qq头像url',
  `vip` tinyint(1) COMMENT '是否黄钻',
  `level` tinyint(1) COMMENT '黄钻等级',
  `is_yellow_year_vip` tinyint(1) COMMENT '是否包年黄钻',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新日期',
  `version` tinyint(4) NOT NULL DEFAULT '0' COMMENT '版本号',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位：0-未删除；1-已删除。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


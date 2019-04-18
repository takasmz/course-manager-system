/*
 Navicat MySQL Data Transfer

 Source Server         : taka
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : course_manager_system

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 19/04/2019 07:40:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course_exam_info
-- ----------------------------
DROP TABLE IF EXISTS `course_exam_info`;
CREATE TABLE `course_exam_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `teacher_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师id',
  `course_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程id',
  `exam_number` int(2) NULL DEFAULT NULL COMMENT '课程作业次数',
  `show_answer_time` datetime(0) NULL DEFAULT NULL COMMENT '显示答案时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态（0:可提交，1:已完成，2:取消，3:创建中）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_exam_info
-- ----------------------------
INSERT INTO `course_exam_info` VALUES (1, '1', '1', 1, NULL, 0, '2018-12-17 15:20:40', '2018-12-29 00:00:00');
INSERT INTO `course_exam_info` VALUES (2, '2', '2', 1, NULL, 0, '2018-12-20 10:55:40', '2018-12-20 10:55:40');
INSERT INTO `course_exam_info` VALUES (3, '3', '3', 1, NULL, 0, '2018-12-20 10:55:40', '2018-12-20 10:55:40');
INSERT INTO `course_exam_info` VALUES (4, '4', '4', 1, NULL, 0, '2018-12-20 10:55:40', '2018-12-20 10:55:40');
INSERT INTO `course_exam_info` VALUES (5, '1', '1', 2, NULL, 0, '2018-12-17 15:20:40', '2018-12-29 00:00:00');
INSERT INTO `course_exam_info` VALUES (112, 'liruhao1', '1', 3, NULL, 0, '2018-12-29 14:14:36', '2019-01-01 00:00:00');
INSERT INTO `course_exam_info` VALUES (113, 'liruhao1', '1', 4, '2019-01-20 00:00:00', 0, '2018-12-29 17:17:21', '2019-01-31 00:00:00');
INSERT INTO `course_exam_info` VALUES (114, 'liruhao1', '1', 5, '2019-01-20 00:00:00', 0, '2019-01-18 11:55:59', '2019-01-31 00:00:00');
INSERT INTO `course_exam_info` VALUES (115, 'liruhao1', '1', 6, '2019-01-18 00:00:00', 0, '2019-01-18 12:07:00', '2019-01-21 00:00:00');
INSERT INTO `course_exam_info` VALUES (116, 'liruhao1', '1', 7, '2019-01-18 00:00:00', 0, '2019-01-18 12:07:33', '2019-01-21 00:00:00');
INSERT INTO `course_exam_info` VALUES (117, 'liruhao1', '1', NULL, NULL, 3, '2019-01-18 15:05:40', NULL);

-- ----------------------------
-- Table structure for course_file
-- ----------------------------
DROP TABLE IF EXISTS `course_file`;
CREATE TABLE `course_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程附件id',
  `course_id` int(11) NULL DEFAULT NULL COMMENT '课程id',
  `course_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程路径',
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件描述',
  `file_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_type` int(1) NULL DEFAULT NULL COMMENT '文件类型(1:课件;2:音频3:习题)',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态(1:正常;2:已删除)',
  `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_file
-- ----------------------------
INSERT INTO `course_file` VALUES (1, 1, 'matrixcomputations', 'Chapter_01', 'Chapter_01.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (2, 1, 'matrixcomputations', 'Chapter_02', 'Chapter_02.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (3, 1, 'matrixcomputations', 'Chapter_03', 'Chapter_03.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (4, 1, 'matrixcomputations', 'Chapter_04', 'Chapter_04.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (5, 1, 'matrixcomputations', 'Chapter_05', 'Chapter_05.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (6, 1, 'matrixcomputations', 'Chapter_06', 'Chapter_06.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (7, 1, 'matrixcomputations', 'Chapter_07', 'Chapter_07.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (8, 1, 'matrixcomputations', 'Chapter_08', 'Chapter_08.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (9, 1, 'matrixcomputations', 'Chapter_09', 'Chapter_09.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (10, 1, 'matrixcomputations', 'Chapter_10', 'Chapter_10.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (11, 1, 'matrixcomputations', 'Chapter_11', 'Chapter_11.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (12, 1, 'matrixcomputations', 'Chapter_12', 'Chapter_12.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (13, 1, 'matrixcomputations', 'Chapter_13', 'Chapter_13.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (14, 1, 'matrixcomputations', 'Chapter_14', 'Chapter_14.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (15, 1, 'matrixcomputations', 'Chapter_15', 'Chapter_15.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (16, 1, 'matrixcomputations', 'Chapter_16', 'Chapter_16.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (17, 1, 'matrixcomputations', 'Chapter_17', 'Chapter_17.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (18, 1, 'matrixcomputations', 'Chapter_18', 'Chapter_18.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (19, 1, 'matrixcomputations', 'Chapter_19', 'Chapter_19.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (20, 1, 'matrixcomputations', 'Chapter_20', 'Chapter_20.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (21, 1, 'matrixcomputations', 'Chapter_21', 'Chapter_21.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (22, 1, 'matrixcomputations', 'Chapter_22', 'Chapter_22.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (23, 1, 'matrixcomputations', 'Chapter_23', 'Chapter_23.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (24, 1, 'matrixcomputations', 'AppendixA', 'AppendixA.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (25, 1, 'matrixcomputations', 'AppendixB', 'AppendixB.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (26, 1, 'matrixcomputations', 'AppendixC', 'AppendixC.pptx', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (27, 2, 'dataanalysis', 'Chapter_01', '1.doc', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (28, 2, 'dataanalysis', 'Chapter_02', '2.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (29, 2, 'dataanalysis', 'Chapter_03', '3.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (30, 2, 'dataanalysis', 'Chapter_04', '4.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (31, 2, 'dataanalysis', 'Chapter_05', '5.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (32, 2, 'dataanalysis', 'Chapter_06', '6.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (33, 2, 'dataanalysis', 'Chapter_07', '7.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (34, 2, 'dataanalysis', 'Chapter_08', '8.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (35, 2, 'dataanalysis', 'Chapter_09', '9.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (36, 2, 'dataanalysis', 'Chapter_10', '10.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (37, 3, 'linearalgebra', 'Matrices and Systems of Equations', 'LinearA1.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (38, 3, 'linearalgebra', 'Determinants', 'LinearA2.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (39, 3, 'linearalgebra', 'Vector Spaces', 'LinearA3.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (40, 3, 'linearalgebra', 'Orthogonality', 'ppt.5.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (41, 3, 'linearalgebra', 'Linear Transformations', 'ppt.4.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (42, 4, 'numericalpde', 'Overview of PDEs', '1.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (43, 4, 'numericalpde', 'Explicit methods for 1-D heat', '2.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (44, 4, 'numericalpde', 'Implicit methods for 1-D heat', '3.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (45, 4, 'numericalpde', 'Jacobi method', '4.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (46, 4, 'numericalpde', '2-D Finite Difference', '5.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (47, 4, 'numericalpde', 'Analytical solutions to 1-DWave equation', '6.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (48, 4, 'numericalpde', 'Flux conservative problems', '7.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (49, 4, 'numericalpde', 'Numerical Solution of 1-D and 2-D Wave Equation', '8.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (50, 4, 'numericalpde', 'Finite element method', '9.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (51, 4, 'numericalpde', 'Example', '10.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (52, 4, 'numericalpde', 'Example', '11.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (53, 4, 'numericalpde', 'Example', '12.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (54, 5, 'probabilitytheory', 'Chapter 02', 'ch2_12.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (55, 5, 'probabilitytheory', 'Chapter 02', 'ch2_34.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (56, 5, 'probabilitytheory', 'Chapter 02', 'ch2_5678.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (57, 5, 'probabilitytheory', 'Chapter 03', 'ch3_123.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (58, 5, 'probabilitytheory', 'Chapter 03', 'ch3_4.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (59, 5, 'probabilitytheory', 'Chapter 04', 'ch4_1.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (60, 5, 'probabilitytheory', 'Chapter 04', 'ch4_23.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (61, 5, 'probabilitytheory', 'Chapter 04', 'ch4_4.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (62, 5, 'probabilitytheory', 'Chapter 05', 'ch5.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (63, 5, 'probabilitytheory', 'Chapter 06', 'ch6_12.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (64, 5, 'probabilitytheory', 'Chapter 06', 'ch6_3.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (65, 5, 'probabilitytheory', 'Chapter 06', 'ch6_4.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (66, 5, 'probabilitytheory', 'Chapter 06', 'ch6_567.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');
INSERT INTO `course_file` VALUES (67, 5, 'probabilitytheory', 'Chapter 07', 'ch7.pdf', 1, 1, 'admin', '2019-01-15 11:48:02');

-- ----------------------------
-- Table structure for course_info
-- ----------------------------
DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `course_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程资源路径',
  `teacher_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师账号',
  `year` int(4) NULL DEFAULT NULL COMMENT '学年',
  `term` int(1) NULL DEFAULT NULL COMMENT '学期',
  `status` int(255) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_info
-- ----------------------------
INSERT INTO `course_info` VALUES (1, 'Matrix Computations', 'matrixcomputations', 'liruhao1', 2018, 2, 1);
INSERT INTO `course_info` VALUES (2, 'DATA Analysis', 'dataanalysis', 'liruhao1', 2018, 2, 1);
INSERT INTO `course_info` VALUES (3, 'Linear algebra', 'linearalgebra', 'liruhao1', 2018, 2, 1);
INSERT INTO `course_info` VALUES (4, 'Numerical PDE', 'numericalpde', 'liruhao1', 2018, 2, 1);
INSERT INTO `course_info` VALUES (5, 'Probability Theory', 'probabilitytheory', 'liruhao1', 2018, 2, 1);
INSERT INTO `course_info` VALUES (6, 'test', NULL, 'liruhao1', 2018, 2, 1);

-- ----------------------------
-- Table structure for course_resource
-- ----------------------------
DROP TABLE IF EXISTS `course_resource`;
CREATE TABLE `course_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_caption` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ppt_filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `video_filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `xh` int(11) NULL DEFAULT NULL,
  `exericesFilename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exerices_filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_resource
-- ----------------------------
INSERT INTO `course_resource` VALUES (1, 'Matrix Computations', 'Chapter_01', '', 'Chapter_01.pptx', '第1章', '', 1, NULL, NULL);
INSERT INTO `course_resource` VALUES (2, 'Matrix Computations', 'Chapter_02', '', 'Chapter_02.pptx', '第2章', '', 2, NULL, NULL);
INSERT INTO `course_resource` VALUES (3, 'Matrix Computations', 'Chapter_03', '', 'Chapter_03.pptx', '第3章', '', 3, NULL, NULL);
INSERT INTO `course_resource` VALUES (4, 'Matrix Computations', 'Chapter_04', '', 'Chapter_04.pptx', '第4章', '', 4, NULL, NULL);
INSERT INTO `course_resource` VALUES (5, 'Matrix Computations', 'Chapter_05', '', 'Chapter_05.pptx', '第5章', '', 5, NULL, NULL);
INSERT INTO `course_resource` VALUES (6, 'Matrix Computations', 'Chapter_06', '', 'Chapter_06.pptx', '第6章', '', 6, NULL, NULL);
INSERT INTO `course_resource` VALUES (7, 'Matrix Computations', 'Chapter_07', '', 'Chapter_07.pptx', '第7章', '', 7, NULL, NULL);
INSERT INTO `course_resource` VALUES (8, 'Matrix Computations', 'Chapter_08', '', 'Chapter_08.pptx', '第8章', '', 8, NULL, NULL);
INSERT INTO `course_resource` VALUES (9, 'Matrix Computations', 'Chapter_09', '', 'Chapter_09.pptx', '第9章', '', 9, NULL, NULL);
INSERT INTO `course_resource` VALUES (10, 'Matrix Computations', 'Chapter_10', '', 'Chapter_10.pptx', '第10章', '', 10, NULL, NULL);
INSERT INTO `course_resource` VALUES (11, 'Matrix Computations', 'Chapter_11', '', 'Chapter_11.pptx', '第11章', '', 11, NULL, NULL);
INSERT INTO `course_resource` VALUES (12, 'Matrix Computations', 'Chapter_12', '', 'Chapter_12.pptx', '第12章', '', 12, NULL, NULL);
INSERT INTO `course_resource` VALUES (13, 'Matrix Computations', 'Chapter_13', '', 'Chapter_13.pptx', '第13章', '', 13, NULL, NULL);
INSERT INTO `course_resource` VALUES (14, 'Matrix Computations', 'Chapter_14', '', 'Chapter_14.pptx', '第14章', '', 14, NULL, NULL);
INSERT INTO `course_resource` VALUES (15, 'Matrix Computations', 'Chapter_15', '', 'Chapter_15.pptx', '第15章', '', 15, NULL, NULL);
INSERT INTO `course_resource` VALUES (16, 'Matrix Computations', 'Chapter_16', '', 'Chapter_16.pptx', '第16章', '', 16, NULL, NULL);
INSERT INTO `course_resource` VALUES (17, 'Matrix Computations', 'Chapter_17', '', 'Chapter_17.pptx', '第17章', '', 17, NULL, NULL);
INSERT INTO `course_resource` VALUES (18, 'Matrix Computations', 'Chapter_18', '', 'Chapter_18.pptx', '第18章', '', 18, NULL, NULL);
INSERT INTO `course_resource` VALUES (19, 'Matrix Computations', 'Chapter_19', '', 'Chapter_19.pptx', '第19章', '', 19, NULL, NULL);
INSERT INTO `course_resource` VALUES (20, 'Matrix Computations', 'Chapter_20', '', 'Chapter_20.pptx', '第20章', '', 20, NULL, NULL);
INSERT INTO `course_resource` VALUES (21, 'Matrix Computations', 'Chapter_21', '', 'Chapter_21.pptx', '第21章', '', 21, NULL, NULL);
INSERT INTO `course_resource` VALUES (22, 'Matrix Computations', 'Chapter_22', '', 'Chapter_22.pptx', '第22章', '', 22, NULL, NULL);
INSERT INTO `course_resource` VALUES (23, 'Matrix Computations', 'Chapter_23', '', 'Chapter_23.pptx', '第23章', '', 23, NULL, NULL);
INSERT INTO `course_resource` VALUES (24, 'Matrix Computations', 'AppendixA', '', 'AppendixA.pptx', '附录A', '', 24, NULL, NULL);
INSERT INTO `course_resource` VALUES (25, 'Matrix Computations', 'AppendixB', '', 'AppendixB.pptx', '附录B', '', 25, NULL, NULL);
INSERT INTO `course_resource` VALUES (26, 'Matrix Computations', 'AppendixC', '', 'AppendixC.pptx', '附录C', '', 26, NULL, NULL);
INSERT INTO `course_resource` VALUES (27, 'DATA Analysis', 'Chapter_01', '', '1.doc', '第1章', '', 1, NULL, NULL);
INSERT INTO `course_resource` VALUES (28, 'DATA Analysis', 'Chapter_02', '', '2.pdf', '第2章', '', 2, NULL, NULL);
INSERT INTO `course_resource` VALUES (29, 'DATA Analysis', 'Chapter_03', '', '3.pdf', '第3章', '', 3, NULL, NULL);
INSERT INTO `course_resource` VALUES (30, 'DATA Analysis', 'Chapter_04', '', '4.pdf', '第4章', '', 4, NULL, NULL);
INSERT INTO `course_resource` VALUES (31, 'DATA Analysis', 'Chapter_05', '', '5.pdf', '第5章', '', 5, NULL, NULL);
INSERT INTO `course_resource` VALUES (32, 'DATA Analysis', 'Chapter_06', '', '6.pdf', '第6章', '', 6, NULL, NULL);
INSERT INTO `course_resource` VALUES (33, 'DATA Analysis', 'Chapter_07', '', '7.pdf', '第7章', '', 7, NULL, NULL);
INSERT INTO `course_resource` VALUES (34, 'DATA Analysis', 'Chapter_08', '', '8.pdf', '第8章', '', 8, NULL, NULL);
INSERT INTO `course_resource` VALUES (35, 'DATA Analysis', 'Chapter_09', '', '9.pdf', '第9章', '', 9, NULL, NULL);
INSERT INTO `course_resource` VALUES (36, 'DATA Analysis', 'Chapter_10', '', '10.pdf', '第10章', '', 10, NULL, NULL);
INSERT INTO `course_resource` VALUES (37, 'Linear algebra', 'Matrices and Systems of Equations', '', 'LinearA1.pdf', '第1章', '', 1, NULL, NULL);
INSERT INTO `course_resource` VALUES (38, 'Linear algebra', 'Determinants', '', 'LinearA2.pdf', '第2章', '', 2, NULL, NULL);
INSERT INTO `course_resource` VALUES (39, 'Linear algebra', 'Vector Spaces', '', 'LinearA3.pdf', '第3章', '', 3, NULL, NULL);
INSERT INTO `course_resource` VALUES (41, 'Numerical PDE', 'Overview of PDEs', '', '1.pdf', '第1章', '', 1, NULL, NULL);
INSERT INTO `course_resource` VALUES (42, 'Numerical PDE', 'Explicit methods for 1-D heat', '', '2.pdf', '第2章', '', 2, NULL, NULL);
INSERT INTO `course_resource` VALUES (43, 'Numerical PDE', 'Implicit methods for 1-D heat', '', '3.pdf', '第3章', '', 3, NULL, NULL);
INSERT INTO `course_resource` VALUES (44, 'Numerical PDE', 'Jacobi method', '', '4.pdf', '第4章', '', 4, NULL, NULL);
INSERT INTO `course_resource` VALUES (45, 'Numerical PDE', '2-D Finite Difference', '', '5.pdf', '第5章', '', 5, NULL, NULL);
INSERT INTO `course_resource` VALUES (46, 'Numerical PDE', 'Analytical solutions to 1-DWave equation', '', '6.pdf', '第6章', '', 6, NULL, NULL);
INSERT INTO `course_resource` VALUES (47, 'Numerical PDE', 'Flux conservative problems', '', '7.pdf', '第7章', '', 7, NULL, NULL);
INSERT INTO `course_resource` VALUES (48, 'Numerical PDE', 'Numerical Solution of 1-D and 2-D Wave Equation', '', '8.pdf', '第8章', '', 8, NULL, NULL);
INSERT INTO `course_resource` VALUES (49, 'Numerical PDE', 'Finite element method', '', '9.pdf', '第9章', '', 9, NULL, NULL);
INSERT INTO `course_resource` VALUES (50, 'Numerical PDE', 'Example', '', '10.pdf', '第10章', '', 10, NULL, NULL);
INSERT INTO `course_resource` VALUES (51, 'Numerical PDE', 'Example', '', '11.pdf', '第11章', '', 11, NULL, NULL);
INSERT INTO `course_resource` VALUES (52, 'Numerical PDE', 'Example', '', '12.pdf', '第12章', '', 12, NULL, NULL);
INSERT INTO `course_resource` VALUES (53, 'Probability Theory', 'Chapter 02', '', 'ch2_12.pdf', '第2章(1-2)', '', 1, NULL, NULL);
INSERT INTO `course_resource` VALUES (54, 'Probability Theory', 'Chapter 02', '', 'ch2_34.pdf', '第2章(3-4)', '', 2, NULL, NULL);
INSERT INTO `course_resource` VALUES (55, 'Probability Theory', 'Chapter 02', '', 'ch2_5678.pdf', '第2章(5-8)', '', 3, NULL, NULL);
INSERT INTO `course_resource` VALUES (56, 'Probability Theory', 'Chapter 03', '', 'ch3_123.pdf', '第3章(1-3)', '', 4, NULL, NULL);
INSERT INTO `course_resource` VALUES (57, 'Probability Theory', 'Chapter 03', '', 'ch3_4.pdf', '第3章(4)', '', 5, NULL, NULL);
INSERT INTO `course_resource` VALUES (58, 'Probability Theory', 'Chapter 04', '', 'ch4_1.pdf', '第4章(1)', '', 6, NULL, NULL);
INSERT INTO `course_resource` VALUES (59, 'Probability Theory', 'Chapter 04', '', 'ch4_23.pdf', '第4章(2-3)', '', 7, NULL, NULL);
INSERT INTO `course_resource` VALUES (60, 'Probability Theory', 'Chapter 04', '', 'ch4_4.pdf', '第4章(4)', '', 8, NULL, NULL);
INSERT INTO `course_resource` VALUES (61, 'Probability Theory', 'Chapter 05', '', 'ch5.pdf', '第5章', '', 9, NULL, NULL);
INSERT INTO `course_resource` VALUES (62, 'Probability Theory', 'Chapter 06', '', 'ch6_12.pdf', '第6章(1-2)', '', 10, NULL, NULL);
INSERT INTO `course_resource` VALUES (63, 'Probability Theory', 'Chapter 06', '', 'ch6_3.pdf', '第6章(3)', '', 11, NULL, NULL);
INSERT INTO `course_resource` VALUES (64, 'Probability Theory', 'Chapter 06', '', 'ch6_4.pdf', '第6章(4)', '', 12, NULL, NULL);
INSERT INTO `course_resource` VALUES (65, 'Probability Theory', 'Chapter 06', '', 'ch6_567.pdf', '第6章(567)', '', 13, NULL, NULL);
INSERT INTO `course_resource` VALUES (66, 'Probability Theory', 'Chapter 07', '', 'ch7.pdf', '第7章', '', 14, NULL, NULL);
INSERT INTO `course_resource` VALUES (70, 'Linear algebra', 'Orthogonality', NULL, 'ppt.5.pdf', 'Chapter 5', NULL, 5, NULL, NULL);
INSERT INTO `course_resource` VALUES (71, 'Linear algebra', 'Linear Transformations', NULL, 'ppt.4.pdf', 'Chapter 4', NULL, 4, NULL, NULL);

-- ----------------------------
-- Table structure for exam_info
-- ----------------------------
DROP TABLE IF EXISTS `exam_info`;
CREATE TABLE `exam_info`  (
  `exam_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '问题id',
  `exam_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问题标题',
  `exam_content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问题正文',
  `submit_type` int(2) NULL DEFAULT NULL COMMENT '提交方式（0程序，1文档）',
  `number` int(11) NULL DEFAULT NULL COMMENT '分值（缺省均分）',
  `identify_type` int(2) NULL DEFAULT NULL COMMENT '验证方式（0人工，1程序）',
  `return_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '返回值类型',
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '答案',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态（1:正常，2:无效，3:创建中）',
  `input_parameter_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '输入参数类型',
  `input_parameter_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '输入参数名称',
  `inputs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '输入',
  `course_exam_id` int(11) NULL DEFAULT NULL COMMENT '所属课程作业ID',
  PRIMARY KEY (`exam_id`) USING BTREE,
  INDEX `course_exam_id`(`course_exam_id`) USING BTREE,
  CONSTRAINT `course_exam_id` FOREIGN KEY (`course_exam_id`) REFERENCES `course_exam_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_info
-- ----------------------------
INSERT INTO `exam_info` VALUES ('11032419', 'ttt,ttt', '<p>fasfasfasfasf</p>', 0, 0, 0, NULL, '', 1, NULL, NULL, '', 113);
INSERT INTO `exam_info` VALUES ('1110', '测试标题1', '测试内容2', 1, 33, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 3);
INSERT INTO `exam_info` VALUES ('1111', '测试标题2', '测试内容2', 1, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 1);
INSERT INTO `exam_info` VALUES ('111123', '测试标题', '测试内容', 0, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 1);
INSERT INTO `exam_info` VALUES ('1112', '测试标题3', '测试内容2', 0, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 1);
INSERT INTO `exam_info` VALUES ('1113', '测试标题4', '测试内容2', 0, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 1);
INSERT INTO `exam_info` VALUES ('1114', '测试标题5', '测试内容2', 0, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 1);
INSERT INTO `exam_info` VALUES ('1115', '测试标题1', '测试内容2', 0, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 2);
INSERT INTO `exam_info` VALUES ('1116', '测试标题2', '测试内容2', 0, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 2);
INSERT INTO `exam_info` VALUES ('1117', '测试标题3', '测试内容2', 0, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 2);
INSERT INTO `exam_info` VALUES ('1118', '测试标题4', '测试内容2', 0, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 2);
INSERT INTO `exam_info` VALUES ('1119', '测试标题5', '测试内容2', 1, 20, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 2);
INSERT INTO `exam_info` VALUES ('1121', '测试标题2', '测试内容2', 1, 33, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 3);
INSERT INTO `exam_info` VALUES ('1122', '测试标题3', '测试内容2', 1, 33, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 3);
INSERT INTO `exam_info` VALUES ('1123', '测试标题1', '测试内容2', 1, 50, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 4);
INSERT INTO `exam_info` VALUES ('1124', '测试标题2', '测试内容2', 1, 50, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 4);
INSERT INTO `exam_info` VALUES ('14463019', '啦啦啦', '<p style=\"\">法大师傅士大夫<a href=\"\">链接文字</a></p><p style=\"\"><img alt=\"test_1.jpg\" src=\"/showImg/teacher/homework/113/111.jpg\" width=\"1000\" height=\"258.7101556708673\"><br></p>', 0, 1, 0, 'java.lang.String', 'sad!', 1, 'java.lang.String[]', 'strs', 'sad', 113);
INSERT INTO `exam_info` VALUES ('20818618', '测试3', '<p style=\"\">法大师傅士大夫<a href=\"\">链接文字</a></p><p style=\"\"><img height=\"349\" width=\"949\" src=\"/showImg/113/111.jpg\" alt=\"test_1.jpg\"><br></p>', 0, 44, 0, NULL, NULL, 3, NULL, NULL, NULL, 112);
INSERT INTO `exam_info` VALUES ('26485918', '测试1', '<p style=\"\"><span style=\"\">测试1</span><span style=\"\">测试1</span><span style=\"\">测试1</span><img alt=\"loading.gif\" src=\"/showImg/112/loading.gif\" width=\"32\" height=\"32\"><br></p>', 0, 1, 0, 'java.lang.String', '', 1, 'java.lang.String[]', 'strs', '', 112);
INSERT INTO `exam_info` VALUES ('30971719', '简单乘法', '<p style=\"\">输入：9 8</p><p style=\"\">输出：72</p><p style=\"\">输入：22 11</p><p style=\"\">输出：242</p>', 0, 100, 1, NULL, NULL, 1, NULL, NULL, NULL, 117);
INSERT INTO `exam_info` VALUES ('40944118', '测试2', '<p style=\"\">威廉克拉克</p><p style=\"\"><img alt=\"close.png\" src=\"/showImg/112/close.png\" width=\"17\" height=\"60\"><br></p><p style=\"\"><a href=\"http://www.baidu.com\" target=\"_blank\">百度</a><br></p>', 0, 1, 0, 'java.lang.String', '100|150|200', 1, 'java.lang.Integer[]', 'nums', '50,50|75,75|120,80', 112);
INSERT INTO `exam_info` VALUES ('41576819', '简单乘法', '<p>输入：100 200</p><p>输出：20000</p><p>输入：6 7</p><p>输出：42</p>', 0, 100, 1, NULL, NULL, 1, NULL, NULL, NULL, 117);
INSERT INTO `exam_info` VALUES ('55679819', '回文子串', '<p>给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。</p><p>具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。</p><p>示例 1:</p><pre><code><span style=\"box-sizing: border-box; font-weight: bolder;\">输入:</span> \"abc\"\n<span style=\"box-sizing: border-box; font-weight: bolder;\">输出:</span> 3\n<span style=\"box-sizing: border-box; font-weight: bolder;\">解释:</span> 三个回文子串: \"a\", \"b\", \"c\".\n</code></pre><p>示例 2:</p><pre><code><span style=\"box-sizing: border-box; font-weight: bolder;\">输入:</span> \"aaa\"\n<span style=\"box-sizing: border-box; font-weight: bolder;\">输出:</span> 6\n<span style=\"box-sizing: border-box; font-weight: bolder;\">说明:</span> 6个回文子串: \"a\", \"a\", \"a\", \"aa\", \"aa\", \"aaa\".\n</code></pre><p>注意:</p><ol><li>输入的字符串长度不会超过1000。</li></ol>', 0, 30, 1, 'java.lang.Integer', '3|6', 1, 'java.lang.String[]', 'strs', 'abc|aaa', 116);
INSERT INTO `exam_info` VALUES ('76471619', '简单求和', '<p style=\"\"><span style=\"\">简单求和</span><br></p><p style=\"\">1+1=2</p>', 0, 100, 1, NULL, NULL, 1, NULL, NULL, NULL, 114);
INSERT INTO `exam_info` VALUES ('87071119', '到达终点', '<p>从点&nbsp;<code>(x, y)</code>&nbsp;可以转换到&nbsp;<code>(x, x+y)</code>&nbsp; 或者&nbsp;<code>(x+y, y)</code>。</p><p>给定一个起点&nbsp;<code>(sx, sy)</code>&nbsp;和一个终点&nbsp;<code>(tx, ty)</code>，如果通过一系列的转换可以从起点到达终点，则返回&nbsp;<code>True&nbsp;</code>，否则返回&nbsp;<code>False</code>。</p><pre><code><span style=\"box-sizing: border-box; font-weight: bolder;\">示例:</span>\n<span style=\"box-sizing: border-box; font-weight: bolder;\">输入:</span> sx = 1, sy = 1, tx = 3, ty = 5\n<span style=\"box-sizing: border-box; font-weight: bolder;\">输出:</span> True\n<span style=\"box-sizing: border-box; font-weight: bolder;\">解释:\n</span>可以通过以下一系列<span style=\"box-sizing: border-box; font-weight: bolder;\">转换</span>从起点转换到终点：\n(1, 1) -&gt; (1, 2)\n(1, 2) -&gt; (3, 2)\n(3, 2) -&gt; (3, 5)\n\n输入: sx = 1, sy = 1, tx = 2, ty = 2\n输出: False\n\n输入: sx = 1, sy = 1, tx = 1, ty = 1\n输出: True\n</code></pre>', 0, 30, 0, 'lava.lang.Boolean', 'true|false|true', 1, 'java.lang.Integer[]', 'nums', '1,1,3,5|1,1,2,2|1,1,1,1', 116);
INSERT INTO `exam_info` VALUES ('91011319', '平衡二叉树', '<p style=\"\">本题中，一棵高度平衡二叉树定义为：<br></p><blockquote><p>一个二叉树每个节点&nbsp;的左右两个子树的高度差的绝对值不超过1。</p></blockquote><p>示例 1:</p><p>给定二叉树&nbsp;<code>[3,9,20,null,null,15,7]</code></p><pre><code>    3\n   / \\\n  9  20\n    /  \\\n   15   7</code></pre><p>返回&nbsp;<code>true</code>&nbsp;。<br><br>示例 2:</p><p>给定二叉树&nbsp;<code>[1,2,2,3,3,null,null,4,4]</code></p><pre><code>       1\n      / \\\n     2   2\n    / \\\n   3   3\n  / \\\n 4   4\n</code></pre><p>返回&nbsp;<code>false</code>&nbsp;。</p>', 0, 20, 1, 'lava.lang.Boolean', 'true|false', 1, 'TreeNode', 'root', '3,9,20,null,null,15,7|1,2,2,3,3,null,null,4,4', 116);
INSERT INTO `exam_info` VALUES ('93765819', '到最近的人的最大距离', '<p>在一排座位（&nbsp;<code>seats</code>）中，<code>1</code>&nbsp;代表有人坐在座位上，<code>0</code>&nbsp;代表座位上是空的。</p><p>至少有一个空座位，且至少有一人坐在座位上。</p><p>亚历克斯希望坐在一个能够使他与离他最近的人之间的距离达到最大化的座位上。</p><p>返回他到离他最近的人的最大距离。</p><p>示例 1：</p><pre><code><span style=\"box-sizing: border-box; font-weight: bolder;\">输入：</span>[1,0,0,0,1,0,1]\n<span style=\"box-sizing: border-box; font-weight: bolder;\">输出：</span>2\n<span style=\"box-sizing: border-box; font-weight: bolder;\">解释：\n</span>如果亚历克斯坐在第二个空位（seats[2]）上，他到离他最近的人的距离为 2 。\n如果亚历克斯坐在其它任何一个空位上，他到离他最近的人的距离为 1 。\n因此，他到离他最近的人的最大距离是 2 。 \n</code></pre><p>示例 2：</p><pre><code><span style=\"box-sizing: border-box; font-weight: bolder;\">输入：</span>[1,0,0,0]\n<span style=\"box-sizing: border-box; font-weight: bolder;\">输出：</span>3\n<span style=\"box-sizing: border-box; font-weight: bolder;\">解释： </span>\n如果亚历克斯坐在最后一个座位上，他离最近的人有 3 个座位远。\n这是可能的最大距离，所以答案是 <span style=\"box-sizing: border-box;\">3 </span>。\n</code></pre><p>提示：</p><ol><li><code>1 &lt;= seats.length &lt;= 20000</code></li><li><code>seats</code>&nbsp;中只含有 0 和 1，至少有一个&nbsp;<code>0</code>，且至少有一个&nbsp;<code>1</code>。</li></ol>', 0, 20, 1, 'java.lang.String', '2|3', 1, 'java.lang.Integer[]', 'nums', '1,0,0,0,1,0,1|1,0,0,0', 116);

-- ----------------------------
-- Table structure for exam_submit_record
-- ----------------------------
DROP TABLE IF EXISTS `exam_submit_record`;
CREATE TABLE `exam_submit_record`  (
  `exam_record_id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作业id',
  `submit_type` int(1) NULL DEFAULT NULL COMMENT '提交种类(0:代码;1:文件)',
  `submit_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '代码内容 或 文件路径',
  `student_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生标识',
  `submit_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`exam_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_submit_record
-- ----------------------------
INSERT INTO `exam_submit_record` VALUES (1, '1', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-16 21:29:06');
INSERT INTO `exam_submit_record` VALUES (2, '1', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-16 21:37:40');
INSERT INTO `exam_submit_record` VALUES (3, '1', 0, 'status', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-16 21:40:04');
INSERT INTO `exam_submit_record` VALUES (4, '1', 0, 'Compile', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-16 21:41:54');
INSERT INTO `exam_submit_record` VALUES (5, '1', 0, '.ivu-tag-yellow.ivu-tag-dot .ivu-tag-dot-inner', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-16 21:43:40');
INSERT INTO `exam_submit_record` VALUES (6, '1', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-16 21:58:51');
INSERT INTO `exam_submit_record` VALUES (7, '1', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-18 15:19:27');
INSERT INTO `exam_submit_record` VALUES (8, '1', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-18 15:22:45');
INSERT INTO `exam_submit_record` VALUES (9, '1', 0, '', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-18 15:24:25');
INSERT INTO `exam_submit_record` VALUES (10, '1', 0, '', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-18 15:25:28');
INSERT INTO `exam_submit_record` VALUES (11, '1', 1, '/E:/workspace/course-manager-system/target/classes/homework/matrixcomputations20191/2015326601098李如豪/2015326601098李如豪第1次作业', '2015326601098', '2018-12-19 12:07:57');
INSERT INTO `exam_submit_record` VALUES (12, '1', 1, '/E:/workspace/course-manager-system/target/classes/homework/matrixcomputations20191/2015326601098李如豪/2015326601098李如豪第1次作业', '2015326601098', '2018-12-19 14:15:46');
INSERT INTO `exam_submit_record` VALUES (13, '1', 1, '/E:/workspace/course-manager-system/target/classes/homework/matrixcomputations20191/2015326601098李如豪/2015326601098李如豪第1次作业', '2015326601098', '2018-12-19 14:17:10');
INSERT INTO `exam_submit_record` VALUES (14, '1', 1, '/E:/workspace/course-manager-system/target/classes/homework/matrixcomputations20191/2015326601098李如豪/2015326601098李如豪第1次作业', '2015326601098', '2018-12-19 14:17:40');
INSERT INTO `exam_submit_record` VALUES (15, NULL, 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-19 14:29:51');
INSERT INTO `exam_submit_record` VALUES (16, NULL, 0, '', '2fc91f7bcb2f42be8b40e1abb1e951680', '2018-12-19 15:12:36');
INSERT INTO `exam_submit_record` VALUES (17, '1111', 1, '/E:/workspace/course-manager-system/target/classes/homework/matrixcomputations20191/2015326601098李如豪/2015326601098李如豪第1次作业', '2015326601098', '2018-12-20 15:49:40');
INSERT INTO `exam_submit_record` VALUES (18, '1111', 1, '/E:/workspace/course-manager-system/target/classes/homework/matrixcomputations20191/2015326601098李如豪/2015326601098李如豪第1次作业', '2015326601098', '2018-12-20 15:50:42');
INSERT INTO `exam_submit_record` VALUES (19, '1111', 1, '/E:/workspace/course-manager-system/target/classes/homework/matrixcomputations20191/2015326601098李如豪/2015326601098李如豪第1次作业', '2015326601098', '2018-12-20 15:52:37');

-- ----------------------------
-- Table structure for exam_test_case
-- ----------------------------
DROP TABLE IF EXISTS `exam_test_case`;
CREATE TABLE `exam_test_case`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` int(11) NULL DEFAULT NULL COMMENT '题目id',
  `input` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '一次输入',
  `output` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '一次输出',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_test_case
-- ----------------------------
INSERT INTO `exam_test_case` VALUES (1, -1, '100,200,300', '600');
INSERT INTO `exam_test_case` VALUES (2, 76471619, '100,200,300', '600');

-- ----------------------------
-- Table structure for homework
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commit_time` datetime(0) NULL DEFAULT NULL,
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `xh` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 978 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES (11, '2018-10-15 23:35:08', 'Matrix Computations', 'MatrixComputations.2014326601031.1.docx', '邓琳', '2014326601031', 1);
INSERT INTO `homework` VALUES (14, '2018-10-15 23:32:25', 'Matrix Computations', 'MatrixComputations.2014326601035.4.pdf', '陆娇娇', '2014326601035', 4);
INSERT INTO `homework` VALUES (16, '2018-11-21 11:47:51', 'Numerical PDE', '2012326601058_1.pdf', '陈萌哲', '2012326601058', 1);
INSERT INTO `homework` VALUES (17, '2018-11-21 11:47:51', 'Numerical PDE', '2012326601065_1.pdf', '吴艳霞', '2012326601065', 1);
INSERT INTO `homework` VALUES (18, '2018-11-21 11:47:51', 'Numerical PDE', '2012326601085_1.doc', '韩美佳', '2012326601085', 1);
INSERT INTO `homework` VALUES (19, '2018-11-21 11:47:51', 'Numerical PDE', '2012326601100_1.docx', '李泽浩', '2012326601100', 1);
INSERT INTO `homework` VALUES (20, '2018-11-21 11:47:51', 'Numerical PDE', '2013326601062_1.docx', '凌海雅', '2013326601062', 1);
INSERT INTO `homework` VALUES (21, '2018-11-21 11:47:51', 'Numerical PDE', '2013326601088_1.docx', '李男', '2013326601088', 1);
INSERT INTO `homework` VALUES (22, '2018-11-21 11:47:51', 'Numerical PDE', '2013326601103_1.doc', '刘君健', '2013326601103', 1);
INSERT INTO `homework` VALUES (23, '2018-11-21 11:47:51', 'Numerical PDE', '2013326601108_1.docx', '吴雯进', '2013326601108', 1);
INSERT INTO `homework` VALUES (24, '2018-11-21 11:47:51', 'Numerical PDE', '2013326601110_1.doc', '熊海洋', '2013326601110', 1);
INSERT INTO `homework` VALUES (25, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601058_2.pdf', '陈萌哲', '2012326601058', 2);
INSERT INTO `homework` VALUES (26, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601065_2.pdf', '吴艳霞', '2012326601065', 2);
INSERT INTO `homework` VALUES (27, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601085_2.doc', '韩美佳', '2012326601085', 2);
INSERT INTO `homework` VALUES (28, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601100_2.docx', '李泽浩', '2012326601100', 2);
INSERT INTO `homework` VALUES (29, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601062_2.docx', '凌海雅', '2013326601062', 2);
INSERT INTO `homework` VALUES (30, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601088_2.docx', '李男', '2013326601088', 2);
INSERT INTO `homework` VALUES (31, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601103_2.doc', '刘君健', '2013326601103', 2);
INSERT INTO `homework` VALUES (32, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601108_2.docx', '吴雯进', '2013326601108', 2);
INSERT INTO `homework` VALUES (34, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601058_3.pdf', '陈萌哲', '2012326601058', 3);
INSERT INTO `homework` VALUES (35, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601065_3.pdf', '吴艳霞', '2012326601065', 3);
INSERT INTO `homework` VALUES (36, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601085_3.doc', '韩美佳', '2012326601085', 3);
INSERT INTO `homework` VALUES (37, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601100_3.docx', '李泽浩', '2012326601100', 3);
INSERT INTO `homework` VALUES (38, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601062_3.docx', '凌海雅', '2013326601062', 3);
INSERT INTO `homework` VALUES (39, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601088_3.docx', '李男', '2013326601088', 3);
INSERT INTO `homework` VALUES (41, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601108_3.docx', '吴雯进', '2013326601108', 3);
INSERT INTO `homework` VALUES (42, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601110_3.doc', '熊海洋', '2013326601110', 3);
INSERT INTO `homework` VALUES (43, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601058_4.pdf', '陈萌哲', '2012326601058', 4);
INSERT INTO `homework` VALUES (44, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601065_4.pdf', '吴艳霞', '2012326601065', 4);
INSERT INTO `homework` VALUES (45, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601085_4.doc', '韩美佳', '2012326601085', 4);
INSERT INTO `homework` VALUES (46, '2018-11-21 11:47:52', 'Numerical PDE', '2012326601100_4.docx', '李泽浩', '2012326601100', 4);
INSERT INTO `homework` VALUES (47, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601062_4.docx', '凌海雅', '2013326601062', 4);
INSERT INTO `homework` VALUES (48, '2018-11-21 11:47:52', 'Numerical PDE', '2013326601088_4.docx', '李男', '2013326601088', 4);
INSERT INTO `homework` VALUES (49, '2018-11-21 11:47:53', 'Numerical PDE', '2013326601103_4.doc', '刘君健', '2013326601103', 4);
INSERT INTO `homework` VALUES (50, '2018-11-21 11:47:53', 'Numerical PDE', '2013326601108_4.docx', '吴雯进', '2013326601108', 4);
INSERT INTO `homework` VALUES (51, '2018-11-21 11:47:53', 'Numerical PDE', '2013326601110_4.doc', '熊海洋', '2013326601110', 4);
INSERT INTO `homework` VALUES (52, '2018-11-21 11:47:53', 'Numerical PDE', '2012326601058_5.pdf', '陈萌哲', '2012326601058', 5);
INSERT INTO `homework` VALUES (53, '2018-11-21 11:47:53', 'Numerical PDE', '2012326601065_5.pdf', '吴艳霞', '2012326601065', 5);
INSERT INTO `homework` VALUES (54, '2018-11-21 11:47:53', 'Numerical PDE', '2012326601085_5.doc', '韩美佳', '2012326601085', 5);
INSERT INTO `homework` VALUES (55, '2018-11-21 11:47:53', 'Numerical PDE', '2012326601100_5.docx', '李泽浩', '2012326601100', 5);
INSERT INTO `homework` VALUES (56, '2018-11-21 11:47:53', 'Numerical PDE', '2013326601062_5.docx', '凌海雅', '2013326601062', 5);
INSERT INTO `homework` VALUES (57, '2018-11-21 11:47:53', 'Numerical PDE', '2013326601088_5.docx', '李男', '2013326601088', 5);
INSERT INTO `homework` VALUES (58, '2018-11-21 11:47:53', 'Numerical PDE', '2013326601103_5.doc', '刘君健', '2013326601103', 5);
INSERT INTO `homework` VALUES (59, '2018-11-21 11:47:53', 'Numerical PDE', '2013326601108_5.docx', '吴雯进', '2013326601108', 5);
INSERT INTO `homework` VALUES (60, '2018-11-21 11:47:53', 'Numerical PDE', '2013326601110_5.doc', '熊海洋', '2013326601110', 5);
INSERT INTO `homework` VALUES (61, '2018-11-21 12:15:55', 'Numerical PDE', '申梦蝶_1.docx', '申梦蝶', '2013326601063', 1);
INSERT INTO `homework` VALUES (62, '2018-11-21 12:15:55', 'Numerical PDE', '方政_1.docx', '方政', '2013326601071', 1);
INSERT INTO `homework` VALUES (63, '2018-11-21 12:15:55', 'Numerical PDE', '芮志_1.docx', '芮志', '2013326601079', 1);
INSERT INTO `homework` VALUES (64, '2018-11-21 12:15:55', 'Numerical PDE', '王力_1.docx', '王力', '2013326601107', 1);
INSERT INTO `homework` VALUES (65, '2018-11-21 12:15:55', 'Numerical PDE', '杨聪霞_1.docx', '杨聪霞', '2014326601063', 1);
INSERT INTO `homework` VALUES (66, '2018-11-21 12:15:55', 'Numerical PDE', '申梦蝶_2.docx', '申梦蝶', '2013326601063', 2);
INSERT INTO `homework` VALUES (67, '2018-11-21 12:15:55', 'Numerical PDE', '方政_2.docx', '方政', '2013326601071', 2);
INSERT INTO `homework` VALUES (68, '2018-11-21 12:15:55', 'Numerical PDE', '芮志_2.docx', '芮志', '2013326601079', 2);
INSERT INTO `homework` VALUES (69, '2018-11-21 12:15:56', 'Numerical PDE', '王力_2.docx', '王力', '2013326601107', 2);
INSERT INTO `homework` VALUES (70, '2018-11-21 12:15:56', 'Numerical PDE', '杨聪霞_2.doc', '杨聪霞', '2014326601063', 2);
INSERT INTO `homework` VALUES (71, '2018-11-21 12:15:56', 'Numerical PDE', '申梦蝶_3.docx', '申梦蝶', '2013326601063', 3);
INSERT INTO `homework` VALUES (72, '2018-11-21 12:15:56', 'Numerical PDE', '方政_3.docx', '方政', '2013326601071', 3);
INSERT INTO `homework` VALUES (73, '2018-11-21 12:15:56', 'Numerical PDE', '芮志_3.docx', '芮志', '2013326601079', 3);
INSERT INTO `homework` VALUES (74, '2018-11-21 12:15:56', 'Numerical PDE', '王力_3.docx', '王力', '2013326601107', 3);
INSERT INTO `homework` VALUES (75, '2018-11-21 12:15:56', 'Numerical PDE', '杨聪霞_3.docx', '杨聪霞', '2014326601063', 3);
INSERT INTO `homework` VALUES (76, '2018-11-21 12:15:56', 'Numerical PDE', '申梦蝶_4.docx', '申梦蝶', '2013326601063', 4);
INSERT INTO `homework` VALUES (77, '2018-11-21 12:15:56', 'Numerical PDE', '方政_4.docx', '方政', '2013326601071', 4);
INSERT INTO `homework` VALUES (78, '2018-11-21 12:15:56', 'Numerical PDE', '芮志_4.docx', '芮志', '2013326601079', 4);
INSERT INTO `homework` VALUES (79, '2018-11-21 12:15:56', 'Numerical PDE', '王力_4.docx', '王力', '2013326601107', 4);
INSERT INTO `homework` VALUES (80, '2018-11-21 12:15:56', 'Numerical PDE', '杨聪霞_4.docx', '杨聪霞', '2014326601063', 4);
INSERT INTO `homework` VALUES (81, '2018-11-21 12:15:56', 'Numerical PDE', '申梦蝶_5.docx', '申梦蝶', '2013326601063', 5);
INSERT INTO `homework` VALUES (82, '2018-11-21 12:15:56', 'Numerical PDE', '方政_5.docx', '方政', '2013326601071', 5);
INSERT INTO `homework` VALUES (83, '2018-11-21 12:15:56', 'Numerical PDE', '芮志_5.docx', '芮志', '2013326601079', 5);
INSERT INTO `homework` VALUES (84, '2018-11-21 12:15:56', 'Numerical PDE', '王力_5.docx', '王力', '2013326601107', 5);
INSERT INTO `homework` VALUES (85, '2018-11-21 12:15:56', 'Numerical PDE', '杨聪霞_5.docx', '杨聪霞', '2014326601063', 5);
INSERT INTO `homework` VALUES (86, '2018-11-21 12:26:29', 'Numerical PDE', '孟旭1.zip', '孟旭  ', '2014326601021', 1);
INSERT INTO `homework` VALUES (87, '2018-11-21 12:26:30', 'Numerical PDE', '邓琳1.zip', '邓琳  ', '2014326601031', 1);
INSERT INTO `homework` VALUES (88, '2018-11-21 12:26:30', 'Numerical PDE', '张艳秋1.zip', '张艳秋', '2014326601041', 1);
INSERT INTO `homework` VALUES (89, '2018-11-21 12:26:30', 'Numerical PDE', '蔡炯坚1.zip', '蔡炯坚', '2014326601042', 1);
INSERT INTO `homework` VALUES (90, '2018-11-21 12:26:30', 'Numerical PDE', '张其伦1.zip', '张其伦', '2014326601058', 1);
INSERT INTO `homework` VALUES (91, '2018-11-21 12:26:30', 'Numerical PDE', '杨铮1.zip', '杨铮  ', '2014326601064', 1);
INSERT INTO `homework` VALUES (92, '2018-11-21 12:26:30', 'Numerical PDE', '张心怡1.zip', '张心怡', '2014326601066', 1);
INSERT INTO `homework` VALUES (93, '2018-11-21 12:26:30', 'Numerical PDE', '杨志伟1.zip', '杨志伟', '2014326601084', 1);
INSERT INTO `homework` VALUES (94, '2018-11-21 12:26:30', 'Numerical PDE', '张泽宇1.zip', '张泽宇', '2014326601085', 1);
INSERT INTO `homework` VALUES (95, '2018-11-21 12:26:30', 'Numerical PDE', '朱良帅1.zip', '朱良帅', '2014326601086', 1);
INSERT INTO `homework` VALUES (96, '2018-11-21 12:26:30', 'Numerical PDE', '李清河1.zip', '李清河', '2014326601089', 1);
INSERT INTO `homework` VALUES (97, '2018-11-21 12:26:30', 'Numerical PDE', '蔡杭1.zip', '蔡杭  ', '2014326601099', 1);
INSERT INTO `homework` VALUES (98, '2018-11-21 12:26:30', 'Numerical PDE', '段旭元1.zip', '段旭元', '2014326601100', 1);
INSERT INTO `homework` VALUES (99, '2018-11-21 12:26:30', 'Numerical PDE', '范许兵1.zip', '范许兵', '2014326601101', 1);
INSERT INTO `homework` VALUES (100, '2018-11-21 12:26:30', 'Numerical PDE', '李云柯1.zip', '李云柯', '2014339900018', 1);
INSERT INTO `homework` VALUES (101, '2018-11-21 12:26:30', 'Numerical PDE', '孟旭2.zip', '孟旭  ', '2014326601021', 2);
INSERT INTO `homework` VALUES (102, '2018-11-21 12:26:30', 'Numerical PDE', '邓琳2.zip', '邓琳  ', '2014326601031', 2);
INSERT INTO `homework` VALUES (103, '2018-11-21 12:26:30', 'Numerical PDE', '张艳秋2.zip', '张艳秋', '2014326601041', 2);
INSERT INTO `homework` VALUES (104, '2018-11-21 12:26:30', 'Numerical PDE', '蔡炯坚2.zip', '蔡炯坚', '2014326601042', 2);
INSERT INTO `homework` VALUES (105, '2018-11-21 12:26:30', 'Numerical PDE', '张其伦2.zip', '张其伦', '2014326601058', 2);
INSERT INTO `homework` VALUES (106, '2018-11-21 12:26:30', 'Numerical PDE', '杨铮2.zip', '杨铮  ', '2014326601064', 2);
INSERT INTO `homework` VALUES (107, '2018-11-21 12:26:30', 'Numerical PDE', '张心怡2.zip', '张心怡', '2014326601066', 2);
INSERT INTO `homework` VALUES (108, '2018-11-21 12:26:30', 'Numerical PDE', '杨志伟2.zip', '杨志伟', '2014326601084', 2);
INSERT INTO `homework` VALUES (109, '2018-11-21 12:26:30', 'Numerical PDE', '张泽宇2.zip', '张泽宇', '2014326601085', 2);
INSERT INTO `homework` VALUES (110, '2018-11-21 12:26:30', 'Numerical PDE', '朱良帅2.zip', '朱良帅', '2014326601086', 2);
INSERT INTO `homework` VALUES (111, '2018-11-21 12:26:30', 'Numerical PDE', '李清河2.zip', '李清河', '2014326601089', 2);
INSERT INTO `homework` VALUES (112, '2018-11-21 12:26:30', 'Numerical PDE', '蔡杭2.zip', '蔡杭  ', '2014326601099', 2);
INSERT INTO `homework` VALUES (113, '2018-11-21 12:26:31', 'Numerical PDE', '段旭元2.zip', '段旭元', '2014326601100', 2);
INSERT INTO `homework` VALUES (114, '2018-11-21 12:26:31', 'Numerical PDE', '范许兵2.zip', '范许兵', '2014326601101', 2);
INSERT INTO `homework` VALUES (115, '2018-11-21 12:26:31', 'Numerical PDE', '李云柯2.zip', '李云柯', '2014339900018', 2);
INSERT INTO `homework` VALUES (116, '2018-11-21 12:26:31', 'Numerical PDE', '孟旭3.zip', '孟旭  ', '2014326601021', 3);
INSERT INTO `homework` VALUES (117, '2018-11-21 12:26:31', 'Numerical PDE', '邓琳3.zip', '邓琳  ', '2014326601031', 3);
INSERT INTO `homework` VALUES (118, '2018-11-21 12:26:31', 'Numerical PDE', '张艳秋3.zip', '张艳秋', '2014326601041', 3);
INSERT INTO `homework` VALUES (119, '2018-11-21 12:26:31', 'Numerical PDE', '蔡炯坚3.zip', '蔡炯坚', '2014326601042', 3);
INSERT INTO `homework` VALUES (120, '2018-11-21 12:26:31', 'Numerical PDE', '张其伦3.zip', '张其伦', '2014326601058', 3);
INSERT INTO `homework` VALUES (121, '2018-11-21 12:26:31', 'Numerical PDE', '杨铮3.zip', '杨铮  ', '2014326601064', 3);
INSERT INTO `homework` VALUES (122, '2018-11-21 12:26:31', 'Numerical PDE', '张心怡3.zip', '张心怡', '2014326601066', 3);
INSERT INTO `homework` VALUES (123, '2018-11-21 12:26:31', 'Numerical PDE', '杨志伟3.zip', '杨志伟', '2014326601084', 3);
INSERT INTO `homework` VALUES (124, '2018-11-21 12:26:31', 'Numerical PDE', '张泽宇3.zip', '张泽宇', '2014326601085', 3);
INSERT INTO `homework` VALUES (125, '2018-11-21 12:26:31', 'Numerical PDE', '朱良帅3.zip', '朱良帅', '2014326601086', 3);
INSERT INTO `homework` VALUES (126, '2018-11-21 12:26:31', 'Numerical PDE', '李清河3.zip', '李清河', '2014326601089', 3);
INSERT INTO `homework` VALUES (127, '2018-11-21 12:26:31', 'Numerical PDE', '蔡杭3.zip', '蔡杭  ', '2014326601099', 3);
INSERT INTO `homework` VALUES (128, '2018-11-21 12:26:31', 'Numerical PDE', '段旭元3.zip', '段旭元', '2014326601100', 3);
INSERT INTO `homework` VALUES (130, '2018-11-21 12:26:31', 'Numerical PDE', '李云柯3.zip', '李云柯', '2014339900018', 3);
INSERT INTO `homework` VALUES (131, '2018-11-21 12:26:31', 'Numerical PDE', '孟旭4.zip', '孟旭  ', '2014326601021', 4);
INSERT INTO `homework` VALUES (132, '2018-11-21 12:26:31', 'Numerical PDE', '邓琳4.zip', '邓琳  ', '2014326601031', 4);
INSERT INTO `homework` VALUES (133, '2018-11-21 12:26:31', 'Numerical PDE', '张艳秋4.zip', '张艳秋', '2014326601041', 4);
INSERT INTO `homework` VALUES (134, '2018-11-21 12:26:31', 'Numerical PDE', '蔡炯坚4.zip', '蔡炯坚', '2014326601042', 4);
INSERT INTO `homework` VALUES (135, '2018-11-21 12:26:31', 'Numerical PDE', '张其伦4.zip', '张其伦', '2014326601058', 4);
INSERT INTO `homework` VALUES (136, '2018-11-21 12:26:31', 'Numerical PDE', '杨铮4.zip', '杨铮  ', '2014326601064', 4);
INSERT INTO `homework` VALUES (138, '2018-11-21 12:26:32', 'Numerical PDE', '杨志伟4.zip', '杨志伟', '2014326601084', 4);
INSERT INTO `homework` VALUES (139, '2018-11-21 12:26:32', 'Numerical PDE', '张泽宇4.zip', '张泽宇', '2014326601085', 4);
INSERT INTO `homework` VALUES (140, '2018-11-21 12:26:32', 'Numerical PDE', '朱良帅4.zip', '朱良帅', '2014326601086', 4);
INSERT INTO `homework` VALUES (141, '2018-11-21 12:26:32', 'Numerical PDE', '李清河4.zip', '李清河', '2014326601089', 4);
INSERT INTO `homework` VALUES (142, '2018-11-21 12:26:32', 'Numerical PDE', '蔡杭4.zip', '蔡杭  ', '2014326601099', 4);
INSERT INTO `homework` VALUES (143, '2018-11-21 12:26:32', 'Numerical PDE', '段旭元4.zip', '段旭元', '2014326601100', 4);
INSERT INTO `homework` VALUES (144, '2018-11-21 12:26:32', 'Numerical PDE', '范许兵4.zip', '范许兵', '2014326601101', 4);
INSERT INTO `homework` VALUES (145, '2018-11-21 12:26:32', 'Numerical PDE', '李云柯4.zip', '李云柯', '2014339900018', 4);
INSERT INTO `homework` VALUES (146, '2018-11-21 12:26:32', 'Numerical PDE', '孟旭5.zip', '孟旭  ', '2014326601021', 5);
INSERT INTO `homework` VALUES (147, '2018-11-21 12:26:32', 'Numerical PDE', '邓琳5.zip', '邓琳  ', '2014326601031', 5);
INSERT INTO `homework` VALUES (148, '2018-11-21 12:26:32', 'Numerical PDE', '张艳秋5.zip', '张艳秋', '2014326601041', 5);
INSERT INTO `homework` VALUES (149, '2018-11-21 12:26:32', 'Numerical PDE', '蔡炯坚5.zip', '蔡炯坚', '2014326601042', 5);
INSERT INTO `homework` VALUES (150, '2018-11-21 12:26:32', 'Numerical PDE', '张其伦5.zip', '张其伦', '2014326601058', 5);
INSERT INTO `homework` VALUES (151, '2018-11-21 12:26:32', 'Numerical PDE', '杨铮5.zip', '杨铮  ', '2014326601064', 5);
INSERT INTO `homework` VALUES (152, '2018-11-21 12:26:32', 'Numerical PDE', '张心怡5.zip', '张心怡', '2014326601066', 5);
INSERT INTO `homework` VALUES (153, '2018-11-21 12:26:32', 'Numerical PDE', '杨志伟5.zip', '杨志伟', '2014326601084', 5);
INSERT INTO `homework` VALUES (154, '2018-11-21 12:26:32', 'Numerical PDE', '张泽宇5.zip', '张泽宇', '2014326601085', 5);
INSERT INTO `homework` VALUES (155, '2018-11-21 12:26:32', 'Numerical PDE', '朱良帅5.zip', '朱良帅', '2014326601086', 5);
INSERT INTO `homework` VALUES (156, '2018-11-21 12:26:32', 'Numerical PDE', '李清河5.zip', '李清河', '2014326601089', 5);
INSERT INTO `homework` VALUES (157, '2018-11-21 12:26:32', 'Numerical PDE', '蔡杭5.zip', '蔡杭  ', '2014326601099', 5);
INSERT INTO `homework` VALUES (158, '2018-11-21 12:26:32', 'Numerical PDE', '段旭元5.zip', '段旭元', '2014326601100', 5);
INSERT INTO `homework` VALUES (159, '2018-11-21 12:26:32', 'Numerical PDE', '范许兵5.zip', '范许兵', '2014326601101', 5);
INSERT INTO `homework` VALUES (160, '2018-11-21 12:26:32', 'Numerical PDE', '李云柯5.zip', '李云柯', '2014339900018', 5);
INSERT INTO `homework` VALUES (533, '2018-11-21 05:48:06', 'DATA Analysis', '2015326601010_1.zip', '张丹妮', '2015326601010', 1);
INSERT INTO `homework` VALUES (534, '2018-11-21 05:48:06', 'DATA Analysis', '2015326601010_2.docx', '张丹妮', '2015326601010', 2);
INSERT INTO `homework` VALUES (535, '2018-11-21 05:48:06', 'DATA Analysis', '2015326601010_3.docx', '张丹妮', '2015326601010', 3);
INSERT INTO `homework` VALUES (536, '2018-11-21 05:48:06', 'DATA Analysis', '2015326601010_4.docx', '张丹妮', '2015326601010', 4);
INSERT INTO `homework` VALUES (537, '2018-11-21 05:48:06', 'DATA Analysis', '2015326601010_5.docx', '张丹妮', '2015326601010', 5);
INSERT INTO `homework` VALUES (538, '2018-11-21 05:48:06', 'DATA Analysis', '2015326601010_6.docx', '张丹妮', '2015326601010', 6);
INSERT INTO `homework` VALUES (539, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601016_1.wps', '胡锆', '2015326601016', 1);
INSERT INTO `homework` VALUES (540, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601016_2.wps', '胡锆', '2015326601016', 2);
INSERT INTO `homework` VALUES (541, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601016_3.wps', '胡锆', '2015326601016', 3);
INSERT INTO `homework` VALUES (542, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601016_4.wps', '胡锆', '2015326601016', 4);
INSERT INTO `homework` VALUES (543, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601019_1.docx', '毛沐其', '2015326601019', 1);
INSERT INTO `homework` VALUES (544, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601019_2.wps', '毛沐其', '2015326601019', 2);
INSERT INTO `homework` VALUES (545, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601019_3.docx', '毛沐其', '2015326601019', 3);
INSERT INTO `homework` VALUES (546, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601019_4.docx', '毛沐其', '2015326601019', 4);
INSERT INTO `homework` VALUES (547, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601019_5.docx', '毛沐其', '2015326601019', 5);
INSERT INTO `homework` VALUES (548, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601019_6.docx', '毛沐其', '2015326601019', 6);
INSERT INTO `homework` VALUES (549, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601019_7.zip', '毛沐其', '2015326601019', 7);
INSERT INTO `homework` VALUES (550, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601025_1.docx', '张仁', '2015326601025', 1);
INSERT INTO `homework` VALUES (551, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601025_2.docx', '张仁', '2015326601025', 2);
INSERT INTO `homework` VALUES (552, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601025_3.docx', '张仁', '2015326601025', 3);
INSERT INTO `homework` VALUES (553, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601025_4.docx', '张仁', '2015326601025', 4);
INSERT INTO `homework` VALUES (554, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601025_5.docx', '张仁', '2015326601025', 5);
INSERT INTO `homework` VALUES (555, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601025_6.docx', '张仁', '2015326601025', 6);
INSERT INTO `homework` VALUES (556, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601025_7.docx', '张仁', '2015326601025', 7);
INSERT INTO `homework` VALUES (557, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601025_8.docx', '张仁', '2015326601025', 8);
INSERT INTO `homework` VALUES (558, '2018-11-21 05:48:07', 'DATA Analysis', '2015326601025_9.docx', '张仁', '2015326601025', 9);
INSERT INTO `homework` VALUES (559, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601054_1.zip', '周鑫', '2015326601054', 1);
INSERT INTO `homework` VALUES (560, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601054_2.docx', '周鑫', '2015326601054', 2);
INSERT INTO `homework` VALUES (561, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601054_3.docx', '周鑫', '2015326601054', 3);
INSERT INTO `homework` VALUES (562, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601054_4.docx', '周鑫', '2015326601054', 4);
INSERT INTO `homework` VALUES (563, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601054_5.docx', '周鑫', '2015326601054', 5);
INSERT INTO `homework` VALUES (564, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601061_1.zip', '徐丽孝', '2015326601061', 1);
INSERT INTO `homework` VALUES (565, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601061_2.docx', '徐丽孝', '2015326601061', 2);
INSERT INTO `homework` VALUES (566, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601061_3.docx', '徐丽孝', '2015326601061', 3);
INSERT INTO `homework` VALUES (567, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601061_4.docx', '徐丽孝', '2015326601061', 4);
INSERT INTO `homework` VALUES (568, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601061_5.doc', '徐丽孝', '2015326601061', 5);
INSERT INTO `homework` VALUES (569, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601061_6.docx', '徐丽孝', '2015326601061', 6);
INSERT INTO `homework` VALUES (570, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601063_1.docx', '赵海乐', '2015326601063', 1);
INSERT INTO `homework` VALUES (571, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601063_2.docx', '赵海乐', '2015326601063', 2);
INSERT INTO `homework` VALUES (572, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601063_3.docx', '赵海乐', '2015326601063', 3);
INSERT INTO `homework` VALUES (573, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601063_4.docx', '赵海乐', '2015326601063', 4);
INSERT INTO `homework` VALUES (574, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601063_5.docx', '赵海乐', '2015326601063', 5);
INSERT INTO `homework` VALUES (575, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601063_6.docx', '赵海乐', '2015326601063', 6);
INSERT INTO `homework` VALUES (576, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601064_1.docx', '赵璐', '2015326601064', 1);
INSERT INTO `homework` VALUES (577, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601064_2.doc', '赵璐', '2015326601064', 2);
INSERT INTO `homework` VALUES (578, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601064_3.docx', '赵璐', '2015326601064', 3);
INSERT INTO `homework` VALUES (579, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601064_4.docx', '赵璐', '2015326601064', 4);
INSERT INTO `homework` VALUES (580, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601064_5.docx', '赵璐', '2015326601064', 5);
INSERT INTO `homework` VALUES (581, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601066_1.docx', '顾文君', '2015326601066', 1);
INSERT INTO `homework` VALUES (582, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601066_2.docx', '顾文君', '2015326601066', 2);
INSERT INTO `homework` VALUES (583, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601066_3.docx', '顾文君', '2015326601066', 3);
INSERT INTO `homework` VALUES (584, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601066_4.docx', '顾文君', '2015326601066', 4);
INSERT INTO `homework` VALUES (585, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601066_5.docx', '顾文君', '2015326601066', 5);
INSERT INTO `homework` VALUES (586, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601066_6.docx', '顾文君', '2015326601066', 6);
INSERT INTO `homework` VALUES (587, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601066_7.docx', '顾文君', '2015326601066', 7);
INSERT INTO `homework` VALUES (588, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601067_1.zip', '洪俣祎', '2015326601067', 1);
INSERT INTO `homework` VALUES (589, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601067_2.docx', '洪俣祎', '2015326601067', 2);
INSERT INTO `homework` VALUES (590, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601067_3.docx', '洪俣祎', '2015326601067', 3);
INSERT INTO `homework` VALUES (591, '2018-11-21 05:48:08', 'DATA Analysis', '2015326601067_4.docx', '洪俣祎', '2015326601067', 4);
INSERT INTO `homework` VALUES (592, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601067_5.docx', '洪俣祎', '2015326601067', 5);
INSERT INTO `homework` VALUES (593, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601067_6.docx', '洪俣祎', '2015326601067', 6);
INSERT INTO `homework` VALUES (594, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601072_1.docx', '毛清集', '2015326601072', 1);
INSERT INTO `homework` VALUES (595, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601072_2.docx', '毛清集', '2015326601072', 2);
INSERT INTO `homework` VALUES (596, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601072_3.docx', '毛清集', '2015326601072', 3);
INSERT INTO `homework` VALUES (597, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601072_4.docx', '毛清集', '2015326601072', 4);
INSERT INTO `homework` VALUES (598, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601074_1.zip', '邵仕浩', '2015326601074', 1);
INSERT INTO `homework` VALUES (599, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601074_2.doc', '邵仕浩', '2015326601074', 2);
INSERT INTO `homework` VALUES (600, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601074_3.docx', '邵仕浩', '2015326601074', 3);
INSERT INTO `homework` VALUES (601, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601074_4.docx', '邵仕浩', '2015326601074', 4);
INSERT INTO `homework` VALUES (602, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601074_5.docx', '邵仕浩', '2015326601074', 5);
INSERT INTO `homework` VALUES (603, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601075_1.wps', '斯翔斌', '2015326601075', 1);
INSERT INTO `homework` VALUES (604, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601075_2.docx', '斯翔斌', '2015326601075', 2);
INSERT INTO `homework` VALUES (605, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601075_3.docx', '斯翔斌', '2015326601075', 3);
INSERT INTO `homework` VALUES (606, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601075_4.docx', '斯翔斌', '2015326601075', 4);
INSERT INTO `homework` VALUES (607, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601075_5.docx', '斯翔斌', '2015326601075', 5);
INSERT INTO `homework` VALUES (608, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601075_6.docx', '斯翔斌', '2015326601075', 6);
INSERT INTO `homework` VALUES (609, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601076_1.docx', '孙立威', '2015326601076', 1);
INSERT INTO `homework` VALUES (610, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601076_2.docx', '孙立威', '2015326601076', 2);
INSERT INTO `homework` VALUES (611, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601076_3.doc', '孙立威', '2015326601076', 3);
INSERT INTO `homework` VALUES (612, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601076_4.docx', '孙立威', '2015326601076', 4);
INSERT INTO `homework` VALUES (613, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601076_5.docx', '孙立威', '2015326601076', 5);
INSERT INTO `homework` VALUES (614, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601076_6.docx', '孙立威', '2015326601076', 6);
INSERT INTO `homework` VALUES (615, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601077_1.zip', '孙永', '2015326601077', 1);
INSERT INTO `homework` VALUES (616, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601077_2.zip', '孙永', '2015326601077', 2);
INSERT INTO `homework` VALUES (617, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601077_3.docx', '孙永', '2015326601077', 3);
INSERT INTO `homework` VALUES (618, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601077_4.zip', '孙永', '2015326601077', 4);
INSERT INTO `homework` VALUES (619, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601077_5.zip', '孙永', '2015326601077', 5);
INSERT INTO `homework` VALUES (620, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601077_6.docx', '孙永', '2015326601077', 6);
INSERT INTO `homework` VALUES (621, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601077_7.zip', '孙永', '2015326601077', 7);
INSERT INTO `homework` VALUES (622, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601078_1.zip', '吴章义', '2015326601078', 1);
INSERT INTO `homework` VALUES (623, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601078_2.pdf', '吴章义', '2015326601078', 2);
INSERT INTO `homework` VALUES (624, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601078_3.docx', '吴章义', '2015326601078', 3);
INSERT INTO `homework` VALUES (625, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601078_4.pdf', '吴章义', '2015326601078', 4);
INSERT INTO `homework` VALUES (626, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601078_5.pdf', '吴章义', '2015326601078', 5);
INSERT INTO `homework` VALUES (627, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601078_6.pdf', '吴章义', '2015326601078', 6);
INSERT INTO `homework` VALUES (628, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601079_1.docx', '徐杰', '2015326601079', 1);
INSERT INTO `homework` VALUES (629, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601079_2.docx', '徐杰', '2015326601079', 2);
INSERT INTO `homework` VALUES (630, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601079_3.docx', '徐杰', '2015326601079', 3);
INSERT INTO `homework` VALUES (631, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601079_4.docx', '徐杰', '2015326601079', 4);
INSERT INTO `homework` VALUES (632, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601089_1.docx', '魏明洁', '2015326601089', 1);
INSERT INTO `homework` VALUES (633, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601089_2.docx', '魏明洁', '2015326601089', 2);
INSERT INTO `homework` VALUES (634, '2018-11-21 05:48:09', 'DATA Analysis', '2015326601089_3.docx', '魏明洁', '2015326601089', 3);
INSERT INTO `homework` VALUES (635, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601089_4.docx', '魏明洁', '2015326601089', 4);
INSERT INTO `homework` VALUES (636, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601089_5.docx', '魏明洁', '2015326601089', 5);
INSERT INTO `homework` VALUES (637, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601089_6.docx', '魏明洁', '2015326601089', 6);
INSERT INTO `homework` VALUES (638, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601090_1.docx', '谢缝彬', '2015326601090', 1);
INSERT INTO `homework` VALUES (639, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601090_2.docx', '谢缝彬', '2015326601090', 2);
INSERT INTO `homework` VALUES (640, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601090_3.docx', '谢缝彬', '2015326601090', 3);
INSERT INTO `homework` VALUES (641, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601090_4.docx', '谢缝彬', '2015326601090', 4);
INSERT INTO `homework` VALUES (642, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601090_5.rar', '谢缝彬', '2015326601090', 5);
INSERT INTO `homework` VALUES (643, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601092_1.docx', '张宁', '2015326601092', 1);
INSERT INTO `homework` VALUES (644, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601092_2.docx', '张宁', '2015326601092', 2);
INSERT INTO `homework` VALUES (645, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601092_3.doc', '张宁', '2015326601092', 3);
INSERT INTO `homework` VALUES (646, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601092_4.docx', '张宁', '2015326601092', 4);
INSERT INTO `homework` VALUES (647, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601092_5.zip', '张宁', '2015326601092', 5);
INSERT INTO `homework` VALUES (648, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601092_6.docx', '张宁', '2015326601092', 6);
INSERT INTO `homework` VALUES (649, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601092_7.docx', '张宁', '2015326601092', 7);
INSERT INTO `homework` VALUES (650, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601094_1.docx', '陈杰', '2015326601094', 1);
INSERT INTO `homework` VALUES (651, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601094_2.doc', '陈杰', '2015326601094', 2);
INSERT INTO `homework` VALUES (652, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601094_3.docx', '陈杰', '2015326601094', 3);
INSERT INTO `homework` VALUES (653, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601094_4.docx', '陈杰', '2015326601094', 4);
INSERT INTO `homework` VALUES (654, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601094_5.docx', '陈杰', '2015326601094', 5);
INSERT INTO `homework` VALUES (655, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601094_6.docx', '陈杰', '2015326601094', 6);
INSERT INTO `homework` VALUES (656, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601100_1.rar', '林洪杰', '2015326601100', 1);
INSERT INTO `homework` VALUES (657, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601100_2.wps', '林洪杰', '2015326601100', 2);
INSERT INTO `homework` VALUES (658, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601100_3.doc', '林洪杰', '2015326601100', 3);
INSERT INTO `homework` VALUES (659, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601100_4.docx', '林洪杰', '2015326601100', 4);
INSERT INTO `homework` VALUES (660, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601100_5.docx', '林洪杰', '2015326601100', 5);
INSERT INTO `homework` VALUES (661, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601100_6.docx', '林洪杰', '2015326601100', 6);
INSERT INTO `homework` VALUES (662, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601101_1.docx', '吕杨才', '2015326601101', 1);
INSERT INTO `homework` VALUES (663, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601101_2.docx', '吕杨才', '2015326601101', 2);
INSERT INTO `homework` VALUES (664, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601101_3.docx', '吕杨才', '2015326601101', 3);
INSERT INTO `homework` VALUES (665, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601101_4.docx', '吕杨才', '2015326601101', 4);
INSERT INTO `homework` VALUES (666, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601101_5.docx', '吕杨才', '2015326601101', 5);
INSERT INTO `homework` VALUES (667, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601101_6.doc', '吕杨才', '2015326601101', 6);
INSERT INTO `homework` VALUES (668, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601103_1.docx', '王俊', '2015326601103', 1);
INSERT INTO `homework` VALUES (669, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601103_2.zip', '王俊', '2015326601103', 2);
INSERT INTO `homework` VALUES (670, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601103_3.rar', '王俊', '2015326601103', 3);
INSERT INTO `homework` VALUES (671, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601103_4.docx', '王俊', '2015326601103', 4);
INSERT INTO `homework` VALUES (672, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601103_5.doc', '王俊', '2015326601103', 5);
INSERT INTO `homework` VALUES (673, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601105_1.docx', '杨茂强', '2015326601105', 1);
INSERT INTO `homework` VALUES (674, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601105_2.docx', '杨茂强', '2015326601105', 2);
INSERT INTO `homework` VALUES (675, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601105_3.docx', '杨茂强', '2015326601105', 3);
INSERT INTO `homework` VALUES (676, '2018-11-21 05:48:10', 'DATA Analysis', '2015326601105_4.docx', '杨茂强', '2015326601105', 4);
INSERT INTO `homework` VALUES (677, '2018-11-21 05:48:11', 'DATA Analysis', '2015326601105_5.docx', '杨茂强', '2015326601105', 5);
INSERT INTO `homework` VALUES (678, '2018-11-21 05:48:11', 'DATA Analysis', '2015326601105_6.docx', '杨茂强', '2015326601105', 6);
INSERT INTO `homework` VALUES (679, '2018-11-21 05:48:11', 'DATA Analysis', '2015334320058_1.zip', '万杰', '2015334320058', 1);
INSERT INTO `homework` VALUES (680, '2018-11-21 05:48:11', 'DATA Analysis', '2015334320058_2.docx', '万杰', '2015334320058', 2);
INSERT INTO `homework` VALUES (681, '2018-11-21 05:48:11', 'DATA Analysis', '2015334320058_3.rar', '万杰', '2015334320058', 3);
INSERT INTO `homework` VALUES (682, '2018-11-21 05:48:11', 'DATA Analysis', '2015334320058_4.docx', '万杰', '2015334320058', 4);
INSERT INTO `homework` VALUES (683, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630001_1.docx', '杨俊燕', '2017426630001', 1);
INSERT INTO `homework` VALUES (684, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630001_2.docx', '杨俊燕', '2017426630001', 2);
INSERT INTO `homework` VALUES (685, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630001_3.docx', '杨俊燕', '2017426630001', 3);
INSERT INTO `homework` VALUES (686, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630001_4.docx', '杨俊燕', '2017426630001', 4);
INSERT INTO `homework` VALUES (687, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630001_5.doc', '杨俊燕', '2017426630001', 5);
INSERT INTO `homework` VALUES (688, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630001_6.doc', '杨俊燕', '2017426630001', 6);
INSERT INTO `homework` VALUES (689, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630001_7.docx', '杨俊燕', '2017426630001', 7);
INSERT INTO `homework` VALUES (690, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630002_1.docx', '范宇', '2017426630002', 1);
INSERT INTO `homework` VALUES (691, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630002_2.docx', '范宇', '2017426630002', 2);
INSERT INTO `homework` VALUES (692, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630002_3.docx', '范宇', '2017426630002', 3);
INSERT INTO `homework` VALUES (693, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630002_4.docx', '范宇', '2017426630002', 4);
INSERT INTO `homework` VALUES (694, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630002_5.docx', '范宇', '2017426630002', 5);
INSERT INTO `homework` VALUES (695, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630002_6.docx', '范宇', '2017426630002', 6);
INSERT INTO `homework` VALUES (696, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630003_1.zip', '施弛也', '2017426630003', 1);
INSERT INTO `homework` VALUES (697, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630003_2.zip', '施弛也', '2017426630003', 2);
INSERT INTO `homework` VALUES (698, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630003_3.docx', '施弛也', '2017426630003', 3);
INSERT INTO `homework` VALUES (699, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630003_4.doc', '施弛也', '2017426630003', 4);
INSERT INTO `homework` VALUES (700, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630003_5.docx', '施弛也', '2017426630003', 5);
INSERT INTO `homework` VALUES (701, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630004_1.docx', '舒屹', '2017426630004', 1);
INSERT INTO `homework` VALUES (702, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630004_2.docx', '舒屹', '2017426630004', 2);
INSERT INTO `homework` VALUES (703, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630004_3.docx', '舒屹', '2017426630004', 3);
INSERT INTO `homework` VALUES (704, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630004_4.docx', '舒屹', '2017426630004', 4);
INSERT INTO `homework` VALUES (705, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630004_5.docx', '舒屹', '2017426630004', 5);
INSERT INTO `homework` VALUES (706, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630004_6.docx', '舒屹', '2017426630004', 6);
INSERT INTO `homework` VALUES (707, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630005_1.rar', '张浩鹏', '2017426630005', 1);
INSERT INTO `homework` VALUES (708, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630005_2.rar', '张浩鹏', '2017426630005', 2);
INSERT INTO `homework` VALUES (709, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630005_3.rar', '张浩鹏', '2017426630005', 3);
INSERT INTO `homework` VALUES (710, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630005_4.docx', '张浩鹏', '2017426630005', 4);
INSERT INTO `homework` VALUES (711, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630005_5.docx', '张浩鹏', '2017426630005', 5);
INSERT INTO `homework` VALUES (712, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630005_6.docx', '张浩鹏', '2017426630005', 6);
INSERT INTO `homework` VALUES (713, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630006_1.docx', '章学勤', '2017426630006', 1);
INSERT INTO `homework` VALUES (714, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630006_2.docx', '章学勤', '2017426630006', 2);
INSERT INTO `homework` VALUES (715, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630006_3.docx', '章学勤', '2017426630006', 3);
INSERT INTO `homework` VALUES (716, '2018-11-21 05:48:11', 'DATA Analysis', '2017426630006_4.docx', '章学勤', '2017426630006', 4);
INSERT INTO `homework` VALUES (717, '2018-11-21 05:48:12', 'DATA Analysis', '2017426630006_5.doc', '章学勤', '2017426630006', 5);
INSERT INTO `homework` VALUES (718, '2018-11-21 05:48:12', 'DATA Analysis', '2017426630006_6.docx', '章学勤', '2017426630006', 6);
INSERT INTO `homework` VALUES (719, '2018-11-21 14:00:21', 'Matrix Computations', '邓琳1.rar', '邓琳  ', '2014326601031', 1);
INSERT INTO `homework` VALUES (720, '2018-11-21 14:00:21', 'Matrix Computations', '蔡杭1.rar', '蔡杭  ', '2014326601142', 1);
INSERT INTO `homework` VALUES (721, '2018-11-21 14:00:21', 'Matrix Computations', '段旭元1.rar', '段旭元', '2014326601017', 1);
INSERT INTO `homework` VALUES (722, '2018-11-21 14:00:21', 'Matrix Computations', '范许兵1.rar', '范许兵', '2014326601025', 1);
INSERT INTO `homework` VALUES (723, '2018-11-21 14:00:21', 'Matrix Computations', '李清河1.rar', '李清河', '2014326601109', 1);
INSERT INTO `homework` VALUES (724, '2018-11-21 14:00:21', 'Matrix Computations', '陆娇娇1.rar', '陆娇娇', '2014326601035', 1);
INSERT INTO `homework` VALUES (725, '2018-11-21 14:00:21', 'Matrix Computations', '孟旭1.rar', '孟旭  ', '2014326600151', 1);
INSERT INTO `homework` VALUES (726, '2018-11-21 14:00:21', 'Matrix Computations', '杨聪霞1.rar', '杨聪霞', '2014326601003', 1);
INSERT INTO `homework` VALUES (727, '2018-11-21 14:00:21', 'Matrix Computations', '邓琳2.rar', '邓琳  ', '2014326601031', 2);
INSERT INTO `homework` VALUES (728, '2018-11-21 14:00:21', 'Matrix Computations', '蔡杭2.rar', '蔡杭  ', '2014326601142', 2);
INSERT INTO `homework` VALUES (729, '2018-11-21 14:00:21', 'Matrix Computations', '段旭元2.rar', '段旭元', '2014326601017', 2);
INSERT INTO `homework` VALUES (730, '2018-11-21 14:00:21', 'Matrix Computations', '范许兵2.rar', '范许兵', '2014326601025', 2);
INSERT INTO `homework` VALUES (731, '2018-11-21 14:00:21', 'Matrix Computations', '李清河2.rar', '李清河', '2014326601109', 2);
INSERT INTO `homework` VALUES (732, '2018-11-21 14:00:21', 'Matrix Computations', '陆娇娇2.rar', '陆娇娇', '2014326601035', 2);
INSERT INTO `homework` VALUES (733, '2018-11-21 14:00:21', 'Matrix Computations', '孟旭2.rar', '孟旭  ', '2014326600151', 2);
INSERT INTO `homework` VALUES (734, '2018-11-21 14:00:21', 'Matrix Computations', '杨聪霞2.rar', '杨聪霞', '2014326601003', 2);
INSERT INTO `homework` VALUES (735, '2018-11-21 14:00:21', 'Matrix Computations', '邓琳3.rar', '邓琳  ', '2014326601031', 3);
INSERT INTO `homework` VALUES (736, '2018-11-21 14:00:21', 'Matrix Computations', '蔡杭3.rar', '蔡杭  ', '2014326601142', 3);
INSERT INTO `homework` VALUES (737, '2018-11-21 14:00:21', 'Matrix Computations', '段旭元3.rar', '段旭元', '2014326601017', 3);
INSERT INTO `homework` VALUES (738, '2018-11-21 14:00:21', 'Matrix Computations', '范许兵3.rar', '范许兵', '2014326601025', 3);
INSERT INTO `homework` VALUES (739, '2018-11-21 14:00:21', 'Matrix Computations', '李清河3.rar', '李清河', '2014326601109', 3);
INSERT INTO `homework` VALUES (740, '2018-11-21 14:00:22', 'Matrix Computations', '陆娇娇3.rar', '陆娇娇', '2014326601035', 3);
INSERT INTO `homework` VALUES (741, '2018-11-21 14:00:22', 'Matrix Computations', '孟旭3.rar', '孟旭  ', '2014326600151', 3);
INSERT INTO `homework` VALUES (742, '2018-11-21 14:00:22', 'Matrix Computations', '杨聪霞3.rar', '杨聪霞', '2014326601003', 3);
INSERT INTO `homework` VALUES (743, '2018-11-21 14:00:22', 'Matrix Computations', '邓琳4.rar', '邓琳  ', '2014326601031', 4);
INSERT INTO `homework` VALUES (744, '2018-11-21 14:00:22', 'Matrix Computations', '蔡杭4.rar', '蔡杭  ', '2014326601142', 4);
INSERT INTO `homework` VALUES (745, '2018-11-21 14:00:22', 'Matrix Computations', '段旭元4.rar', '段旭元', '2014326601017', 4);
INSERT INTO `homework` VALUES (746, '2018-11-21 14:00:22', 'Matrix Computations', '范许兵4.rar', '范许兵', '2014326601025', 4);
INSERT INTO `homework` VALUES (747, '2018-11-21 14:00:22', 'Matrix Computations', '李清河4.rar', '李清河', '2014326601109', 4);
INSERT INTO `homework` VALUES (748, '2018-11-21 14:00:22', 'Matrix Computations', '陆娇娇4.rar', '陆娇娇', '2014326601035', 4);
INSERT INTO `homework` VALUES (749, '2018-11-21 14:00:22', 'Matrix Computations', '孟旭4.rar', '孟旭  ', '2014326600151', 4);
INSERT INTO `homework` VALUES (750, '2018-11-21 14:00:22', 'Matrix Computations', '杨聪霞4.rar', '杨聪霞', '2014326601003', 4);
INSERT INTO `homework` VALUES (751, '2018-11-21 14:00:22', 'Matrix Computations', '邓琳5.rar', '邓琳  ', '2014326601031', 5);
INSERT INTO `homework` VALUES (752, '2018-11-21 14:00:22', 'Matrix Computations', '蔡杭5.rar', '蔡杭  ', '2014326601142', 5);
INSERT INTO `homework` VALUES (753, '2018-11-21 14:00:22', 'Matrix Computations', '段旭元5.rar', '段旭元', '2014326601017', 5);
INSERT INTO `homework` VALUES (754, '2018-11-21 14:00:22', 'Matrix Computations', '范许兵5.rar', '范许兵', '2014326601025', 5);
INSERT INTO `homework` VALUES (755, '2018-11-21 14:00:22', 'Matrix Computations', '李清河5.rar', '李清河', '2014326601109', 5);
INSERT INTO `homework` VALUES (756, '2018-11-21 14:00:22', 'Matrix Computations', '陆娇娇5.rar', '陆娇娇', '2014326601035', 5);
INSERT INTO `homework` VALUES (757, '2018-11-21 14:00:22', 'Matrix Computations', '孟旭5.rar', '孟旭  ', '2014326600151', 5);
INSERT INTO `homework` VALUES (758, '2018-11-21 14:00:22', 'Matrix Computations', '杨聪霞5.rar', '杨聪霞', '2014326601003', 5);
INSERT INTO `homework` VALUES (759, '2018-11-21 14:00:22', 'Matrix Computations', '邓琳6.rar', '邓琳  ', '2014326601031', 6);
INSERT INTO `homework` VALUES (760, '2018-11-21 14:00:22', 'Matrix Computations', '蔡杭6.rar', '蔡杭  ', '2014326601142', 6);
INSERT INTO `homework` VALUES (761, '2018-11-21 14:00:22', 'Matrix Computations', '段旭元6.rar', '段旭元', '2014326601017', 6);
INSERT INTO `homework` VALUES (762, '2018-11-21 14:00:22', 'Matrix Computations', '范许兵6.rar', '范许兵', '2014326601025', 6);
INSERT INTO `homework` VALUES (763, '2018-11-21 14:00:22', 'Matrix Computations', '李清河6.rar', '李清河', '2014326601109', 6);
INSERT INTO `homework` VALUES (764, '2018-11-21 14:00:22', 'Matrix Computations', '陆娇娇6.rar', '陆娇娇', '2014326601035', 6);
INSERT INTO `homework` VALUES (765, '2018-11-21 14:00:22', 'Matrix Computations', '孟旭6.rar', '孟旭  ', '2014326600151', 6);
INSERT INTO `homework` VALUES (766, '2018-11-21 14:00:22', 'Matrix Computations', '杨聪霞6.rar', '杨聪霞', '2014326601003', 6);
INSERT INTO `homework` VALUES (767, '2018-11-22 14:38:49', 'Matrix Computations', '2015326601019_5.txt', '毛沐祺', '2015326601019', 5);
INSERT INTO `homework` VALUES (768, '2018-11-22 14:38:50', 'Matrix Computations', '2015326601019_5.doc', '毛沐祺', '2015326601019', 5);
INSERT INTO `homework` VALUES (769, '2018-11-22 14:38:50', 'Matrix Computations', '2015326601019_1.doc', '毛沐祺', '2015326601019', 1);
INSERT INTO `homework` VALUES (770, '2018-11-22 14:38:50', 'Matrix Computations', '2015326601019_3.doc', '毛沐祺', '2015326601019', 3);
INSERT INTO `homework` VALUES (771, '2018-11-22 14:38:50', 'Matrix Computations', '2015326601019_2.doc', '毛沐祺', '2015326601019', 2);
INSERT INTO `homework` VALUES (772, '2018-11-22 14:38:50', 'Matrix Computations', '2015326601078_2.docx', '吴章仪', '2015326601078', 2);
INSERT INTO `homework` VALUES (773, '2018-11-22 14:38:50', 'Matrix Computations', '2015326601078_1.doc', '吴章仪', '2015326601078', 1);
INSERT INTO `homework` VALUES (774, '2018-11-22 14:38:51', 'Matrix Computations', '2015326601078_5.zip', '吴章仪', '2015326601078', 5);
INSERT INTO `homework` VALUES (775, '2018-11-22 14:38:51', 'Matrix Computations', '2015326601078_3.zip', '吴章仪', '2015326601078', 3);
INSERT INTO `homework` VALUES (776, '2018-11-22 14:38:51', 'Matrix Computations', '2015326601078_4.zip', '吴章仪', '2015326601078', 4);
INSERT INTO `homework` VALUES (777, '2018-11-22 14:38:51', 'Matrix Computations', '2015326601079_1.doc', '徐杰', '2015326601079', 1);
INSERT INTO `homework` VALUES (778, '2018-11-22 14:38:51', 'Matrix Computations', '2015326601079_3.doc', '徐杰', '2015326601079', 3);
INSERT INTO `homework` VALUES (779, '2018-11-22 14:38:51', 'Matrix Computations', '2015326601079_2.doc', '徐杰', '2015326601079', 2);
INSERT INTO `homework` VALUES (780, '2018-11-22 14:38:52', 'Matrix Computations', '2015326601079_4.doc', '徐杰', '2015326601079', 4);
INSERT INTO `homework` VALUES (781, '2018-11-22 14:38:52', 'Matrix Computations', '2015326601079_5.docx', '徐杰', '2015326601079', 5);
INSERT INTO `homework` VALUES (782, '2018-11-22 14:38:52', 'Matrix Computations', '2015326601086_5.zip', '江倩', '2015326601086', 5);
INSERT INTO `homework` VALUES (783, '2018-11-22 14:38:52', 'Matrix Computations', '2015326601086_1.docx', '江倩', '2015326601086', 1);
INSERT INTO `homework` VALUES (784, '2018-11-22 14:38:53', 'Matrix Computations', '2015326601086_3.docx', '江倩', '2015326601086', 3);
INSERT INTO `homework` VALUES (785, '2018-11-22 14:38:53', 'Matrix Computations', '2015326601086_2.doc', '江倩', '2015326601086', 2);
INSERT INTO `homework` VALUES (786, '2018-11-22 14:38:53', 'Matrix Computations', '2015326601086_4.docx', '江倩', '2015326601086', 4);
INSERT INTO `homework` VALUES (787, '2018-11-22 14:38:53', 'Matrix Computations', '2015326601087_1.doc', '马芸', '2015326601087', 1);
INSERT INTO `homework` VALUES (788, '2018-11-22 14:38:53', 'Matrix Computations', '2015326601087_3.doc', '马芸', '2015326601087', 3);
INSERT INTO `homework` VALUES (789, '2018-11-22 14:38:53', 'Matrix Computations', '2015326601087_2.docx', '马芸', '2015326601087', 2);
INSERT INTO `homework` VALUES (790, '2018-11-22 14:38:54', 'Matrix Computations', '2015326601087_4.doc', '马芸', '2015326601087', 4);
INSERT INTO `homework` VALUES (791, '2018-11-22 14:38:54', 'Matrix Computations', '2015326601087_5.docx', '马芸', '2015326601087', 5);
INSERT INTO `homework` VALUES (792, '2018-11-22 14:38:54', 'Matrix Computations', '2015326601089_5.zip', '魏敏洁', '2015326601089', 5);
INSERT INTO `homework` VALUES (793, '2018-11-22 14:38:54', 'Matrix Computations', '2015326601089_1.doc', '魏敏洁', '2015326601089', 1);
INSERT INTO `homework` VALUES (794, '2018-11-22 14:38:54', 'Matrix Computations', '2015326601089_3.docx', '魏敏洁', '2015326601089', 3);
INSERT INTO `homework` VALUES (795, '2018-11-22 14:38:55', 'Matrix Computations', '2015326601089_2.docx', '魏敏洁', '2015326601089', 2);
INSERT INTO `homework` VALUES (796, '2018-11-22 14:38:55', 'Matrix Computations', '2015326601089_4.docx', '魏敏洁', '2015326601089', 4);
INSERT INTO `homework` VALUES (797, '2018-11-22 14:38:55', 'Matrix Computations', '2015326601090_5.doc', '谢逢彬', '2015326601090', 5);
INSERT INTO `homework` VALUES (798, '2018-11-22 14:38:55', 'Matrix Computations', '2015326601090_1.doc', '谢逢彬', '2015326601090', 1);
INSERT INTO `homework` VALUES (799, '2018-11-22 14:38:56', 'Matrix Computations', '2015326601090_3.doc', '谢逢彬', '2015326601090', 3);
INSERT INTO `homework` VALUES (800, '2018-11-22 14:38:56', 'Matrix Computations', '2015326601090_2.doc', '谢逢彬', '2015326601090', 2);
INSERT INTO `homework` VALUES (801, '2018-11-22 14:38:56', 'Matrix Computations', '2015326601090_4.doc', '谢逢彬', '2015326601090', 4);
INSERT INTO `homework` VALUES (802, '2018-11-22 14:38:56', 'Matrix Computations', '2015326601092_5.docx', '张宁', '2015326601092', 5);
INSERT INTO `homework` VALUES (803, '2018-11-22 14:38:56', 'Matrix Computations', '2015326601092_5.doc', '张宁', '2015326601092', 5);
INSERT INTO `homework` VALUES (804, '2018-11-22 14:38:57', 'Matrix Computations', '2015326601100_5.zip', '林鸿杰', '2015326601100', 5);
INSERT INTO `homework` VALUES (806, '2018-11-22 14:38:57', 'Matrix Computations', '2015326601100_1.doc', '林鸿杰', '2015326601100', 1);
INSERT INTO `homework` VALUES (807, '2018-11-22 14:38:57', 'Matrix Computations', '2015326601100_3.zip', '林鸿杰', '2015326601100', 3);
INSERT INTO `homework` VALUES (808, '2018-11-22 14:38:58', 'Matrix Computations', '2015326601100_2.doc', '林鸿杰', '2015326601100', 2);
INSERT INTO `homework` VALUES (809, '2018-11-22 14:38:58', 'Matrix Computations', '2015326601101_1.doc', '吕扬才', '2015326601101', 1);
INSERT INTO `homework` VALUES (810, '2018-11-22 14:38:58', 'Matrix Computations', '2015326601101_3.doc', '吕扬才', '2015326601101', 3);
INSERT INTO `homework` VALUES (811, '2018-11-22 14:38:58', 'Matrix Computations', '2015326601101_2.doc', '吕扬才', '2015326601101', 2);
INSERT INTO `homework` VALUES (812, '2018-11-22 14:38:59', 'Matrix Computations', '2015326601101_4.doc', '吕扬才', '2015326601101', 4);
INSERT INTO `homework` VALUES (813, '2018-11-22 14:38:59', 'Matrix Computations', '2015326601101_5.doc', '吕扬才', '2015326601101', 5);
INSERT INTO `homework` VALUES (814, '2018-11-22 14:38:59', 'Matrix Computations', '2015326601105_1.docx', '杨茂强', '2015326601105', 1);
INSERT INTO `homework` VALUES (815, '2018-11-22 14:38:59', 'Matrix Computations', '2015326601105_3.zip', '杨茂强', '2015326601105', 3);
INSERT INTO `homework` VALUES (816, '2018-11-22 14:39:00', 'Matrix Computations', '2015326601105_2.doc', '杨茂强', '2015326601105', 2);
INSERT INTO `homework` VALUES (817, '2018-11-22 14:39:00', 'Matrix Computations', '2015326601105_4.doc', '杨茂强', '2015326601105', 4);
INSERT INTO `homework` VALUES (818, '2018-11-22 14:39:00', 'Matrix Computations', '2015326601105_5.doc', '杨茂强', '2015326601105', 5);
INSERT INTO `homework` VALUES (819, '2018-11-22 14:39:00', 'Matrix Computations', '2015326690046_1.doc', '张瀚霖', '2015326690046', 1);
INSERT INTO `homework` VALUES (820, '2018-11-22 14:39:00', 'Matrix Computations', '2015326690046_3.doc', '张瀚霖', '2015326690046', 3);
INSERT INTO `homework` VALUES (821, '2018-11-22 14:39:00', 'Matrix Computations', '2015326690046_2.doc', '张瀚霖', '2015326690046', 2);
INSERT INTO `homework` VALUES (822, '2018-11-22 14:39:01', 'Matrix Computations', '2015326690046_4.doc', '张瀚霖', '2015326690046', 4);
INSERT INTO `homework` VALUES (823, '2018-11-22 14:39:01', 'Matrix Computations', '2015326690046_5.doc', '张瀚霖', '2015326690046', 5);
INSERT INTO `homework` VALUES (824, '2018-11-22 14:39:01', 'Matrix Computations', '2016426630001_1.doc', '葛钰莹', '2016426630001', 1);
INSERT INTO `homework` VALUES (825, '2018-11-22 14:39:02', 'Matrix Computations', '2016426630001_3.doc', '葛钰莹', '2016426630001', 3);
INSERT INTO `homework` VALUES (826, '2018-11-22 14:39:02', 'Matrix Computations', '2016426630001_2.doc', '葛钰莹', '2016426630001', 2);
INSERT INTO `homework` VALUES (827, '2018-11-22 14:39:02', 'Matrix Computations', '2016426630001_4.doc', '葛钰莹', '2016426630001', 4);
INSERT INTO `homework` VALUES (828, '2018-11-22 14:39:02', 'Matrix Computations', '2016426630001_5.docx', '葛钰莹', '2016426630001', 5);
INSERT INTO `homework` VALUES (829, '2018-11-22 14:39:03', 'Matrix Computations', '2016426630002_1.doc', '鲁佳琪', '2016426630002', 1);
INSERT INTO `homework` VALUES (830, '2018-11-22 14:39:03', 'Matrix Computations', '2016426630002_3.doc', '鲁佳琪', '2016426630002', 3);
INSERT INTO `homework` VALUES (831, '2018-11-22 14:39:03', 'Matrix Computations', '2016426630002_2.doc', '鲁佳琪', '2016426630002', 2);
INSERT INTO `homework` VALUES (832, '2018-11-22 14:39:03', 'Matrix Computations', '2016426630002_4.docx', '鲁佳琪', '2016426630002', 4);
INSERT INTO `homework` VALUES (833, '2018-11-22 14:39:04', 'Matrix Computations', '2016426630002_5.docx', '鲁佳琪', '2016426630002', 5);
INSERT INTO `homework` VALUES (834, '2018-11-22 14:39:04', 'Matrix Computations', '2016426630003_5.doc', '陆映玲', '2016426630003', 5);
INSERT INTO `homework` VALUES (835, '2018-11-22 14:39:04', 'Matrix Computations', '2016426630003_1.doc', '陆映玲', '2016426630003', 1);
INSERT INTO `homework` VALUES (836, '2018-11-22 14:39:04', 'Matrix Computations', '2016426630003_3.doc', '陆映玲', '2016426630003', 3);
INSERT INTO `homework` VALUES (837, '2018-11-22 14:39:04', 'Matrix Computations', '2016426630003_2.doc', '陆映玲', '2016426630003', 2);
INSERT INTO `homework` VALUES (838, '2018-11-22 14:39:05', 'Matrix Computations', '2016426630003_4.doc', '陆映玲', '2016426630003', 4);
INSERT INTO `homework` VALUES (839, '2018-11-22 14:39:05', 'Matrix Computations', '2016426630004_1.doc', '王依宁', '2016426630004', 1);
INSERT INTO `homework` VALUES (840, '2018-11-22 14:39:05', 'Matrix Computations', '2016426630004_3.doc', '王依宁', '2016426630004', 3);
INSERT INTO `homework` VALUES (841, '2018-11-22 14:39:05', 'Matrix Computations', '2016426630004_2.doc', '王依宁', '2016426630004', 2);
INSERT INTO `homework` VALUES (842, '2018-11-22 14:39:05', 'Matrix Computations', '2016426630004_4.doc', '王依宁', '2016426630004', 4);
INSERT INTO `homework` VALUES (843, '2018-11-22 14:39:06', 'Matrix Computations', '2016426630004_5.doc', '王依宁', '2016426630004', 5);
INSERT INTO `homework` VALUES (844, '2018-11-22 14:39:06', 'Matrix Computations', '2016426630005_5.docx', '朱琴', '2016426630005', 5);
INSERT INTO `homework` VALUES (845, '2018-11-22 14:39:06', 'Matrix Computations', '2016426630005_1.doc', '朱琴', '2016426630005', 1);
INSERT INTO `homework` VALUES (846, '2018-11-22 14:39:06', 'Matrix Computations', '2016426630005_3.doc', '朱琴', '2016426630005', 3);
INSERT INTO `homework` VALUES (847, '2018-11-22 14:39:07', 'Matrix Computations', '2016426630005_2.doc', '朱琴', '2016426630005', 2);
INSERT INTO `homework` VALUES (848, '2018-11-22 14:39:07', 'Matrix Computations', '2016426630005_4.doc', '朱琴', '2016426630005', 4);
INSERT INTO `homework` VALUES (849, '2018-11-22 14:39:07', 'Matrix Computations', '2016426630006_1.doc', '徐侃', '2016426630006', 1);
INSERT INTO `homework` VALUES (850, '2018-11-22 14:39:07', 'Matrix Computations', '2016426630006_3.doc', '徐侃', '2016426630006', 3);
INSERT INTO `homework` VALUES (851, '2018-11-22 14:39:08', 'Matrix Computations', '2016426630006_2.doc', '徐侃', '2016426630006', 2);
INSERT INTO `homework` VALUES (852, '2018-11-22 14:39:08', 'Matrix Computations', '2016426630006_4.doc', '徐侃', '2016426630006', 4);
INSERT INTO `homework` VALUES (853, '2018-11-22 14:39:08', 'Matrix Computations', '2016426630006_5.docx', '徐侃', '2016426630006', 5);
INSERT INTO `homework` VALUES (854, '2018-11-22 14:39:08', 'Matrix Computations', '2017426630001_5.docx', '杨俊燕', '2017426630001', 5);
INSERT INTO `homework` VALUES (855, '2018-11-22 14:39:08', 'Matrix Computations', '2017426630001_1.doc', '杨俊燕', '2017426630001', 1);
INSERT INTO `homework` VALUES (856, '2018-11-22 14:39:08', 'Matrix Computations', '2017426630001_3.doc', '杨俊燕', '2017426630001', 3);
INSERT INTO `homework` VALUES (857, '2018-11-22 14:39:09', 'Matrix Computations', '2017426630001_2.doc', '杨俊燕', '2017426630001', 2);
INSERT INTO `homework` VALUES (858, '2018-11-22 14:39:09', 'Matrix Computations', '2017426630001_4.docx', '杨俊燕', '2017426630001', 4);
INSERT INTO `homework` VALUES (859, '2018-11-22 14:39:09', 'Matrix Computations', '2017426630004_1.doc', '舒轶', '2017426630004', 1);
INSERT INTO `homework` VALUES (860, '2018-11-22 14:39:09', 'Matrix Computations', '2017426630004_3.doc', '舒轶', '2017426630004', 3);
INSERT INTO `homework` VALUES (861, '2018-11-22 14:39:09', 'Matrix Computations', '2017426630004_2.doc', '舒轶', '2017426630004', 2);
INSERT INTO `homework` VALUES (862, '2018-11-22 14:39:10', 'Matrix Computations', '2017426630004_4.doc', '舒轶', '2017426630004', 4);
INSERT INTO `homework` VALUES (863, '2018-11-22 14:39:10', 'Matrix Computations', '2017426630004_5.doc', '舒轶', '2017426630004', 5);
INSERT INTO `homework` VALUES (961, '2018-11-22 15:07:48', 'Matrix Computations', '2015326601078_6.pdf', '吴章仪', '2015326601078', 6);
INSERT INTO `homework` VALUES (962, '2018-11-22 15:07:48', 'Matrix Computations', '2015326601079_6.pdf', '徐杰', '2015326601079', 6);
INSERT INTO `homework` VALUES (963, '2018-11-22 15:07:49', 'Matrix Computations', '2015326601100_6.pdf', '林鸿杰', '2015326601100', 6);
INSERT INTO `homework` VALUES (964, '2018-11-22 15:07:49', 'Matrix Computations', '2015326601101_6.pdf', '吕扬才', '2015326601101', 6);
INSERT INTO `homework` VALUES (965, '2018-11-22 15:07:49', 'Matrix Computations', '2015326601105_6.pdf', '杨茂强', '2015326601105', 6);
INSERT INTO `homework` VALUES (966, '2018-11-22 15:07:49', 'Matrix Computations', '2015326690046_6.doc', '张瀚霖', '2015326690046', 6);
INSERT INTO `homework` VALUES (967, '2018-11-22 15:07:49', 'Matrix Computations', '2016426630001_6.pdf', '葛钰莹', '2016426630001', 6);
INSERT INTO `homework` VALUES (968, '2018-11-22 15:07:49', 'Matrix Computations', '2016426630002_6.pdf', '鲁佳琪', '2016426630002', 6);
INSERT INTO `homework` VALUES (969, '2018-11-22 15:07:50', 'Matrix Computations', '2016426630003_6.pdf', '陆映玲', '2016426630003', 6);
INSERT INTO `homework` VALUES (970, '2018-11-22 15:07:50', 'Matrix Computations', '2016426630004_6.pdf', '王依宁', '2016426630004', 6);
INSERT INTO `homework` VALUES (971, '2018-11-22 15:07:50', 'Matrix Computations', '2016426630005_6.pdf', '朱琴', '2016426630005', 6);
INSERT INTO `homework` VALUES (972, '2018-11-22 15:07:50', 'Matrix Computations', '2017426630001_6.pdf', '杨俊燕', '2017426630001', 6);
INSERT INTO `homework` VALUES (973, '2018-11-22 15:07:50', 'Matrix Computations', '2017426630004_6.pdf', '舒轶', '2017426630004', 6);
INSERT INTO `homework` VALUES (974, '2018-11-22 15:07:50', 'Matrix Computations', '2015326601092_1.docx', '张宁', '2015326601092', 1);
INSERT INTO `homework` VALUES (975, '2018-11-22 15:07:50', 'Matrix Computations', '2015326601092_2.docx', '张宁', '2015326601092', 2);
INSERT INTO `homework` VALUES (976, '2018-11-22 15:07:50', 'Matrix Computations', '2015326601092_3.docx', '张宁', '2015326601092', 3);
INSERT INTO `homework` VALUES (977, '2018-11-22 15:07:50', 'Matrix Computations', '2015326601092_4.docx', '张宁', '2015326601092', 4);
INSERT INTO `homework` VALUES (978, '2018-11-22 15:07:50', 'Matrix Computations', '2015326601100_4.doc', '林鸿杰', '2015326601100', 4);

-- ----------------------------
-- Table structure for notice_info
-- ----------------------------
DROP TABLE IF EXISTS `notice_info`;
CREATE TABLE `notice_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `send_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发送方',
  `insert_time` datetime(0) NULL DEFAULT NULL COMMENT '插入时间',
  `read_status` int(1) NULL DEFAULT NULL COMMENT '已读/未读状态',
  `use_status` int(1) NULL DEFAULT NULL COMMENT '使用状态（使用中，已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单en',
  `title` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单cn',
  `jump` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'url',
  `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  `parent` int(11) NULL DEFAULT NULL COMMENT '父菜单id',
  `is_parent` int(1) NULL DEFAULT NULL COMMENT '是否是父菜单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (1, 'courseHome', '课程主页', '/course', 'layui-icon-home', 1, 0, 1);
INSERT INTO `resource` VALUES (2, 'courseMaterials', '课程材料', '/course/materialView', 'layui-icon-template-1', 1, 0, 1);
INSERT INTO `resource` VALUES (3, 'homeworkManager', '作业管理', '/homework', 'layui-icon-read', 1, 0, 1);
INSERT INTO `resource` VALUES (4, 'courseManager', '课程管理', '/course/managerView', 'layui-icon-list', 1, 0, 1);
INSERT INTO `resource` VALUES (5, 'courseExange', '课程交流', '/course/exangeView', 'layui-icon-dialogue', 1, 0, 1);
INSERT INTO `resource` VALUES (6, 'introduction', '介绍', '/courseHome/course_intro', NULL, 1, 1, 0);
INSERT INTO `resource` VALUES (7, 'instructors', '教师', '/courseHome/course_teacher', NULL, 1, 1, 0);
INSERT INTO `resource` VALUES (8, 'syllabus', '教学大纲', '/courseHome/syllabus', NULL, 1, 1, 0);
INSERT INTO `resource` VALUES (9, 'resource', '课程附件', '/courseMaterials/resource', NULL, 1, 2, 0);
INSERT INTO `resource` VALUES (10, 'exercises', '历年习题', '/courseMaterials/exercises', NULL, 1, 2, 0);
INSERT INTO `resource` VALUES (11, 'video', '课程音频', '/courseMaterials/videos', NULL, 1, 2, 0);
INSERT INTO `resource` VALUES (12, 'submit', '提交作业', 'homeworkManager/student/homework_list', NULL, 1, 3, 0);
INSERT INTO `resource` VALUES (13, 'studentRecord', '提交记录', 'homeworkManager/student/submit_record', NULL, 1, 3, 0);
INSERT INTO `resource` VALUES (14, 'startClass', '新建课程', 'courseManager/teacher/createNewCourse', NULL, 1, 4, 0);
INSERT INTO `resource` VALUES (15, 'newHomework', '新建作业', 'homeworkManager/teacher/new_homework', NULL, 1, 3, 0);
INSERT INTO `resource` VALUES (16, 'teacherRecord', '作业记录', 'homeworkManager/teacher/record', NULL, 1, 3, 0);
INSERT INTO `resource` VALUES (17, 'question', '问题交流', '', NULL, 1, 5, 0);
INSERT INTO `resource` VALUES (18, 'discuss', '课后讨论', '/index', NULL, 1, 5, 0);
INSERT INTO `resource` VALUES (19, 'editHomework', '编辑作业', 'homeworkManager/teacher/edit_homework', NULL, 1, 3, 0);

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource`  (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`, `resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES (0, 1);
INSERT INTO `role_resource` VALUES (0, 2);
INSERT INTO `role_resource` VALUES (0, 3);
INSERT INTO `role_resource` VALUES (0, 4);
INSERT INTO `role_resource` VALUES (0, 5);
INSERT INTO `role_resource` VALUES (0, 6);
INSERT INTO `role_resource` VALUES (0, 7);
INSERT INTO `role_resource` VALUES (0, 8);
INSERT INTO `role_resource` VALUES (0, 9);
INSERT INTO `role_resource` VALUES (0, 10);
INSERT INTO `role_resource` VALUES (0, 11);
INSERT INTO `role_resource` VALUES (0, 12);
INSERT INTO `role_resource` VALUES (0, 13);
INSERT INTO `role_resource` VALUES (0, 17);
INSERT INTO `role_resource` VALUES (0, 18);
INSERT INTO `role_resource` VALUES (1, 1);
INSERT INTO `role_resource` VALUES (1, 2);
INSERT INTO `role_resource` VALUES (1, 3);
INSERT INTO `role_resource` VALUES (1, 4);
INSERT INTO `role_resource` VALUES (1, 5);
INSERT INTO `role_resource` VALUES (1, 6);
INSERT INTO `role_resource` VALUES (1, 7);
INSERT INTO `role_resource` VALUES (1, 8);
INSERT INTO `role_resource` VALUES (1, 9);
INSERT INTO `role_resource` VALUES (1, 10);
INSERT INTO `role_resource` VALUES (1, 11);
INSERT INTO `role_resource` VALUES (1, 14);
INSERT INTO `role_resource` VALUES (1, 15);
INSERT INTO `role_resource` VALUES (1, 16);
INSERT INTO `role_resource` VALUES (1, 17);
INSERT INTO `role_resource` VALUES (1, 18);
INSERT INTO `role_resource` VALUES (1, 19);

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_course
-- ----------------------------
INSERT INTO `student_course` VALUES (1, '2fc91f7bcb2f42be8b40e1abb1e951680', 1);
INSERT INTO `student_course` VALUES (2, '2fc91f7bcb2f42be8b40e1abb1e951680', 2);
INSERT INTO `student_course` VALUES (3, '2fc91f7bcb2f42be8b40e1abb1e951680', 3);
INSERT INTO `student_course` VALUES (4, '2fc91f7bcb2f42be8b40e1abb1e951680', 4);
INSERT INTO `student_course` VALUES (5, '2fc91f7bcb2f42be8b40e1abb1e951680', 5);
INSERT INTO `student_course` VALUES (8, '2fc91f7bcb2f42be8b40e1abb1e951682', 1);
INSERT INTO `student_course` VALUES (9, '2fc91f7bcb2f42be8b40e1abb1e951681', 1);
INSERT INTO `student_course` VALUES (10, '2fc91f7bcb2f42be8b40e1abb1e951683', 1);
INSERT INTO `student_course` VALUES (11, '2fc91f7bcb2f42be8b40e1abb1e951684', 1);

-- ----------------------------
-- Table structure for student_exam_info
-- ----------------------------
DROP TABLE IF EXISTS `student_exam_info`;
CREATE TABLE `student_exam_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生id',
  `exam_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程作业id',
  `submit_type` int(1) NULL DEFAULT NULL COMMENT '提交种类(0:代码;1:文件)',
  `submit_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '代码内容 或 文件路径',
  `result` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '提交结果',
  `error` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误内容',
  `status` int(1) NULL DEFAULT NULL COMMENT '提交状态（0正常，1迟交，2未交，）',
  `submit_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `exam_id`(`exam_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_exam_info
-- ----------------------------
INSERT INTO `student_exam_info` VALUES (43, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, 'favicon.ico', 'delete', NULL, 0, '2018-12-22 10:48:50');
INSERT INTO `student_exam_info` VALUES (44, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, '培训计划导入模板.xlsx|', 'insert', NULL, 0, '2018-12-22 10:49:15');
INSERT INTO `student_exam_info` VALUES (45, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, '培训计划导入模板.xlsx', 'delete', NULL, 0, '2018-12-22 10:49:47');
INSERT INTO `student_exam_info` VALUES (46, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, 'logo.png', 'delete', NULL, 0, '2018-12-22 10:49:51');
INSERT INTO `student_exam_info` VALUES (47, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, '培训系统操作手册.docx|培训系统相关信息.txt|配置操作.txt|需求.docx|', 'insert', NULL, 0, '2018-12-22 10:49:56');
INSERT INTO `student_exam_info` VALUES (48, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, '培训系统相关信息.txt', 'delete', NULL, 0, '2018-12-22 10:51:49');
INSERT INTO `student_exam_info` VALUES (49, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, '培训系统相关信息.txt', 'delete', NULL, 0, '2018-12-22 10:51:55');
INSERT INTO `student_exam_info` VALUES (50, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, '培训系统操作手册.docx', 'delete', NULL, 0, '2018-12-22 10:51:58');
INSERT INTO `student_exam_info` VALUES (51, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, '需求.docx', 'delete', NULL, 0, '2018-12-22 10:52:08');
INSERT INTO `student_exam_info` VALUES (52, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, '配置操作.txt', 'delete', NULL, 0, '2018-12-22 10:52:10');
INSERT INTO `student_exam_info` VALUES (53, '2fc91f7bcb2f42be8b40e1abb1e951680', '1111', 1, 'favicon.ico|配置操作.txt|', 'insert', NULL, 0, '2018-12-22 10:52:15');
INSERT INTO `student_exam_info` VALUES (54, '2fc91f7bcb2f42be8b40e1abb1e951680', '111123', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', 'Accepted', NULL, 0, '2018-12-26 10:55:31');
INSERT INTO `student_exam_info` VALUES (55, '2fc91f7bcb2f42be8b40e1abb1e951680', '1112', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length;i++){\n		a += Integer.parseInt(numbers[i])\n	}\n	return a;\n}\n}', 'Compile Error', 'Code:[compiler.err.expected]\nKind:[ERROR]\nPosition:[142]\nStart Position:[142]\nEnd Position:[142]\nSource:[com.coursemanager.util.compilerutil.CharSequenceJavaFileObject[string:///Main.java]]\nMessage:[需要\';\']\nLineNumber:[5]\nColumnNumber:[50]\n', 0, '2018-12-26 14:20:32');
INSERT INTO `student_exam_info` VALUES (56, '2fc91f7bcb2f42be8b40e1abb1e951680', '1113', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length+1;i++){\n		a += Integer.parseInt(numbers[i])\n	}\n	return a;\n}\n}', 'Compile Error', 'LineNumber:5: 需要\';\'', 0, '2018-12-26 14:28:12');
INSERT INTO `student_exam_info` VALUES (57, '2fc91f7bcb2f42be8b40e1abb1e951680', '1113', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', 'Accepted', NULL, 0, '2018-12-26 14:28:36');
INSERT INTO `student_exam_info` VALUES (58, '2fc91f7bcb2f42be8b40e1abb1e951680', '1113', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length+1;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', 'Compile Error', NULL, 0, '2018-12-26 14:28:42');
INSERT INTO `student_exam_info` VALUES (59, '2fc91f7bcb2f42be8b40e1abb1e951680', '111123', 0, 'public class Main{\npublic int solution(String[] numbers){ \n	int a = 0;\n	for(int i=0;i<numbers.length+2;i++){\n		a += Integer.parseInt(numbers[i]);\n	}\n	return a;\n}\n}', 'Compile Error', 'java.lang.ArrayIndexOutOfBoundsException: 2', 0, '2018-12-26 14:57:06');
INSERT INTO `student_exam_info` VALUES (60, '2fc91f7bcb2f42be8b40e1abb1e951680', '1114', 0, 'asdasdasd', 'Compile Error', 'LineNumber:1: 解析时已到达文件结尾', 0, '2018-12-26 14:59:25');
INSERT INTO `student_exam_info` VALUES (61, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		Scanner scanner = new Scanner(System.in);\n        String line = scanner.nextLine();\n        String[] strs = line.split(\",\");\n        System.out.println(Arrays.stream(strs).mapToInt(Integer::parseInt).sum());\n        }\n    }\n', 'Accepted', NULL, 1, '2019-02-19 13:59:57');
INSERT INTO `student_exam_info` VALUES (62, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		Scanner scanner = new Scanner(System.in);\n        String line = scanner.nextLine();\n        String[] strs = line.split(\",\");\n        System.out.println(Arrays.stream(strs).mapToInt(Integer::parseInt).sum());\n        }\n    }\n', 'Accepted', NULL, 1, '2019-02-19 14:04:53');
INSERT INTO `student_exam_info` VALUES (63, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		Scanner scanner = new Scanner(System.in);\n        String line = scanner.nextLine();\n        String[] strs = line.split(\",\");\n        System.out.println(Arrays.stream(strs).mapToInt(Integer::parseInt).sum());\n        }\n    }\n', 'Accepted', NULL, 1, '2019-02-19 14:05:29');
INSERT INTO `student_exam_info` VALUES (64, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		Scanner scanner = new Scanner(System.in);\n        String line = scanner.nextLine();\n        String[] strs = line.split(\",\");\n        System.out.println(Arrays.stream(strs).mapToInt(Integer::parseInt).sum());\n        }\n    }\n', 'Accepted', NULL, 1, '2019-02-19 14:06:05');
INSERT INTO `student_exam_info` VALUES (65, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:23:44');
INSERT INTO `student_exam_info` VALUES (66, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:24:46');
INSERT INTO `student_exam_info` VALUES (67, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:26:47');
INSERT INTO `student_exam_info` VALUES (68, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:27:45');
INSERT INTO `student_exam_info` VALUES (69, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:28:47');
INSERT INTO `student_exam_info` VALUES (70, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:29:35');
INSERT INTO `student_exam_info` VALUES (71, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:31:11');
INSERT INTO `student_exam_info` VALUES (72, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:32:28');
INSERT INTO `student_exam_info` VALUES (73, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:35:19');
INSERT INTO `student_exam_info` VALUES (74, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:36:37');
INSERT INTO `student_exam_info` VALUES (75, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:38:48');
INSERT INTO `student_exam_info` VALUES (76, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:39:21');
INSERT INTO `student_exam_info` VALUES (77, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		\n        System.out.println(123);\n        }\n    }\n', 'Wrong Answer', NULL, 1, '2019-02-19 14:39:56');
INSERT INTO `student_exam_info` VALUES (78, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		Scanner scanner = new Scanner(System.in);\n        String line = scanner.nextLine();\n        String[] strs = line.split(\",\");\n        System.out.println(Arrays.stream(strs).mapToInt(Integer::parseInt).sum());\n        }\n    }', 'Accepted', NULL, 1, '2019-02-19 14:42:15');
INSERT INTO `student_exam_info` VALUES (79, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		Scanner scanner = new Scanner(System.in);\n        String line = scanner.nextLine();\n        String[] strs = line.split(\",\");\n        System.out.println(Arrays.stream(strs).mapToInt(Integer::parseInt).sum());\n        }\n    }', 'Accepted', NULL, 1, '2019-02-19 14:42:51');
INSERT INTO `student_exam_info` VALUES (80, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		Scanner scanner = new Scanner(System.in);\n        String line = scanner.nextLine();\n        String[] strs = line.split(\",\");\n        System.out.println(Arrays.stream(strs).mapToInt(Integer::parseInt).sum());\n        }\n    }', 'Accepted', NULL, 1, '2019-02-19 14:43:08');
INSERT INTO `student_exam_info` VALUES (81, '2fc91f7bcb2f42be8b40e1abb1e951680', '76471619', 0, 'import java.util.Arrays;\nimport java.util.Map;\nimport java.util.Scanner;\n    public class Main{\n        public static void main(String[] args) {\n		Scanner scanner = new Scanner(System.in);\n        String line = scanner.nextLine();\n        String[] strs = line.split(\",\");\n        System.out.println(Arrays.stream(strs).mapToInt(Integer::parseInt).sum());\n        }\n    }\n', 'Accepted', NULL, 1, '2019-02-19 14:43:35');

-- ----------------------------
-- Table structure for student_info
-- ----------------------------
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info`  (
  `student_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生id',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名（缺省为学号）',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `student_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `sex` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别（0：男；1：女）',
  `student_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学号',
  `grade` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '年级',
  `major` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `class_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级',
  `mobile` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `mail` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `school` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT ' 学校',
  `college` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学院',
  `department` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '系',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_info
-- ----------------------------
INSERT INTO `student_info` VALUES ('2fc91f7bcb2f42be8b40e1abb1e951680', '2015326601098', '580f15a8261f69e31fc6459dc31f91513e9467af98e3ff49', '李如豪', NULL, '2015326601098', NULL, NULL, NULL, NULL, '970877579@qq.com', NULL, NULL, NULL, NULL);
INSERT INTO `student_info` VALUES ('2fc91f7bcb2f42be8b40e1abb1e951681', '20153266010981', '580f15a8261f69e31fc6459dc31f91513e9467af98e3ff49', '李如豪1', NULL, '2015326601098', NULL, NULL, NULL, NULL, '970877579@qq.com', NULL, NULL, NULL, NULL);
INSERT INTO `student_info` VALUES ('2fc91f7bcb2f42be8b40e1abb1e951682', '20153266010982', '580f15a8261f69e31fc6459dc31f91513e9467af98e3ff49', '李如豪2', NULL, '2015326601098', NULL, NULL, NULL, NULL, '970877579@qq.com', NULL, NULL, NULL, NULL);
INSERT INTO `student_info` VALUES ('2fc91f7bcb2f42be8b40e1abb1e951683', '20153266010983', '580f15a8261f69e31fc6459dc31f91513e9467af98e3ff49', '李如豪3', NULL, '2015326601098', NULL, NULL, NULL, NULL, '970877579@qq.com', NULL, NULL, NULL, NULL);
INSERT INTO `student_info` VALUES ('2fc91f7bcb2f42be8b40e1abb1e951684', '20153266010984', '580f15a8261f69e31fc6459dc31f91513e9467af98e3ff49', '李如豪4', NULL, '2015326601098', NULL, NULL, NULL, NULL, '970877579@qq.com', NULL, NULL, NULL, NULL);
INSERT INTO `student_info` VALUES ('32acb241b1f04bbfb9e4ce3b30bf95180', '2015326601020', NULL, '李如豪', NULL, '2015326601020', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('34585f3baddc490d958825c4bbc7f60b0', '2015326601014', NULL, '李如豪', NULL, '2015326601014', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('34bcb8e8aa034667bccde5b1dce565ff0', '2015326601016', NULL, '李如豪', NULL, '2015326601016', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('34dbbd27ac334064b84682d90aa4d4150', '2015326601001', 'qwas74186', '李如豪', NULL, '2015326601001', NULL, NULL, '信息与计算科学', NULL, '837737876@qq.com', '浙江理工大学', '理学院', NULL, 1);
INSERT INTO `student_info` VALUES ('4b77070ef8a3442988142c8e078459540', '2015326601013', NULL, '李如豪', NULL, '2015326601013', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('51da021ac0ba4bfc98debb3ca7440d3c0', '2015326601007', NULL, '李如豪', NULL, '2015326601007', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('54d1f47f97114ae7895c2422d412795b0', '2015326601019', NULL, '李如豪', NULL, '2015326601019', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('5606036f6d9946039ceb5b61e87df3960', '2015326601012', NULL, '李如豪', NULL, '2015326601012', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('5c19e146e3d14241a6361985ff54544f0', '2015326601006', NULL, '李如豪', NULL, '2015326601006', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('5f5d7b8b5f614764aaf2eb33f9cf7a040', '2015326601008', NULL, '李如豪', NULL, '2015326601008', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('7eb4224fb752454fa2190b1756ac9d260', '2015326601015', NULL, '李如豪', NULL, '2015326601015', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('9a8d1511789b4afbb6400568c7f00f950', '2015326601009', NULL, '李如豪', NULL, '2015326601009', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('a5b72eda6fb946f3ab8652621ae2dbaa0', '2015326601010', NULL, '李如豪', NULL, '2015326601010', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('b07f929cbdd7481a8eddb8a1cbe739d80', '2015326601005', NULL, '李如豪', NULL, '2015326601005', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('bac4e1177f57438db123971fac320d540', '2015326601018', NULL, '李如豪', NULL, '2015326601018', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('c16118d30ad8417998f8d105276994960', '2015326601002', NULL, '李如豪', NULL, '2015326601002', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('c180d0abf7b44872baf17fb8d98462110', '2015326601004', NULL, '李如豪', NULL, '2015326601004', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('d33ca4d93e0a44aa8d143dd46d385e340', '2015326601003', NULL, '李如豪', NULL, '2015326601003', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('ebc71cad171841a89616447a5cf1d8be0', '2015326601017', NULL, '李如豪', NULL, '2015326601017', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);
INSERT INTO `student_info` VALUES ('f78b0e2c297440709a381acc600b7bdb0', '2015326601011', NULL, '李如豪', NULL, '2015326601011', NULL, NULL, '信息与计算科学', NULL, '000', '浙江理工大学', '理学院', NULL, NULL);

-- ----------------------------
-- Table structure for teacher_info
-- ----------------------------
DROP TABLE IF EXISTS `teacher_info`;
CREATE TABLE `teacher_info`  (
  `teacher_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教师id',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号（缺省为工号）',
  `teacher_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `teacher_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师姓名',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `resume_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简历url',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_info
-- ----------------------------
INSERT INTO `teacher_info` VALUES ('liruhao1', 'liruhao', '1', 'b20171e3c70bc1b381c4609e484407f9ab1902b91260a608', '陈海波', NULL, NULL, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'Admin', 'admin');

SET FOREIGN_KEY_CHECKS = 1;

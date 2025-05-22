/*
 Navicat Premium Data Transfer

 Source Server         : b2b-microservices-181
 Source Server Type    : PostgreSQL
 Source Server Version : 140017 (140017)
 Source Host           : 10.1.0.181:5432
 Source Catalog        : BIT_AUTONUMBER_SIT
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140017 (140017)
 File Encoding         : 65001

 Date: 18/03/2025 11:08:02
*/

DROP SEQUENCE IF EXISTS hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;


-- ----------------------------
-- Table structure for config_nextnumber
-- ----------------------------
DROP TABLE IF EXISTS "public"."config_nextnumber";
CREATE TABLE "public"."config_nextnumber" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_deleted" bool NOT NULL,
  "next_number" int4 NOT NULL
)
;
ALTER TABLE "public"."config_nextnumber" OWNER TO "postgres";

-- ----------------------------
-- Table structure for config_nextnumber_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."config_nextnumber_aud";
CREATE TABLE "public"."config_nextnumber_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_deleted" bool,
  "next_number" int4
)
;
ALTER TABLE "public"."config_nextnumber_aud" OWNER TO "postgres";

-- ----------------------------
-- Table structure for revinfo
-- ----------------------------
DROP TABLE IF EXISTS "public"."revinfo";
CREATE TABLE "public"."revinfo" (
  "rev" int4 NOT NULL,
  "revtstmp" int8
)
;
ALTER TABLE "public"."revinfo" OWNER TO "postgres";

-- ----------------------------
-- Table structure for trx_configautonumber
-- ----------------------------
DROP TABLE IF EXISTS "public"."trx_configautonumber";
CREATE TABLE "public"."trx_configautonumber" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "digit_penomoran" int4 NOT NULL,
  "end_date" timestamp(6),
  "field_string_1" varchar(255) COLLATE "pg_catalog"."default",
  "field_string_2" varchar(255) COLLATE "pg_catalog"."default",
  "field_string_3" varchar(255) COLLATE "pg_catalog"."default",
  "format_autonumber" jsonb NOT NULL,
  "format_code" jsonb NOT NULL,
  "is_has_rangenumber" bool NOT NULL,
  "is_all_branch" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "ms_branch_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_branch_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "number_end_to" varchar(255) COLLATE "pg_catalog"."default",
  "number_start_from" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "start_date" timestamp(6) NOT NULL,
  "transaction_event" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trx_configautonumber" OWNER TO "postgres";

-- ----------------------------
-- Table structure for trx_configautonumber_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."trx_configautonumber_aud";
CREATE TABLE "public"."trx_configautonumber_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "digit_penomoran" int4,
  "end_date" timestamp(6),
  "field_string_1" varchar(255) COLLATE "pg_catalog"."default",
  "field_string_2" varchar(255) COLLATE "pg_catalog"."default",
  "field_string_3" varchar(255) COLLATE "pg_catalog"."default",
  "format_autonumber" jsonb,
  "format_code" jsonb,
  "is_has_rangenumber" bool,
  "is_all_branch" bool,
  "is_deleted" bool,
  "ms_branch_code" varchar(255) COLLATE "pg_catalog"."default",
  "ms_branch_id" varchar(255) COLLATE "pg_catalog"."default",
  "number_end_to" varchar(255) COLLATE "pg_catalog"."default",
  "number_start_from" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "start_date" timestamp(6),
  "transaction_event" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."trx_configautonumber_aud" OWNER TO "postgres";

-- ----------------------------
-- Primary Key structure for table config_nextnumber
-- ----------------------------
ALTER TABLE "public"."config_nextnumber" ADD CONSTRAINT "config_nextnumber_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config_nextnumber_aud
-- ----------------------------
ALTER TABLE "public"."config_nextnumber_aud" ADD CONSTRAINT "config_nextnumber_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Primary Key structure for table revinfo
-- ----------------------------
ALTER TABLE "public"."revinfo" ADD CONSTRAINT "revinfo_pkey" PRIMARY KEY ("rev");

-- ----------------------------
-- Primary Key structure for table trx_configautonumber
-- ----------------------------
ALTER TABLE "public"."trx_configautonumber" ADD CONSTRAINT "trx_configautonumber_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table trx_configautonumber_aud
-- ----------------------------
ALTER TABLE "public"."trx_configautonumber_aud" ADD CONSTRAINT "trx_configautonumber_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Foreign Keys structure for table config_nextnumber_aud
-- ----------------------------
ALTER TABLE "public"."config_nextnumber_aud" ADD CONSTRAINT "fkjacjnt09mlbqulc902di6i5n0" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table trx_configautonumber_aud
-- ----------------------------
ALTER TABLE "public"."trx_configautonumber_aud" ADD CONSTRAINT "fkpw4fk8ch8f1hc8a9nwnfg8a0w" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

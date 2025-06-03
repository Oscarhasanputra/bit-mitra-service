

DROP SEQUENCE IF EXISTS hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;

-- ----------------------------
-- Table structure for revinfo
-- ----------------------------
DROP TABLE IF EXISTS "public"."revinfo" cascade;
CREATE TABLE "public"."revinfo" (
  "rev" int4 NOT NULL,
  "revtstmp" int8
)
;

-- ----------------------------
-- Table structure for ms_account
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_account" CASCADE;
CREATE TABLE "public"."ms_account" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_active" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for ms_account_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_account_aud" CASCADE;
CREATE TABLE "public"."ms_account_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_active" bool,
  "is_deleted" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for ms_bank
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_bank" CASCADE;
CREATE TABLE "public"."ms_bank" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "bi_code" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "country" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_active" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "swift_code" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for ms_bank_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_bank_aud" CASCADE;
CREATE TABLE "public"."ms_bank_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "bi_code" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "country" varchar(255) COLLATE "pg_catalog"."default",
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_active" bool,
  "is_deleted" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "swift_code" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for ms_city
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_city" CASCADE;
CREATE TABLE "public"."ms_city" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_active" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "province_code" varchar(255) COLLATE "pg_catalog"."default",
  "province_name" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for ms_city_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_city_aud" CASCADE;
CREATE TABLE "public"."ms_city_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_active" bool,
  "is_deleted" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "province_code" varchar(255) COLLATE "pg_catalog"."default",
  "province_name" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for ms_country
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_country" CASCADE;
CREATE TABLE "public"."ms_country" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_active" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for ms_country_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_country_aud" CASCADE;
CREATE TABLE "public"."ms_country_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_active" bool,
  "is_deleted" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for ms_currency
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_currency" CASCADE;
CREATE TABLE "public"."ms_currency" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_active" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for ms_currency_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_currency_aud" CASCADE;
CREATE TABLE "public"."ms_currency_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_active" bool,
  "is_deleted" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for ms_mitra
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_mitra" CASCADE;
CREATE TABLE "public"."ms_mitra" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "bussiness_entity" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_active" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "ms_account_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_account_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "taxidentity_no" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "taxidentity_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for ms_mitra_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_mitra_aud" CASCADE;
CREATE TABLE "public"."ms_mitra_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "bussiness_entity" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_active" bool,
  "is_deleted" bool,
  "ms_account_code" varchar(255) COLLATE "pg_catalog"."default",
  "ms_account_id" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "taxidentity_no" varchar(255) COLLATE "pg_catalog"."default",
  "taxidentity_type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for ms_mitra_locationaddress
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_mitra_locationaddress" CASCADE;
CREATE TABLE "public"."ms_mitra_locationaddress" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "address" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "city" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "country" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "latitude" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "location_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "longitude" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "maxclass_vehicle" bool NOT NULL,
  "mobile_number" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_mitra_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_mitra_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_salesregion_id" bool NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "oddeven_area" bool NOT NULL,
  "province" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "village" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for ms_mitra_locationaddress_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_mitra_locationaddress_aud" CASCADE;
CREATE TABLE "public"."ms_mitra_locationaddress_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "address" varchar(255) COLLATE "pg_catalog"."default",
  "city" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "country" varchar(255) COLLATE "pg_catalog"."default",
  "latitude" varchar(255) COLLATE "pg_catalog"."default",
  "location_type" varchar(255) COLLATE "pg_catalog"."default",
  "longitude" varchar(255) COLLATE "pg_catalog"."default",
  "maxclass_vehicle" bool,
  "mobile_number" varchar(255) COLLATE "pg_catalog"."default",
  "ms_mitra_code" varchar(255) COLLATE "pg_catalog"."default",
  "ms_mitra_id" varchar(255) COLLATE "pg_catalog"."default",
  "ms_salesregion_id" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "oddeven_area" bool,
  "province" varchar(255) COLLATE "pg_catalog"."default",
  "village" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for ms_port
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_port" CASCADE;
CREATE TABLE "public"."ms_port" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "city" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "country" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_active" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "phone_number" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "pic" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for ms_port_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_port_aud" CASCADE;
CREATE TABLE "public"."ms_port_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "city" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "country" varchar(255) COLLATE "pg_catalog"."default",
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_active" bool,
  "is_deleted" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "phone_number" varchar(255) COLLATE "pg_catalog"."default",
  "pic" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for msc_owner
-- ----------------------------
DROP TABLE IF EXISTS "public"."msc_owner" CASCADE;
CREATE TABLE "public"."msc_owner" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "identity_issuer" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "identity_no" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "identity_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "mobile_number" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for msc_owner_aud
-- ----------------------------
DROP TABLE IF EXISTS "public"."msc_owner_aud" CASCADE;
CREATE TABLE "public"."msc_owner_aud" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "rev" int4 NOT NULL,
  "revtype" int2,
  "created_by" varchar(255) COLLATE "pg_catalog"."default",
  "created_date" timestamp(6),
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "identity_issuer" varchar(255) COLLATE "pg_catalog"."default",
  "identity_no" varchar(255) COLLATE "pg_catalog"."default",
  "identity_type" varchar(255) COLLATE "pg_catalog"."default",
  "mobile_number" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default"
)

-- ----------------------------
-- Table structure for revinfo
-- ----------------------------
--DROP TABLE IF EXISTS "public"."revinfo" CASCADE;
--CREATE TABLE "public"."revinfo" (
--  "rev" int4 NOT NULL,
--  "revtstmp" int8
--)
;

-- ----------------------------
-- Primary Key structure for table ms_account
-- ----------------------------
ALTER TABLE "public"."ms_account" ADD CONSTRAINT "ms_account_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ms_account_aud
-- ----------------------------
ALTER TABLE "public"."ms_account_aud" ADD CONSTRAINT "ms_account_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Primary Key structure for table ms_bank
-- ----------------------------
ALTER TABLE "public"."ms_bank" ADD CONSTRAINT "ms_bank_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ms_bank_aud
-- ----------------------------
ALTER TABLE "public"."ms_bank_aud" ADD CONSTRAINT "ms_bank_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Primary Key structure for table ms_city
-- ----------------------------
ALTER TABLE "public"."ms_city" ADD CONSTRAINT "ms_city_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ms_city_aud
-- ----------------------------
ALTER TABLE "public"."ms_city_aud" ADD CONSTRAINT "ms_city_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Primary Key structure for table ms_country
-- ----------------------------
ALTER TABLE "public"."ms_country" ADD CONSTRAINT "ms_country_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ms_country_aud
-- ----------------------------
ALTER TABLE "public"."ms_country_aud" ADD CONSTRAINT "ms_country_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Primary Key structure for table ms_currency
-- ----------------------------
ALTER TABLE "public"."ms_currency" ADD CONSTRAINT "ms_currency_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ms_currency_aud
-- ----------------------------
ALTER TABLE "public"."ms_currency_aud" ADD CONSTRAINT "ms_currency_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Primary Key structure for table ms_mitra
-- ----------------------------
ALTER TABLE "public"."ms_mitra" ADD CONSTRAINT "ms_mitra_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ms_mitra_aud
-- ----------------------------
ALTER TABLE "public"."ms_mitra_aud" ADD CONSTRAINT "ms_mitra_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Primary Key structure for table ms_mitra_locationaddress
-- ----------------------------
ALTER TABLE "public"."ms_mitra_locationaddress" ADD CONSTRAINT "ms_mitra_locationaddress_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ms_mitra_locationaddress_aud
-- ----------------------------
ALTER TABLE "public"."ms_mitra_locationaddress_aud" ADD CONSTRAINT "ms_mitra_locationaddress_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Checks structure for table ms_port
-- ----------------------------
ALTER TABLE "public"."ms_port" ADD CONSTRAINT "ms_port_type_check" CHECK (type::text = ANY (ARRAY['SEAPORT'::character varying, 'AIRPORT'::character varying, 'INLANDPORT'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table ms_port
-- ----------------------------
ALTER TABLE "public"."ms_port" ADD CONSTRAINT "ms_port_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table ms_port_aud
-- ----------------------------
ALTER TABLE "public"."ms_port_aud" ADD CONSTRAINT "ms_port_aud_type_check" CHECK (type::text = ANY (ARRAY['SEAPORT'::character varying, 'AIRPORT'::character varying, 'INLANDPORT'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table ms_port_aud
-- ----------------------------
ALTER TABLE "public"."ms_port_aud" ADD CONSTRAINT "ms_port_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Primary Key structure for table msc_owner
-- ----------------------------
ALTER TABLE "public"."msc_owner" ADD CONSTRAINT "msc_owner_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table msc_owner_aud
-- ----------------------------
ALTER TABLE "public"."msc_owner_aud" ADD CONSTRAINT "msc_owner_aud_pkey" PRIMARY KEY ("rev", "id");

-- ----------------------------
-- Primary Key structure for table revinfo
-- ----------------------------
ALTER TABLE "public"."revinfo" ADD CONSTRAINT "revinfo_pkey" PRIMARY KEY ("rev");

-- ----------------------------
-- Foreign Keys structure for table ms_account_aud
-- ----------------------------
ALTER TABLE "public"."ms_account_aud" ADD CONSTRAINT "fkov86paaajwdv33oatv0bvukt0" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table ms_bank_aud
-- ----------------------------
ALTER TABLE "public"."ms_bank_aud" ADD CONSTRAINT "fkxmpixgf1l2hrw5k2wtkmg898" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table ms_city_aud
-- ----------------------------
ALTER TABLE "public"."ms_city_aud" ADD CONSTRAINT "fkbfe4icl63ccmp6wyfl0vryeml" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table ms_country_aud
-- ----------------------------
ALTER TABLE "public"."ms_country_aud" ADD CONSTRAINT "fk43ljqjrianlhit473awjyhd70" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table ms_currency_aud
-- ----------------------------
ALTER TABLE "public"."ms_currency_aud" ADD CONSTRAINT "fknxrfvuhsce53hecoax2r5mh79" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table ms_mitra_aud
-- ----------------------------
ALTER TABLE "public"."ms_mitra_aud" ADD CONSTRAINT "fk7qa7un42sotcxe198iavd1mpp" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table ms_mitra_locationaddress_aud
-- ----------------------------
ALTER TABLE "public"."ms_mitra_locationaddress_aud" ADD CONSTRAINT "fksgfp71c83aha7ssixxhpu6a0c" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table ms_port_aud
-- ----------------------------
ALTER TABLE "public"."ms_port_aud" ADD CONSTRAINT "fkjt23b1w05gqp9i7buhg845ike" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table msc_owner_aud
-- ----------------------------
ALTER TABLE "public"."msc_owner_aud" ADD CONSTRAINT "fkcldg83lkp4c114t9v0mjd2lfv" FOREIGN KEY ("rev") REFERENCES "public"."revinfo" ("rev") ON DELETE NO ACTION ON UPDATE NO ACTION;

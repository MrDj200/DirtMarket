CREATE TABLE IF NOT EXISTS `market`
(
 `id`       int NOT NULL AUTO_INCREMENT,
 `x`        int NOT NULL ,
 `z`        int NOT NULL ,
 `dim`      char(36) NOT NULL ,
 `plyUUID`  char(36) ,
 `claimed`  varchar(255) NOT NULL ,

PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `market_history`
(
 `plyUUID`          char(36) NOT NULL ,
 `claimed`          varchar(255) NOT NULL ,
 `unclaimed`        varchar(255) NOT NULL ,
 `fk_market_id`     int NOT NULL
);

ALTER TABLE market_history ADD FOREIGN KEY (fk_market_id) REFERENCES market(id);


CREATE TABLE IF NOT EXISTS `market`
(
 `id`      int NOT NULL AUTO_INCREMENT,
 `x`       int NOT NULL ,
 `y`       int NOT NULL ,
 `dim`     int NOT NULL ,
 `plyUUID` char(36) NOT NULL ,
 `claimed` datetime NOT NULL ,

PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `market_history`
(
 `plyUUID`   char(36) NOT NULL ,
 `claimed`   datetime NOT NULL ,
 `unclaimed` datetime NOT NULL ,
 `fk_market_id`        int NOT NULL
);

ALTER TABLE market_history ADD FOREIGN KEY (fk_market_id) REFERENCES market(id);
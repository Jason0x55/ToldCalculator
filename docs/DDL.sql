CREATE TABLE IF NOT EXISTS `Aircraft`
 (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 `name` TEXT NOT NULL COLLATE NOCASE,
 `aircraftType` TEXT NOT NULL,
 `basicEmptyWeight` INTEGER NOT NULL)


CREATE TABLE IF NOT EXISTS `Airport`
 (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 `name` TEXT NOT NULL,
 `ICAO_ID` TEXT COLLATE NOCASE,
 `ident` TEXT, `elevation` INTEGER NOT NULL)


CREATE TABLE IF NOT EXISTS `Runway`
 (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 `airportId` INTEGER NOT NULL,
 `runwayId` TEXT NOT NULL,
 `length` INTEGER NOT NULL,
 `width` INTEGER NOT NULL,
  FOREIGN KEY(`airportId`) REFERENCES `Airport`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )

CREATE  INDEX `index_Runway_airportId` ON `Runway` (`airportId`)


CREATE TABLE IF NOT EXISTS `User`
 (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 `name` TEXT NOT NULL COLLATE NOCASE,
 `aircraftId` INTEGER,
 `airportId` INTEGER,
  FOREIGN KEY(`airportId`) REFERENCES `Airport`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION ,
  FOREIGN KEY(`aircraftId`) REFERENCES `Aircraft`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )

CREATE  INDEX `index_User_airportId` ON `User` (`airportId`)
CREATE  INDEX `index_User_aircraftId` ON `User` (`aircraftId`)


CREATE TABLE IF NOT EXISTS `Weather`
 (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 `airportId` INTEGER NOT NULL,
 `temperature` INTEGER,
 `dewpoint` INTEGER,
 `winds` TEXT,
 `pressure` INTEGER,
 `visibility` TEXT,
 `clouds` TEXT,
 `weatherType` TEXT,
 `rawText` TEXT,
 `timestamp` INTEGER,
  FOREIGN KEY(`airportId`) REFERENCES `Airport`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )

CREATE  INDEX `index_Weather_airportId` ON `Weather` (`airportId`)


CREATE TABLE IF NOT EXISTS `TakeoffPowerN1`
 (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 `aircraftId` INTEGER NOT NULL,
 `temperature` INTEGER NOT NULL,
 `altitude` INTEGER NOT NULL,
 `takeoffPowerN1` REAL NOT NULL,
  FOREIGN KEY(`aircraftId`) REFERENCES `Aircraft`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )

CREATE UNIQUE INDEX `index_TakeoffPowerN1_temperature_altitude` ON `TakeoffPowerN1` (`temperature`, `altitude`)
CREATE  INDEX `index_TakeoffPowerN1_aircraftId` ON `TakeoffPowerN1` (`aircraftId`)


CREATE TABLE IF NOT EXISTS `TakeoffData`
 (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 `aircraftId` INTEGER NOT NULL,
 `altitude` INTEGER NOT NULL,
 `weight` INTEGER NOT NULL,
 `temperature` INTEGER NOT NULL,
 `takeoffDistance` INTEGER,
 `takeoffSpeedV1` INTEGER,
 `takeoffSpeedVR` INTEGER,
 `takeoffSpeedV2` INTEGER,
  FOREIGN KEY(`aircraftId`) REFERENCES `Aircraft`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )

CREATE UNIQUE INDEX `index_TakeoffData_altitude_weight_temperature` ON `TakeoffData` (`altitude`, `weight`, `temperature`)
CREATE  INDEX `index_TakeoffData_aircraftId` ON `TakeoffData` (`aircraftId`)


CREATE TABLE IF NOT EXISTS `SavedTakeoffData`
 (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 `userId` INTEGER NOT NULL,
 `timestamp` INTEGER NOT NULL,
 `aircraftName` TEXT NOT NULL,
 `airportId` TEXT NOT NULL,
 `runwayRequired` INTEGER NOT NULL,
 `takeoffN1` REAL NOT NULL,
 `takeoffV1` INTEGER NOT NULL,
 `takeoffVR` INTEGER NOT NULL,
 `takeoffV2` INTEGER NOT NULL,
 `takeoffWeight` INTEGER NOT NULL,
 `weatherRawText` TEXT,
  FOREIGN KEY(`userId`) REFERENCES `User`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )

CREATE  INDEX `index_SavedTakeoffData_userId` ON `SavedTakeoffData` (`userId`)


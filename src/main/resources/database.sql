DROP TABLE IF EXISTS USER CASCADE;
CREATE TABLE IF NOT EXISTS USER (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    age INTEGER
);

DROP TABLE IF EXISTS ART_BOOK CASCADE;
CREATE TABLE IF NOT EXISTS ART_BOOK (
    id BIGINT NOT NULL PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    numberOfPages INTEGER,
    ageRestriction INTEGER,
    typeOfBook VARCHAR(255),
    genre VARCHAR(255),
    comics INTEGER
);

DROP TABLE IF EXISTS SCIENTIFIC_BOOK CASCADE;
CREATE TABLE IF NOT EXISTS SCIENTIFIC_BOOK (
    id BIGINT NOT NULL PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    numberOfPages INTEGER,
    ageRestriction INTEGER,
    typeOfBook VARCHAR(255),
    direction VARCHAR(255),
    forStudy INTEGER
);

DROP TABLE IF EXISTS CHILDREN_BOOK CASCADE;
CREATE TABLE IF NOT EXISTS CHILDREN_BOOK (
    id BIGINT NOT NULL PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    numberOfPages INTEGER,
    ageRestriction INTEGER,
    typeOfBook VARCHAR(255),
    genre VARCHAR(255),
    comics INTEGER,
    educational INTEGER,
    interactive INTEGER
);

DROP TABLE IF EXISTS LIBRARY CASCADE;
CREATE TABLE IF NOT EXISTS LIBRARY (
    id BIGINT NOT NULL PRIMARY KEY,
    book INTEGER NOT NULL,
    user INTEGER NOT NULL,
    review VARCHAR(255),
    rating INTEGER,
    typeOfBook VARCHAR(255) NOT NULL
);

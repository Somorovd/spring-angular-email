CREATE SCHEMA IF NOT EXISTS EmailDB;

CREATE TABLE IF NOT EXISTS EmailDB.Addresses
(
    id INT NOT NULL,
    username VARCHAR(64) NOT NULL,
    server VARCHAR(254) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EmailDB.Users
(
    id INT NOT NULL,
    addressId INT NOT NULL,
    password INT NOT NULL,
    version INT,
    PRIMARY KEY (id),
    FOREIGN KEY (addressId) REFERENCES EmailDB.Addresses(id)
);

CREATE TABLE IF NOT EXISTS EmailDB.Contacts
(
    id INT NOT NULL,
    address1Id INT NOT NULL,
    address2Id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (address1Id) REFERENCES EmailDB.Addresses(id),
    FOREIGN KEY (address2Id) REFERENCES EmailDB.Addresses(id)
);

CREATE TABLE IF NOT EXISTS EmailDB.Chains
(
    id INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EmailDB.Emails
(
    id INT NOT NULL,
    chainId INT NOT NULL,
    senderAddressId INT NOT NULL,
    subject VARCHAR(80) DEFAULT '',
    body TEXT DEFAULT '',
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (chainId) REFERENCES EmailDB.Chains(id),
    FOREIGN KEY (senderAddressId) REFERENCES EmailDB.Addresses(id)
);

CREATE TABLE IF NOT EXISTS EmailDB.Recipients
(
    id INT NOT NULL,
    addressId INT NOT NULL,
    emailId INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (addressId) REFERENCES EmailDB.Addresses(id),
    FOREIGN KEY (emailId) REFERENCES EmailDB.Emails(id)
);

CREATE TABLE IF NOT EXISTS EmailDB.Statuses
(
    id INT NOT NULL,
    userId INT NOT NULL,
    chainId INT NOT NULL,
    isRead BOOLEAN DEFAULT FALSE,
    isStarred BOOLEAN DEFAULT FALSE,
    location VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES EmailDB.Users(id),
    FOREIGN KEY (chainId) REFERENCES EmailDB.Chains(id)
);




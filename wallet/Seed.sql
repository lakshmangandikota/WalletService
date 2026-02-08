CREATE TABLE Asset_Types (
    Id SERIAL PRIMARY KEY,
    Code VARCHAR(50) UNIQUE NOT NULL,
    Name VARCHAR(100) NOT NULL
);

CREATE TABLE wallets (
    Id SERIAL PRIMARY KEY,
    Owner_Type VARCHAR(20) NOT NULL,
    Owner_Id BIGINT NOT NULL,
    Asset_Type_id INT NOT NULL,
    Balance NUMERIC(20, 0) NOT NULL DEFAULT 0,

    CONSTRAINT fk_wallet_asset
        FOREIGN KEY (asset_type_id)
        REFERENCES Asset_Types(id),

    CONSTRAINT chk_balance_non_negative
        CHECK (Balance >= 0)
);


CREATE TABLE Ledger_Entries (
    Id SERIAL PRIMARY KEY,
    Transaction_Id UUID NOT NULL,
    Wallet_Id BIGINT NOT NULL,
    Entry_Type VARCHAR(10) NOT NULL,  -- CREDIT / DEBIT
    Amount NUMERIC(20, 0) NOT NULL DEFAULT 0,
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ledger_wallet
        FOREIGN KEY (wallet_id)
        REFERENCES wallets(id)
);

CREATE TABLE Idempotency_Keys (
    Idempotency_Key VARCHAR(100) PRIMARY KEY,
    Status VARCHAR(20) NOT NULL,
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Asset types
INSERT INTO Asset_Types (Code, Name) VALUES ('GOLD', 'Gold Coins');
INSERT INTO Asset_Types (Code, Name) VALUES ('SILVER', 'Silver');

-- System wallet (Treasury)
INSERT INTO Wallets (Owner_Type, Owner_Id, Asset_Type_Id, Balance) VALUES ('SYSTEM', 1, 1, 1000000);

-- User wallet
INSERT INTO Wallets (Owner_Type, Owner_Id, Asset_Type_Id, Balance) VALUES ('USER', 101, 1, 500);


-------------------------------Roll Back Scripts----------------------
DROP TABLE Ledger_Entries;
DROP TABLE Idempotency_Keys;
DROP TABLE Wallets;
DROP TABLE Asset_Types;

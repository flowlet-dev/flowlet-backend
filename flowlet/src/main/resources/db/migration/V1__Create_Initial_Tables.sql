-- V1__Create_Initial_Tables.sql

-- 汎用コードマスタ
CREATE TABLE m_code (
    code_group VARCHAR(30),
    code_value VARCHAR(30),
    display_name VARCHAR(50) NOT NULL,
    sort_order INT NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(50),
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50),
    PRIMARY KEY (code_group, code_value)
);

-- カテゴリーマスタ
CREATE TABLE m_category (
    category_cd VARCHAR(20) PRIMARY KEY,
    parent_category_cd VARCHAR(20) REFERENCES m_category(category_cd),
    category_name VARCHAR(50) NOT NULL,
    flow_type VARCHAR(10) NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(50),
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50)
);

-- 実口座マスタ
CREATE TABLE m_physical_account (
    physical_account_id SERIAL PRIMARY KEY,
    account_name VARCHAR(50) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(50),
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50)
);

-- 仮想口座マスタ
CREATE TABLE m_virtual_account (
    virtual_account_id SERIAL PRIMARY KEY,
    account_name VARCHAR(50) NOT NULL,
    is_liquid BOOLEAN NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(50),
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50)
);

-- 取引
CREATE TABLE t_transaction (
    transaction_id SERIAL PRIMARY KEY,
    transaction_date DATE NOT NULL,
    description VARCHAR(200),
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(50),
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50)
);

-- 取引明細
CREATE TABLE t_transaction_detail (
    detail_id SERIAL PRIMARY KEY,
    transaction_id INT NOT NULL REFERENCES t_transaction(transaction_id),
    amount DECIMAL(15,2) NOT NULL,
    category_cd VARCHAR(20) NOT NULL REFERENCES m_category(category_cd),
    physical_account_id INT NOT NULL REFERENCES m_physical_account(physical_account_id),
    virtual_account_id INT NOT NULL REFERENCES m_virtual_account(virtual_account_id),
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(50),
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50)
);

# データベース設計書

## 1. 概要
本ドキュメントは、家計管理アプリ「Flowlet」のデータベース設計を定義したものです。
「実口座と仮想口座の二重管理構造」および「複合内訳（スプリット）管理」を実現するための構造を採用しています。

## 2. ER図
```mermaid
erDiagram
    m_category ||--o{ m_category : "親子関係"
    m_category ||--o{ t_transaction_detail : "分類"
    m_category ||--o{ m_budget : "予算設定"
    m_category ||--o{ m_recurring_rule : "定期設定"
    m_physical_account ||--o{ t_transaction_detail : "資産変動"
    m_physical_account ||--o{ m_recurring_rule : "定期設定"
    m_virtual_account ||--o{ t_transaction_detail : "目的変動"
    m_virtual_account ||--o{ m_recurring_rule : "定期設定"
    t_transaction ||--|{ t_transaction_detail : "内訳"
    m_code ||--o{ m_category : "名称管理"
    m_code ||--o{ m_physical_account : "名称管理"

    m_code {
        varchar code_group PK "コードグループ(ACCOUNT_TYPE等)"
        varchar code_value PK "コード値(BANK, INCOME等)"
        varchar display_name "表示名称(銀行, 収入等)"
        int sort_order "並び順"
    }

    m_category {
        varchar category_cd PK "カテゴリーコード"
        varchar parent_category_cd FK "親カテゴリーコード"
        varchar category_name "カテゴリー名"
        varchar flow_type "収入/支出"
        timestamp create_at
        varchar create_by
        timestamp update_at
        varchar update_by
    }

    m_physical_account {
        serial physical_account_id PK "実口座ID"
        varchar account_name "口座名"
        varchar account_type "種類(BANK, CARD, etc)"
        timestamp create_at
        varchar create_by
        timestamp update_at
        varchar update_by
    }

    m_virtual_account {
        serial virtual_account_id PK "仮想口座ID"
        varchar account_name "目的名"
        boolean is_liquid "流動資産フラグ"
        timestamp create_at
        varchar create_by
        timestamp update_at
        varchar update_by
    }

    m_budget {
        serial budget_id PK "予算ID"
        varchar category_cd FK "カテゴリーコード"
        decimal amount "予算額"
        date start_date "開始日"
        date end_date "終了日"
        timestamp create_at
        timestamp update_at
    }

    m_recurring_rule {
        serial recurring_rule_id PK "定期実行ルールID"
        varchar rule_name "ルール名"
        decimal amount "金額"
        int transaction_day "実行日(日)"
        varchar category_cd FK "カテゴリーコード"
        int physical_account_id FK "実口座ID"
        int virtual_account_id FK "仮想口座ID"
        varchar description "摘要"
        boolean is_active "有効フラグ"
        timestamp create_at
        timestamp update_at
    }

    m_user_setting {
        varchar setting_key PK "設定キー"
        varchar setting_value "設定値"
        timestamp create_at
        timestamp update_at
    }

    t_transaction {
        serial transaction_id PK "取引ID"
        date transaction_date "取引日"
        varchar description "摘要"
        timestamp create_at
        varchar create_by
        timestamp update_at
        varchar update_by
    }

    t_transaction_detail {
        serial detail_id PK "明細ID"
        int transaction_id FK "親取引ID"
        decimal amount "金額"
        varchar category_cd FK "カテゴリー"
        int physical_account_id FK "実口座"
        int virtual_account_id FK "仮想口座"
        timestamp create_at
        varchar create_by
        timestamp update_at
        varchar update_by
    }
```

## 3. テーブル定義

### 3.1. m_code (汎用コードマスタ)
システム内の各種区分値（Type）の表示名や並び順を管理します。

| カラム名 | 型 | 制約 | 説明 |
| :--- | :--- | :--- | :--- |
| `code_group` | VARCHAR(30) | PK | グループ（例: `FLOW_TYPE`, `ACCOUNT_TYPE`） |
| `code_value` | VARCHAR(30) | PK | コード値（例: `INCOME`, `BANK`） |
| `display_name` | VARCHAR(50) | NOT NULL | 画面表示用の名称（例: `収入`, `銀行`） |
| `sort_order` | INT | NOT NULL | 画面上の表示順 |

### 3.2. m_category (カテゴリーマスタ)
| カラム名 | 型 | 制約 | 説明 |
| :--- | :--- | :--- | :--- |
| `category_cd` | VARCHAR(20) | PK | カテゴリーコード |
| `parent_category_cd` | VARCHAR(20) | FK | 親カテゴリーコード（最上位の場合はNULL） |
| `category_name` | VARCHAR(50) | NOT NULL | カテゴリー名 |
| `flow_type` | VARCHAR(10) | NOT NULL | 収入 (INCOME) / 支出 (OUTGO) |

### 3.3. m_physical_account (実口座マスタ)
| カラム名 | 型 | 制約 | 説明 |
| :--- | :--- | :--- | :--- |
| `physical_account_id` | SERIAL | PK | 実口座ID |
| `account_name` | VARCHAR(50) | NOT NULL | 口座名（例：〇〇銀行、現金） |
| `account_type` | VARCHAR(20) | NOT NULL | 種類（BANK, CARD, CASH, etc） |

### 3.4. m_virtual_account (仮想口座マスタ)
| カラム名 | 型 | 制約 | 説明 |
| :--- | :--- | :--- | :--- |
| `virtual_account_id` | SERIAL | PK | 仮想口座ID |
| `account_name` | VARCHAR(50) | NOT NULL | 目的名（例：旅行用、予備費、自由費） |
| `is_liquid` | BOOLEAN | NOT NULL | 流動資産フラグ（TRUE: 「あといくら使えるか」の計算対象） |

### 3.5. m_budget (予算マスタ)
| カラム名 | 型 | 制約 | 説明 |
| :--- | :--- | :--- | :--- |
| `budget_id` | SERIAL | PK | 予算ID |
| `category_cd` | VARCHAR(20) | FK, NOT NULL | カテゴリーコード |
| `amount` | DECIMAL(15,2) | NOT NULL | 予算額 |
| `start_date` | DATE | NOT NULL | 開始日 |
| `end_date` | DATE | NOT NULL | 終了日 |

### 3.6. m_recurring_rule (定期実行ルールマスタ)
| カラム名 | 型 | 制約 | 説明 |
| :--- | :--- | :--- | :--- |
| `recurring_rule_id` | SERIAL | PK | 定期実行ルールID |
| `rule_name` | VARCHAR(100) | NOT NULL | ルール名 |
| `amount` | DECIMAL(15,2) | NOT NULL | 金額 |
| `transaction_day` | INT | NOT NULL | 実行日（日） |
| `category_cd` | VARCHAR(20) | FK, NOT NULL | カテゴリーコード |
| `physical_account_id` | INT | FK, NOT NULL | 実口座ID |
| `virtual_account_id` | INT | FK, NOT NULL | 仮想口座ID |
| `description` | VARCHAR(200) | | 摘要 |
| `is_active` | BOOLEAN | NOT NULL | 有効フラグ |

### 3.7. m_user_setting (ユーザー設定マスタ)
| カラム名 | 型 | 制約 | 説明 |
| :--- | :--- | :--- | :--- |
| `setting_key` | VARCHAR(50) | PK | 設定キー（例: `closing_day`） |
| `setting_value` | VARCHAR(100) | NOT NULL | 設定値 |

### 3.8. t_transaction (取引)
| カラム名 | 型 | 制約 | 説明 |
| :--- | :--- | :--- | :--- |
| `transaction_id` | SERIAL | PK | 取引ID |
| `transaction_date` | DATE | NOT NULL | 取引日 |
| `description` | VARCHAR(200) | | 取引全体の説明・メモ |

### 3.9. t_transaction_detail (取引明細)
| カラム名 | 型 | 制約 | 説明 |
| :--- | :--- | :--- | :--- |
| `detail_id` | SERIAL | PK | 明細ID |
| `transaction_id` | INT | FK, NOT NULL | 親取引IDへの参照 |
| `amount` | DECIMAL(15,2) | NOT NULL | 金額 |
| `category_cd` | VARCHAR(20) | FK, NOT NULL | カテゴリーコードへの参照 |
| `physical_account_id` | INT | FK, NOT NULL | 変動した実口座IDへの参照 |
| `virtual_account_id` | INT | FK, NOT NULL | 割り当てられた仮想口座IDへの参照 |

## 4. 特記事項
- **二重管理の同期**: `t_transaction_detail` にて実口座と仮想口座を同時に記録することで、常に `Σ実口座残高 = Σ仮想口座残高` を保証する。
- **スプリット管理**: 1つの `t_transaction` に対して複数の `t_transaction_detail` を作成可能。
- **共通カラム**: すべてのテーブルは `create_at`, `create_by`, `update_at`, `update_by` を保持する（`.junie/db_guidelines.md` 参照）。

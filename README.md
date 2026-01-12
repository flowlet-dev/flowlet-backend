# Flowlet Backend

家計管理アプリ「Flowlet」のバックエンドシステムです。
DDD（ドメイン駆動設計）の実践、Java 25/Spring Boot 4.0といった最新技術スタックの活用、およびクリーンな設計を学習・構築することを目的としています。

## 🚀 プロジェクトの目的

- **DDDの実践**: 業務ドメインを深く理解し、コードに落とし込む。
- **最新技術の追随**: Java 25 の新機能（record, Pattern Matching等）を積極的に活用する。
- **堅牢な設計**: 型安全な Value Object やガード節を用い、不正な状態を許さない実装を行う。

## 🛠 技術スタック

- **Language**: Java 25
- **Framework**: Spring Boot 4.0.1
- **Build Tool**: Gradle Kotlin DSL
- **Database**: PostgreSQL
- **Library**:
  - Spring Data JPA
  - Spring Bean Validation
  - Lombok (Entityのみ)

## 🏗 アーキテクチャ

DDDの階層化アーキテクチャを採用しています。

- `presentation`: 外部インターフェース（REST API Controller, Exception Handler）
- `application`: ユースケース、DTO、トランザクション管理
- `domain`: ドメインモデル（Entity, Value Object）、リポジトリインターフェース
- `infrastructure`: 永続化の実装（Spring Data JPA）

## 項目定義（ドメイン整理）

### エンティティ / バリューオブジェクト
- **Transaction (TTransaction)**: 取引データ（収入・支出）
- **Category (MCategory)**: 取引カテゴリー（食費、給与など）
- **Money**: 金額（0以上を保証、計算ロジックを内包）
- **TransactionDate**: 取引日（未来日禁止のルールを内包）
- **FlowType**: 収支種別（収入 / 支出）のEnum
- **UserName**: ユーザー名（姓・名を分離して保持）

## 📖 Decision Log（設計判断の記録）

### Java 25 & Spring Boot 4.0 採用
最新の言語機能（recordによるValue Object実装など）を最大限活用し、型安全で簡潔なコードを目指すため。

### Entity 命名規則
DBのテーブル構造（`m_category`, `t_transaction`）との対応を直感的に理解できるよう、`MCategory` や `TTransaction` といった命名を採用。

### Value Object の record 化
不変性（Immutability）を標準で担保し、ボイラープレートを排除するため。

---

## 🏗 DB設計

要件定義に基づき、以下のテーブル構造で設計しています。

### 口座管理
- **m_physical_account (実口座)**: 銀行、証券、クレジットカード、現金など。
- **m_virtual_account (仮想口座)**: 旅行用、予備費、自由費など。

### 取引管理
- **t_transaction (取引)**: 取引の発生日や全体の説明を保持する親テーブル。
- **t_transaction_detail (取引明細)**: いわゆる「スプリット」管理を実現する子テーブル。一つの取引を複数のカテゴリーや口座に分割して記録可能。

### 整合性ルール
- **二重管理の同期**: 取引明細登録時、必ず「実口座」と「仮想口座」の両方を指定することを強制（または「未割当」仮想口座への自動配分）し、常に `Σ実口座残高 = Σ仮想口座残高` を維持する。

---

## 🛠 セットアップと実行

### 起動方法
```bash
./gradlew bootRun
```
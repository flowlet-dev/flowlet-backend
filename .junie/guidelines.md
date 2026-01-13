# Flowlet 開発ガイドライン

本ドキュメントは、家計管理アプリ「Flowlet」のバックエンド開発における設計指針とコーディング規約を定めたものです。

## 1. 設計原則 (DDD)

本プロジェクトはドメイン駆動設計（DDD）の考え方をベースに構築します。

- **ドメイン中心**: DB設計からではなく、業務上の概念（ユビキタス言語）からモデルを設計する。
- **カプセル化**: 不正な状態のオブジェクトが生成されないよう、ガード節によるバリデーションを徹底する。

## 2. アーキテクチャ構成

以下の4層レイヤーを採用しています。

- **Presentation (`presentation`)**: `Controller`, `GlobalExceptionHandler`
  - 外部とのインターフェース。HTTPリクエストの受付とレスポンス変換。
- **Application (`application`)**: `ApplicationService`, `DTO`
  - ユースケースの実現。トランザクション境界の管理。
- **Domain (`domain`)**: `Model (Entity/VO)`, `Repository Interface`, `DomainService`
  - 業務ロジックの核。外部ライブラリへの依存を最小限にする。
- **Infrastructure**: `Repository Implementation`, `Config`
  - DBアクセスや外部通信の具体的な実装。

## 3. コーディング規約

### 3.1. Java 言語活用
- **Java 21**: 最新のLTS(長期サポート)版の言語機能を活用する。
- **Value Object**: 原則として Java `record` を使用する。
- **不変性 (Immutability)**: 可能な限りオブジェクトは不変として扱う。
- **型宣言**: 型推論（`var`）は使用せず、明示的に型を宣言する。
- **制御構文**: 可読性向上のため、`if` 文などの制御構文では常に波括弧 `{}` を使用する。
- **ループとStream**: 単純なリスト変換などは `stream` を使用してもよいが、複雑な処理や副作用を伴う場合は `for` 文を使用する。

### 3.2. 命名規則
- **Entity**: DBテーブル名との対応を明確にするため、以下のプレフィックスを許容する。
  - マスタ系: `MCategory`（テーブル名 `m_category`）
  - トランザクション系: `TTransaction`（テーブル名 `t_transaction`）
- **パッケージ構造**: `com.example.flowlet.[layer].[subdomain]` の形式に従う。

### 3.3. バリデーションとエラーハンドリング
- **入力チェック**: DTOで `jakarta.validation` アノテーションを使用。
- **業務ルール**: Entity/Value Object のコンストラクタでチェックを行い、不正な場合は `IllegalArgumentException` をスローする。
- **エラーメッセージ**: エラーメッセージはソースコード内に直接記述せず、メッセージ定義（MessageSource等）で一元管理する。

## 4. 技術スタックの利用

- **Spring Boot 3.2 / Spring Data JPA**: 永続化フレームワークとして標準利用。
- **Lombok**: Entity の Getter/Setter 等、ボイラープレートコードの削減に使用。ただし、Value Object (`record`) には使用しない。
- **PostgreSQL**: 開発・本番共通のデータベース。

## 5. ドキュメント管理

- **README.md**: プロジェクトの概要、起動方法、および「Decision Log（設計判断の記録）」を記述する。
- **Javadoc**: 公開APIや複雑なドメインロジックには、日本語で意図を記述する。

## 6. Git 運用

- 小さな単位でコミットし、何をなぜ変更したかを明確にする。
- 破壊的な変更を行う場合は、事前に `README.md` の Decision Log を更新することを検討する。
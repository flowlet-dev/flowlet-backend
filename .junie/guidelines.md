# Flowlet 開発ガイドライン

本ドキュメントは、家計管理アプリ「Flowlet」のバックエンド開発における設計指針とコーディング規約を定めたものです。

## 1. 用語定義（ドメイン）

本プロジェクトで扱う主要な概念と英語表記を定義します。

- **実口座 (Physical Account)**: 銀行、証券、現金など、物理的なお金の場所。
- **仮想口座 (Virtual Account)**: 旅行用、予備費など、ユーザーが設定したお金の使用目的。
- **取引 (Transaction)**: 収支または振替。
- **取引内訳 (TransactionDetail)**: 一つの取引に紐づく複数の明細（スプリット）。
- **資産同期 (Balance Synchronization)**: すべての実口座の残高合計と、すべての仮想口座の残高合計を一致させる管理。

## 2. 設計原則 (DDD)

本プロジェクトはドメイン駆動設計（DDD）の考え方をベースに構築します。

- **ドメイン中心**: DB設計からではなく、業務上の概念（ユビキタス言語）からモデルを設計する。
- **計算ロジックの所在**: 
  - 単一のエンティティで完結する計算はエンティティ内に記述。
  - 実口座と仮想口座の同期チェックや、年間支出の平準化ロジックなど、複数のエンティティが関わる複雑なロジックは **Domain Service** に配置する。
- **カプセル化**: 不正な状態のオブジェクトが生成されないよう、ガード節によるバリデーションを徹底する。

## 3. アーキテクチャ構成

以下の4層レイヤーを採用しています。

- **Presentation (`presentation`)**: `Controller`, `GlobalExceptionHandler`
  - 外部とのインターフェース。HTTPリクエストの受付とレスポンス変換。
- **Application (`application`)**: `ApplicationService`, `DTO`
  - ユースケースの実現。トランザクション境界の管理。
- **Domain (`domain`)**: `model`, `repository` (Interface), `service` (Domain Service)
  - 業務ロジックの核。外部ライブラリ（JPA等）への依存を排除する。
  - `model` は不変（Immutable）なオブジェクトとして設計し、JPAエンティティとは分離する。
- **Infrastructure**: `persistence` (Entity, Repository Implementation, Mapper), `config`
  - DBアクセスや外部通信の具体的な実装。
  - JPAエンティティは `infrastructure.persistence.entity` に配置し、ドメインモデルとの変換は `infrastructure.persistence.mapper` で行う。

## 4. コーディング規約

### 4.1. Java 言語活用
- **Java 25**: 最新の機能（プロジェクト・パナマ、構造化並列処理など）の利用も視野に入れつつ、型安全な開発を行う。
- **Value Object**: 原則として Java `record` を使用する。
- **不変性 (Immutability)**: 可能な限りオブジェクトは不変として扱う。
- **型宣言**: 可読性と安全性を優先し、明示的な型宣言を行う。型推論（`var`）は使用しない。
- **引数の明確化**: メソッドの引数名には、`id` などの曖昧な名前を避け、`physicalAccountId` のように何を指しているか明確な名称を使用する。
- **コンストラクタインジェクション**: 依存性の注入にはコンストラクタインジェクションを徹底する。Lombok の `@RequiredArgsConstructor` を使用してボイラープレートを削減する。
- **制御構文**: 可読性向上のため、`if` 文などの制御構文では常に波括弧 `{}` を使用する。

### 4.2. サービス層の役割分担
- **Application Service**:
  - ユースケース（ドメインモデルの取得、ドメインサービスの呼び出し、保存など）の実行手順を記述する。
  - トランザクション境界を管理する。
  - 外部との入出力（DTO）を扱う。
- **Domain Service**:
  - 複数のドメインモデルにまたがるビジネスロジックや、特定のドメインモデルに持たせることが不自然なロジックを記述する。
  - 例：実口座と仮想口座の同期整合性チェック、複雑な資産計算、予算計算など。
  - 状態を持たず（Stateless）、副作用を最小限に抑える。

### 4.3. 命名規則
- **Domain Model**: ドメイン概念をそのままクラス名とする（例: `PhysicalAccount`, `Transaction`）。
- **JPA Entity**: テーブル名に基づいた名称とする（例: `MPhysicalAccount`, `TTransaction`）。
- **パッケージ構造**: `com.example.flowlet.[layer].[subdomain]` の形式に従う。

### 4.3. リポジトリ構成
- **Domain Repository (Interface)**: `domain.repository` に配置。ドメインモデルを扱う。
- **Infrastructure Repository (Implementation)**: `infrastructure.persistence.repository` に配置。
  - `JpaRepository` を継承したインターフェース（例: `JpaTransactionRepository`）と、ドメインリポジトリを実装するクラス（例: `TransactionRepositoryImpl`）の2段構成とする。
  - 実装クラスには `@Primary` を付与し、アプリケーション層ではドメインリポジトリのインターフェースを DI して使用する。

### 4.4. バリデーションとエラーハンドリング
- **入力チェック**: DTOで `jakarta.validation` アノテーションを使用。
- **業務ルール**: Entity/Value Object のコンストラクタや Domain Service でチェックを行い、不正な場合は `BusinessException` をスローする。
- **エラーメッセージ**: `messages.properties` で一元管理し、`BusinessException` にメッセージコードを渡す。
- **例外ハンドリング**: 
  - Application Service では原則として `try-catch` を使用し、チェック例外や未予期な例外を `BusinessException` にラップして再スローする。
  - Presentation 層の `GlobalExceptionHandler` で `BusinessException` を捕捉し、適切なメッセージとステータスコードに変換してクライアントに返却する。

### 4.5. テスト方針
- **ドメインロジック**: 複雑な計算（同期、平準化、予算対比）については、JUnit 5を用いて徹底的にユニットテストを行う。
- **正常・異常系**: 境界値テストを含め、業務上の制約が守られていることをテストで担保する。

## 5. 技術スタックの利用

- **Spring Boot 4.0 / Spring Data JPA**: 最新のSpringエコシステムを活用。
- **Lombok**: Entity の Getter/Setter 等、ボイラープレートコードの削減に使用。Value Object (`record`) には使用しない。
- **MapStruct**: ドメインモデルとエンティティの相互変換に使用。Recordクラスに対応させるため、アクセサ名の命名規則に注意する。
- **PostgreSQL**: 永続化データベース。時刻取得は `CURRENT_TIMESTAMP AT TIME ZONE 'Asia/Tokyo'` を使用し、日本時間で管理する。

## 6. ドキュメント・Git 運用

- **Decision Log**: 設計判断（なぜその実装にしたか）は `README.md` に記録する。
- **Git**: 「1つの関心事につき1コミット」を徹底する。
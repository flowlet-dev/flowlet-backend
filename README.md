# flowlet-backend

## 開発方針

このプロジェクトは、家計管理アプリ「Flowlet」を題材に、
DDD・環境構築・Git運用を学習目的で段階的に開発する。

## ドメイン整理（初期）

### エンティティ候補

- User
- Category
- Transaction

### Value Object 候補

- UserName（姓・名）
- Money
- TransactionDate
- FlowType（収入 / 支出）

### 方針

- DB設計からEntityを起こさない
- 業務用語（ユビキタス言語）を最優先する

## Decision Log

### Spring Boot 初期化

- Gradle を採用
- Spring Web / JPA / PostgreSQL を導入
- Security は初期段階では導入しない

理由：
まずはドメイン設計とDB接続を優先し、
認証認可は後から段階的に導入するため。

### Build Tool 選定

- Gradle を採用
- 理由：
    - 設定が簡潔で学習しやすい
    - Docker / CI との相性が良い
    - 将来的な拡張性が高い
- Maven は安定しているが、今回の目的には Gradle が適していると判断

### Gradle DSL 選定

- Kotlin DSL（build.gradle.kts）を採用
- 理由：
    - 型安全でIDE補完が強い
    - 学習時にミスに気づきやすい
    - 新規プロジェクトでの採用が増えている
- Groovy DSL は情報量が多いが、理解より模倣になりやすいため今回は不採用

### FlowType 設計

- FlowType は enum として定義
- Category / Transaction 共通の業務概念
- 値が固定であるため enum が最適と判断
- 将来的に可変になった場合は Entity 化を検討

### UserName 設計

- UserName は Value Object として定義
- Java record を使用し不変性を担保
- 姓・名を分離して保持
- 不正な状態は生成時に防ぐ設計とした

### Money 設計

- Money を Value Object として定義
- 内部表現に BigDecimal を採用
- 金額は0以上とし、不正値は生成時に防止
- 金額演算は Money 自身が責務を持つ

### TransactionDate 設計

- TransactionDate を Value Object として定義
- 内部表現に LocalDate を採用
- 未来日を禁止する業務ルールを内包

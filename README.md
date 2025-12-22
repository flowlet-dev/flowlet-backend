# flowlet-backend

## 開発方針

このプロジェクトは、家計管理アプリ「Flowlet」を題材に、
DDD・環境構築・Git運用を学習目的で段階的に開発する。

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

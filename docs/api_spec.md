# Flowlet API 仕様書

本ドキュメントは、Flowlet バックエンドが提供する REST API の仕様を定義したものです。

## 共通仕様
- **ベースURL**: `/api`
- **レスポンス形式**: JSON
- **エラーレスポンス**: 400 (Bad Request) または 500 (Internal Server Error) を返し、以下の形式でメッセージを含みます。
  ```json
  { "message": "エラーメッセージ内容" }
  ```

---

## 1. 実口座管理 (Physical Account)
実口座（銀行、カード、現金等）の管理を行います。

### 1.1 実口座一覧取得
- **URL**: `GET /api/physical-accounts`
- **概要**: 登録されているすべての実口座を取得します。

### 1.2 実口座登録
- **URL**: `POST /api/physical-accounts`
- **リクエスト**:
  ```json
  {
    "accountName": "〇〇銀行",
    "accountType": "BANK"
  }
  ```

### 1.3 実口座更新
- **URL**: `PUT /api/physical-accounts/{id}`

### 1.4 実口座削除
- **URL**: `DELETE /api/physical-accounts/{id}`

---

## 2. 仮想口座管理 (Virtual Account)
仮想口座（目的別管理）の管理を行います。

### 2.1 仮想口座一覧取得
- **URL**: `GET /api/virtual-accounts`

### 2.2 仮想口座登録
- **URL**: `POST /api/virtual-accounts`
- **リクエスト**:
  ```json
  {
    "accountName": "旅行用",
    "isLiquid": true
  }
  ```

---

## 3. 取引管理 (Transaction)
収支・振替などの取引および明細を管理します。複合内訳（スプリット）に対応しています。

### 3.1 取引一覧取得
- **URL**: `GET /api/transactions`

### 3.2 取引登録
- **URL**: `POST /api/transactions`
- **リクエスト**:
  ```json
  {
    "transactionDate": "2024-01-01",
    "description": "スーパーでの買い物",
    "details": [
      {
        "amount": -5000,
        "categoryCd": "FOOD",
        "physicalAccountId": 1,
        "virtualAccountId": 2
      }
    ]
  }
  ```

---

## 4. 予算管理 (Budget)
カテゴリー別の予算を管理します。

### 4.1 予算一覧取得
- **URL**: `GET /api/budgets`

### 4.2 予算登録
- **URL**: `POST /api/budgets`
- **概要**: 同一カテゴリーで期間が重複する予算は登録できません。
- **リクエスト**:
  ```json
  {
    "categoryCd": "FOOD",
    "amount": 50000,
    "startDate": "2024-01-01",
    "endDate": "2024-01-31"
  }
  ```

---

## 5. 財務集計 (Financial Summary)
現在の資産状況や今月の収支状況を取得します。

### 5.1 財務サマリー取得
- **URL**: `GET /api/financial-summary`
- **概要**: 締め日に基づく今月の集計期間、流動資産合計、期間収支、自由に使える額を取得します。
- **レスポンス**:
  ```json
  {
    "startDate": "2023-12-26",
    "endDate": "2024-01-25",
    "currentLiquidAssets": 500000,
    "periodBalance": -20000,
    "availableAmount": 480000
  }
  ```

---

## 6. 定期実行ルール (Recurring Rule)
固定費などの自動登録ルールを管理します。

### 6.1 ルール一覧取得
- **URL**: `GET /api/recurring-rules`

### 6.2 ルール登録
- **URL**: `POST /api/recurring-rules`
- **リクエスト**:
  ```json
  {
    "ruleName": "家賃",
    "amount": 80000,
    "transactionDay": 25,
    "categoryCd": "HOUSING",
    "physicalAccountId": 1,
    "virtualAccountId": 1,
    "isActive": true
  }
  ```

---

## APIドキュメント (Swagger UI)
アプリを起動後、ブラウザで以下にアクセスすることで、インタラクティブなAPIドキュメントが確認可能です。
- URL: `http://localhost:8080/swagger-ui.html`

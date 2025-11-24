# Spring Blog System

Spring Boot を使用した RESTful API ブログシステムです。JWT 認証、ロールベースのアクセス制御を実装しています。

## 機能

- ユーザー認証・認可（JWT）
- ロールベースのアクセス制御（USER、ADMIN）
- ブログ記事の CRUD 操作
- コメント機能
- 管理者用エンドポイント

## 技術スタック

- Java 25
- Spring Boot 4.0.0
- Spring Security 7.x
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Token) 0.12.6
- Maven

## セットアップ

### 前提条件

- Java 25
- Maven 3.9 以上
- PostgreSQL 14 以上

### データベース設定

1. PostgreSQL データベースを作成:

```sql
CREATE DATABASE spring_blog_system;
```

2. プロジェクトルートに `.env` ファイルを作成（`.env.example` を参考）:

```env
# データベース設定
DB_URL=jdbc:postgresql://localhost:5432/spring_blog_system
DB_USERNAME=postgres
DB_PASSWORD=your_password

# JWT設定
JWT_SECRET=your_jwt_secret_key_minimum_256_bits
JWT_EXPIRATION=86400000

# 管理者初期設定
ADMIN_USERNAME=admin
ADMIN_EMAIL=admin@example.com
ADMIN_PASSWORD=admin123

# サーバー設定（オプション）
SERVER_PORT=8080
```

**重要**: `.env` ファイルは Git にコミットしないでください（`.gitignore` に含まれています）。

3. Spring プロファイルを選択（オプション）:

```bash
# 開発環境（デフォルト）
export SPRING_PROFILES_ACTIVE=dev

# 本番環境
export SPRING_PROFILES_ACTIVE=prod
```

### ビルドと実行

```bash
# プロジェクトをビルド
mvn clean install

# アプリケーションを起動（開発環境）
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# または本番環境で起動
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

アプリケーションは `http://localhost:8080` で起動します（`SERVER_PORT` で変更可能）。

### 初期データ

初回起動時、以下のデータが自動的に作成されます：

- **ロール**: `ROLE_USER`, `ROLE_ADMIN`（`data.sql` より）
- **管理者ユーザー**: 環境変数 `ADMIN_USERNAME`, `ADMIN_EMAIL`, `ADMIN_PASSWORD` で設定（デフォルト: admin/admin@example.com/admin123）

## API エンドポイント

### 認証

#### ユーザー登録

```
POST /api/auth/register
Content-Type: application/json

{
  "username": "user",
  "email": "user@example.com",
  "password": "password123"
}
```

#### ログイン

```
POST /api/auth/login
Content-Type: application/json

{
  "username": "user",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "username": "user"
}
```

### ブログ記事

#### 全記事取得（認証不要）

```
GET /api/posts
```

#### 記事取得（認証不要）

```
GET /api/posts/{id}
```

#### 記事作成（認証必要）

```
POST /api/posts
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "記事タイトル",
  "content": "記事の内容"
}
```

#### 記事更新（認証必要）

```
PUT /api/posts/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "更新されたタイトル",
  "content": "更新された内容"
}
```

#### 記事削除（認証必要）

```
DELETE /api/posts/{id}
Authorization: Bearer {token}
```

### 管理者機能

#### 全ユーザー取得（管理者のみ）

```
GET /api/admin/users
Authorization: Bearer {admin_token}
```

#### ユーザー削除（管理者のみ）

```
DELETE /api/admin/users/{id}
Authorization: Bearer {admin_token}
```

## プロジェクト構造

```
src/main/java/com/example/blog/
├── BlogApplication.java          # メインアプリケーション
├── config/                       # 設定クラス
│   ├── SecurityConfig.java       # Spring Security設定
│   └── WebConfig.java            # CORS設定
├── controller/                   # RESTコントローラー
│   ├── AdminController.java
│   ├── AuthController.java
│   ├── BlogController.java
│   └── UserController.java
├── dto/                          # データ転送オブジェクト
│   ├── CommentRequest.java
│   ├── LoginRequest.java
│   ├── PostRequest.java
│   └── RegisterRequest.java
├── exception/                    # 例外処理
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── model/                        # エンティティクラス
│   ├── Comment.java
│   ├── Post.java
│   ├── Role.java
│   └── User.java
├── repository/                   # データアクセス層
│   ├── CommentRepository.java
│   ├── PostRepository.java
│   ├── RoleRepository.java
│   └── UserRepository.java
├── security/                     # セキュリティ関連
│   ├── JwtAuthenticationFilter.java
│   ├── JwtTokenProvider.java
│   └── UserDetailsServiceImpl.java
└── service/                      # ビジネスロジック
    ├── AuthService.java
    ├── CommentService.java
    ├── PostService.java
    └── UserService.java
```

## 学習ポイント

このプロジェクトを通じて以下を学べます：

1. **Spring Boot 基礎**

   - アプリケーション構成
   - 依存性注入（DI）
   - レイヤードアーキテクチャ
   - Spring プロファイル（dev/prod 環境分離）

2. **Spring MVC**

   - RESTful API の設計
   - リクエスト/レスポンス処理
   - バリデーション

3. **Spring Data JPA**

   - エンティティマッピング
   - リポジトリパターン
   - リレーションシップ（@ManyToOne, @ManyToMany）
   - データベース自動初期化（`data.sql`, `CommandLineRunner`）

4. **Spring Security**

   - JWT 認証
   - ロールベースアクセス制御
   - パスワードエンコーディング
   - SecurityFilterChain によるエンドポイント保護

5. **例外処理**

   - グローバル例外ハンドラ
   - カスタム例外

6. **設定管理**
   - 環境変数による機密情報管理
   - プロファイル別設定ファイル
   - セキュアな設定のベストプラクティス

## 環境別設定

### 開発環境（dev）

`application-dev.properties` で設定：

- SQL ログ出力有効
- データベース自動更新（`ddl-auto=update`）
- 詳細なログレベル
- 開発用のデフォルト値

### 本番環境（prod）

`application-prod.properties` で設定：

- SQL ログ出力無効
- データベーススキーマ検証のみ（`ddl-auto=validate`）
- 最小限のログ出力
- エラー詳細の非表示
- ファイルベースのログローテーション

## トラブルシューティング

### データベース接続エラー

- PostgreSQL が起動していることを確認
- `.env` ファイルの `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` が正しいか確認
- データベースが作成されているか確認（`CREATE DATABASE spring_blog_system;`）
- ポート 5432 が開いているか確認

### JWT 関連エラー

- `.env` ファイルの `JWT_SECRET` が設定されているか確認（最低 256 ビット必要）
- トークンの有効期限が切れていないか確認
- Bearer トークン形式が正しいか確認（`Authorization: Bearer {token}`）

### 環境変数が読み込まれない

- `.env` ファイルがプロジェクトルートに存在するか確認
- IDE を再起動してみる
- Maven コマンドで直接実行してみる

## ライセンス

このプロジェクトは学習目的のサンプルプロジェクトです。

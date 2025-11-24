# Docker セットアップガイド

## 前提条件

- Docker Desktop for Windows がインストールされていること
- DBeaver Community (推奨 GUI ツール)
- PowerShell または Command Prompt

## 起動手順

### 1. PostgreSQL を起動

```powershell
docker-compose up -d
```

### 2. コンテナの状態確認

```powershell
docker-compose ps
```

### 3. PostgreSQL の接続確認

```powershell
docker exec -it spring-blog-postgres psql -U postgres -d spring_blog_system
```

## アクセス情報

### PostgreSQL

- **Host**: localhost
- **Port**: 5432
- **Database**: spring_blog_system
- **Username**: postgres
- **Password**: your_password

### DBeaver での接続設定

1. DBeaver を起動
2. 「新しい接続」をクリック
3. 「PostgreSQL」を選択
4. 以下を入力：
   - **Host**: localhost
   - **Port**: 5432
   - **Database**: spring_blog_system
   - **Username**: postgres
   - **Password**: your_password
5. 「接続のテスト」で確認後、「完了」

#### DBeaver のダウンロード

https://dbeaver.io/download/

## Spring Boot アプリケーションの起動

```powershell
mvn spring-boot:run
```

または

```powershell
mvn clean package
java -jar target/spring-blog-system-0.0.1-SNAPSHOT.jar
```

## 便利なコマンド

### ログ確認

```powershell
docker-compose logs -f postgres
```

### コンテナ停止

```powershell
docker-compose stop
```

### コンテナ停止＆削除

```powershell
docker-compose down
```

### データも含めて完全削除

```powershell
docker-compose down -v
```

### PostgreSQL シェルに接続

```powershell
docker exec -it spring-blog-postgres psql -U postgres -d spring_blog_system
```

### データベース一覧表示

```powershell
docker exec -it spring-blog-postgres psql -U postgres -c "\l"
```

### テーブル一覧表示

```powershell
docker exec -it spring-blog-postgres psql -U postgres -d spring_blog_system -c "\dt"
```

## トラブルシューティング

### ポート 5432 が既に使用されている

既に PostgreSQL がインストールされている場合、docker-compose.yml のポートを変更：

```yaml
ports:
  - "5433:5432" # ホスト側を5433に変更
```

その場合、application.properties も更新：

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/spring_blog_system
```

### コンテナが起動しない

```powershell
docker-compose logs postgres
```

でエラーログを確認

### データベースをリセットしたい

```powershell
docker-compose down -v
docker-compose up -d
```

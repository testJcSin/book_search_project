# book-search-project
# 書籍検索プロジェクト　ポーティングガイド

## 概要
このドキュメントは、書籍検索プロジェクトを実行するためのガイドです。

## 実行手順
### 1. 前提
   - このプロジェクトは以下を利用しています。
     - ChatGPT
       - ChatGPT APIのアクセスキーが必要になります。
     - Google Oauth
       - Google Oauth 利用のためのクライアント IDやシークレットが必要になります。
     - Google Cloud Translation API
       - Google Cloud Translation APIのAPIキーが必要になります。

### 2. プロジェクトのコピー
   - gitからプロジェクト「book_search_project」をクローン

### 3. 環境変数の設定
   - 「book_search_project」配下に「.env」ファイルを作成
   - .envファイルには以下の定義を追加する。
     - CHATTING_API_KEY=ChatGPT APIのアクセスキー
     - GOOGLE_CLIENT_ID=Google Oauth 利用のためのクライアント ID
     - GOOGLE_CLIENT_SECRET=Google Oauth 利用のためのクライアント シークレット
     - GOOGLE_REDIRECT_URI=localhost:8080/auth2/callback
     - TRANSLATE_API_KEY=Google Cloud Translation APIのAPIキー
     - SECRET_KEY=秘密鍵（適当な文字列）

### 4. アプリケーションの起動
   - ターミナルから「book_search_project/bookbackend」配下に移動し、以下コマンドを実行（jarが作成される）
     - ./gradlew clean build
   - ターミナルから「book_search_project」配下に移動し、以下コマンドを実行（Dockerが起動）
     - docker-compose up --build

### 5. アプリケーションにアクセス
   - Dockerが起動した後、以下URLにアクセスすることで書籍検索画面トップが表示
     - http://localhost:8080/searchTop

## 注意事項
   - 認証後有効期限は24時間

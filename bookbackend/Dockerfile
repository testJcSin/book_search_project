# 実行用の軽量なOpenJDKイメージを使用
FROM openjdk:21 AS runtime

WORKDIR /app

# ローカルビルドの成果物をコピー
COPY /build/libs/bookbackend-0.0.1-SNAPSHOT.jar /app/app.jar

# アプリケーションを起動
ENTRYPOINT ["java","-jar","app.jar"]
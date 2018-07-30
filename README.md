# Task: “RSSリーダーアプリの作成”

<RSS例><br/>
RSS : はてなブックマークのrssを使う。 <br/>
総合 : http://b.hatena.ne.jp/hotentry.rss<br/>
世の中 : http://b.hatena.ne.jp/hotentry/social.rss<br/>
政治と経済 : http://b.hatena.ne.jp/hotentry/economics.rss<br/><br/>
暮らし : http://b.hatena.ne.jp/hotentry/life.rss<br/>

## Build and install

リリースバージョンをビルドするため、キーストアファイルとキーストアープロパティファイル（keystore.properties）が必須です。<br/>
キーストアーファイル作成するため、以下のコンマンドを実行してください。<br/> 
例, ファイル名：`your_release.keystore` 、キーアリアス： `your_key_alias`<br/> 

```
cd src
keytool -genkey -v -keystore your_release.keystore -alias your_key_alias -keyalg RSA -keysize 2048 -validity 10000
```

キーストアープロパティファイルフォマットです。

```
storePassword=(ストアーパスワード)
keyPassword=(キーパスワード)
keyAlias=(キーアリアス)
storeFile=(キーストアーファイル名)

```

ビルドと端末でインストールする時、以下のコンマンドを実行してください。

```
cd src
# for debug version
./gradlew iD
# for release version
./gradlew iR

```




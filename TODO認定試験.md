#Herokuの設定
- [作成したjavaアプリケーション](https://infinite-bastion-90498.herokuapp.com/)

## postgresに接続できる
- https://data.heroku.com/
- App to provision to を選択しないといけない？ とりあえずjavaのappを作ってみる

## SpringBootプロジェクトの作成(hello world)
- [公式ドキュメントに従う](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku)
- herokuにアップロードするにはgit管理しないと行けない。しかしcodecheck-...はgit管理されているので、サブモジュールとしてSpringBootアプリケーションを登録してみる。
- spring init --dependencies=web myappをjavaもワークスペースで行う
- /usr/local/Cellar/springboot/2.0.2.RELEASE/bin/spring
- [codecheck-agile-test]
- [作成したAPI(heroku openで見ることができる)](https://infinite-bastion-90498.herokuapp.com/)
- [公式ドキュメント](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku)にpostgresと接続するマニュアルもあったのでやってみる
```bash
Tomoaki-no-MacBook-Pro:codecheck-agile-test tomoaki$ heroku addons:create heroku-postgresql
Creating heroku-postgresql on ⬢ infinite-bastion-90498... free
Database has been created and is available
 ! This database is empty. If upgrading, you can transfer
 ! data from another database with pg:copy
Created postgresql-contoured-43808 as DATABASE_URL
Use heroku addons:docs heroku-postgresql to view documentation
```

```bash
Tomoaki-no-MacBook-Pro:codecheck-agile-test tomoaki$ heroku config
=== infinite-bastion-90498 Config Vars
DATABASE_URL: postgres://ezeewweyoasfje:66c90a3d5cf20762ccf2504901f72d3f7427aeb20bdcc750b601734096d35104@ec2-23-23-130-158.compute-1.amazonaws.com:5432/dbklggl4p1fgqj
```

postgres://ezeewweyoasfje:66c90a3d5cf20762ccf2504901f72d3f7427aeb20bdcc750b601734096d35104@ec2-23-23-130-158.compute-1.amazonaws.com:5432/dbklggl4p1fgq

- Spring Boot CLI をインストールする 
    - https://blog.tagbangers.co.jp/2017/01/11/install-spring-boot-cli-homebrew

- create.sqlに従ってテーブルを作成
    - postgresはon update CURRENT_TIMESTAMPが使えなかったので以下を参照
    - https://open-groove.net/postgresql/update-timestamp-function/

## 作成するSpringBootアプリケーションとheroku postgresが接続できる
- https://geeorgey.com/archives/3456

```bash
heroku run echo \$JDBC_DATABASE_URL
```
- Spring-Boot-starter-dataをpomに追加

### JPAによるデータベースの利用
- エンティティ作成
- リポジトリ作成


## プロジェクトをHerokuにデプロイできる
- [作成したjavaアプリケーション](https://infinite-bastion-90498.herokuapp.com/)

## 成功のときと失敗のときでResponseのJsonの型を変えたい
- https://qiita.com/syukai/items/f03844feca78572ce24f


## checkstyle, formatter入れる
- [checkstyle](https://gist.github.com/euledge/4f6cfa4d6ea62b38125490e3e0dcbb38)
- [formatter]()

## DbUnit導入(DBSetupも考慮)
- [DBSetup](http://kensanty.hatenablog.com/entry/2017/01/09/133825)

## SpringBootの結合テスト例
- https://www.qoosky.io/techs/84b9daad5c 

## アノテーション使ったテスト


## TDDのコミット
## answer.mdにDIの工夫点記載

## バリデーションは優先度を下げる(テストを参照の上、異常値は見当たらないため)
## wiremock導入(優先度低)

## git submoduleにする
- herokuの都合上、SpringBootプロジェクトをcodecheck-43022のsourceディレクトリにコピーする。
- TDDのコミット経歴などはsourceディレクトリにコピーしたcodeCheck-Agile-Testのgitを参照していただく

## その他

パッケージ構成
Entity, Controller, Service
- Dataacsessパッケージ

コントローラで、エラーが出たら違う型のレスポンスを返したい
- Exception ハンドラー アノテーションをつける。
	- nullぽだったらこのレスポンスを返す。
- コントローラアドバイス アノテーション(今回はあんまり　プロジェクト全体でどうにかしたいとき)

コントローラのテスト方法
単体？結合？ レストのテストってできる？
- コントローラのテストはしていない
- ドメイン層しかやっていない。
- 代表的なものしかやっていない。

1つのコントローラにメソッドどれくらい用意すればいいの？
- 特にこだわりはない。
- 1つでやっている

バリデーションの方法
DTOとエンティティって分けるの？
同じの使うの?
- DTOとEntityはなるべく分けましょう。

insertできたことを確認ってどうやるんだ...

オートインクリメントOK!
https://qiita.com/KevinFQ/items/a6d92ec7b32911e50ffe
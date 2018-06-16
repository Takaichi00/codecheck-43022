## Deploy information
#### Language used
- Java

#### Hosting service
- Heroku

#### Brief explanation about how your code works
- パッケージ構成は[こちら](https://terasolunaorg.github.io/guideline/public_review/Overview/ApplicationLayering.html#repository)を参考にしました

####DIについて
##### DIとは
- DIとは、利用したいクラスのインスタンス生成にnew演算子を使用せず、フィールド/コンストラクタ/setterを利用して外部からインジェクトすることで、クラス間の関連を疎結合に保つ仕組みである。
- シングルトンを生成する、セッションごとに生成するなどのクラスのインスタンス化のライフサイクル管理を行う機能を持つ事ができる。

##### DIの利点
- テスタビリティの向上
- コンテナ管理されているインスタンスの取得を完結にすることができる
```java
@Resource(lookup = "jdbc/PostgresDS")
private Datasourece ds;
```
- FactoryMethodなどのデザインパターンを利用しなくてもDIコンテナから受け渡されるインスタンスをインターフェースで受け取れば、インターフェースベースのコンポーネント化を実現できる


# Deploy information
## Language used
- Java(Spring-Boot)

## Hosting service
- Heroku

## Brief explanation about how your code works
- パッケージ構成は[こちら](https://terasolunaorg.github.io/guideline/public_review/Overview/ApplicationLayering.html#repository)を参考にしました

## 提出物について
- 提出物は[こちらのgithub](https://github.com/Takaichi01/codecheck-43022)で確認することができます。
- Spring Bootプロジェクトは、herokuにpushしなければ行けないという都合上、git submoduleを使って管理しています。submodule化したプロジェクトファイルは、/source/project 配下になります。
- それとは別に、プロジェクトをコピーしてWebテスト画面からも確認できるようにしています。場所は /source/codecheck-agile-test です。

## DIについて
### DIとは
- DIとは、利用したいクラスのインスタンス生成にnew演算子を使用せず、フィールド/コンストラクタ/setterを利用して外部からインジェクトすることで、クラス間の関連を疎結合に保つ仕組みのこと。
- シングルトンを生成する、セッションごとに生成するなどのクラスのインスタンス化のライフサイクル管理を行う機能を持つ事ができる。

### DIの利点
- テスタビリティの向上
- コンテナ管理されているインスタンスの取得を簡潔にすることができる
- FactoryMethodなどのデザインパターンを利用しなくてもDIコンテナから受け渡されるインスタンスをインターフェースで受け取れば、インターフェースベースのコンポーネント化を実現できる。

### 本実装でのDI
- SpringやJavaEEの参考書にあるように、Controller<->Service<->Repository間接続に関してはDIを用いている
    - RecipesControllerはRecipesServiceをDIしている。こうすることで、RecipesControllerTestではRecipesServiceをMockしてコントローラの品質を担保できる。
    - もしRecipesControllerでRecipesServiceをnewしてしまうと、RecipesControllerTestでRecipesServiceの振る舞いを考慮しなければならない。コントローラのテストにもかかわらず、サービスレイヤの振る舞いの検証もしなくてはならないため、テスタビリティが低下してしまう。
    - 仮にサービスクラスがまだ作成途中で、コントローラから先に開発するといった場合に、個人の環境でスタブを作成するのは負担になる。

## bean 对象的命名规范


在 Android 开发中，一般存在 4 种类型的 bean 对象

- 接口返回的 JSON 对应的对象（值不可修改）
- 页面展示需要的对象（一般由接口返回的对象转换而来）
- 发起网络请求的对象
- 数据库对应的对象

例如 `用户对象`，如果我们将其类名定义为 `User`， 那么 `User` 代表的是接口返回对应的对象还是数据库对应的对象呢？从名称上来看是我们是不知道它属于哪个层次的对象。

据此，bean 的命名规范非常重要，将上面 4 种对象的命名规约设计如下：

- 接口返回的 JSON 对应的对象命名规范为：以 BO 结尾，如 xxxBO
- 页面展示需要的对象命名规范为：以 VO 结尾，如 xxxVO，xxx 表示 View 的名称
- 发起网络请求的对象命名规范为：以 RO 结尾，如 xxxRO，xxx 表示接口名称 (Request Request)
- 数据库对应的对象命名规范为：以 DO 结尾，如 xxxDO，xxx 表示业务名称



## 参考《阿里巴巴 Java 开发手册》

**分层领域模型规约**

- DO（ Data Object）：与数据库表结构一一对应，通过DAO层向上传输数据源对象。
- DTO（ Data Transfer Object）：数据传输对象，Service或Manager向外传输的对象。
- BO（ Business Object）：业务对象。 由Service层输出的封装业务逻辑的对象。
- AO（ Application Object）：应用对象。 在Web层与Service层之间抽象的复用对象模型，极为贴近展示层，复用度不高。
- VO（ View Object）：显示层对象，通常是Web向模板渲染引擎层传输的对象。
- POJO（ Plain Ordinary Java Object）：在本手册中， POJO专指只有setter/getter/toString的简单类，包括DO/DTO/BO/VO等。
- Query：数据查询对象，各层接收上层的查询请求。 注意超过2个参数的查询封装，禁止使用Map类来传输。



**领域模型命名规约**

- 数据对象：xxxDO，xxx即为数据表名。
- 数据传输对象：xxxDTO，xxx为业务领域相关的名称。
- 展示对象：xxxVO，xxx一般为网页名称。
- POJO是DO/DTO/BO/VO的统称，禁止命名成xxxPOJO。
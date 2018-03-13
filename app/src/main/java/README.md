## GreenDao使用步骤

#### 1 引入GreenDao
先在project的build.gradle中引入GreenDao支持插件
```
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'
    }
}
```

然后在app的build.gradle中添加依赖
```
    compile 'org.greenrobot:greendao:3.2.2'
    compile 'org.greenrobot:greendao-generator:3.2.2'
```

#### 2 配置GreenDao
同样是在app的build.gradle中，添加如下配置
```
greendao {
    targetGenDir 'src/main/java'
    daoPackage 'package_name.dao'
}
```
targetGenDir 表示greenDAO生成的DAOMaster和DaoSession的位置
daoPackage   表示根据数据库实体类生成的XXXDao类的路径

#### 3 建立数据库实体类
当实体类创建成功之后， studio会根据此实体类，自动生成XXXDao类并保存在在build.gradle中配置的daoPackage路径下
```
@Entity  
public class User {  
    @Id  
    private Long id;  
    @Property(nameInDb = "USERNAME")  
    private String username;  
    @Property(nameInDb = "NICKNAME")  
    private String nickname;  
}
```
@Entity     表示这个实体类一会会在数据库中生成对应的表

@Id         表示该字段是id

@Property   则表示该属性将作为表的一个字段，其中nameInDb看名字就知道这个属性在数据库中对应的数据名称

**更多注解可以参考GreenDaoDemo中的User类**

#### 4 数据库初始化
```
// 首先获取一个DevOpenHelper对象，这个类有点类似于我们使用的SqliteOpenHelper
DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user.db");

// 通过Helper类获取DaoMaster类
DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());

// 通过DaoMaster获取DaoSession类，此类是操作数据库的关键
DaoSesson daoSession = daoMaster.newSession();
```

#### 5 操作数据库

首先通过DaoSession获取响应的XXXDao对象
```
UserDao userDao = daoSession.getUserDao();
```

插入数据
```
User user = new User();

user.setName(editName.getText().toString());

userDao.insert(user);
```

查询数据
```
List<User> users = userDao.queryBuilder().list();
```

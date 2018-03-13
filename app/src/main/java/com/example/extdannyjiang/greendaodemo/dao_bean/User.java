package com.example.extdannyjiang.greendaodemo.dao_bean;

import com.example.extdannyjiang.greendaodemo.utils.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.example.extdannyjiang.greendaodemo.dao.DaoSession;
import com.example.extdannyjiang.greendaodemo.dao.UserDao;
import com.example.extdannyjiang.greendaodemo.dao.MyOrderDao;

/**
 * Created by ext.danny.jiang on 17/6/20.
 *
 * @Entity 用于标识这是一个需要Greendao帮我们生成代码的bean
 * @Id 标明主键，括号里可以指定是否自增
 * @Property 用于设置属性在数据库中的列名（默认不写就是保持一致）
 * @NotNull 非空
 */

@Entity   //  用于标识这是一个需要Greendao帮我们生成代码的bean
public class User {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "user_name")
    @NotNull
    private String name;

    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> nickNames;

    @Transient
    private int temp;

    @ToMany(referencedJoinProperty = "customId")
    private List<MyOrder> orders;

    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getNickNames() {
        return this.nickNames;
    }

    public void setNickNames(List<String> nickNames) {
        this.nickNames = nickNames;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1446109810)
    public synchronized void resetOrders() {
        orders = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1892052450)
    public List<MyOrder> getOrders() {
        if (orders == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MyOrderDao targetDao = daoSession.getMyOrderDao();
            List<MyOrder> ordersNew = targetDao._queryUser_Orders(id);
            synchronized (this) {
                if(orders == null) {
                    orders = ordersNew;
                }
            }
        }
        return orders;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    @Generated(hash = 411979277)
    public User(Long id, @NotNull String name, List<String> nickNames) {
        this.id = id;
        this.name = name;
        this.nickNames = nickNames;
    }

    @Generated(hash = 586692638)
    public User() {
    }
}

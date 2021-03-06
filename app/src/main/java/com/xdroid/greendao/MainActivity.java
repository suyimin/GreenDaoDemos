package com.xdroid.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.xdroid.greendao.bean.Order;
import com.xdroid.greendao.bean.User;
import com.xdroid.greendao.dao.DaoMaster;
import com.xdroid.greendao.dao.DaoSession;
import com.xdroid.greendao.dao.OrderDao;
import com.xdroid.greendao.dao.UserDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserDao userDao;
    private EditText editName;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<>();
    private EditText editNickName1;
    private EditText editNickName2;
    private OrderDao orderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = ((EditText) findViewById(R.id.editUserName));
        editNickName1 = ((EditText) findViewById(R.id.editUserNickName1));
        editNickName2 = ((EditText) findViewById(R.id.editUserNickName2));
        listView = ((ListView) findViewById(R.id.listView));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);
    }

    public void createDatabase(View view) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user.db");

        daoMaster = new DaoMaster(helper.getWritableDb());

        daoSession = daoMaster.newSession();

        userDao = daoSession.getUserDao();

        orderDao = daoSession.getOrderDao();
    }

    public void insert(View view) {
        User user = new User();

        user.setName(editName.getText().toString());

        List<String> nickNames = new ArrayList<>();
        String nickName1 = editNickName1.getText().toString();
        String nickName2 = editNickName2.getText().toString();
        nickNames.add(nickName1);
        nickNames.add(nickName2);

        user.setNickNames(nickNames);

        long insertID = userDao.insert(user);

        if (insertID >= 0) {
            insertOrders(insertID);
        }
    }

    private void insertOrders(long userId) {
        Order order1 = new Order(null, new Date() + "", userId);
        Order order2 = new Order(null, new Date() + "", userId);

        long orderId1 = orderDao.insert(order1);
        long orderId2 = orderDao.insert(order2);

        if (orderId1 >= 0 && orderId2 >= 0) {
            Toast.makeText(this, "插入 Order 成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void query(View view) {
        List<User> users = userDao.queryBuilder().list();
        list.clear();
        for (User user : users) {
            String name = user.getName();
            List<String> nickNames = user.getNickNames();
            if (nickNames == null || nickNames.size() == 0) {
                list.add("name->" + name);
            } else if (nickNames.size() == 1) {
                list.add("name->" + name + ", nickName1->" + nickNames.get(0));
            } else if (nickNames.size() == 2) {
                list.add("name->" + name + ", nickName1->" + nickNames.get(0) + ", nickName2->" + nickNames.get(1));
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void queryAllOrders(View view) {
        List<User> users = userDao.loadAll();

        list.clear();

        for (User user : users) {
            List<Order> orders = user.getOrders();
            String name = user.getName();

            for (Order order : orders) {
                list.add("name->" + name + ", order date->" + order.getDate());
            }
        }
        adapter.notifyDataSetChanged();
    }
}

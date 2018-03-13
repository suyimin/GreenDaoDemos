package com.example.extdannyjiang.greendaodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.extdannyjiang.greendaodemo.dao.DaoMaster;
import com.example.extdannyjiang.greendaodemo.dao.DaoSession;
import com.example.extdannyjiang.greendaodemo.dao.MyOrderDao;
import com.example.extdannyjiang.greendaodemo.dao.UserDao;
import com.example.extdannyjiang.greendaodemo.dao_bean.MyOrder;
import com.example.extdannyjiang.greendaodemo.dao_bean.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserDao userDao;
    private EditText editName;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<>();
    private EditText editNickName1;
    private EditText editNickName2;
    private MyOrderDao orderDao;

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

        orderDao = daoSession.getMyOrderDao();
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
            Toast.makeText(this, "插入 User 成功", Toast.LENGTH_SHORT).show();

            insertOrders(insertID);
        } else {
            Toast.makeText(this, "插入User 失败", Toast.LENGTH_SHORT).show();
        }

    }

    private void insertOrders(long userId) {
        MyOrder myOrder1 = new MyOrder(null, new Date() + "", userId);
        MyOrder myOrder2 = new MyOrder(null, new Date() + "", userId);

        long orderId1 = orderDao.insert(myOrder1);
        long orderId2 = orderDao.insert(myOrder2);

        if (orderId1 >= 0 && orderId2 >= 0) {
            Toast.makeText(this, "插入 MyOrder 成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void query(View view) {
        List<User> users = userDao.queryBuilder().list();

        list.clear();

        for (User user : users) {
            String name = user.getName();
            List<String> nickNames = user.getNickNames();
            for (String nickName : nickNames) {
                Log.e("TAG", "query " + name + " : " + nickName);
            }
            list.add(user.getName());
        }

        adapter.notifyDataSetChanged();
    }

    public void queryAllOrders(View view) {
        List<User> users = userDao.loadAll();
        for (User user : users) {
            List<MyOrder> orders = user.getOrders();

            for (MyOrder order : orders) {
                Log.e("TAG", "get user " + user.getName() + " order " + order.getDate());
            }
        }
    }
}

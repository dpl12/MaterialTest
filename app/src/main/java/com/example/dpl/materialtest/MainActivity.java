package com.example.dpl.materialtest;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private Fruit[] fruits={new Fruit("Apple",R.drawable.apple),new Fruit("Banana",R.drawable.banana),
            new Fruit("Cherry",R.drawable.cherry),new Fruit("Grape",R.drawable.grape),
            new Fruit("Mango",R.drawable.mango),new Fruit("Orange",R.drawable.orange),
            new Fruit("Pear",R.drawable.pear),new Fruit("Pineapple",R.drawable.pineapple),
            new Fruit("Strawberry",R.drawable.strawberry),new Fruit("Watermelon",R.drawable.watermelon)};
    private FruitAdapter adapter;
    private List<Fruit> fruitList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);//取消原按钮
        toolbar.setNavigationIcon(R.mipmap.head1);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();//需要将ActionDrawerToggle与DrawerLayout的状态同步
        //setNavigationOnClickListener一定要放在setSupportActionBar(toolbar)
        // 和 drawerLayout.addDrawerListener(toggle)之后,不然onclick无效
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);//FloatingActionButton:悬浮按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar提示工具
//                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_SHORT).setAction("Undo",
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
//                            }
//                        }).show();
                //回到列表顶部
                recyclerView.smoothScrollToPosition(0);
            }
        });

        initFruit();
        GridLayoutManager manager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
        adapter=new FruitAdapter(fruitList);//传入数据
        recyclerView.setAdapter(adapter);

        swipeRefresh= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item, menu);
        return true;
    }

    /**
     * Android中在按下back键时会调用到onBackPressed()方法，onBackPressed相对于finish方法
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * toolbar中item选择事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_friends:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.clean:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.create_qun:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.pay:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;
    }

    /**
     * 抽屉菜单选择事件
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_call:
                break;
            case R.id.nav_email:
                break;
            case R.id.nav_friends:
                break;
            case R.id.nav_location:
                break;
            default:
                break;
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 初始化水果列表，使其每次打开显示顺序不同（Random）
     */
    private void initFruit(){
        fruitList.clear();
        for (int i=0;i<60;i++){
            Random random=new Random();
            int index=random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    /**
     * SwipeRefreshLayout的刷新线程
     */
    private void refreshFruit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//刷新时线程沉睡3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {//将线程切换到主线程
                    @Override
                    public void run() {
                        initFruit();//重新生成数据
                        adapter.notifyDataSetChanged();//通知数据发生了变化
                        swipeRefresh.setRefreshing(false);//终止刷新，隐藏刷新进度条
                    }
                });
            }
        }).start();
    }
}




package com.example.qiang_pc.newtalkpal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.adapter.TabFragmentAdapter;
import com.example.qiang_pc.newtalkpal.bean.EventMessage;
import com.example.qiang_pc.newtalkpal.fragment.OrderListFragment;
import com.example.qiang_pc.newtalkpal.fragment.TeacherFragment;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;
import com.example.qiang_pc.newtalkpal.utils.HasLoginUtils;
import com.example.qiang_pc.newtalkpal.utils.SPUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPage;
    private TabLayout mTabLayout;
    private TabFragmentAdapter tabFragmentAdapter;
    private FloatingActionButton fab;
    private NavigationView navigationView;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = getViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = getViewById(R.id.fab);

        DrawerLayout drawer = getViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = getViewById(R.id.nav_view);
    }

    @Override
    protected void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(HasLoginUtils.getToken())){
                    Snackbar.make(view, "您已登录，是否注销？", Snackbar.LENGTH_LONG)
                            .setAction("SIGN OUT", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SPUtils.clear(MainActivity.this);
                                    EventBus.getDefault().post(new EventMessage("退出登录"),
                                            "update_order_list");
                                    showToast("退出成功~");
                                }
                            }).show();
                }else{
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initTabLayout();
    }

    private void initTabLayout() {
        mViewPage= (ViewPager) findViewById(R.id.id_viewpager);
        mTabLayout= (TabLayout) findViewById(R.id.id_tabLayout);
        ArrayList<String> tabTitles = new ArrayList<>();
        tabTitles.add("Teacher");
        tabTitles.add("Appointment");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(1)));

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new TeacherFragment());
        fragments.add(new OrderListFragment());

        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments,
                tabTitles);
        mViewPage.setAdapter(tabFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPage);
        mTabLayout.setTabsFromPagerAdapter(tabFragmentAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_ongoing) {
            // Handle the camera action
            mViewPage.setCurrentItem(1);
        } else if (id == R.id.nav_finished_appointment) {
            //判断是否已经登录，没有则跳转到登录页面，有则跳转到已结束订单页面
            if(!TextUtils.isEmpty(HasLoginUtils.getToken())){
                gotoActivity(DoneOrderActivity.class);
            }else{
                gotoActivity(LoginActivity.class);
            }

        } else if (id == R.id.nav_beome_teacher) {
            gotoActivity(BecomeTeacherActivity.class);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about_us) {

        } else if (id == R.id.nav_contact_service) {

        }else if (id == R.id.nav_user_feedback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }
}

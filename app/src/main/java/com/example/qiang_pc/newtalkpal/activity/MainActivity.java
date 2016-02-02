package com.example.qiang_pc.newtalkpal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.adapter.TabFragmentAdapter;
import com.example.qiang_pc.newtalkpal.fragment.OrderListFragment;
import com.example.qiang_pc.newtalkpal.fragment.TeacherFragment;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPage;
    private TabLayout mTabLayout;
    private TabFragmentAdapter tabFragmentAdapter;
    private FloatingActionButton fab;
    private NavigationView navigationView;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                startActivity(new Intent(MainActivity.this,LoginActivity.class));
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        initTabLayout();
//    }

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
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
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
        } else if (id == R.id.nav_finished_appointment) {

        } else if (id == R.id.nav_beome_teacher) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about_us) {

        } else if (id == R.id.nav_contact_service) {

        }else if (id == R.id.nav_user_feedback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.seproject.crowdfunder.Adarsh.UI.RateUsActivity;
import com.seproject.crowdfunder.BuildConfig;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.adapter.ViewPagerAdapter;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<RequestShortDetails> requestShortDetails = new ArrayList<>();
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/

        setContentView(R.layout.activity_home);



        Toolbar toolbar =  findViewById(R.id.toolbar);
        //toolbar.setTitle(R.string.app_name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start a request", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_bookmarked) {
            startActivity(new Intent(this, Bookmarked.class));

        }else if (id == R.id.nav_start_a_request) {
            startActivity(new Intent(this, StartARequestActivity.class));
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "App should be uploaded to playstore to share it",Toast.LENGTH_LONG).show();
            //share_the_app();

        } else if (id == R.id.nav_rate_us) {
            startActivity(new Intent(this, RateUsActivity.class));
        }else if (id == R.id.nav_contact_us) {

            startActivity(new Intent(this, ContactUs.class));
        }else if (id == R.id.nav_about) {

        }
        else if (id == R.id.nav_distance) {
            startActivity(new Intent(this, Distance.class));
        }
        else if (id == R.id.nav_rate_user) {
            startActivity(new Intent(this, RatingTheUser.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.refresh, menu);
        return false;
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // toggle nav drawer on selecting action bar app icon/title
////        if (dr.onOptionsItemSelected(item)) {
////            return true;
////        }
//        // Handle action bar actions click
//        switch (item.getItemId()) {
//            case R.id.refresh:
//                Toast.makeText(MainActivity.this, "Refreshed",Toast.LENGTH_LONG).show();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }
    private void share_the_app() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }


    public void prepareRequestList() {
        int i = 0;
        while (i < 10) {
            requestShortDetails.add(new RequestShortDetails());
            i++;
        }
    }


}

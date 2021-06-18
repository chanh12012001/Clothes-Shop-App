package com.example.clothersshopapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.clothersshopapp.DBqueries.currentUser;
import static com.example.clothersshopapp.RegisterActivity.setSignUpFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int REWARDS_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;

    public static Boolean showCart = false;

    private FrameLayout frameLayout;
    private ImageView noInternetConnection;
    private int currentFragment = 1;
    TextView actionbarLogo;
    private NavigationView navigationView;

    private Window window;
    private Toolbar toolbar;
    private Dialog signInDialog;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        actionbarLogo = findViewById(R.id.tv_actionbar_logo1);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//        window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_home_navigation);

        //navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout = findViewById(R.id.nav_host_fragment);
        noInternetConnection = findViewById(R.id.no_internet_connection);

        if (showCart) {
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);

        }
        if(currentUser == null){
            navigationView.getMenu().getItem(navigationView.getMenu().size() -1).setEnabled(false);
        }
        else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() -1).setEnabled(true);
        }

        signInDialog = new Dialog(MainActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        Button dialogSignIn = signInDialog.findViewById(R.id.btn_sign_in_dialog);
        Button dialogSignUp = signInDialog.findViewById(R.id.btn_sign_up_dialog);
        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);

        dialogSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableClosebtn = true;
                SignUpFragment.disableClosebtn = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        dialogSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableClosebtn = true;
                SignInFragment.disableClosebtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });

        setFragment(new HomeFragment() , HOME_FRAGMENT);

        NavigationView.OnNavigationItemSelectedListener navListener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if(currentUser != null) {
                    int id = item.getItemId();

                    if (id == R.id.nav_mymall) {
                        actionbarLogo.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                        setFragment(new HomeFragment(), HOME_FRAGMENT);

                    } else if (id == R.id.nav_order) {
                        gotoFragment("My Orders", new MyOrdersFragment(), ORDERS_FRAGMENT);
                    } else if (id == R.id.nav_reward) {
                        gotoFragment("My Rewards", new MyRewardsFragment(), REWARDS_FRAGMENT);
                    } else if (id == R.id.nav_cart) {
                        gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
                    } else if (id == R.id.nav_wishlist) {
                        gotoFragment("My Wishlist", new MyWishlistFragment(), WISHLIST_FRAGMENT);
                    } else if (id == R.id.nav_account) {
                        gotoFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGMENT);
                    } else if (id == R.id.nav_setting) {

                    } else if (id == R.id.nav_logout) {

                    } else if (id == R.id.nav_share) {

                    } else if (id == R.id.nav_help) {

                    } else if (id == R.id.nav_about_us) {

                    }


                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        if (currentFragment == HOME_FRAGMENT) {
                            currentFragment = -1;
                            MainActivity.super.onBackPressed();
                        } else {
                            if (showCart) {
                                showCart = false;
                                finish();
                            } else {
                                actionbarLogo.setVisibility(View.VISIBLE);
                                invalidateOptionsMenu();
                                setFragment(new HomeFragment(), HOME_FRAGMENT);
                                navigationView.getMenu().getItem(0).setChecked(true);
                            }
                        }
                    }
                    return true;
                }
                else {
                    drawer.closeDrawer(GravityCompat.START);
                    signInDialog.show();
                    return false;
                }
            }
        };
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navListener);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);
            if (showCart){
                showCart = false;
                finish();
                return true;
            }
            return true;
        }

        //noinspection SimlifiableIfStatement
        else if (id == R.id.action_search) {
            //todo: search
            return true;
        } else if (id == R.id.action_notification){
            //todo: notification
            return true;
        }  else if (id == R.id.action_shopping_cart) {

            if(currentUser == null){
            signInDialog.show();}
            else {
            gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);}
            return true;
        } else if (id == R.id.home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        actionbarLogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT) {
            navigationView.getMenu().getItem(3).setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }


    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
//            if (fragmentNo == REWARDS_FRAGMENT) {
//                window.setStatusBarColor(Color.parseColor("#5B04B1"));
//                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
//            } else {
//                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
//                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            }
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }
}
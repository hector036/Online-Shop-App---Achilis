package com.example.achilis;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.ZoneOffset;

import static com.example.achilis.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.achilis.RegisterActivity.closeBtnDisabled;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseUser currentUser;

    public static AddressesAdapter addressesAdapterFrag;


    public static final int MANAGE_ADDRESS_FRAG = 3;

    private static final int HOME_FRAGEMENT = 0;
    private static final int CART_FRAGEMENT = 1;
    private static final int ORDER_FRAGEMENT = 2;
    private static final int WISHLIST_FRAGEMENT = 3;
    private static final int ACCOUNT_FRAGEMENT = 4;
    private static final int MYADDRESSES_FRAGEMENT = 5;
    public static Boolean showcart = false;

    private FrameLayout parentFrameLayout;
    private NavigationView navigationView;
    private ImageView actionBarLogo;
    private Dialog signInDialog;
    public static DrawerLayout drawer;
    private TextView badgeCount;

    private int currentFragement = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //currentFragement = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        actionBarLogo = findViewById(R.id.actionbar_logo);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        parentFrameLayout = findViewById(R.id.home_framelayout);

        if (showcart) {
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            goToFragment("My Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGEMENT);
        }


        signInDialog = new Dialog(HomeActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogLogInBtn = signInDialog.findViewById(R.id.login_btn_dialog);

        final Intent registerIntent = new Intent(HomeActivity.this, RegisterActivity.class);

        dialogLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setSignUpFragment = false;
                closeBtnDisabled = true;
                startActivity(registerIntent);
                signInDialog.dismiss();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {

            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);
        } else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);

        }
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (currentFragement == HOME_FRAGEMENT) {
                currentFragement = -1;
                super.onBackPressed();
            } else {

                if (showcart) {
                    showcart = false;
                    finish();
                } else {
                    actionBarLogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGEMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (currentFragement == HOME_FRAGEMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.home, menu);
            MenuItem cartItem = menu.findItem(R.id.home_cart_icon);

                cartItem.setActionView(R.layout.badge_layout);
                ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
                badgeIcon.setImageResource(R.mipmap.cart);
                badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);

                if(currentUser!=null){
                    if (DBqueries.cartList.size() == 0) {
                        DBqueries.loadCartList(HomeActivity.this, new Dialog(HomeActivity.this), false,badgeCount);

                    }else {
                            badgeCount.setVisibility(View.VISIBLE);

                        if (DBqueries.cartList.size() < 99) {
                            badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                        } else {
                            badgeCount.setText("99");
                        }
                    }
                }

                cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentUser == null) {
                            signInDialog.show();

                        } else {
                            goToFragment("My Cart", new MyCartFragment(), CART_FRAGEMENT);
                        }
                    }
                });


        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_cart_icon) {
            if (currentUser == null) {
                signInDialog.show();

            } else {
                goToFragment("My Cart", new MyCartFragment(), CART_FRAGEMENT);
            }
            return true;
        } else if (id == R.id.home_notification_icon) {


            return true;
        } else if (id == R.id.home_search_icon) {


            return true;
        } else if (id == android.R.id.home) {
            if (showcart) {
                showcart = false;
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToFragment(String title, Fragment fragment, int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        actionBarLogo.setVisibility(View.GONE);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);

        if (fragmentNo == CART_FRAGEMENT) {
            navigationView.getMenu().getItem(2).setChecked(true);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (currentUser != null) {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Handle the camera action

                actionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new HomeFragment(), HOME_FRAGEMENT);
            } else if (id == R.id.nav_mycart) {
                goToFragment("My Cart", new MyCartFragment(), CART_FRAGEMENT);

            } else if (id == R.id.nav_mywishlist) {
                goToFragment("My Wish", new MyWishListFragment(), WISHLIST_FRAGEMENT);

            } else if (id == R.id.nav_myaccount) {
                goToFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGEMENT);

            } else if (id == R.id.nav_signout) {

                FirebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                Intent loginIntent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(loginIntent);
                finish();


            } else if (id == R.id.nav_myaddressess) {
                goToFragment("My Addresses", new MyAddressesTestFragment(), MYADDRESSES_FRAGEMENT);

            } else if (id == R.id.nav_setting) {

            } else if (id == R.id.nav_myorder) {
                goToFragment("My Order", new MyOrderFragment(), ORDER_FRAGEMENT);

            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            drawer.closeDrawer(GravityCompat.START);
            signInDialog.show();
            return false;
        }

    }

    private void setFragment(Fragment fragment, int fragmentNo) {

        if (fragmentNo != currentFragement) {
            currentFragement = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }


    }

    public static void refreshItemFrag(int deselect, int select) {

        addressesAdapterFrag.notifyItemChanged(deselect);
        addressesAdapterFrag.notifyItemChanged(select);
    }
}
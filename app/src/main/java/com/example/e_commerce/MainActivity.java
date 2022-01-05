package com.example.e_commerce;

import static com.example.e_commerce.RegisterActivity.setsignUpFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.ui.account.MyAccountFragment;
import com.example.e_commerce.ui.cart.CartFragment;
import com.example.e_commerce.ui.coupon.CouponFragment;
import com.example.e_commerce.ui.home.HomeFragment;
import com.example.e_commerce.ui.live.LiveFragment;
import com.example.e_commerce.ui.order.OrderFragment;
import com.example.e_commerce.ui.sign_out.SignOutFragment;
import com.example.e_commerce.ui.theme.ThemeFragment;
import com.example.e_commerce.ui.wishlist.WishlistFragment;
import com.example.lib.Model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    BottomNavigationView bottomNav;
    private ImageView actionbarLogo;
    private TextView mainEmail;

    private FirebaseUser currentUser;
    private TextView badgeCount;
    private Dialog signInDialog;
    private Dialog loadingDialog;

    //Cart
    public static Boolean showCart = false;
    public static Activity mainActivity;
    private FrameLayout frameLayout;

    //address
    private CircularImageView addProfileIcon;
    public static boolean resetMainActivity = false;
    private CircleImageView profileView;
    private TextView fullname, email;

    private Window window;
    private Toolbar toolbar;


    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_ORDER = 2;
    private static final int FRAGMENT_COUPON = 3;
    private static final int FRAGMENT_CART = 4;
    private static final int FRAGMENT_WISHLIST = 5;
    private static final int FRAGMENT_ACCOUNT = 6;
    private static final int FRAGMENT_THEME = 7;
    private static final int FRAGMENT_LIVE = 8;
    private static final int FRAGMENT_SIGN_OUT = 9;
    private static boolean statusClick = false;

    private int currentFragment = FRAGMENT_HOME;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        actionbarLogo = findViewById(R.id.action_bar_logo);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        WebView w = findViewById(R.id.webView1);

        w.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity( intent );
                return true;
            }

        });

        WebSettings webSettings = w.getSettings();
        webSettings.setJavaScriptEnabled(true);
        w.getSettings().setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        w.setWebChromeClient(new WebChromeClient());
        //Click vào ô hộp thử ở dưới góc phải
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusClick == false) {
                    // Load the asset file by URL. Note the 3 slashes "file:///".
                    w.loadUrl("https://www.kienvuongchemistry.tk/APK/ViewDochat.html");
                    w.setVisibility(View.VISIBLE);
                    statusClick = true;
                }else {
                    w.setVisibility(View.GONE);
                    statusClick = false;
                }
            }
        });
//----------------Click on bottom nav----------------
        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        openHomeFragment();
                        navigationView.setCheckedItem(R.id.nav_home);
                        break;
                    default:
                        openHomeFragment();
                        break;

                }
                return true;
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //Dòng này để khai báo id bên navigation drawer để click hiện lên fragment
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_order,
                R.id.nav_coupon,
                R.id.nav_cart,
                R.id.nav_wishlist,
                R.id.nav_theme,
                R.id.nav_account,
                R.id.nav_sign_out)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);                  //set action bar
        NavigationUI.setupWithNavController(navigationView, navController);//Kiểm soát nút điều hướng của nav
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);//Dòng này để set chọn vào phần tử đầu. Nhưng navigation nó đã mặc định rồi nên khỏi cũng k sao
        bottomNav.getMenu().findItem(R.id.bottom_nav_home).setChecked(true);

        frameLayout = findViewById(R.id.main_frame_layout);

        if (showCart) {
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            openCartFragment();
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), FRAGMENT_HOME);
        }

        fullname = navigationView.getHeaderView(0).findViewById(R.id.main_full_name);
        email = navigationView.getHeaderView(0).findViewById(R.id.main_email_address);
        addProfileIcon = navigationView.getHeaderView(0).findViewById(R.id.add_profile_icon);
        profileView = navigationView.getHeaderView(0).findViewById(R.id.main_profile_image);

        //DiaLog signin
        signInDialog = new Dialog(MainActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);

        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog = new Dialog(MainActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(MainActivity.this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        Button signInDialogBtn = signInDialog.findViewById(R.id.sign_in_btn);
        Button signUpDialogBtn = signInDialog.findViewById(R.id.sign_up_btn);

        signInDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setsignUpFragment = false;
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        signUpDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setsignUpFragment = true;
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == FRAGMENT_HOME) {
                currentFragment = -1;
                super.onBackPressed();
            } else {
                actionbarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new HomeFragment(), FRAGMENT_HOME);
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }

    private void setFragment(Fragment fragment, int fragementNo) {
        if (fragementNo != currentFragment) {
            if (fragementNo == FRAGMENT_COUPON) {
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
            }
            currentFragment = fragementNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; thêm các item vào action bar nếu như nó hiển thị
        if (currentFragment == FRAGMENT_HOME) {
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);

            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            MenuItem cartItem = menu.findItem(R.id.main_cart_icon);
            cartItem.setActionView(R.layout.badge_layout);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.drawable.shopping_cart);
            badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);

            if (DBqueries.email != null) {
                if (DBqueries.cartItemModelList.size() == 0) {
                    DBqueries.loadCartList(MainActivity.this, new Dialog(MainActivity.this), false, badgeCount, new TextView(MainActivity.this));
                } else {
                    badgeCount.setVisibility(View.VISIBLE);
                    if (DBqueries.cartItemModelList.size() < 99) {
                        badgeCount.setText(DBqueries.cartItemModelList.size()+"");
                    } else {
                        badgeCount.setText("99");
                    }
                }

            }
            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (DBqueries.email == null) {
                        signInDialog.show();
                    } else {
                        openCartFragment();
                    }
                }
            });
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        AsyncTask<String, Void, String> task1 = new AsyncTask<String, Void, String>() {
            private String _EMAIL = "";
            private String _NAME = "";
            private String _MATK = "";

            @Override
            protected String doInBackground(String... params) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                _EMAIL = prefs.getString("EMAIL", null);
                _NAME = prefs.getString("NAME", null);
                _MATK = prefs.getString("MATK", null);
                return "Load_Thong_Tin";
            }

            protected void onPostExecute(String result) {
                DBqueries.fullname = _NAME;
                DBqueries.email = _EMAIL;
                DBqueries.profile = _MATK;
                OkHttpClient client = new OkHttpClient();
                AsyncTask<String, Void, String> task4 = new AsyncTask<String, Void, String>() {
                    private JSONObject c = new JSONObject();

                    @Override
                    protected String doInBackground(String... params) {
                        String url = Config.IP_ADDRESS + "/api/user/get/" + _MATK;
                        Request request = new Request.Builder()
                                .url(url)
                                .header("Accept-Encoding", "identity")
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            if (!response.isSuccessful()) {
                                throw new IOException("Unexpected code: " + response);
                            } else {
                                String jsonData = response.body().string();

                                JSONArray json = new JSONArray(jsonData);
                                JSONObject b = new JSONObject(json.get(0).toString());
                                c = b;

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return "ttLoad";
                    }

                    protected void onPostExecute(String result) {
                        try {
                            String ngaySinhTemp = c.getString("NGAYSINH");
                            String[] temp = ngaySinhTemp.split("-");
                            String ngaySinhResult = temp[2] + "/" + temp[1] + "/" + temp[0];
                            System.out.println(ngaySinhResult);

                            DBqueries.userInfomation = new UserModel(c.getString("EMAIL"),c.getString("GIOITINH"),c.getString("TENKHACHHANG"),c.getString("DIACHI"),ngaySinhResult,c.getString("SODIENTHOAI"));
                            loadingDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    ;
                };
                task4.execute("ttLoad");
                if (email != null) {
                    fullname.setText(_NAME);
                    email.setText(_EMAIL);
                    if (DBqueries.profile.equals("")) {
                        addProfileIcon.setVisibility(View.VISIBLE);
                    } else {
                        addProfileIcon.setVisibility(View.INVISIBLE);
                        Glide.with(MainActivity.this).load(DBqueries.profile).apply(new RequestOptions().placeholder(R.mipmap.profile_placeholder)).into(profileView);
                    }
                    if (resetMainActivity) {
                        actionbarLogo.setVisibility(View.VISIBLE);
                        resetMainActivity = false;
                        setFragment(new HomeFragment(), FRAGMENT_HOME);
                        navigationView.getMenu().getItem(0).setChecked(true);
                    }
                    invalidateOptionsMenu();
                } else {
                    DBqueries.fullname = "null";
                    DBqueries.email = "null";
                    DBqueries.profile = "";
                    if (email != null) {
                        fullname.setText("null");
                        email.setText("null");
                        if (DBqueries.profile.equals("")) {
                            addProfileIcon.setVisibility(View.VISIBLE);
                        } else {
                            addProfileIcon.setVisibility(View.INVISIBLE);
                            Glide.with(MainActivity.this).load(DBqueries.profile).apply(new RequestOptions().placeholder(R.mipmap.profile_placeholder)).into(profileView);
                        }
                        if (resetMainActivity) {
                            actionbarLogo.setVisibility(View.VISIBLE);
                            resetMainActivity = false;
                            setFragment(new HomeFragment(), FRAGMENT_HOME);
                            navigationView.getMenu().getItem(0).setChecked(true);
                        }
                        invalidateOptionsMenu();
                    }

                }
                ;
            }
        };
        task1.execute("Load_Thong_Tin");


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (currentUser != null) {  ///my code
            DBqueries.checkNotifications(true, null);
        }

    }

    //------------------------------------------------------Sự kiện click cho menu bar ở trang home
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search_icon) {
            //To do search here
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
            return true;
        } else if (id == R.id.main_cart_icon) {
            if (fullname.getText().toString().equals("")) {
                signInDialog.show();
            } else {
                openCartFragment();
            }
            return true;
        } else if (id == android.R.id.home) {
            if (showCart) {
                mainActivity = null;
                showCart = false;
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                //to do with home
                openHomeFragment();
                bottomNav.getMenu().findItem(R.id.bottom_nav_home).setChecked(true);
                break;
            case R.id.nav_order:
                //to do with order
                Intent intent = new Intent(MainActivity.this,HistoryHoaDon.class);
                startActivity(intent);
                break;
            case R.id.nav_coupon:
                //to do with coupon
                openCouponFragment();
                break;
            case R.id.nav_cart:
                //to do with cart
                openCartFragment();
                break;
            case R.id.nav_wishlist:
                //to do with wishlist
                openWishListFragment();
                break;
            case R.id.nav_account:
                //to do with account
                openAccountFragment();

                break;
            case R.id.nav_theme:
                //to do with theme
                openThemeFragment();

                break;
            case R.id.nav_sign_out:
                FirebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                DBqueries.email = null;  //my code
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("MATK"); // delete data by key key_name3
                editor.remove("NAME"); // delete data by key key_name3
                editor.remove("EMAIL"); // delete data by key key_name3
                editor.apply();
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();
                //openSignOutFragment();
                break;
            default:
                return true;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openHomeFragment() {
        if (currentFragment != FRAGMENT_HOME) {
            replaceFrament(new HomeFragment());
            currentFragment = FRAGMENT_HOME;
        }
    }

    private void openOrderFragment() {
        if (currentFragment != FRAGMENT_ORDER) {
            replaceFrament(new OrderFragment());
            currentFragment = FRAGMENT_ORDER;
        }
    }

    private void openCouponFragment() {
        if (currentFragment != FRAGMENT_COUPON) {
            replaceFrament(new CouponFragment());
            currentFragment = FRAGMENT_COUPON;
        }
    }

    private void openCartFragment() {
        if (currentFragment != FRAGMENT_CART) {
            replaceFrament(new CartFragment());
            currentFragment = FRAGMENT_CART;
        }
    }

    private void openWishListFragment() {
        if (currentFragment != FRAGMENT_WISHLIST) {
            replaceFrament(new WishlistFragment());
            currentFragment = FRAGMENT_WISHLIST;
        }
    }

    private void openAccountFragment() {
        if (currentFragment != FRAGMENT_ACCOUNT) {
            replaceFrament(new MyAccountFragment());
            currentFragment = FRAGMENT_ACCOUNT;
        }
    }

    private void openThemeFragment() {
        if (currentFragment != FRAGMENT_THEME) {
            replaceFrament(new ThemeFragment());
            currentFragment = FRAGMENT_THEME;
        }
    }

    private void openLiveFragment() {
        if (currentFragment != FRAGMENT_LIVE) {
            replaceFrament(new LiveFragment());
            currentFragment = FRAGMENT_LIVE;
        }
    }

    private void openSignOutFragment() {
        if (currentFragment != FRAGMENT_SIGN_OUT) {
            replaceFrament(new SignOutFragment());
            currentFragment = FRAGMENT_SIGN_OUT;
        }
    }

    public void replaceFrament(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
        fragmentTransaction.commit();
    }
}

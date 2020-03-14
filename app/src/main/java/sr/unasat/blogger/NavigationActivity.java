package sr.unasat.blogger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import sr.unasat.blogger.Entity.User;
import sr.unasat.blogger.database.DatabaseHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    DatabaseHelper databaseHelper;
    TextView headerUserName, headerStudNr;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        databaseHelper = new DatabaseHelper(this);


        setSupportActionBar(toolbar);

        initNavigation();

        initHeader();

        int fragment = getIntent().getIntExtra("fragmentLoader",0);



        if(savedInstanceState == null && fragment == 0){
            navigationView.setCheckedItem(R.id.nav_home);
            loadFragment(new FeedFragment());
        }else if(fragment == 1){
            navigationView.setCheckedItem(R.id.nav_account);
            loadFragment(new AccountFragment());
        }



    }

    private void initHeader() {
        View headerView = navigationView.getHeaderView(0);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("loggedInUser");

        headerStudNr = headerView.findViewById(R.id.headerStudNr);
        headerUserName = headerView.findViewById(R.id.headerUsername);

        headerUserName.setText(String.format("%s %s", user.getFirstName(), user.getName()));
        headerStudNr.setText(user.getUsername());
    }

    private void initNavigation() {
        navigationView.bringToFront();
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon );

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            finish();
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                loadFragment(new FeedFragment());
                break;
            case R.id.nav_account:
                loadFragment(new AccountFragment());
                break;
                case R.id.nav_timer:
                loadFragment(new TimerFragment());
                break;
            case R.id.nav_logout:
                showLogoutMessage();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void showLogoutMessage(){
        new MaterialAlertDialogBuilder(this)
                .setTitle(String.format("Ben je zeker %s", user.getFirstName()))
                .setMessage("Als u uitlogt, zult u de laatste berichten niet kunnen volgen")
                .setPositiveButton("Uitloggen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logOut(user.getId());
                    }
                })
                .setNegativeButton("Blijven", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        navigationView.setCheckedItem(R.id.nav_home);
                        loadFragment(new FeedFragment());
                    }
                })
                .show();
    }

    private void logOut(int id) {
        if(databaseHelper.logOutUser(id)){
            Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }


}

package com.example.customer_app;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetHost;
import android.content.Intent;
import android.os.Bundle;

import com.example.customer_app.Interface.ItemClickListener;
import com.example.customer_app.Model_User.category;
import com.example.customer_app.ViewHelper.MenuViewHelper;
import com.example.customer_app.common.common;
import com.example.customer_app.ui.send.SendFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static androidx.recyclerview.widget.RecyclerView.*;
import static com.example.customer_app.R.id.drawer_layout;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference CATEGORY;
    TextView set_user_name;
    RecyclerView recycle_view;
    LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);


        //init Firebase
        database = FirebaseDatabase.getInstance();
        CATEGORY = database.getReference("Category");



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(drawer_layout);
        @SuppressLint("ResourceType") ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Home.this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //set user
        View headerView = navigationView.getHeaderView(0);
        set_user_name = headerView.findViewById(R.id.drawer_title);
        set_user_name.setText(common.current_user.getName());

        //Load menu
        recycle_view = (RecyclerView)findViewById(R.id.recycler_menu);
        recycle_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycle_view.setLayoutManager(layoutManager);



        loadMenu();
    }

    public void loadMenu() {
        FirebaseRecyclerAdapter adapter = null;
        FirebaseRecyclerOptions<category> options = new FirebaseRecyclerOptions.Builder<category>().setQuery(CATEGORY,category.class).build();

             adapter = new FirebaseRecyclerAdapter<category,MenuViewHelper>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MenuViewHelper holder, int position, @NonNull category model) {
                    holder.txtMenuName.setText(model.getName());
                    Picasso.get().load(model.getImage()).into(holder.imageView);
                    final category clickItem = model;
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(Home.this, "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @NonNull
                @Override
                public MenuViewHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
                    return new MenuViewHelper(itemView);
                }
            };
             adapter.startListening();
             recycle_view.setAdapter(adapter);

    }
    /*@Override
    protected void onStop() {
        super.onStop();
        FirebaseRecyclerAdapter adapter = null;
        adapter.stopListening();
    }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

   /* @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.recycler_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
   public boolean onOptionsItemSelected(MenuItem item){
       return super.onOptionsItemSelected(item) ;
   }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment frag = null;
        int id = menuItem.getItemId();
        if(id==R.id.nav_menu){
            //navigate to menu fragment
        }
        else if(id==R.id.nav_cart){
            // navigate to cart fragment
            frag = new SendFragment();
        }
        else if(id==R.id.nav_order){
            //navigate to order fragment
        }
        else if(id==R.id.nav_profile){
            //navigate to profile fragment
        }
        else if(id==R.id.nav_logout){
            //navigate to logout fragment
        }
        DrawerLayout drawer = findViewById(drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.example.newaggregator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newaggregator.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Array Lists for Menu Items
    private ArrayList<String> categories = new ArrayList<String>();
    private ArrayList<NewsObject> newsOrganization = new ArrayList<NewsObject>();
    private ArrayList<ArticleObject> newsArticles = new ArrayList<ArticleObject>();

    // Menu
    private Menu menu;

    // Drawer
    private ActionBarDrawerToggle mDrawerToggle;

    // Sorted List
    private ArrayList<String> items = new ArrayList<String>();

    // View Pager
    private ArticleAdapter articleAdapter;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binder
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // View Pager
        articleAdapter = new ArticleAdapter(this, newsArticles);
        binding.viewPagerMain.setAdapter(articleAdapter);
        binding.viewPagerMain.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


        // Drawer
        binding.leftDrawer.setOnItemClickListener(
                (parent, view, position, id) -> selectItem(position)
        );

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                binding.drawerMain,
                R.string.menu_open,
                R.string.menu_close
        );

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

    }

    @Override
    protected void onStart() {
        if (hasNetworkConnection() != false) {
            // Getting the categories and sources first
            DownloadVolley.getData(this, false, "");
        }
        super.onStart();
    }

    public void initializeDrawer() {
        binding.leftDrawer.setAdapter(new ArrayAdapter<>(this,
                R.layout.list_item, items));
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    // Drawer
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (hasNetworkConnection() != false) {
            // Drawer
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }

            // Options Menu
            categorizeDrawer((String) item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildMenu() {
        menu.clear();


        menu.add(0, 0, 0, "All");
        for (int i = 0; i < categories.size(); i++) {
            menu.add(0, i + 1, i +1, categories.get(i));
        }
    }

    private void selectItem(int position) {
        for (int i = 0; i < newsOrganization.size(); i++) {
            if (items.get(position).equals(newsOrganization.get(i).getName())) {
                setTitle(items.get(i));
                DownloadVolley.getData(this, true, newsOrganization.get(i).getId());
            }
        }

        binding.drawerMain.closeDrawer(binding.leftDrawer);
    }

    public void categorizeDrawer(String c) {
        String comp = c;
        items.clear();

        for (int i = 0; i < newsOrganization.size(); i++) {
            if (c.equals("All")) {
                comp = newsOrganization.get(i).getCategory();
            }

            if (comp.equals(newsOrganization.get(i).getCategory())) {
                items.add(newsOrganization.get(i).getName());
            }
        }

        initializeDrawer();
    }

    public void downloadFailed() {
        items.clear();
        newsOrganization.clear();
        newsArticles.clear();
        categories.clear();

        binding.viewPagerMain.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Data fetch failed!", Toast.LENGTH_SHORT).show();
    }

    public void updateSources(ArrayList<NewsObject> availSources) {
        newsOrganization.clear();
        newsOrganization.addAll(availSources);

        categories.clear();
        items.clear();
        for (int i = 0; i < availSources.size(); i++) {
            items.add(availSources.get(i).getName());
            if (categories.contains(availSources.get(i).getCategory())) {
            } else {
                categories.add(availSources.get(i).getCategory());
            }
        }
        Collections.sort(categories);

        buildMenu();
        initializeDrawer();
    }

    public void updateArticles(ArrayList<ArticleObject> availArticles) {
        newsArticles.clear();
        newsArticles.addAll(availArticles);

        articleAdapter.notifyItemRangeChanged(0, newsArticles.size());
        binding.viewPagerMain.setVisibility(View.VISIBLE);
    }

    public void openBrowser(String url) {
        Intent intent = null;
        startActivity(new Intent(intent.ACTION_VIEW, Uri.parse(url)));
    }
}
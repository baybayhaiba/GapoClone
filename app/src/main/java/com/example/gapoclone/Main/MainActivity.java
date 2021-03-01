package com.example.gapoclone.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Toast;

import com.example.gapoclone.Application.PersonAPI;
import com.example.gapoclone.Main.Fragment.fragment_chats;
import com.example.gapoclone.Main.Fragment.fragment_group;
import com.example.gapoclone.Main.Fragment.Home.fragment_home;
import com.example.gapoclone.Main.Fragment.fragment_notification;
import com.example.gapoclone.Main.Fragment.fragment_watch;
import com.example.gapoclone.Model.Person;
import com.example.gapoclone.R;
import com.example.gapoclone.Utilities.ImageSample;
import com.example.gapoclone.ViewpagerAdapter.ViewpagerAdapterMain;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragments;
    private ViewpagerAdapterMain adapter;
    public static ListenerScroll listenerScroll;

    public interface ListenerScroll {
        void ScrollTop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        fragments = initFragments();
        adapter = new ViewpagerAdapterMain(this, fragments);
        viewPager2.setAdapter(adapter);
        controlChangePage();

        Toast.makeText(this, PersonAPI.getInstance().getName(), Toast.LENGTH_SHORT).show();
    }

    private void initUI() {
        viewPager2 = findViewById(R.id.viewpagerMain);
        bottomNavigationView = findViewById(R.id.bottom_main);
    }

    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new fragment_home());
        fragments.add(new fragment_watch());
        fragments.add(new fragment_group());
        fragments.add(new fragment_chats());
        fragments.add(new fragment_notification());
        return fragments;
    }

    private void controlChangePage() {

        //viewpager click response bottom navigation
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.mnHome).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.mnPlay).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.mnGroup).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.mnChats).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.mnNotification).setChecked(true);
                        break;
                }
            }
        });

        //bottom navigation click response viewpager
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnHome:
                    if (viewPager2.getCurrentItem() == 0)
                        listenerScroll.ScrollTop();

                    viewPager2.setCurrentItem(0);
                    break;
                case R.id.mnPlay:
                    if (viewPager2.getCurrentItem() == 1)
                        listenerScroll.ScrollTop();
                    viewPager2.setCurrentItem(1);
                    break;
                case R.id.mnGroup:
                    if (viewPager2.getCurrentItem() == 2)
                        listenerScroll.ScrollTop();
                    viewPager2.setCurrentItem(2);
                    break;
                case R.id.mnChats:
                    if (viewPager2.getCurrentItem() == 3)
                        listenerScroll.ScrollTop();
                    viewPager2.setCurrentItem(3);
                    break;
                case R.id.mnNotification:
                    if (viewPager2.getCurrentItem() == 4)
                        listenerScroll.ScrollTop();
                    viewPager2.setCurrentItem(4);
                    break;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() != 0)
            viewPager2.setCurrentItem(0, false);
        else
            super.onBackPressed();
    }
}
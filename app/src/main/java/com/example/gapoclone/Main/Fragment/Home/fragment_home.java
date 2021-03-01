package com.example.gapoclone.Main.Fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.gapoclone.Application.PersonAPI;
import com.example.gapoclone.Main.Fragment.Home.Viewpager.fragment_home_discover;
import com.example.gapoclone.Main.Fragment.Home.Viewpager.fragment_home_follow;
import com.example.gapoclone.Main.Fragment.Home.Viewpager.fragment_home_surround;
import com.example.gapoclone.R;
import com.example.gapoclone.ViewpagerAdapter.ViewpagerAdapterHome;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_home newInstance(String param1, String param2) {
        fragment_home fragment = new fragment_home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ImageView imgSearch, imgSetting;
    private TabLayout tabLayout;
    public static ViewPager2 viewPager2;
    private List<Fragment> fragments;
    private ViewpagerAdapterHome adapterHome;
    private final String[] titleFragment = {"Khám phá", "Theo dõi", "Quanh đây"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);
        fragments = initFragment();
        adapterHome = new ViewpagerAdapterHome(getActivity(), fragments);
        viewPager2.setAdapter(adapterHome);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(titleFragment[position]);
        }).attach();

        return view;
    }

    private void initUI(View view) {
        imgSearch = view.findViewById(R.id.img_search);
        imgSetting = view.findViewById(R.id.img_setting);
        tabLayout = view.findViewById(R.id.tabLayoutHome);
        viewPager2 = view.findViewById(R.id.viewpagerHome);

        imgSetting.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
        });
    }

    private List<Fragment> initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new fragment_home_discover());
        fragments.add(new fragment_home_follow());
        fragments.add(new fragment_home_surround());
        return fragments;
    }
}
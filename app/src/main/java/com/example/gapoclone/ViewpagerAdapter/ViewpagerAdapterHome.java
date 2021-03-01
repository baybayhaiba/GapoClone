package com.example.gapoclone.ViewpagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ViewpagerAdapterHome extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public ViewpagerAdapterHome(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        if (fragments != null)
            return fragments.size();
        return 0;
    }
}

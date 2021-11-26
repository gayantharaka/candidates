package com.test.candidates.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentAdapter  extends FragmentStateAdapter {
    Context context;
    ArrayList<Fragment> fragments;
    public FragmentAdapter(FragmentActivity fragmentActivity, Context context, ArrayList<Fragment> fragments) {
        super(fragmentActivity);
        this.context = context;
        this.fragments = fragments;
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }



    @Override
    public int getItemCount() {
        return fragments.size();
    }
}

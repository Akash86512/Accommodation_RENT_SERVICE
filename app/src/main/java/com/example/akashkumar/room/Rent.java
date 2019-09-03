package com.example.akashkumar.room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class Rent extends Fragment {

    public Rent() {
        // Required empty public constructor
    }


    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_rent,container,false); {


            tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
            frameLayout=(FrameLayout)view.findViewById(R.id.frameLayout);


            fragment = new Room();
            fragmentManager = getChildFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    // Fragment fragment = null;
                    switch (tab.getPosition()) {
                        case 0:

                            fragment = new Room();

                            break;
                        case 1:

                            fragment = new Flate();


                            break;
                        case 2:
                            fragment = new Shop();

                            break;
                        case 3:
                            fragment = new Office();
                            break;

                    }
                    FragmentManager fm = getChildFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frameLayout, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            // Inflate the layout for this fragment
            return view;

        }

    }
}
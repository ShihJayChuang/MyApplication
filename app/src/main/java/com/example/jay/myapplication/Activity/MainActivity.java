package com.example.jay.myapplication.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.jay.myapplication.R;
import com.example.jay.myapplication.fragment.CosmoFragment;
import com.example.jay.myapplication.fragment.DecoFragment;
import com.example.jay.myapplication.fragment.GlatimaFragment;
import com.example.jay.myapplication.fragment.ImageListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private CountDownTimer countDownTimer;
    private AlertDialog.Builder ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isNetworkConnected();

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new CosmoFragment(),
                    new DecoFragment(),
                    new GlatimaFragment(),
            };

            private final String[] mFragmentNames = new String[]{
                    getString(R.string.heading_cosmo),
                    getString(R.string.heading_deco),
                    getString(R.string.heading_glatima)
            };

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }

        };
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        //mViewPager.setOffscreenPageLimit(2);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        timeOutDialog();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if (nInfo != null && nInfo.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else
            return false;
    }

    private void timeOutDialog() {

        countDownTimer = new CountDownTimer(15000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("jay", TAG + " countDownTimer seconds : " + (millisUntilFinished / 1000) + "");
            }

            @Override
            public void onFinish() {
                if (ImageListFragment.isTimeout() == true) {

                    ab = new AlertDialog.Builder(MainActivity.this);
                    ab.setTitle("網路連線狀況");
                    ab.setMessage("網路連線問題");
                    ab.setCancelable(false);
                    ab.setPositiveButton("前往設定頁面", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    ab.setNeutralButton("等待", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "等待", Toast.LENGTH_LONG).show();
                            timeOutDialog();
                        }
                    });
                    ab.setNegativeButton("關閉", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "關閉", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                    ab.show();


                }
                Log.e("jay", "ImageListFragment.isTimeout(): " + ImageListFragment.isTimeout());
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

}
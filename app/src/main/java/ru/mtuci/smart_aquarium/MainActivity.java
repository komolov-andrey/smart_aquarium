package ru.mtuci.smart_aquarium;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.mtuci.smart_aquarium.adpter.TabsFragmentAdapter;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private Toast toast;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();

        setContentView(R.layout.parameter);

        TextView feeding_intervals = (TextView) findViewById(R.id.feeding_intervals);

        feeding_intervals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this, FeedingIntervals.class);
                    startActivity(intent);
                }catch(Exception ex) {
                    toast = Toast.makeText(getApplicationContext(), "Ошибка при переходе", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void initTabs() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

}
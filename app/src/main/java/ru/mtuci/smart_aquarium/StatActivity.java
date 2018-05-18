package ru.mtuci.smart_aquarium;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mtuci.smart_aquarium.network.AquariumService;
import ru.mtuci.smart_aquarium.network.AquariumServiceFactory;
import ru.mtuci.smart_aquarium.network.Event;
import ru.mtuci.smart_aquarium.network.Temperature;

public class StatActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        ((TabLayout)findViewById(R.id.tabLayout)).setupWithViewPager(mViewPager);
    }

    public static class LogFragment extends Fragment {
        RecyclerView mRecyclerView;
        ItemAdapter<Event> mItemAdapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_log, container, false);
            mRecyclerView = rootView.findViewById(R.id.recycler);
            mItemAdapter = new ItemAdapter<>();
            FastAdapter fastAdapter = FastAdapter.with(mItemAdapter);
            mRecyclerView.setAdapter(fastAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            return rootView;
        }


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            AquariumService service = AquariumServiceFactory.create();
            Call<List<Event>> events = service.listEvents();
            events.enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    if (response.body() != null) {
                        mItemAdapter.add(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {
                    Toast.makeText(getContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public static class GraphFragment extends Fragment {
        BarChart barChart;
        List<BarEntry> entries = new ArrayList<>();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_graph, container, false);

            barChart = rootView.findViewById(R.id.chart);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new DateAxisValueFormatter());
            return rootView;
        }


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            AquariumService service = AquariumServiceFactory.create();
            Call<List<Temperature>> events = service.listTemperatures();
            events.enqueue(new Callback<List<Temperature>>() {
                @Override
                public void onResponse(Call<List<Temperature>> call, Response<List<Temperature>> response) {
                    if (response.body() != null) {
                        for (Temperature data : response.body()) {
                            entries.add(new BarEntry((float)(data.getDatatime().getTime() / 1000000000), data.getTemperature()));
                        }

                        BarDataSet set = new BarDataSet(entries, "Температура");
                        BarData data = new BarData(set);
                        data.setBarWidth(.5f); // set custom bar width
                        barChart.setData(data);
                        barChart.setFitBars(true); // make the x-axis fit exactly all bars
                        barChart.invalidate(); // refresh
                    }
                }

                @Override
                public void onFailure(Call<List<Temperature>> call, Throwable t) {
                    Toast.makeText(getContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            });
        }

        class DateAxisValueFormatter implements IAxisValueFormatter {

            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd:HH:mm");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return sdf.format(new Date((long)value * 1000000000));
            }

        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new LogFragment();
            } else if (position == 1) {
                return new GraphFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Лог";
                case 1:
                    return "Температура";
            }
            return null;
        }
    }
}

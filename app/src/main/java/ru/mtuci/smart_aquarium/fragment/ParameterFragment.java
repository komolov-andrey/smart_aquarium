package ru.mtuci.smart_aquarium.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.mtuci.smart_aquarium.FeedingIntervals;
import ru.mtuci.smart_aquarium.R;

public class ParameterFragment extends Fragment {

    private View view;
    private Toast toast;

    public static Fragment getInstance() {

        Bundle args = new Bundle();
        ParameterFragment parameterFragment = new ParameterFragment();
        parameterFragment.setArguments(args);

        return parameterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.parameter, container, false);


        TextView feeding_intervals = (TextView) view.findViewById(R.id.feeding_intervals);

        feeding_intervals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getActivity(), FeedingIntervals.class);
                    startActivity(intent);
                }catch(Exception ex) {
                    toast = Toast.makeText(getActivity(), "Ошибка при переходе", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return view;
    }
}
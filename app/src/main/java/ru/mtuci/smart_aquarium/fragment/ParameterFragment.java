package ru.mtuci.smart_aquarium.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mtuci.smart_aquarium.R;

public class ParameterFragment extends Fragment {

    private View view;

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
        return view;
    }
}
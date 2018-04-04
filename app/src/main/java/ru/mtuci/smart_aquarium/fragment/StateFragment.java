package ru.mtuci.smart_aquarium.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mtuci.smart_aquarium.R;

public class StateFragment extends Fragment {

    private View view;

    public static Fragment getInstance() {

        Bundle args = new Bundle();
        StateFragment stateFragment = new StateFragment();
        stateFragment.setArguments(args);

        return stateFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.state, container, false);
        return view;
    }
}

package ru.mtuci.smart_aquarium;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mtuci.smart_aquarium.network.AquariumService;
import ru.mtuci.smart_aquarium.network.AquariumServiceFactory;
import ru.mtuci.smart_aquarium.network.Status;

public class StateFragment extends Fragment {

    TextView mLight;
    TextView mTemperature;
    TextView mPump;
    TextView mFeeding;
    TextView mLoading;


    public static Fragment getInstance() {

        Bundle args = new Bundle();
        StateFragment stateFragment = new StateFragment();
        stateFragment.setArguments(args);

        return stateFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_state, container, false);
        mLight = rootView.findViewById(R.id.light);
        mTemperature = rootView.findViewById(R.id.temperature);
        mPump = rootView.findViewById(R.id.pump);
        mFeeding = rootView.findViewById(R.id.feeding);
        mLoading = rootView.findViewById(R.id.loading);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        AquariumService service = AquariumServiceFactory.create();
        Call<Status> events = service.status();
        events.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                if (status == null) {
                    Toast.makeText(getContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                } else {
                    setStatus(mFeeding, status.isFoodOn());
                    setStatus(mLight, status.isLightOn());
                    setStatus(mPump, status.isPumpOn());
                    mTemperature.setText(formatTemperature(status.getTemperature()));
                    mLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setStatus(TextView view, boolean status) {
        if (status) {
            view.setText("Вкл");
            view.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        } else {
            view.setText("Выкл");
            view.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        }
    }

    String formatTemperature(float temp) {
        return String.valueOf(temp) + "°C";
    }
}

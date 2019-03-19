package ru.mtuci.smart_aquarium;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mtuci.smart_aquarium.network.AquariumService;
import ru.mtuci.smart_aquarium.network.AquariumServiceFactory;

public class ParameterFragment extends Fragment {
    TextView mLightStart;
    TextView mLightEnd;
    TextView mPumpStart;
    EditText mPumpInterval;
    TextView mFeedStart;
    EditText mScrewCount;
    EditText mMinTemperature;
    EditText mMaxTemperature;

    TimePickerDialog mTimePicker = null;

    public static Fragment getInstance() {

        Bundle args = new Bundle();
        ParameterFragment parameterFragment = new ParameterFragment();
        parameterFragment.setArguments(args);

        return parameterFragment;
    }

    public static class AquaSettings {
        static Date currentTime;
        static Date pumpStartTime;
        static Integer pumpWorkInterval;
        static Date lightStartTime;
        static Date lightEndTime;
        static Date feedStartTime;
        static Integer screwCount;
        static Integer minimumTemperature;
        static Integer maximumTemperature;

        static public void setSettings(String settings) {

            //12:50:56_00:00:00_10_00:00:00_00:00:00_00:00:00_01_20_40
            Pattern p = Pattern.compile("(\\d{2}:\\d{2}:\\d{2})_(\\d{2}:\\d{2}:\\d{2})_(\\d{2})_(\\d{2}:\\d{2}:\\d{2})_(\\d{2}:\\d{2}:\\d{2})_(\\d{2}:\\d{2}:\\d{2})_(\\d{2})_(\\d{2})_(\\d{2})");
            Matcher m = p.matcher(settings);
            if (m.matches()) {
                currentTime = parseTime(m.group(1));
                pumpStartTime = parseTime(m.group(2));
                pumpWorkInterval = parseInt(m.group(3));
                lightStartTime = parseTime(m.group(4));
                lightEndTime = parseTime(m.group(5));
                feedStartTime = parseTime(m.group(6));
                screwCount = parseInt(m.group(7));
                minimumTemperature = parseInt(m.group(8));
                maximumTemperature = parseInt(m.group(9));
            }
            //error message?
        }


        @Nullable
        static Date parseTime(String rawTime) {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(rawTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return date;
        }

        @Nullable
        static Integer parseInt(String rawInt) {
            Integer screwCount = null;
            try {
                screwCount = Integer.parseInt(rawInt);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return screwCount;
        }

        static public String getSettings() {
            String settings = "";
            settings = settings.concat(formatTime(currentTime))
                    .concat("_")
                    .concat(formatTime(pumpStartTime))
                    .concat("_")
                    .concat(formatInt(pumpWorkInterval))
                    .concat("_")
                    .concat(formatTime(lightStartTime))
                    .concat("_")
                    .concat(formatTime(lightEndTime))
                    .concat("_")
                    .concat(formatTime(feedStartTime))
                    .concat("_")
                    .concat(formatInt(screwCount))
                    .concat("_")
                    .concat(formatInt(minimumTemperature))
                    .concat("_")
                    .concat(formatInt(maximumTemperature));
            return settings;
        }

        static String formatTime(Date time) {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            return sdf.format(time);
        }

        static String formatInt(Integer param) {
            return String.format(Locale.getDefault(), "%02d", param);
        }

        public static String getPumpStartTime() {
            return formatTime(pumpStartTime);
        }

        public static String getLightStartTime() {
            return formatTime(lightStartTime);
        }

        public static String getLightEndTime() {
            return formatTime(lightEndTime);
        }

        public static String getFeedStartTime() {
            return formatTime(feedStartTime);
        }

        public static void setPumpStartTime(String pumpStartTime) {
            AquaSettings.pumpStartTime = parseTime(pumpStartTime);
        }

        public static void setLightStartTime(String lightStartTime) {
            AquaSettings.lightStartTime = parseTime(lightStartTime);
        }

        public static void setLightEndTime(String lightEndTime) {
            AquaSettings.lightEndTime = parseTime(lightEndTime);
        }

        public static void setFeedStartTime(String feedStartTime) {
            AquaSettings.feedStartTime = parseTime(feedStartTime);
        }

        public static void setPumpWorkInterval(String pumpWorkInterval) {
            AquaSettings.pumpWorkInterval = parseInt(pumpWorkInterval);
        }

        public static void setScrewCount(String screwCount) {
            AquaSettings.screwCount = parseInt(screwCount);
        }

        public static void setMinimumTemperature(String minimumTemperature) {
            AquaSettings.minimumTemperature = parseInt(minimumTemperature);
        }

        public static void setMaximumTemperature(String maximumTemperature) {
            AquaSettings.maximumTemperature = parseInt(maximumTemperature);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_parameter, container, false);
        mLightStart = rootView.findViewById(R.id.lightStart);
        mLightEnd = rootView.findViewById(R.id.lightEnd);
        mPumpStart = rootView.findViewById(R.id.pumpStart);
        mPumpInterval = rootView.findViewById(R.id.pumpInterval);
        mFeedStart = rootView.findViewById(R.id.screwStart);
        mScrewCount = rootView.findViewById(R.id.screwCount);
        mMinTemperature = rootView.findViewById(R.id.min_temp);
        mMaxTemperature = rootView.findViewById(R.id.max_temp);
        rootView.findViewById(R.id.update_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncSettingWithInterface();
                postSettings();
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime((TextView)v);
            }
        };
        mLightStart.setOnClickListener(listener);
        mLightEnd.setOnClickListener(listener);
        mPumpStart.setOnClickListener(listener);
        mFeedStart.setOnClickListener(listener);

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) { // Accept only letter & digits ; otherwise just return
                        Toast.makeText(getContext(), "Слишком большое число", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }

        };

        mPumpInterval.setFilters(new InputFilter[]{filter});

        return rootView;
    }

    void pickTime(final TextView view) {
        if (mTimePicker != null) {
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(AquaSettings.parseTime(view.getText().toString()));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                view.setText(AquaSettings.formatInt(selectedHour).concat(":")
                        .concat(AquaSettings.formatInt(selectedMinute)).concat(":00"));
                mTimePicker = null;
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Выберите время");
        mTimePicker.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AquariumService service = AquariumServiceFactory.create();
        Call<ResponseBody> settings = service.settings();
        settings.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        String raw = response.body().string();
                        AquaSettings.setSettings(raw);
                        setupInterface();
                    }
                } catch (IOException e) {
                    Toast.makeText(getContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postSettings() {
        AquariumService service = AquariumServiceFactory.create();
        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), AquaSettings.getSettings());
        Call<ResponseBody> postSettings = service.postSettings(body);
        postSettings.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Обновлено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setupInterface() {
        mLightStart.setText(AquaSettings.getLightStartTime());
        mLightEnd.setText(AquaSettings.getLightEndTime());
        mPumpStart.setText(AquaSettings.getPumpStartTime());
        mPumpInterval.setText(AquaSettings.pumpWorkInterval.toString());
        mFeedStart.setText(AquaSettings.getFeedStartTime());
        mScrewCount.setText(AquaSettings.screwCount.toString());
        mMinTemperature.setText(AquaSettings.minimumTemperature.toString());
        mMaxTemperature.setText(AquaSettings.maximumTemperature.toString());
    }

    void syncSettingWithInterface() {
        AquaSettings.setLightStartTime(mLightStart.getText().toString());
        AquaSettings.setLightEndTime(mLightEnd.getText().toString());
        AquaSettings.setFeedStartTime(mFeedStart.getText().toString());
        AquaSettings.setPumpStartTime(mPumpStart.getText().toString());
        AquaSettings.setMaximumTemperature(mMaxTemperature.getText().toString());
        AquaSettings.setMinimumTemperature(mMinTemperature.getText().toString());
        AquaSettings.setPumpWorkInterval(mPumpInterval.getText().toString());
        AquaSettings.setScrewCount(mScrewCount.getText().toString());
    }
}
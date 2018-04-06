package ru.mtuci.smart_aquarium;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class FeedingIntervals extends Activity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feeding);

        initToolBar();
    }

    private void initToolBar() {
        toolBar = (Toolbar) findViewById(R.id.my_toolbar_feeding);
        toolBar.inflateMenu(R.menu.menu);
    }
}

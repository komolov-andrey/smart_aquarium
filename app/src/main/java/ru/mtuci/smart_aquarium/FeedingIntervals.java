package ru.mtuci.smart_aquarium;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class FeedingIntervals extends Activity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feeding);

        initToolBar();

        ActionMenuItemView imageButton = (ActionMenuItemView) toolBar.findViewById(R.id.check);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        FloatingActionButton plusButton = (FloatingActionButton) findViewById(R.id.flab);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Добавлено", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private void initToolBar() {
        toolBar = (Toolbar) findViewById(R.id.my_toolbar_feeding);
        toolBar.inflateMenu(R.menu.menu);
    }
}

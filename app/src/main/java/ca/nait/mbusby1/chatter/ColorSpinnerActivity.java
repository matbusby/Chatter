package ca.nait.mbusby1.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class ColorSpinnerActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_spinner);

        Spinner spinner = findViewById(R.id.spinner_color_chooser);
        spinner.setOnItemSelectedListener(new MySpinnerListener());
    }
}

class MySpinnerListener implements AdapterView.OnItemSelectedListener
{
    @Override
    public void onItemSelected(AdapterView<?> spinner, View row, int position, long id)
    {
        View linearLayout = (View) spinner.getParent();
        String bgColour = spinner.getResources().getStringArray(R.array.color_values)[position];
        linearLayout.setBackgroundColor(Color.parseColor(bgColour));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
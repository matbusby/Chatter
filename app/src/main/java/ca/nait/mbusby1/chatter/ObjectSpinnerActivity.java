package ca.nait.mbusby1.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class ObjectSpinnerActivity extends AppCompatActivity
{
    private Spinner spinner;
    private MySpinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_spinner);

        ArrayList chatter = new ArrayList();
        populateArray(chatter);
        adapter = new MySpinAdapter(this, android.R.layout.simple_spinner_item, chatter);
        spinner = (Spinner) findViewById(R.id.spinner_objects);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new MyItemSelectedListener(this));
    }

    private void populateArray(ArrayList chatter)
    {
        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            String url = "http://www.youcode.ca/JitterServlet";
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = in.readLine()) != null)
            {
                Chat temp = new Chat();

                temp.setChatSender(line);

                line = in.readLine();
                temp.setChatMessage(line);

                line = in.readLine();
                temp.setChateDate(line);

                chatter.add(temp);

            }
        } catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}

class MyItemSelectedListener implements AdapterView.OnItemSelectedListener
{
    static ObjectSpinnerActivity activity;

    public MyItemSelectedListener(Context context)
    {
        activity = (ObjectSpinnerActivity)context;
    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View row, int position, long id)
    {
        Chat chat = (Chat) spinner.getAdapter().getItem(position);

        TextView tvSender = activity.findViewById(R.id.text_view_object_sender);
        tvSender.setText(chat.getChatSender());

        TextView tvMessage = activity.findViewById(R.id.text_view_object_message);
        tvMessage.setText(chat.getChatMessage());

        TextView tvDate = activity.findViewById(R.id.text_view_object_date);
        tvDate.setText(chat.getChatDate());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
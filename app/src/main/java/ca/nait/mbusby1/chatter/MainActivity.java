package ca.nait.mbusby1.chatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener
{
    Button buttonPost, buttonView;
    EditText editText;
    SharedPreferences prefs;
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        mainView = findViewById(R.id.linear_layout_main);
        int bgColor = prefs.getInt("ambil_warna_key", 0xffcecece);
        mainView.setBackgroundColor(bgColor);

        buttonPost = findViewById(R.id.button_post_chatter);
        buttonPost.setOnClickListener(this);

        buttonView = findViewById(R.id.button_view_chatter);
        buttonView.setOnClickListener(this);

        editText = findViewById(R.id.edit_text_post_chatter);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button_post_chatter:
            {
                String message = editText.getText().toString();
                postToCloud(message);
                editText.setText("");
                break;
            }
            case R.id.button_view_chatter:
            {
                Intent intent = new Intent(this, ChatterListViewActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void postToCloud(String message)
    {
        String userNameKey = getResources().getString(R.string.preference_user_name_key);
        String userName = prefs.getString(userNameKey, "Unknown");
        try
        {
            // this line creates a virtual browser
            HttpClient client = new DefaultHttpClient();

            HttpPost post = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("DATA", message));
            postParameters.add(new BasicNameValuePair("LOGIN_NAME", userName));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            post.setEntity(formEntity);
            client.execute(post);
        } catch (Exception e)
        {
            Toast.makeText(this, "Error " + e, Toast.LENGTH_LONG).show();
        }
    } // closes postToCloud

    private void getFromCloud()
    {
        BufferedReader in = null;
        TextView textView = findViewById(R.id.text_view_view_chatter);
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/JSONServlet"));
            HttpResponse response = client.execute(request);
            InputStreamReader input = new InputStreamReader(response.getEntity().getContent());
            in = new BufferedReader(input);
            String line = "";

            while ((line = in.readLine()) != null)
            {
                textView.append(line + "\n");
            }
            in.close();
        } catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    } // closes getFromCloud

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    } // closes onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_view_chatter_list_view:
            {
                Intent intent = new Intent(this, ChatterListViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_custom_list_view:
            {
                Intent intent = new Intent(this, CustomListViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_colour_spinner:
            {
                Intent intent = new Intent(this, ColorSpinnerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_object_spinner:
            {
                Intent intent = new Intent(this, ObjectSpinnerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_preferences:
            {
                Intent intent = new Intent(this, PrefsActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
//        String bgColorKey = getResources().getString(R.string.preference_main_bg_key);
//        String bgColor = prefs.getString(bgColorKey, "#cecece");
//        mainView.setBackgroundColor(Color.parseColor(bgColor));
        int bgColor = prefs.getInt("ambil_warna_key", 0xffcecece);
        mainView.setBackgroundColor(bgColor);
    }
} // closes class
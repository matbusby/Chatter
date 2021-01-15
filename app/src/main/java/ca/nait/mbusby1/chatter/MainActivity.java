package ca.nait.mbusby1.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    Button buttonPost, buttonSend;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPost = findViewById(R.id.button_post_chatter);
        buttonPost.setOnClickListener(this);

        buttonSend = findViewById(R.id.button_view_chatter);
        buttonSend.setOnClickListener(this);

        editText = findViewById(R.id.edit_text_chatter);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }


    @Override
    public void onClick(View button)
    {
        switch (button.getId())
        {
            case R.id.button_post_chatter:
            {
                String message = editText.getText().toString();
                postToCloud(message);
                Toast.makeText(this, "Send button pressed " + message, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.button_view_chatter:
            {
                Toast.makeText(this, "View Button Pressed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private void postToCloud(String message)
    {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("DATA", message));
            postParameters.add(new BasicNameValuePair("LOGIN_NAME", "Mat B"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            post.setEntity(formEntity);
            client.execute(post);
        }

        catch (Exception e)
        {

        }
    }
}
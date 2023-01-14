package mk.ukim.finki.movielab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvYear, tvTitle;
    ImageView imageView;

    EditText edMName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edMName = findViewById(R.id.edMName);

        tvYear = findViewById(R.id.tvYear);
        tvTitle = findViewById(R.id.tvTitle);
        imageView = findViewById(R.id.imageView);
    }

    public void search(View view) {

        String mName = edMName.getText().toString();
        if(mName.isEmpty())
        {
            edMName.setError("Invalid name");
        }
        String RequestUrl = "https://www.omdbapi.com/?apikey=60efeff3&t="+ mName;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, RequestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject movie = new JSONObject(response);

                            String result = movie.getString("Response");

                            if(result.equals("True")){
                                Toast.makeText(MainActivity.this, "Movie Found!", Toast.LENGTH_SHORT).show();
                                String plot = movie.getString("Plot");
                                tvYear.setText(plot);
                                String title = movie.getString("Title");
                                tvTitle.setText(title);

                                String imageUrl = movie.getString("Poster");
                                if(imageUrl.equals("N/A")){
                                    Toast.makeText(MainActivity.this, "Poster not found!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Picasso.get().load(imageUrl).into(imageView);
                                }

                            }else{
                                Toast.makeText(MainActivity.this, "Movie not Found!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(request);
    }
}
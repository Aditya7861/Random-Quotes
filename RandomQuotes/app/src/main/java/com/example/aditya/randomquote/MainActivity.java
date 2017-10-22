
//NOTE: your package will be different than mine!
        package com.example.aditya.randomquote;


        import android.content.Intent;
        import android.graphics.Typeface;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import org.json.JSONException;
        import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    // Will show the string "data" that holds the results
    TextView Author;
    TextView Quote;
    // URL of object to be parsed
    String JsonURL = "https://random-quote-generator.herokuapp.com/api/quotes/random";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.menu_main,menu);
        fetch_data();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.about:
                Toast.makeText(this, "HEllo I am Aditya :)", Toast.LENGTH_SHORT).show();
             break;
            case R.id.setting:
                Toast.makeText(this,"Under construction",Toast.LENGTH_LONG).show();
             break;
            case R.id.theme:
                Toast.makeText(this,"Under construction",Toast.LENGTH_SHORT).show();
              break;
         }
         return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        // Creates the Volley request queue

        FloatingActionButton fab2=(FloatingActionButton)findViewById(R.id.next_quote_floatingActionButton);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch_data();
            }
        });
    }
        public void fetch_data()
    {
        requestQueue = Volley.newRequestQueue(this);
        Typeface mycustomfont=Typeface.createFromAsset(getAssets(),"Fonts/wg_silverleaf.ttf");
        // Casts results into the TextView found within the main layout XML with id jsonData
        Author = (TextView) findViewById(R.id.Author);
        Author.setTypeface(mycustomfont);
        Quote=(TextView)findViewById(R.id.Quote);
        Quote.setTypeface(mycustomfont);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            String quote_info = response.getString("quote");
                            String author_info = response.getString("author");

                            // Adds strings from object to the "data" string
                            //   data += "Color Name: " + Author +
                            //       "nDescription : " + desc;

                            Quote.setText(quote_info);
                            Author.setText(author_info);

                            // Adds the data string to the TextView "results"

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);

        //On click listiner
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent sharing_content=new Intent(Intent.ACTION_SEND);
                sharing_content.setType("text/plain");
                sharing_content.putExtra(Intent.EXTRA_SUBJECT,"Subject here");
                sharing_content.putExtra(Intent.EXTRA_TEXT,Quote.getText().toString()+"\n\n\n\n"+ " "+Author.getText().toString());
                startActivity(sharing_content);

            }
        });
    }


}
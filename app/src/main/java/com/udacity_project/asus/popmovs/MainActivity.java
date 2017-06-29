package com.udacity_project.asus.popmovs;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.udacity_project.asus.popmovs.adapter.gridview_adapter.GridviewAdapter;
import com.udacity_project.asus.popmovs.itemobject.ItemObjectMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView GdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTitleBar("Movies Most Playing");
        GdLayout = (GridView)findViewById(R.id.movie_item_list);
        FetchMovies fetchMovies = new FetchMovies();
        String url = "http://api.themoviedb.org/3/movie/popular";
        fetchMovies.execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }
    public void setupTitleBar (String title){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(title);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.rate_movies){
            FetchMovies fetchMovies = new FetchMovies();
            String url = "http://api.themoviedb.org/3/movie/top_rated";
            setupTitleBar("Movies High Rate");
            fetchMovies.execute(url);
            return true;
        }else  if(id == R.id.popular_movies){
            FetchMovies fetchMovies = new FetchMovies();
            String url = "http://api.themoviedb.org/3/movie/popular";
            setupTitleBar("Movies Most Playing");
            fetchMovies.execute(url);
            return true;
        }else  if(id == R.id.upcom_movies){
            FetchMovies fetchMovies = new FetchMovies();
            String url = "http://api.themoviedb.org/3/movie/upcoming";
            setupTitleBar("Movies Up Coming");
            fetchMovies.execute(url);
            return true;
        }else  if(id == R.id.nowplay_movies){
            FetchMovies fetchMovies = new FetchMovies();
            String url = "http://api.themoviedb.org/3/movie/now_playing";
            setupTitleBar("Movies Now Playing");
            fetchMovies.execute(url);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class FetchMovies extends AsyncTask<String, Void, List<ItemObjectMovies>> {
        private final String LOG_TAG = FetchMovies.class.getSimpleName();


        @Override
        protected void onPreExecute() {
            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.status_bar_load);
            relativeLayout.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected List<ItemObjectMovies> doInBackground(String... params) {
            List<ItemObjectMovies> data  = new ArrayList<>();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonResult = "";
            try {
                final String BASE_URL =params[0];
                //put api key for to get value movies from TMDB
                final String API_KEY="?api_key="+BuildConfig.THE_MOVIE_API_KEY;
                Uri builtUri = Uri.parse(BASE_URL+API_KEY).buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to TMDB
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonResult = buffer.toString();
                JSONObject result = new JSONObject(jsonResult);


                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    JSONArray data_json =result.getJSONArray("results");
                    for (int i=0;i<data_json.length();i++)
                    {//set link to get value from tmdb key
                        ItemObjectMovies movie  = new ItemObjectMovies();
                        JSONObject object = data_json.getJSONObject(i);
                        movie.setId(object.getInt("id"));
                        movie.setPoster_path("http://image.tmdb.org/t/p/w185"+object.getString("poster_path"));
                        movie.setBackdrop_path("http://image.tmdb.org/t/p/w780"+object.getString("backdrop_path"));
                        movie.setRelease_date(object.getString("release_date"));
                        movie.setTitle(object.getString("title"));
                        movie.setOverview(object.getString("overview"));
                        movie.setVote_average(object.getLong("vote_average"));
                        data.add(movie);
                    }
                }

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(final List<ItemObjectMovies> movies) {
            super.onPostExecute(movies);
            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.status_bar_load);
            relativeLayout.setVisibility(View.GONE);
            GridviewAdapter gridViewAdapter = new GridviewAdapter(getApplicationContext(),movies);
            GdLayout.setAdapter(gridViewAdapter);
            GdLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //put value to packet in intent for share to detail activity
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ItemObjectMovies movie = movies.get(position);
                    Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                    intent.putExtra("poster_path",movie.getPoster_path());
                    intent.putExtra("backdrop_path",movie.getBackdrop_path());
                    intent.putExtra("year",movie.getRelease_date());
                    intent.putExtra("release",movie.getRelease_date());
                    intent.putExtra("sinopsis",movie.getOverview());
                    intent.putExtra("title",movie.getTitle());
                    intent.putExtra("duration",movie.getVote_average());
                    intent.putExtra("id",movie.getId());
                    startActivity(intent);

                }

            });
        }

    }

}

package com.udacity_project.asus.popmovs;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    ImageView image_background;
    ImageView image_poster;
    TextView text_judul;
    TextView text_rating;
    TextView text_released;
    TextView text_synopsis;
    ImageView img_rate_1;
    ImageView img_rate_2;
    ImageView img_rate_3;
    ImageView img_rate_4;
    ImageView img_rate_5;
    public String isi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar.setTitle(intent.getStringExtra("title"));

        }
        image_background = (ImageView) findViewById(R.id.img_background);
        image_poster = (ImageView) findViewById(R.id.image_post);
        text_judul = (TextView) findViewById(R.id.tv_title);
        text_rating = (TextView) findViewById(R.id.tv_value);
        text_released = (TextView) findViewById(R.id.tv_released_date);
        text_synopsis = (TextView) findViewById(R.id.tv_overview);
        img_rate_1 = (ImageView) findViewById(R.id.rs_rate);
        img_rate_2 = (ImageView) findViewById(R.id.rs_rate2);
        img_rate_3 = (ImageView) findViewById(R.id.rs_rate3);
        img_rate_4 = (ImageView) findViewById(R.id.rs_rate4);
        img_rate_5 = (ImageView) findViewById(R.id.rs_rate5);

        Picasso.with(getApplicationContext())
                .load(intent.getStringExtra("backdrop_path"))
                .placeholder(R.drawable.ic_sync_pic)
                .error(R.drawable.ic_warning_pic)
                .fit()
                .into(image_background);
        Picasso.with(getApplicationContext())
                .load(intent.getStringExtra("poster_path"))
                .placeholder(R.drawable.ic_sync_pic)
                .error(R.drawable.ic_warning_pic)
                .fit()
                .into(image_poster);
        String release_date = intent.getStringExtra("year");
        String tahun = release_date.split("-")[0];

        text_judul.setText(intent.getStringExtra("title") + " (" + tahun + ")");
        text_rating.setText(String.valueOf(intent.getFloatExtra("duration", 0)));
        starRate();
        text_released.setText(intent.getStringExtra("release"));
        text_synopsis.setText(intent.getStringExtra("sinopsis"));
        TextView tx_fav = (TextView) findViewById(R.id.tv_pavo);
        tx_fav.setText(R.string.favorite);
        final CardView btn_fap = (CardView) findViewById(R.id.cd_favo);
        if (btn_fap != null) {
            btn_fap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tx_fav = (TextView) findViewById(R.id.tv_pavo);
                    CardView cd_custom = (CardView) findViewById(R.id.tv_custom);
                    ImageView im_fav = (ImageView) findViewById(R.id.ic_favorite);
                    isi = tx_fav.getText().toString();
                    if (isi.equals("Favorite") ) {
                        tx_fav.setText(R.string.unfavorite);
                        im_fav.setImageResource(R.drawable.ic_star_full);
                        cd_custom.setVisibility(View.VISIBLE);
                        Snackbar.make(v, "This movie has beed add to your favorite", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        tx_fav.setText(R.string.favorite);
                        im_fav.setImageResource(R.drawable.ic_star_border);
                        cd_custom.setVisibility(View.INVISIBLE);
                        Snackbar.make(v, "This movie has beed remove from your favorite", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }

                }
            });
        }


    }

    public void starRate() {
        String rate = text_rating.getText().toString();
        img_rate_1 = (ImageView) findViewById(R.id.rs_rate);
        img_rate_2 = (ImageView) findViewById(R.id.rs_rate2);
        img_rate_3 = (ImageView) findViewById(R.id.rs_rate3);
        img_rate_4 = (ImageView) findViewById(R.id.rs_rate4);
        img_rate_5 = (ImageView) findViewById(R.id.rs_rate5);
        if (rate.equals("1.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_half_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_empty_d);
        } else if (rate.equals("2.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_empty_d);

        } else if (rate.equals("3.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_half_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_empty_d);

        } else if (rate.equals("4.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_empty_d);

        } else if (rate.equals("5.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_half_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_empty_d);

        } else if (rate.equals("6.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_empty_d);

        } else if (rate.equals("7.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_half_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_empty_d);

        } else if (rate.equals("8.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_empty_d);

        } else if (rate.equals("9.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_half_d);

        } else if (rate.equals("10.0")) {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_full_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_full_d);

        } else {
            img_rate_1.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_2.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_3.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_4.setImageResource(R.drawable.ic_star_gren_empty_d);
            img_rate_5.setImageResource(R.drawable.ic_star_gren_empty_d);


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}


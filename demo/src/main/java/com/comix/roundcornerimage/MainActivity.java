package com.comix.roundcornerimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.comix.rounded.RoundedCornerImageView;

public class MainActivity extends AppCompatActivity {

    private static final String IMAGE = "http://o.aolcdn.com/hss/storage/midas/8186eb6dbc7bef04d76df61eab52a98e/205033837/mission-iss01-ed.jpeg";

    RoundedCornerImageView imageView1;
    RoundedCornerImageView imageView2;
    RoundedCornerImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (RoundedCornerImageView) findViewById(R.id.imageView1);
        imageView2 = (RoundedCornerImageView) findViewById(R.id.imageView2);
        imageView3 = (RoundedCornerImageView) findViewById(R.id.imageView3);

        Glide.with(this).load(IMAGE).into(imageView1);
        Glide.with(this).load(IMAGE).into(imageView2);
        Glide.with(this).load(IMAGE).into(imageView3);
    }
}

package com.comix.roundcornerimage;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.comix.rounded.RoundedCornerImageView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MainActivity extends AppCompatActivity {

    private static final String IMAGE
        = "http://o.aolcdn.com/hss/storage/midas/8186eb6dbc7bef04d76df61eab52a98e/205033837/mission-iss01-ed.jpeg";
    private static final String IMAGE_1
        = "http://o.aolcdn.com/hss/storage/midas/8186eb6dbc7bef04d76df61eab52a98e/205033837/mission-iss01-ed.jpeg";

    RoundedCornerImageView imageView1;
    RoundedCornerImageView imageView2;
    RoundedCornerImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (RoundedCornerImageView) findViewById(R.id.imageView1);
        imageView2 = (RoundedCornerImageView) findViewById(R.id.imageView2);
        imageView3 = (RoundedCornerImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);

        Glide.with(this).load(IMAGE).centerCrop().into(imageView1);
        Glide.with(this).load(IMAGE).centerCrop().into(imageView2);
        Glide.with(this).load(IMAGE).centerCrop().into(imageView3);
        Glide.with(this)
            .load(IMAGE)
            .bitmapTransform(new RoundedCornersTransformation(this, 30, 6,RoundedCornersTransformation.CornerType.BOTTOM))
            .centerCrop()
            .into(imageView4);
        Glide.with(this)
            .load(IMAGE)
            .centerCrop()
            .bitmapTransform(new RoundedCornersTransformation(this, 60, 6,RoundedCornersTransformation.CornerType.TOP))
            .into(imageView5);
    }
}

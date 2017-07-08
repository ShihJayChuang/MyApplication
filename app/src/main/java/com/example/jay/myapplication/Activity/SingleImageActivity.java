package com.example.jay.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jay.myapplication.Constants;
import com.example.jay.myapplication.R;

public class SingleImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);

        TextView textView = (TextView) findViewById(R.id.singleTextView);
        ImageView imageView = (ImageView) findViewById(R.id.singleImageView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String typeName = bundle.getString(Constants.TYPENAME);
        String pathUrl = bundle.getString(Constants.PATHURL);

        textView.setText(typeName);
        Glide.with(SingleImageActivity.this).load(pathUrl).into(imageView);

    }
}

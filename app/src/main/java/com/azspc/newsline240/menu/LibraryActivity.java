package com.azspc.newsline240.menu;

import android.os.Bundle;
import android.view.View;

import com.azspc.newsline240.Aztivity;
import com.azspc.newsline240.R;

public class LibraryActivity extends Aztivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
    }

    public void backToPosts(View v) {
        super.backToPosts(v);
    }

}

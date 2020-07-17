package com.azspc.newsline240;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

@SuppressLint("Registered")
public class Aztivity extends AppCompatActivity {
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    public void rotate360(View v) {
        if (v != null) v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate360));
    }

    public void backToPosts(View v) {
        finish();
    }

    public String[] getFromCloud(String url, String dName) {
        try {
            ReadableByteChannel rbc = Channels.newChannel(new URL(url).openStream());
            FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), dName));
            fos.getChannel().transferFrom(rbc, 0, Build.VERSION.SDK_INT > 23 ? Long.MAX_VALUE : 8 * 1024);
            fos.close();
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(),
                    "Погане підключення до інтернету, неможливо оновити пости.",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), e + "", Toast.LENGTH_SHORT).show();
        }
        try {
            String line;
            ArrayList<String> ret = new ArrayList<>();
            BufferedReader bread = new BufferedReader(new InputStreamReader(openFileInput(dName)));
            while ((line = bread.readLine()) != null) ret.add(line);
            ret.remove(ret.size() - 1);
            return ret.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{"no data"};
    }
}

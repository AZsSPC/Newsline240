package com.azspc.newsline240;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.azspc.newsline240.menu.InfoActivity;
import com.azspc.newsline240.menu.SettingsActivity;
import com.azspc.newsline240.menu.UpdateActivity;
import com.azspc.newsline240.post.Post;
import com.azspc.newsline240.post.PostAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Aztivity {

    public static String
            savePost = "posts",           //CHANGEABLE
            saveData = "data",            //CHANGEABLE
            saveCheat = "cheat",          //CHANGEABLE
            version = "4.3",              //CHANGEABLE
            post_separator = "│",         //FINAL
            id_moder = "isModer",         //FINAL
            id_url_posts = "urlPost",     //FINAL
            id_url_update = "urlUp",      //FINAL
            id_version = "version",       //FINAL
            id_v_info = "vInfo",          //FINAL
            id_cheat = "isCheat",         //FINAL
            id_id = "verId",         //FINAL
            id_pass = "verPas",         //FINAL
            id_key = "verKey",         //FINAL
            data_load_url =               //FINAL
                    "https://raw.githubusercontent.com/AZsSPC/AZs240/master/usable/data_az.txt";
    public boolean isMenuVisible = false;
    public static SharedPreferences sp;
    private RecyclerView postRecView;

    protected void onResume() {
        super.onResume();
        if (sp.getBoolean(id_cheat, false))
            findViewById(R.id.cheat_fab).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postRecView = findViewById(R.id.ms_rw);
        postRecView.setLayoutManager(new LinearLayoutManager(this));
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        firstRead();
    }

    public void firstRead() {
        try {
            ArrayList<String> arr = new ArrayList<>(Arrays.asList(getFromCloud(data_load_url, saveData)));
            SharedPreferences.Editor ed = sp.edit();
            for (String s : arr)
                ed.putString(s.split(post_separator)[0], s.split(post_separator)[1]);
            ed.apply();
            reloadPosts(null);
            if (!version.equals(sp.getString(id_version, version))) {
                Toast.makeText(getBaseContext(), getText(R.string.need_update).toString()
                        .replaceAll("%vNew%", sp.getString(id_version, "/lost version/")
                                .replaceAll("%vSec%", version)), Toast.LENGTH_LONG).show();
                versionInfo(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Post> getInitialData() {
        ArrayList<Post> ret = new ArrayList<>();
        String[] posts = getFromCloud(sp.getString(id_url_posts, "no url"), savePost);
        int succes = 0;
        int fail = 0;
        for (String post : posts) {
            try {
                String[] d = post.split(post_separator);
                ret.add(0, new Post(getResources(),
                        d[0],
                        d[1].replaceAll("%n", "\n"),
                        d[2],
                        d[3].replaceAll("0", getString(R.string.sys))
                                .replaceAll("", getString(R.string.nobody))));
                succes++;
            } catch (Exception e) {
                Log.e("Post parser", e + "");
                if (sp.getBoolean(id_moder, false))
                    ret.add(0, new Post(getResources(), getString(R.string.err),
                            post.replaceAll(post_separator, "\n---------------\n")
                                    .replaceAll("%n", "\n"), "0", getString(R.string.sys)));
                fail++;
            }
        }
        if (sp.getBoolean(id_moder, false))
            ret.add(0, new Post(getResources(), getString(R.string.err_stat), ""
                    + "Должно быть отображено: " + posts.length + "\n"
                    + "Успешно отображено: " + succes + "\n"
                    + "Не отображено из-за ошибки: " + fail, "0", getString(R.string.sys)));
        if (ret.size() < 1)
            ret.add(0, new Post(getResources(), getString(R.string.sys), "Видимо, постов нет", "0", getString(R.string.sys)));
        return ret;
    }

    public void reloadPosts(View v) {
        postRecView.setAdapter(new PostAdapter(getBaseContext(), getInitialData()));
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        anim.setDuration(500);
        postRecView.startAnimation(anim);
        if (v != null) rotate360(v);
        hideMenu(null);
    }

    public void menuFab(View v) {
        if (v != null) rotate360(v);
        findViewById(R.id.ms_menu).setVisibility((isMenuVisible = !isMenuVisible) ? View.VISIBLE : View.INVISIBLE);
        findViewById(R.id.ms_menu).startAnimation(AnimationUtils.loadAnimation(this, isMenuVisible ? R.anim.fade_in : R.anim.fade_out));
    }

    public void infoScreen(View v) {
        if (v != null) rotate360(v);
        startActivity(new Intent(this, InfoActivity.class));
    }

    public void createPost(View v) {
        if (v != null) rotate360(v);
        Toast.makeText(getBaseContext(), getString(R.string.err), Toast.LENGTH_LONG).show();
        // startActivity(new Intent(this, CreatePostActivity.class));
    }

    public void settingsOpen(View v) {
        if (v != null) rotate360(v);
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void versionInfo(View v) {
        if (v != null) rotate360(v);
        startActivity(new Intent(this, UpdateActivity.class));
    }

    public void openCheat(View v) {
        if (v != null) rotate360(v);
        Toast.makeText(getBaseContext(), getString(R.string.err), Toast.LENGTH_LONG).show();
        //  startActivity(new Intent(this, CheatActivity.class));
    }

    public void hideMenu(View v) {
        if (isMenuVisible) {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            if (v != findViewById(R.id.ms_menu)) anim.setStartOffset(600);
            findViewById(R.id.ms_menu).startAnimation(anim);
        }
        findViewById(R.id.ms_menu).setVisibility((isMenuVisible = false) ? View.VISIBLE : View.INVISIBLE);
    }

}

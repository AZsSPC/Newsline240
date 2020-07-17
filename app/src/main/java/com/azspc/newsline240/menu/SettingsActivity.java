package com.azspc.newsline240.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.azspc.newsline240.Aztivity;
import com.azspc.newsline240.R;

import static com.azspc.newsline240.MainActivity.*;

public class SettingsActivity extends Aztivity {
    int pressCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pressCount = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ((Switch) findViewById(R.id.sw_moder)).setChecked(sp.getBoolean(id_moder, false));
        ((EditText) findViewById(R.id.et_id)).setText(sp.getString(id_id, ""));
        ((EditText) findViewById(R.id.et_pass)).setText(sp.getString(id_pass, ""));
        ((EditText) findViewById(R.id.et_key)).setText(sp.getString(id_key, ""));
    }

    public void setIsModer(View v) {
        sp.edit().putBoolean(id_moder, ((Switch) v).isChecked()).apply();
        Toast.makeText(getBaseContext(), getText(R.string.sw_moder) + ": " + (sp.getBoolean(id_moder, false) ? "ON" : "OFF"), Toast.LENGTH_LONG).show();
    }

    public void checkAccount(View v) {
        rotate360(v);
        sp.edit()
                .putString(id_id, ((EditText) findViewById(R.id.et_id)).getText().toString())
                .putString(id_pass, ((EditText) findViewById(R.id.et_pass)).getText().toString())
                .putString(id_key, ((EditText) findViewById(R.id.et_key)).getText().toString())
                .apply();
        Toast.makeText(getBaseContext(), getString(R.string.err), Toast.LENGTH_LONG).show();
    }

    public void backToPosts(View v) {
        if (pressCount == 12) {
            sp.edit().putBoolean(id_cheat, true).apply();
            Toast.makeText(getBaseContext(), getString(R.string.cheat_on), Toast.LENGTH_LONG).show();
        }
        super.backToPosts(v);
    }

    public void checkCheat(View v) {
        pressCount++;
    }
}

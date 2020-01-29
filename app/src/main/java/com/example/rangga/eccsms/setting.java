package com.example.rangga.eccsms;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


public class setting extends AppCompatActivity {
     private TextView public_key ;
     private ClipData myClip;
     private ClipboardManager keyClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar settingToolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(settingToolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences key_preference = getApplicationContext().getSharedPreferences(String.valueOf(R.string.preference_name), MODE_PRIVATE);
        final SharedPreferences.Editor key_editor = key_preference.edit();
        final SharedPreferences key_getter = getSharedPreferences(String.valueOf(R.string.preference_name), MODE_PRIVATE);
        if(key_preference.contains("privateKey") && key_preference.contains("publicKey")){
            public_key = findViewById(R.id.public_key);
            public_key.setText(key_getter.getString("publicKey","Empty"));
            TextView private_key = (TextView) findViewById(R.id.private_key);
            private_key.setText(key_getter.getString("privateKey","Empty"));
        }





        Button generateButton = findViewById(R.id.generate);
        Button copy = findViewById(R.id.copy);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyPair pair ;
                PrivateKey priv = null;
                PublicKey pub = null ;
                String privateKey;
                String publicKey;
                try {
                    pair = RSA.generateKeyPair();
                    pub = pair.getPublic();
                    priv = pair.getPrivate();

                } catch (Exception e) {

                    e.printStackTrace();
                }

                byte[] privateKeyBytes = priv.getEncoded();
                byte[] publicKeyBytes = pub.getEncoded();
                String privateKeyString = Base64.getEncoder().encodeToString(privateKeyBytes);
                String publicKeyString = Base64.getEncoder().encodeToString(publicKeyBytes);


                publicKey = publicKeyString;

                privateKey = privateKeyString;

                key_editor.putString("publicKey", publicKey);
                key_editor.putString("privateKey", privateKey);
                key_editor.commit();
                TextView public_key = (TextView) findViewById(R.id.public_key);
                public_key.setText(publicKey);
                TextView private_key = (TextView) findViewById(R.id.private_key);
                private_key.setText(privateKey);
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = public_key.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                keyClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Public Key Copied",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;

    }
}

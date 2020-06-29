package com.example.rangga.eccsms;


import android.content.Intent;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import java.security.KeyFactory;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.PublicKey;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar maintoolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(maintoolbar);
        getSupportActionBar().setTitle("Send Message");
        Button sendBtn = (Button) findViewById(R.id.sendBtn);
        final EditText message,key;
        message =findViewById(R.id.message);
        key = findViewById(R.id.key);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    send(message,key);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void send(EditText message, EditText _key ) throws Exception {


        TextView encrypted_message =  findViewById(R.id.encrypted_msg);
        encrypted_message.setText(_key.getText().toString());
        String key,msg;
        key = _key.getText().toString();
        msg=message.getText().toString();

        byte[] publicKeyBytes = Base64.decode(key,Base64.DEFAULT);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        String cipherText = RSA.encrypt(msg,publicKey);
        encrypted_message.setText(cipherText);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, cipherText);
        startActivity(Intent.createChooser(shareIntent, null));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_keySetting: {
                Intent Activity_setting = new Intent(getApplicationContext(), setting.class);
                startActivity(Activity_setting);
                break;
            }
            case R.id.act_decryption:{
                Intent Activity_decrypt = new Intent(getApplicationContext(), Decrypt.class);
                startActivity(Activity_decrypt);
                break;
            }
        }

        return true;
    }

}

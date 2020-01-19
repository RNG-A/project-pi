package com.example.rangga.eccsms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static com.example.rangga.eccsms.RSA.generateKeyPair;

public class setting extends AppCompatActivity {
    static String privateKey;
    String publicKey;
    static PrivateKey priv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar settingToolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(settingToolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        KeyPair pair = null;

        PublicKey pub = null;
        try {
            pair = generateKeyPair();
            pub = pair.getPublic();
            priv = pair.getPrivate();

        } catch (Exception e) {
            TextView public_key = (TextView) findViewById(R.id.public_key);
            public_key.setText("cant get key");
            TextView private_key = (TextView) findViewById(R.id.private_key);
            private_key.setText("cant get key");
            e.printStackTrace();
        }

        byte[] privateKeyBytes = priv.getEncoded();
        byte[] publicKeyBytes = pub.getEncoded();
        String privateKeyString = Base64.getEncoder().encodeToString(privateKeyBytes);
        String publicKeyString = Base64.getEncoder().encodeToString(publicKeyBytes);


        publicKey = publicKeyString;

        privateKey = privateKeyString;


        Button generateButton = (Button) findViewById(R.id.generate);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView public_key = (TextView) findViewById(R.id.public_key);
                public_key.setText(publicKey);
                TextView private_key = (TextView) findViewById(R.id.private_key);
                private_key.setText(privateKey);
            }
        });


    }
    public static PrivateKey getPrivateKey(){
        return priv;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;

    }
}

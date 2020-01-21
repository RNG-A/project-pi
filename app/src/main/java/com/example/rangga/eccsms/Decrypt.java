package com.example.rangga.eccsms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import javax.crypto.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class Decrypt extends AppCompatActivity {
    String msg;
    PrivateKey privateKey;
    byte[] privateKeyBytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        Toolbar settingToolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(settingToolbar);
        getSupportActionBar().setTitle("Decrypt");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        EditText decrypted_msg = findViewById(R.id.decrypted_message);


        decrypted_msg.setText("2");

        decrypted_msg.setText("1");
        //privateKey = setting.getPrivateKey();
        decrypted_msg.setText("0.5");




        Button decrypt_button = findViewById(R.id.decrypt);

        decrypt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText encrypted_msg = findViewById(R.id.message_to_decrypt);
                    msg = encrypted_msg.getText().toString();
                    EditText key = (EditText)findViewById(R.id.private_key);
                    String private_key = key.getText().toString();
                    privateKeyBytes = Base64.decode(private_key.getBytes(), Base64.DEFAULT);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                    privateKey = keyFactory.generatePrivate(privateKeySpec);
                    EditText decrypted_msg = findViewById(R.id.decrypted_message);
                    //decrypted_msg.setText("0");
                   String plaintext = RSA.decrypt(msg,privateKey);
                   decrypted_msg.setText(plaintext);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

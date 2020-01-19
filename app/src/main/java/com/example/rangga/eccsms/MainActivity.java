package com.example.rangga.eccsms;

import android.app.PendingIntent;
import android.content.Intent;

import android.telephony.SmsManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.security.KeyFactory;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.PublicKey;




import static java.nio.charset.StandardCharsets.UTF_8;





public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar maintoolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(maintoolbar);
        getSupportActionBar().setTitle("Messages");
        Button sendBtn = (Button) findViewById(R.id.sendBtn);
        final EditText mobileno,message,key;
        mobileno=(EditText)findViewById(R.id.number);
        message = (EditText)findViewById(R.id.message);
        key = (EditText)findViewById(R.id.key);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    sendSMS(mobileno,message,key);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void sendSMS(EditText mobileno, EditText message, EditText _key ) throws Exception {


        TextView encrypted_message = (TextView) findViewById(R.id.encrypted_msg);
        encrypted_message.setText(_key.getText().toString());
        String key,no,msg;
        key = _key.getText().toString();
        no=mobileno.getText().toString();
        msg=message.getText().toString();

        byte[] publicKeyBytes = Base64.decode(key,Base64.DEFAULT);
        //byte[] decoded = Base64.decode(publicKeyBytes, Base64.DEFAULT);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        String cipherText = RSA.encrypt(msg,publicKey);
        encrypted_message.setText(cipherText);
        /*
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        SmsManager sms=SmsManager.getDefault();
        if(key.isEmpty() || cipherText.isEmpty() == true){
            try {


                sms.sendTextMessage(no, null, msg, pi, null);
                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
            }
            catch (Exception e ){
                Toast.makeText(MainActivity.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            try {
                sms.sendTextMessage(no, null, cipherText, pi, null);
                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
                }
            catch (Exception e) {
                Toast.makeText(MainActivity.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }
            }*/
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

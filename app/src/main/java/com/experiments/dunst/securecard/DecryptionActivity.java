package com.experiments.dunst.securecard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;


public class DecryptionActivity extends ActionBarActivity {

    EditText email;
    String mEmail;
    com.gc.materialdesign.views.ButtonRectangle Submit;
    NiftyDialogBuilder dialogBuilder;

    //Sharedprefernces
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decryption);
        pref = getSharedPreferences("Registration", 0);

        email = (EditText) findViewById(R.id.editText);
        Submit = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = email.getText().toString();
                if (mEmail.length() <= 0) {
                    dialogBuilder = NiftyDialogBuilder.getInstance(DecryptionActivity.this);
                    dialogBuilder
                            .withTitle("Empty email")
                            .withMessage("Please enter your email")
                                    //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#11000000")                              //def
                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                            .withDialogColor("#2980b9")
                            .show();
                } else {
                    decrypt();
                }
            }
        });

    }

    public void decrypt() {
        //decryption
        String encrypted = pref.getString("encrypted", null);
        String password1 = mEmail;
        Log.d("Decry", encrypted);
//        Log.d("Decry", encrypted);
        try {
            String messageAfterDecrypt = AESCrypt.decrypt(password1, encrypted);
            dialogBuilder = NiftyDialogBuilder.getInstance(DecryptionActivity.this);
            dialogBuilder
                    .withTitle("Decrypting...")
                    .withMessage(messageAfterDecrypt)
                            //.withTitle(null)  no title
                    .withTitleColor("#FFFFFF")                                  //def
                    .withDividerColor("#11000000")                              //def
                    .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                    .withDialogColor("#2980b9")
                    .show();
            Log.d("Decry", encrypted);
        } catch (GeneralSecurityException e) {
            //handle error - could be due to incorrect password or tampered encryptedMsg
            dialogBuilder = NiftyDialogBuilder.getInstance(DecryptionActivity.this);
            dialogBuilder
                    .withTitle("Decrypting...")
                    .withMessage("Wrong email")
                            //.withTitle(null)  no title
                    .withTitleColor("#FFFFFF")                                  //def
                    .withDividerColor("#11000000")                              //def
                    .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                    .withDialogColor("#2980b9")
                    .show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_decryption, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

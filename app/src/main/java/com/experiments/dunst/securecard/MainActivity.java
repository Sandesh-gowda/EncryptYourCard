package com.experiments.dunst.securecard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.experiments.dunst.securecard.textwatcher.FourDigitCardFormatWatcher;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.scottyab.aescrypt.AESCrypt;
import java.security.GeneralSecurityException;

public class MainActivity extends Activity {
    String encryptedMsg;
    String message;
    com.gc.materialdesign.views.ButtonRectangle Submit;
    EditText email, card, mm, yy, cvc;
    String mEmail, mCard, mMonth, mYear, mCvc;
    NiftyDialogBuilder dialogBuilder;

    // Saving the card
    SharedPreferences pref;
    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setting components
        pref = getSharedPreferences("Registration", 0);
        Submit = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.submit);
        email = (EditText) findViewById(R.id.email);
        card = (EditText) findViewById(R.id.cardnumber);
        card.addTextChangedListener(new FourDigitCardFormatWatcher());
        mm = (EditText) findViewById(R.id.month);
        yy = (EditText) findViewById(R.id.year);
        cvc = (EditText) findViewById(R.id.cvc);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = email.getText().toString();
                mCard = card.getText().toString();
                mMonth = mm.getText().toString();
                mYear = yy.getText().toString();
                mCvc = cvc.getText().toString();
                if (mEmail.length() <= 0) {
                    dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
                    dialogBuilder
                            .withTitle("Empty email")
                            .withMessage("Please enter your email")
                                    //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#11000000")                              //def
                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                            .withDialogColor("#2980b9")
                            .show();
                } else if (mCard.length() <= 0) {
                    dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
                    dialogBuilder
                            .withTitle("Empty card")
                            .withMessage("Please enter your card number")
                                    //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#11000000")                              //def
                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                            .withDialogColor("#2980b9")
                            .show();
                } else if (mMonth.length() <= 0) {
                    dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
                    dialogBuilder
                            .withTitle("Empty month")
                            .withMessage("Please enter the expiry month of the card")
                                    //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#11000000")                              //def
                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                            .withDialogColor("#2980b9")
                            .show();
                } else if (mYear.length() <= 0) {
                    dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
                    dialogBuilder
                            .withTitle("Empty year")
                                    //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#11000000")                              //def
                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                            .withDialogColor("#2980b9")
                            .withMessage("Please enter your expiry year of the card")
                            .show();
                } else if (mCvc.length() <= 0) {
                    dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
                    dialogBuilder
                            .withTitle("Empty cvc")
                                    //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#11000000")                              //def
                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                            .withDialogColor("#2980b9")
                            .withMessage("Please enter your valid cvc")
                            .show();
                } else {
                    //  encrypt();
                    dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
                    dialogBuilder
                            .withTitle("Modal Dialog")                                  //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#11000000")                              //def
                            .withMessage("This card of yours is getting encrypted" + "\n" + mEmail + " " + mCard + " " + mMonth + " " + mYear + " " + mCvc)                     //.withMessage(null)  no Msg
                            .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                            .withDialogColor("#2980b9")                               //def  | withDialogColor(int resid)
                            .withIcon(getResources().getDrawable(R.drawable.ic_launcher))
                            .withDuration(700)                                          //def
                            .withEffect(Effectstype.Fliph)                                         //def Effectstype.Slidetop
                            .withButton1Text("Encrypt")                                      //def gone
                            .isCancelableOnTouchOutside(true)                           //.setCustomView(View or ResId,context)
                            .setButton1Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    message = mCard + "\n" + mMonth + "\n" + mYear + "\n" + mCvc;
//                                    dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
//                                    dialogBuilder
//                                            .withTitle("Encrypting...")
//                                            .withMessage(message)
//                                            .show();
                                    //     Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                                    String password = mEmail;

                                    try {
                                        encryptedMsg = AESCrypt.encrypt(password, message);
                                        dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
                                        dialogBuilder
                                                .withTitle("Encrypted card is")
                                                        //.withTitle(null)  no title
                                                .withTitleColor("#FFFFFF")                                  //def
                                                .withDividerColor("#11000000")                              //def
                                                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                                                .withDialogColor("#2980b9")
                                                .withMessage(encryptedMsg)
                                                .show();
                                        Log.d("Encry", encryptedMsg);
                                        SharedPreferences.Editor editor = pref.edit();
                                        pref = getSharedPreferences(
                                                "Registration", 0);
                                        editor.putString("encrypted",
                                                encryptedMsg);
                                        editor.putString("email",
                                                mEmail);

                                        editor.commit();

                                    } catch (GeneralSecurityException e) {
                                        //handle error
                                    }
                                }
                            })

                            .show();
                }

            }
        });
    }

    public void encrypt() {
        String password = mEmail;

        try {
            encryptedMsg = AESCrypt.encrypt(password, message);
            dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
            dialogBuilder
                    .withTitle("Encrypted card is")
                            //.withTitle(null)  no title
                    .withTitleColor("#FFFFFF")                                  //def
                    .withDividerColor("#11000000")                              //def
                    .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                    .withDialogColor("#2980b9")
                    .withMessage(encryptedMsg)
                    .show();
            Log.d("Encry", encryptedMsg);
        } catch (GeneralSecurityException e) {
            //handle error
        }
    }


    public void decrypt() {
        //decryption
        String encrypted = pref.getString("encrypted", null);
        String password1 = pref.getString("email", null);
        Log.d("Decry", encrypted);
//        Log.d("Decry", encrypted);
        try {
            String messageAfterDecrypt = AESCrypt.decrypt(password1, encrypted);
            dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
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
            dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
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

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent i = new Intent(MainActivity.this, DecryptionActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

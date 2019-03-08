package com.example.notificationapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ModifyUserActivity extends AppCompatActivity{

   private static final String URL_DELETE = "https://usiuflyers.000webhostapp.com/deleteuser.php?email=";
    private EditText editTextEmail, EditTextPickGroup;
    private Button buttonRemoveUser, buttonModifyUser;
    private Spinner PickGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        buttonRemoveUser=(Button) findViewById(R.id.buttonRemoveUser);
        buttonModifyUser=(Button) findViewById(R.id.buttonModifyUser);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        EditTextPickGroup=(EditText)findViewById(R.id.EditTextPickGroup);

        buttonRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteEntry();
            }
        });

        buttonModifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmUpdateEntry();
            }
        });
    }



    private void deleteEntry(){
        final String email=editTextEmail.getText().toString().trim().toLowerCase();

        class DeleteEntry extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ModifyUserActivity.this, "Deleting...", "Wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("")){
                    Toast.makeText(ModifyUserActivity.this,"Check your Network connection", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ModifyUserActivity.this,s, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                RegisterUserClass ruc= new RegisterUserClass();
                String s = ruc.sendGetRequestParam(URL_DELETE,email);
                return s;
            }
        }

        DeleteEntry de = new DeleteEntry();
        de.execute();
        editTextEmail.setText("");
    }

    private void confirmDeleteEntry(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to remove this user?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEntry();

                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(ModifyUserActivity.this,MainActivity.class);
        startActivity(intent);
        //intent.putExtra("username",username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }
}

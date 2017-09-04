package ac.myblogapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import ac.myblogapp.R;
import ac.myblogapp.model.AuthenticationRequest;
import ac.myblogapp.model.ErrorResponse;
import ac.myblogapp.model.MessageResponse;
import ac.myblogapp.network.APImanager;
import ac.myblogapp.network.CustomResponseListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends BaseActivity {

    EditText username, password;

    public static void startActivity(Activity startingActivity) {
        Intent intent = new Intent(startingActivity, AuthenticationActivity.class);
        startingActivity.startActivity(intent);

        //so that the user cannot go back to login screen
        startingActivity.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sign in or Register");
        setContentView(R.layout.activity_authentication);

        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    //perform login
                    login();
                }
            }
        });

        Button registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFormValid()) {
                    // perform register
                    register();
                    username.setText("");
                    password.setText("");
                }
            }
        });

        //initialise progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please wait");
    }

    private boolean isFormValid() {
        if (username.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Username Please", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Password Please", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void register() {
        showProgressDialog(true);
        APImanager.getApiInterface().registration(new AuthenticationRequest(username.getText().toString().trim(), password.getText().toString().trim()))
                .enqueue(new RegisterResponseListener());
    }

    private class RegisterResponseListener extends CustomResponseListener<MessageResponse> {
        @Override
        public void onSuccessfulResponse(MessageResponse response) {
            showProgressDialog(false);
            showAlert("Welcome", response.getMessage());
        }

        @Override
        public void onFailedResponse(ErrorResponse errorResponse) {
            showProgressDialog(false);
            showAlert("Registration Failed", errorResponse.getError());

        }
    }

    private void login() {
        //Use code below when conducting mock up
        //new loginTask().execute(username.getText().toString(), password.getText().toString());

        showProgressDialog(true);
        APImanager.getApiInterface().login(new AuthenticationRequest(username.getText().toString().trim(), password.getText().toString().trim()))
                //Cleaned up code by using Network.CustomResponseListener class
                .enqueue(new LoginResponseListener());
    }

    ;

    //Mock up class
    /*class loginTask extends AsyncTask<String, Void, Boolean>{

        String mockUser = "test";
        String mockPass = "password";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showProgressDialog(false);
            if(aBoolean){
                showAlert("Welcome", "You are logged in");

            } else showAlert("Disaster", "I don't know you");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            //simulate connection to server by creating delay
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return username.contentEquals(mockUser)&& password.contentEquals(mockPass);
        }
    }*/


    private class LoginResponseListener extends CustomResponseListener<MessageResponse> {

        @Override
        public void onSuccessfulResponse(MessageResponse response) {
            showProgressDialog(false);
            BlogEntryListActivity.startActivity(AuthenticationActivity.this);
        }

        @Override
        public void onFailedResponse(ErrorResponse errorResponse) {
            showProgressDialog(false);
            showAlert("Login Failed", errorResponse.getError());

        }
    }
}
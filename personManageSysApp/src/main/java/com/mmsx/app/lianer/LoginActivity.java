package com.mmsx.app.lianer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mmsx.app.personManagement.PersonDAO;
import com.mmsx.sqlapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
	public static String USER=null;
	private Button login;
    private TextView title;
    private EditText userName,password;
    @Override
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.logininterface);
        /*
		 * 引入组件
		 */
        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        title=(TextView)findViewById(R.id.textView3);
	    title.setTextColor(Color.BLUE);	    	   	    
	    final PersonDAO personDAO = new PersonDAO(this);
	    login.setOnClickListener(new Button.OnClickListener(){
        	Intent intent=null;   	      	
			@Override
			public void onClick(View arg0) {
				if(userName.getText().toString().equals("admin")
						&&password.getText().toString().equals("admin")){
					USER=userName.getText().toString();
					intent=new Intent(LoginActivity.this, MainActivity.class);
					Toast.makeText(LoginActivity.this, "login successfully！^_^", Toast.LENGTH_LONG).show();
				    startActivity(intent);
					finish();
				}
				else{
					Toast.makeText(LoginActivity.this, "The password or username is incorrect！", Toast.LENGTH_LONG).show();
				}			
			}    	
        });

        
    }

}

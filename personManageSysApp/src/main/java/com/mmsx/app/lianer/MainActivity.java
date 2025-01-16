package com.mmsx.app.lianer;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mmsx.app.department.DepartmentActivity;
import com.mmsx.app.personManagement.Person;
import com.mmsx.app.personManagement.PersonDAO;
import com.mmsx.app.personManagement.PersonListActivity;
import com.mmsx.sqlapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button staffmanagement,departmentmanegement,close;

    @Override
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);
        
        /*
		 * Lead-in module
		 */
        staffmanagement=(Button)findViewById(R.id.staffmanagement);
        departmentmanegement=(Button)findViewById(R.id.departmentmanegement);
        close=(Button)findViewById(R.id.close);

	    PersonDAO personDAO = new PersonDAO(this);
        Person person = personDAO.findName(LoginActivity.USER);
        System.out.println(person);

	    //Personnel management
	    staffmanagement.setOnClickListener(new OnClickListener(){
        	Intent intent=null;
			@Override
			public void onClick(View arg0) {
				intent=new Intent(MainActivity.this, PersonListActivity.class);
				startActivity(intent);

			}    	
        });

        //Department management
	    departmentmanegement.setOnClickListener(new OnClickListener(){
        	Intent intent=null;
			@Override
			public void onClick(View arg0) {
				intent=new Intent(MainActivity.this, DepartmentActivity.class);
				startActivity(intent);
			}    	
        });


        //Safe exit
        close.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				System.exit(0);
			}    	
        });
        
    }

}

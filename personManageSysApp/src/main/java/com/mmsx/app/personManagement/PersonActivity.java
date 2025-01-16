package com.mmsx.app.personManagement;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mmsx.sqlapp.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class PersonActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "Add";
    private EditText ednumber, edname, editPhone, edage, editSalary, edjob,editMoney,editFa;
    private Spinner spdepartment, spinner_power,spinner_meno,spinner_fa;
    private RadioButton radio1, radio2;
    private Person person = null;
    PersonDAO personDAO = new PersonDAO(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_management);

        if (this.getIntent() != null && this.getIntent().getExtras() != null) {
            Serializable serializable = this.getIntent().getExtras().getSerializable("Person");
            if (serializable != null) {
                person = (Person) serializable;
            }
        }
        /*
         * 引入组件
         */
        ednumber = (EditText) findViewById(R.id.ednumber);
        edname = (EditText) findViewById(R.id.edname);
        edage = (EditText) findViewById(R.id.edage);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editSalary = (EditText) findViewById(R.id.editSalary);
        edjob = (EditText) findViewById(R.id.edjob);
        spdepartment = (Spinner) findViewById(R.id.spdepartment);
        spinner_power = (Spinner) findViewById(R.id.spinner_power);
        radio1 = (RadioButton) findViewById(R.id.radio0);
        radio2 = (RadioButton) findViewById(R.id.radio1);
        findViewById(R.id.buadd).setOnClickListener(this);
        spinner_meno = (Spinner) findViewById(R.id.spinner_meno);
        spinner_fa = (Spinner) findViewById(R.id.spinner_fa);
        findViewById(R.id.budelete).setOnClickListener(this);

        editMoney = (EditText) findViewById(R.id.editMoney);
        editFa = (EditText) findViewById(R.id.editFa);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departmentName, android.R.layout.simple_spinner_item);
        spdepartment.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.powerName, android.R.layout.simple_spinner_item);
        spinner_power.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.array1, android.R.layout.simple_spinner_item);
        spinner_meno.setAdapter(adapter3);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.array2, android.R.layout.simple_spinner_item);
        spinner_fa.setAdapter(adapter4);

		if (person != null){
            findViewById(R.id.budelete).setVisibility(View.VISIBLE);
			List<String> list = Arrays.asList(getResources().getStringArray(R.array.departmentName));
			List<String> list2 = Arrays.asList(getResources().getStringArray(R.array.powerName));
            List<String> list3 = Arrays.asList(getResources().getStringArray(R.array.array1));
            List<String> list4 = Arrays.asList(getResources().getStringArray(R.array.array2));

			edname.setText(person.getName());
			edage.setText(String.valueOf(person.getAge()));
			spdepartment.setSelection(list.indexOf(person.getDepartment()),true);
			spinner_power.setSelection(list2.indexOf(person.getGrade()),true);
            spinner_meno.setSelection(list3.indexOf(person.getRewards()),true);
            spinner_fa.setSelection(list4.indexOf(person.getFamily()),true);

            editMoney.setText(person.getMoney());
            editFa.setText(person.getFname());

			edjob.setText(person.getJob());
			ednumber.setText(person.getNumber());
			editSalary.setText(person.getSalary());
			editPhone.setText(person.getPhone());

			if (person.getSex().equalsIgnoreCase("男")){
				radio1.setChecked(true);
			}else {
				radio2.setChecked(true);
			}
            Button button = findViewById(R.id.buadd);
            button.setText("update");
            setTitle("Employee information edit");
		}else {
            findViewById(R.id.budelete).setVisibility(View.GONE);
            setTitle("Employee information addition");
        }


    }

    private void delete() {
        Builder builder = new Builder(this);
        builder.setTitle("Tips");
        builder.setMessage("Are you sure to delete this employee information？");
        builder.setPositiveButton(R.string.btnOK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    personDAO.delete(ednumber.getText().toString());
                    Toast.makeText(PersonActivity.this, "Employee number is" + ednumber.getText().toString() + "the employee was deleted successfully！", Toast.LENGTH_LONG).show();
                    finish();
                } catch (Exception e) {
                    Log.i("Delete", e.getMessage());
                }
            }
        });
        builder.setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        builder.create().show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.budelete:
                delete();

                break;
            //增加
            case R.id.buadd:

                if (ednumber.getText().toString().equals("")) {
                    Toast.makeText(PersonActivity.this, "The employee id cannot be empty！", Toast.LENGTH_LONG).show();
                    return;
                }
                if (edname.getText().toString().equals("")) {
                    Toast.makeText(PersonActivity.this, "The name cannot be empty！", Toast.LENGTH_LONG).show();
                    return;
                }

                if (person != null) {
                    person.setName(edname.getText().toString());
                    person.setAge(Integer.valueOf(edage.getText().toString()));
                    person.setDepartment(spdepartment.getSelectedItem().toString());
                    person.setGrade(spinner_power.getSelectedItem().toString());
                    person.setJob(edjob.getText().toString());
                    person.setNumber(ednumber.getText().toString());
                    person.setSalary(editSalary.getText().toString());
                    person.setSex(radio1.isChecked() ? "male" : "female");
                    person.setPhone(editPhone.getText().toString());
                    person.setFamily(spinner_fa.getSelectedItem().toString());
                    person.setRewards(spinner_meno.getSelectedItem().toString());
                    person.setMoney(editMoney.getText().toString());
                    person.setFname(editFa.getText().toString());

                    personDAO.update(person);
                    Toast.makeText(PersonActivity.this, "Update successfully！", Toast.LENGTH_LONG).show();

                    finish();
                } else {
                    Person person1 = personDAO.find(ednumber.getText().toString());
                    if (person1 != null){
                        Toast.makeText(PersonActivity.this, "An employee with this number already exists！", Toast.LENGTH_LONG).show();
                        return;
                    }
                    person = new Person();
                    person.setName(edname.getText().toString());
                    person.setAge(Integer.valueOf(edage.getText().toString()));
                    person.setDepartment(spdepartment.getSelectedItem().toString());
                    person.setGrade(spinner_power.getSelectedItem().toString());
                    person.setJob(edjob.getText().toString());
                    person.setNumber(ednumber.getText().toString());
                    person.setSalary(editSalary.getText().toString());
                    person.setSex(radio1.isChecked() ? "male" : "female");
                    person.setPhone(editPhone.getText().toString());

                    person.setFamily(spinner_fa.getSelectedItem().toString());
                    person.setRewards(spinner_meno.getSelectedItem().toString());
                    person.setMoney(editMoney.getText().toString());
                    person.setFname(editFa.getText().toString());

                    personDAO.add(person);
                    Toast.makeText(PersonActivity.this, "added successfully！", Toast.LENGTH_LONG).show();
                    finish();
                }

                break;
            default:
                break;
        }
    }
}

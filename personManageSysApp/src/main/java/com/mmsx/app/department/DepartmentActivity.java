package com.mmsx.app.department;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mmsx.sqlapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class DepartmentActivity extends AppCompatActivity implements OnClickListener {
    final int DIALOG_DELETE = 0;
    final int DIALOG_UPDATE = 1;
    private static final String TAG = "Add";
    private Button add, find, update, delect;
    private EditText departmentID, principal, tel;
    private ListView listview;
    private Spinner departmentName;
    private TextView datashow;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_management);
        setTitle("Administrator management");

        add = (Button) findViewById(R.id.add);
        find = (Button) findViewById(R.id.find);
        update = (Button) findViewById(R.id.select);
        delect = (Button) findViewById(R.id.selectAll);
        departmentID = (EditText) findViewById(R.id.departmentID);
        departmentName = (Spinner) findViewById(R.id.departmentName);
        principal = (EditText) findViewById(R.id.edID);
        tel = (EditText) findViewById(R.id.tel);
        listview = (ListView) findViewById(R.id.list);
        datashow = (TextView) findViewById(R.id.datashow);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departmentName, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentName.setAdapter(adapter);
        departmentName.setPrompt("Please select a department");
        add.setOnClickListener(this);
        find.setOnClickListener(this);

        updateList();

        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (departmentID.getText().toString().equals("")) {
                    Toast.makeText(DepartmentActivity.this, "The department id cannot be empty！", Toast.LENGTH_LONG).show();
                } else {
                    Department depertment = departmentDAO.find(departmentID.getText().toString());
                    if (depertment == null) {
                        Toast.makeText(DepartmentActivity.this, "This department does not exist！", Toast.LENGTH_LONG).show();
                        empty();
                    } else {
                        showDialog(DIALOG_UPDATE);
                    }
                }
            }
        });

        delect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!departmentID.getText().toString().equals("")) {
                    Department depertment = departmentDAO.find(departmentID.getText().toString());
                    if (depertment == null) {
                        Toast.makeText(DepartmentActivity.this, "This department does not exist！", Toast.LENGTH_LONG).show();
                        empty();
                    } else {
                        showDialog(DIALOG_DELETE);
                    }
                } else {
                    Toast.makeText(DepartmentActivity.this, "Please enter the number of the department you wish to delete", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void updateList() {
        Cursor cursor = departmentDAO.select();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.departmentitem, cursor, new String[]{"_id", "departmentID", "departmentName", "principal", "tel"}, new int[]{R.id._id, R.id.tvdid, R.id.tvdname, R.id.tvdprincipal, R.id.tvdtel});
        listview.setAdapter(cursorAdapter);
        datashow.setText(" ");
    }

    public void empty() {
        departmentID.setText("");
        departmentName.setSelection(0);
        principal.setText("");
        tel.setText("");
        updateList();
    }

    DepartmentDAO departmentDAO = new DepartmentDAO(this);

    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        Builder builder = new Builder(this);
        switch (id) {
            case DIALOG_DELETE: //Delete the department with the specified number
                builder.setTitle("Tips");
                builder.setMessage("Confirm to delete this department information？");
                builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        try {
                            departmentDAO.delete(departmentID.getText().toString());
                            Toast.makeText(DepartmentActivity.this, "id" + departmentID.getText().toString() + "the department was deleted successfully！", Toast.LENGTH_LONG).show();
                            empty();
                        } catch (Exception e) {
                            Log.i("Delete", e.getMessage());
                        }
                    }
                });
                builder.setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });
                dialog = builder.create();
                break;
            case DIALOG_UPDATE:
                builder.setTitle("Tips");
                builder.setMessage("Confirm to edit this department information？");
                builder.setPositiveButton(R.string.btnOK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        try {
                            Department depertment = departmentDAO.find(departmentID.getText().toString());
                            if (!departmentName.getSelectedItem().toString().equals(""))
                                depertment.setDepartmentName(departmentName.getSelectedItem().toString());
                            if (!principal.getText().toString().equals(""))
                                depertment.setPrincipal(principal.getText().toString());
                            if (!tel.getText().toString().equals(""))
                                depertment.setTel(tel.getText().toString());
                            departmentDAO.update(depertment);
                            Toast.makeText(DepartmentActivity.this, "Modified successfully", Toast.LENGTH_LONG).show();
                            datashow.setText("The new data is：" + "\n" + "Department number:" + departmentID.getText().toString() + ",Department name:" + departmentName.getSelectedItem().toString() + ",Department head：" + principal.getText().toString() + ",Contact information:" + tel.getText().toString());
                            empty();
                        } catch (Exception e) {
                            Log.i("Update", e.getMessage());
                            Toast.makeText(DepartmentActivity.this, "An Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }

                });
                dialog = builder.create();
                break;
            default:
                break;
        }
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                try {
                    if (departmentID.getText().toString().equals("")) {
                        Toast.makeText(DepartmentActivity.this, "The department number cannot be empty！", Toast.LENGTH_LONG).show();
                    } else {
                        Department department = departmentDAO.find(departmentID.getText().toString());
                        if (department == null) {
                            department = new Department(departmentID.getText().toString(), departmentName.getSelectedItem().toString(), principal.getText().toString(), tel.getText().toString());
                            departmentDAO.add(department);
                            Toast.makeText(DepartmentActivity.this, "Successfully added！", Toast.LENGTH_LONG).show();
                            datashow.setText("The newly added data is：" + "\n" + "Department id:" + departmentID.getText().toString() + ",Department name:" + departmentName.getSelectedItem().toString() + ",Department head：" + principal.getText().toString() + ",phone number：" + tel.getText().toString());
                            empty();
                        } else {
                            Toast.makeText(DepartmentActivity.this, "The department with this id already exists！", Toast.LENGTH_LONG).show();
                            empty();
                        }
                    }
                } catch (Exception e) {
                    Log.i(TAG, "An Error Occurred");
                    Log.i(TAG, e.getMessage());
                }
                break;
            //Search all departments
            case R.id.find:

                Department department = departmentDAO.find(departmentID.getText().toString());
                if (department != null)
                    datashow.setText("id：" + department.getDepartmentID() + " Department name：" + department.getDepartmentName() + " Department head：" + department.getPrincipal() + " phone number：" + department.getTel());
                else
                    Toast.makeText(DepartmentActivity.this, "This department does not exist！！", Toast.LENGTH_LONG).show();
                empty();

                break;
            default:
                break;
        }
    }
}

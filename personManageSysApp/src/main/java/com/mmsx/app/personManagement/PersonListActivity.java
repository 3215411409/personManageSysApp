package com.mmsx.app.personManagement;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.mmsx.sqlapp.R;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PersonListActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private ListAdapter adapter;
	private List<Person> personList;
	@SuppressLint({"Range", "MissingInflatedId"})
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_list);
		setTitle("Employee list ");

		PersonDAO.getInstance(this.getApplication());
		recyclerView = findViewById(R.id.listView);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//竖向列表
		recyclerView.setLayoutManager(layoutManager);

		adapter = new ListAdapter();
		recyclerView.setAdapter(adapter);

		adapter.setOnItemClickListener(new ListAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, Person note) {
				Intent intent = new Intent(PersonListActivity.this, PersonActivity.class);
				intent.putExtra("Person", note);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Loads the menu resource to the current menu resource
		getMenuInflater().inflate(R.menu.activity_memo,menu);
		return true;
	}

	//菜单的点击事件
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()){
			case R.id.main_add_view:
				startActivity(new Intent(PersonListActivity.this, PersonActivity.class));
				break;
			case R.id.main_search_view:
				View inflate = LayoutInflater.from(this) //To set the context, the argument is this Java code file. This
						.inflate(R.layout.dialog_search, null); //Load the layout file with the first parameter being the layout file resource and the second being null
				EditText editText = inflate.findViewById(R.id.editSearch);

				AtomicInteger type = new AtomicInteger();
				RadioGroup radioGroup = inflate.findViewById(R.id.radioGroup);
				radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
					if (i == R.id.radioButton1){
						editText.setHint("Please enter your name to search");
						type.set(0);
					}else if (i == R.id.radioButton2){
						type.set(1);
						editText.setHint("Please enter the id to search");
					}

				});

				AlertDialog alertDialog = new AlertDialog.Builder(this)
						.setTitle("Query employee")
						.setView(inflate) //Set the layout parameter class
						.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								String text = editText.getText().toString();
								personList = PersonDAO.getInstance(null).queryAll(type.get(), text);
								adapter.setPersonList(personList);
								adapter.notifyDataSetChanged();

							}
						})
						.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								//Cancel the logic after the button is clicked
							}
						})
						.create();
				alertDialog.show();



				break;
			default:super.onOptionsItemSelected(item);
		}
		return true;
	}


	@Override
	protected void onResume() {
		refreshNoteList();
		super.onResume();
	}

	private void refreshNoteList() {
		if (adapter != null){
			personList = PersonDAO.getInstance(this.getApplication()).getAllData();
			adapter.setPersonList(personList);
			adapter.notifyDataSetChanged();
			findViewById(R.id.main_empty_view).setVisibility(personList.size() == 0 ? View.VISIBLE : View.GONE);
		}
	}



}

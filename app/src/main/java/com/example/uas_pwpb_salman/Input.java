package com.example.uas_pwpb_salman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Input extends AppCompatActivity {

    public static String EXTRA_ACT = "act";
    public static String EXTRA_NOTE = "note";
    String act;
    int itemID;
    Context context;
    Note daNote;
    EditText edtTitle,edtDetail;
    Button btnInput;

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        context = this;
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        edtTitle = findViewById(R.id.edtTitle);
        edtDetail = findViewById(R.id.edtDetail);

        btnInput = findViewById(R.id.btnInput);

        act = getIntent().getStringExtra(EXTRA_ACT);
        if(act.equals("update")){
            ab.setTitle("Ubah");
            btnInput.setText("UPDATE");
            daNote = extractingResource();
        }else{
            ab.setTitle("Input");
            btnInput.setText("SAVE");
        }
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(context);
                Note newNote = new Note();
                String title = edtTitle.getText().toString().trim();
                String content = edtDetail.getText().toString();
                Date now = Calendar.getInstance().getTime();
                String datetime = sdf.format(now);
                switch (act){
                    case "update" :
                        newNote.setId(itemID);
                        newNote.setDatetime(datetime);
                        newNote.setTitle(title);
                        newNote.setDetail(content);
                        db.update(newNote);
//                        Toast.makeText(context, "UPDATE!", Toast.LENGTH_SHORT).show();
                        break;
                    case "insert":
                        newNote.setDatetime(datetime);
                        newNote.setTitle(title);
                        newNote.setDetail(content);
                        db.insert(newNote);
//                        Toast.makeText(context, "SUCCESS!", Toast.LENGTH_SHORT).show();
                        break;
                }
                goHome();
            }
        });

    }

    public Note extractingResource(){
        Note note = getIntent().getParcelableExtra(EXTRA_NOTE);
        edtTitle.setText(note.getTitle());
        edtDetail.setText(note.getDetail());
        itemID = note.getId();
        return note;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(act.equals("update")){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_input,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                super.onBackPressed();
                break;
            case R.id.action_delete:
                Database db = new Database(context);
                db.delete(daNote.getId());
                goHome();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goHome(){
        Intent homie = new Intent(context, MainActivity.class);
        startActivity(homie);
    }
}

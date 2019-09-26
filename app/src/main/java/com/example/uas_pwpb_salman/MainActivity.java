package com.example.uas_pwpb_salman;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.UserActionListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Note> listNote;
    ImageView btnAdd;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Notes");

        context = this;
        recyclerView = findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        setupRV();

        btnAdd = findViewById(R.id.Add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(context, Input.class);
                move.putExtra(Input.EXTRA_ACT,"insert");
                context.startActivity(move);
            }
        });
    }

    public void setupRV(){
        Database db = new Database(context);
        listNote = db.selectUserData();
        Adapter adapter = new Adapter(context,listNote,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUserClick(final Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editing Note : #"+note.getId())
                .setItems(new String[]{"Edit","Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0 :
                                Intent move = new Intent(context, Input.class);
                                move.putExtra(Input.EXTRA_ACT,"update");
                                move.putExtra(Input.EXTRA_NOTE,note);
                                context.startActivity(move);
                                break;
                            case 1 :
                                //delete
                                Database db = new Database(context);
                                db.delete(note.getId());
                                setupRV();
                                break;
                            default:
                                Toast.makeText(context, "Unexpected Action!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        AlertDialog userAct = builder.create();
        userAct.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_refresh){
            setupRV();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //nothing
    }
}

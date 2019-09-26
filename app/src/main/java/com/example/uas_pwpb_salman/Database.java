package com.example.uas_pwpb_salman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "UserInfo";
    private static final String TABLE_NAME = "tbl_Note";
    private static final String KEY_ID = "id";
    private static final String KEY_TIMESTAMP = "datetime";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "detail";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "Create Table "+TABLE_NAME+"("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_TIMESTAMP+" TEXT,"+KEY_TITLE+" TEXT,"+KEY_CONTENT+" TEXT"+")";
        sqLiteDatabase.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "Drop Table If Exists "+TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public List<Note> selectUserData(){
        ArrayList<Note> noteList = new ArrayList<Note>();

        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {KEY_ID,KEY_TIMESTAMP,KEY_TITLE,KEY_CONTENT};

        Cursor c = db.query(TABLE_NAME,columns,null,null,null,null,null);

        while(c.moveToNext()){
            int id = c.getInt(0);
            String datetime = c.getString(1);
            String title = c.getString(2);
            String detail = c.getString(3);


            Note n = new Note(id,datetime,title,detail);
            noteList.add(n);
        }

        return noteList;
    }

    public void update(Note note){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_TIMESTAMP,note.getDatetime());
        val.put(KEY_TITLE,note.getTitle());
        val.put(KEY_CONTENT,note.getDetail());
        String whereClause = KEY_ID+"='"+note.getId()+"'";
        db.update(TABLE_NAME,val,whereClause,null);
    }

    public void insert(Note note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
//        val.put(KEY_ID,note.getId());
        val.put(KEY_TIMESTAMP,note.getDatetime());
        val.put(KEY_TITLE,note.getTitle());
        val.put(KEY_CONTENT,note.getDetail());

        db.insert(TABLE_NAME,null,val);
    }

    public void delete(int id){
        SQLiteDatabase  db = getWritableDatabase();
        String whereClause = KEY_ID+"='"+id+"'";
        db.delete(TABLE_NAME,whereClause,null);
    }
}
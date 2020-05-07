package id.ac.umn.quiz1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button btnAdd = findViewById(R.id.btnAdd);
        TextView txtWelcome = findViewById(R.id.txtWelcome);

        SharedPreferences pref = getSharedPreferences("QUIZUTS",MODE_PRIVATE);
        txtWelcome.setText("Welcome, "+pref.getString("email",""));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this, AddActivity.class));
            }
        });
        seedData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                SharedPreferences.Editor prefEdit = getSharedPreferences("QUIZUTS", MODE_PRIVATE).edit();
                prefEdit.clear();
                prefEdit.commit();
                startActivity(new Intent(ListActivity.this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();
        seedData();
    }

    @Override
    protected void onResume(){
        super.onResume();
        seedData();
    }

    public void seedData(){
        ListView listMember = findViewById(R.id.listView);

        MemberContract.MemberDbHelper memberDbHelper =
                new MemberContract.MemberDbHelper(ListActivity.this);
        SQLiteDatabase db = memberDbHelper.getReadableDatabase();

        String[] projection = {
                MemberContract.MemberEntry._ID,
                MemberContract.MemberEntry.COLUMN_NAME_NAME,
                MemberContract.MemberEntry.COLUMN_NAME_POSITION
        };

        Cursor cursor = db.query(
                MemberContract.MemberEntry.TABLE_NAME,
                projection,
                null,null,null,null,null
        );
        cursor.moveToFirst();

        final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<Member> members = new ArrayList<Member>();

        for(int i=0; i<cursor.getCount(); i++, cursor.moveToNext()){
            final int id = cursor.getInt(
                    cursor.getColumnIndex(MemberContract.MemberEntry._ID)
            );
            final String name = cursor.getString(
                    cursor.getColumnIndex(MemberContract.MemberEntry.COLUMN_NAME_NAME)
            );
            final String position = cursor.getString(
                    cursor.getColumnIndex(MemberContract.MemberEntry.COLUMN_NAME_POSITION)
            );
            members.add(new Member(id,name,position));
            list.add(name + " - " + position);
        }

        cursor.close();
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_expandable_list_item_1, list);
        listMember.setAdapter(adapter);

        listMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Member m = members.get(i);
                Intent intent = new Intent(ListActivity.this, UpdateActivity.class);
                intent.putExtra("id",m.get_id());
                intent.putExtra("name",m.get_name());
                intent.putExtra("position",m.get_position());
                startActivity(intent);
            }
        });

        listMember.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                MemberContract.MemberDbHelper memberDbHelper =
                        new MemberContract.MemberDbHelper(ListActivity.this);
                SQLiteDatabase db = memberDbHelper.getReadableDatabase();
                String selection = String.format(
                        "%s = ?",
                        MemberContract.MemberEntry._ID
                );
                Member m = members.get(i);
                String[] selectionArgs = {
                        String.valueOf(m.get_id())
                };
                db.delete(
                        MemberContract.MemberEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                Toast.makeText(ListActivity.this, m.get_name()+" helah dihapus", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return true;
            }
        });
    }
}

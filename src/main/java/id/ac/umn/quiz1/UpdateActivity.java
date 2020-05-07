package id.ac.umn.quiz1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        final int id = getIntent().getIntExtra("id",-1);
        final String name = getIntent().getStringExtra("name");
        final String pos = getIntent().getStringExtra("position");

        final EditText txtName = findViewById(R.id.txtName);
        final Spinner spinnerPos = findViewById(R.id.pos_spinner);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.pos_array,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerPos.setAdapter(arrayAdapter);

        txtName.setText(name);
        String[] myResArray = getResources().getStringArray(R.array.pos_array);
        List<String> myResArrayList = Arrays.asList(myResArray);
        spinnerPos.setSelection(myResArrayList.indexOf(pos));

        Button updateButton = findViewById(R.id.btnUpdate);
        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(txtName.getText().toString().length()>0 &&
                        spinnerPos.getSelectedItem().toString().length()>0){
                    MemberContract.MemberDbHelper accountDbHelper =
                            new MemberContract.MemberDbHelper(UpdateActivity.this);
                    SQLiteDatabase db = accountDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(
                            MemberContract.MemberEntry.COLUMN_NAME_NAME,
                            txtName.getText().toString()
                    );
                    values.put(
                            MemberContract.MemberEntry.COLUMN_NAME_POSITION,
                            spinnerPos.getSelectedItem().toString()
                    );
                    String selection = String.format(
                            "%s = ?",
                            MemberContract.MemberEntry._ID
                    );
                    String[] selectionArgs = {
                            String.valueOf(id)
                    };

                    int count = db.update(
                            MemberContract.MemberEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs
                    );
                    if(count!=-1) {
                        Toast.makeText(UpdateActivity.this, "Member updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(UpdateActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UpdateActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

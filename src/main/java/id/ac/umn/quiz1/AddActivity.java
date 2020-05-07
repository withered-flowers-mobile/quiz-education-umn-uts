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

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText txtName = findViewById(R.id.txtName);
        final Spinner spinnerPos = findViewById(R.id.pos_spinner);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.pos_array,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerPos.setAdapter(arrayAdapter);

        Button addButton = findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(txtName.getText().toString().length()>0 &&
                        spinnerPos.getSelectedItem().toString().length()>0){
                    MemberContract.MemberDbHelper accountDbHelper =
                            new MemberContract.MemberDbHelper(AddActivity.this);
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
                    long newRowId = db.insert(
                            MemberContract.MemberEntry.TABLE_NAME,
                            null,
                            values
                    );
                    if(newRowId!=-1) {
                        Toast.makeText(AddActivity.this, "Member added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(AddActivity.this, "Failed to add", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

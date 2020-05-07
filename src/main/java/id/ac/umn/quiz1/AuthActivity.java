package id.ac.umn.quiz1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity {

    EditText answEdit;
    Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        final String ans = getIntent().getStringExtra("ans");
        Toast.makeText(this, ans, Toast.LENGTH_SHORT).show();
        confirmButton = findViewById(R.id.btnConfirm);
        answEdit = findViewById(R.id.txtAnswer);
        SharedPreferences pref = getSharedPreferences("QUIZUTS", MODE_PRIVATE);
        final String name = pref.getString("email","");
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answEdit.getText().toString().length()>0 && answEdit.getText().toString().equals(ans)){
                    Toast.makeText(AuthActivity.this, "Hi "+name, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AuthActivity.this, ListActivity.class));
                }
                else{
                    Toast.makeText(AuthActivity.this, "You're not human", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}

package id.ac.umn.quiz1;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    Button loginButton;
    EditText emailEdit;
    EditText passwEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.btnLogin);
        emailEdit = findViewById(R.id.txtEmail);
        passwEdit = findViewById(R.id.txtPassword);

        SharedPreferences sharedPreferences = getSharedPreferences("QUIZUTS", MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        String passw = sharedPreferences.getString("passw","");

        if(!email.equals("")&&!passw.equals("")){
            startActivity(new Intent(MainActivity.this, ListActivity.class));
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailEdit.getText().toString().length()>1 &&
                        passwEdit.getText().toString().length()>1){
                    SharedPreferences.Editor prefEdit = getSharedPreferences("QUIZUTS", MODE_PRIVATE).edit();
                    prefEdit.putString("email",emailEdit.getText().toString());
                    prefEdit.putString("passw",passwEdit.getText().toString());
                    prefEdit.commit();
                    Random ran = new Random();
                    int a = ran.nextInt(1000)+1000;
                    int b = ran.nextInt(1000)+500;
                    String x = (a/b>0?"-":"+");
                    int c = (a/b>0?a-b:a+b);
                    Notification notification
                            = new Notification.Builder(MainActivity.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setTicker("Solve this")
                            .setContentTitle("Buktikan jika kamu manusia")
                            .setContentText(String.valueOf(a)+" "+x+" "+String.valueOf(b)+" =")
                            .build();
                    notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;
                    NotificationManager notificationManager
                            = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    Intent auth = new Intent(MainActivity.this, AuthActivity.class);
                    auth.putExtra("ans",String.valueOf(c));
                    startActivity(auth);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please input your name and your NIM", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

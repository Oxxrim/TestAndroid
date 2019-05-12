package ua.kpi.fift.testtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;

import ua.kpi.fift.testtask.pojo.User;
import ua.kpi.fift.testtask.util.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkUser(View view) throws SQLException {
        EditText editLogin = findViewById(R.id.loginInput);
        EditText editPass = findViewById(R.id.editPass);
        TextView err = findViewById(R.id.error);

        String login = editLogin.getText().toString();
        String password = editPass.getText().toString();

        DatabaseHelper helper = new DatabaseHelper(this);

        User user = helper.findUser(login);

        if (user != null && login.equals(user.getLogin()) && password.equals(user.getPassword())){

            err.setText("Hello " + user.getLogin());
            if (user.getRole().equals("admin")) {
                startActivity(new Intent(this, AdminPage.class));
            }
        } else {
            err.setText("Wrong login or password");
        }

    }
}

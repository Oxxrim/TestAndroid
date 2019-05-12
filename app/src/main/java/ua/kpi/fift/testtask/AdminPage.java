package ua.kpi.fift.testtask;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import ua.kpi.fift.testtask.pojo.User;
import ua.kpi.fift.testtask.util.DatabaseHelper;

public class AdminPage extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        List<User> usrs = null;

        try {
            usrs = helper.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        ListView users = findViewById(R.id.users);
        LinearLayout linLayout = findViewById(R.id.linLayout);

        LayoutInflater inflater = getLayoutInflater();

        for (int i = 0; i < usrs.size(); i++){
            User user = usrs.get(i);
            View item = inflater.inflate(R.layout.item, linLayout, false);
            TextView login = item.findViewById(R.id.loginOut);
            login.setText(user.getLogin());
            TextView pass = item.findViewById(R.id.passOut);
            pass.setText(user.getPassword());
            TextView role = item.findViewById(R.id.roleOut);
            role.setText(user.getRole());
            item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            linLayout.addView(item);
            FrameLayout frameLayout = (FrameLayout) linLayout.getChildAt(i);
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editUser(v);
                }
            });
        }
    }

    public void addUser(View view) throws SQLException {
        EditText editLogin = findViewById(R.id.login);
        EditText editPass = findViewById(R.id.pass);
        EditText editRole = findViewById(R.id.role);

        String login = editLogin.getText().toString();
        String password = editPass.getText().toString();
        String role = editRole.getText().toString();



        User user = new User(login, password, role);

        helper.addUser(user);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void editUser(View view) {
        /*TextView login = view.findViewById(R.id.loginOut);
        String text = login.getText().toString();
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();*/
        final User user = new User();

        TextView login = view.findViewById(R.id.loginOut);
        final String log = login.getText().toString();
        TextView password = view.findViewById(R.id.passOut);
        final String pass = password.getText().toString();
        TextView role = view.findViewById(R.id.roleOut);
        final String rol = role.getText().toString();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle("Edit");

        login = dialog.findViewById(R.id.loginUpdate);
        login.setText(log);

        final EditText updPass = dialog.findViewById(R.id.passUpdate);
        updPass.setText(pass);

        final EditText updRole = dialog.findViewById(R.id.roleUpdate);
        updRole.setText(rol);
        final Intent intent = getIntent();

        final Button update = dialog.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setLogin(log);
                user.setPassword(updPass.getText().toString());
                user.setRole(updRole.getText().toString());
                try {
                    helper.updateUser(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                finish();
                startActivity(intent);
            }
        });

        Button delete = dialog.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setLogin(log);
                user.setPassword(pass);
                user.setRole(rol);
                try {
                    helper.deleteUser(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                finish();
                startActivity(intent);
            }
        });
        dialog.show();
    }
}

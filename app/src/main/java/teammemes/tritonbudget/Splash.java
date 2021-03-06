package teammemes.tritonbudget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User usr = User.getInstance(getApplicationContext());
        if (usr.getName().equals("user name not found")) {
            Intent intent = new Intent(getApplicationContext(), Enter_Info.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
        finish();
    }
}

package br.callcontroller.activities;

import android.app.Activity;
import android.os.Bundle;
import br.callcontroller.R;

public class CallControllerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
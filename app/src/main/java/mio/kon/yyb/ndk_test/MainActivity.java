package mio.kon.yyb.ndk_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary ("ndk");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        TextView tv = (TextView) findViewById (R.id.tv);
        TextView tv2 = (TextView) findViewById (R.id.tv2);
        tv.setText (stringFromJNI ());
        tv2.setText ("int from jni:"+intFromJNI ());

    }

    public native int intFromJNI();

    public native String stringFromJNI();


}

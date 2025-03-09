package com.example.exam1;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    TextView tvOutput;
    GestureDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
        tvOutput = (TextView)findViewById(R.id.txtView);
        mDetector = new GestureDetector(this,this); mDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (this.mDetector.onTouchEvent(e)) {
            return true;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent e) { tvOutput.setText("onDown: " + e.toString());
        return false;
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { tvOutput.setText("onFling: " + e1.toString()+e2.toString()); return true;
    }
    @Override
    public void onLongPress(MotionEvent e) { tvOutput.setText("onLongPress: " + e.toString());
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        tvOutput.setText("onScroll: " + e1.toString()+e2.toString());
        return true;
    }
    @Override
    public void onShowPress(MotionEvent e) { tvOutput.setText("onShowPress: " + e.toString());
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) { tvOutput.setText("onSingleTapUp: " + e.toString());
        return true;
    }
    @Override
    public boolean onDoubleTap(MotionEvent e) { tvOutput.setText("onDoubleTap: " + e.toString());
        return true;
    }
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) { tvOutput.setText("onDoubleTapEvent: " + e.toString());
        return true;
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) { tvOutput.setText("onSingleTapConfirmed: " + e.toString());
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            try {
                dialog.setMessage(getTitle().toString() + " версия " +
                        getPackageManager().getPackageInfo(getPackageName(), 0).versionName +
                        "\r\n\nПрограмма с воспроизведением звука \r\n\nАвтор - Титаренко Арсений Владимировия БПИ-2310");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            dialog.setTitle("О программе");
            dialog.setNeutralButton("OK", (dialog1, which) -> dialog1.dismiss());
            dialog.setIcon(R.mipmap.ic_launcher_round);
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}



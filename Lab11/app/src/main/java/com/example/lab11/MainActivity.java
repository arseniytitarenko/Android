package com.example.lab11;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.content.Context;
import android.content.ContentResolver;
import android.app.Notification;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);

        final EditText editText = findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMultiplePermissions();
                Context context = MainActivity.this;
                NotificationChannel newnotchan = new NotificationChannel("mychannel1","mychannel",
                        NotificationManager.IMPORTANCE_HIGH);
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build();
                newnotchan.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+getPackageName()+
                                "/" + R.raw.sms), audioAttributes);

                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(newnotchan);

                String userMessage = editText.getText().toString();

                Notification notification = new Notification.Builder(context, "mychannel1")
                        .setContentTitle("Reminder")
                        .setContentText(userMessage)
                        .setTicker("new notification!")
                        .setChannelId("mychannel1")
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setOngoing(true)
                        .build();
                notificationManager.notify(0, notification);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            try {
                dialog.setMessage(getTitle().toString()+ " версия "+
                        getPackageManager().getPackageInfo(getPackageName(),0).versionName +
                        "\r\n\nПрограмма с примером выполнения диалогового окна \r\n\nАвтор - Титаренко Арсений Владимировия БПИ-2310");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            dialog.setTitle("О программе");
            dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.setIcon(R.mipmap.ic_launcher_round);
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[] { POST_NOTIFICATIONS },
                PERMISSION_REQUEST_CODE);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                return;
            } else finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
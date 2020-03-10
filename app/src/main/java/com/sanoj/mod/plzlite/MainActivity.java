package com.sanoj.mod.plzlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.NoCopySpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dcastalia.localappupdate.DownloadApk;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.jaredrummler.android.shell.Shell;
import com.sanoj.mod.service.FloatWidgetService;
import com.schibsted.spain.parallaxlayerlayout.AnimatedTranslationUpdater;
import com.schibsted.spain.parallaxlayerlayout.ParallaxLayerLayout;
import com.schibsted.spain.parallaxlayerlayout.SensorTranslationUpdater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int APP_PERMISSION_REQUEST = 102;
    private Button red,green,pink,black;
    private Context context;
    private String litered = "com.facebook.litg";
    private String litegreen = "com.facebook.litf";
    private String litepink = "com.facebook.lith";
    private String liteblack = "com.facebook.liti";
    private ParallaxLayerLayout parallaxLayout;
    private SensorTranslationUpdater translationUpdater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red =findViewById(R.id.red);
        green = findViewById(R.id.green);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, APP_PERMISSION_REQUEST);
        } else {

        }


        translationUpdater = new SensorTranslationUpdater(this);

        parallaxLayout = (ParallaxLayerLayout) findViewById(R.id.parallax);
        parallaxLayout.setTranslationUpdater(new AnimatedTranslationUpdater(0.8f));


        SwipeButton enableButton = (SwipeButton) findViewById(R.id.swipe_btn);
        enableButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Toast.makeText(MainActivity.this, "State: " + active, Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, FloatWidgetService.class));

                //Shell.SH.run("am start -n com.facebook.liti/com.facebook.liti.MainActivity");
                //Shell.run("am start -n com.facebook.liti/com.facebook.liti.MainActivity");

            }
        });


        boolean isAppInstalledred = appInstalledOrNot(litered);
        boolean isAppInstalledgreen = appInstalledOrNot(litegreen);
        boolean isAppInstalledpink = appInstalledOrNot(litepink);
        boolean isAppInstalledblack = appInstalledOrNot(liteblack);

        if(isAppInstalledred) {
            red.setText("Already install Lite Red \uD83D\uDC4C");
        } else {
            red.setText("Download Lite Red \uD83D\uDC96");
        }

        if(isAppInstalledgreen) {
            green.setText("Already install Lite Green \uD83D\uDC4C");
        } else {
            green.setText("Download Lite Green \uD83D\uDC96");
        }

        if(isAppInstalledpink) {
            pink.setText("Already install Lite Pink \uD83D\uDC4C");
        } else {
            pink.setText("Download Lite Pink \uD83D\uDC96");
        }

        if(isAppInstalledblack) {
            black.setText("Already install Lite Black \uD83D\uDC4C");
        } else {
            black.setText("Download Lite Black \uD83D\uDC96");
        }




        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/00sanoj00/parallax_lite/raw/master/App/LiteRed.apk";
                DownloadApk downloadApk = new DownloadApk(MainActivity.this);
                downloadApk.startDownloadingApk(url);
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/00sanoj00/parallax_lite/raw/master/App/LiteGreen.apk";
                DownloadApk downloadApk = new DownloadApk(MainActivity.this);
                downloadApk.startDownloadingApk(url);
            }
        });
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/00sanoj00/parallax_lite/raw/master/App/LitePink.apk";
                DownloadApk downloadApk = new DownloadApk(MainActivity.this);
                downloadApk.startDownloadingApk(url);
            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/00sanoj00/parallax_lite/raw/master/App/LiteBlack.apk";
                DownloadApk downloadApk = new DownloadApk(MainActivity.this);
                downloadApk.startDownloadingApk(url);
            }
        });


    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_PERMISSION_REQUEST && resultCode == RESULT_OK) {

        } else {
            Toast.makeText(this, "Draw over other app permission not enable.", Toast.LENGTH_SHORT).show();
        }
    }
}

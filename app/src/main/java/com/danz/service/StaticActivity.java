package com.danz.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Process;
import android.provider.Settings;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.danz.service.FeatureFunction;
import java.io.File;
import java.io.FileOutputStream;

public class StaticActivity {
    private static String id = "UCeH7tadQeQRjWJhkM3mBp4w";
    public static String cacheDir;
    private static String TAG = "Mod";
    public static void Start(final Context c){
        if(FeatureFunction.WithLoging){
            int i = 0;
            if (i == 0){
                i++;
                loginModMenu(c, new onLoginFinish(){

                        @Override
                        public void onLogin(boolean succes,AlertDialog dl) {
                            if (succes){
                                dl.dismiss();
                                LoginSucces(c);
                                Toast.makeText(c, "Login Succes", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(c, "Login Failed", Toast.LENGTH_LONG).show();
                            }
                        }

                    });
            }
        }else{
            LoginSucces(c);
        }   
    }
    private static void LoginSucces(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            context.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION",
                                             Uri.parse("package:" + context.getPackageName())));
            Process.killProcess(Process.myPid());
        } else {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        context.startService(new Intent(context, DanzService.class));

                    }
                }, 1000);
        }

        cacheDir = context.getCacheDir().getPath() + "/";

        writeToFile("OpenMenu.ogg", Sounds.OpenMenu());
        writeToFile("Back.ogg", Sounds.Back());
        writeToFile("Select.ogg", Sounds.Select());
        writeToFile("SliderIncrease.ogg", Sounds.SliderIncrease());
        writeToFile("SliderDecrease.ogg", Sounds.SliderDecrease());
        writeToFile("On.ogg", Sounds.On());
        writeToFile("Off.ogg", Sounds.Off());


    }

    private static void writeToFile(String name, String base64) {
        File file = new File(cacheDir + name);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] decode = Base64.decode(base64, 0);
            fos.write(decode);
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
    public static void loginModMenu(final Context c,final onLoginFinish log){
        LayoutParams params = new LayoutParams(
            LayoutParams.MATCH_PARENT,      
            LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 0, 16, 0);
        final AlertDialog alert = new AlertDialog.Builder(c).create();
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE); alert.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));

        LinearLayout load = new LinearLayout(c);
        load.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        load.setGravity(Gravity.CENTER);
        load.setOrientation(LinearLayout.VERTICAL);
        load.setPadding(0,0,0,16);
        load.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, FeatureFunction.StrokeColor, FeatureFunction.BgColor));

        TextView title = new TextView(c);
        title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        title.setGravity(Gravity.CENTER);
        title.setTextSize(19f);
        title.setPadding(8,8,8,8);
        title.setTextColor(Color.WHITE);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, FeatureFunction.StrokeColor, FeatureFunction.TitleBg));
        title.setText("LOGIN MOD MENU");
        load.addView(title);

        final EditText Target = new EditText(c);   
        Target.setLayoutParams(params);
        Target.setHint("UserName");
        Target.setInputType(InputType.TYPE_CLASS_TEXT);
        Target.setPadding(8,8,8,8);
        Target.setTextSize(16);
        Target.setSingleLine(true);
        Target.setTextColor(Color.WHITE);
        load.addView(Target);

        final EditText Target2 = new EditText(c);
        Target2.setLayoutParams(params);
        Target2.setHint("Password");
        Target2.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        Target2.setPadding(8,8,8,8);
        Target2.setTextSize(16);
        Target2.setSingleLine(true);
        Target2.setTextColor(Color.WHITE);
        load.addView(Target2);

        final Button log_in = new Button(c);
        log_in.setLayoutParams(params);
        log_in.setText("LOGIN");
        log_in.setTextColor(Color.WHITE);
        log_in.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, FeatureFunction.StrokeColor, FeatureFunction.BgColor));
        log_in.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                    boolean wuk = Target.getText().toString().equals(FeatureFunction.username) && Target2.getText().toString().equals(FeatureFunction.password);
                    log.onLogin(wuk,alert);
                }
            });
        load.addView(log_in);
        alert.setView(load);
        alert.setCancelable(false);
        alert.show();
    }
    private static interface onLoginFinish{
        void onLogin(boolean succes,AlertDialog dl);
    }
}


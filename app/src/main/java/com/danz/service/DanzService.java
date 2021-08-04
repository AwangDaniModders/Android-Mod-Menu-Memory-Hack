package com.danz.service;

import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androtrainer.MemoryPatcher;
import androtrainer.MemoryScanner;
import com.bumptech.glide.Glide;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import mb.mungboih.RainbowTextView.RainbowTextView;
import java.io.IOException;

public class DanzService extends Service {
    private PlaySound p = new PlaySound();
    private ImageView iconMenu;
    private LinearLayout MenuBg;
    private LinearLayout bg;
    private WindowManager wm;
    private RainbowTextView kill;
    private RainbowTextView close;
    private int increase;
    private LinearLayout Menus;
    private Switch sw;
    private WindowManager.LayoutParams params;
    private int flags;
    private RainbowTextView title;
    private RelativeLayout relativeLayout ;
    private Timer timer;
    private TimerTask timerTask;
    private String msgToast = AwangDaniWoy.decrypt(FeatureFunction.toastB64);
    private MemoryPatcher mp = new MemoryPatcher();
    private MemoryScanner ms = new MemoryScanner();
    @Override
    public IBinder onBind(Intent p1) {
        return null;
    }

    @Override
    public void onCreate() {
        
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,

            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            , 

            PixelFormat.TRANSPARENT);

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        //View myView = inflater.inflate(R.layout.floating, null);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 0;

        //Icon
        iconMenu = new ImageView(getBaseContext());
        iconMenu.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        iconMenu.getLayoutParams().height = 90;
        iconMenu.getLayoutParams().width = 90;
        iconMenu.requestLayout();
        iconMenu.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(getApplicationContext()).load("file:///android_asset/gameDatabases.db").into(iconMenu);
        iconMenu.setImageAlpha(200);
        
        //Backgroun Menu
        MenuBg = new LinearLayout(getBaseContext());
        MenuBg.setLayoutParams(new RelativeLayout.LayoutParams(
                                   ViewGroup.LayoutParams.WRAP_CONTENT,
                                   ViewGroup.LayoutParams.WRAP_CONTENT));
        MenuBg.getLayoutParams().height = 500;
        MenuBg.getLayoutParams().width = 400;
        MenuBg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, FeatureFunction.StrokeColor, FeatureFunction.BgColor));
        MenuBg.setVisibility(View.GONE);
        MenuBg.setOrientation(LinearLayout.VERTICAL);
        MenuBg.setGravity(Gravity.CENTER_HORIZONTAL);
        MenuBg.setElevation(10f);
        MenuBg.setAlpha(0.9f);
        MenuBg.requestLayout();

        bg = new LinearLayout(getBaseContext());
        bg.setBackgroundColor(Color.TRANSPARENT);
        bg.setOrientation(LinearLayout.HORIZONTAL);
        bg.setLayoutParams(new ViewGroup.LayoutParams(
                               ViewGroup.LayoutParams.WRAP_CONTENT,
                               ViewGroup.LayoutParams.WRAP_CONTENT));
        bg.requestLayout();

        relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
        relativeLayout.setPadding(3, 0, 3, 3);
        relativeLayout.setVerticalGravity(16);
        relativeLayout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)FeatureFunction.CornerRadius, (int)2, FeatureFunction.StrokeColor, FeatureFunction.TitleBg));

        kill = new RainbowTextView(this);
        kill.setText("HIDE/KILL (HOLD)");
        kill.setPadding(18,18,18,18);
        kill.setTypeface(Typeface.DEFAULT_BOLD);
        
        
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);

        close = new RainbowTextView(this);
        close.setText("MINIMIZE");
        close.setPadding(18,18,18,18);
        /*0xFF44C920*/
        close.setTypeface(Typeface.DEFAULT_BOLD);
        close.setLayoutParams(layoutParams);

        relativeLayout.addView(kill);
        relativeLayout.addView(close);

        Menus = new LinearLayout(getBaseContext());
        Menus.setLayoutParams(new LinearLayout.LayoutParams(
                                  ViewGroup.LayoutParams.MATCH_PARENT,
                                  ViewGroup.LayoutParams.MATCH_PARENT,1));
        Menus.setOrientation(LinearLayout.VERTICAL);
        Menus.setGravity(Gravity.CENTER);
        Menus.requestLayout();

        title = new RainbowTextView(this);
        title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        title.setGravity(Gravity.CENTER);
        title.getLayoutParams().height = 80;
        title.setTextSize(19f);
        title.setPadding(8,8,8,8);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)FeatureFunction.CornerRadius, (int)2, FeatureFunction.StrokeColor, FeatureFunction.TitleBg));
        
        ScrollView scrollView = new ScrollView(getBaseContext());
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));

        title.setText(FeatureFunction.title);
        bg.addView(iconMenu);
        bg.addView(MenuBg);
        MenuBg.addView(title);
        MenuBg.addView(scrollView);
        scrollView.addView(Menus);
        MenuBg.addView(relativeLayout);
        String id = FeatureFunction.IdYoutube;
        if(FeatureFunction.gotoYoutube){
            int i = 0;
            if (i == 0){
                DanzService.AboutToast(getApplicationContext(),msgToast);
                startActivity(new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("https://youtube.com/"+"channel/"+id)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                i++;
            }
        }
        FeatureList();
        close.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    iconMenu.setVisibility(View.VISIBLE);
                    iconMenu.setImageAlpha(200);
                    MenuBg.setVisibility(View.GONE);
                    p.play(p.BACK,getApplicationContext());
                    AboutToast(getApplicationContext(),msgToast);
                }
            });
        kill.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    iconMenu.setVisibility(View.VISIBLE);
                    iconMenu.setImageAlpha(0);
                    MenuBg.setVisibility(View.GONE);
                    p.play(p.BACK,getApplicationContext());
                    AboutToast(getApplicationContext(),msgToast);
                }
            });
        kill.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    AboutToast(getApplicationContext(),msgToast);
                    wm.removeView(bg);
                    p.play(p.BACK,getApplicationContext());
                    return false;
                }
            });

        MenuBg.setOnTouchListener(new View.OnTouchListener() {
                final View collapsedView = iconMenu;
                final View expandedView = MenuBg;
                private float initialTouchX;
                private float initialTouchY;
                private int initialX;
                private int initialY;

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = motionEvent.getRawX();
                            initialTouchY = motionEvent.getRawY();
                            return true;
                        case MotionEvent.ACTION_UP:
                            int rawX = (int) (motionEvent.getRawX() - initialTouchX);
                            int rawY = (int) (motionEvent.getRawY() - initialTouchY);
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            //Calculate the X and Y coordinates of the view.
                            params.x = initialX + ((int) (motionEvent.getRawX() - initialTouchX));
                            params.y = initialY + ((int) (motionEvent.getRawY() - initialTouchY));

                            //Update the layout with new X & Y coordinate
                            wm.updateViewLayout(bg, params);
                            return true;
                        default:
                            return false;
                    }
                }

                private boolean isViewCollapsed() {
                    return bg == null || iconMenu.getVisibility() == View.VISIBLE;
                }
            });

        iconMenu.setOnTouchListener(new View.OnTouchListener() {
                final View collapsedView = iconMenu;
                final View expandedView = MenuBg;
                private float initialTouchX;
                private float initialTouchY;
                private int initialX;
                private int initialY;

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = motionEvent.getRawX();
                            initialTouchY = motionEvent.getRawY();
                            return true;
                        case MotionEvent.ACTION_UP:
                            int rawX = (int) (motionEvent.getRawX() - initialTouchX);
                            int rawY = (int) (motionEvent.getRawY() - initialTouchY);

                            //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                            //So that is click event.
                            if (rawX < 10 && rawY < 10 && isViewCollapsed()) {
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                p.play(p.OPEN_MENU,getApplicationContext());
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                                //Toast.makeText(FloatingModMenuService.this, Html.fromHtml(Toast()), Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            //Calculate the X and Y coordinates of the view.
                            params.x = initialX + ((int) (motionEvent.getRawX() - initialTouchX));
                            params.y = initialY + ((int) (motionEvent.getRawY() - initialTouchY));

                            //Update the layout with new X & Y coordinate
                            wm.updateViewLayout(bg, params);
                            return true;
                        default:
                            return false;
                    }
                }

                private boolean isViewCollapsed() {
                    return bg == null || iconMenu.getVisibility() == View.VISIBLE;
                }
            });
        //Floating Show
        
        wm.addView(bg, params);
        FeatureFunction.onCreate();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
                public void run() {
                    Thread();
                    handler.postDelayed(this, 10);
                    //Toast.makeText(FloatingModMenuService.this, Html.fromHtml(Toast()), Toast.LENGTH_SHORT).show();
                }
            });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        wm.removeView(bg);
    }
    private void addSwitch(final int id, final String name, final onSwitchChecked listner) {
        final Switch sw = new Switch(this);
        sw.setThumbTintList(new android.content.res.ColorStateList( new int[][]{ new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_checked}, new int[]{} }, new int[]{ Color.BLUE, 0xFF4CAF50, 0xFFF44336 } ));
        sw.setText(Html.fromHtml("<font color=\"BLACK\"># </font>"+name+" : "+"<font color=\"RED\">OFF</font>"));
        sw.setTextColor(Color.WHITE);
        sw.setPadding(8,8,8,8);
        //sw.setTextSize(dipToPixels());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listner.OnChecked(id,isChecked);
                    if(isChecked){
                        p.play(p.ON,getApplicationContext());
                        sw.setText(Html.fromHtml("<font color=\"BLACK\"># </font>"+name+" : "+"<font color=\"GREEN\">ON</font>"));
                    }else{
                        p.play(p.OFF,getApplicationContext());
                        sw.setText(Html.fromHtml("<font color=\"BLACK\"># </font>"+name+" : "+"<font color=\"RED\">OFF</font>"));
                    }
                }
            });
        Menus.addView(sw);
    }
    private interface onSwitchChecked{
        void OnChecked(int id,boolean z);
    }
    private void addCategory(final String name,Typeface tp){
        final TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        tv.setText("✓ "+name+" ✓");
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(tp);
        tv.setPadding(8,8,8,8);
        tv.setTextSize(16f);
        tv.setBackgroundColor(FeatureFunction.TitleBg);
        tv.setGravity(Gravity.CENTER);
        Menus.addView(tv);
    }
    public static void AboutToast(Context c,String msg){
        RainbowTextView message = new RainbowTextView(c);
        message.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        message.setGravity(Gravity.CENTER);
        message.getLayoutParams().height = 80;
        message.setTextSize(19f);
        message.setPadding(8,16,8,16);
        message.setAlpha(0.8f);
        message.setTypeface(Typeface.SERIF);
        message.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)2, FeatureFunction.StrokeColor, FeatureFunction.TitleBg));
        message.setText(msg);
        
        Toast toast = new Toast(c);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(message);
        toast.show();
    }
    private void addSeekBar(final int id,final String feature, final int prog, int max, final InterfaceInt interInt) {
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        linearLayout.setPadding(10, 5, 0, 5);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(10);
        linearLayout.setLayoutParams(layoutParams);
        final TextView textView = new TextView(this);
        textView.setText(Html.fromHtml("<font face='roboto'>" + feature + ": <font color='#ffffff'>" + prog + "</font>"));
        textView.setTextColor(Color.parseColor("#ffffff"));
        SeekBar seekBar = new SeekBar(this);
        seekBar.setPadding(25, 10, 35, 10);
        seekBar.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        seekBar.setMax(max);
        seekBar.setProgress(prog);
        increase = 0;
        final TextView textView2 = textView;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                int l;

                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {

                    if (i < prog) {
                        seekBar.setProgress(prog);
                        interInt.OnWrite(id,prog);
                        TextView textView = textView2;
                        textView.setText(Html.fromHtml("<font face='roboto'>" + feature + ": <font color='#41c300'>" + prog + "</font>"));
                        p.play(p.INCREASE,getApplicationContext());
                        return;
                    }
                    if (i > increase){
                        p.play(p.INCREASE,getApplicationContext());
                    }else{
                        p.play(p.DECREASE,getApplicationContext());
                    }
                    increase = i;
                    interInt.OnWrite(id,i);
                    textView.setText(Html.fromHtml("<font face='roboto'>" + feature + ": <font color='#41c300'>" + i + "</font>"));
                }
            });

        linearLayout.addView(textView);
        linearLayout.addView(seekBar);
        Menus.addView(linearLayout);
    }
    private void addSpinner(final int uid,String feature, final setOnSelected interInt) {
        List<String> list = new LinkedList<>(Arrays.asList(feature.split("_")));

        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        linearLayout.setPadding(10, 5, 10, 5);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(17);
        linearLayout.setLayoutParams(layoutParams);

        final TextView textView = new TextView(this);
        textView.setText(Html.fromHtml("<font face='roboto'>" + list.get(0) + ": <font color='#41c300'></font>"));
        textView.setTextColor(Color.parseColor("#DEEDF6"));

        // Create another LinearLayout as a workaround to use it as a background
        // and to keep the 'down' arrow symbol
        // If spinner had the setBackgroundColor set, there would be no arrow symbol
        LinearLayout linearLayout2 = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -1);
        layoutParams2.setMargins(10, 2, 10, 5);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.setGravity(17);
        linearLayout2.setLayoutParams(layoutParams2);

        Spinner spinner = new Spinner(this);
        spinner.setPadding(5, 10, 5, 8);
        spinner.setLayoutParams(layoutParams2);
        spinner.getBackground().setColorFilter(1, PorterDuff.Mode.SRC_ATOP); //trick to show white down arrow color
        //Creating the ArrayAdapter instance having the list
        list.remove(0);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    ((TextView) parentView.getChildAt(0)).setTextColor(Color.parseColor("#f5f5f5"));
                    interInt.onSelected(uid,position);
                    p.play(p.SELECT,getApplicationContext());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    p.play(p.SELECT,getApplicationContext());
                }
            });
        linearLayout.addView(textView);
        linearLayout2.addView(spinner);
        Menus.addView(linearLayout);
        Menus.addView(linearLayout2);
    }
    private interface setOnSelected{
        void onSelected(int id,int i);
    }
    private interface InterfaceInt {
        void OnWrite(int id,int i);
    }
    private boolean isNotInGame() {
        RunningAppProcessInfo runningAppProcessInfo = new RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(runningAppProcessInfo);
        if (FeatureFunction.MenuRunningInBackground){
            return false;
        }else{
            return runningAppProcessInfo.importance != 100;
        }
    }
    public void Thread() {
        if (bg == null) {
            return;
        }
        if (isNotInGame()) {
            bg.setVisibility(View.INVISIBLE);
        } else {
            bg.setVisibility(View.VISIBLE);
        }
    }
    public void addTextField(final int id,String assetPath, int inputType, String hint, final onImageClick im){
        ImageView imageRight = new ImageView(getBaseContext());
        final LinearLayout gg = new LinearLayout(getBaseContext());
        gg.setLayoutParams(new LinearLayout.LayoutParams(
                               ViewGroup.LayoutParams.MATCH_PARENT,
                               ViewGroup.LayoutParams.WRAP_CONTENT));
        gg.setOrientation(LinearLayout.HORIZONTAL);
        gg.setGravity(Gravity.CENTER);
        gg.requestLayout();
        
        imageRight.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        imageRight.getLayoutParams().height = 35;
        imageRight.getLayoutParams().width = 35;
        imageRight.requestLayout();
        imageRight.setScaleType(ImageView.ScaleType.FIT_XY);
        if (assetPath.equals("sendImage")){
            imageRight.setImageDrawable(Base64ToBitmapDrawable(new AwangDaniSaddBoy().Send));
        }else if (assetPath.equals("searchImage")){
            imageRight.setImageDrawable(Base64ToBitmapDrawable(new AwangDaniSaddBoy().Search));
        }
        
        final EditText Target = new EditText(this);
        Target.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        Target.setHint(hint);
        Target.setInputType(inputType);
        Target.setPadding(8,8,8,8);
        Target.setTextSize(16);
        Target.setSingleLine(true);
        Target.setFocusable(true);
        Target.setFocusableInTouchMode(true);
        imageRight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    im.onClick(id,Target);
                }
            });
        Target.setOnTouchListener(new View.OnTouchListener() { 

                @Override

                public boolean onTouch(View v, MotionEvent event) { 

                    Target.setCursorVisible(true); 

                    WindowManager.LayoutParams floatWindowLayoutParamUpdateFlag = params; 
                    floatWindowLayoutParamUpdateFlag.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN; 
                    wm.updateViewLayout(bg, floatWindowLayoutParamUpdateFlag); 

                    return false; 

                } 
            });
        Target.requestFocus();
        gg.addView(Target);
        gg.addView(imageRight);
        Menus.addView(gg);
    }
    private interface onImageClick {
        void onClick(int id,EditText textInput);
    }
    public void showKey(boolean v, final EditText Target){

    }
    public void addButton(final int id,LinearLayout BackGround, final String Data ,final OnNumberClick onc){
        TextView txt = new TextView(getBaseContext());
        txt.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
        txt.setPadding(19,19,19,19);
        txt.setText(Data);
        txt.setTextSize(16);
        txt.setTextColor(0xFF000000);
        txt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    onc.onClick(id,Data);
                }
            });
        BackGround.addView(txt);

    }
    public interface OnNumberClick {
        void onClick(int id,String Title);
    }
    public static String toHexadecimal(String text){
        String hexText;
        try{
            String clearText = text;
            char[] chars = clearText.toCharArray();
            StringBuffer hex = new StringBuffer();
            for (int i = 0; i < chars.length; i++) {
                hex.append(Integer.toHexString((int) chars[i]));
            }
            hexText = hex.toString();
            return hexText;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void FeatureList(){
        String[] FL = FeatureFunction.Feature;
        for (int i=0;i < FL.length;i++) {
            if (FL[i].startsWith("Spinner_")){
                addSpinner(i, FL[i].replaceAll("Spinner_", ""), new setOnSelected(){

                        @Override
                        public void onSelected(int id, int inted) {
                            FeatureFunction.FunSpinner(getApplicationContext(),id,inted);
                        }
                        
                });
            }
            if (FL[i].startsWith("Switch_")){
                addSwitch(i, FL[i].replaceAll("Switch_", ""), new onSwitchChecked(){

                        @Override
                        public void OnChecked(int id, boolean z) {
                            FeatureFunction.FunSwitch(getApplicationContext(),id,z);
                        }
                        
                    
                });
            }
            if (FL[i].startsWith("Seekbar_")){
                String[] split = FL[i].replaceAll("Seekbar_","").split("_");
                addSeekBar(i, split[0], Integer.valueOf(split[1]), Integer.valueOf(split[2]), new InterfaceInt(){

                        @Override
                        public void OnWrite(int id, int tari) {
                            FeatureFunction.FunSeekbar(getApplicationContext(),id,tari);
                        }
                    });
            }
            if (FL[i].startsWith("Category_")){
                addCategory(FL[i].replaceFirst("Category_",""),Typeface.SERIF);
            }
            if (FL[i].startsWith("TextField_")){
                String[] split = FL[i].replaceAll("TextField_","").split("_");
                addTextField(i,split[1], InputType.TYPE_CLASS_TEXT,split[0],new onImageClick(){

                    @Override
                    public void onClick(int id, EditText textInput) {
                        FeatureFunction.FunTextField(getApplicationContext(),id,textInput.getText().toString());
                    }
                });
            }
        }
    }
    public ObjectAnimator rainbowAnim(TextView target){
        return ObjectAnimator.ofInt(target, "textColor",


                                    Color.RED,Color.YELLOW,Color.GREEN,Color.YELLOW,Color.YELLOW,Color.RED);
    }
    public BitmapDrawable Base64ToBitmapDrawable(String B64){
        byte[] decoded= android.util.Base64.decode(B64, android.util.Base64.DEFAULT);
        android.graphics.Bitmap bmp = android.graphics.BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
        android.graphics.drawable.BitmapDrawable bd = new android.graphics.drawable.BitmapDrawable(bmp);
        return bd;
    }
    public Bitmap getBitmapFromAssets(String fileName) {
        Bitmap bitmap = null;
        AssetManager assetManager = getAssets();
        try{
        InputStream istr = assetManager.open(fileName);
        bitmap = BitmapFactory.decodeStream(istr);
        istr.close();
        }catch(IOException e){
            
        }

        return bitmap;
    }
}

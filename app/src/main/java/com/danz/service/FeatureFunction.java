package com.danz.service;

import android.content.Context;
import android.graphics.Color;
import androtrainer.MemoryPatcher;

public class FeatureFunction {
    public static boolean gotoYoutube = false;
    public static String title = "AwangDani MOD MENU";
    public static int StrokeColor = Color.parseColor("#263238");
    public static int BgColor = Color.parseColor("#5A6177");
    public static int TitleBg = Color.parseColor("#3C4357");
    private static String dump = "icon.png";
    public static int CornerRadius = 5;
    public static boolean MenuRunningInBackground = false;
    public static String IdYoutube = "UCeH7tadQeQRjWJhkM3mBp4w";
    public static String toastB64 = "Q"+"3J"+"lY"+"XR"+"lZ"+"CB"+"Ce"+"SB"+"Bd"+"2F"+"uZ"+"0R"+"hb"+"mk=";
    
    public static boolean WithLoging = false;
    public static String username = "";
    public static String password = "";
    private static String StringToHex(String s){return DanzService.toHexadecimal(s);}
  
    // onMenu Create
    public static void onCreate(){
    }
    // List Feature
    
    // Used Feature
    // For Category : "Category_YourCategoryString"
    // For Switch : "Switch_YourSwitchName"
    // For Seekbar : "Seekbar_YourSeekBarName_Progress_ProgressMax"
    // For Spinner : "Spinner_TitleSpinner_Feature1_Feature2"
    // For TextField : "TextField_Hint_sendImage or searchImage
  
    public static String[] Feature = {
        "Category_MENU PLAYER",
        "Switch_GHOST MODE",
        "Switch_WALLHACK",
        "Seekbar_Speed Player_0_5",
        "Category_MENU MAP",
        "Switch_REMOVE HOUSE",
        "Switch_REMOVE TREE",
        "Category_MENU ACCOUNT",
        "Switch_RESET TASK",
        "Spinner_Modders_DANZ FF_AwangDani"
        
    };
    
    // Funtion
    private static void Function(Context c,int uuid,int SeekBar,boolean Switch,int Spinner,String Editext){
        String LibTargetMain = "libmain.so";
        switch(uuid){
            case 0:
                break;
            case 1:
                if (Switch){
                    MemoryPatcher.nativePatch(LibTargetMain,"302E3066",643184);
                }else{
                    MemoryPatcher.nativePatch(LibTargetMain,"312E3066",643184);
                }
                break;
            case 2:
                if (Switch){
                    MemoryPatcher.nativePatch(LibTargetMain,"77 61 6C 6C ",0x9CDD0);
                }else{
                    MemoryPatcher.nativePatch(LibTargetMain,"6D 61 69 6E ",0x9CDD0);
                }
                break;
            case 3:
                MemoryPatcher.nativePatch(LibTargetMain,StringToHex(Integer.toString(SeekBar))+"30 2E 30",643280);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                switch(Spinner){
                    case 0:
                        MemoryPatcher.nativePatch(LibTargetMain,"53636F72653A20",636732);
                        break;
                    case 1:
                        MemoryPatcher.nativePatch(LibTargetMain,StringToHex("A"+"w"+"a"+"n"+"g"+": ")+"3A20",636732);
                        break;
                }
                break;
        }
    }
    public static void FunSwitch(Context c, int uuid,boolean sw){
        Function(c,uuid,0,sw,0,"");
    }
    public static void FunSeekbar(Context c, int uuid,int sb){
        Function(c,uuid,sb,false,0,"");
    }
    public static void FunSpinner(Context c, int uuid,int sp){
        Function(c,uuid,0,false,sp,"");
    }
    public static void FunTextField(Context c, int uuid,String text){
        Function(c,uuid,0,false,0,text);
    }
    
}

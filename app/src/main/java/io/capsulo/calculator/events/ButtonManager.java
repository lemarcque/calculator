package io.capsulo.calculator.events;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import io.capsulo.calculator.R;


/**
 * @author lemarcque
 * Description : S'occupe de gérer les événements clicks sur les touches
 */

public class ButtonManager implements View.OnTouchListener {

    public static int ID;           // ID de la touche actuellement pressé  (Integer)
    public static String IDSTRING;  // ID de la touche actuellement pressé  (String)
    public static String TAG;       // TAG de la touche actuellement pressé
    public static View V;

    public ButtonManager() {
    }

    /* Add onclick method listener to the toucharea gridlayout */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ID = v.getId();
        IDSTRING = v.getResources().getResourceEntryName(ID);
        TAG = v.getTag().toString();
        V = v;

        // get the alpha color background
        int color = Color.TRANSPARENT;
        Drawable background = v.getBackground();                                                    // "background" du bouton
        if (background instanceof ColorDrawable) color = ((ColorDrawable) background).getColor();   // couleur au format integer
        String strColor = String.format("#%06X", 0xFFFFFF & color);                                 // Couleur au format string (6 digits)  - no alpha

        // Changement de la couleur de fond
        changeColor(strColor, event);

        return true;
    }

    private void changeColor(String color, MotionEvent event) {

        int colorBackground = 0;    // Couleur de fond des touches

        if(TAG.equals(ButtonString.TAG_NUMERIC) || TAG.equals(ButtonString.TAG_SPECIAL)) {
            int colorPressed;                                                                       // Couleur de fond des touches pressées
            int colorRelease;                                                                       // Couleur de fond des touches relâchées
            int colorPressedBtnEqual;                                                               // Couleur de fond de la touche "égal" pressée
            int colorReleaseBtnEqual;                                                               // Couleur de fond de la touche "égal" relâchée

            // Touche pressée
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                colorPressed = Color.parseColor("#33FFFFFF");                                       // 20% transparent
                colorPressedBtnEqual = Color.parseColor("#75e6f1");                                 // +20% blanc
                if(ID == R.id.btn_equal)
                    colorPressed = colorPressedBtnEqual;
                colorBackground = colorPressed;
            }
            // Touche relâchée
            else if(event.getAction() == MotionEvent.ACTION_UP) {
                colorRelease = Color.TRANSPARENT;                                                   // 100% transparent
                colorReleaseBtnEqual = Color.parseColor("#3BDBEA");                                 // -20% blanc
                if(ID == R.id.btn_equal)
                    colorRelease = colorReleaseBtnEqual;
                colorBackground = colorRelease;
            }

            // Gestion des boutons spécial
            switch (ID) {
                // Gestions des touches des spéciales
                case R.id.btn_clear:
                    Log.i("c", "clear the memory");
                    break;
                case R.id.btn_plusminus:
                    Log.i("c", "changement de signe");
                    break;
                case R.id.btn_percent:
                    Log.i("c", "percent");
                    break;
                case R.id.btn_equal:
                    Log.i("c", "equal");
                    break;
                default:
                    Log.i("info", "touche numérique !");
                    break;
            }

        }else if(TAG.equals(ButtonString.TAG_OPERATION)) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
                colorBackground = Color.parseColor("#66" + color.replace("#", ""));
            else if(event.getAction() == MotionEvent.ACTION_UP)
                colorBackground = Color.parseColor("#33" + color.replace("#", ""));


            // Gestions des touches opérations
            switch (ID) {
                case R.id.btn_divide:
                    Log.i("c", "divide");
                    break;
                case R.id.btn_multiply:
                    Log.i("c", "changement de signe");
                    break;
                case R.id.btn_subtract:
                    Log.i("c", "substract");
                    break;
                case R.id.btn_add:
                    Log.i("c", "adding");
                    break;
            }
        }

        // Affectation de la couleur
        V.setBackgroundColor(colorBackground);
    }




    /*
            // Gestions des touches numériques
            case R.id.btn_point:
                Log.i("c", "point");
                break;
            case R.id.btn_one:
                Log.i("c", "one");
                break;
            case R.id.btn_two:
                Log.i("c", "one");
                break;
            case R.id.btn_three:
                Log.i("c", "three");
                break;
            case R.id.btn_four:
                Log.i("c", "4");
                break;
            case R.id.btn_five:
                Log.i("c", "5");
                break;
            case R.id.btn_six:
                Log.i("c", "6 de signe");
                break;
            case R.id.btn_seven:
                Log.i("c", "7");
                break;
            case R.id.btn_eight:
                Log.i("c", "8");
                break;
            case R.id.btn_nine:
                Log.i("c", "9");
                break;
        }
    }*/
}
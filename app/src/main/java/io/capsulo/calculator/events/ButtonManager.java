package io.capsulo.calculator.events;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import io.capsulo.calculator.calculator.Calculator;
import io.capsulo.calculator.R;
import io.capsulo.calculator.activity.ComputationActivity;


/**
 * @author lemarcque
 * Description : S'occupe de gérer les événements clicks sur les touches
 */

public class ButtonManager implements View.OnTouchListener {

    // Propreties
    public static int ID;           // ID de la touche actuellement pressé  (Integer)
    public static String IDSTRING;  // ID de la touche actuellement pressé  (String)
    public static String TAG;       // TAG de la touche actuellement pressé
    public static Button V;

    private ComputationActivity activity;
    private Calculator calculator;  // Machine à calculer

    public ButtonManager(ComputationActivity activity) {
        calculator = new Calculator();
        this.activity = activity;
    }

    /* Add onclick method listener to the toucharea gridlayout */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Récupération des informations du bouttons
        ID = v.getId();
        IDSTRING = v.getResources().getResourceEntryName(ID);
        TAG = v.getTag().toString();
        V = (Button)v;

        // get the alpha color background
        int color = Color.TRANSPARENT;
        Drawable background = v.getBackground();                                                    // "background" du bouton
        if (background instanceof ColorDrawable) color = ((ColorDrawable) background).getColor();   // couleur au format integer
        String strColor = String.format("#%06X", 0xFFFFFF & color);                                 // Couleur au format string (6 digits)  - no alpha

        // Changement de la couleur de fond
        changeColor(strColor, event);

        return true;
    }

    /* Changement de la couleur de fond du bouton
       dépendant du type d'événement de du type du boutons */
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

                updateValues();
                colorBackground = colorRelease;
            }

        }else if(TAG.equals(ButtonString.TAG_OPERATION)) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                //colorBackground = Color.parseColor("#66" + color.replace("#", ""));
                colorBackground = Color.parseColor("#66FFFFFF");
            }else if(event.getAction() == MotionEvent.ACTION_UP) {
                //colorBackground = Color.parseColor("#33" + color.replace("#", ""));
                colorBackground = Color.parseColor("#33FFFFFF");
                updateCompute();
            }
        }

        // Affectation de la couleur
        V.setBackgroundColor(colorBackground);
    }

    // Fonction des boutons
    private void updateValues() {

        if(TAG.equals(ButtonString.TAG_SPECIAL)) {
            // Gestion des boutons spécial
            switch (ID) {
                // Gestions des touches des spéciales
                case R.id.btn_clear:
                    calculator.reset();
                    break;
                case R.id.btn_plusminus:
                    calculator.setSign();
                    break;
                case R.id.btn_percent:
                    calculator.getPercent();
                    break;
                case R.id.btn_equal:
                    calculator.compute();
                    break;
            }

            this.updateInterface();
        }else {
            calculator.addValues(V.getText().toString());
            this.updateInterface();
        }
    }

    private void updateCompute() {
        calculator.updateCompute(V.getText().toString());
    }

    private void updateInterface() {
        activity.getTxtResult().setText(calculator.getResult());
        activity.getTxtCompute().setText(calculator.getFormula());
    }

}
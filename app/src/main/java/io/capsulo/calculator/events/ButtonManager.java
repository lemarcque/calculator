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
import io.capsulo.calculator.calculator.Constants;


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
    private int backgroundColor;    // background color of button

    private ComputationActivity activity;
    private Calculator calculator;  // Machine à calculer

    public ButtonManager(ComputationActivity activity) {
        calculator = new Calculator();
        this.activity = activity;
        backgroundColor = 0;
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
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            this.handlingDown();
        if(event.getAction() == MotionEvent.ACTION_UP) {
            this.handlingUp();
            this.updateValues();
        }

        // Affectation de la couleur
        V.setBackgroundColor(backgroundColor);

        return true;
    }

    private void handlingDown() {
        int colorPressed = Color.parseColor("#33FFFFFF");
        int colorPressedBtnEqual = Color.parseColor("#75e6f1");

        if(TAG.equals(ButtonString.TAG_NUMERIC) || TAG.equals(ButtonString.TAG_SPECIAL)) {
            if(ID == R.id.btn_equal) backgroundColor = colorPressedBtnEqual;
            else backgroundColor = colorPressed;
        }
        if(TAG.equals(ButtonString.TAG_OPERATION)) {
            backgroundColor = Color.parseColor("#66FFFFFF");
        }
    }

    private void handlingUp() {
        int colorRelease = Color.TRANSPARENT;
        int colorReleaseBtnEqual = Color.parseColor("#3BDBEA");

        if(TAG.equals(ButtonString.TAG_NUMERIC) || TAG.equals(ButtonString.TAG_SPECIAL)) {
            if(ID == R.id.btn_equal) backgroundColor = colorReleaseBtnEqual;
            else backgroundColor = colorRelease;
        }
        if(TAG.equals(ButtonString.TAG_OPERATION)) {
            backgroundColor = Color.parseColor("#66FFFFFF");
        }

        if(TAG.equals(ButtonString.TAG_OPERATION)) {
            backgroundColor = Color.parseColor("#33FFFFFF");
        }
    }

    // Fonction des boutons
    private void updateValues() {

        if(TAG.equals(ButtonString.TAG_SPECIAL)) {
            // Gestion des boutons spécial
            switch (ID) {
                // Gestions des touches spéciales
                case Constants.CLEAR:
                    calculator.reset();
                    break;
                case Constants.PLUSMINUS:
                    calculator.setSign();
                    break;
                case Constants.PERCENT:
                    calculator.getPercent();
                    break;
                case Constants.EQUAL:
                    calculator.compute();
                    break;
            }
        }

        else if(TAG.equals(ButtonString.TAG_OPERATION))
            calculator.updateCompute(V.getText().toString());

        else if(TAG.equals(ButtonString.TAG_NUMERIC))
            calculator.addValues(V.getText().toString());

        this.updateInterface();
    }

    private void updateInterface() {
        activity.getTxtResult().setText(calculator.getResult());
        activity.getTxtCompute().setText(calculator.getFormula());
    }

}
package io.capsulo.calculator.events;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
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
    private int backgroundColor;

    private ComputationActivity activity;
    private Calculator calculator;

    public ButtonManager(ComputationActivity activity) {
        calculator = new Calculator();
        this.activity = activity;
        backgroundColor = 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Retrieve data
        ID = v.getId();
        IDSTRING = v.getResources().getResourceEntryName(ID);
        TAG = v.getTag().toString();
        V = (Button)v;

        // obtention de l'opacité de la couleur
        int color = Color.TRANSPARENT;
        Drawable background = v.getBackground();                                                    // "background" du bouton
        if (background instanceof ColorDrawable) color = ((ColorDrawable) background).getColor();   // couleur au format integer
        String strColor = String.format("#%06X", 0xFFFFFF & color);                                 // Couleur au format string (6 digits)  - no alpha

        // Changement de la couleur
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            this.handlingDown();
        if(event.getAction() == MotionEvent.ACTION_UP) {
            this.handlingUp();
            this.updateValues();
        }

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

        // Affectation de la couleur
        V.setBackgroundColor(backgroundColor);
    }

    private void handlingUp() {
        if(TAG.equals(ButtonString.TAG_OPERATION) || ID == Constants.EQUAL) {
            updateColor();
        }else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                int opacity = 255;

                @Override
                public void run() {
                    opacity -= 15;
                    if(opacity <= 0) {
                        opacity = 255;
                        updateColor();
                        handler.removeCallbacks(this);
                    } else {
                        V.getBackground().setAlpha(opacity);
                        handler.postDelayed(this, 10);
                    }
                }
            }, 0);
        }
    }
    // Only for down
    private void updateColor() {
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

        // Affectation de la couleur
        V.setBackgroundColor(backgroundColor);
    }

    private void updateValues() {

        // Gestion des boutons spécial
        if(TAG.equals(ButtonString.TAG_SPECIAL)) {
            switch (ID) {
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

        // Gestion des boutons d'opération
        else if(TAG.equals(ButtonString.TAG_OPERATION))
            calculator.addOperator(V.getText().toString());

            // Gestion des boutons numériques
        else if(TAG.equals(ButtonString.TAG_NUMERIC))
            calculator.addNumber(V.getText().toString());

        this.updateInterface();
    }

    private void updateInterface() {
        activity.getTxtResult().setText(calculator.getResult());
        activity.getTxtCompute().setText(calculator.getFormula());
    }

}
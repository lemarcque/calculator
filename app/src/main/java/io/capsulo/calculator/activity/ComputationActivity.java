package io.capsulo.calculator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import io.capsulo.calculator.R;

/**
 * @author lemarcque
 * Description :    classe de l'unique et seul activité de l'application
 *                  Elle gère toute les fonctionnalités (interactions avec l'utilisateur)
 *                  Gestion des touches et affichage du calcul et de sa réponse.
 */

public class ComputationActivity extends Activity {


    // Views
    private LinearLayout computationActivityLayout;
    private LinearLayout calculArea;
    private TextView computeNumber;
    private TextView resultNumber;
    GridLayout touchArea;
    private ArrayList<Button> numericBtn;
    private ArrayList<Button> operationBtn;
    private ArrayList<Button> specialBtn;

    // Propreties
    private float screenWidth;          // Largeur de l'écran du smartphone / Screen width of the device
    private float screenHeight;         // Hauteur de l'écran du smartphone / Screen height of the device
    private int widthButton;          // Largeur des boutons
    private int heightButton;         // Hauteur des boutons
    private final int columnCount = 4;
    private final int rowCount = 5;

    public void onCreate(Bundle saveInstanceState) {
        // configuration of the view
        super.onCreate(saveInstanceState);
        super.setContentView(R.layout.computationactivity_layout);

        init();
        setInterface();
    }

    /* configuration des ressources */
    private void init() {
        computationActivityLayout = (LinearLayout) this.findViewById(R.id.computationactivity_wrapper);
        calculArea = (LinearLayout) this.findViewById(R.id.calcularea);
        computeNumber = (TextView) this.findViewById(R.id.computenumber);
        resultNumber = (TextView) this.findViewById(R.id.resultnumber);
        touchArea = (GridLayout) this.findViewById(R.id.toucharea);

        // Add all the buttons in the grid (GridLayout)
        // Numeric touch : 1-2-3-4-5-6-7-8-9
        // Operation touch : / - x - (-) - +
        // Special touch : C - ± - %
        numericBtn = new ArrayList<Button>();
        operationBtn = new ArrayList<Button>();
        specialBtn = new ArrayList<Button>();

        numericBtn.addAll(
                Arrays.asList(
                        (Button) this.findViewById(R.id.btn_point),
                        (Button) this.findViewById(R.id.btn_zero),
                        (Button) this.findViewById(R.id.btn_one),
                        (Button) this.findViewById(R.id.btn_two),
                        (Button) this.findViewById(R.id.btn_three),
                        (Button) this.findViewById(R.id.btn_four),
                        (Button) this.findViewById(R.id.btn_five),
                        (Button) this.findViewById(R.id.btn_six),
                        (Button) this.findViewById(R.id.btn_seven),
                        (Button) this.findViewById(R.id.btn_eight),
                        (Button) this.findViewById(R.id.btn_nine)
                )
        );

        operationBtn.addAll(
                Arrays.asList(
                        (Button) this.findViewById(R.id.btn_divide),
                        (Button) this.findViewById(R.id.btn_multiply),
                        (Button) this.findViewById(R.id.btn_subtract),
                        (Button) this.findViewById(R.id.btn_add)
                )
        );

        specialBtn.addAll(
                Arrays.asList(
                        (Button) this.findViewById(R.id.btn_clear),
                        (Button) this.findViewById(R.id.btn_plusminus),
                        (Button) this.findViewById(R.id.btn_percent),
                        (Button) this.findViewById(R.id.btn_equal)
                        )
        );
    }

    /* Configurer la taille, le positionnement et l'agencement des views sur l'interface */
    private void setInterface() {
        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float pxWidth = outMetrics.widthPixels;     // Largeur en pixel
        float pxHeight = outMetrics.heightPixels;   // Hauteur en pixel

        screenWidth = pxWidth;
        screenHeight = pxHeight;

        widthButton = Math.round(screenWidth / columnCount);    // 1 : buttons width equal to screenwidth divide by the number of column
        heightButton = Math.round(widthButton);                 // 2 : the button height equal to buttons width

        // resize all the button
        for(Button btn : numericBtn) {
            btn.getLayoutParams().width = widthButton;
            btn.getLayoutParams().height = heightButton;
            btn.setOnClickListener(new OnClickListener());
            btn.requestLayout();
        }

        for(Button btn : specialBtn) {
            btn.getLayoutParams().width = widthButton;
            btn.getLayoutParams().height = heightButton;
            btn.setOnClickListener(new OnClickListener());
            btn.requestLayout();
        }

        for(Button btn : operationBtn) {
            btn.getLayoutParams().width = widthButton;
            btn.getLayoutParams().height = heightButton;
            btn.setOnClickListener(new OnClickListener());
            btn.requestLayout();
        }

        // 3 :  the positions of the gridlayout equal to the screenheight minus the gridlayout height
        //      or relatve to the calcularea position on the y axe
        calculArea.getLayoutParams().height = Math.round(screenHeight) - (heightButton * rowCount);  // no difference ?
    }

    public class OnClickListener implements View.OnClickListener {

        @Override
        // Add onclick method listener to the toucharea gridlayout
        public void onClick(View v) {
            switch(v.getId()) {
                // Gestions des touches des opérations
                case R.id.btn_clear:
                    Log.i("c", "clear the memory");
                    break;
                case R.id.btn_plusminus:
                    Log.i("c", "changement de signe");
                    break;
                case R.id.btn_percent:
                    Log.i("c", "divide");
                    break;
                case R.id.btn_equal:
                    Log.i("c", "equal");
                    break;

                // Gestions des touches spéciales
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
        }
    }

    public void verification
}

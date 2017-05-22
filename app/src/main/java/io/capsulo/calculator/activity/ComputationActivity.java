package io.capsulo.calculator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import io.capsulo.calculator.R;
import io.capsulo.calculator.events.ButtonManager;

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
    private TextView txtCompute;
    private TextView txtResult;
    GridLayout touchArea;
    private ArrayList<Button> buttons;

    // Propreties
    private float screenWidth;
    private int widthButton;
    private int heightButton;
    final private int columnCount = 4;
    final private int rowCount = 5;

    // Class
    private ButtonManager buttonManager;

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
        txtCompute = (TextView) this.findViewById(R.id.txtCompute);
        txtResult = (TextView) this.findViewById(R.id.txtResult);
        touchArea = (GridLayout) this.findViewById(R.id.toucharea);
        buttons = new ArrayList<>();
        buttons.addAll(
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
                        (Button) this.findViewById(R.id.btn_nine),

                        (Button) this.findViewById(R.id.btn_divide),
                        (Button) this.findViewById(R.id.btn_multiply),
                        (Button) this.findViewById(R.id.btn_subtract),
                        (Button) this.findViewById(R.id.btn_add),

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

        float screenWidth = outMetrics.widthPixels;

        widthButton = Math.round(screenWidth / columnCount);    // 1 : buttons width equal to screenwidth divide by the number of column
        heightButton = Math.round(widthButton);                 // 2 : the button height equal to buttons width

        buttonManager = new ButtonManager(this);
        // resize all the button
        for(Button btn : buttons) {
            btn.getLayoutParams().height = heightButton;
            btn.requestLayout();
            btn.setOnTouchListener(buttonManager);
        }
    }


    /* Getter */

    public TextView getTxtCompute() {
        return this.txtCompute;
    }
    public TextView getTxtResult() {
        return this.txtResult;
    }
}

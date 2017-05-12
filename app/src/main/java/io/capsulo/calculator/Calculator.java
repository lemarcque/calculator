package io.capsulo.calculator;

import android.util.Log;

import java.util.ArrayList;

/**
 * @author lemarcque
 * Description : Classe permettant d'effectuer des opérations mathématiques
 */

public class Calculator {

    private ArrayList<String> currentComputation;         // Calcul affiché dans la zone de texte
    private ArrayList<String> currrentWritingNumber;      /* Valeur du nombre : affiché dans le champs de texte
                                                                  actuellement entrain d'être écrit */

    public Calculator() {
        currentComputation = new ArrayList<String>();
        currrentWritingNumber = new ArrayList<String>();
    }

    /* Ajoute une valeur (point, entier, etc...) au nombre actuellement écrit */
    public void addValues(String values) {
        currrentWritingNumber.add(values);
    }

    /* On ajoute le nombre actuellement entrain d'être écrit au calcul
       et on rénitialise sa valeur à zéro.
     */
    public void updateCompute(String operator) {
        // Uniquement si un nombre est écrit
        if(currrentWritingNumber.size() > 0) {
            for(String n :  currrentWritingNumber) {
                currentComputation.add(n);
            }

            currrentWritingNumber = new ArrayList<String>();
            if(operator.length() > 0) currentComputation.add(operator);
        }
    }

    /* Calculer le calcul (not more explicit) */
    public void compute() {


        // User click on the equal button without defining calcul
        // Note: il faudrait plutôt vérifier si il existe un opérateur dans le calcul
        // findOperator() ....
        if(currentComputation.size() > 0) {
            // Savoir si c'est une addition et/ou soustraction
            // Si multi ou division
            // priorite etc.
            // efectuer l'additoin soustraction
            // et voila : done.
            updateCompute("");

            Log.i("calcul to calculate", currentComputation.toString());
            currentComputation = new ArrayList<String>();
        }
    }

    /* trouver un opérateur (signe) dans le calcul .. */
    private void findOperator() {

    }
}

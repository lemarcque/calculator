package io.capsulo.calculator;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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

            currrentWritingNumber.clear();
            if(operator.length() > 0) currentComputation.add(operator);
        }
    }

    /* Calculer le calcul (not more explicit) */
    public HashMap<String, String> compute() {
        HashMap<String, String> results = new HashMap<String, String>();

        // Existing calcul: User click on the equal button without defining calcul
        // Note: il faudrait plutôt vérifier si il existe un opérateur dans le calcul
        // findOperator() ....
        if(currentComputation.size() > 0) {
            // 1: Savoir si c'est une addition et/ou soustraction
            // 2: Si multi ou division
            // 3: priorite etc.
            // 4: efectuer l'additoin soustraction
            // 5: et voila : done.

            updateCompute("");  // Update the value of the current writer calcul
            trim();

            Log.i("calcul to calculate", currentComputation.toString());

            // On attache les Tableau de chaïne de caractère ensemble
            String resultJoined = "-";
            String calculJoined = TextUtils.join(" ", currentComputation);
            results.put("result", resultJoined);
            results.put("calcul", calculJoined);
            currentComputation.clear();
        }
        // Existing writing number
        else if(currrentWritingNumber.size() > 0) {
            // Si l'utilisateur a écrit un nombre mais aucun calcul
            // on affiche simplement ce nombre
            String resultJoined = TextUtils.join("", currrentWritingNumber);
            String calculJoined = resultJoined;
            results.put("result", resultJoined);
            results.put("calcul", calculJoined);
            clear();
        }

        return results;
    }

    /* trouver un opérateur (signe) dans le calcul .. */
    private void trim() {
        ArrayList<HashMap<String, String>> listOperators = new ArrayList<HashMap<String, String>>();
        boolean toReplace = false;

        for(int i = 0; i < currentComputation.size(); i++) {
            if(currentComputation.get(i).toLowerCase().equals("x") || currentComputation.get(i).equals("÷")) {
                toReplace = true;
                listOperators.add(new HashMap<String, String>());
                listOperators.get(listOperators.size() - 1).put("pos", String.valueOf(i));          // pos : position dans le tableau
                listOperators.get(listOperators.size() - 1).put("value", currentComputation.get(i));// values : valeur du nombre ou autres
            }
        }

        // Si la variable toReplace equals true, alors il faut "do stuff"
        if(toReplace) {
            replace(listOperators);
        }else {
            calculate();
        }

    }

    private void replace(ArrayList<HashMap<String, String>> operators) {
        for (int i = 0; i < operators.size(); i++) {
            boolean leftValuesNotNull = true;
            boolean rightValuesNotNull = true;
            int leftNumber;                     //  Le nombre se toruvant à la droite de l'opérateur
            int rightNumber;                    // Le nombre se toruvant à la droite de l'opérateur
            int index = 0;
            ArrayList<String> currentLeftNumber = new ArrayList<String>();
            String currentRightNumber = "";

            while(leftValuesNotNull) {
                index--;
                int newpos = Integer.parseInt(operators.get(i).get("pos")) + index;     // Position de la valeur

                if(newpos >= 0) {
                    String values = currentComputation.get(newpos);
                    if(!values.equals("x") && !values.equals("÷") && !values.equals("+") && !values.equals("-")) {
                        currentLeftNumber.add(values);
                    }else {
                        leftValuesNotNull = false;
                        index = 0;
                    }
                }else {
                    leftValuesNotNull = false;
                    index = 0;
                }
            }

            while(rightValuesNotNull) {
                index++;
                int newpos = Integer.parseInt(operators.get(i).get("pos")) + index;     // Position de la valeur

                if(newpos < currentComputation.size()) {
                    String values = currentComputation.get(newpos);
                    if(!values.equals("x") && !values.equals("÷") && !values.equals("+") && !values.equals("-")) {
                        currentRightNumber += values;
                    }else {
                        rightValuesNotNull = false;
                        index = 0;
                    }
                }else {
                    rightValuesNotNull = false;
                    index = 0;
                }

            }

            Collections.reverse(currentLeftNumber);
            Log.i("left", TextUtils.join("", currentLeftNumber));
            Log.i("o", operators.get(i).get("value"));
            Log.i("right", currentRightNumber);
        }
    }

    private void calculate() {

    }

    // Rafraichit  / Rénitialise
    private void clear() {
        currentComputation.clear();
        currrentWritingNumber.clear();
    }
}

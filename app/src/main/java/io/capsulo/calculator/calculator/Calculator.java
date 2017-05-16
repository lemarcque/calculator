package io.capsulo.calculator.calculator;

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

    private String computation;
    private String result;
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
            computation = TextUtils.join(" ", currentComputation);
        }
    }

    /* Calculer le calcul (not more explicit) */
    public void compute() {
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

            Log.i("result array", currentComputation.toString());

            // On attache les Tableau de chaïne de caractère ensemble
            result = TextUtils.join("", currentComputation);
            currentComputation.clear();
        }
        // Existing writing number
        else if(currrentWritingNumber.size() > 0) {
            // Si l'utilisateur a écrit un nombre mais aucun calcul
            // on affiche simplement ce nombre
            result = TextUtils.join("", currrentWritingNumber);
            clear();
        }
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
        if(toReplace)
            replace(listOperators);
        calculate();
    }

    private void replace(ArrayList<HashMap<String, String>> operators) {
        for (int i = 0; i < operators.size(); i++) {
            boolean leftValuesNotNull = true;
            boolean rightValuesNotNull = true;
            int leftNumber;                     //  Le nombre se toruvant à la droite de l'opérateur
            int rightNumber;                    // Le nombre se toruvant à la droite de l'opérateur
            int index = 0;

            ArrayList<String> arrcurrentLeftNumber = new ArrayList<String>();
            String currentLeftNumber = "";
            String currentRightNumber = "";
            String newValue =  "";
            int newPosition = -1;

            while(leftValuesNotNull) {
                index--;
                int newpos = Integer.parseInt(operators.get(i).get("pos")) + index;     // Position de la valeur

                if(newpos >= 0) {
                    String values = currentComputation.get(newpos);
                    if(!values.equals("x") && !values.equals("÷") && !values.equals("+") && !values.equals("-")) {
                        arrcurrentLeftNumber.add(values);
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

            Collections.reverse(arrcurrentLeftNumber);
            currentLeftNumber = TextUtils.join("", arrcurrentLeftNumber);

            // calculate
            if(operators.get(i).get("value").equals("x")) {
                // handling floating..
                if(currentLeftNumber.contains(".") || currentRightNumber.contains(".")) {
                    float multiFloatingResult = Float.parseFloat(currentLeftNumber) * Float.parseFloat(currentRightNumber);
                    newValue = String.valueOf(multiFloatingResult);
                }else {
                    int multiplicationResult = Integer.parseInt(currentLeftNumber) * Integer.parseInt(currentRightNumber);
                    newValue = String.valueOf(multiplicationResult);
                }

            }else if(operators.get(i).get("value").equals("÷")) {
                // handling floating..
                if(currentLeftNumber.contains(".") || currentRightNumber.contains(".")) {
                    float diviFloatingResult = Float.parseFloat(currentLeftNumber) / Float.parseFloat(currentRightNumber);
                    newValue = String.valueOf(diviFloatingResult);
                }else {
                    int divisionResult = Integer.parseInt(currentLeftNumber) / Integer.parseInt(currentRightNumber);
                    newValue = String.valueOf(divisionResult);
                }
            }else if(operators.get(i).get("value").equals("+")) {
                // handling floating..
                if(currentLeftNumber.contains(".") || currentRightNumber.contains(".")) {
                    float addFloatingResult = Float.parseFloat(currentLeftNumber) + Float.parseFloat(currentRightNumber);
                    newValue = String.valueOf(addFloatingResult);
                }else {
                    int additionResult = Integer.parseInt(currentLeftNumber) + Integer.parseInt(currentRightNumber);
                    newValue = String.valueOf(additionResult);
                }
            }else if(operators.get(i).get("value").equals("-")) {
                // handling floating..
                if(currentLeftNumber.contains(".") || currentRightNumber.contains(".")) {
                    float minusFloatinResult = Float.parseFloat(currentLeftNumber) - Float.parseFloat(currentRightNumber);
                    newValue = String.valueOf(minusFloatinResult);
                }else {
                    int minusResult = Integer.parseInt(currentLeftNumber) - Integer.parseInt(currentRightNumber);
                    newValue = String.valueOf(minusResult);
                }
            }



            String calculBloc = currentLeftNumber + operators.get(i).get("value") + currentRightNumber;
            int deletePos = -1;

            //remove the bloc of calcul
            for(int j = 0; j < calculBloc.length(); j++) {
                if(deletePos == -1) {
                    deletePos = Integer.parseInt(operators.get(i).get("pos")) - currentLeftNumber.length();
                }
                currentComputation.remove(deletePos);
            }

            // insert newvalue in the calcul
            for(int k = 0; k < newValue.length(); k ++) {
                String reverseValue = new StringBuilder(newValue).reverse().toString();     // New value reverted
                currentComputation.add(deletePos, String.valueOf(reverseValue.charAt(k)));
            }

            newPosition = currentComputation.size() - newValue.length();
            //change the position of each operator
            for(HashMap<String, String> o : operators) {
                //Log.i("pos", String.valueOf(Integer.parseInt(o.get("pos")) - (calculBloc.length() - newValue.length())));
                o.put("pos", String.valueOf(Integer.parseInt(o.get("pos")) - (calculBloc.length() - newValue.length())));
            }

        }
    }

    /* Cette fonction va simplement effectuer les additions et les soustractions
       Si il n y a aucune opération ä effectuer, il ne fait rien
     */
    private void calculate() {
        ArrayList<HashMap<String, String>> listOperators = new ArrayList<HashMap<String, String>>();
        boolean toReplace = false;

        for(int i = 0; i < currentComputation.size(); i++) {
            if(currentComputation.get(i).toLowerCase().equals("+") || currentComputation.get(i).equals("-")) {
                toReplace = true;
                listOperators.add(new HashMap<String, String>());
                listOperators.get(listOperators.size() - 1).put("pos", String.valueOf(i));          // pos : position dans le tableau
                listOperators.get(listOperators.size() - 1).put("value", currentComputation.get(i));// values : valeur du nombre ou autres
            }
        }

        if(toReplace) replace(listOperators);
    }

    // Rafraichit  / Rénitialise
    public void clear() {
        currentComputation.clear();
        currrentWritingNumber.clear();
        result = "";
        computation = "";
    }


    /* FONCTION SPECIAL */
    private void setSignCurrentWriterNumber() {

    }

    /* use float instead.. */
    public void setPercentWriterNumber() {
        /*if(currrentWritingNumber.size() > 0) {Log.i("LO", currrentWritingNumber.toString());
            Integer n = Integer.parseInt(TextUtils.join("", currrentWritingNumber));
            n = n / 100;
            String writerNumber = String.valueOf(n);
            currrentWritingNumber.clear();

            for(int i = 0; i < writerNumber.length(); i++) {
                currrentWritingNumber.add(String.valueOf(writerNumber.charAt(i)));
            }

        }*/
    }

    /* GETTER / SETTER */
    public String getComputation() {
        return this.computation;
    }

    public String getResult() {
        return this.result;
    }
}

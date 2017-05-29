package io.capsulo.calculator.calculator;

/**
 * @author lemarcque
 * Description : Classe comportant plusieurs propriétés statiques
 * permettant de définir le status de la calculatrice
 */

public class Mode {

    public static String RESET = "reset";           // lorsque le calcul est remit à zéro
    public static String WRITING = "writing";       // lorsque l'on est entrain d'écrire un calcul
    public static String OFF = "off";               // lorsque le résultat final est affiché
}

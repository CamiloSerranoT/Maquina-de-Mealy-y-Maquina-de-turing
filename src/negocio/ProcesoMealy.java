/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author CAMILO
 */
public class ProcesoMealy {

    public String estadoInicial;
    public ArrayList<String> alfabetoEntrada;
    public ArrayList<String> alfabetoSalida;
    public String cadena;
    Controlador control;

    public ProcesoMealy(String estado, ArrayList entrada, ArrayList salida, String auxCadena, Controlador controlAux) {
        this.estadoInicial = estado;
        this.alfabetoEntrada = entrada;
        this.alfabetoSalida = salida;
        this.cadena = auxCadena;
        this.control = controlAux;
        metodoPrincipal();
    }

    private void metodoPrincipal() {
        ArrayList<String> estadoActual = new ArrayList();
        ArrayList<String> procesCadena = new ArrayList();
        ArrayList<String> transicionEntrada = new ArrayList();
        ArrayList<String> transicionSalida = new ArrayList();
        ArrayList<String> salida = new ArrayList();
        boolean comprobarEntrada = false;
        boolean comprobarSalida = false;

        estadoActual.add(estadoInicial);
        for (int i = 0; i < cadena.length(); i++) {
            comprobarEntrada = false;
            procesCadena.add(Character.toString(cadena.charAt(i)));
            //Verifica y encuentra la funcion de entrada correspondiente
            for (int j = 0; j < alfabetoEntrada.size(); j++) {
                if (control.fraseCompleta(Character.toString(alfabetoEntrada.get(j).charAt(4)), estadoActual.get(i)).matches()) {
                    if (control.fraseCompleta(Character.toString(alfabetoEntrada.get(j).charAt(8)), procesCadena.get(i)).matches()) {
                        transicionEntrada.add(alfabetoEntrada.get(j));
                        estadoActual.add(Character.toString(alfabetoEntrada.get(j).charAt(14)));
                        j = alfabetoEntrada.size() + 1;
                        comprobarEntrada = true;
                    }
                }
            }

            if (comprobarEntrada == true) {
                comprobarSalida = false;
                //Verifica y encuentra la funcion de salida correspondiente
                for (int j = 0; j < alfabetoSalida.size(); j++) {
                    if (control.fraseCompleta(Character.toString(alfabetoSalida.get(j).charAt(4)), estadoActual.get(i)).matches()) {
                        if (control.fraseCompleta(Character.toString(alfabetoSalida.get(j).charAt(8)), procesCadena.get(i)).matches()) {
                            salida.add(Character.toString(alfabetoSalida.get(j).charAt(14)));
                            transicionSalida.add(alfabetoSalida.get(j));
                            j = alfabetoSalida.size() + 1;
                            comprobarSalida = true;
                        }
                    }
                }
                if (comprobarSalida == false) {
                    i = cadena.length() + 1;
                    JOptionPane.showMessageDialog(null, "No se encontró la correspondiente transición de salida(λ)."
                            + "\n         La cadena de caracteres no cumple los requisitos. ", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                i = cadena.length() + 1;
                JOptionPane.showMessageDialog(null, "No se encontró la correspondiente transición de entrada(T)."
                        + "\n         La cadena de caracteres no cumple los requisitos. ", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (i + 1 == cadena.length()) {
                JOptionPane.showMessageDialog(null, "La cadena de caracteres introducida, si cumple con la 6-tupla.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        control.pasoDeDatosPEscritorio(estadoActual, procesCadena, transicionEntrada, transicionSalida, salida);
    }
}

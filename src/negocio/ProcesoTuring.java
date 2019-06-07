/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.ArrayList;

/**
 *
 * @author CAMILO
 */
public class ProcesoTuring {

    String estadoInicialMT;
    String simFinalMT;
    String estadosAceptacionMT;
    ArrayList<String> arrayMT;

    public ProcesoTuring(String estadoInicial, String simFinal, String aceptacion, ArrayList array) {
        this.estadoInicialMT = estadoInicial;
        this.simFinalMT = simFinal;
        this.estadosAceptacionMT = aceptacion;
        this.arrayMT = array;
    }
}

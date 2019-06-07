/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import vista.MMealy;
import vista.MTuring;
import vista.Menu;
import vista.TransicionMealy;
import vista.TransicionTuring;

/**
 *
 * @author CAMILO
 */
public class Controlador {

    Menu menu;
    MMealy mealy;
    TransicionMealy transMealy;
    ProcesoMealy procMealy;
    MTuring turing;
    TransicionTuring transTuring;
    ProcesoTuring procTuring;
    //atributos de M.Mealy
    String funFinalEstados;
    String funFinalEstadoInicial;
    String funFinalAlfaEntrada;
    String funFinalAlfaSalida;
    ArrayList<String> arrayEntrada;
    ArrayList<String> arraySalida;
    //atributos de M.Turing
    String funFinalEstadosMT;
    String funFinalEstadoInicialMT;
    String funFinalEntradaMT;
    String funSimExtremosMT;
    String funFinalCintaMT;
    String funAceptacionMT;
    ArrayList<String> arrayTuring;

    public Controlador() {
        arrayEntrada = new ArrayList();
        arraySalida = new ArrayList();
        arrayTuring = new ArrayList();
    }

    // <editor-fold defaultstate="collapsed" desc="Creacion de clases"> 
    public void maquina(int numMaquina, Menu menuNuevo) {
        menu = menuNuevo;
        menu.setVisible(false);
        if (numMaquina == 1) {
            MTuring maquinaTuring = new MTuring(this);
            turing = maquinaTuring;
            maquinaTuring.setVisible(true);
        } else {
            MMealy maquinaMealy = new MMealy(this);
            mealy = maquinaMealy;
            maquinaMealy.setVisible(true);
        }
    }

    public void transMaquinaMealy(DefaultTableModel model) {
        TransicionMealy transicion = new TransicionMealy(this, model);
        transMealy = transicion;
        transicion.setVisible(true);
        mealy.setVisible(false);
    }

    public void transMaquinaTuring(DefaultTableModel modelo) {
        TransicionTuring transicionMT = new TransicionTuring(this, modelo);
        transTuring = transicionMT;
        transicionMT.setVisible(true);
        turing.setVisible(false);
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="Paso entre clases"> 
    public void pasoDeClases(int opcion) {
        switch (opcion) {
            case 1:
                mealy.setVisible(false);
                menu.setVisible(true);
                break;
            case 2:
                transMealy.setVisible(false);
                mealy.setVisible(true);
                break;
            case 3:
                turing.setVisible(false);
                menu.setVisible(true);
                break;
            case 4:
                transTuring.setVisible(false);
                turing.setVisible(true);
                break;
        }
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Comprobaciones mealy"> 
    // <editor-fold defaultstate="collapsed" desc="MMealy"> 
    public boolean comprobacionMMealy(int canEstados, String jt1, String jt2, String jt3) {
        boolean valido = false;
        boolean salir = false;
        boolean repite = false;
        int opcion = 1;
        String funEstados = "";
        String funAlfabetoEntrada = "";
        String aux = "";
        String funAlfabetoSalida = "";

        do {
            salir = true;
            if (jt1.length() == 0 || jt2.length() == 0 || jt3.length() == 0) {
                JOptionPane.showMessageDialog(null, "    Para agregar alguna transición, es recomendable\n"
                        + "llenar primero todos parámetros en blanco anteriores.\n"
                        + "               Por favor llenarlos y volver a intentar", "Recomendación", JOptionPane.QUESTION_MESSAGE);
            } else {
                switch (opcion) {
                    case 1:
                        if (canEstados != 1) {
                            funEstados = funEstados + "(0";
                            for (int i = 1; i < canEstados; i++) {
                                funEstados = funEstados + "|" + i;
                            }
                            funEstados = funEstados + ")";
                            opcion++;
                            salir = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Debe seleccionar la cantidad de estados que manejara\n"
                                    + "               por favor escogerlos y volver a intentar", "Recomendación", JOptionPane.QUESTION_MESSAGE);
                        }
                        break;
                    case 2:
                        if (jt1.length() == 1) {
                            if (fraseCompleta(funEstados, jt1).matches()) {
                                opcion++;
                                salir = false;
                            } else {
                                JOptionPane.showMessageDialog(null, "El parámetro inicial, debe ser uno de los estados determinados\n"
                                        + "             en el campo anterior, por favor vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Solo puede existir un parámetro inicial\n"
                                    + "         por favor vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case 3:
                        repite = false;
                        funAlfabetoEntrada = funAlfabetoEntrada + "(";
                        for (int i = 0; i < jt2.length(); i++) {
                            aux = Character.toString(jt2.charAt(i));
                            if (fraseCompleta(funAlfabetoEntrada + ")", aux).matches()) {
                                JOptionPane.showMessageDialog(null, "Uno de los símbolos en el alfabeto de entrada(Σ)\n"
                                        + "        se repite, por favor volverlo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                                i = jt2.length() + 1;
                                repite = true;
                            } else {
                                if (fraseCompleta(funEstados, aux).matches()) {
                                    JOptionPane.showMessageDialog(null, "Uno de los símbolos introducidos en el alfabeto de\n"
                                            + "     entrada(Σ) es un estado, y no está permitido.\n"
                                            + "                  Por favor vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                                    i = jt2.length() + 1;
                                    repite = true;
                                } else {
                                    if (fraseCompleta("(,| )", aux).matches()) {
                                    } else {
                                        if (funAlfabetoEntrada.length() == 1) {
                                            funAlfabetoEntrada = funAlfabetoEntrada + aux;
                                        } else {
                                            funAlfabetoEntrada = funAlfabetoEntrada + "|" + aux;
                                        }
                                    }
                                }

                            }
                        }
                        funAlfabetoEntrada = funAlfabetoEntrada + ")";
                        if (funAlfabetoEntrada.length() == 2) {
                            JOptionPane.showMessageDialog(null, "No hay ningún símbolo en el alfabeto de entrada(Σ)\n"
                                    + "  que se pueda usar, por favor volverlo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (repite != true) {
                                opcion++;
                                salir = false;
                            }
                        }
                        break;
                    case 4:
                        repite = false;
                        funAlfabetoSalida = "(";
                        aux = "";
                        for (int i = 0; i < jt3.length(); i++) {
                            aux = Character.toString(jt3.charAt(i));
                            if (fraseCompleta(funAlfabetoSalida + ")", aux).matches()) {
                                JOptionPane.showMessageDialog(null, "Uno de los símbolos en el alfabeto de salida(O)\n"
                                        + "        se repite, por favor volverlo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                                i = jt3.length() + 1;
                                repite = true;
                            } else {
                                if (fraseCompleta("(,| )", aux).matches()) {
                                } else {
                                    if (funAlfabetoSalida.length() == 1) {
                                        funAlfabetoSalida = funAlfabetoSalida + aux;
                                    } else {
                                        funAlfabetoSalida = funAlfabetoSalida + "|" + aux;
                                    }
                                }
                            }
                        }
                        funAlfabetoSalida = funAlfabetoSalida + ")";
                        if (funAlfabetoSalida.length() == 2) {
                            JOptionPane.showMessageDialog(null, "No hay ningún símbolo en el alfabeto de salida(O)\n"
                                    + " que se pueda usar, por favor volverlo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (repite != true) {
                                valido = true;
                                funFinalEstados = funEstados;
                                funFinalEstadoInicial = jt1;
                                funFinalAlfaEntrada = funAlfabetoEntrada;
                                funFinalAlfaSalida = funAlfabetoSalida;
                            }
                        }
                        break;
                }
            }
        } while (salir == false);
        return valido;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="TransicionMealy"> 
    public boolean comprobarTransMealy(String jt1, String jt2, String jt3, String jt4, String jt5, String jt6) {
        int cont = 1;
        boolean valido = false;
        boolean salir = false;

        if (jt1.length() == 0 || jt2.length() == 0 || jt3.length() == 0 || jt4.length() == 0 || jt5.length() == 0 || jt6.length() == 0) {
            JOptionPane.showMessageDialog(null, "            Algún campo de transición esta vacio\n"
                    + "Por favor llenarlo para poder agregar la transición", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (jt1.length() != 1 || jt2.length() != 1 || jt3.length() != 1 || jt4.length() != 1 || jt5.length() != 1 || jt6.length() != 1) {
                JOptionPane.showMessageDialog(null, "En cada campo solo puede ir maximo un simbolo.\n"
                        + "                  Por favor, vuelvalo a intentar", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                do {
                    salir = true;
                    switch (cont) {
                        case 1:
                            if (fraseCompleta(funFinalEstados, jt1).matches()) {
                                cont++;
                                salir = false;
                            } else {
                                JOptionPane.showMessageDialog(null, "El primer campo de texto, solo recibe algún estado ya establecido\n"
                                        + "                             Por favor, vuélvalo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 2:
                            if (fraseCompleta(funFinalAlfaEntrada, jt2).matches()) {
                                cont++;
                                salir = false;
                            } else {
                                JOptionPane.showMessageDialog(null, "El segundo campo de texto, solo recibe símbolos\n"
                                        + "    ya introducidos en el Alfabeto de entrada(Σ).\n"
                                        + "                 Por favor, vuélvalo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 3:
                            if (fraseCompleta(funFinalEstados, jt3).matches()) {
                                cont++;
                                salir = false;
                            } else {
                                JOptionPane.showMessageDialog(null, "El tercer campo de texto, solo recibe algún estado ya establecido\n"
                                        + "                             Por favor, vuélvalo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 4:
                            if (fraseCompleta(funFinalEstados, jt4).matches()) {
                                cont++;
                                salir = false;
                            } else {
                                JOptionPane.showMessageDialog(null, "El cuarto campo de texto, solo recibe algún estado ya establecido\n"
                                        + "                             Por favor, vuélvalo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 5:
                            if (fraseCompleta(funFinalAlfaEntrada, jt5).matches()) {
                                cont++;
                                salir = false;
                            } else {
                                JOptionPane.showMessageDialog(null, "El quinto campo de texto, solo recibe símbolos\n"
                                        + "  ya introducidos en el Alfabeto de entrada(Σ).\n"
                                        + "               Por favor, vuélvalo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 6:
                            if (fraseCompleta(funFinalAlfaSalida, jt6).matches()) {
                                valido = true;
                            } else {
                                JOptionPane.showMessageDialog(null, "El ultimo campo de texto, solo recibe simbolos\n"
                                        + "    ya introducidos en el Alfabeto de salida(O).\n"
                                        + "                 Por favor, vuélvalo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                } while (salir == false);
                if (valido == true) {
                    if (comTransIgual(jt1, jt2, jt3, arrayEntrada) == false) {
                        if (comTransIgual(jt4, jt5, jt6, arraySalida) == false) {
                            arrayEntrada.add("T ( " + jt1 + " , " + jt2 + " ) → " + jt3);
                            arraySalida.add("λ ( " + jt4 + " , " + jt5 + " ) → " + jt6);
                            mealy.agregarALaTabla("T ( " + jt1 + " , " + jt2 + " ) → " + jt3, "λ ( " + jt4 + " , " + jt5 + " ) → " + jt6);
                        } else {
                            JOptionPane.showMessageDialog(null, "No pueden existir dos transiciones de salida(λ) iguales\n"
                                    + "                       Por favor vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No pueden existir dos transiciones de entrada(T) iguales\n"
                                + "                        Por favor vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        return valido;
    }

    private boolean comTransIgual(String com1, String com2, String com3, ArrayList<String> array) {
        boolean repetido = false;
        for (int i = 0; i < array.size(); i++) {
            if (fraseCompleta(Character.toString(array.get(i).charAt(4)), com1).matches()) {
                if (fraseCompleta(Character.toString(array.get(i).charAt(8)), com2).matches()) {
                    if (fraseCompleta(Character.toString(array.get(i).charAt(14)), com3).matches()) {
                        repetido = true;
                        i = array.size() + 1;
                    } else {

                    }
                }
            }
        }
        return repetido;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Cadena de caracteres">
    public void comprobarCadena(String jt4, int canEstados, String jt1, String jt2, String jt3) {
        String aux = "";
        int cont = 0;

        if (mealy.getCanTransiciones() == 0) {
            JOptionPane.showMessageDialog(null, "Debe agregar mínimo una transición, para poder validar la cadena\n"
                    + "                                           Vuelva a intentarlo", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (comprobacionMMealy(canEstados, jt1, jt2, jt3) == true) {
                if (segundaComprovacionTransiciones() == true) {
                    if (jt4.length() == 0) {
                        JOptionPane.showMessageDialog(null, "La cadena esta vacía, por favor llenarla antes "
                                + "de volver a intentar\n", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        for (int i = 0; i < jt4.length(); i++) {
                            aux = Character.toString(jt4.charAt(i));
                            if (fraseCompleta(funFinalAlfaEntrada, aux).matches()) {
                                cont++;
                            }
                        }
                        if (cont == jt4.length()) {
                            procMealy = new ProcesoMealy(funFinalEstadoInicial, arrayEntrada, arraySalida, jt4, this);
                        } else {
                            if (cont + 1 == jt4.length()) {
                                JOptionPane.showMessageDialog(null, "Un símbolo de la cadena no corresponde\n"
                                        + "     a alguno del alfabeto de entrada(Σ).\n"
                                        + "           Por favor, vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "            Varios símbolos de la cadena no\n"
                                        + "corresponden a los del alfabeto de entrada(Σ).\n"
                                        + "                Por favor, vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Alguna transición no cumple los parámetros, especificados en la 6-tupla\n"
                            + "          Por favor, elimínela o modifique los datos para continuar", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public boolean segundaComprovacionTransiciones() {
        boolean valido = true;
        int cont = 0;

        for (int i = 0; i < arrayEntrada.size(); i++) {
            if (fraseCompleta(funFinalEstados, Character.toString(arrayEntrada.get(i).charAt(4))).matches()) {
                if (fraseCompleta(funFinalAlfaEntrada, Character.toString(arrayEntrada.get(i).charAt(8))).matches()) {
                    if (fraseCompleta(funFinalEstados, Character.toString(arrayEntrada.get(i).charAt(14))).matches()) {
                        if (fraseCompleta(funFinalEstados, Character.toString(arraySalida.get(i).charAt(4))).matches()) {
                            if (fraseCompleta(funFinalAlfaEntrada, Character.toString(arraySalida.get(i).charAt(8))).matches()) {
                                if (fraseCompleta(funFinalAlfaSalida, Character.toString(arraySalida.get(i).charAt(14))).matches()) {
                                    cont++;
                                }
                            }
                        }
                    }
                }
            }

            if (i + 1 != cont) {
                valido = false;
                i = arrayEntrada.size() + 1;
            }
        }
        return valido;
    }
    // </editor-fold> 
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Comprobaciones turing"> 
    // <editor-fold defaultstate="collapsed" desc="M.Turing"> 
    public boolean comprobacionCamposTuring(String jt1, String jt2, String jt3, String jt4, String jt6) {
        boolean valido = false;
        boolean salir = false;
        boolean empezar = true;
        boolean primero = true;
        int cont = 1;
        int auxCont = 0;
        String aux = "";
        String aux2 = "";
        String funEstados = "";
        String funEntrada = "";
        String funAceptacion = "";

        do {
            salir = true;
            empezar = true;
            if (jt1.length() == 0 || jt2.length() == 0 || jt3.length() == 0 || jt4.length() == 0 || jt6.length() == 0) {
                JOptionPane.showMessageDialog(null, "Para continuar es recomendable llenar primero todos los\n"
                        + "  campos vacios. Por favor llenarlos y volver a intentar\n", "Recomendación", JOptionPane.QUESTION_MESSAGE);
            } else {
                switch (cont) {
                    case 1:
                        funEstados = "(";
                        jt1 = jt1 + ",";
                        for (int i = 0; i < jt1.length(); i++) {
                            if (empezar == true && (fraseCompleta("(,| )", Character.toString(jt1.charAt(i))).matches())) {
                            } else {
                                empezar = false;
                                if (fraseCompleta(",", Character.toString(jt1.charAt(i))).matches()) {
                                    if (fraseCompleta(funEstados + ")", aux).matches()) {
                                        JOptionPane.showMessageDialog(null, "Algun simbolo se repitio dos  o mas veces en Q\n"
                                                + "         Por favor quitarlo y volver a probar", "Error", JOptionPane.ERROR_MESSAGE);
                                        i = jt1.length();
                                        auxCont = i;
                                    } else {
                                        if (primero == true) {
                                            primero = false;
                                            funEstados = funEstados + aux;
                                        } else {
                                            funEstados = funEstados + "|" + aux;
                                        }
                                        aux = "";
                                        empezar = true;
                                    }
                                } else {
                                    if (fraseCompleta(" ", Character.toString(jt1.charAt(i))).matches()) {
                                    } else {
                                        aux = aux + Character.toString(jt1.charAt(i));
                                    }
                                }
                            }
                            auxCont++;
                        }

                        if (auxCont == jt1.length()) {
                            salir = false;
                            cont++;
                            funEstados = funEstados + ")";
                            funFinalEstadosMT = funEstados;
                        }
                        break;
                    case 2:
                        if (fraseCompleta(funFinalEstadosMT, jt2).matches()) {
                            salir = false;
                            cont++;
                            funFinalEstadoInicialMT = jt2;
                        } else {
                            JOptionPane.showMessageDialog(null, "El campo de q0, debe ser llenado con un simbolo introducido en Q\n"
                                    + "                Por favor vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case 3:
                        funEntrada = "(";
                        jt3 = jt3 + ",";
                        primero = true;
                        empezar = true;
                        auxCont = 0;
                        aux = "";
                        for (int i = 0; i < jt3.length(); i++) {
                            if (empezar == true && (fraseCompleta("(,| )", Character.toString(jt3.charAt(i))).matches())) {
                            } else {
                                empezar = false;
                                if (fraseCompleta(",", Character.toString(jt3.charAt(i))).matches()) {
                                    if (fraseCompleta(funEntrada + ")", aux).matches()) {
                                        JOptionPane.showMessageDialog(null, "Algun simbolo se repitio dos o mas veces en Σ\n"
                                                + "         Por favor quitarlo y volver a probar", "Error", JOptionPane.ERROR_MESSAGE);
                                        i = jt3.length();
                                        auxCont = i;
                                    } else {
                                        if (fraseCompleta(funFinalEstadosMT, aux).matches()) {
                                            JOptionPane.showMessageDialog(null, "Introdujo un simbolo que es un estado de Q, y no esta permitido\n"
                                                    + "                     Por favor vuelvalo a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                                            i = jt3.length();
                                            auxCont = i;
                                        } else {
                                            if (primero == true) {
                                                primero = false;
                                                funEntrada = funEntrada + aux;
                                            } else {
                                                funEntrada = funEntrada + "|" + aux;
                                            }
                                            aux = "";
                                            empezar = true;
                                        }
                                    }
                                } else {
                                    if (fraseCompleta(" ", Character.toString(jt3.charAt(i))).matches()) {
                                    } else {
                                        aux = aux + Character.toString(jt3.charAt(i));
                                    }
                                }
                            }
                            auxCont++;
                        }

                        if (auxCont == jt3.length()) {
                            salir = false;
                            cont++;
                            funFinalCintaMT = funEntrada;
                            funEntrada = funEntrada + ")";
                            funFinalEntradaMT = funEntrada;
                        }
                        break;
                    case 4:
                        if (fraseCompleta(funFinalEstadosMT, jt4).matches()) {
                            JOptionPane.showMessageDialog(null, "El campo de B, debe ser diferente a algun estado de Q\n"
                                    + "          Por favor vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (fraseCompleta(funFinalEntradaMT, jt4).matches()) {
                                JOptionPane.showMessageDialog(null, "El campo de B, debe ser diferente a algun simbolo del alfabeto de entrada(Σ)\n"
                                        + "                    Por favor vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                salir = false;
                                cont++;
                                funFinalCintaMT = funFinalCintaMT + "|" + jt4 + ")";
                                funSimExtremosMT = jt4;
                            }
                        }
                        break;
                    case 5:
                        aux = funFinalCintaMT;
                        aux2 = "";
                        for (int i = 0; i < aux.length(); i++) {
                            if (fraseCompleta("\\(|\\)", Character.toString(aux.charAt(i))).matches()) {
                            } else {
                                aux2 = aux2 + Character.toString(aux.charAt(i));
                            }
                        }
                        aux = "";
                        for (int i = 0; i < aux2.length(); i++) {
                            if (fraseCompleta("\\|", Character.toString(aux2.charAt(i))).matches()) {
                                aux = aux + ",";
                            } else {
                                aux = aux + Character.toString(aux2.charAt(i));
                            }
                        }
                        cont++;
                        salir = false;
                        break;
                    case 6:
                        funAceptacion = "(";
                        jt6 = jt6 + ",";
                        primero = true;
                        empezar = true;
                        auxCont = 0;
                        aux2 = "";
                        for (int i = 0; i < jt6.length(); i++) {
                            if (empezar == true && (fraseCompleta("(,| )", Character.toString(jt6.charAt(i))).matches())) {
                            } else {
                                empezar = false;
                                if (fraseCompleta(",", Character.toString(jt6.charAt(i))).matches()) {
                                    if (fraseCompleta(funAceptacion + ")", aux2).matches()) {
                                        JOptionPane.showMessageDialog(null, "En el campo F, esta repitiendo el mismo parametro.\n"
                                                + "            Por favor vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                                        i = jt6.length();
                                        auxCont = i;
                                    } else {
                                        if (fraseCompleta(funFinalEstadosMT, aux2).matches()) {
                                            if (primero == true) {
                                                primero = false;
                                                funAceptacion = funAceptacion + aux2;
                                            } else {
                                                funAceptacion = funAceptacion + "|" + aux2;
                                            }
                                            aux2 = "";
                                            empezar = true;
                                        } else {
                                            JOptionPane.showMessageDialog(null, "    Un simbolo introducido en F, no corresponde a un estado.\n"
                                                    + "Y debe ser un estado para ser final, por favor volver a intentar", "Error", JOptionPane.ERROR_MESSAGE);
                                            i = jt6.length();
                                            auxCont = i;
                                        }
                                    }
                                } else {
                                    if (fraseCompleta(" ", Character.toString(jt6.charAt(i))).matches()) {
                                    } else {
                                        aux2 = aux2 + Character.toString(jt6.charAt(i));
                                    }
                                }
                            }
                            auxCont++;
                        }

                        if (auxCont == jt6.length()) {
                            valido = true;
                            funAceptacionMT = funAceptacion;
                            turing.cambioCinta(aux);
                        }
                        break;
                }
            }
        } while (salir != true);

        return valido;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Transicion turing"> 
    public void transicionTuring(String jt1, String jt2, String jt3, String jt4, String jt5) {
        boolean salir = true;
        int cont = 1;
        String aux = "";

        do {
            salir = true;
            if (jt1.length() == 0 || jt2.length() == 0 || jt3.length() == 0 || jt4.length() == 0) {
                JOptionPane.showMessageDialog(null, "Para continuar es recomendable llenar primero todos los\n"
                        + "  campos vacios. Por favor llenarlos y volver a intentar\n", "Recomendación", JOptionPane.QUESTION_MESSAGE);
            } else {
                switch (cont) {
                    case 1:
                        if (fraseCompleta(funFinalEstadosMT, jt1).matches()) {
                            cont++;
                            salir = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "El primer campo no corresponde a un estado(Q)\n"
                                    + "        Por favor vuelvalo a intentar", "Recomendación", JOptionPane.QUESTION_MESSAGE);
                        }
                        break;
                    case 2:
                        if (fraseCompleta(funFinalEntradaMT, jt2).matches()) {
                            cont++;
                            salir = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "El segundo campo no corresponde simbolo del alfabeto de cinta(T)\n"
                                    + "                     Por favor vuelvalo a intentar", "Recomendación", JOptionPane.QUESTION_MESSAGE);
                        }
                        break;
                    case 3:
                        if (fraseCompleta(funFinalEstadosMT, jt3).matches()) {
                            cont++;
                            salir = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "El tercer campo no corresponde a un estado(Q)\n"
                                    + "        Por favor vuelvalo a intentar", "Recomendación", JOptionPane.QUESTION_MESSAGE);
                        }
                        break;
                    case 4:
                        if (fraseCompleta(funFinalEntradaMT, jt4).matches()) {
                            aux = "δ ( " + jt1 + " , " + jt2 + " ) = ( " + jt3 + " , " + jt4 + " , " + jt5 + " )";
                            arrayTuring.add(aux);
                            turing.agregarALaTabla(aux);
                        } else {
                            JOptionPane.showMessageDialog(null, "El cuarto campo no corresponde simbolo del alfabeto de cinta(T)\n"
                                    + "                     Por favor vuelvalo a intentar", "Recomendación", JOptionPane.QUESTION_MESSAGE);
                        }
                        break;
                }
            }
        } while (salir == false);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Comprobacion cadena M. turing"> 
    public void comprobarCadenaMT(String jt1, String jt2, String jt3, String jt4, String jt6, String jt7) {
        int cont = 0;
        if (turing.getNumFilas() == 0) {
            JOptionPane.showMessageDialog(null, "Debe agregar mínimo una transición, para poder validar la cadena\n"
                    + "                                           Vuelva a intentarlo", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (comprobacionCamposTuring(jt1, jt2, jt3, jt4, jt6) == true) {
                if (reafirmacionArray() == true) {
                    for (int i = 0; i < jt7.length(); i++) {
                        if (fraseCompleta(funFinalCintaMT, Character.toString(jt7.charAt(i))).matches()) {
                            cont++;
                        }
                    }
                    if (cont == jt7.length()) {
                        ProcesoTuring proceso = new ProcesoTuring(funFinalEstadoInicialMT, funSimExtremosMT, funAceptacionMT, arrayTuring);
                        procTuring = proceso;
                    }
                }
            }
        }
    }

    public boolean reafirmacionArray() {
        boolean valido = false;
        String aux = "";
        int cont = 0;
        for (int i = 0; i < arrayTuring.size(); i++) {
            for (int j = 0; j < arrayTuring.get(i).length(); j++) {
                if (j == 0 || j == arrayTuring.get(i).length() - 1) {
                } else {
                    aux = aux = Character.toString(arrayTuring.get(i).charAt(j));
                }
            }
            String[] aux2 = aux.split(" ");

            if (fraseCompleta(funFinalEstadosMT, aux2[2]).matches()) {
                if (fraseCompleta(funFinalCintaMT, aux2[4]).matches()) {
                    if (fraseCompleta(funFinalEstadosMT, aux2[7]).matches()) {
                        if (fraseCompleta(funFinalCintaMT, aux2[9]).matches()) {
                            cont++;
                        }
                    }
                }
            }
        }

        if (cont == arrayEntrada.size()) {
            valido = true;
        }
        return valido;
    }
    // </editor-fold>

    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Metodos de diferentes clases"> 
    public void salir() {
        System.exit(0);
    }

    public void reinicio(int opcion) {
        if (opcion == 1) {
            turing.setVisible(false);
        } else {
            mealy.setVisible(false);
        }
        menu.nuevaMaquina(opcion);
    }

    //Metodo de expresiones regulares
    public Matcher fraseCompleta(String buscar, String frase) {
        Pattern busqueda = Pattern.compile(buscar);
        Matcher palabra = busqueda.matcher(frase);
        return palabra;
    }

    public void llamarDatos(int opcion) throws IOException {
        if (opcion == 1) {

        } else {
            mealy.llamarDatosMealy();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Metodos de maquina de mealy"> 
    public void explicaciones(int opcion) {
        switch (opcion) {
            case 1:
                JOptionPane.showMessageDialog(null, "Descripción:\n"
                        + "     T: Función de transición de entrada.\n"
                        + "     Q: Conjunto finito de estados.\n"
                        + "     Σ : Conjunto de símbolos del alfabeto de entrada.\n\n"
                        + "Estructura:\n                      T : Q × Σ → Q\n\n"
                        + "Ejemplos:\n                      T ( 0 , a ) → 1\n"
                        + "                      T ( 1 , b ) → 2", ""
                        + "Estructura de transiciones de entrada(T)", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Descripción:\n"
                        + "     λ: Función de stransición de salida,.\n"
                        + "     Q: Conjunto finito de estados.\n"
                        + "     Σ: Conjunto de símbolos del alfabeto de entrada.\n"
                        + "     O: Conjunto de símbolos del alfabeto de salida.\n\n"
                        + "Estructura:\n                      λ : Q x Σ → O\n\n"
                        + "Ejemplos:\n                      λ ( 0 , a ) → b\n"
                        + "                      λ ( 1 , b ) → h", ""
                        + "Estructura de transiciones de salida(λ)", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Descripción:\n"
                        + "     δ: Función de transición.\n"
                        + "Estructura:\n     δ: Q x T  Q x T x {L,R}\n"
                        + "Ejemplos:\n     δ ( q0 , 0 ) = ( q1 , 1 , L)\n"
                        + "     δ ( q1 , 1 ) = ( q2 , 0 , R)", ""
                        + "Función de transición(δ)", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    public void vaciarCampos(JTextField jt1, JTextField jt2, JTextField jt3, JTextField jt4, JTextField jt5, JTextField jt6) {
        jt1.setText("");
        jt2.setText("");
        jt3.setText("");
        jt4.setText("");
        jt5.setText("");
        jt6.setText("");
    }

    public void eliminarTabla(JTable jTable1, DefaultTableModel model, JButton jb2, int opcion) {
        int fun = jTable1.getSelectedRow();
        model.removeRow(fun);
        arrayEntrada.remove(fun);
        arraySalida.remove(fun);
        controlBotonEliminar(jb2, opcion);
    }

    public void controlBotonEliminar(JButton jb2, int opcion) {
        if (opcion == 1) {
            if (mealy.getCanTransiciones() == 0) {
                mealy.setCanTransiciones(mealy.getCanTransiciones() + 1);
                jb2.setEnabled(true);
            } else {
                mealy.setCanTransiciones(mealy.getCanTransiciones() + 1);
            }
        } else {
            if (mealy.getCanTransiciones() != 0) {
                if (mealy.getCanTransiciones() - 1 == 0) {
                    mealy.setCanTransiciones(mealy.getCanTransiciones() - 1);
                    jb2.setEnabled(false);
                } else {
                    mealy.setCanTransiciones(mealy.getCanTransiciones() - 1);
                }
            }
        }
    }

    public void pasoDeDatosPEscritorio(ArrayList campo1, ArrayList campo2, ArrayList campo3, ArrayList campo4, ArrayList campo5) {
        mealy.creacionPruebaEscritorio(campo1, campo2, campo3, campo4, campo5);
    }

    public void agregarALosArray(String entrada, String salida) {
        arrayEntrada.add(entrada);
        arraySalida.add(salida);
    }

    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Metodos de maquina de turing">
    public void eliminarTransicionMT(JTable jTable1) {
        int fun = jTable1.getSelectedRow();
        turing.setNumFilas(turing.getNumFilas() + 1);
        arrayTuring.remove(fun);
        turing.elimicarFilaTabla(fun);
    }
    // </editor-fold> 
    // </editor-fold> 
}

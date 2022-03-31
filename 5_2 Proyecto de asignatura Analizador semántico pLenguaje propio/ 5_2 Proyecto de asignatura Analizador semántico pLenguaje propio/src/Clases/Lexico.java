/*
******NOMBRE DEL ARCHIVO*******
  Lexico_exprecionAritimetica

************MATERIA************
    Lenguajes y Autómatas II

******NOMBRE DEL PROFESOR******
    Hernandez Perez Roberto 

****INTEGRANTES DEL EQUIPO*****
 Hernandez Gala Brandon Javier 
 Mellado Mendoza Wendolen Citlali
 Meneses Rivera Frida Sarahi

*************ACCION************
Esta clase funciona como un analizador lexico
para una exprecion aritmetica simple,
funciona con la version 3.

********FECHA DE ENTREGA*******
    22 de Noviembre del 2021
*/

package Clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Lexico {
    //Variables utilizadas
    private static int numLineas;
    private static FileReader fr;
    private static BufferedReader bf;
    public static int numLexemas ;
    private static String[] nombreTokens;
    private static int[][] tablaSimbolos;
    public static String[] listaToken;
    public static String[] listaLexema;
    public static int[] listaLinea;
    public static int[] listaID;
    private static int contadorLista;
    
    //Constrisctor de la clase
    public Lexico(){
        numLineas = 0;
        numLexemas = 0;
        contadorLista = 0;
        obtenerLexemasYLineas();
        tablaSimbolos = new int[22][numLineas];
        for(int i = 0; i<22; i++){
            for(int j = 0; j<numLineas; j++){
                tablaSimbolos[i][j] = 0;
            }
        }
        listaLexema = new String[numLexemas];
        listaToken = new String[numLexemas];
        listaLinea = new int[numLexemas];
        listaID = new int[numLexemas];
        nombreTokens = new String[22];
        nombreTokens[0] = "      OPERADOR       ";
        nombreTokens[1] = "   ABRE PARENTESIS   ";
        nombreTokens[2] = "  CIERRA PARENTESIS  ";
        nombreTokens[3] = "     APLICACION      ";
        nombreTokens[4] = "        FIN          ";
        nombreTokens[5] = "   CIERRA CORCHETE   ";
        nombreTokens[6] = "    ABRE CORCHETE    ";
        nombreTokens[7] = "        GATO         ";
        nombreTokens[8] = "        IGUAL        ";
        nombreTokens[9] = "    DOBLE COMILLAS   ";
        nombreTokens[10] = "   COMILLAS SIMPLES  ";
        nombreTokens[11] = "         SI          ";
        nombreTokens[12] = "        SINO         ";
        nombreTokens[13] = "       MIENTRAS      ";
        nombreTokens[14] = "      TIPO DATO      ";
        nombreTokens[15] = "      IMPRIMIR       ";
        nombreTokens[16] = "       NUMERO        ";
        nombreTokens[17] = "      BOOLEANO       ";
        nombreTokens[18] = "       CADENA        ";
        nombreTokens[19] = "      MAYOR QUE      ";
        nombreTokens[20] = "      MENOR QUE      ";
        nombreTokens[21] = "     POR DEFECTO     ";
        obtenerTokens();
    }
    
    //Método que nos da el numero de lexemas y lineas de nuestro archivo.
    public static void obtenerLexemasYLineas(){
        String cadena = "";
        String linea = "";
        try{
            fr = new FileReader("C:\\ficheros\\codigo.txt");
            bf = new BufferedReader(fr);             
            while ((linea = bf.readLine())!=null) {   
                numLineas++;
                for(int i=0; i<linea.length(); i++){
                    if(linea.charAt(i)==32 || linea.charAt(i)==9){
                        if(cadena!=""){
                            numLexemas++;
                            cadena="";
                        }
                    }
                    else{
                        if(linea.charAt(i) == 47 && linea.charAt(i+1) == 47){
                            break;
                        }
                        if(linea.charAt(i) == 123 || linea.charAt(i) == 125 || linea.charAt(i) == 40 || 
                           linea.charAt(i) == 41 || linea.charAt(i) == 43 || linea.charAt(i) == 42 || 
                           linea.charAt(i) == 45 || linea.charAt(i) == 61 || linea.charAt(i) == 35 ||
                           linea.charAt(i) == 34 || linea.charAt(i) == 39 || linea.charAt(i) == 62 || linea.charAt(i) == 60){
                            if(cadena!=""){
                                numLexemas++;
                                cadena="";
                                cadena=cadena+linea.charAt(i);
                                numLexemas++;
                                cadena="";
                            }
                            else{
                                cadena=cadena+linea.charAt(i);
                                numLexemas++;
                                cadena="";
                            }
                        }
                        else{
                            cadena=cadena+linea.charAt(i);
                        }
                    }
                }
                if(cadena!=""){
                    numLexemas++;
                    cadena="";
                }
            } 
        }
        catch(IOException e){
            System.out.println("El archivo no fue encontrado");
        }
    }
    
    //Método que lee todos los lexemas de nuestro archivo.
    public static void obtenerTokens(){
        String linea = "";
        String cadena = "";
        int apuntadorLinea = 0;
        try{
            fr = new FileReader("C:\\ficheros\\codigo.txt");
            bf = new BufferedReader(fr); 
            while ((linea = bf.readLine())!=null) {   
                apuntadorLinea++;
                for(int i=0; i<linea.length(); i++){
                    if(linea.charAt(i)==32 || linea.charAt(i)==9){
                        if(cadena!=""){
                            checarTipo(cadena, apuntadorLinea);
                            cadena="";
                        }
                    }
                    else{
                        if(linea.charAt(i) == 47 && linea.charAt(i+1) == 47){
                            break;
                        }
                        if(linea.charAt(i) == 123 || linea.charAt(i) == 125 || linea.charAt(i) == 40 || 
                           linea.charAt(i) == 41 || linea.charAt(i) == 43 || linea.charAt(i) == 42 || 
                           linea.charAt(i) == 45 || linea.charAt(i) == 61 || linea.charAt(i) == 35 ||
                           linea.charAt(i) == 34 || linea.charAt(i) == 39 || linea.charAt(i) == 62 || linea.charAt(i) == 60){
                            if(cadena!=""){
                                checarTipo(cadena, apuntadorLinea);
                                cadena="";
                                cadena=cadena+linea.charAt(i);
                                checarTipo(cadena, apuntadorLinea);
                                cadena="";
                            }
                            else{
                                cadena=cadena+linea.charAt(i);
                                checarTipo(cadena, apuntadorLinea);
                                cadena="";
                            }
                        }
                        else{
                            cadena=cadena+linea.charAt(i);
                        }
                    }
                }
                if(cadena!=""){
                    checarTipo(cadena, apuntadorLinea);
                    cadena="";
                }
            } 
        }
        catch(IOException e){
            System.out.println("El archivo no fue encontrado");
        }
    }
    
    //Método que se encarga de clasificar los lexemas en tokens.
    private static void checarTipo(String Lexema, int posicion){
        int id = 21;
        if(!checkTokenOperador(Lexema)){
            if(!Lexema.equals("(")){
                if(!Lexema.equals(")")){
                    if(!Lexema.equals("Aplicacion")){
                        if(!Lexema.equals("Fin")){
                            if(!Lexema.equals("}")){
                                if(!Lexema.equals("{")){
                                    if(!Lexema.equals("#")){
                                        if(!Lexema.equals("=")){
                                            if(!Lexema.equals("\"")){
                                                if(!Lexema.equals("'")){
                                                    if(!Lexema.equals("si")){
                                                        if(!Lexema.equals("sino")){
                                                            if(!Lexema.equals("mientras")){
                                                                if(!checkTokenTipoDato(Lexema)){
                                                                    if(!Lexema.equals("imprimir")){
                                                                        if(!checkTokenNumero(Lexema)){
                                                                            if(!checkTokenBooleano(Lexema)){
                                                                                if(!checkTokenCadena(Lexema)){
                                                                                    if(!Lexema.equals(">")){
                                                                                        if(!Lexema.equals("<")){
                                                                                            id = 21;
                                                                                        }
                                                                                        else{
                                                                                            id = 20;
                                                                                        }
                                                                                    }
                                                                                    else{
                                                                                        id = 19;
                                                                                    }
                                                                                }
                                                                                else{
                                                                                    id = 18;
                                                                                }
                                                                            }
                                                                            else{
                                                                                id = 17;
                                                                            }
                                                                        }
                                                                        else{
                                                                            id = 16;
                                                                        }
                                                                    }
                                                                    else{
                                                                        id = 15;
                                                                    }
                                                                }
                                                                else{
                                                                    id = 14;
                                                                }
                                                            }
                                                            else{
                                                                id = 13;
                                                            }
                                                        }
                                                        else{
                                                            id = 12;
                                                        }
                                                    }
                                                    else{
                                                        id = 11;
                                                    }
                                                }
                                                else{
                                                    id = 10;
                                                }
                                            }
                                            else{
                                                id = 9;
                                            }
                                        }
                                        else{
                                            id = 8;
                                        }
                                    }
                                    else{
                                        id = 7;
                                    }
                                }
                                else{
                                    id = 6;
                                }
                            }
                            else{
                                id = 5;
                            }
                        }
                        else{
                            id = 4;
                        }
                    }
                    else{
                        id = 3;
                    }
                }
                else{
                    id = 2;
                }
            }
            else{
                id = 1;
            }
        } 
        else{
            id = 0;
        }
        guardarToken(Lexema, posicion, nombreTokens[id], id);
        tablaSimbolos[id][posicion-1] = 1;
    }
    
    //Metodo que guarda los datos de los tokens en una tabla.
    private static void guardarToken(String lexema, int linea, String token, int id){
        listaLinea[contadorLista] = linea;
        listaLexema[contadorLista] = lexema;
        listaToken[contadorLista] = token;
        listaID[contadorLista] = id;
        contadorLista++;
    }
    
    //Métodos que compruebas que un lexeman pertenece a cierto token.
    public static boolean checkTokenOperador(String lexema){
        boolean regreso = false;
        if("+".equals(lexema) || "*".equals(lexema) || "-".equals(lexema)){
            regreso = true;
        }
        return regreso;
    }
    
    public static boolean checkTokenTipoDato(String lexema){
        boolean regreso = false;
        if(lexema.equals("entero") || lexema.equals("cadena") || lexema.equals("caracter") || lexema.equals("boolean")){
            regreso = true;
        }
        return regreso;
    }
    
    public static boolean checkTokenBooleano(String lexema){
        boolean regreso = false;
        if(lexema.equals("verdadero") || lexema.equals("falso")){
            regreso = true;
        }
        return regreso;
    }
    
    public static boolean checkTokenNumero(String lexema){
        boolean regreso = false;
        for(int i=0; i<lexema.length(); i++){
            if(lexema.charAt(i)>=48 && lexema.charAt(i)<=57){
                regreso = true;
            }
            else{
                regreso = false;
                break;
            }
        }
        return regreso;
    }
    
    public static boolean checkTokenCadena(String lexema){
        boolean regreso = false;
        for(int i=0; i<lexema.length(); i++){
            if((lexema.charAt(i)>=97 && lexema.charAt(i)<=122) || (lexema.charAt(i)>=65 && lexema.charAt(i)<=90)){
                regreso = true;
            }
            else{
                regreso = false;
                break;
            }
        }
        return regreso;
    }
    
    public static boolean checkTokenComillaSimple(String lexema){
        boolean regreso = false;
        if(lexema.charAt(0) == 39){
            regreso = true;
        }
        return regreso;
    }
    
    public static boolean checkTokenComillasDobles(String lexema){
        boolean regreso = false;
        /*if(lexema.charAt(0) == 34){
            regreso = true;
        }*/
        char a = lexema.charAt(0);
        int b = a;
        System.out.println(lexema.charAt(0)+" : "+b);
        return regreso;
    }
    
     
    //Método que imprime nuestra tabla de simbolos.
    public static void imprimirTablaSimbolos(){
        int contador;
        System.out.println("**********Tabla de símbolos***********");
        for(int i = 0; i < nombreTokens.length; i++){
            System.out.print(nombreTokens[i]+"|  ");
            contador = 0;
            for(int j = 0; j<numLineas; j++){
                if(tablaSimbolos[i][j] == 1){
                    if(contador == 0){
                        System.out.print((j+1));
                        contador++;
                    }
                    else{
                        System.out.print(", "+(j+1));
                        contador++;
                    }
                }
            };
            System.out.println(".");
        }
    }
    
    //Metodo que imprime nuestros lexemans organizados por tokens.
    public static void imprimirLexemas(){
        System.out.println("****************Lista de lexemas****************");
        for(int i = 0; i < numLexemas; i++){
            if(listaID[i] > 9){
                System.out.println("Linea: "+listaLinea[i]+" |"+listaToken[i]+"| "+listaID[i]+" | "+listaLexema[i]);
            }
            else{
                System.out.println("Linea: "+listaLinea[i]+" |"+listaToken[i]+"| 0"+listaID[i]+" | "+listaLexema[i]);
            }
        }
    }
}
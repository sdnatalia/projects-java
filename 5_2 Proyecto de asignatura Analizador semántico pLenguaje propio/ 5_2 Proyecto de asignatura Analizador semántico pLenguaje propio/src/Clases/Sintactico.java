/*
******NOMBRE DEL ARCHIVO*******
         Sintactico

************MATERIA************
    Lenguajes y Autómatas II

******NOMBRE DEL PROFESOR******
    Hernandez Perez Roberto 

****INTEGRANTES DEL EQUIPO*****
 Hernandez Gala Brandon Javier 
 Mellado Mendoza Wendolen Citlali
 Meneses Rivera Frida Sarahi

*************ACCION************
Esta clase funciona como un analizador sintáctico
de la gramatica de nuestro lenguaje, contiene métodos
para cada una de las produccines de la gramatica.

********FECHA DE ENTREGA*******
     22 de Noviembre del 2021
*/

package Clases;
public class Sintactico {
    //Variables utilizadas
    private static int tokenID;
    private static int tokenLinea;
    
    private static int NexttokenID;
    
    private static int apuntadorToken;
    private static String mensajeError;
    public static Lexico lex;
    private static boolean ControlBloques = false;
    private static boolean ControleExpresion = false;
    private static int ControleParentesis = 0;
    private static boolean exprecion = false;
   
    private static int ExpApuntador = 0;
    
    public static boolean finSintaxis = false;
    
    //En el constructor iniciamos primero el analizador léxico
    public Sintactico() {
        apuntadorToken = 0;
        mensajeError = "";
        lex = new Lexico();
        nextToken();
    }
    
    //Este metodo hace uso del lexico para obtener los tokens
    private static void nextToken(){
        tokenID = lex.listaID[apuntadorToken];
        tokenLinea = lex.listaLinea[apuntadorToken];
        NextNextToken();        
        apuntadorToken++;

    }
    
    private static void NextNextToken(){
        if(apuntadorToken+1<lex.listaID.length){
            NexttokenID = lex.listaID[apuntadorToken+1];
        }
    }
    
    //Este método funciona como la primer produccion de nuestro lenguaje, y asi respectivamente.
    public static void S(){
        if(tokenID == 3){
            nextToken();
            if(BLOQUE()){
                if(tokenID == 4){
                    System.out.println("Sintaxis correcta");
                    finSintaxis = true;
                }
                else{
                    mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: Fin";
                    System.out.println(mensajeError);
                }
            }
            else{
                System.out.println(mensajeError);
            }
        }
        else{
            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba el token: Aplicacion";
            System.out.println(mensajeError);
        }
    }
    
    private static boolean BLOQUE(){
        boolean regreso = false;
        if(OPERACION()){
            if(BLOQUE()){
                regreso = true;
            }
        }
        else{
            if(ControlBloques && tokenID == 4){
                regreso = true;
                
            }
            ControlBloques = false;
        }
        return regreso;
    }
    
    private static boolean OPERACION(){
        boolean regreso = false;
        switch (tokenID){
            case 14:
                if(DECLARACION()){
                    regreso = true;
                }
                break;
            case 18:
                if(ASIGNACION()){
                    regreso = true;
                }
                break;
            case 11:
                if(IF()){
                    if(tokenID == 12){
                        if(ELSE()){
                            regreso = true;
                        }
                    }
                    else{
                        regreso = true;
                    }
                }
                break;
            case 13:
                if(WHILE()){
                    regreso = true;
                }
                break;
            case 15:
                if(PRINT()){
                    regreso = true;
                }
                break;
            default: 
                if(tokenID == 4){                  
                    ControlBloques = true;
                }
                else{
                    mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba empezar un bloque";
                }
                break;
        }
        return regreso;
    }
    
    private static boolean PRINT(){
        boolean regreso = false;
        if(tokenID == 15){
            nextToken();
            if(tokenID == 1){
                nextToken();
                if(tokenID == 18){
                    nextToken();
                    if(tokenID == 2){
                        nextToken();
                        if(tokenID == 7){
                            nextToken();
                            regreso = true;
                        }
                        else{
                            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: #";
                        }
                    }
                    else{
                        mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: )";
                    }
                }
                else{
                    mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba una cadena";
                }
            }
            else{
                mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: (";
            }
        }
        else{
            if(tokenID == 4){
                ControlBloques = true;
            }
            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: imprimir";
        }
        return regreso;
    }
    
    private static boolean WHILE(){
        boolean regreso = false;
        boolean controlWhile = true;
        if(tokenID == 13){
            nextToken();
            if(tokenID == 1){   
                nextToken();
                if(CONDICION()){
                    if(tokenID == 2){
                        nextToken();
                        if(tokenID == 6){
                            nextToken();
                            while(tokenID != 5){
                                if(!OPERACION()){
                                    
                                    controlWhile = false;
                                    break;
                                } 
                            } 
                            if(controlWhile){
                                
                                if(tokenID == 5){
                                    nextToken();
                                    regreso = true;
                                }
                                else{
                                    mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: }";
                                }
                            }
                        
                        }
                        else{
                            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: {";
                        }
                    }
                    else{
                        mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: )";
                    }
                }
            }
            else{
                mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: (";
            }
        }
        else{
            if(tokenID == 4){
                ControlBloques = true;
            }
            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: minetras";
        }
        return regreso;
    }
    
    private static boolean IF(){
        boolean regreso = false;
        boolean controlIF = true;
        if(tokenID == 11){
            nextToken();
            if(tokenID == 1){
                nextToken();
                if(CONDICION()){
                    if(tokenID == 2){
                        nextToken();
                        if(tokenID == 6){
                            nextToken();
                            while(tokenID != 5){
                                if(!OPERACION()){
                                    
                                    controlIF = false;
                                    break;
                                } 
                            } 
                            if(controlIF){
                                if(tokenID == 5){
                                    nextToken();
                                    regreso = true;
                                }
                                else{
                                    mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: }";
                                }
                            }
                        
                        }
                        else{
                            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: {";
                        }
                    }
                    else{
                        mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: )";
                    }
                }
            }
            else{
                mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: (";
            }
        }
        else{
            if(tokenID == 4){
                ControlBloques = true;
            }
            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: si";
        }
        return regreso;
    }
    
    private static boolean ELSE(){
        boolean regreso = false;
        boolean controlIF = true;
        if(tokenID == 12){
            nextToken();
            if(tokenID == 6){
                nextToken();
                while(tokenID != 5){
                    if(!OPERACION()){

                        controlIF = false;
                        break;
                    } 
                } 
                if(controlIF){
                    if(tokenID == 5){
                        nextToken();
                        regreso = true;
                    }
                    else{
                        mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: }";
                    }
                }

            }
            else{
                mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: {";
            }
        }
        else{
            if(tokenID == 4){
                ControlBloques = true;
            }
            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: si";
        }
        return regreso;
    }
    
    private static boolean CONDICION(){
        boolean regreso = false;
        if(tokenID == 18){
            nextToken();
            if(COMPARADOR()){
                if(tokenID == 18 || tokenID == 17 || tokenID == 16){
                    nextToken();
                    regreso = true;
                }
                else{
                    mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba un valor";
                }
            }
        }
        else{
            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba una cadena";
        }
        return regreso;
    }
    
    private static boolean COMPARADOR(){
        boolean regreso = false;
        if(tokenID == 8 || tokenID == 19 || tokenID == 20){
            nextToken();
            if(tokenID == 8){
                nextToken();
                regreso = true;
            }
            else{
                mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: =";
            }
        }
        else{
            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: < o > o =";
        }
        return regreso;
    }
    
    private static boolean DECLARACION(){
        boolean regreso = false;
        if(TIPO()){
            if(tokenID == 18){
                nextToken();
                if(tokenID == 7){
                    nextToken();
                    regreso = true;
                }
                else{
                    mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: #";
                }
            }
            else{
                mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba una cadena";
            }
        }
        return regreso;
    }
    
    private static boolean ASIGNACION(){
        boolean regreso = false;
        if(tokenID == 18){
            nextToken();
            if(tokenID == 8){
                nextToken();
                if(VAL_ASIG()){ 
                    if(tokenID == 7){
                        nextToken();
                        regreso = true;
                    }
                    else{
                        mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: #";
                    }
                }
            }
            else{
                mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: =";
            }
        }
        else{
            if(tokenID == 4){
                ControlBloques = true;
            }
            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba una cadena";
        }
        return regreso;
    }
    
    private static boolean VAL_ASIG(){
        boolean regreso = false;
        if(tokenID == 17){
            nextToken();
            regreso = true;
        }
        else{
            if(tokenID == 9){
                nextToken();
                if(tokenID == 18){
                    nextToken();
                    if(tokenID == 9){
                        nextToken();
                        regreso = true;
                    }
                    else{
                        mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: \"";
                    }
                }
                else{
                    mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba una cadena";
                }
            }
            else{
                if(tokenID == 10){
                    nextToken();
                    if(tokenID == 18){
                        nextToken();
                        if(tokenID == 10){
                            nextToken();
                            regreso = true;
                        }
                        else{
                            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba: '";
                        }
                    }
                    else{
                        mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba una cadena";
                    }
                }
                else{
                    if(tokenID == 1 || tokenID == 16 || tokenID == 18){
                        E();
                        if(exprecion == true){
                            nextToken();
                            regreso = true;
                        }
                        else{
                            
                            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba una exprecion aritmetica";
                        }
                        ExpApuntador = 0;
                        ControleExpresion = false;
                        ControleParentesis =0;
                    }
                    else{
                        mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba un valor de asignación";
                    }
                    
                }
            }
        }
        return regreso;
    }
       
    private static void E(){       
        ExpApuntador++;
        if(NexttokenID == 0 || NexttokenID == 1 || NexttokenID == 2 || NexttokenID == 16 || NexttokenID == 18){
            if(ExpApuntador == 1){
                if((tokenID == 16 || tokenID == 18)){//(A+B)*A+(A+B
                    ControleExpresion = true;
                    nextToken();
                    E();
                }
                else{
                    if(tokenID == 1){
                        ControleParentesis++;
                        nextToken();
                        E();
                    }
                    else{
                        if(tokenID == 0 || tokenID == 2){
                            exprecion = false;
                        }
                        else{
                            exprecion = false;
                        }
                    }
                }
            }
            else{
                if((tokenID == 16 || tokenID == 18) && !ControleExpresion){
                    ControleExpresion = true;
                    nextToken();
                    E();
                }
                else{
                    if(tokenID == 0 && ControleExpresion){
                        ControleExpresion = false;
                        nextToken();
                        E();
                    }
                    else{
                        if(tokenID == 1 && !ControleExpresion){
                            ControleExpresion = false;
                            ControleParentesis++;
                            nextToken();
                            E();
                        }
                        else{
                            if(tokenID == 2){
                                ControleParentesis--;
                                nextToken();
                                E();
                            }
                            else{
                                exprecion = false;
                            }
                        }
                    }
                }
            }
        }
        else{
            if(tokenID == 2 && ControleParentesis == 1 && ControleExpresion){
                exprecion = true;
            }
            else{
                if((tokenID == 16 || tokenID == 18) && ControleParentesis == 0 && !ControleExpresion){
                    exprecion = true;
                    
                }
                else{
                    exprecion = false;
                }
            }
        }
        
        
    }
    
    private static boolean TIPO(){
        boolean regreso = false;
        if(tokenID == 14){
            nextToken();
            regreso = true;
        }
        else{
            if(tokenID == 4){
                ControlBloques = true;
            }
            mensajeError = "ERROR DE SINTAXIS EN LA LINEA: "+tokenLinea+" | Se esperaba un tipo de dato";
        }
        return regreso;
    }
    
}

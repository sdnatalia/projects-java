
package Clases;


public class Semántico {
    private static String[] variableTipo;
    private static String[] variableNombre;
    private static String[] variableValor;
    
    private static String[] codigoEtiquetas;
    private static String[] codigoTexto;
    
    private static int numeroVariables;
    private static Lexico lex;
    
    private static int contadorSemantica;
    
    private static int error = -1;
    
    public Semántico(){
        numeroVariables = 0;
        contadorSemantica = 0;
        lex = new Lexico();
        obtenerNumVariables();
        variableNombre = new String[numeroVariables];
        variableTipo = new String[numeroVariables];
        variableValor = new String[numeroVariables];
        checarSemantica();
    }
    
    private static void obtenerNumVariables(){
        for(int i = 0; i < lex.numLexemas; i++){
            if(lex.listaID[i] == 14){
                numeroVariables++;
            }
        }
    }
        
    private static void guardarVariables(int variables){
        variableTipo[variables] = lex.listaLexema[contadorSemantica];
        contadorSemantica++;
        variableNombre[variables] = lex.listaLexema[contadorSemantica];
        contadorSemantica++;
    }
    
    private static void checarSemantica(){
        boolean semantica = true;
        int variables = 0;
        while(contadorSemantica < lex.numLexemas){
            if(lex.listaID[contadorSemantica] == 14){
                guardarVariables(variables);
                variables++;
            }
            else{
                if(lex.listaID[contadorSemantica] == 18){
                    if(!checarVariableDefinida(lex.listaLexema[contadorSemantica])){
                        error = 0;
                        semantica = false;
                        break;
                    }
                    else{
                        if(!checarCompatibilidadDatos()){
                            semantica = false;
                            break;
                        }
                    }
                }
                else{
                    if(lex.listaID[contadorSemantica] == 11 || lex.listaID[contadorSemantica] == 13){
                        contadorSemantica++;
                        contadorSemantica++;
                        if(checarVariableDefinidaInt(lex.listaLexema[contadorSemantica])){
                            if(!checarCompatibilidadDatosIF()){
                                semantica = false;
                                break;
                            }
                        }
                        else{
                            if(checarVariableDefinida(lex.listaLexema[contadorSemantica])){
                                contadorSemantica++;
                                if(lex.listaID[contadorSemantica] == 19 || lex.listaID[contadorSemantica] == 20){
                                    semantica = false;
                                    break;
                                }
                                else{
                                    contadorSemantica--;
                                    if(!checarCompatibilidadDatosIF()){
                                        semantica = false;
                                        break;
                                    }
                                }
                            }
                            else{
                                error = 0;
                            }
                        }
                    }
                }
            }
            contadorSemantica++;
        }
        if(semantica){
            System.out.println("Semanticamente correcto");
            System.out.println("");
            ImprimirVariablesNoValor();
        }
        else{
            if(error == 0){
                System.out.println("Error semántico: Variable no definida");
            }
            else{
                System.out.println("Error semántico: Incopatibilidad de datos");
            }
        }
    }
    
    private static boolean checarVariableDefinida(String variable){
        boolean regreso = false;
        for(int i = 0; i < numeroVariables; i++){
            if(variable.equals(variableNombre[i])){
                regreso = true;
                break;
            }
        }
        return regreso;
    }
    
    private static boolean checarVariableDefinidaInt(String variable){
        boolean regreso = false;
        for(int i = 0; i < numeroVariables; i++){
            if(variable.equals(variableNombre[i])){
                if("entero".equals(variableTipo[i])){
                    regreso = true;
                    break;
                }
            }
        }
        return regreso;
    }
    
    private static boolean checarVariableDefinidaString(String variable){
        boolean regreso = false;
        for(int i = 0; i < numeroVariables; i++){
            if(variable.equals(variableNombre[i])){
                if("cadena".equals(variableTipo[i])){
                    regreso = true;
                    break;
                }
            }
        }
        return regreso;
    }
    
    private static boolean checarVariableDefinidaBoolean(String variable){
        boolean regreso = false;
        for(int i = 0; i < numeroVariables; i++){
            if(variable.equals(variableNombre[i])){
                if("boolean".equals(variableTipo[i])){
                    regreso = true;
                    break;
                }
            }
        }
        return regreso;
    }
    
    private static boolean checarVariableDefinidaCaracter(String variable){
        boolean regreso = false;
        for(int i = 0; i < numeroVariables; i++){
            if(variable.equals(variableNombre[i])){
                if("caracter".equals(variableTipo[i])){
                    regreso = true;
                    break;
                }
            }
        }
        return regreso;
    }
     
    
    private static int checarIDVariable(String variable){
        int regreso = -1;
        for(int i = 0; i < numeroVariables; i++){
            if(variable.equals(variableNombre[i])){
                regreso = i;
                break;
            }
        }
        return regreso;
    }
    
    private static boolean checarCompatibilidadDatos(){
        boolean regreso = false;
        int idVal = checarIDVariable(lex.listaLexema[contadorSemantica]);
        contadorSemantica++;
        contadorSemantica++;
        while(lex.listaID[contadorSemantica] != 7){
            if("entero".equals(variableTipo[idVal]) && (lex.listaID[contadorSemantica] == 16 || lex.listaID[contadorSemantica] == 1 || lex.listaID[contadorSemantica] == 18)){
                regreso = true;
                while(lex.listaID[contadorSemantica] != 7){
                    if(lex.listaID[contadorSemantica] == 18){
                        if(!checarVariableDefinidaInt(lex.listaLexema[contadorSemantica])){
                            error = 0;
                            regreso = false;
                            contadorSemantica++;
                            break;
                        }
                    }
                    contadorSemantica++;
                }
            }
            else{
                if("cadena".equals(variableTipo[idVal]) && (lex.listaID[contadorSemantica] == 9 || lex.listaID[contadorSemantica] == 18)){
                    if(lex.listaID[contadorSemantica] == 18){
                        if(!checarVariableDefinidaString(lex.listaLexema[contadorSemantica])){
                            error = 0;
                            regreso = false;
                            contadorSemantica++;
                            break;
                        }
                        else{
                            contadorSemantica++;
                            regreso = true;
                        }
                    }
                    else{
                        contadorSemantica++;
                        contadorSemantica++;
                        contadorSemantica++;
                        regreso = true;
                    } 
                }
                else{
                    if("boolean".equals(variableTipo[idVal]) && (lex.listaID[contadorSemantica] == 17 || lex.listaID[contadorSemantica] == 18)){
                        if(lex.listaID[contadorSemantica] == 18){
                            if(!checarVariableDefinidaBoolean(lex.listaLexema[contadorSemantica])){
                                error = 0;
                                regreso = false;
                                contadorSemantica++;
                                break;
                            }
                            else{
                                contadorSemantica++;
                                regreso = true;
                            }
                        }
                        else{
                            contadorSemantica++;
                            regreso = true;
                        } 
                    }
                    else{
                        if("caracter".equals(variableTipo[idVal]) && (lex.listaID[contadorSemantica] == 10 || lex.listaID[contadorSemantica] == 18)){
                            if(lex.listaID[contadorSemantica] == 18){
                                if(!checarVariableDefinidaCaracter(lex.listaLexema[contadorSemantica])){
                                    error = 0;
                                    regreso = false;
                                    contadorSemantica++;
                                    break;
                                }
                                else{
                                    contadorSemantica++;
                                    regreso = true;
                                }
                            }
                            else{
                                contadorSemantica++;
                                contadorSemantica++;
                                contadorSemantica++;
                                regreso = true;
                            } 
                        }
                        else{
                            error = 1;
                            break;
                        }
                    }
                }
            }
        }
        return regreso;
    }
    
    private static boolean checarCompatibilidadDatosIF(){
        boolean regreso = false;
        int idVal = checarIDVariable(lex.listaLexema[contadorSemantica]);
        contadorSemantica++;
        contadorSemantica++;
        contadorSemantica++;
        while(lex.listaID[contadorSemantica] != 6){
            if("entero".equals(variableTipo[idVal]) && (lex.listaID[contadorSemantica] == 16 || lex.listaID[contadorSemantica] == 1 || lex.listaID[contadorSemantica] == 18)){
                regreso = true;
                while(lex.listaID[contadorSemantica] != 6){
                    if(lex.listaID[contadorSemantica] == 18){
                        if(!checarVariableDefinidaInt(lex.listaLexema[contadorSemantica])){
                            error = 0;
                            regreso = false;
                            contadorSemantica++;
                            break;
                        }
                    }
                    contadorSemantica++;
                    contadorSemantica++;
                }
            }
            else{
                if("cadena".equals(variableTipo[idVal]) && (lex.listaID[contadorSemantica] == 9 || lex.listaID[contadorSemantica] == 18)){
                    if(lex.listaID[contadorSemantica] == 18){
                        if(!checarVariableDefinidaString(lex.listaLexema[contadorSemantica])){
                            error = 0;
                            regreso = false;
                            contadorSemantica++;
                            break;
                        }
                        else{
                            contadorSemantica++;
                            contadorSemantica++;
                            regreso = true;
                        }
                    }
                    else{
                        contadorSemantica++;
                        contadorSemantica++;
                        contadorSemantica++;
                        contadorSemantica++;
                        regreso = true;
                    } 
                }
                else{
                    if("boolean".equals(variableTipo[idVal]) && (lex.listaID[contadorSemantica] == 17 || lex.listaID[contadorSemantica] == 18)){
                        if(lex.listaID[contadorSemantica] == 18){
                            if(!checarVariableDefinidaBoolean(lex.listaLexema[contadorSemantica])){
                                error = 0;
                                regreso = false;
                                contadorSemantica++;
                                break;
                            }
                            else{
                                contadorSemantica++;
                                contadorSemantica++;
                                regreso = true;
                            }
                        }
                        else{
                            contadorSemantica++;
                            contadorSemantica++;
                            regreso = true;
                        } 
                    }
                    else{
                        if("caracter".equals(variableTipo[idVal]) && (lex.listaID[contadorSemantica] == 10 || lex.listaID[contadorSemantica] == 18)){
                            if(lex.listaID[contadorSemantica] == 18){
                                if(!checarVariableDefinidaCaracter(lex.listaLexema[contadorSemantica])){
                                    error = 0;
                                    regreso = false;
                                    contadorSemantica++;
                                    break;
                                }
                                else{
                                    contadorSemantica++;
                                    contadorSemantica++;
                                    regreso = true;
                                }
                            }
                            else{
                                contadorSemantica++;
                                contadorSemantica++;
                                contadorSemantica++;
                                contadorSemantica++;
                                regreso = true;
                            } 
                        }
                        else{
                            error = 1;
                            break;
                        }
                    }
                }
            }
        }
        return regreso;
    }
       
    
    public static void ImprimirVariables(){
        for(int i = 0; i < numeroVariables; i++){
            System.out.print(variableTipo[i] + " ; ");
            System.out.print(variableNombre[i] + " ; ");
            System.out.println(variableValor[i]);
        }
    }
    
    public static void ImprimirVariablesNoValor(){
        for(int i = 0; i < numeroVariables; i++){
            System.out.print(variableTipo[i] + " ; ");
            System.out.println(variableNombre[i]);
        }
    }
}

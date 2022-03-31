
package Clases;

public class main {
    public static void main(String[] args) {
        Sintactico sin = new Sintactico();
        System.out.println("*********Analizador semántico**********\n\n"
                + "Lenguaje propio: Team3Lengage\n"
                
                );
        /*sin.lex.imprimirLexemas();
        System.out.println("");
        sin.lex.imprimirTablaSimbolos();
        System.out.println(" ");*/
        sin.S();
        if(sin.finSintaxis){
            System.out.println(" ");
            Semántico sem = new Semántico();
        }
        
    }
}

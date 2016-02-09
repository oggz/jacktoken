import java.io.IOException;

public class CompilationEngine {

    JackTokenizer jt;
    public CompilationEngine(String in, String xml) {
        try {
            jt = new JackTokenizer(in);
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public void compileClass() {
        try {
            while(jt.hasMoreTokens()) {
                jt.advance();
                switch(jt.tokenType()) {
                case KEYWORD:
                    System.out.println("<keyword> " + jt.keyword() + " <keyword>");
                    break;
                case SYMBOL:
                    System.out.println("<symbol> " + jt.symbol() + " <symbol>");
                    break;
                case IDENTIFIER:
                    System.out.println("<identifier> " + jt.identifier() + " <identifier>");
                    break;
                case INT_CONST:
                    System.out.println("<integer_const> " + jt.intVal() + " <integer_const>");
                    break;
                case STRING_CONST:
                    System.out.println("<string_const> " + jt.stringVal() + " <string_const>");
                    break;
                default:
                    break;
                }
            }
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
        //write to output file
    }
}



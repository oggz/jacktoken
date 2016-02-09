import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JackTokenizer {

   enum TokenType {KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST;}
   enum Keyword {CLASS_METHOD,FUNCTION,CONSTRUCTOR,INT,BOOLEAN,CHAR,VOID,VAR,STATIC,FEILD,LET,DO,IF,ELSE,WHILE,RETURN,TRUE,FALSE,NULL,THIS;}

    PushbackReader pr;
    String state = "start";
    String current_token = "";
    ArrayList<String> tokens;

    public JackTokenizer(String infile) throws IOException {
        pr = new PushbackReader(new FileReader(infile));
    }

    public Boolean hasMoreTokens() throws IOException {
        if(pr.read() == -1)
            return false;
        return true;
    }

    public void advance() throws IOException {
        char curr;
        ArrayList<String> syms = new ArrayList<String>(Arrays.asList("/", ">", "<"));
        while(state != "finish" && state != "error") {
            curr = (char)pr.read();
            switch(state) {
            case "start":
                if(Character.isDigit(curr)) {
                    current_token += curr;
                    state = "in_int_const";
                }
                else if(curr == '"') {
                    current_token += curr;
                    state = "in_string_const";
                }
                else if(Character.isLetter(curr)) {
                    current_token += curr;
                    state = "in_identifier";
                }
                else if(syms.contains("curr")) {
                    tokens.add(current_token);
                    pr.unread(curr);
                    state = "in_symbol";
                }
                break;
            case "in_symbol":
                // label symbol and advance
                break;
            case "in_identifier":
                // gather and label identifier
                break;
            case "in_int_const":
                // gather and label int
                break;
            case "in_string_const":
                // gather and label string
                break;
            case default:
                // advance line or error
                break;
            }
        }
    }


    public TokenType tokenType() {
        // switch(){
        // case:
        //     break;
        // case:
        //     break;
        // default:
        //     break;
        // }
        return null;
    }

    public Keyword keyWord() {
        return null;
    }

    public char symbol() {
        return 'a';
    }

    public String identifier() {
        return null;
    }

    public int intVal() {
        return 42;
    }

    public String stringVal() {
        return null;
    }
}

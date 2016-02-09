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
        char current_char;
        ArrayList<String> syms = new ArrayList<String>(Arrays.asList("/", ">", "<", "..."));
        while(state != "finish" && state != "error") {
            current_char = (char)pr.read();
            switch(state) {
            case "start":
                if(Character.isDigit(current_char)) {
                    current_token += current_char;
                    state = "in_int_const";
                }
                else if(current_char == '"') {
                    current_token += current_char;
                    state = "in_string_const";
                }
                else if(Character.isLetter(current_char)) {
                    current_token += current_char;
                    state = "in_identifier";
                }
                else if(syms.contains(current_char)) {
                    tokens.add(current_token);
                    pr.unread(current_char);
                    state = "in_symbol";
                }
                break;
            case "in_symbol":
                if(syms.contains(current_char)) {
                    current_token += current_char;
                    state = "in_symbol";
                }
                else {
                    tokens.add(current_token);
                    state = "start";
                }
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
            default:
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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JackTokenizer {

    enum TokenType {KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST, COMMENT;}
    enum Keyword {CLASS_METHOD,FUNCTION,CONSTRUCTOR,INT,BOOLEAN,CHAR,VOID,VAR,STATIC,FIELD,LET,DO,IF,ELSE,WHILE,RETURN,TRUE,FALSE,NULL,THIS;}
    ArrayList<String> keywords = new ArrayList<String>(Arrays.asList("class_method","function","constructor","int","boolean","char","void",
                                                                        "var","static","field","let","do","if","else","while","return","true","false","null","this"));
    ArrayList<String> syms = new ArrayList<String>(Arrays.asList("{","}","(",")","[","]",".",",",";","+","-","*","/","&","|","<",">","=","~"));

    PushbackReader pr;
    String state = "start";
    String current_token = "";
    ArrayList<String> tokens;
    TokenType token_type;

    public JackTokenizer(String infile) throws IOException {
        pr = new PushbackReader(new FileReader(infile));
    }

    public Boolean hasMoreTokens() throws IOException {
        int c = pr.read();
        if(c == -1)
            return false;
        pr.unread((char)c);
        return true;
    }

    public void advance() throws IOException {
        char current_char = ' ';
        char prev_char = current_char;
        while(state != "finish" && state != "error") {
            prev_char = current_char;
            current_char = (char)pr.read();
            //            System.out.println(current_char);
            switch(state) {
            case "start":
                // whitespace
                if(Character.isWhitespace(current_char)) {
                    break;
                }
                // int
                else if(Character.isDigit(current_char)) {
                    current_token += current_char;
                    state = "in_int_const";
                }
                // string
                else if(current_char == '"') {
                    current_token += current_char;
                    state = "in_string_const";
                }
                // identifier
                else if(Character.isLetter(current_char)) {
                    current_token += current_char;
                    state = "in_identifier";
                }
                // symbol
                else if(syms.contains(Character.toString(current_char))) {
                    current_token += current_char;
                    state = "in_symbol";
                }
                break;
            case "in_int_const":
                if(Character.isDigit(current_char)) {
                    current_token += current_char;
                    state = "in_int_const";
                }
                else {
                    pr.unread(current_char);
                    token_type = TokenType.INT_CONST;
                    state = "finish";
                }
                break;
            case "in_string_const":
                if(current_char != '"') {
                    current_token += current_char;
                    state = "in_string_const";
                }
                else {
                    current_token += '"';
                    token_type = TokenType.STRING_CONST;
                    state = "finish";
                }
                break;
            case "in_identifier":
                if(Character.isDigit(current_char) || Character.isLetter(current_char) || current_char == '_' ) {
                    current_token += current_char;
                    state = "in_int_const";
                }
                else {
                    if(keywords.contains(current_token)) {
                        token_type = TokenType.KEYWORD;
                    }
                    else {
                        token_type = TokenType.IDENTIFIER;
                    }
                    //tokens.add(current_token);
                    pr.unread(current_char);
                    state = "finish";
                }
                break;
            case "in_symbol":
                // special case
                if(current_char == '/') {
                    token_type = TokenType.COMMENT;
                    state = "in_line_comment";
                }
                else if (current_char == '*') {
                    token_type = TokenType.COMMENT;
                    state = "in_block_comment";
                }
                else {
                    pr.unread(current_char);
                    //tokens.add(prev_char);
                    token_type = TokenType.SYMBOL;
                    state = "finish";
                }
                break;
            case "in_line_comment":
                if(pr.read() != '\n') {
                    state = "in_line_comment";
                } else {
                    state = "start";
                }
                break;
            case "in_block_comment":
                if(pr.read() == '*') {
                    if(pr.read() == '/') {
                        state = "start";
                    }
                    state = "in_block_comment";
                }
                else {
                    state = "in_block_comment";
                }
                break;
            default:
                state = "error";
                break;
            }
            System.out.println(current_token);
        }
    }


    private void readLineComment() {
        try {
            if(pr.read() != '\n') {
                readLineComment();
            }
        }
        catch(IOException e) {

        }
    }

    private void readBlockComment() {
        try {
            if(pr.read() != '*') {
                readLineComment();
            }
        }
        catch(IOException e) {

        }
    }
    
    public TokenType tokenType() {
        return token_type;
    }

    public String keyword() {
        if(token_type == TokenType.KEYWORD) {
            return current_token;
        }
        return null;
    }

    public char symbol() {
        if(token_type == TokenType.SYMBOL) {
            return (char)current_token.charAt(0);
        }
        return ' ';
    }

    public String identifier() {
        if(token_type == TokenType.IDENTIFIER) {
            return current_token;
        }
        return null;
    }

    public int intVal() {
        return Integer.parseInt(current_token);
    }

    public String stringVal() {
        return current_token;
    }
}

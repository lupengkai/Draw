/**
 * Created by tage on 11/17/15.
 */


public class Token {
    /**
     * 记号的类别
     */
    TokenType type;

    /**
     * 构成记号的字符串
     */
    String lexeme;

    /**
     * 若为常数，则是常数的值
     */
    double value;

    /**
     * 若为函数，则是包含函数方法的对象
     */
    Function func;

    public Token(TokenType type, String lexeme, double value, Function func) {
        this.type = type;
        this.lexeme = lexeme;
        this.value = value;
        this.func = func;
    }

    public Token(){
        this.func= new Function();
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Function getFunc() {
        return func;
    }

    public void setFunc(Function func) {
        this.func = func;
    }
}

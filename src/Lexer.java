import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by tage on 11/17/15.
 */

/**
 * 符号表内容
 */
public class Lexer {
    static final int TOKEN_LEN = 100;
    static Token tokenTab[] =
            {
                    new Token(TokenType.CONST_ID, "PI", 3.1415926, new Function()),
                    new Token(TokenType.CONST_ID, "E", 2.71828, new Function()),
                    new Token(TokenType.T, "T", 0.0, new Function()),
                    new Token(TokenType.FUNC, "SIN", 0.0, new Sin()),
                    new Token(TokenType.FUNC, "COS", 0.0, new Cos()),
                    new Token(TokenType.FUNC, "TAN", 0.0, new Tan()),
                    new Token(TokenType.FUNC, "LN", 0.0, new Log()),
                    new Token(TokenType.FUNC, "EXP", 0.0, new Exp()),
                    new Token(TokenType.FUNC, "SQRT", 0.0, new Sqrt()),
                    new Token(TokenType.ORIGIN, "ORIGIN", 0.0, new Function()),
                    new Token(TokenType.SCALE, "SCALE", 0.0, new Function()),
                    new Token(TokenType.ROT, "ROT", 0.0, new Function()),
                    new Token(TokenType.IS, "IS", 0.0, new Function()),
                    new Token(TokenType.FOR, "FOR", 0.0, new Function()),
                    new Token(TokenType.FROM, "FROM", 0.0, new Function()),
                    new Token(TokenType.TO, "TO", 0.0, new Function()),
                    new Token(TokenType.STEP, "STEP", 0.0, new Function()),
                    new Token(TokenType.DRAW, "DRAW", 0.0, new Function()),

            };

    /**
     * 跟踪源文件行号
     */
    static int lineNo;

    /**
     * 输入的文件
     */
    static BufferedReader br = null;

    /**
     * 记号字符缓冲
     */
    static StringBuffer tokenBuffer = new StringBuffer(0);

    /**
     * 初始化词法分析器
     *
     * @param fileName 打开的文件路径
     */
    static void initScanner(String fileName) {
        lineNo = 1;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Open Source File Error!");
            e.printStackTrace();
        }
    }

    /**
     * 关闭词法分析器
     */
    static void closeScanner() {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从源文件中读入一个字符
     *
     * @return 读入的字符(都是大写)
     */
    static char getChar() {
        int ch = 0;

        try {
            br.mark(20);
            ch = br.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Character.toUpperCase((char) ch);
    }

    /**
     * 回退一个字符
     */
    static void backChar(int ch) {
        try {
            if (ch != 65535)
            br.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入字符到记号缓冲区中
     *
     * @param ch 要添加的字符
     */
    static void addCharTokenString(char ch) {
        tokenBuffer.append(ch);
    }

    /**
     * 清空记号缓冲区
     */
    static void emptyTokenString() {
        tokenBuffer.delete(0, tokenBuffer.length());
    }

    /**
     * 判断所给的字符串是否在符号表中
     *
     * @param str 给定的字符串
     * @return 字符串属于的记号
     */
    static Token judgeKeyToken(String str) {
        int loop;
        for (loop = 0; loop < tokenTab.length; loop++) {
            if (tokenTab[loop].lexeme.equals(str))
                return tokenTab[loop ];
        }
        Token errorToken = new Token();
        errorToken.setType(TokenType.ERRTOKEN);
        errorToken.setLexeme(str);
        return errorToken;
    }

    /**
     * 获取一个记号
     *
     * @return
     */
    static Token getToken() {
        Token token = new Token();
        int ch;
        emptyTokenString();
        token.setLexeme(tokenBuffer.toString());
        while(true) {
            ch = getChar();
            if (ch == 65535) {
                token.setType(TokenType.NONTOKEN);
                return token;
            }

            if (ch == 10) lineNo++;
            if (ch != ' '&&ch != 10) break;
        }
        addCharTokenString((char) ch);
        if (Character.isLetter(ch)) {
            while (true) {
                ch = getChar();
                if (Character.isLetterOrDigit(ch)) addCharTokenString((char) ch);
                else break;
            }
            backChar(ch);
            token = judgeKeyToken(tokenBuffer.toString());
            token.setLexeme(tokenBuffer.toString());
            return token;
        } else if (Character.isDigit(ch)) {
            while (true) {
                ch = getChar();
                if (Character.isDigit(ch)) addCharTokenString((char) ch);
                else break;
            }
            if (ch == '.') {
                addCharTokenString((char) ch);
                while (true) {
                    ch = getChar();
                    if (Character.isDigit(ch)) addCharTokenString((char) ch);
                    else break;
                }

            }
            backChar(ch);
            token.setType(TokenType.CONST_ID);
            token.setValue(Double.parseDouble(tokenBuffer.toString()));
            token.setLexeme(tokenBuffer.toString());
            return token;
        } else {
            switch (ch) {
                case ';':
                    token.setType(TokenType.SEMICO);
                    token.setLexeme(";");
                    break;
                case '(':
                    token.setType(TokenType.L_BRACKET);
                    token.setLexeme("(");
                    break;
                case ')':
                    token.setType(TokenType.R_BRACKET);
                    token.setLexeme(")");
                    break;
                case ',':
                    token.setType(TokenType.COMMA);
                    token.setLexeme(",");
                    break;
                case '+':
                    token.setType(TokenType.PLUS);
                    token.setLexeme("+");
                    break;
                case '-':
                    ch = getChar();
                    if (ch == '-') {
                        while (ch != '\n' && ch != -1) ch = getChar();//注释
                        backChar(ch);
                        return getToken();
                    } else {
                        backChar(ch);
                        token.setType(TokenType.MINUS);
                        token.setLexeme("-");
                        break;
                    }

                case '/':
                    ch = getChar();
                    if (ch == '/') {
                        while (ch != '\n' && ch != -1) ch = getChar();
                        backChar(ch);
                        return getToken();

                    } else {
                        backChar(ch);
                        token.setType(TokenType.DIV);
                        token.setLexeme("/");
                        break;
                    }
                case '*':
                    ch = getChar();
                    if (ch == '*') {
                        token.setType(TokenType.POWER);
                        token.setLexeme("**");
                        break;
                    } else {
                        backChar(ch);
                        token.setType(TokenType.MUL);
                        break;
                    }
                default:
                    token.setType(TokenType.ERRTOKEN);
                    token.setLexeme(Integer.toString(ch));
                    break;
            }
        }
        return token;
    }


    public static void main(String[] args) {
        Token token;

        if (args.length < 1) {
            System.out.println("please input Source File!");
            return;
        }

        initScanner(args[0]);

        System.out.println("        type        lexeme        value        func");
        System.out.println("___________________________________________________");
        while (true) {
            token = getToken();
            if (token.getType() != TokenType.NONTOKEN )
                System.out.printf("%12s %12s,%12f,%12s\n",token.getType().toString(), token.getLexeme(), token.getValue(), token.getFunc().name);
            else break;
        }
        System.out.println("___________________________________________________");
        closeScanner();

    }


}

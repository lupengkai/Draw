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
                    new Token(TokenType.CONST_ID, "PI", 3.1415926, null),
                    new Token(TokenType.CONST_ID, "E", 2.71828, null),
                    new Token(TokenType.T, "T", 0.0, null),
                    new Token(TokenType.FUNC, "SIN", 0.0, new Sin()),
                    new Token(TokenType.FUNC, "COS", 0.0, new Cos()),
                    new Token(TokenType.FUNC, "TAN", 0.0, new Tan()),
                    new Token(TokenType.FUNC, "LN", 0.0, new Log()),
                    new Token(TokenType.FUNC, "EXP", 0.0, new Exp()),
                    new Token(TokenType.FUNC, "SQRT", 0.0, new Sqrt()),
                    new Token(TokenType.ORIGIN, "ORIGN", 0.0, null),
                    new Token(TokenType.SCALE, "SCALE", 0.0, null),
                    new Token(TokenType.ROT, "ROT", 0.0, null),
                    new Token(TokenType.IS, "IS", 0.0, null),
                    new Token(TokenType.FOR, "FOR", 0.0, null),
                    new Token(TokenType.FROM, "FROM", 0.0, null),
                    new Token(TokenType.TO, "TO", 0.0, null),
                    new Token(TokenType.STEP, "STEP", 0.0, null),
                    new Token(TokenType.DRAW, "DRAW", 0.0, null),

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
    static void backChar() {
        try {
            br.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入字符到记号缓冲区中
     * @param ch 要添加的字符
     */
    static void addCharTokenString(char ch) {
        int tokenLength = tokenBuffer.length();
        if (tokenLength + 1 >= tokenBuffer.length()) return;
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
     * @param str 给定的字符串
     * @return 字符串属于的记号
     */
    static Token judgeKeyToken(String str) {
        int loop;
        for (loop = 0; loop < tokenTab.length; loop++) {
            if (tokenTab[loop].lexeme.equals(str))
                return tokenTab[loop];
        }
        Token errorToken = new Token();
        errorToken.setType(TokenType.ERRTOKEN);
        return errorToken;
    }

    /**
     * 获取一个记号
     * @return
     */
    static Token getToken() {
        Token token = new Token();
        int ch;
        emptyTokenString();
        token.setLexeme(tokenBuffer.toString());
        for (; ; ) {
            ch = getChar();
            if (ch==-1) {
                token.setType(TokenType.NONTOKEN);
            }

            if (ch=='\n') lineNo++;
            if (ch=='\0') break;
        }
        addCharTokenString((char)ch);
    }


    public static void main(String[] args) {
        Token token;

        if (args.length < 1) {
            System.out.println("please input Source File!");
            return;
        }

        initScanner(args[0]);

        System.out.println("type    lexeme  value   func");
        System.out.println("____________________________");
        while (true) {
            token
        }

    }


}

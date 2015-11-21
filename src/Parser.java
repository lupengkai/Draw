/**
 * Created by tage on 11/20/15.
 */
public class Parser {
    public static double parameter = 0;
    public static double originX = 0;
    public static double originY = 0;
    public static double scaleX = 0;
    public static double scaleY = 0;
    public static double rotAngle = 0;
    public static Token token;

    public static void fetchToken() {
        token = Lexer.getToken();
        if (token.getType() == TokenType.ERRTOKEN) syntaxError(1);
    }

    public static void matchToken(TokenType theToken) {
        if (token.getType() != theToken) syntaxError(2);
        fetchToken();
    }


    public static void syntaxError(int caseOf) {
        switch (caseOf) {
            case 1:
                System.out.println(Lexer.lineNo + "错误记号" + token.getLexeme());
                Lexer.closeScanner();
                break;
            case 2:
                System.out.println(Lexer.lineNo + "不是预期记号" + token.getLexeme());
                Lexer.closeScanner();
                break;
        }
    }




    /**
     * 先序遍历并打印表达式的语法树
     *
     * @param root   根节点
     * @param indent
     */
    public static void printSyntaxTree(ExprNode root, int indent) {
        int temp;
        for (temp = 1; temp < indent; temp++) System.out.print("\t");
        switch (root.getOpCode()) {
            case PLUS:
                System.out.println("+");
                break;
            case MINUS:
                System.out.println("-");
                break;
            case MUL:
                System.out.println("*");
                break;
            case DIV:
                System.out.println("/");
                break;
            case POWER:
                System.out.println("**");
                break;
            case FUNC:
                System.out.println(((FuncNode) root).getFunction().name);
                break;
            case CONST_ID:
                System.out.println(((ConstNode) root).getConstValue());
                break;
            case T:
                System.out.println("T");
                break;
            default:
                throw new IllegalArgumentException("Error Tree Node");

        }

        if (root.getOpCode() == TokenType.CONST_ID || root.getOpCode() == TokenType.T)
            return;
        if (root.getOpCode() == TokenType.FUNC)
            printSyntaxTree(((FuncNode) root).getChild(), indent + 1);
        else {
            printSyntaxTree(((OperatorNode) root).getLeft(), indent + 1);
            printSyntaxTree(((OperatorNode) root).getRight(), indent + 1);

        }


    }


    public static void parser(String fileName) {
        System.out.println("Enter Parser");
        Lexer.initScanner(fileName);
        fetchToken();
        program();
        Lexer.closeScanner();
        System.out.println("Back Parser");
        return;
    }

    public static void program() {
        System.out.println("Enter Program");
        while (token.getType() != TokenType.NONTOKEN) {
            statement();
            matchToken(TokenType.SEMICO);
        }
        System.out.println("Back Program");
    }

    public static void statement() {
        System.out.println("Enter Statement");
        switch (token.getType()) {
            case ORIGIN:
                orignStatement();
                break;
            case SCALE:
                scaleStatement();
                break;
            case ROT:
                rotStatement();
                break;
            case FOR:
                forStatement();
                break;
            default:
                syntaxError(2);
        }
        System.out.println("Back Statement");

    }


    public static void orignStatement() {
        ExprNode tmp = new ExprNode();
        System.out.println("Enter OrignStatement");
        matchToken(TokenType.ORIGIN);
        matchToken(TokenType.IS);
        matchToken(TokenType.L_BRACKET);
        /**
         * 获取表达式
         */
        tmp = expression();


    }

    public static void scaleStatement() {

    }

    public static void rotStatement() {

    }

    public static void forStatement() {

    }

    public static ExprNode expression() {
        ExprNode left, right;
        TokenType tokenTmp;

        System.out.println("Enter Expression");
        left = term();

    }

    public static ExprNode term() {
        ExprNode left, right;
        TokenType tokenTmp;
        left = factor();

    }

    public static ExprNode factor() {
        ExprNode left, right;
        if (token.type == TokenType.PLUS) {
            matchToken(TokenType.PLUS);
            right = factor();
        } else if (token.type == TokenType.MINUS) {
            matchToken(TokenType.MINUS);
            right = factor();
            left = new ConstNode();
            left.setOpCode(TokenType.CONST_ID);
            ((ConstNode)left).setConstValue(0.0);
            right = makeExprNode()

        }

    }

    public static ExprNode component() {

    }

    public static ExprNode atom() {
        Token t = token;
        ExprNode address, temp;

        switch (token.type) {
            case CONST_ID:
                matchToken(TokenType.CONST_ID);
                address =
        }
    }

    /**
     * 生成常数节点
     *
     * @param value
     * @return
     */
    public static ExprNode makeExprNode(double value) {
        ExprNode expr = new ConstNode();
        ((ConstNode) expr).setConstValue(value);
        return expr;
    }

    public static ExprNode makeExprNode(Function function, ExprNode tmp) {
        ExprNode expr = new FuncNode();

    }

    public static ExprNode makeExprNode() {
        ExprNode expr = new ParmNode();
        ((ParmNode)expr).setParm(parameter);
        return expr;
    }

    public static ExprNode makeExprNode(TokenType opCode, ExprNode left, ExprNode right) {

    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("please input Source File!");
            return;
        }

        parser(args[0]);
    }


}

class ExprNode {
    private TokenType opCode;

    public TokenType getOpCode() {
        return opCode;
    }

    public void setOpCode(TokenType opCode) {
        this.opCode = opCode;
    }
}

class OperatorNode extends ExprNode {
    private ExprNode left;
    private ExprNode right;

    public ExprNode getLeft() {
        return left;
    }

    public void setLeft(ExprNode left) {
        this.left = left;
    }

    public ExprNode getRight() {
        return right;
    }

    public void setRight(ExprNode right) {
        this.right = right;
    }
}

class FuncNode extends ExprNode {
    private ExprNode child;
    private Function function;

    public ExprNode getChild() {
        return child;
    }

    public void setChild(ExprNode child) {
        this.child = child;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}

class ConstNode extends ExprNode {
    ConstNode() {
        super.setOpCode(TokenType.CONST_ID);
    }

    double constValue;

    public double getConstValue() {
        return constValue;
    }

    public void setConstValue(double constValue) {
        this.constValue = constValue;
    }
}

class ParmNode extends ExprNode {
    Double parm;

    public Double getParm() {
        return parm;
    }

    public void setParm(Double parm) {
        this.parm = parm;
    }
}



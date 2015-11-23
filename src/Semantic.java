/**
 * Created by tage on 11/23/15.
 */
public class Semantic {
    public static double getExprValue(ExprNode root) {
        if (root == null) return 0.0;
        switch (root.getOpCode()) {
            case PLUS:
                return getExprValue(((OperatorNode) root).getRight()) +
                        getExprValue(((OperatorNode) root).getLeft());
            case MINUS:
                return getExprValue(((OperatorNode) root).getRight()) -
                        getExprValue(((OperatorNode) root).getLeft());
            case MUL:
                return getExprValue(((OperatorNode) root).getRight()) *
                        getExprValue(((OperatorNode) root).getLeft());
            case DIV:
                return getExprValue(((OperatorNode) root).getRight()) /
                        getExprValue(((OperatorNode) root).getLeft());
            case POWER:
                return Math.pow(getExprValue(((OperatorNode) root).getRight()),
                        getExprValue(((OperatorNode) root).getLeft()));
            case FUNC:
                return ((FuncNode) root).getFunction().function(getExprValue(((FuncNode) root).getChild()));
            case CONST_ID:
                return ((ConstNode) root).getConstValue();
            case T:
                return ((ParmNode) root).getParm();
            default:
                return 0.0;
        }
    }


}

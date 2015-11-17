/**
 * Created by tage on 31/10/15.
 */


/**
 * 记号种类
 */
public enum TokenType {
    ORIGIN, SCALE, ROT, IS, TO,
    STEP, DRAW, FOR, FROM,
    T,
    SEMICO, L_BRACKET, R_BRACKET, COMMA,
    PLUS, MINUS, MUL, DIV, POWER,
    FUNC, CONST_ID,
    NONTOKEN,
    ERRTOKEN
}

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by tage on 11/24/15.
 */
public class Draw extends Frame {
    public static String filePath;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;


    public void paint(Graphics g) {
        try {
            Parser.parser(filePath, g);
        } catch (Parser.ErrTokenException e) {
            e.printStackTrace();
        } catch (Parser.NotExpectedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Lexer.closeScanner();
        }
    }
    public void launchFrame() {
        setLocation(400, 300);
        setSize(WIDTH, HEIGHT);
        setBackground(Color.WHITE);
        setTitle("Draw");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
        setResizable(false);

    }


    public static void drawLoop(double start, double end, double step, ExprNode xNode, ExprNode yNode, Graphics g) {
        double x, y;
        System.out.println(start + " " + end + " " + step);
        for (Parser.parameter = start; Parser.parameter <= end; Parser.parameter += step) {
            Coordinate coo = Semantic.calcCoord(xNode, yNode);
            System.out.println(coo.getX() + "  " + coo.getY());
            g.drawLine((int) coo.getX(), (int) coo.getY(), (int) coo.getX(), (int) coo.getY());
        }
    }


    public static void main(String[] args) throws Exception {
        Draw draw = new Draw();
        filePath = args[0];
        draw.launchFrame();


    }


}

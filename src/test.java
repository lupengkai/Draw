/**
 * Created by tage on 11/17/15.
 */
public class test {

        static char[] ch = new char[100];
        public static void main(String[] args) {
           /* String str=new String(ch);
            System.out.println(str.length());
            System.out.println(str);*/

            StringBuffer sb = new StringBuffer(0);
            System.out.println(sb.length());
            sb.append('a');
            sb.append('b');
            sb.append('c');
            System.out.println(sb);
            System.out.println(sb.length());
            sb.delete(0, 3);
            System.out.println(sb);
            System.out.println(sb.length());
            int a = '\n';
            System.out.println(a=='\n');
            System.out.println("\0 2 \0");
            System.out.println((char)65535);

        }

}

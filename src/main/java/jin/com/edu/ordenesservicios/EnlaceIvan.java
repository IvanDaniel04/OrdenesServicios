package jin.com.edu.ordenesservicios;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;


public class EnlaceIvan {
    private static Connection c;

    public static boolean isConnectedToInternet() {
        try {
            InetAddress inetAddress = InetAddress.getByName("www.google.com");
            return inetAddress.isReachable(5000);
        } catch (IOException e) {

            return false;
        }
    }

    public static Connection getConexion() {
        try {
            if (c == null || c.isClosed()) {
                String url = "jdbc:mysql://65.99.252.253:3306/eduwitco_ordenes";
                c = DriverManager.getConnection(url, "eduwitco_ordenes", "hoY4VbM(~m?$");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

}

package jin.com.edu.ordenesservicios;
import java.sql.Connection;
import java.sql.DriverManager;


public class EnlaceNB {
    private static Connection c;

    public static Connection getConexion(){
        try {
            if(c==null) {
                String url = "jdbc:mysql://localhost:3306/ordenesservicios";

                c = DriverManager.getConnection(url, "root", "root");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return c;

    }
}
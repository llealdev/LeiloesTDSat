
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



public class conectaDAO {
    Connection conn;
        
    public  Connection connectDB(){
         conn = null;
         try {
        
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?user=root&password=1");
            
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO" + erro.getMessage());
        }
        return conn;
    }
    
     public void desconectar(){
        try {
              if (conn != null && !conn.isClosed()) {
                  conn.close();
                  System.out.println("Voce se desconectou do banco de dados.");
              }
          } catch (SQLException ex) {
              System.out.println("Nao foi possivel desconectar do banco dados.");
          }
    }
}

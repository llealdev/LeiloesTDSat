

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public boolean cadastrarProduto (ProdutosDTO produto){
        conectaDAO conecta = new conectaDAO();
        Connection conn = conecta.connectDB();
        PreparedStatement prep = null;
        int status;
        try{
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?,?,?);";
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
     
            status = prep.executeUpdate();
            return status > 0;
        } catch(SQLException ex){
            System.out.println("Erro ao conectar: "+ ex.getMessage());
            System.out.println("Erro na sintaxe: "+ ex.getErrorCode());
            
            return false;
        }  finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conecta.desconectar();
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        return listagem;
    }
    
    
    
        
}



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

    public boolean cadastrarProduto(ProdutosDTO produto) {
        conectaDAO conecta = new conectaDAO();
        Connection conn = conecta.connectDB();
        PreparedStatement prep = null;
        int status;
        try {
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?,?,?);";
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            status = prep.executeUpdate();
            return status > 0;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            System.out.println("Erro na sintaxe: " + ex.getErrorCode());

            return false;
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conecta.desconectar();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        conectaDAO conecta = new conectaDAO();
        Connection conn = conecta.connectDB();
        PreparedStatement prep = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT id, nome, valor, status FROM produtos";
            prep = conn.prepareStatement(sql);
            rs = prep.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar produtos: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conecta.desconectar();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }
        return listagem;
    }

    public boolean venderProduto(int produtoId) {
        conectaDAO conecta = new conectaDAO();
        Connection conn = conecta.connectDB();
        PreparedStatement prep = null;

        try {
            // Comando SQL para atualizar o status do produto
            String sql = "UPDATE produtos SET status = ? WHERE id = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido"); // Define o novo status
            prep.setInt(2, produtoId);    // Define o ID do produto a ser atualizado

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0; // Retorna true se o produto foi atualizado
        } catch (SQLException ex) {
            System.out.println("Erro ao vender o produto: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conecta.desconectar();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();
        conectaDAO conecta = new conectaDAO();
        Connection conn = conecta.connectDB();
        PreparedStatement prep = null;
        ResultSet rs = null;

        try {
            // Consulta para buscar produtos com status "Vendido"
            String sql = "SELECT id, nome, valor, status FROM produtos WHERE status = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido"); // Define o parâmetro do status

            rs = prep.executeQuery();

            // Itera pelos resultados e adiciona à lista
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                produtosVendidos.add(produto);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar produtos vendidos: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conecta.desconectar();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }
        return produtosVendidos;
    }

}

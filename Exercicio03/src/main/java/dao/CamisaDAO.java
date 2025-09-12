package dao;
import model.Camisa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class CamisaDAO extends DAO {
    public CamisaDAO() {
        super();
        conectar();
    }
    public void finalize() {
        close();
    }
    public boolean insert(Camisa camisa) {
        boolean status = false;
        try {
            String sql = "INSERT INTO camisa (modelo, marca, tamanho, preco) VALUES (?, ?, ?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, camisa.getModelo());
            st.setString(2, camisa.getMarca());
            st.setString(3, camisa.getTamanho());
            st.setDouble(4, camisa.getPreco());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
    public Camisa get(int id) {
        Camisa camisa = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM camisa WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                camisa = new Camisa(rs.getInt("id"), rs.getString("modelo"), rs.getString("marca"),
                        rs.getString("tamanho"), rs.getDouble("preco"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return camisa;
    }
    public List<Camisa> get() {
        return get("");
    }
    public List<Camisa> getOrderByID() {
        return get("id");
    }
    public List<Camisa> getOrderByModelo() {
        return get("modelo");
    }
    public List<Camisa> getOrderByMarca() {
        return get("marca");
    }
    public List<Camisa> getOrderByPreco() {
        return get("preco");
    }
    private List<Camisa> get(String orderBy) {
        List<Camisa> camisas = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM camisa" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Camisa p = new Camisa(rs.getInt("id"), rs.getString("modelo"), rs.getString("marca"),
                        rs.getString("tamanho"), rs.getDouble("preco"));
                camisas.add(p);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return camisas;
    }
    public boolean update(Camisa camisa) {
        boolean status = false;
        try {
            String sql = "UPDATE camisa SET modelo = ?, marca = ?, tamanho = ?, preco = ? WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, camisa.getModelo());
            st.setString(2, camisa.getMarca());
            st.setString(3, camisa.getTamanho());
            st.setDouble(4, camisa.getPreco());
            st.setInt(5, camisa.getID());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM camisa WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
}
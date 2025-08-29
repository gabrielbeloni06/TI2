package Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Principal {
    private static DAO dao = new DAO();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    listarRegistros();
                    break;
                case 2:
                    inserirRegistro();
                    break;
                case 3:
                    excluirRegistro();
                    break;
                case 4:
                    atualizarRegistro();
                    break;
                case 5:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
        scanner.close();
    }
    private static void exibirMenu() {
        System.out.println("\n--- MENU DE OPÇÕES ---");
        System.out.println("1. Listar");
        System.out.println("2. Inserir");
        System.out.println("3. Excluir");
        System.out.println("4. Atualizar");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }
    private static void inserirRegistro() {
        System.out.print("Digite o nome do registro: ");
        String nome = scanner.nextLine();
        String sql = "INSERT INTO x (nome) VALUES (?)";
        try {
            dao.conectar();
            PreparedStatement stmt = dao.conexao.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.executeUpdate();
            System.out.println("Registro inserido com sucesso!");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
    }
    private static void listarRegistros() {
        List<X> lista = new ArrayList<>();
        String sql = "SELECT * FROM x";
        try {
            dao.conectar();
            PreparedStatement stmt = dao.conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("--- Registros no Banco ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                lista.add(new X(id, nome));
            }
            rs.close();
            stmt.close();
            
            if (lista.isEmpty()) {
                System.out.println("Nenhum registro encontrado.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
    }
    private static void excluirRegistro() {
        System.out.print("Digite o ID do registro a ser excluído: ");
        int id = scanner.nextInt();
        
        String sql = "DELETE FROM x WHERE id = ?";
        try {
            dao.conectar();
            PreparedStatement stmt = dao.conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Registro excluído com sucesso!");
            } else {
                System.out.println("Nenhum registro com o ID " + id + " foi encontrado.");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
    }
    private static void atualizarRegistro() {
        System.out.print("Digite o ID do registro a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();
        
        String sql = "UPDATE x SET nome = ? WHERE id = ?";
        try {
            dao.conectar();
            PreparedStatement stmt = dao.conexao.prepareStatement(sql);
            stmt.setString(1, novoNome);
            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Registro atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro com o ID " + id + " foi encontrado.");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
    }
}
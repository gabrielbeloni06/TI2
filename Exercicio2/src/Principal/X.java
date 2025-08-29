package Principal;
public class X {
    private int id;
    private String nome;
    public X(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public X(String nome) {
        this.nome = 0;
        this.nome = nome;
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome;
    }
}
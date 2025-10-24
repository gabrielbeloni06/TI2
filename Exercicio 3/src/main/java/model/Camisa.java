package model;
public class Camisa {
    private int id;
    private String modelo;
    private String marca;
    private String tamanho;
    private double preco;
    public Camisa() {
        id = -1;
        modelo = "";
        marca = "";
        tamanho = "";
        preco = 0.0;
    }
    public Camisa(int id, String modelo, String marca, String tamanho, double preco) {
        setId(id);
        setModelo(modelo);
        setMarca(marca);
        setTamanho(tamanho);
        setPreco(preco);
    }
    public int getID() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getTamanho() {
        return tamanho;
    }
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    @Override
    public String toString() {
        return "Camisa: " + modelo + " - Marca: " + marca + " - Tamanho: " + tamanho + " - Preço: " + preco;
    }
    @Override
    public boolean equals(Object obj) {
        return (this.getID() == ((Camisa) obj).getID());
    }
}
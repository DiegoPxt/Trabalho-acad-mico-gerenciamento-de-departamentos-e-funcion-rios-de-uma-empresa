public class No {

    public int valor;
    public String nome;
    public String cargo;
    int altura;
    public No esquerda;
    public No direita;
    public No pai;
    ArvoreFun arvoreFuncionarios;

    public No(int valor, String nome, String cargo) {
        this.nome = nome;
        this.cargo = cargo;
        this.esquerda = null;
        this.direita = null;
        this.pai = null;
        this.valor = valor;
        this.altura = 1;
        this.arvoreFuncionarios = new ArvoreFun();
    }
}



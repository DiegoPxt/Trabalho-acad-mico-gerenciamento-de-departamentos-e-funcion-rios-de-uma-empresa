import java.util.ArrayList;
import java.util.List;

public class NoDep {
    int valorDep; // ID do departamento
    String nomeDep;
    String gerente;
    public NoDep esquerda;
    public NoDep direita;
    public NoDep pai;
    int altura;
    ArvoreFun arvoreFuncionarios;

    public NoDep(int valorDep, String nomeDep, String gerente) {
        this.valorDep = valorDep;
        this.nomeDep = nomeDep;
        this.gerente = gerente;
        this.arvoreFuncionarios = new ArvoreFun();
        this.altura = 1;
        this.esquerda = null;
        this.direita = null;
        this.pai = null;
    }

    public void mostrarFuncionarios() {
        arvoreFuncionarios.exibirFuncionariosEmOrdemCadastro();
    }
}

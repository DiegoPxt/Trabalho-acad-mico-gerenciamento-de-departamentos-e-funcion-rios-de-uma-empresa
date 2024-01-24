import java.util.ArrayList;
import java.util.List;

public class ArvoreFun {
    public No raiz;

    private int altura(No no) {
        if (no == null) return 0;
        return no.altura;
    }

    private int getBalanceamento(No no) {
        if (no == null) return 0;
        return altura(no.esquerda) - altura(no.direita);
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    public void inserirFun(int idFuncionario, String nomeFuncionario, String cargoFuncionario, int idDepartamento) {
        this.raiz = inserirFunRec(this.raiz, idFuncionario, nomeFuncionario, cargoFuncionario, idDepartamento);
    }

    private No inserirFunRec(No no, int idFuncionario, String nomeFuncionario, String cargoFuncionario, int idDepartamento) {
        if (no == null) {
            return new No(idFuncionario, nomeFuncionario, cargoFuncionario);
        }

        if (idFuncionario < no.valor) {
            no.esquerda = inserirFunRec(no.esquerda, idFuncionario, nomeFuncionario, cargoFuncionario, idDepartamento);
        } else if (idFuncionario > no.valor) {
            no.direita = inserirFunRec(no.direita, idFuncionario, nomeFuncionario, cargoFuncionario, idDepartamento);
        } else {
            // Valor já existe na árvore, não faz nada
            return no;
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int balanceamento = getBalanceamento(no);

        if (balanceamento > 1 && idFuncionario < no.esquerda.valor) {
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && idFuncionario > no.direita.valor) {
            return rotacaoEsquerda(no);
        }

        if (balanceamento > 1 && idFuncionario > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && idFuncionario < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public ArrayList<No> inOrder() {
        ArrayList<No> funcionarios = new ArrayList<>();
        inOrderRec(raiz, funcionarios);
        return funcionarios;
    }

    private void inOrderRec(No no, ArrayList<No> funcionarios) {
        if (no != null) {
            inOrderRec(no.esquerda, funcionarios);
            funcionarios.add(no);
            inOrderRec(no.direita, funcionarios);
        }
    }

    public int removerFun(int valor) {
        this.raiz = removerFunRec(this.raiz, valor);
        return valor;
    }

    private No removerFunRec(No no, int valor) {
        if (no == null) {
            return null;
        }

        if (valor < no.valor) {
            no.esquerda = removerFunRec(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = removerFunRec(no.direita, valor);
        } else {
            if ((no.esquerda == null) || (no.direita == null)) {
                No temp = null;
                if (null == no.esquerda) {
                    temp = no.direita;
                } else {
                    temp = no.esquerda;
                }

                if (temp == null) {
                    temp = no;
                    no = null;
                } else {
                    no = temp;
                }
            } else {
                No temp = sucessorMinimo(no.direita);

                no.valor = temp.valor;
                no.nome = temp.nome; // Atualiza também o nome do funcionário
                no.cargo = temp.cargo; // Atualiza também o cargo do funcionário

                no.direita = removerFunRec(no.direita, temp.valor);
            }
        }

        if (no == null) {
            return null;
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));


        int balanceamento = getBalanceamento(no);

        if (balanceamento > 1 && getBalanceamento(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }

        if (balanceamento > 1 && getBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && getBalanceamento(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }

        if (balanceamento < -1 && getBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }


    private No sucessorMinimo(No no) {
        No atual = no;

        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }

        return atual;
    }

    public No buscarFuncionarioPorId(int idFuncionario) {
        return buscarFuncionarioPorIdRec(this.raiz, idFuncionario);
    }

    private No buscarFuncionarioPorIdRec(No no, int idFuncionario) {
        if (no == null || no.valor == idFuncionario) {
            return no;
        }

        No resultado = buscarFuncionarioPorIdRec(no.esquerda, idFuncionario);
        if (resultado != null) {
            return resultado;
        }

        return buscarFuncionarioPorIdRec(no.direita, idFuncionario);
    }


    public void exibirFuncionariosEmOrdemCadastro() {
        if (this.raiz != null) {
            List<No> funcionarios = new ArrayList<>();
            percorrerEmOrdem(this.raiz, funcionarios);

            System.out.println("Funcionários cadastrados:");
            for (No funcionario : funcionarios) {
                System.out.println("ID: " + funcionario.valor + ", Nome: " + funcionario.nome + ", Cargo: " + funcionario.cargo);
            }
        } else {
            System.out.println("Nenhum funcionário cadastrado neste departamento.");
        }
    }

    private void percorrerEmOrdem(No atual, List<No> funcionarios) {
        if (atual != null) {
            percorrerEmOrdem(atual.esquerda, funcionarios);
            funcionarios.add(atual);
            percorrerEmOrdem(atual.direita, funcionarios);
        }
    }
}



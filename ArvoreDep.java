import java.util.ArrayList;

public class ArvoreDep {
    private NoDep raiz;

    private int altura(NoDep no) {
        if (no == null) return 0;
        return no.altura;
    }

    private int getBalanceamento(NoDep no) {
        if (no == null) return 0;
        return altura(no.esquerda) - altura(no.direita);
    }

    private NoDep rotacaoDireita(NoDep y) {
        System.out.println("Realizando a rotação à direita em " + y.valorDep);
        NoDep x = y.esquerda;
        NoDep T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private NoDep rotacaoEsquerda(NoDep x) {
        System.out.println("Realizando a rotação à esquerda em " + x.valorDep);
        NoDep y = x.direita;
        NoDep T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    public void inserirDep(int valor, String nome, String gerente) {
        this.raiz = inserirDepRec(this.raiz, valor, nome, gerente);
    }

    private NoDep inserirDepRec(NoDep no, int valor, String nome, String gerente) {
        if (no == null) {
            return new NoDep(valor, nome, gerente);
        }

        if (valor < no.valorDep) {
            no.esquerda = inserirDepRec(no.esquerda, valor, nome, gerente);
        } else if (valor > no.valorDep) {
            no.direita = inserirDepRec(no.direita, valor, nome, gerente);
        } else {
            // Valor já existe na árvore, não faz nada
            return no;
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int balanceamento = getBalanceamento(no);

        if (balanceamento > 1 && valor < no.esquerda.valorDep) {
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor > no.direita.valorDep) {
            return rotacaoEsquerda(no);
        }

        if (balanceamento > 1 && valor > no.esquerda.valorDep) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor < no.direita.valorDep) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void listarFuncionariosDep(int valorDep) {
        NoDep departamento = consultarDep(valorDep);
        if (departamento != null) {
            ArrayList<No> funcionarios = departamento.arvoreFuncionarios.inOrder();
            if (funcionarios.isEmpty()) {
                System.out.println("Não há funcionários neste departamento.");
            } else {
                System.out.println("Funcionários do Departamento " + departamento.nomeDep + " em ordem de cadastro:");
                for (No funcionario : funcionarios) {
                    System.out.println("ID: " + funcionario.valor + ", Nome: " + funcionario.nome + ", Cargo: " + funcionario.cargo);
                }
            }
        } else {
            System.out.println("Departamento não encontrado.");
        }
    }

    // Métodos de conexão e interação entre as árvores
    public void inserirFuncionario(int idFuncionario, String nomeFuncionario, String cargoFuncionario, int idDepartamento) {
        this.raiz = inserirFuncionarioRec(this.raiz, idFuncionario, nomeFuncionario, cargoFuncionario, idDepartamento);
    }

    private NoDep inserirFuncionarioRec(NoDep no, int idFuncionario, String nomeFuncionario, String cargoFuncionario, int idDepartamento) {
        if (no != null) {
            if (no.valorDep == idDepartamento) {
                no.arvoreFuncionarios.inserirFun(idFuncionario, nomeFuncionario, cargoFuncionario, idDepartamento);
            } else if (idDepartamento < no.valorDep) {
                no.esquerda = inserirFuncionarioRec(no.esquerda, idFuncionario, nomeFuncionario, cargoFuncionario, idDepartamento);
            } else {
                no.direita = inserirFuncionarioRec(no.direita, idFuncionario, nomeFuncionario, cargoFuncionario, idDepartamento);
            }
            return no;
        } else {
            System.out.println("Departamento não encontrado.");
            return null;
        }
    }


    public void removerDep(int valor) {
        this.raiz = removerDepRec(this.raiz, valor);
    }

    private NoDep removerDepRec(NoDep no, int valor) {
        if (no == null) {
            return null;
        }

        if (valor < no.valorDep) {
            no.esquerda = removerDepRec(no.esquerda, valor);
        } else if (valor > no.valorDep) {
            no.direita = removerDepRec(no.direita, valor);
        } else {
            if ((no.esquerda == null) || (no.direita == null)) {
                NoDep temp;
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
                NoDep temp = sucessorMinimo(no.direita);

                no.valorDep = temp.valorDep;

                no.direita = removerDepRec(no.direita, temp.valorDep);
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

    public boolean removerFuncionarioDepartamento(int idFuncionario) {
        return removerFuncionarioDepartamentoRec(raiz, idFuncionario);
    }

    private boolean removerFuncionarioDepartamentoRec(NoDep no, int idFuncionario) {
        if (no != null) {
            No funcionarioEncontrado = no.arvoreFuncionarios.buscarFuncionarioPorId(idFuncionario);

            if (funcionarioEncontrado != null) {
                no.arvoreFuncionarios.removerFun(idFuncionario);
                System.out.println("Funcionário removido do departamento com sucesso!");

                No funcionarioAbaixo = no.arvoreFuncionarios.buscarFuncionarioPorId(idFuncionario + 1);
                if (funcionarioAbaixo != null) {
                    no.arvoreFuncionarios.removerFun(idFuncionario + 1);

                    // Atualizando o ID do funcionário realocado
                    funcionarioAbaixo.valor = idFuncionario;

                    // Realocando o funcionário atualizado
                    no.arvoreFuncionarios.inserirFun(funcionarioAbaixo.valor, funcionarioAbaixo.nome, funcionarioAbaixo.cargo, no.valorDep);
                    System.out.println("Funcionário abaixo realocado para ocupar o lugar do funcionário removido.");
                } else {
                    System.out.println("Não foi possível encontrar o funcionário abaixo para realocação.");
                }

                return true;
            } else {
                System.out.println("Funcionário não encontrado no departamento.");
            }
        } else {
            System.out.println("Departamento não encontrado.");
        }

        return false;
    }
    private NoDep sucessorMinimo(NoDep no) {
        NoDep atual = no;

        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }

        return atual;
    }

    public NoDep consultarDep(int valor) {
        return (NoDep) consultarDepRec(this.raiz, valor);
    }

    private Object consultarDepRec(NoDep no, int valor) {
        if (no == null || no.valorDep == valor) {
            return no;
        }

        if (valor < no.valorDep) {
            return consultarDepRec(no.esquerda, valor);
        } else {
            return consultarDepRec(no.direita, valor);
        }
    }
    public NoDep buscarDepartamentoPorId(int idDepartamento) {
        return buscarDepartamentoPorIdRec(this.raiz, idDepartamento);
    }

    private NoDep buscarDepartamentoPorIdRec(NoDep no, int idDepartamento) {
        if (no == null || no.valorDep == idDepartamento) {
            return no;
        }

        NoDep resultado = buscarDepartamentoPorIdRec(no.esquerda, idDepartamento);
        if (resultado != null) {
            return resultado;
        }

        return buscarDepartamentoPorIdRec(no.direita, idDepartamento);
    }

}
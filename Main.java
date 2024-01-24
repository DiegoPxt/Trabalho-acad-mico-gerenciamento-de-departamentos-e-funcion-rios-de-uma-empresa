import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArvoreFun arvoreFun = new ArvoreFun();
        ArvoreDep arvoreDep = new ArvoreDep();

        boolean sair = false;

        while (!sair) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Testar ArvoreFun");
            System.out.println("2. Testar ArvoreDep");
            System.out.println("3. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    testarArvoreFun(arvoreFun, arvoreDep, scanner);
                    break;
                case 2:
                    testarArvoreDep(arvoreDep, scanner);
                    break;
                case 3:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

        scanner.close();
    }

    private static void testarArvoreFun(ArvoreFun arvoreFun, ArvoreDep arvoreDep, Scanner scanner) {
        boolean sair = false;

        while (!sair) {
            System.out.println("\nMenu dos Funcionários:");
            System.out.println("1. Inserir funcionário");
            System.out.println("2. Remover funcionário");
            System.out.println("3. Consultar funcionário");
            System.out.println("4. Voltar");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID do funcionário: ");
                    int idFuncionario = scanner.nextInt();
                    scanner.nextLine(); // Consumir quebra de linha

                    System.out.print("Digite o nome do funcionário: ");
                    String nomeFuncionario = scanner.nextLine();

                    System.out.print("Digite o cargo do funcionário: ");
                    String cargoFuncionario = scanner.nextLine();

                    System.out.print("Digite o ID do departamento do funcionário: ");
                    int idDepartamento = scanner.nextInt();

                    // Adicionar funcionário na arvoreFun e na arvoreDep correspondente
                    arvoreFun.inserirFun(idFuncionario, nomeFuncionario, cargoFuncionario, idDepartamento);
                    arvoreDep.inserirFuncionario(idFuncionario, nomeFuncionario, cargoFuncionario, idDepartamento);

                    System.out.println("Funcionário cadastrado e alocado ao departamento com sucesso!");
                    break;
                case 2:
                    System.out.println("Digite o ID do funcionario que deseja remover: ");
                    int idRemover = scanner.nextInt();
                    arvoreFun.removerFun(idRemover); // Remove o funcionário da arvoreFun
                    arvoreDep.removerFuncionarioDepartamento(idRemover);// Remove o funcionário do departamento
                    System.out.println("Funcionário removido com sucesso!");
                    break;
                case 3:
                    System.out.print("Digite o ID do funcionário a ser consultado: ");
                    int idConsultar = scanner.nextInt();
                    System.out.println("Digite o departamento: ");
                    int idConsultarDep = scanner.nextInt();
                    NoDep dep = arvoreDep.buscarDepartamentoPorId(idConsultarDep);
                    No funcionario = arvoreFun.buscarFuncionarioPorId(idConsultar);

                    if (funcionario != null) {
                        if (dep != null) {
                            System.out.println("ID: " + funcionario.valor);
                            System.out.println("Nome: " + funcionario.nome);
                            System.out.println("Cargo: " + funcionario.cargo);
                        } else {
                            System.out.println("Funcionário não encontrado.");
                        }
                    }
                    break;
                case 4:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void testarArvoreDep(ArvoreDep arvoreDep, Scanner scanner) {
        boolean sair = false;

        while (!sair) {
            System.out.println("\nMenu dos Departamentos:");
            System.out.println("1. Inserir departamento");
            System.out.println("2. Remover departamento");
            System.out.println("3. Consultar departamento");
            System.out.println("4. Voltar");


            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID do departamento: ");
                    int idDepartamento = scanner.nextInt();
                    scanner.nextLine(); // Consumir quebra de linha

                    System.out.print("Digite o nome do departamento: ");
                    String nomeDepartamento = scanner.nextLine();

                    System.out.print("Digite o nome do gerente: ");
                    String nomeGerente = scanner.nextLine();

                    arvoreDep.inserirDep(idDepartamento, nomeDepartamento, nomeGerente);
                    break;
                case 2:
                    System.out.print("Digite o ID do departamento a ser removido: ");
                    int idRemover = scanner.nextInt();
                    arvoreDep.removerDep(idRemover);
                    System.out.println("Departamento removido com sucesso!");
                    break;
                case 3:
                    System.out.print("Digite o ID do departamento a ser consultado: ");
                    int idConsultar = scanner.nextInt();
                    arvoreDep.listarFuncionariosDep(idConsultar);
                    break;

                case 4:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
}

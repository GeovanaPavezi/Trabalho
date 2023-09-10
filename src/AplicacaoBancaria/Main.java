package AplicacaoBancaria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static boolean sistema = true;
    public static ClienteService clienteService = new ClienteService();
    private static ContaService contaService = new ContaService(clienteService);
    private static BufferedReader ler = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {

        do {
            switch (menu()) {
                case 1:
                    clienteService.cadastrarCliente();
                    break;

                case 2:
                    contaService.cadastrarContaCorrente();
                    break;

                case 3:
                    contaService.cadastrarContaPoupanca();
                    break;

                case 4:
                    contaService.Saque();
                    break;

                case 5:
                    contaService.deposito();
                    break;

                case 6:
                    contaService.transferencia();
                    break;

                case 0:
                    sistema = false;
                    break;

                default:
                    break;
            }
        }while (sistema);
    }

    public static int menu() throws IOException {
        System.out.println("_____________________________");
        System.out.println("           MENU              ");
        System.out.println(" 1 Cadastrar Cliente         ");
        System.out.println(" 2 Cadastrar Conta Corrente  ");
        System.out.println(" 3 Cadastar Conta Poupança   ");
        System.out.println(" 4 Efetuar Saque             ");
        System.out.println(" 5 Efetuar Depósito          ");
        System.out.println(" 6 Efetuar Transfêrencia     ");
        System.out.println(" 0 Encerrar Programa         ");
        System.out.println("_____________________________");
        return Integer.parseInt(ler.readLine());

    }
}
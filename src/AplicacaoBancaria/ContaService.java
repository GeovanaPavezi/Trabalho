package AplicacaoBancaria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ContaService {

    private final int SAQUE = 1, DEPOSITO = 2, TRANSFERENCIA = 3, ORIGEM_CORRENTE =4, ORIGEM_POUPANCA = 5;
    private final boolean POUPANCA = false, CORRENTE = true;
    private ClienteService clienteService = new ClienteService();
    private HashMap<String, AbstractConta> hashContaCorrente = new HashMap<String, AbstractConta>();
    private HashMap<String, AbstractConta> hashContaPoupanca = new HashMap<String, AbstractConta>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ContaService(ClienteService cliente) {
        this.clienteService = cliente;
    }

    public void cadastrarContaCorrente() throws Exception {
        System.out.println("____________________________________________________");
        System.out.println("             Cadastrar Conta Corrente               ");
        System.out.println("____________________________________________________");

        ContaCorrente(new ContaCorrente(clienteService.verificarCliente(false),
                getCodigoAgencia(), getSenha()));
    }

    public void ContaCorrente(AbstractConta conta) throws Exception {
        if (hashContaCorrente.containsKey(conta.getCpf())) {
            System.out.println("O CPF inserido ja possui uma conta corrente " + "\nAperte qualquer tecla para continuar");
            System.in.read();
            return;
        }
        hashContaCorrente.put(conta.getCpf(), conta);

        System.out.println(hashContaCorrente.toString());
    }

    public void cadastrarContaPoupanca() throws Exception {
        System.out.println("____________________________________________________");
        System.out.println("             Cadastrar Conta Poupanca               ");
        System.out.println("____________________________________________________");

        ContaPoupanca(new ContaPoupanca(clienteService.verificarCliente(false),
                getCodigoAgencia(), getSenha()));
    }

    private void ContaPoupanca(AbstractConta conta) throws Exception {
        if (hashContaPoupanca.containsKey(conta.getCpf())) {
            System.out.println("O CPF inserido já possui uma conta corrente " + "\nAperte qualquer tecla para continuar");
            System.in.read();
            return;
        }
        hashContaPoupanca.put(conta.getCpf(), conta);

        System.out.println(hashContaPoupanca.toString());
    }

    private Integer getSenha() throws Exception {
        System.out.println("Insira uma senha de 4 dígitos: ");
        String verificaSenha = reader.readLine();
        if (!verificaSenha.matches("[0-9]+") || verificaSenha.length() != 4) {
            System.out.println("Senha com parâmetros inválidos!");
            return  getSenha();
        } else {
            int senha = Integer.parseInt(verificaSenha);
            return senha;
        }
    }

    private Boolean verificarSenha(AbstractConta conta) throws Exception {
        if (conta.getSenha() == getSenha() ){
            return true;
        } else {
            return false;
        }
    }

    private Integer getCodigoAgencia() throws NumberFormatException, IOException {
        System.out.println("Insira o número da sua agência: ");
        try {
            return Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Insira apenas números: ");
            return getCodigoAgencia();
        }
    }

    private Boolean verificarCodigoAgencia(AbstractConta conta) throws NumberFormatException, IOException {
        if (conta.getCodigoAgencia() == getCodigoAgencia()) {
            return true;
        } else {
            System.out.println("O código da agência não existe");
            return verificarCodigoAgencia(conta);
        }
    }

    private AbstractConta verificarContaPoupanca() throws IOException {
        System.out.println("Insira o CPF: ");
        String cpf = reader.readLine();
        if (hashContaPoupanca.containsKey(cpf)) {
            return hashContaPoupanca.get(cpf);
        } else {
            System.out.println("O CPF não foi cadastrado como conta poupança, insira novamente: ");
            return verificarContaPoupanca();
        }
    }
    private AbstractConta verificarContaCorrente() throws IOException {
        System.out.println("Insira o CPF: ");
        String cpf = reader.readLine();
        if (hashContaCorrente.containsKey(cpf)) {
            return hashContaCorrente.get(cpf);
        } else {
            System.out.println("O CPF não foi cadastrado como conta corrente, insira novamente: ");
            return verificarContaCorrente();
        }
    }

    private void verificarConta(int verificar) throws Exception {
        System.out.println(" 1 Conta Corrente ");
        System.out.println(" 2 Conta Poupança ");

        switch (Integer.parseInt(reader.readLine())) {
            case 1:
                if (verificar == SAQUE) {
                    efetuarSaque(verificarContaCorrente(), CORRENTE);
                } else if (verificar == DEPOSITO) {
                    efetuarDeposito(verificarContaCorrente(), CORRENTE);

                } else if (verificar == TRANSFERENCIA) {
                    System.out.println("Conta destino da tranferência: ");
                    verificarConta(ORIGEM_CORRENTE);

                } else if (verificar == ORIGEM_CORRENTE) {
                    System.out.println("Insira o CPF da conta Origem, e após a de Destino: ");
                    efetuarTransferencia(verificarContaCorrente(), verificarContaCorrente(), CORRENTE, CORRENTE);
                } else if (verificar == ORIGEM_POUPANCA) {
                    System.out.println("Insira o CPF da conta Origem, e após a de Destino: ");
                    efetuarTransferencia(verificarContaPoupanca(), verificarContaCorrente(), POUPANCA, CORRENTE);
                }
                break;

            case 2:
                if (verificar == SAQUE) {
                    efetuarSaque(verificarContaPoupanca(), POUPANCA);
                } else if (verificar == DEPOSITO) {
                    efetuarDeposito(verificarContaPoupanca(), POUPANCA);

                } else if (verificar == TRANSFERENCIA) {
                    System.out.println("Conta destino da tranferência: ");
                    verificarConta(ORIGEM_POUPANCA);

                } else if (verificar == ORIGEM_CORRENTE) {
                    System.out.println("Insira o CPF da conta Origem, e após a de Destino: ");
                    efetuarTransferencia(verificarContaCorrente(), verificarContaPoupanca(), CORRENTE, POUPANCA);
                } else if (verificar == ORIGEM_POUPANCA) {
                    System.out.println("Insira o CPF da conta Origem, e após a de Destino: ");
                    efetuarTransferencia(verificarContaPoupanca(), verificarContaPoupanca(), POUPANCA, POUPANCA);
                }
                break;

            default:
                System.out.println("Insira um valor presente no menu: ");
                return;
        }
    }

    public void Saque() throws Exception {
        System.out.println("_________________________________________");
        System.out.println(" Efetuar Saque ");
        System.out.println(" Qual a conta que deseja efetuar o saque?");
        verificarConta(SAQUE);
    }

    private void efetuarSaque(AbstractConta conta, boolean verificar) throws Exception {
        verificarCodigoAgencia(conta);
        Double valor = getValorTransacao();
        if (verificarSenha(conta)) {
            if (!verificarSaldo(conta, valor)) {
                System.out.println("O Saque não pôde ser concluído, Saldo insuficiente!");
            } else {
                conta.saldo = conta.saldo - valor;

                if (verificar) {
                    hashContaCorrente.replace(conta.getCpf(), conta);
                    System.out.println(hashContaCorrente.toString());
                } else{
                    hashContaPoupanca.replace(conta.getCpf(), conta);
                    System.out.println(hashContaPoupanca.toString());
                }
            }
        }
    }

public void deposito() throws Exception {
    System.out.println("____________________________________________");
    System.out.println("  Efetuar Deposito ");
    System.out.println(" Qual a conta que deseja efetuar o depósito?");
    verificarConta(DEPOSITO);
}

    private void efetuarDeposito(AbstractConta conta, boolean verificar) throws Exception {
        verificarCodigoAgencia(conta);
        Double valor = getValorTransacao();
        if (verificarSenha(conta)) {
            conta.saldo = conta.saldo + valor;

            if (verificar) {
                hashContaCorrente.replace(conta.getCpf(), conta);
                System.out.println(hashContaCorrente.toString());
            } else {
                hashContaPoupanca.replace(conta.getCpf(), conta);
                System.out.println(hashContaPoupanca.toString());
            }
        }
    }

    public void transferencia() throws Exception {
        System.out.println("_____________________________________");
        System.out.println("  Efetuar Transferencia ");
        System.out.println(" Qual a conta origem da tranferência?");
        verificarConta(TRANSFERENCIA);
    }

    private void efetuarTransferencia(AbstractConta contaOrigem, AbstractConta contaDestino, boolean origem, boolean destino) throws Exception {
        System.out.println("Insira o Código de Agência da conta Origem, e após a de Destino: ");
        verificarCodigoAgencia(contaOrigem);
        verificarCodigoAgencia(contaDestino);
        Double valor = getValorTransacao();
        if (!verificarSenha(contaOrigem)){
            System.out.println("Senha inválida!");
            return;
        }
        if (!verificarSaldo(contaOrigem, valor)){
            System.out.println("A Transferência não pode ser concluída, Saldo insuficiente!");
            return;
        }
        contaOrigem.saldo = contaOrigem.saldo - valor;
        contaDestino.saldo = contaDestino.saldo + valor;

        if (origem == true && destino == true){
            hashContaCorrente.replace(contaDestino.getCpf(), contaDestino);
            hashContaCorrente.replace(contaOrigem.getCpf(), contaOrigem);

        } else if (origem == true && destino == false) {
            hashContaCorrente.replace(contaOrigem.getCpf(), contaOrigem);
            hashContaPoupanca.replace(contaDestino.getCpf(), contaDestino);

        } else if (origem == false && destino == false) {
            hashContaPoupanca.replace(contaDestino.getCpf(), contaDestino);
            hashContaPoupanca.replace(contaOrigem.getCpf(), contaOrigem);
        } else if (origem == false && destino == true) {
            hashContaPoupanca.replace(contaOrigem.getCpf(), contaOrigem);
            hashContaCorrente.replace(contaDestino.getCpf(), contaDestino);

        }
    }

    private Boolean verificarSaldo(AbstractConta conta, Double valor){
        if (conta.saldo - valor < 0){
            return false;
        } else{
            return true;
        }
    }

    private Double getValorTransacao() throws NumberFormatException, IOException {
        double valor = 0;
        try {
        System.out.println("Insira o valor desejado: ");
        valor = Double.parseDouble(reader.readLine());
            if (valor <= 0) {
                System.out.println("O Valor não pode ser igual ou menor que 0!");
                return getValorTransacao();
            }
        }catch (Exception e){
            System.out.println("Inválido! Insira apenas números");
            return getValorTransacao();
        }
            return valor;
        }
    }

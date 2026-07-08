package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.dao.UsuarioDAO;
import br.udesc.doo2.cantina.exception.UsuarioException;
import br.udesc.doo2.cantina.model.Administrador;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.repository.UsuarioRepository;
import br.udesc.doo2.cantina.view.LoginView;
import br.udesc.doo2.cantina.view.administrador.HomeAdministradorView;
import br.udesc.doo2.cantina.view.cliente.HomeClienteView;
import br.udesc.doo2.cantina.view.CadastroView;
import br.udesc.doo2.cantina.controller.CadastroController;
import br.udesc.doo2.cantina.infra.Sessao;

public class LoginController {

    private final LoginView view;
    private final UsuarioRepository usuarioRepository;

    public LoginController(LoginView view) {
        this.view = view;
        this.usuarioRepository = new UsuarioDAO();
        registrarListeners();
    }

    // Construtor alternativo para testes (injeção de um fake/mock de
    // UsuarioRepository), mantendo o DAO real como padrão em produção.
    public LoginController(LoginView view, UsuarioRepository usuarioRepository) {
        this.view = view;
        this.usuarioRepository = usuarioRepository;
        registrarListeners();
    }

    private void registrarListeners() {
        view.adicionarAcaoBtnEntrarCliente(e -> tratarLoginCliente());
        view.adicionarAcaoBtnEntrarAdmin(e -> tratarLoginAdministrador());
        view.adicionarAcaoBtnCadastrar(e -> abrirTelaCadastro());
    }

    /**
     * Fluxo de login do Cliente: lê os campos da View, chama o DAO via
     * autenticarCliente, e decide a navegação.
     */
    private void tratarLoginCliente() {
        String matricula = view.getMatricula();
        char[] senha = view.getSenhaCliente();

        try {
            Cliente cliente = usuarioRepository.autenticarCliente(matricula, senha);

            new HomeClienteView(cliente).setVisible(true);
            view.fecharTela();

        } catch (UsuarioException ex) {
            view.apresentarMensagemErro(ex.getMessage());
        } finally {
            // Limpa a senha da memória assim que possível, independente do
            // resultado (sucesso ou falha de autenticação).
            java.util.Arrays.fill(senha, '\0');
        }
    }

    /**
     * Fluxo de login do Administrador: lê apenas o campo de senha (não há
     * matrícula/identificador para Admin).
     */
    private void tratarLoginAdministrador() {
        char[] senha = view.getSenhaAdmin();

        try {
            Administrador admin = usuarioRepository.autenticarAdministrador(senha);

            new HomeAdministradorView(admin).setVisible(true);
            view.fecharTela();

        } catch (UsuarioException ex) {
            view.apresentarMensagemErro(ex.getMessage());
        } finally {
            java.util.Arrays.fill(senha, '\0');
        }
    }

    private void abrirTelaCadastro() {
        CadastroView cadastroView = new CadastroView();
        new CadastroController(cadastroView);
        cadastroView.setVisible(true);
        view.fecharTela();
    }
}
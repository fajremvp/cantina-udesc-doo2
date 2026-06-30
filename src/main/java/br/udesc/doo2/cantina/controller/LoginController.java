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

import javax.swing.JOptionPane;

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

    /**
     * Registra, via lambda, os Listeners nos botões da View. Centraliza
     * aqui toda a reação a eventos - a View não sabe o que acontece quando
     * o botão é clicado, apenas dispara o evento.
     */
    private void registrarListeners() {
        view.getBotaoEntrarCliente().addActionListener(e -> tratarLoginCliente());
        view.getBotaoEntrarAdmin().addActionListener(e -> tratarLoginAdministrador());

        // TODO: o fluxo de Cadastro pertence à Issue #22 (CadastroController).
        // Por ora, deixamos o botão delegando para uma tela ainda a definir.
        view.getBotaoCadastrar().addActionListener(e -> abrirTelaCadastro());
    }

    /**
     * Fluxo de login do Cliente: lê os campos da View, chama o DAO via
     * autenticarCliente, e decide a navegação.
     */
    private void tratarLoginCliente() {
        String matricula = view.getCampoMatricula().getText();
        char[] senha = view.getCampoSenhaCliente().getPassword();

        try {
            Cliente cliente = usuarioRepository.autenticarCliente(matricula, senha);

            new HomeClienteView(cliente).setVisible(true);
            view.dispose();

        } catch (UsuarioException ex) {
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Erro de autenticação",
                    JOptionPane.ERROR_MESSAGE
            );
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
        char[] senha = view.getCampoSenhaAdmin().getPassword();

        try {
            Administrador admin = usuarioRepository.autenticarAdministrador(senha);

            new HomeAdministradorView(admin).setVisible(true);
            view.dispose();

        } catch (UsuarioException ex) {
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Erro de autenticação",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            java.util.Arrays.fill(senha, '\0');
        }
    }

    private void abrirTelaCadastro() {
        CadastroView cadastroView = new CadastroView();
        new CadastroController(cadastroView);
        cadastroView.setVisible(true);
        view.dispose();
    }
}
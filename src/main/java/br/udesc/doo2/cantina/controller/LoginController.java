package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.dao.UsuarioDAO;
import br.udesc.doo2.cantina.exception.UsuarioException;
import br.udesc.doo2.cantina.model.Administrador;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.repository.UsuarioRepository;
import br.udesc.doo2.cantina.view.LoginView;
// TODO: ajustar os imports abaixo quando as Home Views estiverem prontas
// na pasta correta (administrador/cliente).
import br.udesc.doo2.cantina.view.administrador.HomeAdministradorView;
import br.udesc.doo2.cantina.view.cliente.HomeClienteView;

import javax.swing.JOptionPane;

/**
 * Controller orquestrador do fluxo de Login. Diferente de um Controller
 * "passivo", este registra os Listeners diretamente nos componentes da
 * LoginView (recebida no construtor) e decide todo o fluxo: chamar o DAO,
 * tratar a UsuarioException, abrir a Home correta e fechar a tela de login.
 *
 * A View, portanto, fica "burra": só expõe os componentes (getters) e não
 * conhece HomeClienteView/HomeAdministradorView nem o DAO.
 *
 * CONTRATO ASSUMIDO da LoginView (ajustar nomes conforme implementação real):
 *   - getCampoMatricula()      -> JTextField (aba Cliente)
 *   - getCampoSenhaCliente()   -> JPasswordField (aba Cliente)
 *   - getBotaoEntrarCliente()  -> JButton (aba Cliente)
 *   - getBotaoCadastrar()      -> JButton (aba Cliente)
 *   - getCampoSenhaAdmin()     -> JPasswordField (aba Administrador)
 *   - getBotaoEntrarAdmin()    -> JButton (aba Administrador)
 */
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

    /**
     * Abre a tela de cadastro de Cliente. Implementação completa pertence
     * à Issue #22 - aqui deixamos apenas a navegação básica para não
     * deixar o botão "morto" enquanto a Issue #22 não for desenvolvida.
     */
    private void abrirTelaCadastro() {
        // TODO (Issue #22): substituir por CadastroController quando este
        // existir, ou abrir CadastroView diretamente se o fluxo for simples.
        new br.udesc.doo2.cantina.view.CadastroView().setVisible(true);
        view.dispose();
    }
}
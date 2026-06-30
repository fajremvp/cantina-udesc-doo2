package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.dao.UsuarioDAO;
import br.udesc.doo2.cantina.exception.UsuarioException;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.repository.UsuarioRepository;
import br.udesc.doo2.cantina.util.SenhaUtils;
import br.udesc.doo2.cantina.view.CadastroView;
import br.udesc.doo2.cantina.view.LoginView;

import javax.swing.JOptionPane;
import java.util.Arrays;

public class CadastroController {

    private final CadastroView view;
    private final UsuarioRepository usuarioRepository;

    public CadastroController(CadastroView view) {
        this.view = view;
        this.usuarioRepository = new UsuarioDAO();
        registrarListeners();
    }

    private void registrarListeners() {
        view.getBotaoConfirmar().addActionListener(e -> tratarCadastro());
        view.getBotaoVoltar().addActionListener(e -> voltarParaLogin());
    }

    private void tratarCadastro() {
        String nome      = view.getCampoNome().getText().trim();
        String matricula = view.getCampoMatricula().getText().trim();
        char[] senha     = view.getCampoSenha().getPassword();

        try {
            if (nome.isEmpty() || matricula.isEmpty() || senha.length == 0) {
                throw new UsuarioException("Todos os campos são obrigatórios.");
            }

            // Valida duplicidade primeiro
            if (usuarioRepository.buscarPorMatricula(matricula) != null) {
                 throw new UsuarioException("Matrícula " + matricula + " já está cadastrada.");
            }

            // Faz o Hash na hora, ANTES de mandar para o Cliente
            String hashSenha = SenhaUtils.gerarHash(senha);
            Cliente novoCliente = new Cliente(0, nome, hashSenha, matricula);

            usuarioRepository.salvar(novoCliente);

            JOptionPane.showMessageDialog(view, "Cadastro realizado! Faça login com sua matrícula.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            voltarParaLogin();

        } catch (UsuarioException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(senha, '\0');
        }
    }

    private void voltarParaLogin() {
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
        view.dispose();
    }
}
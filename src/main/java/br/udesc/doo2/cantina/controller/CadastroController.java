package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.dao.UsuarioDAO;
import br.udesc.doo2.cantina.exception.UsuarioException;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.repository.UsuarioRepository;
import br.udesc.doo2.cantina.util.SenhaUtils;
import br.udesc.doo2.cantina.view.CadastroView;
import br.udesc.doo2.cantina.view.LoginView;

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
        view.adicionarAcaoBtnConfirmar(e -> tratarCadastro());
        view.adicionarAcaoBtnVoltar(e -> voltarParaLogin());
    }

    private void tratarCadastro() {
        String nome = view.getNome();
        String matricula = view.getMatricula();
        char[] senha = view.getSenha();

        try {
            if (nome.isEmpty() || matricula.isEmpty() || senha.length == 0) {
                throw new UsuarioException("Todos os campos são obrigatórios.");
            }

            if (usuarioRepository.buscarPorMatricula(matricula) != null) {
                 throw new UsuarioException("Matrícula " + matricula + " já está cadastrada.");
            }

            String hashSenha = SenhaUtils.gerarHash(senha);
            Cliente novoCliente = new Cliente(0, nome, hashSenha, matricula);

            usuarioRepository.salvar(novoCliente);

            view.apresentarMensagem("Cadastro realizado! Faça login com sua matrícula.", "Sucesso");
            voltarParaLogin();

        } catch (UsuarioException ex) {
            view.setMensagemErro(ex.getMessage());
        } finally {
            Arrays.fill(senha, '\0');
        }
    }

    private void voltarParaLogin() {
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
        view.fecharTela();
    }
    
}
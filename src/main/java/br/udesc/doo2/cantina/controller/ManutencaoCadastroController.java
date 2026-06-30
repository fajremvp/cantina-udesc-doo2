package br.udesc.doo2.cantina.controller;

import br.udesc.doo2.cantina.dao.UsuarioDAO;
import br.udesc.doo2.cantina.exception.UsuarioException;
import br.udesc.doo2.cantina.model.Cliente;
import br.udesc.doo2.cantina.model.Usuario;
import br.udesc.doo2.cantina.repository.UsuarioRepository;
import br.udesc.doo2.cantina.util.SenhaUtils;
import br.udesc.doo2.cantina.view.AlterarCadastroView;

import javax.swing.JOptionPane;
import java.util.Arrays;

public class ManutencaoCadastroController {

    private final AlterarCadastroView view;
    private final UsuarioRepository usuarioRepository;

    public ManutencaoCadastroController(AlterarCadastroView view) {
        this.view = view;
        this.usuarioRepository = new UsuarioDAO();
        registrarListeners();
    }

    private void registrarListeners() {
        view.getBotaoSalvar().addActionListener(e -> tratarAtualizacao());
        view.getBotaoVoltar().addActionListener(e -> view.dispose());
    }

    private void tratarAtualizacao() {
        Usuario usuario = view.getUsuarioLogado();
        String nome     = view.getCampoNome().getText().trim();
        char[] senha    = view.getCampoSenha().getPassword();

        try {
            if (nome.isEmpty()) {
                throw new UsuarioException("Nome é obrigatório.");
            }

            usuario.setNome(nome);

            if (usuario instanceof Cliente cliente) {
                String matricula = view.getCampoMatricula().getText().trim();
                if (matricula.isEmpty()) {
                    throw new UsuarioException("Matrícula é obrigatória.");
                }
                cliente.setMatricula(matricula);
            }

            if (senha.length > 0) {
                String hashSenha = SenhaUtils.gerarHash(senha);
                usuario.setSenha(hashSenha);
            }

            usuarioRepository.atualizar(usuario);

            JOptionPane.showMessageDialog(view, "Cadastro atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();

        } catch (UsuarioException ex) {
             JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(senha, '\0');
        }
    }
}
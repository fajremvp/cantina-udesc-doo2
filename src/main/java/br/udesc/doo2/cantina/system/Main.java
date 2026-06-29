package br.udesc.doo2.cantina.system;

import br.udesc.doo2.cantina.controller.LoginController;
import br.udesc.doo2.cantina.view.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}
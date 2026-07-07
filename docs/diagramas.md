# Diagramas de Sequência

## Issue #21 - User Authentication and Initial Navigation
**Autor:** João Vitor Fogaça de Oliveira - @fajremvp

### 1. Login Cliente
![Login Cliente](https://www.plantuml.com/plantuml/png/bLDBRjim4Dtp50Dj8WDD3o2WHH9RG1PssiPDknvJ0ui0Fov9sRbEjWLw2b-i92MPjj0QxORc6E-zUVE6kdCUhU-KXANP2ZsNWhGdveMN10k1MuiAeI5Oqu_k-5DpWN3ROs44C-JUM7Xn7LfX01qKuNvy2qN1Ta5USB53xQ7O2pfKYVmjt8e8ZPTB43VQMoCbsHuwFzM9aCjBMeEc6YmixZybQFYzn7HUo6fDkiMNS7C9b4hMeCSDEe9YWvgRQlrbAJprAW-CZRFTVao6A_XiYJHloO92Ru-_U2Vn7RYe1GHS2euMIC5D7ToYlOaPYJrwwWTcyOngUPeA0j2ZFTz3ESkyZ6GitxEZBVavgfrPeRSYsWdalikaZHe1LsLIBDd67vNIk5Ct8VGAiFE164CO9VgkEQKyMLeJ2qTafxRHXKNxYAujf-04OEc4sx5T6v337cc278KN3nMiwsKz_pg-M_Z-M3_NEGwuVreC6_oG5NFd-0vGUk2M6eeVWd2mFxv9qQ1Z0100sH4VUWHWJ0SRao0DdSi1G4HFSnt9cP5pIlzbB4oOcWxmQ1GD8yQjbOFQR4BDAs-4snb7vHIHV0qllm9w5HkQYZD6qj5594B_DHGT0kqKM7ATz24IA9_CSohgLquxBuoU_KTClhNcC1eyAPJ5wlH5Wkc6ZOwkZ7jVpAu6Eqt7jUQm8kTmImk1qcp_jS7r81NfAAcW8I1h3IFTiFFdc-dd__QXtmLmf-Hl)

<details>
<summary>Ver código fonte (PlantUML)</summary>

```plantuml
@startuml LoginCliente
title Diagrama de Sequência - Login Cliente

actor Usuario as "Usuário"
participant "view:LoginView" as View
participant "controller:LoginController" as Controller
participant "dao:UsuarioDAO" as DAO
participant "util:SenhaUtils" as Senha
database "banco:SQLite" as DB

Usuario -> View: Preencher matrícula, senha e clicar em 'Entrar'
activate View

View -> Controller: tratarLoginCliente()
activate Controller

Controller -> View: getCampoMatricula()
View --> Controller: matricula
Controller -> View: getCampoSenhaCliente()
View --> Controller: senha

Controller -> DAO: autenticarCliente(matricula, senha)
activate DAO

DAO -> Senha: gerarHash(senha)
activate Senha
Senha --> DAO: hashSenha
deactivate Senha

DAO -> DB: SELECT Cliente WHERE matricula AND senha = hashSenha
activate DB

alt credenciais válidas
    DB --> DAO: cliente encontrado
    deactivate DB
    DAO --> Controller: cliente
    deactivate DAO

    Controller -> Controller: new HomeClienteView(cliente)
    Controller -> View: dispose()
    Controller --> Usuario: exibe HomeClienteView

else credenciais inválidas
    DB --> DAO: nenhum resultado (NoResultException)
    deactivate DB
    DAO --> Controller: throw UsuarioException("Matrícula ou senha inválidos.")
    deactivate DAO

    Controller -> View: showMessageDialog("Matrícula ou senha inválidos.")
    Controller --> Usuario: exibe mensagem de erro
end

deactivate Controller
deactivate View

@enduml
```
</details>

### 2. Login Administrador
![Login Administrador](https://www.plantuml.com/plantuml/png/ZL9BRjim4Dtp50Dj8Y_I0mXeaTGMu8NxQToqwx4qi0XGf4jITgwJRQ_XYvKSAQ6fD80tzD1wxysRDxnp7gqVUWKRivVwlksbbivRR8qLNdf5i9AujzWZj0HR-ZES_-f68joE3CWf0XjlB3ow0QqqW0wAK9-VuwLawA8GXz1IDlA0saDnb7IgMEfNg8f8YKKEQepsrYX5TeGkN-zCIDUSrgAf9Ykh-syC3RyvPl1ILLlI7Jw6oZ68hw95Zpjq1CKETMEgxO-Dz3IgV1RYPSRRJsouWk-MI3STMN2H3mIDaWrQe1vkwk0GxKtCHnxHqpYaY6TKI1DK480UxTjzb8l4JdWXKfsyxCaliJyOdeHL0djiDkl6NkSY8QKASF2aVPmWjy6Cv8KZ5U68HEuNsrkqQtJT73n5ooTRuKvTG4wPqmmv2OV4AzZMcthvCtzny7jTFzHJuX-JKkeOTuNAJv3Z-Ld95WK0X2_90cQYf1kZkMPWIvTgJ0skvacoHCAdSFY_BE2Cf-a4QzDJDbVSLScAYnazBRcLxc0SbMyH0J8zpmheIUxe_ms48ELeIaRgzxBHuVlGWoKtA1-uK7ur3tofdnewU6dOvBKv-SwQquk_LuMouBq5ZNmNoTU7Od55kYaUrvdJ5t8EzxIIgCp-cXRNPDcJZg9z529hZI3TYikdc-YN_uw9tmNmqAj_)

<details>
<summary>Ver código fonte (PlantUML)</summary>

```plantuml
@startuml LoginAdministrador
title Diagrama de Sequência - Login Administrador

actor Usuario as "Usuário (Admin)"
participant "view:LoginView" as View
participant "controller:LoginController" as Controller
participant "dao:UsuarioDAO" as DAO
participant "util:SenhaUtils" as Senha
database "banco:SQLite" as DB

Usuario -> View: Preencher senha e clicar em 'Entrar'
activate View

View -> Controller: tratarLoginAdministrador()
activate Controller

Controller -> View: getCampoSenhaAdmin()
View --> Controller: senha

Controller -> DAO: autenticarAdministrador(senha)
activate DAO

DAO -> Senha: gerarHash(senha)
activate Senha
Senha --> DAO: hashSenha
deactivate Senha

DAO -> DB: SELECT Administrador WHERE senha = hashSenha
activate DB

alt senha válida
    DB --> DAO: administrador encontrado
    deactivate DB
    DAO --> Controller: admin
    deactivate DAO

    Controller -> Controller: new HomeAdministradorView(admin)
    Controller -> View: dispose()
    Controller --> Usuario: exibe HomeAdministradorView

else senha inválida
    DB --> DAO: nenhum resultado (NoResultException)
    deactivate DB
    DAO --> Controller: throw UsuarioException("Senha de administrador inválida.")
    deactivate DAO

    Controller -> View: showMessageDialog("Senha de administrador inválida.")
    Controller --> Usuario: exibe mensagem de erro
end

deactivate Controller
deactivate View

@enduml
```
</details>

## Issue #22 - Customer Registration and Profile Management
**Autor:** João Vitor Fogaça de Oliveira - @fajremvp

### 3. Cadastro Cliente
![Cadastro Cliente](https://www.plantuml.com/plantuml/png/dPRFRZet4CVlVeefbzyYaQhdbLgb4AfKofyqaFPO3TufkFNQrFP2bBU9UgXKAQS-mhvORI-xPXE2A8TaRVETZ-VFpCAbTMXSLKgOO87M6JsHWfGZveIJ15S2bmPBX89WHdzMzJ-A2uIBJWwjdY5tsi2JhT083MXXuDVrQzXaIcyqy4Ov7B0r6YUuMADoCDW8skOjxcU_6GJZiEZhk5PU82MPJZtfZg9DslOj2zJvpg-hx--Zr3_xcie9cSz8hV39hsmKnIqhqE42BS5WWOhhVFRJZN3KKCQCjG5VV1Tzpk7142c-8WDtkgGHtA8pzHkl908rIA2G2uu6g8GltklVX2dHV0af51jqr4JFml_0JN7bu0mwD6q2ic6osOkUfNNoQqbkWkLQ1w-yNGD_HrUwfAF68HWHOlcCKBQ2evYOXiyGDgQTeNJ0WzY2NXYnH5V_QuJVRV35FnW0x9bzoDFAw6tRZTDdJcidjCe6Srq4e6tHjlvBz_bV3uQHVSX_kzBRMx8Mb-GdG-hbkK1FtFcM0psB1K59Ac3BC6baZ6QCfAMMkcwQIXI70W_TdCEYiXpDWpQfHbqn6WVQLWdwUE2VqNoSmsnwCvtCssc6NwwdZzDKJFWshT-Xngmva0xArEc_rw_0coR5eZ695uxXelMNxwuY5ISR2zt92jhdjwVHstwP4-HPM1TMVKOADvwVrpAt_R38kbvmRNbFxvbJYMSqJRIBZRDNZ4BOjLRrsuRamKeeKgkg14EsakwyEgXAol-P-lWE2Ay4W-OQxIgBSvyYxnXHs1t7tOVxLnxGrnNK1vpI0OgsxGHatupYst2Kq2D8y_GPBURJejoWYLzwErelgluClfl37-zcqyUvVypleMgA3Lbq9Dq-0jluuyHQ_xfJ7RfYVB3S-eyp2hpHee3CLfoirSFJItry4BfV4OPGYXSiz5Vm0zP_8_YFXGAkIx0LfcO-CcrzRwL3yu06Rm8dEtMUeZfu6_Ma2XR-s5wKoNx_D9gmIo-kIlaV)

<details>
<summary>Ver código fonte (PlantUML)</summary>

```plantuml
@startuml CadastroCliente
title Diagrama de Sequência - Cadastro Cliente

actor Usuario as "Usuário (novo cliente)"
participant "view:CadastroView" as View
participant "controller:CadastroController" as Controller
participant "dao:UsuarioDAO" as DAO
participant "util:SenhaUtils" as Senha
database "banco:SQLite" as DB

Usuario -> View: Preencher Nome, Matrícula e Senha e clicar em 'Confirmar'
activate View

View -> Controller: tratarCadastro()
activate Controller

Controller -> View: getCampoNome()
View --> Controller: nome
Controller -> View: getCampoMatricula()
View --> Controller: matricula
Controller -> View: getCampoSenha()
View --> Controller: senha

alt campos obrigatórios vazios
    Controller -> Controller: throw UsuarioException("Todos os campos são obrigatórios.")
    Controller -> View: showMessageDialog("Todos os campos são obrigatórios.")
    Controller --> Usuario: exibe mensagem de erro

else campos preenchidos
    Controller -> DAO: buscarPorMatricula(matricula)
    activate DAO
    DAO -> DB: SELECT Cliente WHERE matricula = matricula
    activate DB

    alt matrícula já cadastrada
        DB --> DAO: cliente encontrado
        deactivate DB
        DAO --> Controller: cliente existente
        deactivate DAO

        Controller -> Controller: throw UsuarioException("Matrícula já está cadastrada.")
        Controller -> View: showMessageDialog("Matrícula já está cadastrada.")
        Controller --> Usuario: exibe mensagem de erro

    else matrícula disponível
        DB --> DAO: nenhum resultado
        deactivate DB
        DAO --> Controller: null
        deactivate DAO

        Controller -> Senha: gerarHash(senha)
        activate Senha
        Senha --> Controller: hashSenha
        deactivate Senha

        Controller -> Controller: new Cliente(0, nome, hashSenha, matricula)

        Controller -> DAO: salvar(novoCliente)
        activate DAO
        DAO -> DB: INSERT INTO usuario (nome, matricula, senha, tipo_usuario)
        activate DB
        DB --> DAO: ok
        deactivate DB
        DAO --> Controller: void (sucesso)
        deactivate DAO

        Controller -> View: showMessageDialog("Cadastro realizado! Faça login com sua matrícula.")
        Controller -> Controller: voltarParaLogin()
        Controller --> Usuario: exibe LoginView
    end
end

deactivate Controller
deactivate View

@enduml
```
</details>

## Issue #20 - Menu and Meat Option Management (Admin)
**Autor:** Gustavo Hoffmann - @GustavoHoffmann3

### 4. Cadastrar Refeição
![Cadastrar Refeição](https://github.com/fajremvp/cantina-udesc-doo2/blob/main/docs/assets/CadastrarRefeicao.jpg?raw=true)

### 5. Cadastrar Opção de Carne
![Cadastrar Opção de Carne](https://github.com/fajremvp/cantina-udesc-doo2/blob/main/docs/assets/CadastrarOpcaoCarne.jpg?raw=true)

## Issue #23 - Point of Sale - Place an Order (Customer)
**Autor:** Maria Zortea - @maria-zortea

### 6. Realizar Pedido
![Realizar Pedido](https://github.com/fajremvp/cantina-udesc-doo2/blob/main/docs/assets/assets/RealizarPedido.png?raw=true)

<details>
<summary>Ver código fonte (PlantUML)</summary>

```plantuml
@startuml
title Consulta de Pedidos

actor Administrador as Admin
participant HomeAdministradorView as Home
participant ConsultarPedidosView as View
participant PedidoController as Controller
participant PedidoDAO as DAO
participant PedidoTableModel as TableModel
database Banco

Admin -> Home: Clicar em "Consultar Pedidos"
Home -> View: new ConsultarPedidosView()
Home -> DAO: new PedidoDAO()
Home -> Controller: new PedidoController(View, DAO)

Controller -> DAO: buscarTodos()
DAO -> Banco: SELECT pedidos
Banco --> DAO: Lista de pedidos
DAO --> Controller: pedidos

Controller -> Controller: Collections.sort(pedidos)
Controller -> View: apresentarPedidos(pedidos)

== Busca com filtros ==

Admin -> View: Preencher nome ou tipo de consumo
Admin -> View: Clicar em "Realizar Busca"
View -> Controller: Evento do botão
Controller -> Controller: buscarPedidos()

Controller -> View: getNomeCliente()
View --> Controller: nomeCliente

Controller -> View: getTipoConsumoSelecionado()
View --> Controller: tipoConsumo

alt Nenhum filtro preenchido
    Controller -> Controller: throw new PedidoException()
    Controller -> View: apresentarMensagem(\n"preencha algum dos filtros para realizar a busca")

else Nome preenchido
    Controller -> DAO: buscarPorCliente(nomeCliente)
    DAO -> Banco: SELECT pedidos por nome
    Banco --> DAO: pedidos
    DAO --> Controller: pedidos

    Controller -> Controller: Collections.sort(pedidos)
    Controller -> View: apresentarPedidos(pedidos)
    View -> TableModel: new PedidoTableModel(pedidos)

else Tipo de consumo preenchido
    Controller -> DAO: buscarPorTipoConsumo(tipoConsumo)
    DAO -> Banco: SELECT pedidos por tipo
    Banco --> DAO: pedidos
    DAO --> Controller: pedidos

    Controller -> Controller: Collections.sort(pedidos)
    Controller -> View: apresentarPedidos(pedidos)
    View -> TableModel: new PedidoTableModel(pedidos)
end

@enduml
```
</details>

## Issue #24 - Kitchen Dashboard (Admin)
**Autor:** Maria Zortea - @maria-zortea

### 7. Consultar Pedidos
![Consultar Pedidos](https://github.com/fajremvp/cantina-udesc-doo2/blob/main/docs/assets/ConsultarPedidos.png?raw=true)

<details>
<summary>Ver código fonte (PlantUML)</summary>

```plantuml
@startuml
title Realização de Pedido

actor Cliente
participant HomeClienteView as Home
participant RefeicaoDAO as RefeicaoDAO
participant PedidoView as View
participant PedidoController as Controller
participant PedidoDAO as DAO
database Banco

Cliente -> Home: Clicar em "Fazer Pedido"
Home -> RefeicaoDAO: buscarPorData(dataAtual)
RefeicaoDAO -> Banco: Buscar refeição e carnes do dia
Banco --> RefeicaoDAO: Refeição
RefeicaoDAO --> Home: refeicaoDoDia

Home -> Controller: new PedidoController(\nView, DAO, cliente, refeicao)
Controller -> View: iniciarOpcoesCarne(refeicao.getCarnes())
Controller -> View: apresentarTela()

Cliente -> View: Selecionar carne e tipo de consumo
Cliente -> View: Clicar em "Finalizar Pedido"
View -> Controller: finalizarPedido()

Controller -> View: getOpcaoCarneSelecionada()
View --> Controller: nomeCarne

alt Carne não selecionada
    Controller -> View: apresentarMensagem(\n"Preencha a opção de carne")
else Carne selecionada
    Controller -> View: getTipoConsumoSelecionado()
    View --> Controller: tipoConsumo

    alt Tipo de consumo não selecionado
        Controller -> View: apresentarMensagem(\n"Preencha a opção de consumo")
    else Tipo selecionado
        Controller -> DAO: existePorClienteERefeicao(\nclienteId, refeicaoId)
        DAO -> Banco: SELECT COUNT(pedido)
        Banco --> DAO: quantidade
        DAO --> Controller: existe

        alt Cliente já realizou pedido
            Controller -> View: apresentarMensagem(\n"Cliente já realizou um pedido")
        else Pedido permitido
            Controller -> Controller: buscarCarneSelecionada(nomeCarne)
            Controller -> Controller: new Pedido(...)
            Controller -> DAO: salvar(pedido)
            DAO -> Banco: INSERT pedido
            Banco --> DAO: Pedido salvo
            DAO --> Controller: sucesso
            Controller -> View: apresentarMensagem(\n"Pedido realizado...")
            Controller -> View: limparSelecoes()
        end
    end
end

@enduml
```
</details>

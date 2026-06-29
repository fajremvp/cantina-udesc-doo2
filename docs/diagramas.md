# Diagramas de Sequência

## Issue #21 - User Authentication and Initial Navigation
**Autor:** João Vitor Fogaça de Oliveira - @fajremvp

### 1. Login Cliente
![Login Cliente](//www.plantuml.com/plantuml/png/bLDBRjim4Dtp50Dj8WDD3o2WHH9RG1PssiPDknvJ0ui0Fov9sRbEjWLw2b-i92MPjj0QxORc6E-zUVE6kdCUhU-KXANP2ZsNWhGdveMN10k1MuiAeI5Oqu_k-5DpWN3ROs44C-JUM7Xn7LfX01qKuNvy2qN1Ta5USB53xQ7O2pfKYVmjt8e8ZPTB43VQMoCbsHuwFzM9aCjBMeEc6YmixZybQFYzn7HUo6fDkiMNS7C9b4hMeCSDEe9YWvgRQlrbAJprAW-CZRFTVao6A_XiYJHloO92Ru-_U2Vn7RYe1GHS2euMIC5D7ToYlOaPYJrwwWTcyOngUPeA0j2ZFTz3ESkyZ6GitxEZBVavgfrPeRSYsWdalikaZHe1LsLIBDd67vNIk5Ct8VGAiFE164CO9VgkEQKyMLeJ2qTafxRHXKNxYAujf-04OEc4sx5T6v337cc278KN3nMiwsKz_pg-M_Z-M3_NEGwuVreC6_oG5NFd-0vGUk2M6eeVWd2mFxv9qQ1Z0100sH4VUWHWJ0SRao0DdSi1G4HFSnt9cP5pIlzbB4oOcWxmQ1GD8yQjbOFQR4BDAs-4snb7vHIHV0qllm9w5HkQYZD6qj5594B_DHGT0kqKM7ATz24IA9_CSohgLquxBuoU_KTClhNcC1eyAPJ5wlH5Wkc6ZOwkZ7jVpAu6Eqt7jUQm8kTmImk1qcp_jS7r81NfAAcW8I1h3IFTiFFdc-dd__QXtmLmf-Hl)

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
![Login Administrador](//www.plantuml.com/plantuml/png/ZL9BRjim4Dtp50Dj8Y_I0mXeaTGMu8NxQToqwx4qi0XGf4jITgwJRQ_XYvKSAQ6fD80tzD1wxysRDxnp7gqVUWKRivVwlksbbivRR8qLNdf5i9AujzWZj0HR-ZES_-f68joE3CWf0XjlB3ow0QqqW0wAK9-VuwLawA8GXz1IDlA0saDnb7IgMEfNg8f8YKKEQepsrYX5TeGkN-zCIDUSrgAf9Ykh-syC3RyvPl1ILLlI7Jw6oZ68hw95Zpjq1CKETMEgxO-Dz3IgV1RYPSRRJsouWk-MI3STMN2H3mIDaWrQe1vkwk0GxKtCHnxHqpYaY6TKI1DK480UxTjzb8l4JdWXKfsyxCaliJyOdeHL0djiDkl6NkSY8QKASF2aVPmWjy6Cv8KZ5U68HEuNsrkqQtJT73n5ooTRuKvTG4wPqmmv2OV4AzZMcthvCtzny7jTFzHJuX-JKkeOTuNAJv3Z-Ld95WK0X2_90cQYf1kZkMPWIvTgJ0skvacoHCAdSFY_BE2Cf-a4QzDJDbVSLScAYnazBRcLxc0SbMyH0J8zpmheIUxe_ms48ELeIaRgzxBHuVlGWoKtA1-uK7ur3tofdnewU6dOvBKv-SwQquk_LuMouBq5ZNmNoTU7Od55kYaUrvdJ5t8EzxIIgCp-cXRNPDcJZg9z529hZI3TYikdc-YN_uw9tmNmqAj_)

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


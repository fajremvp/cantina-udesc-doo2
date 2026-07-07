# Especificação de Requisitos

Este documento reúne os Requisitos Funcionais (RF), as Regras de Negócio (RN) e os Requisitos Não Funcionais/Técnicos (RNF) do sistema, com base nas Issues #20 a #29 do projeto.

---

## A) Requisitos Funcionais (RF)

### Módulo de Autenticação e Cadastro (Issues #21, #22)

| ID | Descrição |
|----|-----------|
| RF01 | O sistema deve permitir que um Cliente se autentique informando Matrícula e Senha. |
| RF02 | O sistema deve permitir que um Administrador se autentique informando apenas Senha. |
| RF03 | O sistema deve permitir que um novo Cliente se autocadastre informando Nome, Matrícula e Senha. |
| RF04 | O sistema deve permitir que o usuário logado (Cliente ou Administrador) altere seus próprios dados de cadastro. Para Cliente, os campos editáveis são Nome, Matrícula e Senha; para Administrador, apenas Nome e Senha. |
| RF05 | Após autenticação bem-sucedida, o sistema deve direcionar o Cliente para `HomeClienteView` e o Administrador para `HomeAdministradorView`. |

### Módulo de Cardápio (Issue #20)

| ID | Descrição |
|----|-----------|
| RF06 | O sistema deve permitir que o Administrador cadastre a Refeição do dia, informando Data, Descrição, Preço e Capacidade Máxima de pedidos. |
| RF07 | O sistema deve permitir que o Administrador cadastre Opções de Carne (Nome e Quantidade Disponível) para associar à Refeição. |
| RF08 | O sistema deve exibir, na tela do Cliente, a descrição e o preço da Refeição cadastrada para a data atual. |

### Módulo de Pedido (Issue #23)

| ID | Descrição |
|----|-----------|
| RF09 | O sistema deve permitir que o Cliente escolha exatamente uma Opção de Carne dentre as disponíveis na Refeição do dia. |
| RF10 | O sistema deve permitir que o Cliente escolha o Tipo de Consumo do pedido: LOCAL ou LEVAR. |
| RF11 | O sistema deve registrar todo novo pedido com status inicial REALIZADO e a data/hora exata de criação. |

### Módulo de Atendimento (Issue #24)

| ID | Descrição |
|----|-----------|
| RF12 | O sistema deve listar, para o Administrador, todos os pedidos realizados em uma tabela (JTable). |
| RF13 | O sistema deve permitir filtrar a listagem de pedidos por nome do Cliente ou por Tipo de Consumo. |
| RF14 | O sistema deve permitir que o Administrador selecione um pedido na tabela e confirme sua retirada, atualizando o status para CONCLUIDO. |

### Módulo de Feedback (Issues #25, #26)

| ID | Descrição |
|----|-----------|
| RF15 | O sistema deve permitir que o Cliente envie um comentário/avaliação sobre a refeição, composto por uma nota (1 a 5) e um texto livre. |
| RF16 | O sistema deve permitir que o Administrador consulte todos os comentários enviados pelos Clientes, ordenados por data (mais recentes primeiro). |

### Módulo de Relatórios (Issue #27)

| ID | Descrição |
|----|-----------|
| RF17 | O sistema deve calcular e exibir a quantidade de clientes únicos que realizaram pedido em um período. |
| RF18 | O sistema deve calcular e exibir o volume de pedidos agrupado por tipo de consumo e/ou opção de carne. |

---

## B) Regras de Negócio (RN)

| ID | Descrição |
|----|-----------|
| RN01 | Um Cliente não pode realizar mais de um pedido para a mesma Refeição (validado antes de salvar via `existePorClienteERefeicao`). |
| RN02 | O Administrador não possui tela de autocadastro — é inserido diretamente no banco via `database/seed.sql`, pois se assume um único Administrador pré-cadastrado. |
| RN03 | O Administrador não possui o atributo Matrícula; portanto as telas de cadastro/alteração devem ocultar ou desabilitar esse campo quando o usuário logado for Administrador (decisão tomada via polimorfismo/`instanceof`). |
| RN04 | Nome é um campo obrigatório para qualquer Usuario (Cliente ou Administrador); Matrícula é obrigatória apenas para Cliente. |
| RN05 | Não é permitido cadastrar uma Matrícula já existente no banco — deve ser validada a duplicidade antes de persistir um novo Cliente, lançando `UsuarioException`. |
| RN06 | A Capacidade Máxima de uma Refeição deve ser maior que zero. |
| RN07 | As duas Opções de Carne associadas a uma mesma Refeição não podem ter nomes duplicados. |
| RN08 | Um Pedido está sempre associado a exatamente um Cliente, uma Refeição e uma Opção de Carne (nunca uma lista de carnes). |
| RN09 | Um pedido novo é sempre criado com status REALIZADO; a alteração de status para CONCLUIDO só ocorre via confirmação explícita de retirada pelo Administrador. |
| RN10 | Um pedido com status CANCELADO não deve poder ser alterado para CONCLUIDO. |
| RN11 | A nota de um comentário deve estar estritamente entre 1 e 5; valores fora desse intervalo devem ser rejeitados com `ComentarioException`, sem persistir o registro. |
| RN12 | Ordenação padrão de Pedidos na consulta administrativa: por data/hora de criação (mais antigos primeiro), podendo ser refinada por Tipo de Consumo como critério primário e data como critério de desempate. |
| RN13 | Ordenação padrão de Comentários: por data, mais recentes primeiro. |

---

## C) Requisitos Não Funcionais e Técnicos

Requisitos exigidos pelo edital da disciplina (DOO2) e sua aplicação concreta neste projeto:

| Requisito Técnico | Como é atendido no projeto |
|---|---|
| **MVC** | Pacotes `view`, `controller` e `model` estritamente separados. Views (ex.: `PedidoView`, `ConsultarPedidosView`) não contêm lógica de negócio, apenas expõem componentes Swing via getters e métodos de apresentação; toda a decisão fica nos Controllers (ex.: `PedidoController`, `LoginController`). |
| **DAO** | Pacote `dao` contém as implementações concretas (`UsuarioDAO`, `PedidoDAO`, `RefeicaoDAO`, `OpcaoCarneDAO`, `ComentarioDAO`) que executam as operações via `EntityManager`. |
| **Pacotes** | Organização por responsabilidade: `model`, `view` (com subpacotes `cliente`/`administrador`), `controller`, `dao`, `repository`, `exception`, `enums`, `util`, `infra`, `system`. |
| **Modificador de escopo** | Uso de `private`/`protected` para atributos e métodos internos das entidades e controllers (ex.: atributos de `Usuario`, métodos auxiliares como `montarConfirmacao` em `PedidoController`). |
| **Modificador de visibilidade** | Construtores protegidos em entidades JPA (`protected Usuario()`, `protected Cliente()`) exigidos pelo Hibernate, mas expostos publicamente apenas via construtores de negócio. |
| **Interface, classe abstrata, herança e polimorfismo** | `Usuario` é classe abstrata (`@Inheritance(SINGLE_TABLE)`), estendida por `Cliente` e `Administrador`. Interfaces `UsuarioRepository`, `PedidoRepository`, `RefeicaoRepository`, `OpcaoCarneRepository`, `ComentarioRepository` definem os contratos implementados pelos DAOs. Polimorfismo é usado em `AlterarCadastroView`/`ManutencaoCadastroController` (`instanceof Cliente`) para decidir campos exibidos. |
| **Collection Framework — List** | `List<Pedido>` retornado por `PedidoRepository.buscarTodos()`, `buscarPorCliente()`, `buscarPorTipoConsumo()`, ordenado com `Collections.sort()` (usa `Comparable`). |
| **Collection Framework — Map** | Usado no módulo de Relatórios (Issue #27) para agrupar volume de pedidos por Tipo de Consumo/Opção de Carne (`Map<String, Integer>` ou equivalente). |
| **Collection Framework — Set** | `Set<OpcaoCarne>` em `Refeicao.carnes` (relação `@ManyToMany`); também usado no módulo de Relatórios para contar clientes únicos sem duplicidade. |
| **Interfaces gráficas com Swing** | Todas as telas (`view/*`) construídas com `javax.swing.*` (JFrame, JTextField, JPasswordField, JButton, JRadioButton, JComboBox, JTabbedPane). |
| **JTable** | Utilizado em `ConsultarPedidosView` (listagem de pedidos via `PedidoTableModel`), `CadastrarCarnesView` (listagem de opções de carne) e planejado para `ConsultarComentariosView`. |
| **Listeners para tratamento de eventos** | `addActionListener` registrado nos Controllers via expressões lambda (ex.: `view.getBotaoSalvar().addActionListener(e -> tratarAtualizacao())`). |
| **Tratamento de exceções e controle de erros** | Exceções de negócio customizadas por domínio: `UsuarioException`, `PedidoException`, `RefeicaoException`, `OpcaoCarneException`, `ComentarioException`, `DatabaseException`, todas capturadas nos Controllers e reportadas ao usuário via `JOptionPane`, sem propagar crash para a UI. |
| **Classe anônima ou lambda** | Lambdas usadas extensivamente em Listeners (`e -> metodo()`) e na inicialização do `Main`/Controllers. |
| **Interface Comparable / Comparator** | `Pedido implements Comparable<Pedido>` (ordena por `dataHoraCriacao`); `Comentario` receberá `Comparable<Comentario>` (ordenação por data) na Issue #26; `Comparator<Pedido>` com critério composto (Tipo de Consumo + data) planejado para a Issue #24. |
| **Banco de dados (JPA/Hibernate)** | Persistência via JPA com Hibernate 5.6 (`pom.xml`), SQLite como banco (`sqlite-jdbc` + dialect customizado), `EntityManager` obtido de uma factory Singleton (`Conexao`), mapeamento declarado em `persistence.xml` com `hibernate.hbm2ddl.auto=update` para geração/atualização automática do schema. |
| **Build e CI/CD** | Projeto Maven (`pom.xml`, JDK 21). Pipeline no GitHub Actions (`.github/workflows/build.yml`) executa `mvn clean package` a cada Pull Request contra `main`, bloqueando merge em caso de falha de compilação. |
| **Controle de versão e processo** | Git Flow com branch `main` protegida, branches `feat/`/`fix/`, Conventional Commits, commits atômicos e Pull Requests obrigatórios, conforme `CONTRIBUTING.md`. |
| **Segurança de senha** | Senhas nunca armazenadas em texto plano: hash SHA-256 unidirecional via `SenhaUtils`, com limpeza best-effort do array de senha em memória (`Arrays.fill(senha, '\0')`) após uso. |

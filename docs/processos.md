# Atores e Processos de Negócio

Este documento descreve quem utiliza o sistema e como o software automatiza a rotina de reservas e atendimento da cantina, substituindo o fluxo manual baseado em WhatsApp, Google Forms e listas de papel.

---

## 1. Atores do Sistema

O sistema possui dois perfis de usuário, ambos modelados a partir da classe abstrata `Usuario` (herança/polimorfismo), diferenciados por sua forma de autenticação e permissões.

### 1.1 Cliente (Aluno, Professor ou Funcionário)

Representa qualquer membro da comunidade acadêmica que consome as refeições da cantina.

**Identificação:** Matrícula + Senha.

**O que pode fazer no sistema:**
- Autocadastrar-se informando Nome, Matrícula e Senha (`Cliente` é a única subclasse de `Usuario` com autocadastro).
- Autenticar-se (login) usando Matrícula e Senha.
- Consultar a refeição do dia (descrição, preço) na sua tela inicial (`HomeClienteView`).
- Realizar um pedido de almoço, escolhendo **uma** opção de carne (`OpcaoCarne`) e o tipo de consumo (`TipoConsumo.LOCAL` ou `TipoConsumo.LEVAR`).
- Enviar um comentário/avaliação (nota de 1 a 5 + texto livre) sobre a refeição.
- Alterar seu próprio cadastro (Nome, Matrícula e Senha).

### 1.2 Administrador (Equipe da Cantina)

Representa a equipe operacional da cantina, responsável por planejar o cardápio e controlar o atendimento.

**Identificação:** Apenas Senha (perfil pré-cadastrado internamente no sistema, sem fluxo de autocadastro).

**O que pode fazer no sistema:**
- Autenticar-se (login) usando apenas Senha.
- Cadastrar a refeição do dia (`Refeicao`): data, descrição, preço e capacidade máxima de pedidos.
- Cadastrar as opções de carne (`OpcaoCarne`) disponíveis, com nome e quantidade disponível.
- Consultar todos os pedidos realizados, com filtros por nome do cliente ou tipo de consumo.
- Confirmar a retirada de um pedido, alterando seu status de `REALIZADO` para `CONCLUIDO`.
- Consultar os comentários/avaliações enviados pelos clientes.
- Gerar relatórios agregados (clientes únicos que pediram, volume de pedidos por tipo/carne).
- Alterar seu próprio cadastro (Nome e Senha — não possui campo Matrícula).

---

## 2. Processos de Negócio Automatizados

O objetivo central do sistema é ser o **único ponto de contato** entre o aluno e a cozinha, eliminando os gargalos do processo manual descritos no README (dispersão em WhatsApp/Forms, fila e fricção no atendimento, falta de teto de capacidade).

### 2.1 Planejamento do Cardápio (Administrador)

Antes do horário de almoço, o Administrador define o que será servido:

1. Cadastra as `OpcaoCarne` disponíveis (ex.: "Frango", "Carne Bovina"), cada uma com sua quantidade disponível.
2. Cadastra a `Refeicao` do dia, informando data, descrição, preço e `capacidadeMaxima` de pedidos — o teto rígido que faltava no processo manual.
3. Associa duas opções de carne à refeição cadastrada.

Esse processo substitui o "Google Forms enviado tardiamente": o cardápio agora é uma fonte única e estruturada no banco de dados.

### 2.2 Reserva do Almoço (Cliente)

Durante a janela de pedidos, o Cliente acessa o sistema:

1. Faz login com Matrícula e Senha (ou se cadastra, caso seja seu primeiro acesso).
2. Ao acessar a tela inicial, o sistema carrega e exibe automaticamente a descrição e o preço da refeição configurada para o dia atual. Caso nenhuma refeição tenha sido cadastrada para o dia, o botão de pedido é desabilitado.
3. Escolhe uma opção de carne dentre as cadastradas para aquela refeição e define o tipo de consumo (`LOCAL` ou `LEVAR`).
4. Finaliza o pedido. O sistema valida:
   - Se uma opção de carne foi selecionada;
   - Se um tipo de consumo foi selecionado;
   - Se o cliente **ainda não realizou nenhum pedido** para a refeição do dia, evitando reservas duplicadas.
5. O pedido é salvo com status inicial `StatusPedido.REALIZADO` e data/hora de criação.

Esse processo substitui a coleta dispersa por WhatsApp: a reserva passa a ser individual, validada e centralizada no banco.

### 2.3 Atendimento no Balcão (Administrador)

No horário de pico (11:30 às 13:00):

1. O Administrador abre a tela de `ConsultarPedidosView`, que lista todos os pedidos ordenados por data/hora de criação.
2. Pode filtrar a lista por nome do cliente ou por tipo de consumo, agilizando a localização do pedido de quem está no balcão.
3. Ao localizar e selecionar o pedido do aluno na fila, confirma a retirada — o status muda de `REALIZADO` para `CONCLUIDO`.

Esse processo substitui a lista de papel "riscada à caneta": a baixa agora é digital, rastreável e instantânea para toda a equipe.

### 2.4 Avaliação da Refeição (Cliente)

Após consumir a refeição, o Cliente pode registrar feedback:

1. Informa uma nota de 1 a 5 e um comentário textual livre.
2. O sistema valida que a nota está no intervalo permitido antes de persistir.

### 2.5 Gestão e Melhoria Contínua (Administrador)

Com o histórico acumulado, o Administrador utiliza o sistema para decisões de gestão:

1. Consulta os comentários enviados pelos clientes, ordenados por data (mais recentes primeiro).
2. Gera relatórios analíticos: quantidade de clientes únicos atendidos e volume de pedidos agrupado por tipo de consumo e opção de carne escolhida, apoiando a gestão de estoque e compras.

---

## 3. Passo a Passo — Rotina de um Dia Típico

```
Dia anterior        Administrador cadastra a Refeição do dia seguinte e as Opções de Carne disponíveis.
Dia anterior–08:00  Clientes acessam o sistema, consultam a refeição do dia e realizam seus pedidos
                    (escolhendo carne + tipo de consumo).
11:30–13:00         Horário de atendimento: Administrador consulta a fila de pedidos (com filtros),
                    localiza o pedido de cada cliente que chega ao balcão e confirma a retirada
                    (status REALIZADO → CONCLUIDO).
Após o almoço       Clientes que desejarem podem enviar comentário/avaliação sobre a refeição/cantina.
Periodicamente      Administrador consulta comentários e gera relatórios agregados (clientes
                    únicos, volume por tipo de consumo/carne) para planejar os próximos cardápios.
```

Esse fluxo elimina as três dores centrais descritas na Motivação do projeto: **descentralização da coleta**, **fila e fricção no atendimento** e **falta de previsibilidade de capacidade**.

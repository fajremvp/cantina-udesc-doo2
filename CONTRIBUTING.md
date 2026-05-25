# Guia de Contribuição

Para garantirmos a nota máxima e evitarmos conflitos de código na hora da entrega, nós adotamos um fluxo de trabalho profissional baseado em **Pull Requests**. A branch `main` é bloqueada para commits diretos.

Siga o passo a passo abaixo para desenvolver suas funcionalidades.

## 1. Como usar o nosso Kanban (GitHub Projects)
Nosso quadro de tarefas está organizado nas seguintes colunas para facilitar o acompanhamento:
* **Backlog:** Tarefas mapeadas para o escopo do projeto, mas que ainda não estão prontas para desenvolvimento.
* **Ready:** Tarefas detalhadas e prontas para alguém assumir.
* **In progress:** O que estamos codificando ativamente no momento.
* **In review:** Código finalizado aguardando aprovação do líder (Pull Request aberto).
* **Done:** Código aprovado, funcional e mesclado na branch principal (main).

**Passo a passo para pegar uma tarefa:**
1. Acesse a aba **Projects** no nosso GitHub.
2. Escolha um cartão que esteja na coluna `Ready`.
3. Atribua o cartão a você clicando em **Assignees** e mova-o para a coluna `In progress`.

## 2. Fluxo de Desenvolvimento (Git)

Abra o terminal na pasta do projeto e siga esta ordem exata de comandos:

### Passo A: Atualizar seu código com a versão mais recente
Antes de começar qualquer coisa, garanta que você tem o código mais atualizado da nossa branch principal:
```bash
git checkout main
git pull origin main
```

### Passo B: Criar uma nova Branch para a sua funcionalidade
Nunca codifique na `main`. Crie uma "ramificação" para a sua tarefa.
Use `feat/` para novas telas/funcionalidades ou `fix/` para correção de bugs.
```bash
git checkout -b feat/nome-da-sua-funcionalidade
```
*(Exemplo: `git checkout -b feat/tela-login`)*

### Passo C: Codificar
Vá para a sua IDE (Eclipse/IntelliJ/NetBeans) e implemente a funcionalidade seguindo o padrão MVC definido pelo projeto.

### Passo D: Salvar e Enviar para o GitHub (Conventional Commits)
Nós usamos o padrão **Conventional Commits** para as mensagens. Isso mantém nosso histórico legível e facilita muito na hora de montar a apresentação do trabalho. Sempre inicie o seu commit com um dos prefixos abaixo:

* `feat:` Nova funcionalidade ou tela (ex: `feat: cria tela de gerenciamento de cardapio`).
* `fix:` Correção de um bug (ex: `fix: resolve erro ao salvar matricula do aluno`).
* `docs:` Alterações na documentação (ex: `docs: atualiza readme com diagramas`).
* `chore:` Tarefas de configuração ou infraestrutura (ex: `chore: atualiza dependencias do banco de dados`).
* `refactor:` Melhorias no código sem alterar a funcionalidade (ex: `refactor: reorganiza classes nos pacotes MVC`).
* `test:` Adição ou ajuste de testes (ex: `test: adiciona testes para validacao de login`).
* `style:` Mudanças de formatação/código que não afetam lógica (ex: `style: ajusta indentacao das classes DAO`).
* `build:` Alterações no sistema de build ou dependências (ex: `build: adiciona driver JDBC no pom.xml`).
* `ci:` Integração contínua / GitHub Actions (ex: `ci: adiciona workflow de build Java`).

Terminou a tela ou vai parar de programar por hoje? Salve seu progresso:
```bash
# Adiciona os arquivos modificados
git add .

# Cria uma "foto" do momento com uma mensagem clara (Siga o padrão acima!)
git commit -m "feat: adicionado botoes e layout da tela de login"

# Envia a sua branch para o GitHub
git push -u origin feat/nome-da-sua-feature
```

## 2.1 Commits Atômicos (REGRA OBRIGATÓRIA)

Todos os commits devem ser **atômicos**, ou seja, cada commit deve representar **uma única mudança lógica completa**.

### O que isso significa na prática:
- Um commit por funcionalidade pequena
- Um commit por correção de bug
- Não misturar várias tarefas no mesmo commit

### Exemplos CORRETOS:
- feat: cria tela de login
- fix: corrige validação de matrícula
- chore: configura dependências do projeto

### Exemplos ERRADOS:
- feat: cria login + ajusta banco + corrige bug da tela de cardápio
- fix: arruma tudo
- chore: várias mudanças gerais

### Regra de ouro:
Se você não consegue descrever o commit em uma frase simples,
ele provavelmente está grande demais e deve ser dividido.

## 3. Abrindo um Pull Request (PR)
1. Vá até a página inicial do nosso repositório no GitHub.
2. Você verá um aviso amarelo dizendo que sua branch teve commits recentes. Clique no botão verde **Compare & pull request**.
3. Adicione um título claro e uma breve descrição do que você fez.
4. Clique em **Create pull request**.
5. **Mova o cartão da sua tarefa no Kanban para `In review`.**
6. Aguarde a revisão do líder. Se houver algo a ajustar, ele comentará no código. Após aprovado, seu código será integrado à `main`!

## ⚠️ Regras
* **Siga o MVC:** Separe suas Views (telas) dos seus Controllers e Models.
* **Dúvidas?** Chame no grupo do WhatsApp antes de forçar qualquer comando no Git!

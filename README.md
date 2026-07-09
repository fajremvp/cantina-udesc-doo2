# 🍽️ Sistema de Gestão de Reservas - Cantina UDESC

Este projeto foi desenvolvido como Atividade Curricular de Extensão para a disciplina de Desenvolvimento Orientado a Objetos II (DOO2) do curso de Engenharia de Software na UDESC (CEAVI). O sistema visa resolver um problema real de logística e atendimento na comunidade interna da universidade.

Como premissa do grupo, este é um projeto de Código Aberto (Open Source). Isso garante transparência, permite que qualquer pessoa audite ou contribua no futuro, e serve como um excelente portfólio para a nossa equipe.

---

## 📌 Introdução e Contexto

A cantina da universidade atende diariamente dezenas de acadêmicos e professores no período do almoço e lanches noturnos. Para o almoço, há a necessidade de os clientes reservarem suas refeições previamente para que a cozinha possa dimensionar a produção diária.

## 🚀 Motivação (O Problema)

Atualmente, o processo de reserva de almoço é realizado de maneira analógica e descentralizada, gerando gargalos operacionais:
- **Descentralização na Coleta:** As reservas dependem de interações manuais e dispersas (ora por Google Forms enviado tardiamente, ora por listas em grupos de WhatsApp).
- **Fila e Fricção no Atendimento:** No rush do atendimento (11:30 às 13:00), a validação dos pedidos é feita em listas de papel impressas, onde os nomes são checados e riscados à caneta, gerando lentidão e filas.
- **Falta de Previsibilidade:** A ausência de um teto rígido automatizado de capacidade e de horários limites sistêmicos abre margem para pedidos fora do prazo ou falta de comida, além do prejuízo gerado por desistências não gerenciadas.

## 🎯 Objetivo do Sistema

O objetivo central deste software é centralizar e digitalizar o fluxo de ponta a ponta, tornando-se o **único ponto de contato** entre o aluno e a cozinha da cantina. 

O sistema visa substituir o uso de mensagens de WhatsApp, formulários externos e papelada por um ecossistema digital integrado, garantindo rapidez no pedido pelo aluno e um painel de controle eficiente para a administração da cantina.

---

## 📂 Documentação do Sistema

Para manter o projeto organizado, as especificações detalhadas de negócio e requisitos do sistema foram divididas na nossa pasta de documentação. Acesse os links abaixo para ler os detalhes:

- 👥 [Atores e Processos de Negócio](./docs/processos.md) — *Análise de como o software automatiza a rotina da cantina.*
- 📋 [Especificação de Requisitos](./docs/requisitos.md) — *Listagem completa de requisitos funcionais e regras de negócio do sistema.*
- 📊 [Diagramas de Sequência](./docs/diagramas.md) — *Modelagem das interações e fluxos de mensagens do sistema.*

---

## 🗃️ Configuração do Banco de Dados (primeiro uso)

O banco SQLite é criado automaticamente pelo Hibernate na primeira execução.
Após iniciar o sistema pela primeira vez, rode o seed para popular os dados de demonstração:

```bash
# Requer sqlite3 instalado
sqlite3 database/cantina.db < database/seed.sql
```

**Credenciais de demonstração:**
- Administrador — senha: `admin123`
- Cliente (Ana Silva) — matrícula: `2023001`, senha: `senha123`
- Cliente (Bruno Costa) — matrícula: `2023002`, senha: `senha456`
- Cliente (Carla Dias) — matrícula: `2023003`, senha: `senha789`

---

### 👥 Desenvolvedores (Equipe)

- Gustavo Hoffmann
- João Vitor Fogaça de Oliveira
- Maria Eduarda Zanin Zortea
- Ruan Carlos Kestring

# Desafio API - Projeto de Testes Automatizados

Este projeto é uma solução para o desafio de testes de API, implementado em **Java 11**, demonstrando habilidades em **JUnit 5**, **RestAssured**, **Lombok**, geração de relatórios com **Allure**, boas práticas de organização de código em camadas e gestão de recursos de teste.

O objetivo é validar cenários de login, cadastros e operações de produtos, com automação completa, paralelismo e relatórios detalhados.

---

## 🔹 Estrutura do Projeto

### Camadas principais (`src/main/java`)
- **logic** → Contém toda a lógica dos testes e operações (ex.: login, produtos).
- **utils** → Funções utilitárias: leitura de YAML, JSON, asserts customizados, Filtro de requisições para gerar relatórios detalhados no Allure, etc.
- **enum StatusCode** → Mantém todos os códigos de status HTTP e mensagens de retorno padronizadas.

### Recursos de teste (`src/test/resources`)
- **fixture** → Contém todos os arquivos JSON que serão enviados nas requests.
- **environment.yaml** → Configuração de URLs e variáveis de ambiente.
- **logback.xml** → Configuração de logs.
- **junit-platform.properties** → Configuração de execução paralela dos testes.

### Tecnologias
- Java 11
- JUnit 5
- RestAssured
- Lombok
- Maven
- Allure Reports
- Java Faker (geração de dados dinâmicos nos testes)

---

## 🌐 Configuração Nessárias

1. 🌐 Configurando Variáveis de Ambiente no Windows (Interface)
   1️⃣ Abrir as Configurações de Variáveis de Ambiente

Abra o Menu Iniciar e digite Editar as variáveis de ambiente do sistema.

Clique na opção que aparecer.

Na janela Propriedades do Sistema, clique no botão Variáveis de Ambiente....

2️⃣ Configurar JAVA_HOME

Na seção Variáveis do sistema, clique em Novo....

No campo Nome da variável, digite:

JAVA_HOME


No campo Valor da variável, coloque o caminho da instalação do Java 11, por exemplo:

C:\Program Files\Java\jdk-11.0.20


Clique em OK para salvar.

3️⃣ Configurar MAVEN_HOME

Ainda em Variáveis do sistema, clique em Novo....

Nome da variável:

MAVEN_HOME


Valor da variável: caminho da pasta do Maven, por exemplo:

C:\apache-maven-3.9.5


Clique em OK.

4️⃣ Configurar ALLURE_HOME

Clique em Novo... novamente.

Nome da variável:

ALLURE_HOME


Valor da variável: caminho da pasta do Allure CLI, por exemplo:

C:\allure-2.35.1


Clique em OK.

5️⃣ Atualizar a variável PATH

Na seção Variáveis do sistema, localize a variável Path e clique em Editar....

Clique em Novo e adicione os seguintes caminhos (ajuste conforme suas instalações):

%JAVA_HOME%\bin

%MAVEN_HOME%\bin

%ALLURE_HOME%\bin


Clique em OK em todas as janelas para salvar.

6️⃣ Testar as configurações

Abra o Prompt de Comando (cmd).

Execute:

java -version

mvn -version

allure --version


Se cada comando retornar a versão correta, as variáveis estão configuradas.

---

## 🚀 Como executar localmente

2. Clone o repositório:
   ```bash
    git clone https://gitlab.com/LucasPereiraValentim/automated-test-challenge
  
    cd {caminhoDiretorioProjeto}

    Instale dependências:

    mvn clean install

    Execute os testes:

    mvn test

    Gere e visualize o Allure Report:

    allure serve ./allure-results

## 🟢 Pipeline de Testes Automatizados (GitHub Actions)

O projeto possui uma pipeline para executar os testes e gerar os resultados do Allure:

Como executar
   ```bash
      Acesse a aba Actions no repositório.
      Selecione o workflow Automação de Testes.
      Clique em Run workflow e confirme.
      Passos executados
      Checkout do repositório.
      Configuração do JDK 11 e Maven com cache de dependências.
      Limpeza de resultados antigos (allure-results).
      Build e execução dos testes via Maven (mvn clean verify).
      Upload do diretório allure-results como artefato.
      Como acessar o relatório
      Após a execução, vá em Actions → [execução do workflow] → Artifacts → allure-results.
      Baixe o ZIP e extraia em uma pasta local.
      Entre na pasta onde os arquivos .json e .txt estão e rode:

      allure serve .


   É importante rodar allure serve . dentro do diretório onde os arquivos do artefato foram extraídos, para que os gráficos e resultados sejam carregados corretamente.

   O Allure vai gerar o HTML dinamicamente e abrir um servidor local com todos os detalhes dos testes.

---

### ✍️ Desenvolvido por
**Lucas Pereira Valentim** – Testes automatizados, café e código 💻☕

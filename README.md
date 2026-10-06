# 📚 API Biblioteca

API REST desenvolvida com **Java + Spring Boot** para gerenciamento de **clientes** e **livros** de uma biblioteca, com persistência em **SQL Server**.

Projeto desenvolvido para o **Check Point 2 — Microservices and Web Engineering (2º semestre/2026)**.

---

## 🚀 Tecnologias utilizadas

* Java 17
* Spring Boot 4 (Web MVC)
* Spring Data JPA / Hibernate
* **Microsoft SQL Server 2022**
* Flyway (migrações de banco)
* Docker / Docker Compose
* Swagger / OpenAPI (springdoc)

---

## 🗂️ Estrutura do projeto

```
src/main/java/com/github/carolinapacifico/apir_biblioteca
├── Application.java          # classe principal do Spring Boot
├── controller/               # endpoints REST (ClienteController, LivroController)
├── model/                    # entidades JPA (Cliente, Livro) mapeadas para as tabelas
└── repository/               # interfaces Spring Data JPA (JpaRepository)

src/main/resources
├── application.properties      # configuração padrão (local)
├── application-prd.properties  # configuração do profile prd (Docker)
└── db/migration/               # scripts Flyway (V1 clientes, V2 livros)
```

---

## 🗄️ Banco de dados (SQL Server)

### Informações de conexão

| Item | Valor padrão |
|---|---|
| Servidor / host | `localhost` |
| Porta | `1433` |
| Banco (database) | `biblioteca_db` |
| Usuário | `sa` |
| Senha | `Biblioteca@2026` |
| URL JDBC | `jdbc:sqlserver://localhost:1433;databaseName=biblioteca_db;encrypt=true;trustServerCertificate=true` |

### Tabelas

As tabelas são criadas automaticamente pelo **Flyway** na primeira execução da aplicação (`src/main/resources/db/migration`):

| Tabela | Colunas |
|---|---|
| `clientes` | `id` (BIGINT IDENTITY, PK), `nome`, `nome_livro`, `duracao_aluguel`, `telefone` |
| `livros` | `id` (BIGINT IDENTITY, PK), `nome_livro`, `genero`, `autor`, `qtd_paginas` |

> O Hibernate roda com `ddl-auto=validate`: ele apenas confere se as entidades batem com as tabelas. Quem cria o schema é o Flyway.

### Subindo um SQL Server local com Docker

O `docker-compose.yml` do projeto sobe um SQL Server 2022 e já cria o banco `biblioteca_db`:

```bash
docker compose up -d
```

Aguarde ~30 segundos (o container `sqlserver_biblioteca` precisa ficar `healthy`). Para conferir:

```bash
docker compose ps
```

### Usando um SQL Server já existente (remoto ou local)

Basta criar o banco e apontar a aplicação para ele:

```sql
CREATE DATABASE biblioteca_db;
```

A conexão é configurada por variáveis de ambiente (os valores padrão estão em `application.properties`):

| Variável | Padrão | Descrição |
|---|---|---|
| `DB_HOST` | `localhost` (`sqlserver` no profile `prd`) | Host do SQL Server |
| `DB_PORT` | `1433` | Porta do SQL Server |
| `DB_NAME` | `biblioteca_db` | Nome do banco |
| `DB_USER` | `sa` | Usuário |
| `DB_PASSWORD` | `Biblioteca@2026` | Senha |
| `SPRING_PROFILES_ACTIVE` | `default` | Profile (`default` ou `prd`) |

Exemplo (PowerShell):

```powershell
$env:DB_HOST="meu-servidor.database.windows.net"; $env:DB_USER="usuario"; $env:DB_PASSWORD="senha"
.\mvnw.cmd spring-boot:run
```

Exemplo (bash):

```bash
DB_HOST=meu-servidor DB_USER=usuario DB_PASSWORD=senha ./mvnw spring-boot:run
```

---

## ▶️ Executando a aplicação

### Pré-requisitos

* Java 17+
* Docker (para subir o SQL Server) **ou** um SQL Server acessível
* Maven (opcional — o projeto inclui o wrapper `mvnw`)

### Passo a passo

1. Suba o SQL Server:
   ```bash
   docker compose up -d
   ```
2. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run      # Linux/macOS
   .\mvnw.cmd spring-boot:run  # Windows
   ```
3. Acesse o Swagger em **http://localhost:8080/**.

No log deve aparecer `Successfully applied 2 migrations` (primeira execução) e `Started Application`.

---

## 🐳 Executando a aplicação também em Docker (profile `prd`)

```bash
# 1. Subir o SQL Server (cria a rede apir_biblioteca_default)
docker compose up -d

# 2. Gerar a imagem da aplicação
docker build -t apir_biblioteca .

# 3. Rodar a aplicação na mesma rede do SQL Server
docker run -d --name apir-biblioteca --network apir_biblioteca_default \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prd \
  -e DB_HOST=sqlserver \
  -e DB_PASSWORD=Biblioteca@2026 \
  apir_biblioteca
```

> No PowerShell, troque as quebras de linha `\` por acento grave `` ` `` ou rode em uma única linha.

---

## 📚 Endpoints

Base URL: `http://localhost:8080`

Documentação interativa (Swagger UI): `http://localhost:8080/` — especificação OpenAPI: `http://localhost:8080/v3/api-docs`

### 📖 Livros — `/livros`

| Método | Endpoint | Descrição | Resposta |
|---|---|---|---|
| `POST` | `/livros` | Cadastrar livro | `201 Created` |
| `GET` | `/livros` | Listar livros | `200 OK` |
| `GET` | `/livros/{id}` | Buscar livro por ID | `200 OK` / `404` |
| `PUT` | `/livros/{id}` | Atualizar livro | `200 OK` / `404` |
| `DELETE` | `/livros/{id}` | Excluir livro | `204 No Content` / `404` |

Body (POST/PUT):

```json
{
  "nome_livro": "Clean Code",
  "genero": "Tecnologia",
  "autor": "Robert C. Martin",
  "qtd_paginas": "400"
}
```

### 👤 Clientes — `/clientes`

| Método | Endpoint | Descrição | Resposta |
|---|---|---|---|
| `POST` | `/clientes` | Cadastrar cliente | `201 Created` |
| `GET` | `/clientes` | Listar clientes | `200 OK` |
| `GET` | `/clientes/{id}` | Buscar cliente por ID | `200 OK` / `404` |
| `PUT` | `/clientes/{id}` | Atualizar cliente | `200 OK` / `404` |
| `DELETE` | `/clientes/{id}` | Excluir cliente | `204 No Content` / `404` |

Body (POST/PUT):

```json
{
  "nome": "Ricardo",
  "nome_livro": "Clean Code",
  "duracao_aluguel": "7 dias",
  "telefone": "11999999999"
}
```

> O `id` é gerado automaticamente pelo SQL Server (`IDENTITY`) — não precisa ser enviado no POST.

---

## 🧪 Testando a API (curl)

```bash
# Inserir
curl -X POST http://localhost:8080/livros -H "Content-Type: application/json" \
  -d '{"nome_livro":"Clean Code","genero":"Tecnologia","autor":"Robert C. Martin","qtd_paginas":"400"}'

# Consultar
curl http://localhost:8080/livros
curl http://localhost:8080/livros/1

# Alterar
curl -X PUT http://localhost:8080/livros/1 -H "Content-Type: application/json" \
  -d '{"nome_livro":"Clean Code (2a ed.)","genero":"Tecnologia","autor":"Robert C. Martin","qtd_paginas":"464"}'

# Excluir
curl -X DELETE http://localhost:8080/livros/1
```

PowerShell:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" `
  -Body '{"nome":"Ricardo","nome_livro":"Clean Code","duracao_aluguel":"7 dias","telefone":"11999999999"}'
Invoke-RestMethod http://localhost:8080/clientes
```

### Conferindo os dados direto no SQL Server

```bash
docker exec -it sqlserver_biblioteca /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P "Biblioteca@2026" -C -d biblioteca_db \
  -Q "SELECT * FROM livros; SELECT * FROM clientes;"
```

Também é possível conectar pelo **Azure Data Studio**, **SSMS** ou **DBeaver** com os dados da tabela de conexão acima.

---

## 🛑 Parando o ambiente

```bash
docker compose down        # para o SQL Server (mantém os dados)
docker compose down -v     # para e apaga os dados
```

---

## 👨‍💻 Integrantes

| Nome | RM |
|---|---|
| Ricardo Henrique de Almeida Santos | RM557093 |
| Carolina Pacifico | RM555000 |

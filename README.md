# 📚 API Biblioteca

API REST desenvolvida com **Spring Boot** para gerenciamento de **clientes** e **livros** em um sistema de biblioteca.

---

## 🚀 Tecnologias utilizadas

* Java 17
* Spring Boot 4
* Spring Data JPA / Hibernate
* MySQL 8
* Flyway (migrações de banco)
* Docker
* Swagger / OpenAPI (springdoc)

---

## ⚙️ Profiles de execução

A aplicação possui dois profiles:

| Profile | Uso | Banco de dados | Schema |
|---|---|---|---|
| `default` | Desenvolvimento local (IDE / `mvnw`) | `localhost:3306` | `ddl-auto=update` |
| `prd` | Produção / execução via Docker | configurável por variáveis de ambiente | `ddl-auto=validate` — as tabelas **não** são criadas pela aplicação, apenas validadas. Quem cria/atualiza o schema é o **Flyway**, através de migrações versionadas em `src/main/resources/db/migration` |

---

## 🐳 Rodando a partir da imagem publicada no Docker Hub

A imagem oficial da aplicação está publicada em:

**https://hub.docker.com/r/carolinapacifico/apir_biblioteca**

### 1. Baixar a imagem

```bash
docker pull carolinapacifico/apir_biblioteca:latest
```

### 2. Subir um banco MySQL

A aplicação precisa de um MySQL acessível. Suba um container dedicado, na mesma rede Docker que será usada pela aplicação:

```bash
docker network create apir-net

docker run -d --name mysql-biblioteca --network apir-net \
  -e MYSQL_ROOT_PASSWORD=root_pwd \
  -e MYSQL_DATABASE=biblioteca_db \
  -p 3306:3306 \
  mysql:8.4
```

Aguarde alguns segundos até o MySQL terminar de iniciar antes do próximo passo.

### 3. Rodar a aplicação (profile `prd`)

```bash
docker run -d --name apir-biblioteca --network apir-net \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prd \
  -e DB_HOST=mysql-biblioteca \
  -e DB_PORT=3306 \
  -e DB_NAME=biblioteca_db \
  -e DB_USER=root \
  -e DB_PASSWORD=root_pwd \
  carolinapacifico/apir_biblioteca:latest
```

> No PowerShell, troque as quebras de linha `\` por acento grave `` ` ``, ou rode o comando em uma única linha.

O comando acima:
* mapeia a porta **8080** do container para a porta 8080 da máquina;
* define o profile de execução como **`prd`**;
* passa as variáveis de ambiente necessárias para conexão com o banco.

### 4. Variáveis de ambiente

| Variável | Obrigatória | Padrão | Descrição |
|---|---|---|---|
| `SPRING_PROFILES_ACTIVE` | Sim (para produção) | `default` | Profile de execução (`default` ou `prd`) |
| `DB_HOST` | Não | `mysql` | Host do MySQL |
| `DB_PORT` | Não | `3306` | Porta do MySQL |
| `DB_NAME` | Não | `biblioteca_db` | Nome do banco de dados |
| `DB_USER` | Não | `root` | Usuário do banco |
| `DB_PASSWORD` | Não | `root_pwd` | Senha do banco |

### 5. Verificar se subiu corretamente

```bash
docker logs apir-biblioteca
```

Deve aparecer `The following 1 profile is active: "prd"` e, em seguida, `Started Application`.

### 6. Parar e remover os containers

```bash
docker rm -f apir-biblioteca mysql-biblioteca
docker network rm apir-net
```

---

## 📘 Acessando o Swagger / OpenAPI

Com a aplicação rodando (local ou via Docker), acesse no navegador:

```
http://localhost:8080/
```

A UI do Swagger está configurada na raiz da aplicação (`springdoc.swagger-ui.path=/`).

A especificação OpenAPI (JSON) fica disponível em:

```
http://localhost:8080/v3/api-docs
```

---

## 💻 Rodando localmente sem Docker (profile `default`)

### Pré-requisitos
* Java 17
* Maven (ou usar o `mvnw` incluso)

### Passos

1. Suba um MySQL local (pode usar o `docker-compose.yml` do projeto):
   ```bash
   docker-compose up -d
   ```
2. Rode a aplicação (profile `default` já é o padrão):
   ```bash
   ./mvnw spring-boot:run
   ```
   No Windows: `mvnw spring-boot:run`
3. Acesse `http://localhost:8080`.

---

## 🏗️ Build da imagem Docker localmente

Caso queira gerar a imagem você mesmo a partir do código-fonte:

```bash
docker build -t apir_biblioteca .
```

O `Dockerfile` usa build multi-stage: compila o projeto com Maven em uma imagem intermediária e empacota o `.jar` final em uma imagem Java 17 (JRE) enxuta, expondo a porta `8080`.

---

## 📚 Endpoints disponíveis

### 📖 Livros (`/livros`)

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/livros` | Criar livro |
| `GET` | `/livros` | Listar livros |
| `GET` | `/livros/{id}` | Buscar por ID |
| `PUT` | `/livros/{id}` | Atualizar livro |
| `DELETE` | `/livros/{id}` | Deletar livro |

**Body de exemplo (criação):**
```json
{
  "id": 1,
  "nome_livro": "Clean Code",
  "genero": "Tecnologia",
  "autor": "Robert C. Martin",
  "qtd_paginas": "400"
}
```

### 👤 Clientes (`/cliente`)

| Método | Endpoint | Descrição |
|---|---|---|
| `PUT` | `/cliente` | Criar cliente |
| `GET` | `/cliente` | Listar clientes |
| `GET` | `/cliente/{id}` | Buscar por ID |
| `PUT` | `/cliente/{id}` | Atualizar cliente |
| `DELETE` | `/cliente/{id}` | Deletar cliente |

**Body de exemplo (criação):**
```json
{
  "id": 1,
  "nome": "Ricardo",
  "nome_livro": "Clean Code",
  "duracao_aluguel": "7 dias",
  "telefone": "11999999999"
}
```

---

## ⚠️ Observações importantes

* O `id` das entidades **não é auto incrementável**, deve ser informado manualmente.
* O endpoint de criação de cliente utiliza `PUT` (não é o padrão REST mais comum).
* Não há relacionamento entre `Cliente` e `Livro` (está como String).
* Campos aceitam valores nulos.
* No profile `prd`, o schema do banco é gerenciado exclusivamente pelo **Flyway** — a aplicação não cria nem altera tabelas automaticamente.

---

## 💡 Melhorias futuras

* Adicionar validações (`@NotNull`, `@Size`)
* Criar relacionamento entre Cliente e Livro (FK)
* Implementar autenticação
* Padronizar uso de `POST`
* Melhorar tratamento de erros

---

## 👨‍💻 Autor

Carolina Pacifico
Projeto desenvolvido para fins acadêmicos (Checkpoint 1 — Microservices and Web Engineering).

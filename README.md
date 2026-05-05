# 📚 API Biblioteca

API REST desenvolvida com **Spring Boot** para gerenciamento de **clientes** e **livros** em um sistema de biblioteca.

---

## 🚀 Tecnologias utilizadas

* Java 17+
* Spring Boot
* Spring Data JPA
* MySQL
* Docker / Docker Compose
* Swagger (Springdoc OpenAPI)

---

## 📦 Como rodar o projeto (PASSO A PASSO)

### ✅ Pré-requisitos

Antes de começar, você precisa ter instalado:

* Docker
* Docker Compose
* Java 17 (caso queira rodar sem Docker)

---

## 🐳 Subindo o banco de dados com Docker (OBRIGATÓRIO)

Na raiz do projeto, execute:

```bash
docker-compose up -d
```

Esse comando irá:

* Criar e iniciar um container MySQL
* Expor a porta do banco (geralmente 3306)

---

### 📄 Exemplo esperado do `docker-compose.yml`

Caso precise recriar:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8
    container_name: mysql_biblioteca
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: biblioteca
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

---

## ▶️ Rodando a aplicação

### Opção 1 — Pela IDE (recomendado)

* Abra o projeto
* Execute a classe `main` (Spring Boot)

---

### Opção 2 — Via terminal

```bash
./mvnw spring-boot:run
```

ou (Windows):

```bash
mvnw spring-boot:run
```

---

## 🔌 Acessando a aplicação

Após subir:

```bash
http://localhost:8080
```

---

## 📘 Swagger (Documentação da API)

Acesse:

```bash
http://localhost:8080/swagger-ui/index.html
```

---

## 📚 Endpoints disponíveis

---

### 📖 Livros (`/livros`)

#### ➤ Criar livro

```http
POST /livros
```

**Body:**

```json
{
  "id": 1,
  "nome_livro": "Clean Code",
  "genero": "Tecnologia",
  "autor": "Robert C. Martin",
  "qtd_paginas": "400"
}
```

---

#### ➤ Listar livros

```http
GET /livros
```

---

#### ➤ Buscar por ID

```http
GET /livros/{id}
```

---

#### ➤ Atualizar livro

```http
PUT /livros/{id}
```

---

#### ➤ Deletar livro

```http
DELETE /livros/{id}
```

---

### 👤 Clientes (`/cliente`)

#### ➤ Criar cliente

```http
PUT /cliente
```

**Body:**

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

#### ➤ Listar clientes

```http
GET /cliente
```

---

#### ➤ Buscar por ID

```http
GET /cliente/{id}
```

---

#### ➤ Atualizar cliente

```http
PUT /cliente/{id}
```

---

#### ➤ Deletar cliente

```http
DELETE /cliente/{id}
```

---

## 🗄️ Banco de Dados

* Banco: `biblioteca`
* As tabelas são criadas automaticamente via:

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

## ⚠️ Observações importantes

* O `id` das entidades **não é auto incrementável**, deve ser informado manualmente
* O endpoint de criação de cliente utiliza `PUT` (não é o padrão REST mais comum)
* Não há relacionamento entre `Cliente` e `Livro` (está como String)
* Campos aceitam valores nulos

---

## 💡 Melhorias futuras

* Adicionar validações (`@NotNull`, `@Size`)
* Criar relacionamento entre Cliente e Livro (FK)
* Implementar autenticação
* Padronizar uso de `POST`
* Melhorar tratamento de erros

---

## 👨‍💻 Autor

Ricardo Almeida
Projeto desenvolvido para fins de estudo.

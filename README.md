# Documentação da API de Gestão de Empregos

Esta API gerencia empresas e vagas de emprego, oferecendo funcionalidades para criação, atualização, exclusão e busca.

---

## Autenticação

A API utiliza **JWT (JSON Web Token)**.  
Tokens devem ser enviados no cabeçalho `Authorization`:


---

## Endpoints

### 1. Autenticação e Login

#### Login de Empresa
**POST** `/login`

**Corpo da Requisição (LoginRequestDTO):**

| Campo    | Tipo   | Obrigatório | Descrição                  |
|---------|--------|-------------|----------------------------|
| email   | string | Sim         | Email da empresa           |
| password| string | Sim         | Senha da empresa           |

**Respostas:**

| Código | Descrição                                   |
|--------|---------------------------------------------|
| 200 OK | Retorna token JWT e tempo de expiração     |
| 401    | Credenciais inválidas                       |
| 404    | Empresa não encontrada                      |

---

### 2. Gerenciamento de Empresas

#### Criação de Empresa
**POST** `/api/company/make-company`

**Parâmetro de Requisição:**

| Campo            | Tipo   | Obrigatório | Descrição                       |
|-----------------|--------|-------------|---------------------------------|
| codeEmailConfirm | string | Sim         | Código de verificação enviado por email |

**Corpo da Requisição (CompanyDTO):**

| Campo       | Tipo   | Obrigatório | Descrição             |
|------------|--------|-------------|---------------------|
| name       | string | Sim         | Nome da empresa      |
| cnpj       | string | Sim         | CNPJ único           |
| email      | string | Sim         | Email único          |
| password   | string | Sim         | Senha da empresa     |
| phoneNumber| string | Sim         | Telefone único       |
| address    | string | Sim         | Endereço único       |

**Respostas:**

| Código | Descrição                               |
|--------|-----------------------------------------|
| 201    | Empresa criada com sucesso              |
| 409    | Empresa com email já existente          |
| 406    | Código de verificação inválido          |

---

#### Obter Detalhes da Empresa por ID
**GET** `/api/company/find/{id}`

| Parâmetro | Tipo | Descrição            |
|-----------|------|--------------------|
| id        | UUID | ID da empresa       |

**Respostas:**

| Código | Descrição                                   |
|--------|---------------------------------------------|
| 200 OK | Retorna dados da empresa (CompanyDTO)      |
| 401    | Token JWT não corresponde ao ID da empresa |
| 404    | Empresa não encontrada                      |

---

#### Atualizar Dados da Empresa
**PUT** `/api/company/update/{id}`

| Parâmetro | Tipo | Descrição            |
|-----------|------|--------------------|
| id        | UUID | ID da empresa       |

**Corpo da Requisição (CompanyUpdateDTO):**

| Campo       | Tipo   | Obrigatório | Descrição          |
|------------|--------|-------------|------------------|
| name       | string | Não         | Novo nome da empresa |
| email      | string | Não         | Novo email         |
| password   | string | Não         | Nova senha         |
| phoneNumber| string | Não         | Novo telefone      |
| address    | string | Não         | Novo endereço      |

**Respostas:**

| Código | Descrição                                   |
|--------|---------------------------------------------|
| 202    | Atualização aceita                         |
| 401    | Token JWT não corresponde ao ID da empresa |
| 404    | Empresa não encontrada                      |

---

#### Obter Vagas de uma Empresa por Email
**GET** `/api/company/alljobscompany/{email}`

| Parâmetro | Tipo   | Descrição       |
|-----------|--------|----------------|
| email     | string | Email da empresa|

**Respostas:**

| Código | Descrição                              |
|--------|----------------------------------------|
| 200 OK | Retorna HomeDTO com empresa e vagas   |

---

#### Deletar uma Empresa
**DELETE** `/api/company/delete/{id}`

| Parâmetro | Tipo | Descrição       |
|-----------|------|----------------|
| id        | UUID | ID da empresa   |

**Respostas:**

| Código | Descrição                       |
|--------|---------------------------------|
| 204    | Empresa deletada com sucesso    |

---

### 3. Gerenciamento de Vagas

#### Obter Todas as Vagas
**GET** `/api/jobs/alljobs`

| Código | Descrição              |
|--------|----------------------|
| 200 OK | Retorna lista de JobDTO|

---

#### Criar uma Nova Vaga
**POST** `/api/jobs/{empresaId}/vagas`

| Parâmetro   | Tipo | Descrição           |
|-------------|------|-------------------|
| empresaId   | UUID | ID da empresa      |

**Corpo da Requisição (JobDTO):**

| Campo    | Tipo   | Obrigatório | Descrição          |
|----------|--------|-------------|------------------|
| titulo   | string | Sim         | Título da vaga    |
| descricao| string | Sim         | Descrição da vaga |
| valor    | double | Sim         | Salário ou valor  |

**Respostas:**

| Código | Descrição                               |
|--------|-----------------------------------------|
| 201    | Vaga criada com sucesso                 |
| 401    | Token JWT não corresponde ao ID da empresa |
| 404    | Empresa não encontrada                  |

---

#### Atualizar uma Vaga
**PUT** `/api/jobs/{empresaId}/vagas/{idVaga}`

| Parâmetro   | Tipo | Descrição           |
|-------------|------|-------------------|
| empresaId   | UUID | ID da empresa      |
| idVaga      | UUID | ID da vaga         |

**Corpo da Requisição (JobUpdateDTO):**

| Campo     | Tipo   | Obrigatório | Descrição        |
|-----------|--------|-------------|----------------|
| titulo    | string | Não         | Novo título     |
| descricao | string | Não         | Nova descrição  |
| valor     | double | Não         | Novo valor      |

**Respostas:**

| Código | Descrição                               |
|--------|-----------------------------------------|
| 200 OK | Vaga atualizada com sucesso             |
| 401    | Token JWT não corresponde ao ID da empresa |
| 404    | Empresa ou vaga não encontrada          |

---

#### Deletar uma Vaga
**DELETE** `/api/jobs/{empresaId}/vagas/{idVaga}`

| Parâmetro   | Tipo | Descrição           |
|-------------|------|-------------------|
| empresaId   | UUID | ID da empresa      |
| idVaga      | UUID | ID da vaga         |

**Respostas:**

| Código | Descrição                  |
|--------|----------------------------|
| 204    | Vaga deletada com sucesso |

---

### 4. Busca e Navegação

#### Obter Vagas para a Página Inicial
**GET** `/jobs/home`

| Parâmetro | Tipo | Descrição                   |
|-----------|------|----------------------------|
| query     | string | Termo de busca (opcional) |
| page      | int    | Número da página (default: 0) |
| size      | int    | Tamanho da página (default: 1) |
| sort      | string | Propriedade para ordenação (opcional) |

**Respostas:**

| Código | Descrição                       |
|--------|---------------------------------|
| 200 OK | Retorna Page<HomeDTO>           |

---

### 5. Confirmação de Email

#### Enviar Código de Verificação de Email
**POST** `/email-confirm/send-code`

| Parâmetro | Tipo   | Descrição                       |
|-----------|--------|--------------------------------|
| email     | string | Email para envio do código      |

**Respostas:**

| Código | Descrição                        |
|--------|----------------------------------|
| 200 OK | Código enviado com sucesso       |
| 409    | Email já registrado              |

---

#### Verificar Código de Email
**POST** `/email-confirm/verify-code`

| Parâmetro         | Tipo   | Descrição                   |
|------------------|--------|----------------------------|
| email             | string | Email associado ao código  |
| codeEmailConfirm  | string | Código recebido por email  |

**Respostas:**

| Código | Descrição           |
|--------|-------------------|
| 200 OK | Código válido       |
| 401    | Código inválido     |

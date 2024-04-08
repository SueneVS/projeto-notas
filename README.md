# Documentação do Mini Projeto 02 - Sistema de Gerenciamento de Notas


Esta é a documentação do mini projeto - Sistema de Gerenciamento de Notas, desenvolvido como exercício prático, com intuito de aplicar os conhecimentos adquiridos até a décima segunda semana do Módulo Back-End do Curso FullStack oferecido pelo Lab365 e SESI/SENAI.
Aqui você encontrará informações sobre a sua funcionalidade, como os endpoints disponíveis, seus métodos, parâmetros necessários, e exemplos de resposta.

## Base URL

A URL base para todas as solicitações é `http://localhost:8089`.

## Endpoints

### Professores

#### `GET /professores/{id}`

Este endpoint retorna informações de um professor específico.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID do professor.

#### `PUT /professores/{id}`

Este endpoint atualiza as informações de um professor específico.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID do professor.

##### Corpo da Requisição

O corpo da requisição deve conter um objeto JSON com as informações a serem atualizadas, seguindo o seguinte formato:

```json
{
  "nome": "Novo Nome do Professor"
}
```

#### `DELETE /professores/{id}`

Este endpoint exclui um professor específico.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID do professor.

#### `GET /professores`

Este endpoint retorna uma lista de todos os professores.

### Disciplinas

#### `GET /disciplinas/{id}`

Este endpoint retorna informações de uma disciplina específica.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID da disciplina.

#### `PUT /disciplinas/{id}`

Este endpoint atualiza as informações de uma disciplina específica.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID da disciplina.

##### Corpo da Requisição

O corpo da requisição deve conter um objeto JSON com as informações a serem atualizadas, seguindo o seguinte formato:

```json
{
  "nome": "Novo Nome da Disciplina",
  "professorId": 123
}
```

#### `DELETE /disciplinas/{id}`

Este endpoint exclui uma disciplina específica.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID da disciplina.

#### `GET /disciplinas`

Este endpoint retorna uma lista de todas as disciplinas.

### Alunos

#### `GET /alunos/{id}`

Este endpoint retorna informações de um aluno específico.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID do aluno.

#### `PUT /alunos/{id}`

Este endpoint atualiza as informações de um aluno específico.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID do aluno.

##### Corpo da Requisição

O corpo da requisição deve conter um objeto JSON com as informações a serem atualizadas, seguindo o seguinte formato:

```json
{
  "nome": "Novo Nome do Aluno",
  "dataNascimento": "1990-01-01"
}
```

#### `DELETE /alunos/{id}`

Este endpoint exclui um aluno específico.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID do aluno.

#### `GET /alunos`

Este endpoint retorna uma lista de todos os alunos.

### Notas

#### `POST /notas`

Este endpoint cria uma nova nota.

##### Corpo da Requisição

O corpo da requisição deve conter um objeto JSON com as informações da nota, seguindo o seguinte formato:

```json
{
  "nota": 8.5,
  "coeficiente": 2.0,
  "matriculaId": 123
}
```

#### `DELETE /notas/{id}`

Este endpoint exclui uma nota específica.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID da nota.

#### `GET /notas/matriculas/{id}`

Este endpoint retorna todas as notas associadas a uma matrícula específica.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID da matrícula.

### Matrículas

#### `GET /matriculas/{id}`

Este endpoint retorna informações de uma matrícula específica.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID da matrícula.

#### `DELETE /matriculas/{id}`

Este endpoint exclui uma matrícula específica.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID da matrícula.

#### `GET /matriculas/disciplinas/{id}`

Este endpoint retorna todas as matrículas associadas a uma disciplina específica.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID da disciplina.

#### `GET /matriculas/alunos/{id}`

Este endpoint retorna todas as matrículas associadas a um aluno específico.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID do aluno.

#### `GET /matriculas/alunos/{id}/media`

Este endpoint retorna a média das notas de um aluno específico.

##### Parâmetros da URL

- `id` (integer, obrigatório): O ID do aluno.

## Tecnologias utilizadas:
- JAVA
- Spring Boot

## Equipe:
##### SQUAD 5 - Semana 12

- Gabriela Silva
- Leandro da Silveira Dias
- Scheila Stihler
- Suene Souza

# Taskboard API

REST API para gerenciamento de projetos e tarefas, construída com Spring Boot e PostgreSQL.

## Tecnologias

- Java 17
- Spring Boot 4.0.6
- Spring Data JPA + Hibernate
- PostgreSQL (Neon)
- Lombok
- SpringDoc OpenAPI (Swagger UI)

## Configuração

Crie as variáveis de ambiente antes de rodar:

```
DATABASE_USERNAME=seu_usuario
DATABASE_PASSWORD=sua_senha
```

## Executando

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.  
Swagger UI disponível em `http://localhost:8080/swagger-ui.html`.

## Endpoints

### Projects `/v1/projects`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/v1/projects` | Cria um projeto |
| GET | `/v1/projects` | Lista projetos paginados |
| GET | `/v1/projects/{id}` | Busca projeto por ID |
| PUT | `/v1/projects/{id}` | Atualiza projeto |
| DELETE | `/v1/projects/{id}` | Remove projeto |

**Request body (POST / PUT):**
```json
{
  "name": "Meu Projeto",
  "description": "Descrição do projeto"
}
```

---

### Tasks `/v1/tasks`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/v1/tasks` | Cria uma tarefa |
| GET | `/v1/tasks` | Lista tarefas paginadas |
| GET | `/v1/tasks/project/{projectId}` | Lista tarefas de um projeto |
| PUT | `/v1/tasks/{id}` | Atualiza tarefa |
| DELETE | `/v1/tasks/{id}` | Remove tarefa |

**Request body (POST / PUT):**
```json
{
  "name": "Minha Tarefa",
  "description": "Descrição da tarefa",
  "priority": "MEDIUM",
  "status": "TODO",
  "projectId": "uuid-do-projeto"
}
```

**Enums:**
- `priority`: `LOW`, `MEDIUM`, `HIGH`
- `status`: `TODO`, `IN_PROGRESS`, `DONE`

---

### Paginação

Todos os endpoints de listagem aceitam os query params:

| Param | Padrão | Exemplo |
|-------|--------|---------|
| `page` | `0` | `?page=0` |
| `size` | `10` | `?size=20` |
| `sort` | — | `?sort=name,asc` |

## Coleção Bruno

Importe a pasta `bruno/` no Bruno para acessar todas as requisições prontas. Selecione o environment **local** para usar `http://localhost:8080` como base URL.

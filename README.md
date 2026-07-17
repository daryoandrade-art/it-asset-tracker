# IT Asset Tracker

API REST para inventário e controle de ciclo de vida de ativos de TI (notebooks, desktops, impressoras, equipamentos de rede, PDVs), construída em Java + Spring Boot como projeto de portfólio.

Nasceu da experiência real com gestão de ativos, atribuição por filial e manutenção corretiva/preventiva em ambiente corporativo de suporte técnico e redes.

## Stack

- Java 17
- Spring Boot / Spring Web
- Spring Data JPA (Hibernate)
- PostgreSQL
- Flyway (versionamento de schema desde o início)
- BCrypt (`spring-security-crypto`) para hash de senha
- JWT (`auth0/java-jwt`) para autenticação
- Thymeleaf (painel administrativo interno, somente leitura)
- `spring-dotenv` para variáveis de ambiente
- Docker (PostgreSQL)
- Maven

## Domínio

| Entidade | Descrição |
|---|---|
| `Site` | Filial/local físico onde os ativos ficam alocados |
| `Asset` | Ativo de TI (notebook, desktop, impressora, equipamento de rede, PDV etc.) |
| `AssetAssignment` | Histórico de atribuição de um ativo a um site/responsável |
| `MaintenanceRecord` | Registro de manutenção preventiva/corretiva de um ativo |

### Regras de negócio principais

- Um ativo nunca pode ter duas atribuições ativas simultâneas — atribuir a um novo site/responsável fecha automaticamente a atribuição anterior.
- Abrir um registro de manutenção muda o status do ativo para `EM_MANUTENCAO` automaticamente; fechar a manutenção retorna o ativo ao status anterior.

## Como rodar o projeto

### Pré-requisitos
- JDK 17 ou superior
- Docker (para o PostgreSQL)
- Maven (ou `./mvnw` incluso no projeto)

### 1. Subir o banco de dados

```bash
docker run -d --name asset-tracker-db -e POSTGRES_PASSWORD=suasenha -p 5432:5432 postgres:17
```

### 2. Configurar variáveis de ambiente

Crie um `.env` na raiz do projeto:

```
DB_URL=jdbc:postgresql://localhost:5432/db_asset_tracker
DB_USER=postgres
DB_PASSWORD=suasenha
JWT_SECRET=uma-chave-secreta-longa-e-aleatoria
```

### 3. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

As migrations do Flyway rodam automaticamente na subida da aplicação.

## Decisões técnicas

- **Schema versionado via Flyway desde o primeiro commit** — nenhuma tabela é criada via `ddl-auto`.
- **DTOs de entrada e saída separados das entidades JPA**, evitando mass assignment e vazamento de dados sensíveis.
- **Autenticação via JWT**, com verificação de posse do recurso (ownership) nos endpoints de escrita — usuário autenticado só pode alterar dados que ele tem permissão sobre.
- **Tratamento de erro centralizado** via `@RestControllerAdvice`, respostas no padrão RFC 7807 (`ProblemDetail`).
- **Constraint de unicidade no banco** para impedir atribuição ativa duplicada de um mesmo ativo, além da validação em nível de aplicação.

## Status do projeto

- [ ] Modelagem inicial (`Site`, `Asset`)
- [ ] Migrations Flyway iniciais
- [ ] CRUD de `Site`
- [ ] CRUD de `Asset`
- [ ] `AssetAssignment` com regra de fechamento automático da atribuição anterior
- [ ] `MaintenanceRecord` com atualização automática de status do ativo
- [ ] Autenticação JWT
- [ ] Autorização por ownership nos endpoints de escrita
- [ ] Painel Thymeleaf (somente leitura): lista de ativos, alertas de manutenção prolongada
- [ ] Testes automatizados
- [ ] Documentação OpenAPI/Swagger

> Projeto em desenvolvimento — commits frequentes conforme o roadmap acima é implementado.
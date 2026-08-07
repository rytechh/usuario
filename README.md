# 🛠️usuarios — Task Scheduler Ecosystem

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)

Microsserviço responsável pelo domínio cadastral de usuários, endereços e telefones dentro da arquitetura do **Task Scheduler Ecosystem**.

> 💡 **Nota:** Este serviço é um componente interno do ecossistema. Para a documentação completa da API e orquestração do ecossistema, acesse o [BFF Agendador de Tarefas](https://github.com/rytechh/bff-agendador-tarefas).

---

## 📌 Responsabilidades do Servico
* Cadastro e atualização de usuários, endereços e telefones.
* Validação de duplicidade cadastral.
* Consumo síncrono da API do **ViaCEP** para autocompletar dados de endereço.
* Persistência relacional e garantia de consistência via `@Transactional`.

---

## 🛠️ Tech Stack & Infraestrutura
* **Linguagem/Framework:** Java 21 / Spring Boot 3.x (Spring Data JPA, Spring Web)
* **Banco de Dados:** PostgreSQL
* **Porta Padrão de Execução:** `8080`

---

## ⚙️ Variáveis de Ambiente Recomendadas

| Variável | Descrição | Valor Padrão (Local) |
| :--- | :--- | :--- |
| `SERVER_PORT` | Porta de execução do serviço | `8080` |
| `SPRING_DATASOURCE_URL` | URL de conexão PostgreSQL | `jdbc:postgresql://localhost:5432/db_usuario` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco de dados | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco de dados | `postgres` |

---

## 👤 Autor
Desenvolvido por **Raian Santos** — [@rytechh](https://github.com/rytechh)

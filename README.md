# Teste Técnico Velsis

## Como rodar

Antes de subir a aplicação, crie o arquivo `.env` na raiz com base no `.env.example`.

Depois suba o PostgreSQL com o `docker-compose.yml`:

```
docker compose up -d
```

Em seguida, rode o backend em `backend` e o frontend em `frontend`:

```
cd backend
mvn spring-boot:run
```

```
cd frontend
npm install
npm run dev
```

## Testes

Os testes estão no diretório `backend/src/test`.

Foram selecionados testes relacionados à criação de usuário, cobrindo cenários com dados inválidos e validações de entrada.

## Projeto

A aplicação segue uma arquitetura em camadas.

O backend usa JWT para autenticação, neste caso com token fixo.

Para paginação foi usado `Pageable` e, para filtros, `Criteria API` com specifications.

O frontend foi feito em ReactJS com Vite, como uma SPA para consumir a aplicação. Tal stack foi escolhida por permitir buscar os usuários pelo nome sem necessidade de recarregar a página, e por ser de fácil manutenção e escalabilidade.

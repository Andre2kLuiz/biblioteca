📚 Documentação da API
Esta aplicação expõe uma API RESTful para gerenciar um sistema de biblioteca, incluindo operações com livros, alunos e empréstimos.
A documentação da API é gerada automaticamente com o Swagger (OpenAPI) e está disponível assim que a aplicação é iniciada.

🔗 Acessar a documentação Swagger
Após iniciar o projeto, você pode visualizar a documentação interativa da API acessando o seguinte endereço no navegador:

http://localhost:8080/swagger-ui.html

ou

http://localhost:8080/swagger-ui/index.html

📌 Importante: Certifique-se de que a aplicação está rodando na porta 8080, ou ajuste conforme sua configuração.

📄 Documentação OpenAPI em JSON
Você também pode acessar diretamente o arquivo de especificação OpenAPI (em formato JSON):

http://localhost:8080/v3/api-docs

💡 Recursos disponíveis na documentação:

📘 CRUD de Livros

🎓 Cadastro de Alunos

📖 Empréstimos e Devoluções

🔍 Filtros por título, autor, disponibilidade e mais

⚠️ Respostas de erro com mensagens claras

=================================================================================================================================

🚀 Como Rodar a API com Docker
📦 Requisitos

Docker instalado

(Opcional) Docker Compose

Compile seu projeto Java primeiro, para gerar o .jar:
Se usa Maven, no terminal da raiz do projeto:

mvn clean package

Isso vai gerar o arquivo target/biblioteca-0.0.1-SNAPSHOT.jar (ou com o nome certo, conforme seu pom.xml).

🛠️ Construindo e Executando a API
🔁 Usando Docker Compose (recomendado)
Este projeto já vem configurado com docker-compose.yml para rodar a API e o banco MongoDB juntos.

# Build e start
docker-compose up -d --build

Isso irá:

Buildar a imagem da API Java com Maven
Subir um container MongoDB com usuário e senha
Iniciar a aplicação Spring Boot na porta 8080

A aplicação estará disponível em:

http://localhost:8080

🧪 Testando a Documentação Swagger
Após a aplicação iniciar, acesse:

http://localhost:8080/swagger-ui/index.html

Para visualizar a documentação automática dos endpoints gerada com o Swagger / OpenAPI.

🔧 Rodando sem Docker Compose (apenas a API)
Se você já tem um MongoDB local rodando:

docker build -t biblioteca-api .
docker run -p 8080:8080 biblioteca-api


🧹 Parando e limpando os containers

docker-compose down


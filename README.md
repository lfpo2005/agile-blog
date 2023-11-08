# Sistema de Microserviços com Spring e Node

Este projeto é uma transformação de uma arquitetura monolítica (MVC) para uma arquitetura de microserviços baseada na Arquitetura Hexagonal, que utiliza Spring Boot, Spring Security e NestJS com Kafka para mensageria. Este estilo arquitetural promove a separação de preocupações, permitindo a criação de componentes de software com alta coesão e baixo acoplamento. A arquitetura é projetada para escalabilidade e segurança robusta com o uso de JWT para autenticação.

Na branch "monolith-old" é possível ver como era o sistema monotilo.

## Arquitetura

A arquitetura consiste nos seguintes microserviços:

- **API Gateway (Spring)**: Ponto de entrada para todas as requisições, implementando Spring Security e JWT para autenticação e autorização.
- **MS-USER (Spring + PostgreSQL)**: Gerenciamento de usuários e autenticação.
- **MS-QUIZ (Spring + PostgreSQL)**: Criação e gerenciamento de quizzes.
- **MS-BLOG (Spring + PostgreSQL)**: Publicação e gerenciamento de conteúdo do blog.
- **MS-PAYMENT (Spring + PostgreSQL)**: Processamento de transações de pagamento.
- **MS-EMAIL (Spring + PostgreSQL)**: Envio de e-mails transacionais e de marketing.
- **MS-FORUM (NestJS + MongoDB)**: Espaço para discussões e troca de informações entre usuários.
- **MS-NOTIFICATION (NestJS + MongoDB)**: Sistema de notificações para alertar usuários sobre eventos importantes.

Todos os serviços são orquestrados e se comunicam através de mensagens do Kafka.

## Desenho da arquitera

<img height="500" src="D:\blog\img\blog.png" width="700"/>  

## Recursos

- **Mensageria com Kafka**: Desacoplamento dos serviços através de mensagens assíncronas.
- **Serviços de Configuração (Spring Cloud Config)**: Centralização e gerenciamento de configurações externas.
- **JWT (Json Web Token)**: Autenticação e autorização seguras entre os serviços.
- **Boas Práticas de Datas nas APIs**: Utilização do padrão ISO 8601 UTC para todas as datas e horários.
- **Filtros Avançados em APIs com Specification**: Busca avançada e dinâmica com filtros, relacionamentos e paginação.

## Tecnologias Utilizadas

Este projeto incorpora uma variedade de tecnologias modernas para garantir uma arquitetura robusta, segura e escalável:

- Java JDK 17
- Node.js 12+
- Docker e Docker Compose
- Kafka
- MongoDB
- PostgreSQL
- Maven
- Gradle
- Spring
- JWT
- Mulesoft
- Salesforce
- AWS
- Angular(front end)

Essas tecnologias foram selecionadas para atender os requisitos de performance, segurança e escalabilidade do sistema.


## Configuração e Instalação

Para inicializar, é necessário ter instalado na máquina o Docker, Maven e Java JDK 17.

## Executando os Serviços

As instruções passo a passo serão adicionadas conforme o desenvolvimento de cada serviço.

## Testando a Aplicação

Instruções sobre como testar a aplicação e os endpoints disponíveis serão fornecidas.

## Contribuições

Este projeto é para estudo. Se quiser contribuir, por favor entre em contato através do GitHub.

## Licença

Este projeto está disponível sob uma licença livre para uso e estudo.

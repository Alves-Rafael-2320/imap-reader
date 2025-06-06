# 📬 Gmail IMAP Reader

Leitor de e-mails via IMAP.
Conecta-se à caixa de entrada do Gmail e exibe os assuntos dos 10 e-mails mais recentes.

## ⚙️ Tecnologias
- Java 21 + Spring Boot 3.5
- Jakarta Mail (IMAPS - porta 993)
- API REST

## 🔐 Requisitos
- Conta Gmail com verificação em duas etapas
- Senha de app gerada em [myaccount.google.com](https://myaccount.google.com)
- IMAP ativado nas configurações do Gmail

## ▶️ Uso

1. Execute o projeto:
2. Faça a requisição:
- POST /api/gmail/read?email=seu_email@gmail.com&appPassword=sua_senha_de_app

⚠️ Aviso
   Use apenas localmente. Nunca exponha senhas em produção.



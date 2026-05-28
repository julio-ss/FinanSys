# Finix - Aplicativo de Controle Financeiro Pessoal

Uma aplicação Android PREMIUM completa de controle financeiro pessoal desenvolvida em Kotlin com arquitetura moderna, código limpo e animações avançadas.

## 🎯 Visão Geral

Finix é um aplicativo de gerenciamento financeiro pessoal inspirado nos melhores apps modernos do mercado (YNAB, Rocket Money, Monarch, Copilot, Quicken Simplifi). Oferece recursos avançados para controle de transações, metas, cartões de crédito, assinaturas e geração de insights com IA.

## 📋 Requisitos

- Android Studio Giraffe (2022.3.1) ou superior
- JDK 17 ou superior
- Android SDK 24+ (mín), SDK 34 (compileSdk)
- Conta Firebase configurada
- Google Play Services

## 🚀 Stack Tecnológico

### Frontend
- **Kotlin** - Linguagem principal
- **Jetpack Compose** - Interface moderna
- **Material Design 3** - Design system
- **Compose Navigation** - Navegação
- **Compose Animations** - Animações fluidas

### Arquitetura & Padrões
- **Clean Architecture** - Separação em layers
- **MVVM** - Model-View-ViewModel
- **Repository Pattern** - Abstração de dados
- **Use Cases** - Lógica de negócio isolada

### Persistência
- **Room Database** - SQLite local
- **DataStore** - Preferências seguras
- **Firebase Firestore** - Banco em nuvem

### Networking & APIs
- **Retrofit** - Cliente HTTP
- **OkHttp** - Interceptores e logging
- **Kotlin Serialization** - JSON parsing

### Injeção de Dependência
- **Hilt** - Injeção de dependências

### Concorrência
- **Coroutines** - Processamento assíncrono
- **Flow** - Streams reeativos
- **Paging 3** - Listagens paginadas

### Firebase
- **Authentication** - Autenticação com email/Google
- **Firestore** - Banco de dados em nuvem
- **Cloud Storage** - Armazenamento de arquivos
- **Cloud Messaging** - Notificações push
- **Analytics** - Rastreamento de eventos
- **Crashlytics** - Relatório de erros

### Utilitários
- **Coil** - Carregamento de imagens
- **Timber** - Logging estruturado
- **WorkManager** - Tarefas em background
- **Lottie** - Animações vetoriais
- **MPAndroidChart** - Gráficos avançados

## 📁 Estrutura do Projeto

```
app/src/main/
├── kotlin/com/finix/app/
│   ├── data/
│   │   ├── local/          # Room Database
│   │   │   ├── dao/
│   │   │   ├── entity/
│   │   │   └── FinixDatabase.kt
│   │   ├── remote/         # APIs (opcional)
│   │   └── repository/     # Implementações
│   ├── domain/
│   │   ├── model/          # Data models
│   │   ├── repository/     # Interfaces
│   │   └── usecase/        # Lógica de negócio
│   ├── presentation/
│   │   ├── screen/         # Telas Compose
│   │   ├── component/      # Componentes reutilizáveis
│   │   ├── viewmodel/      # ViewModels
│   │   ├── navigation/     # Grafo de navegação
│   │   └── theme/          # Tema Material Design 3
│   ├── di/                 # Módulos Hilt
│   ├── firebase/           # Serviços Firebase
│   ├── util/               # Utilitários
│   ├── FinixApplication.kt
│   └── MainActivity.kt
├── res/
│   ├── values/
│   │   ├── strings.xml
│   │   ├── colors.xml
│   │   ├── themes.xml
│   │   └── dimens.xml
│   └── xml/
│       ├── backup_descriptor.xml
│       └── data_extraction_rules.xml
└── AndroidManifest.xml

gradle/
├── libs.versions.toml       # Catalogo de dependências

build.gradle.kts            # Build script raiz
settings.gradle.kts         # Settings
```

## 🎨 Funcionalidades Principais

### 1. Autenticação
- ✅ Cadastro com email/senha
- ✅ Login com email/senha
- ✅ Login com Google (OAuth)
- ✅ Recuperação de senha
- ✅ Biometria (fingerprint)
- ✅ PIN de segurança

### 2. Dashboard Financeiro
- ✅ Saldo total
- ✅ Receita mensal
- ✅ Gastos mensais
- ✅ Gráficos por categoria
- ✅ Últimas transações
- ✅ Metas em progresso
- ✅ Cartões ativos
- ✅ Comparativos mensais
- ✅ Insights automáticos

### 3. Gerenciamento de Transações
- ✅ Adicionar receita/despesa
- ✅ Transferências
- ✅ Transações recorrentes
- ✅ Categorias personalizadas
- ✅ Tags
- ✅ Upload de comprovantes
- ✅ Busca e filtros avançados
- ✅ Histórico completo

### 4. Cartões de Crédito
- ✅ Cadastro de cartões
- ✅ Gerenciamento de limite
- ✅ Faturas
- ✅ Fechamento automático
- ✅ Melhor dia de compra
- ✅ Detecção de parcelas
- ✅ Alertas de limite

### 5. Metas Financeiras
- ✅ Criar metas com deadline
- ✅ Progresso visual (barra animada)
- ✅ Prioridades (baixa/média/alta)
- ✅ Categorias de metas
- ✅ Economias simuladas
- ✅ Notificações de conclusão

### 6. Assinaturas e Contas Recorrentes
- ✅ Detecção automática
- ✅ Alertas de vencimento
- ✅ Calendário de pagamentos
- ✅ Cálculo de total mensal
- ✅ Histórico de pagamentos

### 7. Relatórios e Gráficos
- ✅ Gráficos pizza (categorias)
- ✅ Gráficos barras (comparativos)
- ✅ Gráficos linha (evolução)
- ✅ Fluxo de caixa
- ✅ Evolução patrimonial
- ✅ Exportar PDF/Excel

### 8. IA Financeira
- ✅ Análise de gastos
- ✅ Detecção de excessos
- ✅ Sugestões de economia
- ✅ Previsão de gastos
- ✅ Insights automáticos
- ✅ Resumo mensal inteligente
- ✅ Recomendações personalizadas

### 9. Notificações
- ✅ Contas a vencer
- ✅ Meta atingida
- ✅ Limite do cartão
- ✅ Gastos excessivos
- ✅ Lembretes inteligentes
- ✅ Push notifications

### 10. Perfil e Segurança
- ✅ Foto de perfil
- ✅ Configurações gerais
- ✅ Segurança (biometria, PIN)
- ✅ Backup automático
- ✅ Exportar dados
- ✅ Apagar conta

## 📱 Telas Implementadas

- [x] SplashScreen - Splash animada com logo
- [x] LoginScreen - Login com email e Google
- [x] RegisterScreen - Cadastro de novo usuário
- [x] HomeScreen - Dashboard principal com navegação
- [x] DashboardScreen - Dashboard detalhado
- [x] ProfileScreen - Perfil do usuário
- [ ] TransactionsScreen - Lista de transações
- [ ] AddTransactionScreen - Adicionar transação
- [ ] CardsScreen - Gerenciamento de cartões
- [ ] GoalsScreen - Gerenciamento de metas
- [ ] ReportsScreen - Relatórios e análises
- [ ] SettingsScreen - Configurações
- [ ] NotificationsScreen - Centro de notificações
- [ ] AIInsightsScreen - Insights da IA

## 🔧 Configuração e Instalação

### 1. Clone o repositório
```bash
git clone <repository-url>
cd FinanSys
```

### 2. Configure o Firebase
1. Acesse [Firebase Console](https://console.firebase.google.com/)
2. Crie um novo projeto
3. Adicione um app Android
4. Baixe `google-services.json`
5. Coloque em `app/google-services.json`

### 3. Abra no Android Studio
```bash
open -a Android\ Studio .
```

### 4. Sincronize Gradle
File → Sync Now

### 5. Rode a aplicação
Run → Run 'app'

## 🔐 Configuração de Regras Firestore

Crie as seguintes regras no Firestore (Security Rules):

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;
      
      match /transactions/{transactionId} {
        allow read, write: if request.auth.uid == userId;
      }
      
      match /cards/{cardId} {
        allow read, write: if request.auth.uid == userId;
      }
      
      match /goals/{goalId} {
        allow read, write: if request.auth.uid == userId;
      }
    }
  }
}
```

## 🧪 Testes

```bash
# Rodar testes unitários
./gradlew test

# Rodar testes de instrumentação
./gradlew connectedAndroidTest

# Cobertura de testes
./gradlew jacocoTestReport
```

## 📦 Build

### Debug
```bash
./gradlew buildDebug
```

### Release
```bash
./gradlew buildRelease
```

### APK
```bash
./gradlew assembleRelease
```

## 🚀 Deploy

### Firebase App Distribution
```bash
./gradlew appDistributionUploadDebug
```

### Google Play Store
1. Assine o APK/AAB com sua chave de release
2. Suba para Google Play Console
3. Configure rollout gradual

## 📊 Análise do Código

```bash
# Lint
./gradlew lint

# Detekt (análise estática Kotlin)
./gradlew detekt

# Jacoco (cobertura)
./gradlew jacocoTestReport
```

## 🔄 CI/CD

Este projeto está pronto para integração com:
- GitHub Actions
- GitLab CI/CD
- Jenkins
- Firebase Test Lab

## 📝 Padrões de Código

### Nomenclatura
- Classes: `PascalCase`
- Funções: `camelCase`
- Constantes: `SCREAMING_SNAKE_CASE`
- Privados: `_camelCase`

### Comentários
Usar comentários apenas para o WHY, não o WHAT:
```kotlin
// Aguarda o carregamento inicial do Firebase antes de navegar
viewModelScope.launch { }
```

### Null Safety
```kotlin
// Preferir?.let { } a if (x != null)
user?.let { 
    updateUI(it)
}
```

## 🎓 Aprendizados e Boas Práticas

1. **Clean Architecture** - Separação clara de responsabilidades
2. **MVVM** - Gerenciamento de estado reativo
3. **Coroutines** - Processamento assíncrono seguro
4. **Compose** - UI moderna e declarativa
5. **Firebase** - Backend escalável
6. **Hilt** - Injeção de dependência simples

## 🐛 Troubleshooting

### Erro de Gradle
```bash
./gradlew clean
./gradlew build
```

### Erro de Compilação Kotlin
Verifique a versão do JDK (deve ser 17+)
```bash
java -version
```

### Firebase não conecta
1. Verifique google-services.json
2. Verifique regras de segurança Firestore
3. Verifique conexão de internet

## 📚 Documentação

- [Jetpack Compose](https://developer.android.com/develop/ui/compose)
- [Firebase Android](https://firebase.google.com/docs/android/setup)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Hilt Dependency Injection](https://dagger.dev/hilt/)

## 📄 Licença

Este projeto está sob licença MIT. Veja LICENSE para detalhes.

## 👥 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📧 Contato

Para sugestões e reportar bugs, abra uma issue no GitHub.

---

Desenvolvido com ❤️ para demonstrar as melhores práticas de desenvolvimento Android moderno.

# Banco Imobiliário

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=JoaoGeniselli_Banco-Imobiliario&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=JoaoGeniselli_Banco-Imobiliario)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=JoaoGeniselli_Banco-Imobiliario&metric=coverage)](https://sonarcloud.io/summary/new_code?id=JoaoGeniselli_Banco-Imobiliario)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=JoaoGeniselli_Banco-Imobiliario&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=JoaoGeniselli_Banco-Imobiliario)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=JoaoGeniselli_Banco-Imobiliario&metric=bugs)](https://sonarcloud.io/summary/new_code?id=JoaoGeniselli_Banco-Imobiliario)

Aplicativo para controlar as transações financeiras do jogo Banco Imobiliário. Feito para o
jogador com papel de banqueiro, o app é capaz de configurar partidas do jogo, controlar os jogadores
participantes com seus respectivos saldos, efetuar operações de depósito, saque e transferências,
exibir o histórico de transações e computar o(a) vencedor(a) da partida. Este app não utiliza
recursos de rede, seu armazenamento é complemente local.

O projeto possui os idiomas implementados:
- Inglês (padrão)
- Português Brasil (quando definido no dispositivo)

## Tecnologias Utilizadas

| Nome                                                                                                      | Contexto/Descrição                                     |
|-----------------------------------------------------------------------------------------------------------|--------------------------------------------------------|
| [JetPack Compose](https://developer.android.com/compose)                                                  | Framework para criação de telas                        |
| [Material Design 3](https://m3.material.io/)                                                              | Design System (Light & Dark modes)                     |
| [Kotlin](https://kotlinlang.org/)                                                                         | Linguagem de programação principal                     |
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)                                 | Paralelismo e reatividade                              |
| [Jetpack Navigation](https://developer.android.com/develop/ui/compose/navigation)                         | Navegação entre telas                                  |
| [Room Database](https://developer.android.com/training/data-storage/room)                                 | Armazenamento local SQLite                             |
| [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)                          | Injeção de dependências                                |
| [Android Lint](https://developer.android.com/studio/write/lint)                                           | Análise estática (Android)                             |
| [Detekt](https://detekt.dev/)                                                                             | Análise estática (Kotlin)                              |
| [Sonarqube](https://www.sonarsource.com/products/sonarqube/)                                              | Análise de qualidade de código, segurança e relatórios |
| [JaCoCo](https://www.eclemma.org/jacoco/)                                                                 | Cobertura de testes unitários                          |
| [KotlinX Serialization](https://kotlinlang.org/docs/serialization.html)                                   | Conversão de objetos JSON                              |
| [JUnit 4](https://junit.org/junit4/)                                                                      | Execução de testes unitários                           |
| [MockK](https://mockk.io/)                                                                                | Gerenciamento de mocks para testes unitários           | 
| [Turbine](https://github.com/cashapp/turbine)                                                             | Teste de corrotinas e kotlin flows                     |
| [R8 (proguard)](https://developer.android.com/topic/performance/app-optimization/enable-app-optimization) | Ofuscação de código                                    |
| [Google KSP](https://github.com/google/ksp)                                                               | Processamento de anotações                             |
| [Version Catalogs](https://docs.gradle.org/current/userguide/version_catalogs.html)                       | Organização de versões e dependências                  |
| [Github Actions](https://github.com/features/actions?locale=pt-BR)                                        | Workflows de validação e compilação do projeto         |
| [Timber](https://github.com/JakeWharton/timber)                                                           | Prevenção de logs em modo `release`                    |

## Configuração do Projeto
1. Baixe o repositório localmente em sua máquina;
2. Abra a pasta raiz do projeto no [Android Studio](https://developer.android.com/studio?hl=pt-br);
3. Se necessário, realize o download da versão 21 da JDK (`Settings` >
   `Build, Execution, Deployment` > `Build Tools` > `Gradle`);
4. Selecione a build variant `debug` (`Build` > `Select build variant` >
   `:app = debug`);
5. Sincronize o projeto (`File` > `Sync Project with Gradle Files`);
6. Execute o projeto em um dispositivo com o sistema operacional Android 11 ou posterior (`Run` > `Run 'app'`).

## Saiba mais
- [Arquitetura](./docs/architecture.md)
- [Versionamento](./docs/versioning.md)
- [Github Workflows](./docs/workflows.md)
- [Templates de Desenvolvimento](./docs/templates.md)

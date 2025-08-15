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

O projeto possui dois idiomas implementatos:
- Inglês (padrão)
- Português BR (quando o idioma está definido no dispositivo)

## Tecnologias Utilizadas

| Nome                                                                                                      | Contexto                                               |
|-----------------------------------------------------------------------------------------------------------|--------------------------------------------------------|
| [JetPack Compose](https://developer.android.com/compose)                                                  | Framework para criação de telas                        |
| [Material Design 3](https://m3.material.io/)                                                              | Design System (Light & Dark modes)                     |
| [Kotlin](https://kotlinlang.org/)                                                                         | Linguagem de programação principal                     |
| [JUnit 4](https://junit.org/junit4/)                                                                      | Execução de testes unitários                           |
| [Detekt](https://detekt.dev/)                                                                             | Análise estática (Kotlin)                              |
| [Android Lint](https://developer.android.com/studio/write/lint)                                           | Análise estática (Android)                             |
| [Sonarqube](https://www.sonarsource.com/products/sonarqube/)                                              | Análise de qualidade de código, segurança e relatórios |
| [KotlinX Kover](https://github.com/Kotlin/kotlinx-kover)                                                  | Cobertura de testes unitários                          |
| [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)                          | Injeção de dependências                                |
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)                                 | Paralelismo e reatividade                              |
| [Jetpack Navigation](https://developer.android.com/develop/ui/compose/navigation)                         | Navegação entre telas                                  |
| [KotlinX Serialization](https://kotlinlang.org/docs/serialization.html)                                   | Conversão de objetos JSON                              |
| [MockK](https://mockk.io/)                                                                                | Gerenciamento de mocks para testes unitários           | 
| [Turbine](https://github.com/cashapp/turbine)                                                             | Teste de corrotinas e kotlin flows                     |
| [Room Database](https://developer.android.com/training/data-storage/room)                                 | Armazenamento local SQLite                             |
| [R8 (proguard)](https://developer.android.com/topic/performance/app-optimization/enable-app-optimization) | Ofuscação de código                                    |
| [Version Catalogs](https://docs.gradle.org/current/userguide/version_catalogs.html)                       | Organização de versões e dependências                  |
| [Github Actions](https://github.com/features/actions?locale=pt-BR)                                        | Workflows de validação e compilação do projeto         |

## Configuração do Projeto

## Arquitetura

## Versionamento

## Templates de Desenvolvimento

Este projeto possui alguns templates de código para facilitar a criação de componentes Compose.
Para utilizá-los em sua IDE, siga os passos abaixo:

1. Acesse o menu `Settings` > `Editor` > `File and Code Templates` do Android Studio.
2. No campo `Scheme`, selecione a opção `Project`, aplique as alterações e clique em Ok.
3. A IDE irá importar os templates disponíveis em `.idea/fileTemplates` automaticamente;
4. Agora, basta testar a importação seguindo os passos:
    - Selecione um diretório de código do projeto
    - Clique com o botão direito do mouse
    - Selecione o menu `New`
    - No submenu, clique em um dos itens customizados, como o `Composable Content`
    - Insira o nome desejado para o componente e verifique o arquivo gerado. 
## Github Workflows

O projeto conta com três workflows para auxiliar o processo de desenvolvimento, seus objetivos estão descritos nos tópicos abaixo.

### Continous Integration
- **Arquivo YAML**: [ci.yaml](.github/workflows/ci.yaml)
- **Objetivo**: Verificar a adequação de novas alterações de código com o padrão de qualidade do projeto.
- **Gatilhos**: Abertura ou sincronização de Pull Requests destinados às branches `develop` e `main`, ou alterações na branch `main`.
- **Passos Executados**: 
   - Análise estática Android (Android Lint)
   - Análise estática Kotlin (Detekt)
   - Testes unitários
   - Cobertura de testes (Kover)
   - Análise do Quality Gate (Sonar -- se branch `main`)

### Build QA
- **Arquivo YAML**: [build-qa.yaml](.github/workflows/build-qa.yaml)
- **Objetivo**: Gerar uma APK de testes. 
- **Gatilhos**: Alterações na branch `develop`, ou gatilho manual.
- **Passos Executados**: 
   - Compilação de APK em modo `debug`
   - Exportação de artefatos na pipeline

### Build Production
- **Arquivo YAML**: [build-prod.yaml](.github/workflows/build-prod.yaml)
- **Objetivo**: Gerar os Android App Bundles de uma nova versão para produção.
- **Gatilhos**: Sincronização de Pull Requests com destino à `main`.
- **Passos Executados**: 
   - Compilação de AAB em modo `release` (ofuscado, otimizado e assinado)
   - Exportação de artefatos na pipeline

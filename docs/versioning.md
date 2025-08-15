## Versionamento

O projeto segue a seguinte variante do padrão Git-Flow:

```mermaid
---
config:
   logLevel: 'debug'
   gitGraph:
      showBranches: true
      showCommitLabel: true
      mainBranchName: main
---
   gitGraph:
      commit id: "inicial"
      branch develop
      commit id: "criação develop"
      branch feat/abc
      branch feat/def
      commit id: "def 1"
      checkout feat/abc
      commit id: "abc 1"
      commit id: "abc 2"
      checkout develop
      merge feat/abc id: "PR #1"
      checkout feat/def
      merge develop id: "rebase"
      commit id: "def 2"
      checkout develop
      merge feat/def id: "PR #2"
      branch release/v2.1.0
      commit id: "version bump 2.1.0"
      commit id: "fix 1"
      checkout main
      merge release/v2.1.0 id: "Release PR"
      checkout develop
      merge main id: "mergeback 2.1.0"
      checkout main
      branch hotfix/v2.1.1
      commit id: "fix 2"
      checkout main
      merge hotfix/v2.1.1
      checkout develop
      merge main id: "mergeback 2.1.1"
```

### Papéis das Branches

#### Main
Contém sempre o código mais atual de produção do app.

#### Develop
Serve como a branch mais atualizada do projeto, a porta de entrada para funcionalidades novas.

#### Feature branches
Branches para o desenvolvimento de funcionalidades, correções, melhorias, débitos técnicos. São criadas à partir da `develop` e voltam para ela. Padrão de nomenclatura: `feature/descricao`, `debt/descricao`, `refactor/descricao`, `bugfix/descricao`...

#### Releases
Branches temporárias criadas para evitar o bloqueio da `develop` enquanto um processo de release está em andamento (testes, análise da google play...). Estas branches servem como um snapshot da develop em determinado momento, representando um "release candidate". São criadas à partir da `develop`, mas são mergeadas na `main`. O padrão utilizado para versões do app é o [Semantic Versioning (SemVer)](https://semver.org/lang/pt-BR/). Padrão de nomenclatura: `release/vX.Y.Z`.

#### Hotfixes
Branches de release destinadas à correções urgentes de produção. São criadas à partir da `main` e voltam diretamente para ela após as correções validadas.

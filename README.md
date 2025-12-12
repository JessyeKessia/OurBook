# OurBook

Aplicativo Android desenvolvido em Kotlin usando Jetpack Compose.



**Recursos principais**
- UI construída com Jetpack Compose
- Navegação com Navigation Compose
- `ViewModel` e arquitetura AndroidX
- Material3 (componentes e temas)
- Banco de dados FireBase

**Pré-requisitos**
- JDK 11
- Android Studio (recomendado)
- Android SDK com `compileSdk = 36`
- Uso do wrapper Gradle: execute `./gradlew` (não precisa instalar Gradle globalmente)

**Como compilar e executar**
- Compilar APK debug: `./gradlew assembleDebug`
- Limpar build: `./gradlew clean`
- Instalar no dispositivo/emulador (após assemble):

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

- Executar testes unitários: `./gradlew test`
- Executar testes instrumentados (com dispositivo/emulador conectado): `./gradlew connectedAndroidTest`

**Estrutura do projeto**
- `app/` — módulo Android principal (código-fonte em `app/src/main/`)
- Arquivos de configuração no root: `build.gradle.kts`, `settings.gradle.kts`, `gradle.properties`, `gradle/`

**Como desenvolver**
- Abra o projeto no Android Studio
- Execute o app em um emulador ou dispositivo físico
- Crie branches para features/bugs e envie pull requests com descrições claras

**Contribuição**
<table>
  <tr>
   <td align="center">
      <a href="https://github.com/jessyekessia" title="gitHub">
        <img src="https://avatars.githubusercontent.com/u/128109017?v=4" width="100px;" alt="Foto de Jessye"/><br>
        <sub>
          <b>Jessye Késsia Pereira</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Maira-larissa" title="gitHub">
        <img src="https://avatars.githubusercontent.com/u/131016411?v=4" width="100px;" alt="Foto de Maira"/><br>
        <sub>
          <b>Maira Larissa</b>
        </sub>
      </a>
    </td>
     <td align="center">
      <a href="https://github.com/iamjonn" title="gitHub">
        <img src="https://avatars.githubusercontent.com/u/110827553?v=4" width="100px;" alt="Foto de Jon"/><br>
        <sub>
          <b>Jonata Nascimento</b>
        </sub>
      </a>
    </td>
      </a>
    </td>
  </tr>
</table>
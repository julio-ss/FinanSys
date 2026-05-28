# Troubleshooting - Guia de Resolução de Problemas

## Erro: Gradle Cache Corrompido

Se você receber o erro:
```
Unable to find method 'org.gradle.api.file.FileCollection org.gradle.api.artifacts.Configuration.fileCollection(org.gradle.api.specs.Spec)'
```

### Solução Completa:

#### 1. **No Android Studio:**
- File → Settings → Build, Execution, Deployment → Gradle
- Desmarque "Make Gradle build process a daemon"
- Clique em "Clear cache and restart"

#### 2. **Via Terminal (Windows/Mac/Linux):**

**Windows (PowerShell):**
```powershell
# Parar daemons do Gradle
./gradlew --stop

# Limpar caches
Remove-Item -Path "$env:USERPROFILE\.gradle\caches" -Recurse -Force
Remove-Item -Path "$env:USERPROFILE\.gradle\wrapper" -Recurse -Force

# Limpar diretórios de build
Remove-Item -Path "build" -Recurse -Force
Remove-Item -Path "app\build" -Recurse -Force

# Sincronizar projeto
./gradlew clean
./gradlew sync
```

**Mac/Linux:**
```bash
# Parar daemons do Gradle
./gradlew --stop

# Limpar caches
rm -rf ~/.gradle/caches ~/.gradle/wrapper

# Limpar diretórios de build
rm -rf build app/build

# Sincronizar projeto
./gradlew clean
./gradlew sync
```

#### 3. **Após Limpar:**
1. Feche o Android Studio completamente
2. Aguarde 10 segundos
3. Reabra o Android Studio
4. Clique em "File → Sync Now"
5. Aguarde o sync completar

## Erro: Plugin Não Encontrado

Se receber erro de plugin não encontrado:

```bash
# Limpar gradle
./gradlew --stop
rm -rf ~/.gradle/caches ~/.gradle/wrapper

# Fazer download das dependências novamente
./gradlew --refresh-dependencies

# Sincronizar
./gradlew clean
```

## Erro: Kotlin Compilation Error

Se receber erros de compilação Kotlin:

```bash
# Invalidar cache do IDE
./gradlew clean

# Forçar recompilação
./gradlew build --refresh-dependencies
```

## Erro: Out of Memory (Heap Space)

Se receber erro de memória:

**Windows:**
Edite `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx2g -XX:MaxMetaspaceSize=512m
```

**Mac/Linux:**
```bash
echo "org.gradle.jvmargs=-Xmx2g -XX:MaxMetaspaceSize=512m" >> gradle.properties
```

## Erro: BuildConfig Não Encontrado

Se receber erro sobre BuildConfig:

```bash
# Limpar build
./gradlew clean

# Reconstruir
./gradlew build

# No Android Studio: Build → Rebuild Project
```

## Erro: Firebase Initialization Failed

Se o Firebase não inicializar:

1. Verifique se `google-services.json` está em `app/google-services.json`
2. Verifique se Firebase está habilitado no projeto
3. Execute:
```bash
./gradlew clean
./gradlew build
```

## Checklist de Resolução Geral

- [ ] Parar todos os daemons Gradle
- [ ] Limpar `~/.gradle/caches`
- [ ] Limpar `~/.gradle/wrapper`
- [ ] Limpar diretórios `build` e `app/build`
- [ ] Fechar Android Studio
- [ ] Reabrir Android Studio
- [ ] Sincronizar Gradle (File → Sync Now)
- [ ] Aguardar 2-3 minutos para sincronização completa
- [ ] Tentar novamente

## Dicas Importantes

1. **Não use cache do Gradle em Development:**
   ```bash
   ./gradlew build --no-build-cache
   ```

2. **Force refresh das dependências:**
   ```bash
   ./gradlew --refresh-dependencies
   ```

3. **Veja logs detalhados:**
   ```bash
   ./gradlew build --info
   ./gradlew build --debug
   ```

4. **Confirme versão do Gradle:**
   ```bash
   ./gradlew --version
   ```

## Se Nada Funcionar

1. Feche o Android Studio
2. Delete toda a pasta `.gradle` na home
3. Delete toda a pasta `.android` na home
4. Delete `.idea` e `.gradle` do projeto
5. Reabra o Android Studio
6. Deixe sincronizar completamente

```bash
# Script completo (Mac/Linux):
./gradlew --stop
rm -rf ~/.gradle ~/.android
rm -rf .idea .gradle build app/build
```

## Contato com Suporte

Se o problema persistir:
- Verifique sua conexão de internet
- Tente usar uma VPN diferente
- Consulte a documentação oficial do Gradle: https://gradle.org/

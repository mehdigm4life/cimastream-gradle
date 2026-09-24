# CimaStream gradle plugin

Build plugin used to compile and package extensions for the **CimaStream** app.

Every extension now belongs to the CimaStream ecosystem:

- Extension API artifact: `com.github.mehdigm4life.cimastream:library-android:pre-release` (the Android AAR of the CimaStream API, published via JitPack from [`mehdigm4life/cimastream`](https://github.com/mehdigm4life/cimastream))
- Extension DSL: `cimastream("com.github.mehdigm4life.cimastream:library-android:pre-release")` and `cimastream { ... }`
- Extension packages: `com.mehdigm.cimastream4.*`
- Plugin id: `com.mehdigm.cimastream4.gradle`
- Built extension files use the `.cima4` extension

## How to use

Prerequisite: [AGP 9](https://developer.android.com/build/releases/gradle-plugin) with its built-in Kotlin support (a separate `kotlin-android` plugin is not required on AGP 9).

Root `build.gradle.kts`:

```kotlin
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:9.1.1")
        classpath("com.github.mehdigm4life:cimastream-gradle:VERSION")
    }
}

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "com.mehdigm.cimastream4.gradle")
}
```

Every provider module `build.gradle.kts`:

```kotlin
version = 1

repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
}

cimastream {
    description = "My provider"
    authors = listOf("Your Name")
    language = "ar"
    tvTypes = listOf("Movie", "TvSeries")
}

android {
    namespace = "your.package.name"
    compileSdk = 37
    defaultConfig {
        minSdk = 23
    }
}

dependencies {
    val cimastream by configurations
    cimastream("com.github.mehdigm4life.cimastream:library-android:pre-release")
    implementation("com.github.mehdigm4life:NiceHttp:v0.5.0")
}
```

In every provider module annotate the main class with:

```kotlin
@CimastreamPlugin
class MainProvider : MainAPI() { ... }
```

Import the API from `com.mehdigm.cimastream4.*`.

Build every extension with `./gradlew :<module>:make` (produces `<module>.cima4`), then run `./gradlew makePluginsJson` to produce the repository `plugins.json` listing every `.cima4` extension. Publish the file on a branch (or use the raw GitHub links) and add the repository URL inside the app.

## Maintainer

- [mehdigm4life](https://github.com/mehdigm4life) — owner and maintainer of the CimaStream ecosystem

## License

Released under the GNU Lesser General Public License v3.0. See [LICENSE](LICENSE).
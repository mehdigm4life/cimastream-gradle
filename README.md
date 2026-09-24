# CimaStream gradle plugin

Build plugin used to compile and package extensions for the **CimaStream** app.

Every extension now belongs to the CimaStream ecosystem:

- Extension API artifact: `com.mehdigm.api:library:pre-release` (published via JitPack from [`mehdigm4life/cimastream`](https://github.com/mehdigm4life/cimastream))
- Extension DSL: `cimastream("com.mehdigm.api:library:pre-release")` and `cimastream { ... }`
- Extension packages: `com.mehdigm.cimastream4.*`
- Plugin id: `com.mehdigm.cimastream4.gradle`
- Built extension files use the `.cima4` extension

## How to use

In your extension repository's `build.gradle.kts`:

```kotlin
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.13.2")
        classpath("com.github.mehdigm4life:cimastream-gradle:VERSION")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:VERSION")
    }
}

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.mehdigm.cimastream4.gradle")

    cimastream {
        description = "My provider"
        authors = listOf("Your Name")
        language = "ar"
        tvTypes = listOf("Movie", "TvSeries")
    }

    dependencies {
        val cimastream by configurations
        cimastream("com.mehdigm.api:library:pre-release")
        implementation("com.github.mehdigm4life:NiceHttp:v0.5.0")
    }
}
```

In every provider module annotate the main class with:

```kotlin
@CimastreamPlugin
class MainProvider : MainAPI() { ... }
```

Import the API from `com.mehdigm.cimastream4.*`.

Run `./gradlew makePluginsJson` to produce the repository `plugins.json` listing every `.cima4` extension, then publish it on a branch (or use the raw GitHub links) and add the repository URL inside the app.

## Maintainer

- [mehdigm4life](https://github.com/mehdigm4life) — owner and maintainer of the CimaStream ecosystem

## License

Released under the GNU Lesser General Public License v3.0. See [LICENSE](LICENSE).
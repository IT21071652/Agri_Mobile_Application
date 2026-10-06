# Agri_Mobile_Application
This is a mobile app developed for SLIIT 2nd year 2nd semester MAD project. We have developed an app for agriculture field to improve their service using new technology.

## Build and run

The project uses Kotlin, Jetpack Compose, Material 3, AndroidX ViewModel, coroutines, and the existing local SQLite farm records. Live conditions come from Open-Meteo and need an internet connection; choose a supported Sri Lankan region in the app. Saved crop, product, article, and news records remain on the device.

New SQLite databases are initialized with clearly labeled sample crops, market products, learning articles, and news. These starter records are only added when each database is first created; they are not reinserted on later launches.

Install JDK 17 and Android SDK Platform 35, then open the project root in Android Studio or use the Gradle wrapper from PowerShell:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat testDebugUnitTest
```

To install and launch the app, connect an Android device with USB debugging enabled or start an emulator, then run:

```powershell
.\gradlew.bat installDebug
```

The debug APK is generated at `app\build\outputs\apk\debug\app-debug.apk`.

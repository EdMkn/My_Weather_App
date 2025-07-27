# My Weather App 🌦️

Application météo Android avec prévisions en temps réel utilisant l'API OpenWeatherMap

![App Screenshot](/app/src/main/res/drawable/screenshot.png) *(Remplacez par votre propre capture d'écran)*

## Fonctionnalités ✨

- Météo actuelle avec température, conditions et icônes
- Prévisions sur 5 jours
- Géolocalisation automatique
- Recherche manuelle par ville (optionnel)
- Thème sombre/clair (optionnel)

## Technologies 🛠️

- **Kotlin** - Langage principal
- **Architecture** - MVVM (Model-View-ViewModel)
- **API** - OpenWeatherMap
- **Bibliothèques** :
    - Retrofit - Appels réseau
    - Gson - Conversion JSON
    - Coroutines - Gestion asynchrone
    - View Binding - Liaison des vues
    - Glide - Chargement des images
    - Play Services Location - Géolocalisation

## Prérequis 📋

- Clé API OpenWeatherMap ([Obtenez-la ici](https://openweathermap.org/api))
- Android Studio Arctic Fox ou ultérieur
- SDK Android minimum : API 21 (Lollipop)

## Installation 🔧

1. Cloner le dépôt :
```bash
git clone https://github.com/votre-utilisateur/my-weather-app.git
```

2. Ajouter votre clé api

```kotlin
// Dans secrets.properties
OPEN_WEATHER_API_KEY="votre_clé_api"
```
3. Synchroniser le projet avec Gradle

## Améliorations prévues 🚀
Notifications météo

Widget Android

Support multi-langues

Historique des recherches

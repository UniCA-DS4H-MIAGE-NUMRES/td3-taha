[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/VYS_GaEs)
# Université Côte d'Azur - DS4H - 
# MASTER MIAGE NUMRES
## Cours de Programmation Mobile Moderne - Leo Donati

### TD3 : Application Kotlin Multiplateforme


**Objectif du TD3** : faire de l'application PizzApp (td2) une application multiplateforme qui puisse tourner sur
- Android
- Navigateur Web
- Desktop (MacOS, Windows, Linux)

#### Consignes
- A faire en binôme
- Utiliser le repository du github classroom en suivant [ce lien]() pour le binôme
- Décrire dans ce ReadMe les détails de votre projet et comment vous avez surmonté les difficultés (typiquement concernant certains packages comme Room)
- Ajouter le lien vers une vidéo (Youtube ou autre) avec une démo de votre projet où l'on voit son déploiement sur differentes plateformes

# Projet PizzaApp - Application Multiplateforme Kotlin

## Description du Projet
PizzaApp est une application multiplateforme développée en Kotlin utilisant Compose Multiplatform. L'application permet aux utilisateurs de parcourir un menu de pizzas, d'ajouter des pizzas à leur panier, et de visualiser leur commande. L'application est disponible sur les plateformes suivantes :

- **Android**
- **Desktop (Windows, macOS, Linux)**
- **Web (via WebAssembly)**

## Technologies Utilisées
- **Kotlin Multiplatform** : Pour partager le code entre les différentes plateformes.
- **Compose Multiplatform** : Pour l'interface utilisateur commune sur Android, Desktop et Web.
- **Skiko** : Pour le rendu graphique sur Web via WebAssembly.
- **Ktor** : Pour les requêtes réseau (si nécessaire).
- **Voyager** : Pour la navigation entre les écrans.

## Fonctionnalités
- **Menu des Pizzas** : Affiche une liste de pizzas avec leurs noms, prix et images.
- **Détails d'une Pizza** : Permet de voir les détails d'une pizza et d'ajouter des options supplémentaires (ex : fromage supplémentaire).
- **Panier** : Affiche les pizzas ajoutées au panier et calcule le prix total.
- **Navigation** : Utilise Voyager pour naviguer entre les écrans.

## Difficultés Rencontrées et Solutions

### 1. Problème d'Affichage des Images sur Web
**Problème** :
Les images ne s'affichaient pas sur la version Web de l'application, bien qu'elles fonctionnaient correctement sur Android et Desktop.

**Solution** :
- **Utilisation de `painterResource`** : Nous avons remplacé `KamelImage` par `Image` avec `painterResource` pour charger les images. Cela a résolu le problème d'affichage sur le Web.
- **Placement des Images** : Les images ont été placées dans le dossier `commonMain/resources/` pour qu'elles soient accessibles sur toutes les plateformes.

### 2. Problème de Persistance du Panier
**Problème** :
Le panier ne conservait pas les articles ajoutés lors de la navigation entre les écrans.

**Solution** :
- **État Global** : Nous avons créé un objet global `CartState` pour gérer l'état du panier. Cet objet est partagé entre tous les écrans, ce qui permet de conserver les articles ajoutés au panier.

```kotlin
object CartState {
    private val _cart = mutableStateListOf<Pizza>()
    val cart: List<Pizza> get() = _cart

    fun addToCart(pizza: Pizza) {
        _cart.add(pizza)
    }

    fun clearCart() {
        _cart.clear()
    }
}
```

### 3. Problème de Compatibilité WebAssembly (Wasm)
**Problème** :
L'application ne fonctionnait pas sur le Web en raison d'une erreur liée à Skiko (`WebAssembly.instantiate(): Import #4681 "./skiko.mjs"`).

**Solution** :
- **Mise à Jour des Dépendances** : Nous avons mis à jour les dépendances de Skiko et Compose Multiplatform pour utiliser des versions compatibles.
- **Nettoyage du Projet** : Nous avons exécuté `./gradlew clean` pour supprimer les artefacts de build corrompus.
- **Configuration Correcte de `wasmJs`** : Nous avons configuré le target `wasmJs` avec les bonnes options pour générer les fichiers WebAssembly et JavaScript nécessaires.

```kotlin
wasmJs {
    moduleName = "composeApp"
    browser {
        commonWebpackConfig {
            outputFileName = "composeApp.js"
        }
    }
    binaries.executable()
}
```

### 4. Problème de Version Minimale du SDK Android
**Problème** :
Une dépendance (`androidx.wear.compose:compose-material-core`) nécessitait une version minimale du SDK Android (`minSdkVersion`) de 25, alors que notre projet était configuré pour une version 24.

**Solution** :
- **Mise à Jour de `minSdkVersion`** : Nous avons mis à jour la version minimale du SDK Android à 25 dans le fichier `build.gradle.kts`.

```kotlin
android {
    defaultConfig {
        minSdk = 25
    }
}
```

### 5. Problème de Conflit de Namespace
**Problème** :
Deux bibliothèques (`kamel-image-android` et `kamel-fetcher-resources-android`) utilisaient le même namespace (`io.kamel.image`), ce qui causait un conflit.

**Solution** :
- **Exclusion de la Bibliothèque Conflictuelle** : Nous avons exclu la bibliothèque conflictuelle dans les dépendances.

```kotlin
implementation("media.kamel:kamel-image:1.0.3") {
    exclude(group = "media.kamel", module = "kamel-fetcher-resources-android")
}
```

## Problèmes rencontrés avec la base de données et solutions

### 1. **Problème de compatibilité Room sur Kotlin Multiplatform**
**Problème** : Room n'est disponible que pour Android, ce qui empêche une implémentation directe sur Desktop et Web.

**Solution** :
- Nous avons utilisé **Room pour Android**, **Exposed + JDBC pour Desktop**, et **une implémentation WebSQLite pour WASM**.
- Ajout de classes spécifiques pour chaque plateforme via `expect/actual` pour gérer les différences d'implémentation.

---

### 2. **Problème de compatibilité WebAssembly avec SQLite**
**Problème** : WebAssembly ne supporte pas les bases de données locales de la même manière que Desktop et Android.

**Solution** :
- Utilisation de `WebSqliteDriver` pour gérer la base de données dans le navigateur.
- Implémentation d'un DAO spécifique (`WasmOrderDao`) utilisant une liste en mémoire si la base de données n'est pas persistante.

---

### 3. **Problème d'incompatibilité de `MutableStateFlow` sur WebAssembly**
**Problème** : `MutableStateFlow` n'est pas disponible en `wasmJsMain`, ce qui empêche l'utilisation des `Flow` comme sur Android et Desktop.

**Solution** :
- Remplacement de `Flow<List<Order>>` par `List<Order>` dans la couche DAO pour WASM.
- Adaptation de l'interface DAO pour utiliser des `MutableList<Order>` en mémoire sur Web.

---

### 4. **Problème d'autoincrement avec SQLite sur Desktop**
**Problème** : Sur SQLite (via JDBC), l'auto-incrémentation ne fonctionnait pas comme prévu.

**Solution** :
- Utilisation explicite de `INTEGER PRIMARY KEY AUTOINCREMENT` dans la définition de la table.
- Vérification que l'ID est bien récupéré après chaque insertion.

---

### 5. **Problème de compatibilité entre Android et Desktop lors du partage de classes sérialisables**
**Problème** : Les classes `Order`, `Pizza`, et `CartItem` étaient sérialisables (`@Serializable`), mais Room sur Android n'accepte pas directement des listes dans les entités.

**Solution** :
- Ajout d'un **TypeConverter** pour convertir les listes en JSON et vice-versa.
- Utilisation de `@TypeConverters(OrderConverters::class)` sur l'entité `Order`.
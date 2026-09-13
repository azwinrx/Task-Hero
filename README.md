# Productive Heroes

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-purple.svg?style=for-the-badge&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-brightgreen.svg?style=for-the-badge&logo=jetpack-compose)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-API%2024+-green.svg?style=for-the-badge&logo=android)](https://android.com)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)

Productive Heroes is a gamified task management application for Android that integrates RPG elements into daily productivity. Users complete tasks to gain experience points (EXP), manage stamina, and engage in a timer-based combat system to level up their character.

---

## Features

### Task Management
* **CRUD Operations:** Add, complete, or remove daily tasks.
* **Reward System:** Task completion yields 100 EXP.
* **Persistence:** All task states are automatically saved locally using DataStore.

### Combat & Timer System
* **Entity Scaling:** Four distinct monster tiers with scaling attributes:
    * Slime Ghost (900 HP, +50 EXP)
    * Baby Lizard (1,200 HP, +100 EXP)
    * Evil Tree (1,500 HP, +150 EXP)
    * The Unknown (1,800 HP, +200 EXP)
* **Automated Combat:** Real-time damage execution based on a countdown timer.
* **Stamina Management:** Integrates a Pomodoro-style rest mechanic. Users must pause combat to regenerate stamina.

### Character Progression
* **Leveling System:** Experience-based progression.
* **Stat Growth:** Character attributes scale automatically upon leveling up.
* **State Management:** Complete session persistence across application restarts.

---

## Installation

### Prerequisites
* Android Studio (Ladybug 2024.2.1 or newer)
* JDK 11 or higher
* Android SDK API 24+ (Android 7.0 Nougat or newer)
* Gradle 8.13

### Build Instructions

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/azwinrx/Task-Hero.git
    cd Task-Hero
    ```

2.  **Open in Android Studio:**
    * Navigate to `File` -> `Open` and select the project directory.
    * Allow Gradle to complete the sync process.

3.  **Run the application:**
    * Execute via an emulator or a physical device (`Shift + F10`).

4.  **Build APK (Optional):**
    ```bash
    ./gradlew assembleDebug
    ```
    *Output directory:* `app/build/outputs/apk/debug/`

*Note: Pre-built APKs are available in the [Releases](https://github.com/azwinrx/Productive-Heroes/releases) section.*

---

## Usage Guide

### Quest Mode (Task View)
* **Input:** Enter task details in the provided text field and submit.
* **Execution:** Select the checkmark to mark a task as complete and claim EXP.
* **Deletion:** Select the 'X' to remove a task without gaining EXP.

### Timer Mode (Combat View)
* **Initialization:** Select a target entity from the dropdown menu to reset its HP.
* **Engagement:** Press 'Fight' to begin the timer and deal damage over time. Stamina will deplete actively.
* **Recovery:** Press 'Rest' to halt damage output and regenerate stamina.
* **Completion:** Depleting an entity's HP to 0 grants the designated EXP reward. The entity will automatically respawn.

---

## Tech Stack

### Architecture
* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose (Material3)
* **Architecture Pattern:** MVVM (Model-View-ViewModel)

### Dependencies
* `androidx.compose.material3` - UI components
* `androidx.lifecycle:lifecycle-viewmodel-compose` - State management
* `androidx.navigation:navigation-compose:2.9.5` - View routing
* `androidx.datastore:datastore-preferences` - Local key-value storage
* `io.coil-kt:coil-compose` - Asynchronous image loading & GIF rendering
* `com.google.accompanist:accompanist-drawablepainter` - Drawable support in Compose
* `com.google.code.gson:gson` - JSON serialization

### Project Structure
```text
com.azwin.dotask
 ├── Model
 │   ├── Fight (Statistic, TimerData)
 │   └── Quest (QuestData, ToDo)
 ├── View
 │   ├── Components (GameButton, StatisticBar)
 │   ├── QuestView
 │   └── TimerView
 ├── ViewModel
 │   ├── Fight (TimerViewModel)
 │   └── Quest (QuestViewModel)
 ├── Data
 │   ├── QuestRepository
 │   └── SettingsManager
 └── MainActivity.kt
```

---

## Screenshots

<p align="center">
  <img src="screenshoots/Screenshot_20251111_142545.png" width="270" alt="Quest Mode View" />
  <img src="screenshoots/Screenshot_20251111_142556.png" width="270" alt="Timer Mode View" />
  <img src="screenshoots/Screenshot_20251111_142605.png" width="270" alt="Statistics View" />
</p>

---

## Roadmap

Future iterations of this project will focus on the following implementations:
* Achievement and badge system
* Character equipment and avatar customization
* Advanced statistics and analytics dashboard
* Cloud synchronization for data backup
* Audio feedback integration
* Dark mode support
* Global leaderboard
* Daily quest generation

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/NewFeature`)
3. Commit your changes (`git commit -m 'Add NewFeature'`)
4. Push to the branch (`git push origin feature/NewFeature`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Asset Credits

* **Entities:**
    * Slime Ghost: [Pinterest](https://id.pinterest.com/pin/72972456457340602/) / [Gifer](https://i.gifer.com/PcHK.gif)
    * Baby Lizard: [Tenor](https://tenor.com/id/view/green-monster-cute-creatures-pixel-monster-cute-monsters-portal-fantasy-game-gif-27138031)
    * Evil Tree: [Tenor](https://tenor.com/id/view/pixel-pixelart-pixel-game-cute-monster-mossy-gif-27137951)
    * The Unknown: [Tumblr](https://64.media.tumblr.com/01d0f90c74074e8a45825dea194b659f/tumblr_oklrofjnhU1uj3emso1_1280.gif)
* **Environment:** [Craftpix](https://craftpix.net/freebies/free-pixel-art-fantasy-2d-battlegrounds/)
* **UI Components:** [Scroll Kit](https://gamedeveloperstudio.itch.io/scroll-kit)

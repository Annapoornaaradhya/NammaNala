<div align="center">

# 🌿 Namma-Nala
### Smart Canal Health Monitoring System

<p>
  Smart irrigation and canal monitoring application built using Kotlin, Jetpack Compose, Firebase, GPS, and Google Maps.
</p>

<p>
   <a href="https://doi.org/10.5281/zenodo.20213371">
    <img src="https://zenodo.org/badge/1228929567.svg" alt="DOI">
  </a>
</p>

<p>
  <img src="https://img.shields.io/badge/Platform-Android-green?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Kotlin-Jetpack%20Compose-purple?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Firebase-Backend-orange?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Status-Active-success?style=for-the-badge" />
</p>

</div>

---

# 📖 Overview

**Namma-Nala** is an Android-based smart irrigation and canal monitoring system designed to help farmers and authorities efficiently manage canal water usage.

The application provides:
- real-time monitoring,
- intelligent insights,
- issue reporting,
- maintenance management,
- and interactive canal visualization.

The goal of the project is to improve water distribution efficiency and support sustainable irrigation practices.

---

# ✨ Features

## 💧 Real-time Water Monitoring
Track canal water levels and flow conditions dynamically.

## 🤖 AI-Based Insights
Receive smart recommendations based on water usage and availability patterns.

## 🚨 Issue Reporting System
Report:
- leakage
- blockage
- overflow
- damaged canals

with image and location support.

## 🛠️ Maintenance Scheduling
Schedule and manage canal maintenance activities effectively.

## 🗺️ Interactive Canal Map
Visualize canal locations and routes using Google Maps integration.

## 🔐 User Authentication
Secure login and signup using Firebase Authentication.

## 📊 Data Visualization
Analyze water usage trends through graphical representations.

---

# 🛠️ Tech Stack

| Category | Technologies |
|---|---|
| Frontend | Kotlin, Jetpack Compose |
| Backend | Firebase Authentication, Firestore |
| Maps & Location | Google Maps API, GPS |
| Charts | MPAndroidChart |
| Architecture | Modern Android Architecture |

---

# 🧱 Project Structure

```bash
NammaNala/
│
├── app/
│   ├── src/main/
│   │   ├── java/com/example/nammanala/
│   │   │   ├── screens/
│   │   │   ├── navigation/
│   │   │   ├── components/
│   │   │   ├── viewmodel/
│   │   │   └── utils/
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   │
│   └── build.gradle.kts
│
├── gradle/
├── local.properties
└── README.md
```

---

# 🔐 Security

Sensitive configurations are secured using:

- Restricted API Keys
- SHA-1 Authentication
- Local configuration via `local.properties`

Example:

```properties
MAPS_API_KEY=your_api_key_here
```

---

# 🚀 Getting Started

## Prerequisites

- Android Studio (Latest Version)
- Firebase Project Setup
- Google Maps API Key

---

## Installation Steps

### 1️⃣ Clone Repository

```bash
git clone https://github.com/Annapoornaaradhya/NammaNala.git
```

### 2️⃣ Open in Android Studio

Open the cloned project folder in Android Studio.

### 3️⃣ Configure API Key

Add your Google Maps API key inside:

```properties
local.properties
```

```properties
MAPS_API_KEY=your_api_key_here
```

### 4️⃣ Run the Application

Sync Gradle and run the project on an emulator or physical device.

---

# 🎥 Demo Video

https://github.com/user-attachments/assets/7fd83871-8da8-43ad-adb2-d66ae6b080c9

---

# 📱 Screenshots

<p align="center">
  <img width="280" src="https://github.com/user-attachments/assets/b53407d8-656f-4e41-afc2-652a41df6c86"/>
</p>

---

# 📊 Future Enhancements

- 📡 IoT integration for live sensor-based monitoring
- 🤖 Advanced AI prediction models
- 🌐 Multi-language support for rural accessibility
- 📶 Offline functionality
- ☁️ Cloud analytics dashboard
- 🌦️ Weather-based irrigation recommendations

---

# 👩‍💻 Author

## Annapoorna Aradhya

- GitHub: https://github.com/Annapoornaaradhya
- LinkedIn: https://linkedin.com/in/annapoorna-aradhya

---

# 📄 License

This project is developed for academic, internship, and educational purposes.

---

<div align="center">

### 🌱 “Smart Water Management for a Sustainable Future”

</div>

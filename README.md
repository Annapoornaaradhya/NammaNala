# 🌿 Namma-Nala: Smart Canal Health Monitoring System

Namma-Nala is an Android-based smart irrigation monitoring system designed to help farmers efficiently manage canal water usage. The application provides real-time insights, issue reporting, and intelligent recommendations to optimize water distribution.

---

## 📱 Features

* **Real-time Water Monitoring**
  Track water levels and flow conditions dynamically.

* **AI-Based Insights**
  Get recommendations based on water availability and usage patterns.

* **Issue Reporting System**
  Report canal issues such as leakage, blockage, overflow, and more with location and image support.

* **Maintenance Scheduling**
  Plan and manage canal maintenance activities efficiently.

* **Interactive Canal Map**
  Visualize canal paths and locations using Google Maps integration.

* **User Authentication**
  Secure login and signup using Firebase Authentication.

* **Data Visualization**
  View water usage trends using graphical representations.

---

## 🛠️ Tech Stack

* **Frontend:** Kotlin, Jetpack Compose
* **Backend:** Firebase (Authentication, Firestore)
* **Maps & Location:** Google Maps API, GPS
* **Charts:** MPAndroidChart
* **Architecture:** Modern Android (Composable UI, Navigation)

---

## 🧱 Project Structure

```
app/
 ├── src/main/
 │   ├── java/com/example/nammanala/
 │   ├── res/
 │   └── AndroidManifest.xml
 ├── build.gradle.kts
```

---

## 🔐 Security Note

API keys and sensitive configurations are secured using:

* Restricted API keys (Package name + SHA-1)
* Local configuration (`local.properties`) for sensitive data

---

## 🚀 Getting Started

### Prerequisites

* Android Studio (latest version)
* Firebase project setup
* Google Maps API key

### Steps

1. Clone the repository
2. Open in Android Studio
3. Add your API key in `local.properties`:

   ```
   MAPS_API_KEY=your_api_key_here
   ```
4. Sync Gradle and run the app

---

## Watch Demo


https://github.com/user-attachments/assets/7fd83871-8da8-43ad-adb2-d66ae6b080c9


---

## 📱 Screenshots

<img width="716" height="1600" alt="WhatsApp Image 2026-05-04 at 8 28 50 PM" src="https://github.com/user-attachments/assets/b53407d8-656f-4e41-afc2-652a41df6c86" /> 

---

## 📊 Future Enhancements

* IoT integration for real-time sensor data
* Advanced AI-based prediction models
* Multi-language support for rural accessibility
* Offline functionality

---

## 👩‍💻 Author

**Annapoorna V**

---

## 📄 License

This project is for academic and educational purposes.

# Real Estate Management Application

This repository contains the source code for a Real Estate Management application, developed with modern Android development practices and technologies, to streamline the management of properties, customer interactions, and related data. The application offers a user-friendly and efficient experience tailored for real estate professionals.

## Overview

This application aims to simplify the complexities of managing real estate data by providing a centralized platform for handling properties, customers, and their specific needs. It is designed to enhance productivity and organization within the real estate sector.

## Key Features

*   **Property Management:** Seamlessly add, edit, and manage property details, including address, property type, size, pricing, availability, and high-quality images.
*   **Customer Management:** Efficiently track customer information, preferences, and interaction history to personalize services.
*   **Requirement Matching:** Intelligently match customer requirements to suitable properties, saving time and improving customer satisfaction.
*   **Secure Authentication:** Utilizes Firebase Authentication for secure user registration and login, ensuring data privacy.
*   **Data Storage & Backup:** Leverages Firebase Storage for secure data backup and recovery, providing data resilience.
*   **Data Encryption:** Employs AES encryption to safeguard sensitive data, protecting personal information.
*   **Robust Security:** Implemented Proguard & R8 to prevent reverse engineering and malicious code injection, securing the application's integrity.

## Technology Stack

*   **Business Logic (BA):** Kotlin
*   **Frontend (FE):** Jetpack Compose, enabling a modern and declarative UI
*   **Authentication:** Firebase Authentication, simplifying user sign-in and registration
*   **Data Backup & Recovery:** Firebase Storage, facilitating secure app data backup and restoration
*   **Data Encryption:** AES (Advanced Encryption Standard), providing robust data at rest encryption
*   **Build & Security:** Proguard & R8, ensuring code shrinking, obfuscation, and protection against reverse engineering and code injection

## Architecture

The application adheres to the MVVM (Model-View-ViewModel) architectural pattern for a clean, testable, and maintainable codebase.

*   **Model:** Manages data and business logic, interacting with data sources such as databases and network APIs.
*   **View:** Comprises UI components built using Jetpack Compose, responsible for displaying data and capturing user input.
*   **ViewModel:** Bridges the View and Model, preparing data for display and handling user interactions, independent of UI details.

## Security Measures

To guarantee the security and integrity of the application, the following measures have been implemented:

*   **Data Encryption:** Sensitive data, including user credentials and personal information, are encrypted using AES before storage.
*   **Code Obfuscation:** Proguard and R8 are used to shrink, obfuscate, and optimize the code, significantly hindering reverse engineering attempts.
*   **Malicious Code Injection Prevention:** Proguard/R8 effectively fortify the application against malicious code injection.

## Getting Started

To build and run this project locally, follow these steps:

1.  Clone this repository
2.  Set up a Firebase project and configure Firebase Authentication and Storage for Android
3.  Configure the `google-services.json` file in your Android project (`app/`).
4.  Build and run the project.

This setup ensures that the Real Estate Management Application is not only feature-rich but also built on a solid, secure foundation for reliable performance and data protection.

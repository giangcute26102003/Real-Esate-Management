# Real Estate Management Application

This repository contains the source code for a Real Estate Management application built using modern Android development practices and technologies.

## Overview

This application is designed to streamline the management of real estate properties, customer interactions, and related data. It aims to provide a user-friendly and efficient experience for real estate professionals.

## Key Features

*   **Property Management:** Add, edit, and manage details for various properties, including address, type, size, price, availability, and more.
*   **Customer Management:** Track customer information, preferences, and interaction history.
*   **Requirement Matching:** Match customer requirements to suitable properties.
*   **Secure Authentication:** Utilizes Firebase Authentication for secure user registration and login.
*   **Data Storage & Backup:** Leverages Firebase Storage for secure data backup and recovery.
*   **Data Encryption:** Employs AES encryption to safeguard sensitive data.
*   **Robust Security:** Implemented Proguard & R8 to prevent reverse engineering and malicious code injection.

## Technology Stack

This application is built using the following technologies:

*   **Business Logic (BA):** Kotlin
*   **Frontend (FE):** Jetpack Compose for building a modern and declarative UI
*   **Authentication:** Firebase Authentication for handling user sign-in and registration.
*   **Data Backup & Recovery:** Firebase Storage for securely backing up and restoring app data.
*   **Data Encryption:** AES (Advanced Encryption Standard) for encrypting sensitive data at rest.
*   **Build & Security:** Proguard & R8 for code shrinking, obfuscation, and protection against reverse engineering and code injection.
*   **Architecture:** MVVM (Model-View-ViewModel) architectural pattern for clean, testable, and maintainable code.

## Architecture

The application adheres to the Model-View-ViewModel (MVVM) architectural pattern:

*   **Model:** Handles the data and business logic. This includes interacting with data sources (e.g. database, network).
*   **View:** The UI components built using Jetpack Compose, responsible for displaying data and handling user input.
*   **ViewModel:** Acts as a bridge between the View and Model. It prepares data for the View and handles user input, without having direct knowledge of the UI.

## Security Measures

To ensure the security and integrity of the application, the following measures have been implemented:

*   **Data Encryption:** Sensitive data such as user credentials and personal information are encrypted using AES before being stored.
*   **Code Obfuscation:** Proguard and R8 are used to shrink, obfuscate, and optimize the code. This makes it harder for malicious actors to reverse engineer the application.
*   **Malicious Code Injection Prevention:** By applying Proguard/R8 properly, the application becomes more resistant against malicious code injection.

## Getting Started

To build and run this project locally, follow these steps:
   1. Clone this repository
   2. Set up a Firebase project and configure Firebase Authentication and Storage for Android
   3. Configure `google-services.json` file to your android folder.
   4. Build and run the project.

## Contributing

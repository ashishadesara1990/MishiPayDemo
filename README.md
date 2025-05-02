# MishiPayDemo

MishiPayDemo is a sample Android application designed to demonstrate a streamlined mobile shopping cart experience.
It leverages modern Android development practices, including MVVM architecture, Jetpack libraries, 
and Kotlin, to create a user-friendly and efficient shopping application. 
The application includes a scanner, a cart, and a checkout flow.



## Features

-   **Barcode Scanning:**
    -   A dedicated scanner page capable of scanning both 1-Dimensional and 2-Dimensional barcodes.
    -   Products are automatically added to the cart upon successful scanning.
-   **Shopping Cart:**
    -   Displays a list of added products.
    -   Each item includes:
        -   Product title.
        -   Product price and discount price (if applicable).
        -   Product image.
    -   Users can adjust the quantity of items.
    -   Users can remove products from the cart.
    - Dynamic total calculation.
-   **Checkout Flow:**
    -   An option to checkout from the cart page.
    -   A dummy invoice page to complete the checkout simulation.
-   **Intuitive Navigation:**
    -   Clear navigation between all pages (scanner, cart, invoice).
    -   Users are never stuck on a page and don't need to rely on phone hardware buttons for navigation.
-   **User Experience:**
    - Modern User interface design using Jetpack Compose.
    - Smooth and fluid interaction.

## Tech Stack

-   **Kotlin:** The primary programming language.
-   **Android Jetpack:**
    -   **ViewModel:** For managing UI-related data and lifecycle.
    -   **LiveData/StateFlow:** For observing data changes.
    -   **Compose:** For building the UI.
    -   **Navigation:** For managing in-app navigation.
    -   **Animation:** For adding smooth animations.(Lottie-Compose).

## Architecture

The application follows the **Model-View-ViewModel (MVVM)** architectural pattern.

-   **Model:** Represents the data layer. It includes:
    -   Data classes to hold product and cart information.
-   **View:** The UI components (Activities, Fragments, Composable functions) that display data and handle user interactions.
-   **ViewModel:** The bridge between the View and the Model. It exposes data to the View and contains the logic for handling user interactions and data manipulation.

## Video 
- MishiPayDemo Application flow video.
- https://drive.google.com/file/d/1K5BMZV4s7QZBDKJpOE64nHNkWNjX_L-0/view?usp=sharing
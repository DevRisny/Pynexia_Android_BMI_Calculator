# BMI Calculator (Android, Kotlin)

Pynexia Android Development Internship — Week 1 project.

Calculates Body Mass Index from the height and weight a user enters, and shows the value with its health category.

## Features
- Material 3 UI built with XML (`TextInputLayout`, `MaterialCardView`), light and clean
- Input handling with click listeners; validation with inline error messages
- BMI = weight (kg) ÷ height (m)²
- WHO categories: **Underweight** (< 18.5), **Normal** (18.5–24.9), **Overweight** (25–29.9), **Obese** (≥ 30), colour-coded with a short advice message
- Reset button and keyboard auto-hide

## Project structure
```
app/src/main/java/com/risny/bmicalculator/MainActivity.kt   # logic + event handling
app/src/main/res/layout/activity_main.xml                    # UI
app/src/main/res/values/                                     # strings, colors, theme
```

## Run
Open the folder in Android Studio (Hedgehog or newer), let Gradle sync, then run on an emulator or device (minSdk 24).

Author: Junaideen Risny Suha

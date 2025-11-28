# Android-Studio
SOBRE LA UTILIZACIÓN DE LA APP

Crea un proyecto nuevo de tipo Empty Views Activity 
El proyecto se tiene que crear en kotlin

Añadir libreria GSON:
1. Seleccione en el proyecto build.gradle.kts (Module:app)
2. Implementación de la libreria gson

   implementation("com.google.code.gson:gson:2.13.2") // La libreria puede estar desactualizada 

3. Click derecho + Show content actions
4. Sync now
5. La libreria se te descargara despues de unos segundos

Añadir permisos de acceso a internet:
1. Abrir manifest -> AndroidManifest.xml
2. Añadir en cualquier lado
  
   <uses-permission android:name="android.permission.INTERNET"/>

Creación de la carpeta model
1. Buscar carpeta con el MainActivity
2. Click derecho sobre la carpeta -> new -> Package
3. Llamar al package "model"

Estructura del proyecto: 

**kotlin+java/**
│
├── algo.nombre_de_tu_app/
│ ├── model
│ │ ├── descargalPThread.java
│ │ ├── IPs.java
│ │ └── NetUtils.java
│ └── MainActivity.kt
└── README.md ← Explicación breve del proyecto

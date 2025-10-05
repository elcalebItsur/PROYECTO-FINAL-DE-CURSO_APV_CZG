# Prototipos de la Aplicación

## 1. Pantalla Principal (MainActivity)
```
+---------------------------------+
|  [≡] Notas y Tareas      [🔍]  |
|---------------------------------|
| [📝] Nueva Nota/Tarea          |
|---------------------------------|
| [Notas] | [Tareas] | [Todas]   |
|---------------------------------|
|  Ordenado por: Fecha ▼         |
|---------------------------------|
| 📝 Reunión de trabajo          |
| 14:30 - Pendiente              |
| Con archivos multimedia (3)     |
|---------------------------------|
| 📌 Comprar víveres            |
| Sin fecha - Nota               |
| Con grabación de audio         |
|---------------------------------|
| 📝 Presentación proyecto       |
| Mañana 15:00 - 2 recordatorios |
| Con fotos (2)                  |
+---------------------------------+
```

## 2. Pantalla de Creación/Edición
```
+---------------------------------+
| [←] Nueva Nota/Tarea    [✓]    |
|---------------------------------|
| Título:                         |
| [________________________]      |
|---------------------------------|
| Descripción:                    |
| [                         ]     |
| [                         ]     |
|---------------------------------|
| Tipo:                           |
| (•) Nota  ( ) Tarea            |
|---------------------------------|
| [+ Adjuntar Multimedia]         |
|---------------------------------|
| Archivos adjuntos:             |
| 🖼️ IMG_001.jpg                |
| 🎥 VID_002.mp4                 |
| 🎤 REC_003.mp3                 |
|---------------------------------|
| [Solo si es tarea:]            |
| Fecha límite: [📅 Seleccionar] |
| Hora: [⏰ Seleccionar]         |
| [+ Agregar recordatorio]        |
+---------------------------------+
```

## 3. Vista de Archivo Multimedia
```
+---------------------------------+
| [←] Vista Previa        [⋮]    |
|---------------------------------|
|                                 |
|          [Imagen/Video]         |
|                                 |
|                                 |
|---------------------------------|
| Descripción:                    |
| [________________________]      |
|---------------------------------|
| Fecha: 05/10/2025              |
| Tipo: Imagen/Video/Audio        |
+---------------------------------+
```

## 4. Pantalla de Recordatorios
```
+---------------------------------+
| [←] Recordatorios       [+]    |
|---------------------------------|
| Tarea: Presentación proyecto    |
|---------------------------------|
| 📅 14/10/2025 - 14:30          |
| [Eliminar] [Editar]            |
|---------------------------------|
| 📅 14/10/2025 - 14:00          |
| [Eliminar] [Editar]            |
|---------------------------------|
| [+ Agregar nuevo recordatorio]  |
+---------------------------------+
```

## 5. Barra de Búsqueda Expandida
```
+---------------------------------+
| [←] [________________] [×]     |
|---------------------------------|
| Resultados de búsqueda:         |
|---------------------------------|
| 📝 Reunión de trabajo          |
| Coincide con: "trabajo"        |
|---------------------------------|
| 📝 Presentación trabajo        |
| Coincide con: "trabajo"        |
+---------------------------------+
```

## 6. Vista Tablet (Modo Landscape)
```
+----------------+------------------+
| Lista de       |   Vista Detalle |
| Notas/Tareas   |                |
|                | [Contenido de   |
| 📝 Reunión     |  nota/tarea     |
| 📌 Compras     |  seleccionada]  |
| 📝 Presentación|                |
|                |                |
| [+ Nueva]      |                |
+----------------+------------------+
```

## Notas sobre el diseño:

### Elementos de Material Design a utilizar:
- FloatingActionButton para crear nueva nota/tarea
- BottomAppBar para acciones principales
- Chips para filtros y etiquetas
- Cards para mostrar notas y tareas
- BottomSheet para opciones adicionales
- NavigationDrawer para configuraciones
- Snackbar para confirmaciones

### Características de accesibilidad:
- Soporte para modo oscuro
- Textos escalables
- Alto contraste
- Iconos significativos
- Etiquetas descriptivas

### Adaptabilidad:
- Layout responsivo para diferentes tamaños de pantalla
- Grid layout para tablets
- Vista dividida en landscape para tablets
- Densidad de información ajustable

### Gestos soportados:
- Swipe para acciones rápidas
- Pull-to-refresh para actualizar
- Pinch-to-zoom en multimedia
- Long press para menú contextual

### Interacción:
- Transiciones suaves entre pantallas
- Feedback táctil en acciones importantes
- Animaciones sutiles
- Estados de carga visibles
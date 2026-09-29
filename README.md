# Smart Pantry Manager

## Description
The Smart Pantry Manager is an Android application engineered to minimize household food waste.

1.Database Choice: Why SQLite
For the data persistence layer of the Smart Pantry Manager, SQLite was chosen via Android's native SQLiteOpenHelper framework [2026 Edition].
This selection was made over alternative solutions (such as SharedPreferences, or cloud databases like Firebase) based on the following engineering requirements:

1.1 Relational Data Integrity via SQLjoins
The core value proposition of the application is its strict-matching recipe rule (e.g., if a recipe requires 5 ingredients and the user only has 4, it must be
excluded entirely).
- SQLite excels at handling structured relational schemas. By splitting data into three normalized tables (pantry, recipes, and a junction table recipe_ingredients)
  , the application ensures high data integrity.
- This allows the strict-matching logic to be executed directly in the database engine using an optimized LEFT JOIN and a HAVING COUNT() clause. Performing this
   sorting at the database level eliminates the need to pull heavy datasets into memory, keeping the app lightweight and fast.

1.2 Zero Cloud Dependency & Offline Reliability
  Food tracking applications are frequently used in environments with intermittent network coverage (like pantry closets, basements, or grocery stores).
  - SQLite is an in-process, serverless database system. It stores data locally in a single file on the device.
  - This eliminates the need for user authentication, API endpoints, or network overhead, guaranteeing 100% offline availability and low latency.

1.3 Lightweight Footprint and Zero Configuration
Unlike heavier enterprise database layers, SQLite requires no complex background server configuration. It is built natively into the Android Operating Sytem,
reducing the final compiled APK size and minimizing battery consumption on consumer mobile devices.

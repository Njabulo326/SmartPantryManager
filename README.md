# Smart Pantry Manager

## Description
The Smart Pantry Manager is an Android application engineered to minimize household food waste.

## Database Strategy Choice
-**Engine Selected: ** SQLite via SQLiteOpenHelper.
-**Reasoning: ** A local relational architecture provides reliable relational query mapping through simple 'JOIN' syntax. This allows for complex filtering directly on the device
with zero cloud dependency, low latency, and no active internet connection requirements.

## Installation and Execution Steps
1. Clone this public repository into your active local workspace directory.
2. Launch Android Studio and click **File > Open**, selecting the cloned root folder structure.
3. Allow Gradle to synchronize project dependencies.
4. Target a simulated virtual device profile running Android 11.0 (API Level 30) or higher.
5. Click **Run 'app'** to launch and build the package project.

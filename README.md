# SCapp-complete
This app uses Firebase for storage, authentication, and database. The app also utilizes external libraries such as Glide for image loading and Android Image Cropper for image cropping.

Installation
1. Clone this repository to your local machine using https://github.com/GDSC-KOU-Tech-Team/SCapp-complete
2. Open the project in Android Studio.
3. Create a new Firebase project and add your Android app to the project. Download the google-services.json file and add it to the app directory in the project.
4. In the Firebase Console, enable Email/Password authentication and Cloud Firestore.
5. In the Firebase Console, create a Cloud Storage bucket to store the images.
6. In the build.gradle file for the app module, add the following dependencies:

Copy code
implementation 'com.google.firebase:firebase-storage:19.2.1'
implementation 'com.google.firebase:firebase-auth:21.0.1'
implementation 'com.google.firebase:firebase-firestore:23.0.1'
implementation 'com.github.bumptech.glide:glide:4.12.0'
implementation 'com.theartofdev.edmodo:android-image-cropper:2.8.+'

1. Sync the project with Gradle.
2. Run the app on an emulator or a physical device.

Usage
1. Register a new account or log in to an existing account.
2. Select an image from the device's gallery.
3. Add a comment and a username.
4. Share the photo with others.
5. View your shared photos in the Home screen.
6. Create new events using event calendar.
7. See events created by others

Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
Please make sure to update tests as appropriate.

License
MIT



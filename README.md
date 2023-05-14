# RateMyApp
[![](https://jitpack.io/v/RonLulwi/RateMyApp.svg)](https://jitpack.io/#RonLulwi/RateMyApp)
[![API](https://img.shields.io/badge/API-26%2B-green.svg?style=flat)]()
![GitHub repo size](https://img.shields.io/github/repo-size/RonLulwi/RateMyApp)

A library for simple implementation of smart ranking.
The user will see a dialog every x time.
If the user gives a high score(=5), he will be transferred to the Google store. If he gives a low score(<4), feedback dialog will appear.

![rateMyApp_feedbackDialog](https://github.com/RonLulwi/RateMyApp/assets/95926852/04b22fcb-792b-4043-91c6-45f5ce6159fb)

![rateMyApp_marketDialog](https://github.com/RonLulwi/RateMyApp/assets/95926852/77d0ceaf-d4d4-4fa6-8eea-fc555fc207fd)

![rateMyApp_starsDialog](https://github.com/RonLulwi/RateMyApp/assets/95926852/ba18ffb9-c5ad-4c83-b27a-8f551bddc55c)


## Setup
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
	maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency:

```
dependencies {
	implementation 'com.github.RonLulwi:RateMyApp:1.00.01'
}
```
## Usage

###### Use the library:
```java

// Initiate Rate Dialog:
        RateMyApp.reteMeOnMarketDialog(MainActivity.this, MainActivity.this, R.mipmap.ic_launcher, new RateMyApp.FeedbackReceivedListener() {
            @Override
            public void onFeedbackReceived(String feedback) {
                // Handle user feedback here.
            }
        });
);

```


## Credits

5 stars icon:
Icon made by Flat Icons (www.flat-icons.com) from www.flaticon.com
Google play icon:
Icon made by Flat Icons (www.flat-icons.com) from www.flaticon.com

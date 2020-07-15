# Android LiveSharedPreferences Library
Simple library to provide reactive sharedpreferences using RxJava.
By using this library you will have the ability to get notified when the sharedpreferences data is changed.

# Gradle dependency 
Add this dependency in your module's build.gradle file:
```
dependencies {
    implementation 'com.github.Abdulrahman-Tayara:LiveSharedPreferences:1.0'
    
    // RxJava/RxAndroid
    implementation 'io.reactivex.rxjava2:rxjava:2.2.17'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
}
```
Add repository to your app's build.gradle file :
```
allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
}
```
About RxJava: [a link](https://github.com/ReactiveX/RxJava)

# Example

```Java
// Init
ISharedPreferences sharedPreferences = new LiveSharedPreferences(context);

// save the data before the subscribe
sharedPreferences.putInt("KEY", 10);

// Subscribe
Disposable disposable = sharedPreferences.getIntObservable("KEY", DEFAULT_VALUE)
                .subscribe(newData -> {
                    System.out.println("new data: " + newData);
                });        
  
// save new data
sharedPreferences.putInt("KEY", 20);
sharedPreferences.putInt("KEY", 20);        

/*
   Output:
   new data: 10
   new data: 20
   new data: 30        
*/        
```

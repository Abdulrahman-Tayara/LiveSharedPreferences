# Android LiveSharedPreferences Library
Simple library to provide reactive sharedpreferences using RxJava.
By using this library you will have the ability to get notified when the sharedpreferences data is changed and you will able to save an object in sharedpreferences.

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
About [RxJava](https://github.com/ReactiveX/RxJava)

# Example

```Java
// Init
ISharedPreferences liveSharedPreferences = new LiveSharedPreferences(context);

// save the data before the subscribe
liveSharedPreferences.putInt("KEY", 10);

// Subscribe
Disposable disposable = liveSharedPreferences.getIntObservable("KEY", DEFAULT_VALUE)
                .subscribe(newData -> {
                    Log.d("Log", "new data: " + newData);
                });        
  
// save new data
liveSharedPreferences.putInt("KEY", 20);
liveSharedPreferences.putInt("KEY", 20);        

/*
   Output:
   new data: 10
   new data: 20
   new data: 30        
*/        

// Using normal sharedPreferences
int value = liveSharedPreferences.getInt("KEY", DEFAULT_VALUE);



// Save an object
class User {
    String username;
    String email;
    
    User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}

User user = new User("Abdulrahman", "test@test.com");

liveSharedPreferences.putObject<User>("OBJECT_KEY", user);

// Retrieve the object using observable
Disposable disposable = liveSharedPreferences.getObjectObservable<User>("OBJECT_KEY")
                .subscribe(savedUser -> {
                    Log.d("Log", "username: " + savedUser.username);
                });
                
// Retrieve the object directly
User savedUser = liveSharedPreferences.getObject<User>("OBJECT_KEY");       
```

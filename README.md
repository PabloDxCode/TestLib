# TestLib
Proyect example of library on jitpack

## Add lib

Add in .gradle of project:

#### Gradle

.gradle of project:
```groovy
allprojects {
   repositories {
   ...
   maven { url 'https://jitpack.io' }
   }
}
```
.gradle of main module:
```groovy
dependencies {
   implementation 'com.github.PabloDxCode.TestLib:core:1.0.6'
   implementation 'com.github.PabloDxCode.TestLib:component-box:1.0.4'
   implementation 'com.github.PabloDxCode.TestLib:debugging-log:1.0.4'
}
```

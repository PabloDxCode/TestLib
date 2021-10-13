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
   implementation 'com.github.PabloDxCode:TestLib:Tag'
}
```

Add in .gradle of project:

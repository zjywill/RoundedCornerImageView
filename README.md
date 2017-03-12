# RoundedCornerImageView
-------------------------------------
In some cases, you need to make your image with rounded corner. But Glide may be limited, the rounded corner may disappear when image scaled. So this library came to fix this problem.

</br>
<img width="40%" src="https://github.com/zjywill/RoundedCornerImageView/blob/master/SampleImage/screenshot.png?raw=true">
</br>

# Example
------------------------------------
xml code
```xml

<com.comix.rounded.RoundedCornerImageView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/imageView1"
    android:layout_width="60dp"
    android:layout_height="60dp"
    android:layout_gravity="center_horizontal"
    android:layout_marginTop="16dp"
    app:cornerBottomDisable="false"
    app:cornerLeftDisable="false"
    app:cornerRightDisable="false"
    app:cornerTopDisable="false"
    app:cornerColor="@android:color/white"
    app:cornerRadius="6dp"
    />
```

# Installation
Gradle
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

```
dependencies {
        compile 'com.github.zjywill:RoundedCornerImageView:1.1.0'
}
```

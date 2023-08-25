plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android-extensions")
    id("kotlin-kapt")

}

android {
    namespace = "com.doubleclick.doctorapp.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.doubleclick.doctorapp.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
        packagingOptions {
            exclude("META-INF/DEPENDENCIES")
            exclude("META-INF/LICENSE")
            exclude("META-INF/LICENSE.txt")
            exclude("META-INF/license.txt")
            exclude("META-INF/NOTICE")
            exclude("META-INF/NOTICE.txt")
            exclude("META-INF/notice.txt")
            exclude("META-INF/ASL2.0")
            exclude("META-INF/*.kotlin_module")
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true

    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.2.1")
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.compose.foundation:foundation:1.2.1")
    implementation("androidx.compose.material:material:1.2.1")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("com.squareup.picasso:picasso:2.8")

    implementation("com.karumi:dexter:6.2.3")

    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:1.0.0-rc03")
    //data-store
    implementation("androidx.datastore:datastore-preferences:1.0.0-rc02")
    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation("androidx.datastore:datastore-core:1.0.0")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")

    implementation("com.iceteck.silicompressorr:silicompressor:2.2.4")

    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("com.airbnb.android:lottie:5.2.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
//    implementation("androidx.preference:preference:1.2.0")
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    implementation("androidx.preference:preference:1.1.1")

//    implementation(files("libs\\duo-navigation-drawer-release.aar"))
//    implementation(files("libs\\drawernavigationslider-release.aar"))

    compileOnly("com.nineoldandroids:library:2.4.0")

    implementation("org.jitsi.react:jitsi-meet-sdk:+") {
//        transitive = true
    }

    implementation(files("libs\\CalenderView-release.aar"))
    implementation(files("libs\\MultiSearchView-release.aar"))
    implementation(files("libs\\pix-release.aar"))


    // CameraX core library using camera2 implementation
    implementation("androidx.camera:camera-camera2:1.1.0-alpha05")
    // CameraX Lifecycle Library
    implementation("androidx.camera:camera-lifecycle:1.1.0-alpha05")
    // CameraX View class
    implementation("androidx.camera:camera-view:1.0.0-alpha25")
    // If you want to additionally use the CameraX Extensions library
    implementation("androidx.camera:camera-extensions:1.0.0-alpha25")

    kapt("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.github.bumptech.glide:recyclerview-integration:4.12.0") {
        // Excludes the support library because it's already included by Glide.
    }

//    implementation ("com.kizitonwose.calendar:view:2.2.0")

    implementation("com.airbnb.android:lottie:5.2.0")

    implementation("com.android.support:palette-v7:25.0.0")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    implementation("dev.turingcomplete:kotlin-onetimepassword:2.4.0")

    implementation("androidx.room:room-runtime:2.4.3")
    implementation("androidx.room:room-rxjava2:2.4.3")
    implementation("android.arch.paging:runtime:1.0.1")
    implementation("commons-codec:commons-codec:1.15")

    implementation("com.googlecode.ez-vcard:ez-vcard:0.11.3")

    implementation("com.iceteck.silicompressorr:silicompressor:2.2.4")

    implementation("com.android.volley:volley:1.2.0")
    implementation("org.apache.httpcomponents:httpclient:4.5")

    //Agora
//    implementation ("io.agora.rtc:full-sdk:3.3.1")
    implementation(group = "io.agora.rtc", name = "full-sdk", version = "4.1.1")

    implementation("com.google.android.exoplayer:exoplayer:2.12.2")

    implementation ("androidx.media3:media3-exoplayer:1.0.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.0.1")
    implementation("androidx.media3:media3-ui:1.0.1")
    implementation("androidx.media3:media3-exoplayer-hls:1.0.1")
    implementation("com.github.bjoernpetersen:m3u-parser:1.3.0")
    implementation("androidx.media3:media3-datasource-okhttp:1.0.1")
    implementation ("androidx.media3:media3-exoplayer-smoothstreaming:1.0.1")


}
plugins {
    id("com.android.application") version "8.5.0" apply false
    kotlin("android") version "2.0.0" apply false
    kotlin("kapt") version "2.0.0" apply false
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}

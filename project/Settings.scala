import android.Keys._
import sbt.Keys._
import sbt._

object Settings {

  val scalaV = "2.11.4"

  lazy val appSettings = commonSettings ++
      Seq(
        run <<= run in Android,
        transitiveAndroidLibs in Android := false,
        proguardScala in Android := false,
        useProguard in Android := false,
        proguardOptions in Android ++= Seq(
          "-ignorewarnings",
          "-keepattributes Signature",
          "-keepattributes InnerClasses",
          "-dontwarn scala.collection.**",
          "-keep class scala.Dynamic",
          "-keep class macroid.** { *; }",
          "-keep class android.** { *; }",
          "-keep class com.google.** { *; }"
        )
      )

  lazy val androidLibSettings = android.Plugin.androidBuildAar ++
      commonSettings ++
      Seq(
        exportJars := true
      )

  lazy val commonSettings = Seq(
    scalaVersion := scalaV,
    resolvers ++= commonResolvers,
    libraryDependencies ++= commonDependencies)

  lazy val commonDependencies = Seq(
    aar("com.android.support" % "appcompat-v7" % "21.0.0"),
    aar("com.android.support" % "recyclerview-v7" % "21.0.0"),
    aar("com.android.support" % "cardview-v7" % "21.0.0"),
    aar("org.macroid" %% "macroid" % "2.0.0-M3"),
    compilerPlugin("org.brianmckenna" %% "wartremover" % "0.10"))

  lazy val commonResolvers = Seq(
    Resolver.sonatypeRepo("releases"),
    "jcenter" at "http://jcenter.bintray.com"
  )
}
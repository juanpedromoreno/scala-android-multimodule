import sbt._
import sbt.Keys._
import android.Keys._
import Settings._

object AppBuild extends Build {

  def excludeArtifact(module: ModuleID, artifactOrganizations: String*): ModuleID =
    module.excludeAll(artifactOrganizations map (org => ExclusionRule(organization = org)): _*)

  lazy val root = Project(id = "root", base = file("."))
      .settings(
        scalaVersion := scalaV,
        platformTarget in Android := "android-21",
        packageT in Compile <<= packageT in Android in app,
        packageRelease <<= packageRelease in Android in app,
        packageDebug <<= packageDebug in Android in app,
        install <<= install in Android in app,
        run <<= run in Android in app,
        packageName in Android := "com.example.app"

      )
      .aggregate(app, androidLib)

  lazy val app = Project(id = "app", base = file("modules/app"))
      .androidBuildWith(androidLib)
      .settings(projectDependencies ~= (_.map(excludeArtifact(_, "com.android"))))
      .settings(appSettings: _*)

  val androidLib = Project(id = "androidLib", base = file("modules/androidLib"))
      .settings(androidLibSettings: _*)
}
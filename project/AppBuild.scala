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
        compile <<= compile in(app, Android),
        install <<= install in(app, Android),
        run <<= run in(app, Android))
      .aggregate(app, androidLib)

  lazy val app = Project(id = "app", base = file("modules/app"))
      .settings(appSettings: _*)
      .androidBuildWith(androidLib)
      .settings(projectDependencies ~= (_.map(excludeArtifact(_, "com.android"))))

  val androidLib = Project(id = "androidLib", base = file("modules/androidLib"))
      .settings(androidLibSettings: _*)
}
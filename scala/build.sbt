name := "tlp"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "TDL" at "http://dl.bintray.com/julianghionoiu/maven"

def scalatest = "org.scalatest" %% "scalatest" % "2.2.4" % "test"
def client = "ro.ghionoiu" % "tdl-client-java" % "0.11.1"

scalacOptions += "-Xexperimental"

libraryDependencies ++= Seq(
  client,
  scalatest
)
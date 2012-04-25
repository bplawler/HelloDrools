name := "HelloDrools"

version := "0.1"

libraryDependencies ++= Seq (
    "org.jvnet.jaxb2_commons" % "jaxb-xjc" % "2.1.10.1", 
    "org.drools" % "drools-core" % "5.3.1.Final",
    "org.drools" % "drools-compiler" % "5.3.1.Final"
)

libraryDependencies += "org.specs2" %% "specs2" % "1.9" % "test"

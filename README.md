# Spray MongoDB project

[g8](http://github.com/n8han/giter8) template to get a Scalatra web service up and running quickly with MongoDB support using Casbah. The template generates a project that uses SBT 0.11.2. It will not build under 0.7.x or 0.10.x versions of SBT. 

## Usage

Install giter8 (g8) - [readme](http://github.com/n8han/giter8#readme) for more information.

Install SBT 0.11.1 - [Setup](https://github.com/harrah/xsbt/wiki/Setup) for more information.

In a shell, run the following:

    g8 JanxSpirit/spray-mongodb
    cd <name-of-app>
    sbt
    > update
    > container:start
    
You should be able to browse to a [test resource](http://localhost:8080/test)

## What's inside

The servlet provides two resources:

1. 'test' - a 'Hello World' type resource just to make sure everything is working.
2. 'msgs' - GET msgs to see all records currently in the database and a form to add another record - the form issues a POST to the same resource to add records

The project includes a few basic tests to get you started. Run 'sbt test' to see the test output.

The template allows you to configure where your MongoDB server is running, the name of your project and other parameters.

## Executable Jar

At the SBT prompt you can run 'assembly' to generate an executable jar in the /target directory, It bundles Jetty and Scala and takes an optional argument for port (default is 8080).
> java -jar /target/myproject-assembly.jar

## Standalone Shell Script

In the root of the project, run something like

> cat src/main/resources/execute_jar.sh target/spray-mongodb-project-assembly-1.0.jar > target/spray_mongo

That will make a handy standalone shell command called target/spray_mongo that wraps the executable jar created above. chmod +x that script and you can run your app with no 'java -jar ...'

## Thanks

Thanks to [Nathan Hamblen](https://github.com/n8han) for [giter8](https://github.com/n8han/giter8), [Brendan McAdams](https://github.com/bwmcadams) for the [Casbah](https://github.com/mongodb/casbah) MongoDB Driver, [Mathias](https://github.com/sirthias) for [Spray](https://github.com/spray/spray)
and [Coda Hale](https://github.com/codahale) and [Eugene Yokota](https://github.com/eed3si9n) for the [SBT Assembly Plugin](https://github.com/eed3si9n/sbt-assembly).

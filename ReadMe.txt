This is a submission for the Blockchain.com coding challenge.

The core of the application (the "app" module) is pure JVM, as is the basic domain model
(the "model" module). The "app-android" module serves to bootstrap the process, and
supplies adapters to the Android GUI and various services to the ports defined by the app.

The implementation uses Retrofit for calling the REST API, with its RxJava connector for
managing the asynchronous request and delivering the data to the app.

Unit Testing
------------

I decided to experiment a bit and try Spek for unit tests. It seems to still be less
mature than rSpec was when I last used that, but shows promise. It is certainly more
concise than the equivalent in straight JUnit 4/5.

To run Spek specifications in IntelliJ IDEA or Android Studio, install the Spek Framework
plugin (Preferences > Plugins > search for "Spek Framework").

https://plugins.jetbrains.com/plugin/10915-spek-framework

There is a run configuration included to execute all of the app module specs (the only
module that has specs at the time of writing). Running individual specs from the icons in
the editor margin does not seem to work at this time.

Machine Learning - Hands On For Developers and Technical Professionals

All the code covered in the book is here. There's also a directory with the test data used.
 
Java
=====
Within the src directory are the chapters as package names. Within each package name is the code for that chapter. The Apache Spark examples from Chapter 11 are run via the Spark program; please refer to Chapter 11.

I've supplied a Maven pom file which will pull down the required jar files to build.

Where possible I've not used path names to data files. Change as required then recompile.

Scala
=====
The Scala examples are covered in Chapter 11. The sbt build files are there for each project.To run them within Apache Spark, look at the run information in Chapter 11.

R
=====
The R examples are quite simple. You need to install the libraries 
required (these are outlined in the book in Chapter 12). Then load them 
into R using the source() function.

For example:
 >source('sentiment.r')

And it will run the script. For the Twitter OAuth example you need to 
edit the file with your Twitter app credentials. See Chapter 9 on how 
to do this.


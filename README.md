JSQL
===========

[<img src="https://api.travis-ci.org/rbuck/jsql.png?branch=master" alt="Build Status" />](http://travis-ci.org/rbuck/jsql)

# Description

A simple SQL lexer/parser implementation written in Java that uses JFlex
under the covers. It's just a simple project I mocked up to experiment with
the SQL dialect, concepts, and to have fun with query parsers. Nothing
production quality here, albeit interesting from a learning standpoint with
respect to using SQL with the Java type system.

# Dependencies

The project has the following dependencies:

    JavaCC (net.java.dev.javacc) 5.0

# Build Procedure

To compile and test the project issue the following commands:

    mvn clean install

To release the project issue the following commands:

    mvn release:clean
    mvn release:prepare -Dgpg.passphrase= -Dgpg.keyname=
    mvn release:perform

# License

See the LICENSE file herein.

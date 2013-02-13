
java  -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 ../target/lifecycle-core-1.0-SNAPSHOT.jar server ../src/main/resources/conf/lifecycle.json 
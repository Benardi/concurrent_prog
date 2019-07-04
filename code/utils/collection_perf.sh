#!/usr/bin/bash

SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"

echo "class,elements,total_time,threads,write_level" > ${SCRIPTPATH}/../../output/list1/ex5/report.csv
javac ${SCRIPTPATH}/../list1/java-code/ex5/Main.java
java -classpath ${SCRIPTPATH}/../list1/java-code/ex5/ Main >> ${SCRIPTPATH}/../../output/list1/ex5/report.csv

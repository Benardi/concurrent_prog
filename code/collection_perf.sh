main_path="../java-code/src/list-1/ex_05/"

echo "class,elements,total_time,threads,write_level" > ../output/report.csv
javac ${main_path}Main.java
java -classpath ${main_path} Main >> ../output/report.csv

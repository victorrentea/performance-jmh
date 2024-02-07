mvn clean package

java -jar target/benchmarks.jar -prof stack:lines=5;top=3;detailLine=true;excludePackages=true;period=1


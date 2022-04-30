helios:
	ssh -p2222 -Y -L 5432:pg:5432 s283945@se.ifmo.ru

build:
	mvn clean package

deploy:
	scp -P 2222 ./target/blps.jar s283945@se.ifmo.ru:.
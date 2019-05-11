-- this will run a zookeeper instance:
docker run -p 2181:2181 -p 3888:3888 -p 2888:2888 --name some-zookeeper --restart always -d zookeeper

-- this will start zookeeper cli
docker run -it --rm --link some-zookeeper:zookeeper zookeeper zkCli.sh -server zookeeper
FROM cassandra

RUN apt-get update && apt-get install -y iputils-ping

ADD modified-entrypoint.sh /usr/bin

RUN mkdir /cassandra

ADD database-schema.cql /cassandra
ADD database-schema.sh /cassandra
ADD truncate-database.cql /cassandra
ADD truncate-database.sh /cassandra

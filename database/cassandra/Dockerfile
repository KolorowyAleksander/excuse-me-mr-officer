FROM cassandra

USER root

RUN apt-get update && apt-get install -y iputils-ping

ADD modified-entrypoint.sh /usr/bin

ENTRYPOINT ["bash", "/usr/bin/modified-entrypoint.sh"]

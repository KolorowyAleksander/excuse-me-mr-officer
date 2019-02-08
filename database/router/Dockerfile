FROM debian

USER root

ADD entrypoint.sh /usr/bin

ENTRYPOINT ["bash", "/usr/bin/entrypoint.sh"]

# run the container indefinitely
CMD ["tail", "-f", "/dev/null"]
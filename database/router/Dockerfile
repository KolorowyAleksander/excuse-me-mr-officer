FROM debian

USER root

ADD entrypoint.sh /usr/bin
RUN chmod +x /usr/bin/entrypoint.sh

ENTRYPOINT ["bash", "/usr/bin/entrypoint.sh"]

# run the container indefinitely
CMD ["tail", "-f", "/dev/null"]
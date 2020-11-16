ARG JAVA_BASE=11-jre
FROM openjdk:${JAVA_BASE}
ENV JAVA_VERSION=${JAVA_BASE}

VOLUME /tmp

COPY ./target/yals.jar /app/
COPY ./docker-entrypoint.sh /
ADD https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.19.0/elastic-apm-agent-1.19.0.jar /apm-agent.jar

RUN sh -c 'chmod +x /docker-entrypoint.sh'
RUN sh -c 'apt-get update && apt-get upgrade -y && apt-get install -y netcat curl jq && apt -y autoremove && rm -rf /var/lib/apt/lists/*'

ENTRYPOINT ./docker-entrypoint.sh

EXPOSE 8080

HEALTHCHECK --start-period=60s --interval=5s --timeout=20s --retries=3 \
   CMD curl --silent --request GET http://127.0.0.1:8080/actuator/health \
                   | jq --exit-status '.status == "UP"' || exit 1

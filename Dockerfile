FROM redhat-openjdk-18/openjdk18-openshift
USER root
COPY   jumiapay/target/jumiapay-0.0.1-SNAPSHOT.jar /app.jar
RUN set -x \
    && chown jboss:root /app.jar \
	&& chmod og+rwx /app.jar
ENV JAVA_OPTS=""
EXPOSE 2020
USER jboss
ENTRYPOINT ["java","-jar", "/app.jar" ]

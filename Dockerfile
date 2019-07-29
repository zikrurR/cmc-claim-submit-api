ARG APP_INSIGHTS_AGENT_VERSION=2.3.1
FROM hmctspublic.azurecr.io/base/java:openjdk-8-distroless-1.0

LABEL maintainer="https://github.com/hmcts/cmc-claim-submit-api"

COPY lib/applicationinsights-agent-2.3.1.jar lib/AI-Agent.xml /opt/app/
COPY build/libs/cmc-claim-submit-api.jar /opt/app/

EXPOSE 4550
CMD [ "cmc-claim-submit-api.jar" ]

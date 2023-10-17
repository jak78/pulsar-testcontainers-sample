# Pulsar+TestContainers sample

## Goal

Illustrate how to test an Apache Pulsar client using TestContainers.

Disclaimer: This sample Pulsar client code is not designed for production use. It requires further hardening. Shortcuts have been taken for the sake of simplicity.

## Running the test

```bash
mvn test
```

The first time you launch this command, it can take several minutes to download the Apache Pulsar Docker image. The second time should take only a few seconds.

This has been tested on OpenJDK 19 on a MacBook M1.

package com.challenge.wallet.support;

import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaContainerConfiguration extends KafkaContainer {

  private static final String IMAGE_VERSION = "confluentinc/cp-kafka:5.4.3";
  private static KafkaContainerConfiguration container;

  private KafkaContainerConfiguration() {
    super(DockerImageName.parse(IMAGE_VERSION));
  }

  public static KafkaContainerConfiguration getInstance() {
    if (container == null) {
      container = new KafkaContainerConfiguration();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("BOOTSTRAP_SERVERS", container.getBootstrapServers());
  }

  @Override
  public void stop() {
    // do nothing, JVM handles shut down
  }
}

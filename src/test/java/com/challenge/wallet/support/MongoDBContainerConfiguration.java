package com.challenge.wallet.support;

import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoDBContainerConfiguration extends MongoDBContainer {

  private static final String IMAGE_NAME = "mongo:4.0.10";
  private static MongoDBContainerConfiguration container;

  private MongoDBContainerConfiguration() {
    super(DockerImageName.parse(IMAGE_NAME));
  }

  public static MongoDBContainerConfiguration getInstance() {
    if (container == null) {
      container = new MongoDBContainerConfiguration();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("MONGODB_URI", container.getReplicaSetUrl());
  }
}
package com.challenge.wallet.support;

import static java.lang.Long.min;
import static java.lang.Thread.sleep;

import com.challenge.wallet.WalletApplication;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("container-test")
@SpringBootTest(
    webEnvironment = WebEnvironment.DEFINED_PORT,
    classes = WalletApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class TestContainerSupport {

  public static final MongoDBContainerConfiguration mongoContainer;

  public static final KafkaContainerConfiguration kafkaContainer;

  @Autowired
  private ApplicationContext applicationContext;

  static {
    kafkaContainer = KafkaContainerConfiguration.getInstance();
    kafkaContainer.start();
    mongoContainer = MongoDBContainerConfiguration.getInstance();
    mongoContainer.start();
  }

  @Before
  public abstract void init();

  protected <T> void waitUntil(final Integer limitCount, final T id, final Predicate<T> predicate) {
    final long increment = 250;
    int count = 0;
    try {
      do {
        count++;
        await(min(increment * count, 3000));
      } while (!predicate.test(id) && count < limitCount);
    } catch (final Exception e) {
      log.error("Fail to wait until condition", e);
    }
  }

  protected void await(final Long millis) {
    try {
      sleep(millis);
    } catch (final InterruptedException e) {
      log.error("Thread sleeping has been failed", e);
    }
  }

  protected ExceptionHandlerExceptionResolver getExceptionResolver() {
    final ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
    resolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    resolver.setApplicationContext(applicationContext);
    resolver.afterPropertiesSet();
    return resolver;
  }
}

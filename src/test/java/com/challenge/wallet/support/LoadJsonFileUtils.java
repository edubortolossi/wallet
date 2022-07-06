package com.challenge.wallet.support;

import com.challenge.wallet.utils.JsonUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadJsonFileUtils {

  private static final String PATH_FORMAT = "%s/%s/%s";
  private final JsonUtils jsonUtils;

  public <T> T asObject(final String folderPath, final String scenario,
      final String fileName, final Class<T> clazz) throws IOException {
    val filePath = String.format(PATH_FORMAT, folderPath, scenario, fileName);
    return jsonUtils.toObject(asString(filePath), clazz);
  }

  public String asString(final String filePath) throws IOException {
    final File resource = new ClassPathResource(filePath).getFile();
    return new String(Files.readAllBytes(resource.toPath()));
  }

  public String asString(final String folderPath,
      final String scenario,
      final String fileName) throws Exception {
    val filePath = String.format(PATH_FORMAT, folderPath, scenario, fileName);
    return asString(filePath);
  }
}

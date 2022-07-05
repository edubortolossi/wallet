package com.challenge.wallet.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.jar.asm.TypeReference;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonUtils {

  private static final String ERROR_TO_CONVERT_JSON_TO_OBJECT = "Error to convert Json to Object";
  private static final String ERROR_TO_CONVERT_OBJECT_TO_JSON = "Error to convert Object to Json";
  private static final String ERROR_TO_CONVERT_OBJECT_TO_OBJECT =
      "Error to convert Object(%s) to Object(%s)";

  private final ObjectMapper objectMapper;

  public <T> T convertValue(final Object object, final Class<T> valueType) {
    return objectMapper.convertValue(object, valueType);
  }

  public <T> T toObject(final String json, final Class<T> valueType) {
    try {
      return objectMapper.readValue(json, valueType);
    } catch (Exception ex) {
      throw new RuntimeException(ERROR_TO_CONVERT_JSON_TO_OBJECT, ex);
    }
  }

  public String toJson(final Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception ex) {
      throw new RuntimeException(ERROR_TO_CONVERT_OBJECT_TO_JSON, ex);
    }
  }
}

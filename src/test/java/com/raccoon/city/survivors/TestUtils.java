package com.raccoon.city.survivors;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raccoon.city.survivors.dto.SurvivorDTO;
import com.raccoon.city.survivors.service.SurvivorService;

public class TestUtils {

  public static <T> T getResource(String resourceName, Class<T> resourceClass) {
    ObjectMapper mapper = new ObjectMapper();

    String payload = getPayload(resourceName);

    T result = null;

    try {
      result = mapper.readValue(payload, resourceClass);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

  public static <T> List<T> getResourceList(String resourceName, Class<T> resourceClass) {
    ObjectMapper mapper = new ObjectMapper();

    String payload = getPayload(resourceName);

    List<T> result = new ArrayList<>();

    try {
      result = mapper.readValue(payload, mapper.getTypeFactory().constructCollectionType(List.class, resourceClass));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

  public static String getPayload(String payload) {
    Class<TestUtils> clazz = TestUtils.class;

    InputStream inputStream = clazz.getResourceAsStream("/payloads/" + payload + ".json");

    return readFromInputStream(inputStream);
  }

  private static String readFromInputStream(InputStream inputStream) {
    StringBuilder resultStringBuilder = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        resultStringBuilder.append(line).append("\n");
      }

      return resultStringBuilder.toString();
    } catch (Exception e) {
      return "";
    }
  }

  public static void loadSurvivors(SurvivorService service, List<SurvivorDTO> survivors) {
    survivors.forEach(it -> service.save(it));
  }
}
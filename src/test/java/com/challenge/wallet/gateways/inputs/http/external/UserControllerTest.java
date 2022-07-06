package com.challenge.wallet.gateways.inputs.http.external;

import static org.skyscreamer.jsonassert.JSONAssert.*;

import com.challenge.wallet.gateways.outputs.mongodb.documents.UserDocument;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.UserRepository;
import com.challenge.wallet.support.LoadJsonFileUtils;
import com.challenge.wallet.support.TestContainerSupport;
import com.challenge.wallet.support.utils.HttpCaller;
import com.challenge.wallet.usecases.DeleteUser;
import com.challenge.wallet.usecases.FindAllUsers;
import com.challenge.wallet.usecases.SaveUser;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class UserControllerTest extends TestContainerSupport {

  @Autowired
  private SaveUser saveUser;

  @Autowired
  private DeleteUser deleteUser;

  @Autowired
  private FindAllUsers findAllUsers;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private LoadJsonFileUtils loadJsonFileUtils;

  @Autowired
  private HttpCaller httpCaller;

  private static final String FOLDER_TEST_PATH = "container-tests/user-controller";
  private static final String USER_REQUEST_FILENAME = "user-request.json";
  private static final String USER_DOCUMENT_FILENAME = "user-document.json";
  private static final String USER_EXPECTED_RESPONSE_FILENAME = "user-expected-response.json";

  @Override
  public void init() {
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("Should save user")
  public void scenario01() throws Exception {
    var request = getUserRequestJson("scenario-01");
    var expectedPostResponse = getExpectedPostResponseJson("scenario-01");
    ResponseEntity<String> postResponse = httpCaller.post("/user", request);
    Assert.assertEquals(200, postResponse.getStatusCodeValue());
    assertEquals(expectedPostResponse, postResponse.getBody(), JSONCompareMode.STRICT);
  }

  @Test
  @DisplayName("Should save user with conflict")
  public void scenario02() throws Exception {
    var request = getUserRequestJson("scenario-02");
    var document = getUserDocument("scenario-02");
    userRepository.save(document);
    ResponseEntity<String> postResponse = httpCaller.post("/user", request);
    Assert.assertEquals(409, postResponse.getStatusCodeValue());
  }

  @Test
  @DisplayName("Should delete user")
  public void scenario03() throws Exception {
    var document = getUserDocument("scenario-03");
    userRepository.save(document);
    ResponseEntity<String> postResponse = httpCaller.delete("/user/"+"1234567897");
    List<UserDocument> users = userRepository.findAll();
    Assert.assertEquals(200, postResponse.getStatusCodeValue());
    Assert.assertEquals(0, users.size());
  }

  @Test
  @DisplayName("Find all users")
  public void scenario04() throws Exception {
    var document = getUserDocument("scenario-04");
    var expectedPostResponse = getExpectedPostResponseJson("scenario-04");
    userRepository.save(document);
    ResponseEntity<String> postResponse = httpCaller.get("/user");
    Assert.assertEquals(200, postResponse.getStatusCodeValue());
    assertEquals(expectedPostResponse, postResponse.getBody(), JSONCompareMode.STRICT);
  }

  private String getUserRequestJson(final String scenario) throws Exception {
    return loadJsonFileUtils.asString(FOLDER_TEST_PATH, scenario, USER_REQUEST_FILENAME);
  }

  private UserDocument getUserDocument(final String scenario) throws Exception {
    return loadJsonFileUtils.asObject(FOLDER_TEST_PATH, scenario, USER_DOCUMENT_FILENAME, UserDocument.class);
  }


  private String getExpectedPostResponseJson(final String scenario) throws Exception {
    return loadJsonFileUtils.asString(FOLDER_TEST_PATH, scenario, USER_EXPECTED_RESPONSE_FILENAME);
  }
}

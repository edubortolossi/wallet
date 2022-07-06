package com.challenge.wallet.gateways.inputs.http.external;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import com.challenge.wallet.gateways.outputs.mongodb.documents.AccountDocument;
import com.challenge.wallet.gateways.outputs.mongodb.documents.UserDocument;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.AccountRepository;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.UserRepository;
import com.challenge.wallet.support.LoadJsonFileUtils;
import com.challenge.wallet.support.TestContainerSupport;
import com.challenge.wallet.support.utils.HttpCaller;
import com.challenge.wallet.usecases.FindAllAccounts;
import com.challenge.wallet.usecases.FindAllUsers;
import com.challenge.wallet.usecases.SaveAccount;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class AccountControllerTest extends TestContainerSupport {

  @Autowired
  private SaveAccount saveAccount;

  @Autowired
  private FindAllAccounts findAllAccounts;

  @Autowired
  private FindAllUsers findAllUsers;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private LoadJsonFileUtils loadJsonFileUtils;

  @Autowired
  private HttpCaller httpCaller;

  private static final String FOLDER_TEST_PATH = "container-tests/account-controller";
  private static final String ACCOUNT_REQUEST_FILENAME = "account-request.json";
  private static final String ACCOUNT_DOCUMENT_FILENAME = "account-document.json";
  private static final String ACCOUNT_EXPECTED_RESPONSE_FILENAME = "account-expected-response.json";
  private static final String USER_DOCUMENT_FILENAME = "user-document.json";

  @Override
  public void init() {
    accountRepository.deleteAll();
  }

  @Test
  @DisplayName("Should save account")
  public void scenario01() throws Exception {
    var request = getAccountRequestJson("scenario-01");
    var expectedPostResponse = getExpectedPostResponseJson("scenario-01");
    var document = getUserDocument("scenario-01");
    userRepository.save(document);
    ResponseEntity<String> postResponse = httpCaller.post("/account", request);
    Assert.assertEquals(200, postResponse.getStatusCodeValue());
    assertEquals(expectedPostResponse, postResponse.getBody(), JSONCompareMode.STRICT);
  }

  @Test
  @DisplayName("Should save account with conflict")
  public void scenario02() throws Exception {
    var request = getAccountRequestJson("scenario-02");
    var accountDocument = getAccountDocument("scenario-02");
    var userDocument = getUserDocument("scenario-02");
    userRepository.save(userDocument);
    accountRepository.save(accountDocument);
    ResponseEntity<String> postResponse = httpCaller.post("/account", request);
    Assert.assertEquals(409, postResponse.getStatusCodeValue());
  }

  @Test
  @DisplayName("Find all accounts")
  public void scenario03() throws Exception {
    var accountDocument = getAccountDocument("scenario-03");
    var expectedPostResponse = getExpectedPostResponseJson("scenario-03");
    accountRepository.save(accountDocument);
    ResponseEntity<String> postResponse = httpCaller.get("/account");
    Assert.assertEquals(200, postResponse.getStatusCodeValue());
    assertEquals(expectedPostResponse, postResponse.getBody(), JSONCompareMode.STRICT);
  }

  private String getAccountRequestJson(final String scenario) throws Exception {
    return loadJsonFileUtils.asString(FOLDER_TEST_PATH, scenario, ACCOUNT_REQUEST_FILENAME);
  }

  private AccountDocument getAccountDocument(final String scenario) throws Exception {
    return loadJsonFileUtils.asObject(FOLDER_TEST_PATH, scenario, ACCOUNT_DOCUMENT_FILENAME, AccountDocument.class);
  }


  private String getExpectedPostResponseJson(final String scenario) throws Exception {
    return loadJsonFileUtils.asString(FOLDER_TEST_PATH, scenario, ACCOUNT_EXPECTED_RESPONSE_FILENAME);
  }

  private UserDocument getUserDocument(final String scenario) throws Exception {
    return loadJsonFileUtils.asObject(FOLDER_TEST_PATH, scenario, USER_DOCUMENT_FILENAME, UserDocument.class);
  }
}

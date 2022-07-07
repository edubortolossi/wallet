package com.challenge.wallet.gateways.inputs.http.external;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import com.challenge.wallet.consumers.BroadcastDepositKafkaConsumer;
import com.challenge.wallet.consumers.BroadcastPaymentKafkaConsumer;
import com.challenge.wallet.consumers.BroadcastTransferKafkaConsumer;
import com.challenge.wallet.gateways.outputs.mongodb.documents.AccountDocument;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.AccountRepository;
import com.challenge.wallet.support.LoadJsonFileUtils;
import com.challenge.wallet.support.TestContainerSupport;
import com.challenge.wallet.support.utils.HttpCaller;
import com.challenge.wallet.usecases.PublishDepositMessage;
import com.challenge.wallet.usecases.PublishPaymentMessage;
import com.challenge.wallet.usecases.PublishTransferMessage;
import com.challenge.wallet.utils.JsonUtils;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class OperationsControllerTest extends TestContainerSupport {

  @Autowired
  private PublishTransferMessage publishTransferMessage;

  @Autowired
  private PublishDepositMessage publishDepositMessage;

  @Autowired
  private PublishPaymentMessage publishPaymentMessage;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private LoadJsonFileUtils loadJsonFileUtils;

  @Autowired
  private BroadcastDepositKafkaConsumer broadcastDepositKafkaConsumer;

  @Autowired
  private BroadcastPaymentKafkaConsumer broadcastPaymentKafkaConsumer;

  @Autowired
  private BroadcastTransferKafkaConsumer broadcastTransferKafkaConsumer;

  @Autowired
  private HttpCaller httpCaller;

  @Autowired
  private JsonUtils jsonUtils;

  private static final String FOLDER_TEST_PATH = "container-tests/operations-controller";
  private static final String ACCOUNT_DOCUMENT_FILENAME = "account-document.json";
  private static final String SECOND_ACCOUNT_DOCUMENT_FILENAME = "second-account-document.json";
  private static final String ACCOUNT_VALUE_REQUEST_FILENAME = "account-value-request.json";
  private static final String ACCOUNT_EXPECTED_RESPONSE_FILENAME = "account-expected-response.json";

  private static final String PAYMENT_REQUEST_FILENAME = "payment-request.json";

  private static final String PAYMENT_EXPECTED_RESPONSE_FILENAME = "payment-expected-response.json";
  private static final String SECOND_PAYMENT_EXPECTED_RESPONSE_FILENAME = "second-payment-expected-response.json";

  @Override
  public void init() {
    broadcastDepositKafkaConsumer.reset();
    broadcastPaymentKafkaConsumer.reset();
    accountRepository.deleteAll();
  }

  @Test
  @DisplayName("Deposit value in account")
  public void scenario01() throws Exception {
    var expectedPostResponse = getExpectedPostResponseJson("scenario-01", ACCOUNT_EXPECTED_RESPONSE_FILENAME);
    var request = getRequestJson("scenario-01", ACCOUNT_VALUE_REQUEST_FILENAME);
    var accountDocument = getAccountDocument("scenario-01", ACCOUNT_DOCUMENT_FILENAME);
    accountRepository.save(accountDocument);
    ResponseEntity<String> response = httpCaller.post("/operations/deposit", request);
    Assert.assertEquals(201, response.getStatusCodeValue());
    waitDepositConsumer();
    List<AccountDocument> accounts = accountRepository.findAll();
    assertEquals(expectedPostResponse, jsonUtils.toJson(accounts.get(0)), JSONCompareMode.STRICT);
  }

  @Test
  @DisplayName("Deposit value with account not found")
  public void scenario02() throws Exception {
    var request = getRequestJson("scenario-02", ACCOUNT_VALUE_REQUEST_FILENAME);
    ResponseEntity<String> response = httpCaller.post("/operations/deposit", request);
    Assert.assertEquals(201, response.getStatusCodeValue());
    waitDepositConsumer();
    List<AccountDocument> accounts = accountRepository.findAll();
    Assert.assertEquals(0 ,accounts.size());
  }

  @Test
  @DisplayName("Withdraw value in account")
  public void scenario03() throws Exception {
    var expectedPostResponse = getExpectedPostResponseJson("scenario-03", ACCOUNT_EXPECTED_RESPONSE_FILENAME);
    var request = getRequestJson("scenario-03", ACCOUNT_VALUE_REQUEST_FILENAME);
    var accountDocument = getAccountDocument("scenario-03", ACCOUNT_DOCUMENT_FILENAME);
    accountRepository.save(accountDocument);
    ResponseEntity<String> response = httpCaller.post("/operations/withdraw", request);
    Assert.assertEquals(200, response.getStatusCodeValue());
    assertEquals(expectedPostResponse, response.getBody(), JSONCompareMode.STRICT);
  }

  @Test
  @DisplayName("Withdraw value with account not found")
  public void scenario04() throws Exception {
    var request = getRequestJson("scenario-04", ACCOUNT_VALUE_REQUEST_FILENAME);
    ResponseEntity<String> response = httpCaller.post("/operations/withdraw", request);
    Assert.assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  @DisplayName("Withdraw value in account without balance")
  public void scenario05() throws Exception {
    var request = getRequestJson("scenario-05", ACCOUNT_VALUE_REQUEST_FILENAME);
    var accountDocument = getAccountDocument("scenario-05", ACCOUNT_DOCUMENT_FILENAME);
    accountRepository.save(accountDocument);
    ResponseEntity<String> response = httpCaller.post("/operations/withdraw", request);
    Assert.assertEquals(400, response.getStatusCodeValue());
  }

  @Test
  @DisplayName("Pay bill in account")
  public void scenario06() throws Exception {
    var expectedPostResponse = getExpectedPostResponseJson("scenario-06", PAYMENT_EXPECTED_RESPONSE_FILENAME);
    var request = getRequestJson("scenario-06", PAYMENT_REQUEST_FILENAME);
    var accountDocument = getAccountDocument("scenario-06", ACCOUNT_DOCUMENT_FILENAME);
    accountRepository.save(accountDocument);
    ResponseEntity<String> response = httpCaller.post("/operations/pay-bills", request);
    Assert.assertEquals(201, response.getStatusCodeValue());
    waitPaymentConsumer();
    List<AccountDocument> accounts = accountRepository.findAll();
    assertEquals(expectedPostResponse, jsonUtils.toJson(accounts.get(0)), JSONCompareMode.STRICT);
  }

  @Test
  @DisplayName("Pay bill with account not found")
  public void scenario07() throws Exception {
    var request = getRequestJson("scenario-07", PAYMENT_REQUEST_FILENAME);
    ResponseEntity<String> response = httpCaller.post("/operations/pay-bills", request);
    Assert.assertEquals(201, response.getStatusCodeValue());
    waitPaymentConsumer();
    List<AccountDocument> accounts = accountRepository.findAll();
    Assert.assertEquals(0 ,accounts.size());
  }

  @Test
  @DisplayName("Pay bill with account without balance")
  public void scenario08() throws Exception {
    var expectedPostResponse = getExpectedPostResponseJson("scenario-08", PAYMENT_EXPECTED_RESPONSE_FILENAME);
    var request = getRequestJson("scenario-08", PAYMENT_REQUEST_FILENAME);
    var accountDocument = getAccountDocument("scenario-08", ACCOUNT_DOCUMENT_FILENAME);
    accountRepository.save(accountDocument);
    ResponseEntity<String> response = httpCaller.post("/operations/pay-bills", request);
    Assert.assertEquals(201, response.getStatusCodeValue());
    waitPaymentConsumer();
    List<AccountDocument> accounts = accountRepository.findAll();
    assertEquals(expectedPostResponse, jsonUtils.toJson(accounts.get(0)), JSONCompareMode.STRICT);
  }

  @Test
  @DisplayName("Transfer between accounts")
  public void scenario09() throws Exception {
    var expectedPostResponse = getExpectedPostResponseJson("scenario-09", PAYMENT_EXPECTED_RESPONSE_FILENAME);
    var expectedPostResponse2 = getExpectedPostResponseJson("scenario-09", SECOND_PAYMENT_EXPECTED_RESPONSE_FILENAME);
    var request = getRequestJson("scenario-09", PAYMENT_REQUEST_FILENAME);
    var accountDocument1 = getAccountDocument("scenario-09", ACCOUNT_DOCUMENT_FILENAME);
    var accountDocument2 = getAccountDocument("scenario-09", SECOND_ACCOUNT_DOCUMENT_FILENAME);
    accountRepository.save(accountDocument1);
    accountRepository.save(accountDocument2);
    ResponseEntity<String> response = httpCaller.post("/operations/transfer", request);
    Assert.assertEquals(201, response.getStatusCodeValue());
    waitPaymentConsumer();
    List<AccountDocument> accounts = accountRepository.findAll();
    assertEquals(expectedPostResponse, jsonUtils.toJson(accounts.get(0)), JSONCompareMode.STRICT);
    assertEquals(expectedPostResponse2, jsonUtils.toJson(accounts.get(1)), JSONCompareMode.STRICT);
  }

  @Test
  @DisplayName("Transfer between accounts with account not found")
  public void scenario10() throws Exception {
    var expectedPostResponse = getExpectedPostResponseJson("scenario-10", PAYMENT_EXPECTED_RESPONSE_FILENAME);
    var request = getRequestJson("scenario-10", PAYMENT_REQUEST_FILENAME);
    var accountDocument1 = getAccountDocument("scenario-10", ACCOUNT_DOCUMENT_FILENAME);
    accountRepository.save(accountDocument1);
    ResponseEntity<String> response = httpCaller.post("/operations/transfer", request);
    Assert.assertEquals(201, response.getStatusCodeValue());
    waitPaymentConsumer();
    List<AccountDocument> accounts = accountRepository.findAll();
    Assert.assertEquals(1 ,accounts.size());
    assertEquals(expectedPostResponse, jsonUtils.toJson(accounts.get(0)), JSONCompareMode.STRICT);
  }

  @Test
  @DisplayName("Transfer between accounts without balance")
  public void scenario11() throws Exception {
    var expectedPostResponse = getExpectedPostResponseJson("scenario-09", PAYMENT_EXPECTED_RESPONSE_FILENAME);
    var expectedPostResponse2 = getExpectedPostResponseJson("scenario-09", SECOND_PAYMENT_EXPECTED_RESPONSE_FILENAME);
    var request = getRequestJson("scenario-09", PAYMENT_REQUEST_FILENAME);
    var accountDocument1 = getAccountDocument("scenario-09", ACCOUNT_DOCUMENT_FILENAME);
    var accountDocument2 = getAccountDocument("scenario-09", SECOND_ACCOUNT_DOCUMENT_FILENAME);
    accountRepository.save(accountDocument1);
    accountRepository.save(accountDocument2);
    ResponseEntity<String> response = httpCaller.post("/operations/transfer", request);
    Assert.assertEquals(201, response.getStatusCodeValue());
    waitPaymentConsumer();
    List<AccountDocument> accounts = accountRepository.findAll();
    assertEquals(expectedPostResponse, jsonUtils.toJson(accounts.get(0)), JSONCompareMode.STRICT);
    assertEquals(expectedPostResponse2, jsonUtils.toJson(accounts.get(1)), JSONCompareMode.STRICT);
  }

  private String getExpectedPostResponseJson(final String scenario, final String fileName) throws Exception {
    return loadJsonFileUtils.asString(FOLDER_TEST_PATH, scenario, fileName);
  }

  private AccountDocument getAccountDocument(final String scenario, String fileName) throws Exception {
    return loadJsonFileUtils.asObject(FOLDER_TEST_PATH, scenario, fileName, AccountDocument.class);
  }

  private String getRequestJson(final String scenario, final String fileName) throws Exception {
    return loadJsonFileUtils.asString(FOLDER_TEST_PATH, scenario, fileName);
  }

  private void waitDepositConsumer() {
    waitUntil(5, EMPTY, (payload) -> nonNull(broadcastDepositKafkaConsumer.getMessage()));
  }

  private void waitPaymentConsumer() {
    waitUntil(5, EMPTY, (payload) -> nonNull(broadcastPaymentKafkaConsumer.getMessage()));
  }

  private void waitTransferConsumer() {
    waitUntil(5, EMPTY, (payload) -> nonNull(broadcastTransferKafkaConsumer.getMessage()));
  }

}

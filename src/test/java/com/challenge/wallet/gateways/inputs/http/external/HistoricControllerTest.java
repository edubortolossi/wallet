package com.challenge.wallet.gateways.inputs.http.external;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import com.challenge.wallet.gateways.outputs.mongodb.documents.AccountDocument;
import com.challenge.wallet.gateways.outputs.mongodb.documents.HistoricDocument;
import com.challenge.wallet.gateways.outputs.mongodb.documents.UserDocument;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.AccountRepository;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.HistoricRepository;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.UserRepository;
import com.challenge.wallet.support.LoadJsonFileUtils;
import com.challenge.wallet.support.TestContainerSupport;
import com.challenge.wallet.support.utils.HttpCaller;
import com.challenge.wallet.usecases.FindAllAccounts;
import com.challenge.wallet.usecases.FindAllHistoric;
import com.challenge.wallet.usecases.FindAllUsers;
import com.challenge.wallet.usecases.FindHistoricByCpfAndAccount;
import com.challenge.wallet.usecases.SaveAccount;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class HistoricControllerTest extends TestContainerSupport {

  @Autowired
  private FindAllHistoric findAllHistoric;

  @Autowired
  private FindHistoricByCpfAndAccount findHistoricByCpfAndAccount;

  @Autowired
  private LoadJsonFileUtils loadJsonFileUtils;

  @Autowired
  private HistoricRepository historicRepository;

  @Autowired
  private HttpCaller httpCaller;

  private static final String FOLDER_TEST_PATH = "container-tests/historic-controller";
  private static final String HISTORIC_DOCUMENT_FILENAME = "historic-document.json";
  private static final String HISTORIC_EXPECTED_RESPONSE_FILENAME = "historic-expected-response.json";

  @Override
  public void init() {
    historicRepository.deleteAll();
  }

  @Test
  @DisplayName("Find all historic")
  public void scenario01() throws Exception {
    var expectedPostResponse = getExpectedPostResponseJson("scenario-01");
    var historicDocument = getHistoricDocument("scenario-01");
    historicRepository.save(historicDocument);
    ResponseEntity<String> postResponse = httpCaller.get("/historic");
    Assert.assertEquals(200, postResponse.getStatusCodeValue());
    assertEquals(expectedPostResponse, postResponse.getBody(), JSONCompareMode.STRICT);
  }

  @Test
  @DisplayName("Find with cpf and account")
  public void scenario02() throws Exception {
    var expectedPostResponse = getExpectedPostResponseJson("scenario-02");
    var historicDocument = getHistoricDocument("scenario-02");
    historicRepository.save(historicDocument);
    ResponseEntity<String> postResponse = httpCaller.get("/historic/36768246979/account/000121214574");
    Assert.assertEquals(200, postResponse.getStatusCodeValue());
    assertEquals(expectedPostResponse, postResponse.getBody(), JSONCompareMode.STRICT);
  }
  private String getExpectedPostResponseJson(final String scenario) throws Exception {
    return loadJsonFileUtils.asString(FOLDER_TEST_PATH, scenario, HISTORIC_EXPECTED_RESPONSE_FILENAME);
  }

  private HistoricDocument getHistoricDocument(final String scenario) throws Exception {
    return loadJsonFileUtils.asObject(FOLDER_TEST_PATH, scenario, HISTORIC_DOCUMENT_FILENAME, HistoricDocument.class);
  }

}

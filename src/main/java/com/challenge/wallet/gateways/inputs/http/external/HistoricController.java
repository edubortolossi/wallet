package com.challenge.wallet.gateways.inputs.http.external;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.usecases.DeleteUser;
import com.challenge.wallet.usecases.FindAllHistoric;
import com.challenge.wallet.usecases.FindHistoricByCpfAndAccount;
import com.challenge.wallet.usecases.SaveUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/historic")
public class HistoricController {

  private final SaveUser saveUser;

  private final DeleteUser deleteUser;

  private final FindAllHistoric findAllHistoric;

  private final FindHistoricByCpfAndAccount findHistoricByCpfAndAccount;

  @ApiOperation(value = "Get historic")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Request success."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @GetMapping(
      produces = APPLICATION_JSON_VALUE)
  public List<Historic> findAllHistoric() {
    return findAllHistoric.execute();
  }

  @ApiOperation(value = "Get historic")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Request success."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @GetMapping(value = "/{cpf}/account/{account}",
      produces = APPLICATION_JSON_VALUE)
  public List<Historic> findHistoric(@PathVariable(value = "cpf") final String cpf,
                                     @PathVariable(value = "account") final String account) {
    return findHistoricByCpfAndAccount.execute(cpf, account);
  }
}

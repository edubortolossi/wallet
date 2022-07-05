package com.challenge.wallet.gateways.inputs.http.external;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.gateways.inputs.http.external.resources.request.AccountRequest;
import com.challenge.wallet.gateways.inputs.http.external.resources.responses.AccountResponse;
import com.challenge.wallet.usecases.FindAllAccounts;
import com.challenge.wallet.usecases.SaveAccount;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/account")
public class AccountController {

  private final SaveAccount saveAccount;
  private final FindAllAccounts findAllAccounts;

  @ApiOperation(value = "Create new account")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Request success."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @PostMapping(
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public AccountResponse create(@RequestBody AccountRequest accountRequest) {
    return new AccountResponse(saveAccount.execute(accountRequest.toDomain()));
  }

  @ApiOperation(value = "Get accounts")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Request success."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @GetMapping(
      produces = APPLICATION_JSON_VALUE)
  public List<Account> findAllUsers() {
    return findAllAccounts.execute();
  }
}
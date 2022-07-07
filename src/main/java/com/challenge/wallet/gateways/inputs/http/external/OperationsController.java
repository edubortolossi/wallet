package com.challenge.wallet.gateways.inputs.http.external;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.challenge.wallet.gateways.inputs.http.external.resources.request.AccountValueRequest;
import com.challenge.wallet.gateways.inputs.http.external.resources.request.PaymentRequest;
import com.challenge.wallet.gateways.inputs.http.external.resources.request.TransferRequest;
import com.challenge.wallet.gateways.inputs.http.external.resources.responses.AccountResponse;
import com.challenge.wallet.usecases.PublishDepositMessage;
import com.challenge.wallet.usecases.PublishPaymentMessage;
import com.challenge.wallet.usecases.PublishTransferMessage;
import com.challenge.wallet.usecases.WithdrawValue;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/operations")
public class OperationsController {
  private final PublishTransferMessage publishTransferMessage;
  private final PublishDepositMessage publishDepositMessage;
  private final PublishPaymentMessage publishPaymentMessage;

  public final WithdrawValue withdrawValue;

  @ApiOperation(value = "Transfer between accounts")
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = "Request has been processed."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @PostMapping(
      value = "/transfer",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void transfer(@RequestBody TransferRequest transferRequest) {
    publishTransferMessage.execute(transferRequest.toDomain());
  }

  @ApiOperation(value = "Withdraw value to account")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Request success."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @PostMapping(
      value = "/withdraw",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public AccountResponse withdraw(@RequestBody AccountValueRequest accountValueRequest) {
    return new AccountResponse(withdrawValue.execute(accountValueRequest.toDomain()));
  }

  @ApiOperation(value = "Deposit value in account")
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = "Request has been processed."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @PostMapping(
      value = "/deposit",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void deposit(@RequestBody AccountValueRequest accountValueRequest) {
    publishDepositMessage.execute(accountValueRequest.toDomain());
  }

  @ApiOperation(value = "Pay bills")
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = "Request has been processed."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @PostMapping(
      value = "/pay-bills",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void billPayment(@RequestBody PaymentRequest paymentRequest) {
    publishPaymentMessage.execute(paymentRequest.toDomain());
  }


}

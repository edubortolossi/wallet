package com.challenge.wallet.gateways.inputs.http.external;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.challenge.wallet.domains.History;
import com.challenge.wallet.usecases.DeleteUser;
import com.challenge.wallet.usecases.FindAllHistoric;
import com.challenge.wallet.usecases.SaveUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

  @ApiOperation(value = "Get historic")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Request success."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @GetMapping(
      produces = APPLICATION_JSON_VALUE)
  public List<History> findAllHistoric() {
    return findAllHistoric.execute();
  }
}

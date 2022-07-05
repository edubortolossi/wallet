package com.challenge.wallet.gateways.inputs.http.external;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.challenge.wallet.domains.User;
import com.challenge.wallet.gateways.inputs.http.external.resources.request.UserRequest;
import com.challenge.wallet.gateways.inputs.http.external.resources.responses.UserResponse;
import com.challenge.wallet.usecases.DeleteUser;
import com.challenge.wallet.usecases.FindAllUsers;
import com.challenge.wallet.usecases.SaveUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

  private final SaveUser saveUser;

  private final DeleteUser deleteUser;

  private final FindAllUsers findAllUsers;

  @ApiOperation(value = "Create user")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Request success."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  public UserResponse create( @RequestBody UserRequest userRequest) {
    return new UserResponse(saveUser.execute(userRequest.toDomain()));
  }

  @ApiOperation(value = "Delete user")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Request success."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @DeleteMapping(
      value = "/{cpf}",
      produces = APPLICATION_JSON_VALUE)
  public void delete( @PathVariable(value = "cpf") final String cpf) {
    deleteUser.execute(cpf);
  }

  @ApiOperation(value = "Get users")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Request success."),
          @ApiResponse(code = 400, message = "Bad request. Check request and try again."),
          @ApiResponse(code = 409, message = "Conflict. Resource already exists.")
      })
  @GetMapping(
      produces = APPLICATION_JSON_VALUE)
  public List<User> findAllUsers() {
    return findAllUsers.execute();
  }
}

package mohammaddarwaish.com.github.userservice.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import mohammaddarwaish.com.github.userservice.data.models.User;
import mohammaddarwaish.com.github.userservice.services.UserService;
import mohammaddarwaish.com.github.userservice.views.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> postUser(@RequestBody UserRequest userRequest) {
        User user = userService.createUser(userRequest);
        URI uri = ServletUriComponentsBuilder.fromPath("/v1/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @ApiImplicitParams({@ApiImplicitParam(dataType = "UserRequest", name = "updatedFields", paramType = "body", required = true)})
    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchUser(@PathVariable final Long id, @RequestBody final Map<String, Object> updatedFields) {
        userService.updateUser(id, updatedFields);
    }

}
package com.trolit.github.grocerystore.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1")
@RestController
public class ConnectionController {

    @GetMapping(path = "online")
    @ApiOperation(value = "Returns OK if API is online")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "API is online")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> isApiOnline(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

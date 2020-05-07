package userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.dto.LoginUser;
import userservice.entity.User;
import userservice.service.UserService;
import userservice.utils.EncDnc;
import userservice.utils.JwtTokenUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class Auth {

    @Autowired
    private UserService userService;

    @Value("${secretkey}")
    private String secretKey;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<String> getAuthToken(@RequestBody LoginUser loginUser){
        String encryptedPassword = EncDnc.EncryptString(loginUser.getPassword(),secretKey);
        String encryptedMail = EncDnc.EncryptString(loginUser.getEmail(),secretKey);
        Optional<User> user = userService.userByEmailAndPassword(encryptedMail,encryptedPassword);
        if(!user.isPresent())
            return new ResponseEntity("Not a valid User", HttpStatus.BAD_REQUEST);
        User fetchedUser = user.get();
        String jwtToken = jwtTokenUtil.generateToken(fetchedUser);
            return new ResponseEntity(jwtToken, HttpStatus.ACCEPTED);
    }


    @GetMapping("/token-expired")
    public ResponseEntity isTokenExpired(@RequestParam("token") String token){
        return  new ResponseEntity(jwtTokenUtil.isTokenExpired(token),HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-claim")
    public ResponseEntity getDECPassword(@RequestParam("token") String token){
        return  new ResponseEntity(jwtTokenUtil.getAllClaimsFromToken(token),HttpStatus.ACCEPTED);
    }
}

package ru.fusing.costprice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fusing.costprice.auth.entities.RefreshToken;
import ru.fusing.costprice.auth.entities.User;
import ru.fusing.costprice.auth.services.AuthService;
import ru.fusing.costprice.auth.services.JwtService;
import ru.fusing.costprice.auth.services.RefreshTokenService;
import ru.fusing.costprice.auth.utils.*;

import java.util.Optional;

@Tag(name = "Аутентификация")
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Регистрация нового пользователя", description = "Регистрация нового пользователя с предоставленными данными")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @Operation(summary = "Вход пользователя", description = "Вход пользователя с предоставленными учетными данными")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно вошел в систему",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверные учетные данные")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(summary = "Обновление токена доступа", description = "Обновление токена доступа с использованием предоставленного токена обновления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен доступа успешно обновлен",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный токен обновления")
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }

    @Operation(summary = "Получение информации о пользователе", description = "Возвращает имя, имя пользователя, email и роль пользователя для авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе успешно получена",
                    content = @Content(schema = @Schema(implementation = AccountDetails.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/user")
    public ResponseEntity<AccountDetails> getUserInfo(@RequestParam("token") String token) {
        String email = jwtService.extractUsername(token);

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<User> userOptional = Optional.ofNullable(authService.getUserByEmail(email));
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var user = userOptional.get();

        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setName(user.getName());
        accountDetails.setUsername(user.getRealUsername());
        accountDetails.setEmail(user.getEmail());
        accountDetails.setRole(user.getRole().name());
        return ResponseEntity.ok(accountDetails);
    }
}

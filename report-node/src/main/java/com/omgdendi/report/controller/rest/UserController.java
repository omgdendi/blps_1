package com.omgdendi.report.controller.rest;

import com.omgdendi.data.dto.res.NotificationResDto;
import com.omgdendi.report.service.EssayService;
import com.omgdendi.report.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final EssayService essayService;

    @Autowired
    public UserController(UserService userService, EssayService essayService) {
        this.userService = userService;
        this.essayService = essayService;
    }

    @Operation(summary = "Получить все уведомления")
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResDto>> getNotifications() {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        List<NotificationResDto> notifications = userService.getNotification(principal);
        return ResponseEntity.ok(notifications);
    }

}
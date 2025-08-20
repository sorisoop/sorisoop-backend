package com.futurenet.sorisoopbackend.member.presentation;

import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import com.futurenet.sorisoopbackend.member.application.MemberAuthService;
import com.futurenet.sorisoopbackend.member.application.MemberService;
import com.futurenet.sorisoopbackend.member.dto.request.SignupRequest;
import com.futurenet.sorisoopbackend.member.dto.response.FindMemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberAuthService memberAuthService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        memberAuthService.signup(request);
        return ResponseEntity.ok(new ApiResponse<>("ME100", "회원가입 성공", null));
    }

    @GetMapping("check-email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        FindMemberResponse result = memberService.getMemberByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>("ME101", "이메일 조회 성공", result.getEmail()));
    }

}

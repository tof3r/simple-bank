package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Notice;
import com.dawidg90.simple_bank.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class NoticesController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> notices() {
        List<Notice> allActiveNotices = noticeRepository.findAllActiveNotices();
        if (allActiveNotices != null) {
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                    .body(allActiveNotices);
        } else {
            return null;
        }
    }
}

package com.eazybytes.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.eazybytes.model.Notice;
import com.eazybytes.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticesController {

	private final NoticeRepository noticeRepository;

	@GetMapping("/notices")
	public ResponseEntity<List<Notice>> getNotices() {
		List<Notice> notices = noticeRepository.findAllActiveNotices();
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
				.body(notices);
	}

}

package com.eazybytes.repository;

import java.util.List;

import com.eazybytes.model.Notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	@Query("from Notice n WHERE CURDATE() BETWEEN n.noticeBegDt AND n.noticeEndDt")
	List<Notice> findAllActiveNotices();
}
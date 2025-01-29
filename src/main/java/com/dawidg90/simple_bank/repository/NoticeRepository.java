package com.dawidg90.simple_bank.repository;

import java.util.List;

import com.dawidg90.simple_bank.model.Notice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {
	
	@Query(value = "from Notice n where CURDATE() BETWEEN noticeBeginDate AND noticeEndDate")
	List<Notice> findAllActiveNotices();

}

package com.osite.omok.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osite.omok.entity.OmokOrder;
import com.osite.omok.entity.OmokSetting;

public interface OmokOrderRepository extends JpaRepository<OmokOrder, Integer>{

	List<OmokOrder> findBygameNum(OmokSetting gameNum);
	
	void deleteBygameNum(OmokSetting gameNum);
}

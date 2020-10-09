package com.ck.trade;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ck.trade.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String>{
	
	@Query("select a from Trade a where a.maturityDate <= :maturityDate")
    List<Trade> findAllWithMaturityDateBefore(
      @Param("maturityDate") Date maturityDate);

}

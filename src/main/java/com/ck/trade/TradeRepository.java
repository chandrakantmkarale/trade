package com.ck.trade;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ck.trade.model.Trade;

@Repository
public interface TradeRepository extends CrudRepository<Trade, String>{
	
	@Query("select a from Trade a where a.maturityDate <= :maturityDate")
    List<Trade> findAllWithMaturityDateGreaterThanEqual(@Param("maturityDate") Date maturityDate);

}

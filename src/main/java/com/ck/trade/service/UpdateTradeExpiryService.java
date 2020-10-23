package com.ck.trade.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ck.trade.TradeRepository;
import com.ck.trade.model.Trade;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UpdateTradeExpiryService {
	
	@Autowired
	TradeRepository tradeRepo;

	/**
	 * 3. Update trades which have expired.
	 */
	@Scheduled(fixedDelay = 360000)
	public void updateTrade() {
		Date tillDate = new Date();
		log.info("Setting expired=true for all trades expired till "+tillDate);
		List<Trade> tradeList =tradeRepo.findAllWithMaturityDateGreaterThanEqual(tillDate);
		for (Trade trade : tradeList) {
			boolean isMaturityDateValid = TradeValidator.isMaturityDateValid(trade, tillDate);
			if (!isMaturityDateValid) {
				// Expire the trade
				trade.setExpired(true);
				tradeRepo.save(trade);

			}
		}
	}
}

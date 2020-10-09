package com.ck.trade.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ck.trade.exception.TradeNotFoundException;
import com.ck.trade.exception.TradeValidationException;
import com.ck.trade.model.Trade;
import com.ck.trade.service.TradeManagementService;
import com.ck.trade.transport.rest.dto.TradeDTO;

@RestController
@RequestMapping("/trade-management")
public class TradeManagementController {
	@Autowired
	TradeManagementService tService;

	@Autowired
	TradeMapper tradeMapper;

	@PostMapping("/trades")
	public ResponseEntity<String> saveTrade(@Valid @RequestBody TradeDTO receivedTrade)
			throws TradeValidationException {
		Trade trade = tradeMapper.convertToEntity(receivedTrade);
		tService.saveTrade(trade);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/trades")
	public List<TradeDTO> findAllTrades() {
		return tradeMapper.convertToDTO(tService.findAll());
	}

	@GetMapping("/trades/{id}")
	public TradeDTO getTrade(@PathVariable String id) throws TradeNotFoundException {
		Trade trade = tService.getTrade(id);
		if (trade != null) {

			return tradeMapper.convertToDTO(trade);
		} else {
			throw new TradeNotFoundException(id);
		}

	}
}
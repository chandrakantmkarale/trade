package com.ck.trade.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ck.trade.model.Trade;
import com.ck.trade.transport.rest.dto.TradeDTO;

@Configuration
public class TradeMapper {

	@Autowired
	private ModelMapper modelMapper;

	public TradeDTO convertToDTO(Trade trade) {
		TradeDTO tradeDto = modelMapper.map(trade, TradeDTO.class);

		return tradeDto;

	}

	public Trade convertToEntity(TradeDTO tradeDTO) {
		Trade trade = modelMapper.map(tradeDTO, Trade.class);
		return trade;
	}

	public List<TradeDTO> convertToDTO(List<Trade> trades) {
		return trades.stream().map(trade -> modelMapper.map(trade, TradeDTO.class)).collect(Collectors.toList());
	}

	public List<Trade> convertToEntity(List<TradeDTO> trades) {
		return trades.stream().map(trade -> modelMapper.map(trade, Trade.class)).collect(Collectors.toList());
	}

}

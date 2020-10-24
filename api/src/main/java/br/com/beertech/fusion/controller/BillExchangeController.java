package br.com.beertech.fusion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.controller.dto.BillExchangeDTO;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.service.BillExchangeService;

@RestController
@RequestMapping("/beercoin")
public class BillExchangeController {

	@Autowired
	private BillExchangeService billExchangeService;
	
	@PostMapping("/bill")
	@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
	public void billExchange(@RequestBody BillExchangeDTO billExchangeDTO,
			@RequestHeader(value = "Authorization", required = false) String token) throws FusionException {
		
		billExchangeService.payBillExchange(billExchangeDTO, token);

	}

}

package br.com.beertech.fusion.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.beertech.fusion.service.BillExchangeService;
import br.com.beertech.fusion.service.impl.BillExchangeServiceImpl;

public class BillExchangeTest {

	private String barcode = "23790215009000002954780021021003183820000004018";

	@Test
	public void testGetBarcodeValue() {

		BillExchangeService billExchangeService = new BillExchangeServiceImpl();
				
		String valueBarcode = billExchangeService.getValueBarcode(barcode);
		assertEquals("0000004018", valueBarcode);

	}
	
	@Test
	public void testFormatValue() {

		BillExchangeService billExchangeService = new BillExchangeServiceImpl();
		String valueBarcode = billExchangeService.getValueBarcode(barcode);
		Double formatValue = billExchangeService.formatValue(valueBarcode);
		assertEquals(40.18, formatValue);

	}
}	

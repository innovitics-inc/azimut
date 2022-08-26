package innovitics.azimut.pdfgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.storage.blob.models.BlobItem;

import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.rest.BaseRestConsumer;
@Service
public class Executor {
	public final static Logger logger = LogManager.getLogger(Executor.class.getName());

	  @Autowired
	    private PdfGenerateService pdfGenerateService;
	public void execute () throws IOException {
		  Map<String, Object> data = new HashMap<>();
	       
		  	Customer customer = new Customer();
	        customer.setCompanyName("Simple Solution");
	        customer.setContactName("John Doe");
	        customer.setAddress("123, Simple Street");
	        customer.setEmail("john@simplesolution.dev");
	        customer.setPhone("123 456 789");
	        data.put("customer", customer);

	        List<QuoteItem> quoteItems = new ArrayList<>();
	        QuoteItem quoteItem1 = new QuoteItem();
	        quoteItem1.setDescription("Test Quote Item 1");
	        quoteItem1.setQuantity(1);
	        quoteItem1.setUnitPrice(100.0);
	        quoteItem1.setTotal(100.0);
	        quoteItems.add(quoteItem1);

	        QuoteItem quoteItem2 = new QuoteItem();
	        quoteItem2.setDescription("Test Quote Item 2");
	        quoteItem2.setQuantity(4);
	        quoteItem2.setUnitPrice(500.0);
	        quoteItem2.setTotal(2000.0);
	        quoteItems.add(quoteItem2);

	        QuoteItem quoteItem3 = new QuoteItem();
	        quoteItem3.setDescription("Test Quote Item 4");
	        quoteItem3.setQuantity(2);
	        quoteItem3.setUnitPrice(200.0);
	        quoteItem3.setTotal(400.0);
	        quoteItems.add(quoteItem3);

	        data.put("quoteItems", quoteItems);
	        pdfGenerateService.generatePdfFile("quotation2-copy", data, "quotation");
		
	}
	
	public void download() throws BusinessException, IOException 
	{
		pdfGenerateService.downloadAndMergeLoop();
	}
	public void downloadLoop() throws BusinessException, IOException 
	{
		pdfGenerateService.downloadAndMergeLoop();
	}
	public List<BlobItem> listBlobs() throws BusinessException, IOException 
	{	List<BlobItem> blobItems=pdfGenerateService.listBlobItems();
		
		for(BlobItem blobItem:blobItems)
		{
			this.logger.info("Blob Item name::"+blobItem.getName());
			this.logger.info("Blob Item properties::"+blobItem.getProperties());
		}
	
		return blobItems ;
	}
}

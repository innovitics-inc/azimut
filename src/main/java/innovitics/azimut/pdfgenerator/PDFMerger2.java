package innovitics.azimut.pdfgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

public class PDFMerger2 {

	
	public OutputStream merge(List<InputStream> inputStreams,OutputStream outputStream)
	{
		 
		Document document = new Document(PageSize.LETTER);

	    try
	    {
	      PdfCopy copy = new PdfCopy(document, outputStream);

	      document.open();

	      for (InputStream file : inputStreams)
	      {
	        copy.addDocument(new PdfReader(file)); // writes directly to the output stream
	      }
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	return  outputStream;
	}
	
	
}
	


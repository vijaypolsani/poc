package com.kd.poc.mule;

import java.io.File;
import java.io.FileOutputStream;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class StorePDFfromZillow extends AbstractMessageTransformer {
	private static final File file = new File("ZillowDocument.pdf");
	private static Document document = null;

	public static boolean convertTextfileToPDF(File file, String data) {
		try {
			document = new Document();
			// step 2
			PdfWriter.getInstance(document, new FileOutputStream(file));
			// step 3
			document.open();
			// step 4
			document.add(new Paragraph(data));
			// step 5
			document.close();
		} catch (Exception e) {
			System.out.println("FileUtility.covertEmailToPDF(): exception = " + e.getMessage());
		}
		return true;
	}

	public static void performPDFCleanup() {
		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		Object data = message.getPayload();
		System.out.println("Starting to write PDF with Data: " + data);
		performPDFCleanup();
		convertTextfileToPDF(file, data.toString());
		return message;
	}
}

package client;

import org.example.ravelry.RavelryPortType;
import org.example.ravelry.RavelryService;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

public class WSClient {
	public static void main(String[] args) {
		RavelryService service = new RavelryService();
		RavelryPortType port = service.getRavelryPort();

		String[] designer = { "karim.ainine", "password10" };
		String[] store = { "clegs", "password2" };

		Map outProps = new HashMap();
		Client client = org.apache.cxf.frontend.ClientProxy.getClient(port);
		Endpoint cxfEndpoint = client.getEndpoint();

		BindingProvider portBP = (BindingProvider) port;
		String urlUsed = (String) portBP.getRequestContext().get(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		System.out.println("Using URL: " + urlUsed);
		if (!"https://".equals(urlUsed.substring(0, 8))) {
			throw new IllegalStateException("Endpoint URL must use HTTPS!");
		}

		portBP.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
				designer[0]);
		portBP.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				designer[1]);

		uploadPattern(port,"/Volumes/AININE WD 1/RMIT/WS/PatternPDFs/Flood_Celes.pdf","Flood Celes", 200.00);
				
		portBP.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
				store[0]);
		portBP.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				store[1]);

		sellPattern(port, "Figueroa Cobble", 200.50);
		
	}

	public static void uploadPattern(RavelryPortType port, String file_url,
			String name, double price) {
		try {
			File file = new File(file_url);
			byte[] fileContent = getBytesFromFile(file);
			String resp = port.uploadPattern(fileContent, name, price);
			System.out.println("Upload Response: " + resp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void sellPattern(RavelryPortType port, String name,
			double yarnsValue) {
		try {
			Holder<Double> balance = new Holder<Double>();
			Holder<byte[]> file = new Holder<byte[]>();
			port.sellPattern(name, yarnsValue, balance, file);
			System.out.println("Remaining Balance: " + balance.value);
			WriteByteArrayToFile("DownloadedPDFs/" + name.replaceAll(" ", "_") + ".pdf", file.value);
		} catch (Exception e) {
			System.out.println("Client: " + e.toString());
			e.printStackTrace();
		}
	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public static void WriteByteArrayToFile(String strFilePath, byte[] content) {
		try {
			File file = new File(strFilePath);
			file.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content);
			fos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

package service;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.example.ravelry.*;

import java.io.File;
import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.xml.soap.MimeHeader;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.Holder;

import net.webservicex.CurrencyConvertor;
import net.webservicex.CurrencyConvertorSoap;
import net.webservicex.Currency;

@WebService(targetNamespace = "http://www.example.org/Ravelry", portName = "RavelryPort", serviceName = "RavelryService", endpointInterface = "org.example.ravelry.RavelryPortType")
public class RavelryImpl implements RavelryPortType {
	WebServiceContext wsContext = new org.apache.cxf.jaxws.context.WebServiceContextImpl();
	Logger logger = Logger.getLogger(RavelryImpl.class.getName());

	public String uploadPattern(byte[] file, String name, double price)
			throws InvalidPriceFault, AccessDeniedFault, InvalidFileFault,
			NameAlreadyExistsFault {
		String message = "";
		if (wsContext.isUserInRole("designer")) {
			if (price < 0) {
				throw new InvalidPriceFault(
						"Upload Failed! Invalid price value!");
			} else {
				// check if name already exists
				try {
					ResultSet rset = DatabaseConnection
							.executeQuery("SELECT COUNT(NAME) FROM RAVELRY_PATTERN WHERE NAME = '"
									+ name + "'");
					rset.next();
					int count = rset.getInt(1);
					if (count > 0) {
						throw new NameAlreadyExistsFault(
								"Upload Failed! Pattern with similar name already exists.");
					} else {
						ResultSet user = DatabaseConnection
								.executeQuery("SELECT ID FROM RAVELRY_USER WHERE USERNAME = '"
										+ wsContext.getUserPrincipal()
												.getName() + "'");
						user.next();
						int userID = user.getInt("ID");
						PreparedStatement stmt = DatabaseConnection
								.getPreparedStatememt("INSERT INTO RAVELRY_PATTERN (NAME, PRICE, FILE_CONTENT, DESIGNER) VALUES (?,?,?,?)");
						stmt.setString(1, name);
						stmt.setDouble(2, price);
						stmt.setObject(3, file);
						stmt.setInt(4, userID);
						int result = stmt.executeUpdate();
						if (result > 0) {
							message = "Pattern uploaded successfully";
						} else {
							message = "Pattern failed to upload.";
						}
					}
					DatabaseConnection.closeConnections();
				} catch (Exception e) {
					message = e.toString();// "Failed to upload pattern due to database exceptions. Please try again later.";
				}
				return message;
			}
		} else {
			throw new AccessDeniedFault(
					"Access Denied! Only designers can upload patterns.");
		}
	}

	private void getPattern(String name, Holder<Double> balance,
			Holder<byte[]> file) throws InsufficientFundsFault,
			PatternNotFoundFault {
		try {
			// look up pattern
			ResultSet pattern = DatabaseConnection
					.executeQuery("SELECT * FROM RAVELRY_PATTERN WHERE NAME = '"
							+ name + "'");
			if (pattern.next()) {
				// convert price to AUD
				CurrencyConvertor convertor = new CurrencyConvertor();
				CurrencyConvertorSoap port = convertor
						.getCurrencyConvertorSoap();
				double rate = port.conversionRate(Currency.USD, Currency.AUD);
				double price = pattern.getDouble("PRICE");
				double convertedPrice = price*rate;

				// get store's balance
				ResultSet user = DatabaseConnection
						.executeQuery("SELECT ID FROM RAVELRY_USER WHERE USERNAME = '"
								+ wsContext.getUserPrincipal().getName() + "'");
				user.next();
				int userID = user.getInt("ID");

				ResultSet store = DatabaseConnection
						.executeQuery("SELECT * FROM RAVELRY_STORE_DETAILS WHERE STORE_ID = '"
								+ userID + "'");
				store.next();
				double oldBalance = store.getDouble("BALANCE");
				// check stores balance
				if (convertedPrice < oldBalance) {
					// deduct price from balance
					PreparedStatement stmt = DatabaseConnection
							.getPreparedStatememt("UPDATE RAVELRY_STORE_DETAILS SET BALANCE = ? WHERE STORE_ID = ?");
					stmt.setDouble(1, (oldBalance - convertedPrice));
					stmt.setInt(2, userID);
					int result = stmt.executeUpdate();
					if (result > 0) {
						// send pdf blob and new balance
						balance.value = (oldBalance - convertedPrice);
						file.value = pattern.getBytes("FILE_CONTENT");
					} else {
						System.out
								.println("Could not deduct price from balance.");
					}
				} else {
					// throw insufficient funds exception
					throw new InsufficientFundsFault(
							"You do not have sufficient funds to retrieve this pattern.");
				}
			} else {
				// throw pattern not found exception
				throw new PatternNotFoundFault("Pattern not found!");
			}
		} catch (Exception e) {
			System.out.println("Get Pattern: " + e.toString());
		}
	}

	public void sellPattern(String name, double yarnsValue,
			Holder<Double> balance, Holder<byte[]> file)
			throws AccessDeniedFault, PatternNotFoundFault,
			InsufficientFundsFault {
		//System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		//Logger.getAnonymousLogger().getParent().setLevel(Level.WARNING);
		if (wsContext.isUserInRole("store")) {
			try {
				//logger.log(Level.SEVERE, "MY NEW LOG: Into Sell Pattern");
				getPattern(name, balance, file);
				// look up pattern
				ResultSet pattern = DatabaseConnection
						.executeQuery("SELECT * FROM RAVELRY_PATTERN WHERE NAME = '"
								+ name + "'");
				if (pattern.next()) {
					ResultSet user = DatabaseConnection
							.executeQuery("SELECT ID FROM RAVELRY_USER WHERE USERNAME = '"
									+ wsContext.getUserPrincipal().getName()
									+ "'");
					user.next();
					int userID = user.getInt("ID");
					// insert into sale table
					PreparedStatement stmt = DatabaseConnection
							.getPreparedStatememt("INSERT INTO RAVELRY_SALE (PATTERN_ID, STORE_ID, TOTAL_YARN) VALUES (?,?,?)");
					stmt.setInt(1, pattern.getInt("ID"));
					stmt.setInt(2, userID);
					stmt.setDouble(3, yarnsValue);
					int result = stmt.executeUpdate();
					if (result > 0) {
						if (result % 5 == 0) {
							// send top designers list to stores
							// send top stores list to designers
						}
						// check designers total sales
						int totalDesignerSales = 0;
						if (totalDesignerSales % 5 == 0) {
							// get last 5 sales
							// send 90% to designer along with details of the
							// last 5 sales
						}
					} else {
						System.out.println("Failed to log sale.");
					}
				} else {
					// throw pattern not found exception
					throw new PatternNotFoundFault(
							"Pattern not found! Please check the pattern name.");
				}
			} catch (PatternNotFoundFault pnf) {
				throw pnf;
			} catch (InsufficientFundsFault iff) {
				throw iff;
			} catch (Exception e) {
				System.out.println("Sell Pattern: " + e.toString());// "Failed to log the sale due to database connectivity issues."
			}
		} else {
			throw new AccessDeniedFault(
					"Access Denied! Only stores can sell patterns.");
		}
	}
}

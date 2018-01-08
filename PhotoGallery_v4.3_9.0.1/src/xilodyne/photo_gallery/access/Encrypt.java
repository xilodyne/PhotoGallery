package xilodyne.photo_gallery.access;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.security.*;

import javax.crypto.*;

import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;

import java.util.Base64;


/**
 * @author aholiday
 *
 */

public class Encrypt {
	public static Key key;
	public static Cipher cipher;
	
	public Encrypt(boolean bLoad) {
		this.initEncrypt();
	}

	public Encrypt() {}
	
	private boolean initEncrypt () {
		//read in the key

		
		boolean keyFound = false;
		
//		String sTemp = System.getProperty("catalina.home");
//		Logging.debug(this.getClass().getName(),"Cataline home: " + sTemp + ", " + Globals.CATALINE_HOME);
//		Globals.CATALINE_HOME = sTemp;
//		Logging.debug(this.getClass().getName(),"Cataline home: " + sTemp + ", " + Globals.CATALINE_HOME);
		
		String keyFile = Globals.CATALINE_HOME + Globals.GALLERY_DEFAULT_CONF_DIR + Globals.KEY_NAME;
		Logging.debug(this.getClass().getName(), "Key File: " + keyFile);
		try {
		
			ObjectInputStream in = new ObjectInputStream( new FileInputStream( keyFile ));
			Logging.debug(this.getClass().getName(), "Encrypt: reading --> " + keyFile);

			key = (Key) in.readObject();
			in.close();
			keyFound = true;
		}  catch (IOException e ) {
			Logging.error( e.getMessage() );
		} catch (ClassNotFoundException e ){
			e.printStackTrace();
			Logging.error( e.getMessage() );
			
		}
		
		try {
		cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		} catch (NoSuchAlgorithmException e ) {
			Logging.error( e.getLocalizedMessage() );
			keyFound = false;
		} catch (NoSuchPaddingException e ) {
			Logging.error( e.getMessage() );
			keyFound = false;
		}

		
		
		return keyFound;
		
		
	}
	
	//to generate the key, comment out the public Encrypt init, run in debug mode
	public void makeKey(PrintWriter writer) {
	/*	try  {
		Key key;
		KeyGenerator generator = KeyGenerator.getInstance( "DES");
		generator.init( new SecureRandom() );
		
		key=generator.generateKey();
	    writer.println("<tr><th align=\"right\">(Encrypt) Key</th>");
	    writer.println( "<td>" + key.getEncoded().length + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("</td>");
	    writer.println("</tr>");
	    
	    
	    
	    ObjectOutputStream out =new ObjectOutputStream( new FileOutputStream("f:\\austin.ser"));
	    out.writeObject( key );
	    out.close();
	    
		
		} catch (NoSuchAlgorithmException e){
		    writer.println("<tr><th align=\"right\">(Encrypt) Error</th>");
		    writer.println( "<td>" + e.toString() + "</td>");
		    writer.println("</tr>");

			writer.println( e.toString() );
		} catch (IOException e) {
		}
*/
		
		
	}
	
	public String encrypt(String sCode ) {
		String base64 = sCode;
		
		if (key == null ) {
			this.initEncrypt();
		} 
		
		if (key == null ) {
			Logging.debug(this.getClass().getName(), " ------> Encrypt:Key is NULL" );
		} else {
			Logging.debug(this.getClass().getName(), " ------> Encrypting Key is: " + key.getAlgorithm() );
		}
		
		Logging.debug(this.getClass().getName(), " ------> Encrypting: " + base64 );
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
	
			byte[] stringBytes = sCode.getBytes("UTF8");
			Logging.debug(this.getClass().getName(), " ------> Encrypting to bytes: " + stringBytes );

		//	byte[] raw = cipher.doFinal( stringBytes );
		//	base64 = Base64.getEncoder().encode(raw).toString();
			base64 = Base64.getEncoder().encodeToString(cipher.doFinal(stringBytes));
		}
			catch (InvalidKeyException e) {
				Logging.error("Encrypt.encrypt: " + e.getMessage());
			}
			catch (IOException e) {
				Logging.error("Encrypt.encrypt: " +  e.getMessage());
			}
			catch (BadPaddingException e) {
				Logging.error("Encrypt.encrypt: " +  e.getMessage());
			}
			catch (IllegalBlockSizeException e) {	
				Logging.error("Encrypt.encrypt: " +  e.getMessage());
			}
			catch (NullPointerException e) {
				Logging.error("Encrypt.encrypt: " +  e.getMessage());
			}
			
		Logging.debug(this.getClass().getName(), " ------> Encrypted: " + base64 );

		return base64;
	}
	
	public String decrypt(String sEncrypted ) {
		String sResult = sEncrypted;

		if (key == null ) {
			Logging.debug(this.getClass().getName(), " ------> Encrypt:Key is NULL" );
			this.initEncrypt();
		}

		if (key == null ) {
			Logging.debug(this.getClass().getName(), " ------> Encrypt:Key is NULL" );
		}
		
		Logging.debug(this.getClass().getName(), " ------> Decrypting: " + sResult );
		
		try {
			cipher.init( Cipher.DECRYPT_MODE, key );
			Logging.debug(this.getClass().getName(), sEncrypted);
		//	byte[] raw = Base64.getDecoder().decode(sEncrypted);
		//			BASE64.Decoder decoder = new BASE64Decoder();
			//byte[] raw = decoder.decodeBuffer( sEncrypted );
		//	byte[] stringBytes = cipher.doFinal(raw );
			
			byte[] stringBytes = cipher.doFinal(Base64.getDecoder().decode(sEncrypted));
			sResult = new String(stringBytes, "UTF8");
		}
		catch (InvalidKeyException e) {
			Logging.error("Encrypt.decrypt: " + e.getMessage());
		}
		catch (IOException e) {
			Logging.error("Encrypt.decrypt: " +  e.getMessage());
		}
		catch (BadPaddingException e) {
			Logging.error("Encrypt.decrypt: " +  e.getMessage());
		}
		catch (IllegalBlockSizeException e) {	
			Logging.error("Encrypt.decrypt: " +  e.getMessage());
		}
		catch (NullPointerException e) {
			Logging.error("Encrypt.decrypt: " +  e.getMessage());
		}
		
		
		Logging.debug(this.getClass().getName(), " ------> Decrypted: " + sResult );

		return sResult;
		
	}
	
}
	
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;



/**
 *
 * @author gdotta
 */
public class SmartcardTests {
    //add BC provider for validation to work
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    // Certificado extraido del eID
    private static String certificate_HEX_DER_encoded = "";

    private static final String HASH = "4D7CCCBD17064A12DD43021668679F7B488AFD55AAB1502E0CA8A55F5A8E2C0B";

    private static String HASH_Signature = "";

    public String[] leer(){
        try {
            return readCard();
        } catch (IOException ex) {
            Logger.getLogger(SmartcardTests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NoSuchProviderException ex) {
            Logger.getLogger(SmartcardTests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SmartcardTests.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String[6];
    }
    
    private String[] readCard() throws CardException, IOException, CertificateException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, Exception {
        // TODO code application logic here
        // show the list of available terminals
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        // get the first terminal
        CardTerminal terminal = terminals.get(0);
        // establish a connection with the card
        Card card = terminal.connect("T=0");
        // System.out.println("card ATR: " +
        CardChannel channel = card.getBasicChannel();
        //Log Configuration
        LogUtils logUtils = LogUtils.getInstance();
        logUtils.configure(System.getProperty("user.dir"),"");
        selectIAS(channel);
        String[] resp = datosCedula(channel);
        card.disconnect(false);
        return resp;
    }

    public static boolean getCPLCData(CardChannel channel) throws CardException, FileNotFoundException, UnsupportedEncodingException {
        String CLASS = "80";
        String INSTRUCTION = "CA";
        String PARAM1 = "9F";
        String PARAM2 = "7F";

        String dataIN = "";

        byte CLASSbyte = Utils.hexStringToByteArray(CLASS)[0];
        byte INSbyte = Utils.hexStringToByteArray(INSTRUCTION)[0];
        byte P1byte = Utils.hexStringToByteArray(PARAM1)[0];
        byte P2byte = Utils.hexStringToByteArray(PARAM2)[0];
        ResponseAPDU r = Utils.sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte, Utils.hexStringToByteArray(dataIN), 0);
        return (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00);
  
    }

    // Retorna true si pudo seleccionar la aplicacion IAS
    public static boolean selectIAS(CardChannel channel) throws CardException, FileNotFoundException, UnsupportedEncodingException {
        String CLASS = "00";
        String INSTRUCTION = "A4";
        String PARAM1 = "04";
        String PARAM2 = "00";

        String dataIN = "A00000001840000001634200"; //IAS AID
        // String dataIN = "A0000000180C000001634200";

        // ESTO DEBE SER EL APP ID de la IAS
        // REVISAR LA DOCUMENTACION DE GEMALTO PARA CONFIRMARLO
        byte CLASSbyte = Utils.hexStringToByteArray(CLASS)[0];
        byte INSbyte = Utils.hexStringToByteArray(INSTRUCTION)[0];
        byte P1byte = Utils.hexStringToByteArray(PARAM1)[0];
        byte P2byte = Utils.hexStringToByteArray(PARAM2)[0];
        ResponseAPDU r = Utils.sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte, Utils.hexStringToByteArray(dataIN), 0);
        return (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00);
    }
       // Precondicion: Verify PIN
    public static boolean MSE_SET_DST(CardChannel channel) throws CardException, FileNotFoundException, UnsupportedEncodingException {

        String CLASS = "00";
        String INSTRUCTION = "22";
        String PARAM1 = "41";
        String PARAM2 = "B6";

        // mse-set, en el documento de IAS esta al reves la especificacion.
        // Revisar bien los parámetros, el iso.
        String dataIN = "840101800102"; // Select the key pair (RSA/ECC) and the
        // signature ALGO

        byte CLASSbyte = Utils.hexStringToByteArray(CLASS)[0];
        byte INSbyte = Utils.hexStringToByteArray(INSTRUCTION)[0];
        byte P1byte = Utils.hexStringToByteArray(PARAM1)[0];
        byte P2byte = Utils.hexStringToByteArray(PARAM2)[0];
        ResponseAPDU r = Utils.sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte, Utils.hexStringToByteArray(dataIN), 0);
        return (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00);

        // El SW 6A80 indica error de codificacion, es decir, en los TLV
        // El SW 63Cx indica error de match y que quedan x intentos
        // 9000 es SW de exito
    }

    // Precondicion: Verify PIN
    public static boolean PSO_HASH(CardChannel channel) throws CardException, FileNotFoundException, UnsupportedEncodingException {

        String CLASS = "00";
        String INSTRUCTION = "2A";
        String PARAM1 = "90";
        String PARAM2 = "A0";

        String hash_external = HASH;
        String length = Utils.byteArrayToHex(Utils.intToByteArray(hash_external.length() / 2));

        String dataIN = "90"; // Select the key pair (RSA/ECC) and the signature
        // ALGO
        dataIN += length;
        dataIN += hash_external;

        byte CLASSbyte = Utils.hexStringToByteArray(CLASS)[0];
        byte INSbyte = Utils.hexStringToByteArray(INSTRUCTION)[0];
        byte P1byte = Utils.hexStringToByteArray(PARAM1)[0];
        byte P2byte = Utils.hexStringToByteArray(PARAM2)[0];
        ResponseAPDU r = Utils.sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte, Utils.hexStringToByteArray(dataIN), 0);
        return (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00);

        // El SW 6A80 indica error de codificacion, es decir, en los TLV
        // El SW 63Cx indica error de match y que quedan x intentos
        // 9000 es SW de exito
    }

    // Precondicion: PSO_HASH
    public static boolean PSO_CDS(CardChannel channel) throws CardException, FileNotFoundException, UnsupportedEncodingException {

        String CLASS = "00";
        String INSTRUCTION = "2A";
        String PARAM1 = "9E";
        String PARAM2 = "9A";

        String dataIN = "";

        byte CLASSbyte = Utils.hexStringToByteArray(CLASS)[0];
        byte INSbyte = Utils.hexStringToByteArray(INSTRUCTION)[0];
        byte P1byte = Utils.hexStringToByteArray(PARAM1)[0];
        byte P2byte = Utils.hexStringToByteArray(PARAM2)[0];
        ResponseAPDU r = Utils.sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte, Utils.hexStringToByteArray(dataIN), 0);

        HASH_Signature = Utils.byteArrayToHex(r.getData());

        return (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00);

        // El SW 6A80 indica error de codificacion, es decir, en los TLV
        // El SW 63Cx indica error de match y que quedan x intentos
        // 9000 es SW de exito
    }

    public static UserId getUserId(CardChannel channel) {
        //FCITemplate fcit = selectFile(channel, "7001");

        return null;
    }

    public static boolean readCertificate(CardChannel channel) throws CardException, Exception {

        FCITemplate fcit = selectFile(channel, "B001");
        certificate_HEX_DER_encoded = readBinary(channel, fcit.getFileSize());

        return true;
    }

    public String[] datosCedula(CardChannel channel) throws CertificateException, Exception {
        String[] resultado = new String[6];
        readCertificate(channel);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream b64eIDCertificate = new ByteArrayInputStream(Utils.hexStringToByteArray(certificate_HEX_DER_encoded));
        X509Certificate eIDCertificate = (X509Certificate) cf.generateCertificate(b64eIDCertificate);
        String certSerialNumber = Utils.formatHexaString(eIDCertificate.getSerialNumber().toString(16));
        resultado[0]= certSerialNumber;
        resultado[1]=eIDCertificate.getIssuerDN()+"";
        resultado[2]=eIDCertificate.getNotBefore()+"";
        resultado[3]=eIDCertificate.getNotAfter()+"";
        resultado[4]=eIDCertificate.getSubjectDN()+"";
        FCITemplate fcit7004 = selectFile(channel, "7004");
        resultado[5]= readBinary(channel, fcit7004.getFileSize());
        return resultado;
    }

    //POR AHORA ESTA SOPORTA SOLO LECTURA DE EF
    //CAPAZ SOPORTA DF TAMBIEN, PERO NO FUE HECHA PARA ESO
    public static FCITemplate selectFile(CardChannel channel, String fileID) throws CardException, Exception {

        String CLASS = "00";
        String INSTRUCTION = "A4";
        String PARAM1 = "00";
        String PARAM2 = "00";

        String dataIN = fileID;

        byte CLASSbyte = Utils.hexStringToByteArray(CLASS)[0];
        byte INSbyte = Utils.hexStringToByteArray(INSTRUCTION)[0];
        byte P1byte = Utils.hexStringToByteArray(PARAM1)[0];
        byte P2byte = Utils.hexStringToByteArray(PARAM2)[0];

        ResponseAPDU r = Utils.sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte, Utils.hexStringToByteArray(dataIN), 0);

        // Si la lectura del archivo es exitosa debo construir el fci template
        if (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00) {

            FCITemplate fcit = new FCITemplate();
            fcit.buildFromBuffer(r.getData(), 0, r.getData().length);
            return fcit;

        } else {

            return null;
        }

    }

    public static String readBinary(CardChannel channel, int fileSize) throws CardException, FileNotFoundException, UnsupportedEncodingException {

        // Construyo el Read Binary, lo que cambia en cada read son P1 y P2
        // porque van variando los offset para ir leyendo el binario hasta llegar al tamaño total
        // en cada read leo FF
        String CLASS = "00";
        String INSTRUCTION = "B0";
        String dataIN = "";
        String PARAM1;
        String PARAM2;

        int FF_int = Integer.parseInt("FF", 16);

        int cantBytes = 0;
        int dataOUTLength = 0; //le

        String binaryHexString = "";

        while (cantBytes < fileSize) {

            // Calculo el LE
            // Si la cantidad de Bytes que me quedan por obtener es mayor a
            // FF entonces me traigo FF. Sino me traigo los Bytes que me quedan.
            if (cantBytes + FF_int <= fileSize) {
                dataOUTLength = FF_int;
            } else {
                dataOUTLength = fileSize - cantBytes;
            }

            // Param1 y param2 comienzan en 00 00, voy incrementando FF
            // bytes hasta leer el total del binario.
            String PARAM1_PARAM2 = Utils.byteArrayToHex(Utils.intToByteArray(cantBytes));

            //uso solo p2 porque la cantidad de bytes que voy leyendo es menor a FF
            if (cantBytes <= 255) {
                PARAM1 = "00";
                PARAM2 = PARAM1_PARAM2.substring(0, 2);
            } else {
                PARAM1 = PARAM1_PARAM2.substring(0, 2);
                PARAM2 = PARAM1_PARAM2.substring(2, 4);
            }
            byte CLASSbyte = Utils.hexStringToByteArray(CLASS)[0];
            byte INSbyte = Utils.hexStringToByteArray(INSTRUCTION)[0];
            byte P1byte = Utils.hexStringToByteArray(PARAM1)[0];
            byte P2byte = Utils.hexStringToByteArray(PARAM2)[0];

            ResponseAPDU r = Utils.sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte, Utils.hexStringToByteArray(dataIN), dataOUTLength);

            binaryHexString += Utils.byteArrayToHex(r.getData());

            if (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00) {

                cantBytes += dataOUTLength;

            } else {
                // Fallo algun read binary
                return "";
            }

        }
        return binaryHexString;
    }

    public String[] leerCedula(int tipoDato) 
              throws CardException, IOException, CertificateException, InvalidKeyException, 
              NoSuchAlgorithmException, SignatureException, NoSuchPaddingException, 
              IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, Exception 
      {
        String[] resp= new String [5];
        // TODO code application logic here
        // show the list of available terminals
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        // get the first terminal
        CardTerminal terminal = terminals.get(0);
        if(terminal.isCardPresent()){
            // establish a connection with the card
            Card card = terminal.connect("T=0");
            // System.out.println("card ATR: " +
            CardChannel channel = card.getBasicChannel();
            //Log Configuration
            LogUtils logUtils = LogUtils.getInstance();
            logUtils.configure(System.getProperty("user.dir"),"");
            selectIAS(channel);
            
            if(tipoDato ==0){
                resp = sacarDatos(channel);
            }else{
                resp = sacarFoto(channel);
            }
            card.disconnect(false);        
        }
        return resp;
    }

    public String[] sacarDatos(CardChannel channel) throws CertificateException, Exception {
        String[] resultado = new String[5];
        readCertificate(channel);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream b64eIDCertificate = new ByteArrayInputStream(Utils.hexStringToByteArray(certificate_HEX_DER_encoded));
        X509Certificate eIDCertificate = (X509Certificate) cf.generateCertificate(b64eIDCertificate);
        String certSerialNumber = Utils.formatHexaString(eIDCertificate.getSerialNumber().toString(16));
        resultado[0]= certSerialNumber;
        resultado[1]=eIDCertificate.getIssuerDN()+"";
        resultado[2]=eIDCertificate.getNotBefore()+"";
        resultado[3]=eIDCertificate.getNotAfter()+"";
        resultado[4]=eIDCertificate.getSubjectDN()+"";
        return resultado;
    }
    public String[] sacarFoto(CardChannel channel) throws CertificateException, Exception {
        String[] resultado = new String[1];
        readCertificate(channel);
        FCITemplate fcit7004 = selectFile(channel, "7004");
        resultado[0]= readBinary(channel, fcit7004.getFileSize());
        return resultado;
    }


}

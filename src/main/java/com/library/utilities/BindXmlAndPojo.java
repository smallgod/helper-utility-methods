package com.library.utilities;

/*import com.advertexpo.addisplay.config.v1_0.Appconfig;
import com.advertexpo.addisplay.constants.ErrorCategory;
import com.advertexpo.addisplay.constants.ErrorCode;
import com.advertexpo.addisplay.exceptiontype.MyCustomException;*/
import com.library.customexception.MyCustomException;
import com.library.datamodel.Constants.ErrorCategory;
import com.library.datamodel.Constants.ErrorCode;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import com.sun.xml.bind.marshaller.DataWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BindXmlAndPojo {

    /**
     * Marshall information into xml.
     *
     * @param xmlObject
     * @param classToBind
     * @return
     */
    public static String objectToXMLOLD(Object xmlObject, Class... classToBind) throws MyCustomException {

        String xmlOutput = null;
        StringWriter sw = new StringWriter();

        JAXBContext jc;

        try {
            jc = JAXBContext.newInstance(classToBind);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //m.marshal( xmlObject, System.out );
            marshaller.marshal(xmlObject, sw);
        } catch (JAXBException e) {
            throw new MyCustomException("Error marshalling", ErrorCode.INTERNAL_ERR, "Error creating response: " + e.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
        }

        xmlOutput = sw.toString();

        return xmlOutput;
    }

    public static String objectToXML(Object xmlObject, Class... classToBind) throws JAXBException {

        String xmlOutput = null;

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new CharacterEscapeHandler() {
            @Override
            public void escape(char[] buf, int start, int len, boolean b, Writer out) throws IOException {
                out.write(buf, start, len);
            }
        });

        JAXBContext jc = JAXBContext.newInstance(classToBind);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        //marshaller.setProperty(CharacterEscapeHandler.class.getName(), new XmlCharacterHandler());
        //m.marshal( xmlObject, System.out );

        marshaller.marshal(xmlObject, dataWriter);

        xmlOutput = sw.toString();

        //JAXBContext jaxb = JAXBContext.newInstance("com.ats.vis.services.concentrator", com.ats.vis.services.concentrator.LoadDataRequest.class.getClassLoader());
        //Marshaller marshaller = jaxb.createMarshaller();
        return xmlOutput;
    }

    //    public static <T> String marshalFile(JAXBElement<T> config, Class classToBind) throws JAXBException {
//
//        StringWriter sw = new StringWriter();
//
//        JAXBContext jc = JAXBContext.newInstance(classToBind);
//        Marshaller marshaller = jc.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        //m.marshal( xmlObject, System.out );
//
//        //jaxb.com.library.datamodel.dsm_bridge.xmlpojos.file.ObjectFactory objectFactory = new jaxb.com.library.datamodel.dsm_bridge.xmlpojos.file.ObjectFactory();
//        //JAXBElement<ConfigType> configType = objectFactory.createConfig(config);
//        marshaller.marshal(config, sw);
//
//        //JAXBContext jaxb = JAXBContext.newInstance("com.ats.vis.services.concentrator", com.ats.vis.services.concentrator.LoadDataRequest.class.getClassLoader());
//        //Marshaller marshaller = jaxb.createMarshaller();
//        return sw.toString();
//    }
    /**
     * Unmarshall information into a java object
     *
     * @param xmlString
     * @param xsdFilesFolderLocation
     * @param classToBind
     * @return
     * @throws javax.xml.bind.JAXBException
     * @throws org.xml.sax.SAXException
     */
//    public static Object xmlToObject(String xmlString, String xsdFilesFolderLocation, Class... classToBind) throws SAXException, JAXBException, NullPointerException, RuntimeException {
//
//        InputSource is = new InputSource(new StringReader(xmlString));
//
//        //retrieve xsd file
//        String classXSDfile = getXSDfile(xsdFilesFolderLocation, classToBind[0]);
//
//        //validate the XML
//        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        Schema schema = sf.newSchema(new File(classXSDfile));
//
//        JAXBContext jc = JAXBContext.newInstance(classToBind);
//        Unmarshaller unmarshaller = jc.createUnmarshaller();
//
//        //uncomment to validate requests against XSD
//        //unmarshaller.setSchema(schema);
//        //unmarshaller.setEventHandler(new XMLValidationEventHandler());
//        Object xmlObject = unmarshaller.unmarshal(is);
//
//        return xmlObject;
//    }
    /**
     * *
     *
     * @param xsdFilesFolderLocation
     * @param classToBind
     * @return xsd file path
     */
//    public static String getXSDfile(String xsdFilesFolderLocation, Class classToBind) throws NullPointerException {
//
//        String xsdFilePath = null;
//
////        if (classToBind  == Servicerequest.class) {
////            xsdFilePath = xsdFilesFolderLocation + "ServiceBinding/Service.xsd";
////        } else 
//        if (classToBind == Appconfig.class) {
//            xsdFilePath = xsdFilesFolderLocation + "appconfigs/appconfigs.xsd";
//        } else {
//            throw new NullPointerException("Could not find XSD file for JAXB class: " + classToBind.toString());
//        }
//
//        return xsdFilePath;
//    }
    /**
     *
     * @param xmlFilePath
     * @param xsdFilesFolderLocation
     * @param classToBind
     * @return JAXB class
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     * @throws javax.xml.bind.ValidationException
     * @throws org.xml.sax.SAXException
     */
//    public static Object xmlFileToObject(String xmlFilePath, String xsdFilesFolderLocation, Class... classToBind) throws FileNotFoundException, UnsupportedEncodingException, SAXException, ValidationException, JAXBException, NullPointerException {
//
//        //if schema file loc is not known - http://docs.oracle.com/javase/6/docs/api/javax/xml/validation/SchemaFactory.html#newSchema%28%29
//        //SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//        //Schema schema = factory.newSchema();
//        //Unmarshaller unmarshaller = jc.createUnmarshaller();
//        //unmarshaller.setSchema(schema);
//        File file = new File(xmlFilePath);
//
//        InputStream inputStream = new FileInputStream(file);
//        Reader reader = new InputStreamReader(inputStream, "UTF-8");
//        InputSource is = new InputSource(reader);
//        is.setEncoding("UTF-8");
//
//        //retrieve xsd file
//        String classXSDfile = getXSDfile(xsdFilesFolderLocation, classToBind[0]);
//        
//        System.out.println("XSDFile got: " + classXSDfile);
//        System.out.println("FilePath   : " + file.getAbsolutePath());
//
//        //validate the XML
//        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        Schema schema = sf.newSchema(new File(classXSDfile));
//        
//        JAXBContext jc = JAXBContext.newInstance(classToBind);
//        Unmarshaller unmarshaller = jc.createUnmarshaller();
//        
//        unmarshaller.setSchema(schema);
//
//        unmarshaller.setEventHandler(new XMLValidationEventHandler());
//        //XMLObject xmlObject = (DBMSXMLObject) unmarshaller.unmarshal(new File(xmlFilePath)); 
//
//        Object xmlObject = null;
//        try{
//            xmlObject = unmarshaller.unmarshal(is);
//        }catch(JAXBException e){
//            e.printStackTrace();
//            System.out.println("error: " + e.getMessage());
//        }
//
//        System.out.println("done unmarshalling!!!!!!!!");
//
//        return xmlObject;
//    }
    /**
     *
     * @param <T>
     * @param xmlFilePath - the xml file to unmarshal (convert to Java Object)
     * @param xsdFilePath - file from which the xml file is generated
     * @param classToBind - corresponding java classs (JAXB) used for
     * marshalling/unmarshalling
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws SAXException
     * @throws ValidationException
     * @throws JAXBException
     * @throws NullPointerException
     */
    public static <T> Object xmlFileToObject(String xmlFilePath, String xsdFilePath, Class<T> classToBind) throws FileNotFoundException, UnsupportedEncodingException, SAXException, ValidationException, JAXBException, NullPointerException {

        //if schema file loc is not known - http://docs.oracle.com/javase/6/docs/api/javax/xml/validation/SchemaFactory.html#newSchema%28%29
        //SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        //Schema schema = factory.newSchema();
        //Unmarshaller unmarshaller = jc.createUnmarshaller();
        //unmarshaller.setSchema(schema);
        File file = new File(xmlFilePath);

        if (file.exists()) {
            System.out.println("File exists here in xmlFileToObject");
        } else {
            System.err.println("File DOESN'T exists here in xmlFileToObject");
        }

        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream, "utf-8");
        InputSource is = new InputSource(reader);
        is.setEncoding("utf-8");

        System.out.println("type of class to unmarshal to is: " + classToBind.getTypeName());

        //validate the XML
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(xsdFilePath));

        JAXBContext jc = JAXBContext.newInstance(classToBind);
        Unmarshaller unmarshaller = jc.createUnmarshaller();

        unmarshaller.setSchema(schema);

        unmarshaller.setEventHandler(new XMLValidationEventHandler());
        //XMLObject xmlObject = (DBMSXMLObject) unmarshaller.unmarshal(new File(xmlFilePath)); 

        Object xmlObject = null;
        try {
            //xmlObject = unmarshaller.unmarshal(is);
            xmlObject = JAXBIntrospector.getValue(unmarshaller.unmarshal(is));
        } catch (JAXBException e) {
            e.printStackTrace();
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("DEBUG: Done unmarshalling, Object is: " + xmlObject.getClass().getTypeName() + "  !!!!!!!!");

        return xmlObject;
    }

    public static <T> T xmlFileToObject1(String xmlFilePath, Class<T> classToBind) throws FileNotFoundException, UnsupportedEncodingException, JAXBException {

        File file = new File(xmlFilePath);

        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-8");

        JAXBContext jc = JAXBContext.newInstance(classToBind);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        T xmlObject = classToBind.cast(unmarshaller.unmarshal(is));
        //XMLObject xmlObject = (DBMSXMLObject)unmarshaller.unmarshal(file); //NOI18N

        return xmlObject;
    }
}

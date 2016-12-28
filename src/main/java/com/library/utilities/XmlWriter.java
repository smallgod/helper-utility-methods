package com.library.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlWriter {

    public static void testItOut(String[] args) {
        
        final String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <Emp id=\"1\"><name>Pankaj</name><age>25</age> <role>Developer</role><gen>Male</gen></Emp>";
        
        Document doc = convertStringToDocument(xmlStr);

        String str = convertDocumentToString(doc);
        System.out.println(str);
    }

    public static String convertDocumentToString(Document doc) {
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Document convertStringToDocument(String xmlStr) {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void stringToDom(String xmlSource, String xmlFilePath) throws SAXException, ParserConfigurationException, IOException, TransformerException {
        // Parse the given input
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

        // Write the parsed document to an xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(xmlFilePath));
        transformer.transform(source, result);
    }

    /**
     * Write an XML string to a file
     * 
     * @param xmlSource
     * @param xmlFilePath
     * @throws IOException 
     */
    public static void writeStringToFile(String xmlSource, String xmlFilePath) throws IOException {

        FileWriter fw = new FileWriter(xmlFilePath);
        fw.write(xmlSource);
        fw.close();
    }

}

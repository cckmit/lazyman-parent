package org.lazyman.common.util;

import org.lazyman.common.constant.StringPool;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

public final class JAXBUtils {
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    public static String convert2Xml(Object obj) throws Exception {
        return convert2Xml(obj, false, StringPool.UTF_8);
    }

    public static String convert2Xml(Object obj, boolean isWithHeader, String encoding) throws Exception {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        if (!isWithHeader) {
            return writer.toString();
        }
        return XML_HEADER + writer.toString();
    }

    public static <T> T convert2JavaBean(String xml, Class<T> c) throws Exception {
        xml = xml.replace(XML_HEADER, "");
        JAXBContext context = JAXBContext.newInstance(c);
        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
        XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xml));
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(xsr);
    }

    public static Object xmlToObjectSafe(String xml, Class<?> klass) throws Exception {
        JAXBContext context = JAXBContext.newInstance(klass);
        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
        XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xml));
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(xsr);
    }
}

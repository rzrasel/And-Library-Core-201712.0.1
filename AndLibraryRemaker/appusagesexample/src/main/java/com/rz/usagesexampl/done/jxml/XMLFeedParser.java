package com.rz.usagesexampl.done.jxml;

import android.content.Context;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class XMLFeedParser {
    private Context context;
    private CoreXMLFeedParser coreXMLFeedParser;
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser xmlPullParser;
    private InputStream inputStream;
    private String xmlFilePath;
    private boolean isXMLStringNull = false;
    private static String methodName = "methodName-var";

    public XMLFeedParser(Context argContext) {
        methodName = "XMLFeedParser(Context argContext)";
        this.context = argContext;
        isXMLStringNull = false;
        coreXMLFeedParser = new CoreXMLFeedParser(argContext);
    }

    /**
     * Function used to fetch an XML file from assets folder
     *
     * @param argFileName - XML file name to convert it to String
     * @return - return XML in String form
     */
    public String onReadAssetsFile(String argFileName) throws IOException {
        methodName = "String onReadAssetsFile(String argFileName)";
        return coreXMLFeedParser.onReadAssetsFile(argFileName);
    }

    public XMLFeedParser withTag(String argTagKey) {
        methodName = "XMLFeedParser withTag(String argTagKey)";
        //coreXMLFeedParser.withTag(argTagKey);
        return this;
    }

    public XMLFeedParser withAttribute(String argAttributeKey) {
        methodName = "XMLFeedParser withAttribute(String argAttributeKey)";
        //coreXMLFeedParser.withAttribute(argAttributeKey);
        return this;
    }

    public XMLFeedParser onXMLPrepareItems(String argXMLString) throws XmlPullParserException, UnsupportedEncodingException, IOException {
        methodName = "XMLFeedParser onXMLPrepareItems(String argXMLString)";
        //coreXMLFeedParser.onXMLPrepareItems(argXMLString);
        return this;
    }

    //|------------------------------------------------------------|
    public List<Map<String, String>> getXMLParsedItems(String argItemStartingEndingTag) throws Exception {
        methodName = "List<Map<String, String>> getXMLParsedItems(String argItemStartingEndingTag)";
        return coreXMLFeedParser.getXMLParsedItems(argItemStartingEndingTag);
    }
    //|------------------------------------------------------------|

    public List<Map<String, String>> getXMLParsedItems(List<String> argKeyList, String argItemStartingEndingTag) throws Exception {
        methodName = "List<Map<String, String>> getXMLParsedItems(List<String> argKeyList, String argItemStartingEndingTag)";
        return coreXMLFeedParser.getXMLParsedItems(argKeyList, argItemStartingEndingTag);
    }

    //|------------------------------------------------------------|
    public List<Map<String, String>> getAttributeItems() {
        methodName = "List<Map<String, String>> getAttributeItems()";
        return coreXMLFeedParser.getAttributeItems();
    }

    //|------------------------------------------------------------|
    public String getXMLByTagAttribute(String argXMLString, String argXMLTag, String argXMLAttribute, String argXMLAttributeValue) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        methodName = "String getXMLByTagAttribute(String argXMLString, String argXMLTag, String argXMLAttribute, String argXMLAttributeValue)";
        return coreXMLFeedParser.getXMLByTagAttribute(argXMLString, argXMLTag, argXMLAttribute, argXMLAttributeValue);
    }
    //|------------------------------------------------------------|
}


/*
<?xml version="1.0" encoding="utf-8"?>
<word_book_arabic_to_bangal>
    <word_list subjective_category="BANK_MANAGER">
        <word_item>
            <audio_file>amake_bolun</audio_file>
            <main_word>আমাকে দেখুন</main_word>
            <secondary_word>(কুললি)</secondary_word>
        </word_item>
        <word_item>
            <audio_file>apnar_vai_acen_ki</audio_file>
            <main_word>আপনার ভাই আছেন কি?</main_word>
            <secondary_word>(হাল আখুকা মাওজুদ)</secondary_word>
        </word_item>
    </word_list>
    <word_list subjective_category="PASSPORT_OFFICE">
        <word_item>
            <audio_file>amake_bolun</audio_file>
            <main_word>করিম কোথায় যাচ্ছ</main_word>
            <secondary_word>(আইনা তাযহাবু, ইয়া করিম)</secondary_word>
        </word_item>
        <word_item>
            <audio_file>amake_bolun</audio_file>
            <main_word>পাসপোর্ট অফিসে যাচ্ছি</main_word>
            <secondary_word>(ইলা মাকতাবেল জাওয়ায)</secondary_word>
        </word_item>
    </word_list>
</word_book_arabic_to_bangal>
XMLFeedParser xmlFeedParser = new XMLFeedParser(context);
String xmlStr = xmlFeedParser.onReadAssetsFile("db_dir/word_book_arabic_to_bangal.xml");
String xmlTag = "word_list";
String xmlAttr = "subjective_category";
String xmlAttrValue = "BANK_MANAGER";
xmlStr = xmlFeedParser.getXMLTagByAttributes(xmlStr, xmlTag, xmlAttr, xmlAttrValue);
List<String> listXMLTags = new ArrayList<>();
listXMLTags.add("audio_file");
listXMLTags.add("main_word");
listXMLTags.add("secondary_word");
String xmlItemStartingTag = "word_item";
List<Map<String, String>> listItems = xmlFeedParser.onXMLPrepareItems(xmlStr)
        .getXMLParsedItems(listXMLTags, xmlItemStartingTag);
for (Map<String, String> listItem : listItems) {
    for (Map.Entry<String, String> entry : listItem.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        System.out.println("XML_KEY: " + key + " - VALUE: " + value);
    }
}
*/
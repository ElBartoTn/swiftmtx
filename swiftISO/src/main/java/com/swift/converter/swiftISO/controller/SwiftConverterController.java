package com.swift.converter.swiftISO.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.HashMap;
import java.util.Map;
@RestController
public class SwiftConverterController {
	@PostMapping("/convert")
	
	public String ConvertMtToMx(@RequestBody String mtMessage){
	return parseAndMapMT101ToXML(mtMessage);
	}
	public static String parseAndMapMT101ToXML(String mt101Message) {
        Map<String, String> fields = parseMT101Message(mt101Message);

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<MT101>\n");
        xmlBuilder.append("    <Reference>").append(fields.get("20")).append("</Reference>\n");
        xmlBuilder.append("    <CreditDebit>").append(fields.get("23B")).append("</CreditDebit>\n");
        xmlBuilder.append("    <Amount>").append(fields.get("32B")).append("</Amount>\n");
        xmlBuilder.append("    <OrderingCustomer>").append(fields.get("50K")).append("</OrderingCustomer>\n");
        xmlBuilder.append("    <BeneficiaryCustomer>").append(fields.get("59")).append("</BeneficiaryCustomer>\n");
        xmlBuilder.append("    <DetailsOfCharges>").append(fields.get("71A")).append("</DetailsOfCharges>\n");
        xmlBuilder.append("</MT101>");

        return xmlBuilder.toString();
    }

    public static Map<String, String> parseMT101Message(String mt101Message) {
        Map<String, String> fields = new HashMap<>();
        String[] lines = mt101Message.split("\n");
        for (String line : lines) {
            if (line.startsWith(":")) {
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    String fieldTag = parts[1];
                    String fieldValue = parts[2];
                    fields.put(fieldTag, fieldValue);
                }
            }
        }
        return fields;
    }
}


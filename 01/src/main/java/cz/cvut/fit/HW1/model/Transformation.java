package cz.cvut.fit.HW1.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

record Person(String name, String surname) {}

public class Transformation {
    private SubmitDTO submit;
    private final String pureMessage;

    public Transformation(String message) {
        this.pureMessage = getTextBetweenTripleEqualSymbols(message);
    }

    public void transform() {
        String id = getBookingDetail("Tour id");
        String location = getBookingDetail("Location");
        String[] person = getBookingDetail("Person").split(" ");

        submit = new SubmitDTO(id, location, new Person(person[0], person[1]));

    }

    public SubmitDTO getTransformedSubmit() {
        return submit;
    }

    private String getTextBetweenTripleEqualSymbols(String str) {
        String regex = "===([^]]+)===";
        return getSubstring(regex, str);
    }

    private String getBookingDetail(String detail) {
        String regex = detail + ": \"(.*?)\"";
        return getSubstring(regex, pureMessage);
    }

    private String getSubstring(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        return matcher.group(1);
    }
}



package at.htl.iea.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static Parser instance = null;

    private void Parser() {}

    public static Parser getInstance () {
        if (Parser.instance == null) {
            Parser.instance = new Parser();
        }
        return Parser.instance;
    }

    public void persist(String fileContent) throws ParseException {
        //alle zahlungen (vom csv-file) werden in dieser liste gespeichert (und persistiert in der DB)
        List<Zahlung> zahlungen = new ArrayList<>(); // besser als linked list
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy");

        String[] lines = fileContent.split("\n");
        String [] lineElements;
        for(int i = 1; i < lines.length; i++){ //1. zeile überspringen
            lineElements = lines[i].split(";");
            Zahlung z = new Zahlung(format.parse(lineElements[0].replaceAll("\"", "")), lineElements[1], lineElements[2], lineElements[3], lineElements[4], lineElements[5], replaceLast(lineElements[6].replaceAll("\\.", ""), ",", "."), lineElements[7], lineElements[8], lineElements[9], lineElements[10], format.parse(lineElements[11].replaceAll("\"", "")));
            z = entfernenVonHochkommasBeiZahlung(z, "\"", ""); // "18.10.2018" --> 18.10.018 ... "100,00" --> 100,00
            zahlungen.add(z);
        }

        Database.initJdbc();
        for (Zahlung z : zahlungen){
            Database.insertIntoDatabase(z); //daten in der db persistieren
        }


        //for(Zahlung z : zahlungen){
        //    System.out.println(z.toString()); //ausgabe der vitalen informationen einer zahlung
        //}

    }

    private static String replaceLast(String string, String toReplace, String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + toReplace.length(), string.length());
        } else {
            return string;
        }
    }

    private static Zahlung entfernenVonHochkommasBeiZahlung(Zahlung z, String regex, String replacement) {
        //booking date wird in persist() bearbeitet (.format(...)) (bookingDate ist Date)
        z.setAmount(z.getAmount().replaceAll(regex, replacement));
        z.setBookingText(z.getBookingText().replaceAll(regex, replacement));
        z.setInitialRecognitionReference(z.getInitialRecognitionReference().replaceAll(regex, replacement));
        z.setNote(z.getNote().replaceAll(regex, replacement));
        z.setPartnerBankCode(z.getPartnerBankCode().replaceAll(regex, replacement));
        z.setPartnerBic(z.getPartnerBic().replaceAll(regex, replacement));
        z.setPartnerIban(z.getPartnerIban().replaceAll(regex, replacement));
        z.setPartnerAccountNumber(z.getPartnerAccountNumber().replaceAll(regex, replacement));
        z.setPartnerName(z.getPartnerName().replaceAll(regex, replacement));
        // value date wird in persist() bearbeitet (.format(...)) (valueDate ist Date)
        z.setCurrency(z.getCurrency().replaceAll(regex, replacement));
        return z;
    }

    // momentan NICHT in verwendung [löschen wenn keine verwendung vorhanden ist im späteren verlauf]
    private static String[] replaceCharacterFromArray(String[] lineElements, String regex, String newReplacement) {
        int len = lineElements.length;
        for (int i = 0; i < len; i++){
            lineElements[i] = lineElements[i].replaceAll(regex, newReplacement);
        }
        return lineElements;
    }
}
package de.tu.darmstadt.dataModel;

import java.time.LocalDate;
import java.util.Date;

/**
 * This class is used to test any functionalities of data model.
 */
public class DataModelPlayground {

    public static void main(String[] args) {
        test_birth_date();
    }


    private static void test_birth_date() {

        LocalDate date = LocalDate.now();
        String date_as_string = date.toString();

        String[] explode = date_as_string.split("-");

        System.out.println(date_as_string);
        for (String s : explode) {
            System.out.println(s);
        }

        LocalDate date2 = LocalDate.parse(date_as_string);
        System.out.println(date2);

        String h = "   Hello World     ";
        System.out.println(h + "!");
        System.out.println(h.trim() + "!");

    }

}

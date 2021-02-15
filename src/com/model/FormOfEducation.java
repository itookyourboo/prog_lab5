package com.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * FormOfEducation enum class. Contains three values.
 */
@XmlRootElement(name="FormOfEducation")
@XmlEnum
public enum FormOfEducation {
    DISTANCE_EDUCATION,
    FULL_TIME_EDUCATION,
    EVENING_CLASSES;

    /**
     * Returns comma separated list with the forms.
     * @return DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (FormOfEducation formOfEducation: values()) {
            nameList.append(formOfEducation.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
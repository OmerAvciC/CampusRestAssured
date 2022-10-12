package GradeLevel;

import org.apache.commons.lang3.RandomStringUtils;

public class GradeLevel {

        String id;
        String name;
        String shortName;

        String order;
        String nextGradeLevel;
        String translateName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getNextGradeLevel() {
        return nextGradeLevel;
    }

    public void setNextGradeLevel(String nextGradeLevel) {
        this.nextGradeLevel = nextGradeLevel;
    }

    public String getTranslateName() {
        return translateName;
    }

    public void setTranslateName(String translateName) {
        this.translateName = translateName;
    }
    public static String getRandomShortName() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }
    public static String getRandomName() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }


}

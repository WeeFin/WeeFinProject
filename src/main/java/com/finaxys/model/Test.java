package com.finaxys.model;

public class Test {

    String field2;

    long count;

    public Test(String field2, long count) {
        this.field2 = field2;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Test{");
        sb.append("field2='").append(field2).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    public static Test fromString(String test) {
        System.err.println(test);
        String[] splittedValues = test.split("\\s+");
        return new Test(splittedValues[0], Long.valueOf(splittedValues[1]));
    }
}

package model;

public enum State {
    NORMAL("健康", 0),
    SUSPECTED("疑似", 1),
    SHADOW("潜伏", 2),
    CONFIRMED("确诊", 3),
    FREEZE("隔离", 4),
    CURED("治癒", 5);

    public final String name;
    public final int value;

    State(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }
}

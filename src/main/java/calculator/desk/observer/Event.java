package calculator.desk.observer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Event {
    private UpdateNumbers updateNumbers;
    private RepaintScreen repaintScreen;
    private SetOperation setOperation;
    private PerformBaseAction performBaseAction;
    private static Event event;

    public static Event getInstance() {
        if (event == null) {
            event = new Event();
        }
        return event;
    }
}

package level.menu;

public class MenuItem {

    @FunctionalInterface
    interface MenuAction {
        void go();
    }

    final private String name;
    final private MenuAction action;

    public MenuItem(String name, MenuAction action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public MenuAction getAction() {
        return action;
    }
}
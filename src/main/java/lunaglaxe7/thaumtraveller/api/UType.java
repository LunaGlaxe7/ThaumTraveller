package lunaglaxe7.thaumtraveller.api;

import java.util.HashMap;

public class UType {

    private final String name;
    private static HashMap<String, UType> types = new HashMap<>();

    // 传导升级，每层使vis消耗减少10%
    // upgrade of conducting, every times reduces 10% consuming of vis
    public static UType CONDUCTING = new UType("conducting");

    public UType(String name) {
        this.name = name;
        types.put(this.name, this);
    }

    public String getName() {
        return name;
    }

    public static UType getTypeByName(String name) {
        return types.get(name);
    }
}

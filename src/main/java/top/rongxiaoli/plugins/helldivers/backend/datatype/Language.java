package top.rongxiaoli.plugins.helldivers.backend.datatype;

import java.util.HashMap;

public enum Language {
    EN(),
    ZHS(),
    ZHT();
    public HashMap<String, String> toHeaderMap() {
        final String headerName = "Accept-Language";
        HashMap<String, String> ret = new HashMap<>();
        switch (this) {
            case EN:
                ret.put(headerName, "en");
                break;
            default:
            case ZHS:
                ret.put(headerName, "zh-cn");
                break;
            case ZHT:
                ret.put(headerName, "zh-hk");
                break;
        }
        return ret;
    }
}

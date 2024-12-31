package top.rongxiaoli.backend.Utils;

import net.mamoe.mirai.utils.MiraiLogger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassEnumerator {
    public ClassEnumerator(MiraiLogger logger) {
        LOGGER = logger;
    }
    private final MiraiLogger LOGGER;
    private List<Class> loadClassByLoader(ClassLoader loader) throws URISyntaxException, IOException {
        List<Class> out = new ArrayList<>();
        Enumeration<URL> urls;
        try {
            urls = loader.getResources("");
        } catch (IOException e) {
            LOGGER.error("程序出现错误，请上报开发者。错误如下：");
            LOGGER.error(e);
            LOGGER.verbose("Exception打印完成，错误继续。");
            throw e;
        }
        while (urls.hasMoreElements()) {
            try {
                URL url = urls.nextElement();
                if (!url.getProtocol().equals("file")) break;
                File urlTarget = new File(url.toURI());
                loadClassByPath(null, urlTarget.getPath(), out, loader);
            } catch (URISyntaxException e) {
                LOGGER.error("程序出现错误，请上报开发者。错误如下：");
                LOGGER.error(e);
                LOGGER.verbose("Exception打印完成，错误继续。");
                throw e;
            }
        }
        return out;
    }
    private void loadClassByPath(String root, String path, List<Class> list, ClassLoader loader) throws ClassNotFoundException {
        File f = new File(path);
        if (root == null) root = f.getPath();
        if (f.isFile() && f.getName().matches("^.*\\.class$") && f.getPath().contains("base")) {
            String classPath = f.getPath();
            String className = classPath.substring(root.length() + 1, classPath.length() - 6).replace("/", ".").replace("\\", ".");
            try {
                list.add(loader.loadClass(className));
            } catch (ClassNotFoundException e) {
                LOGGER.error("程序出现错误，请上报开发者。错误如下：");
                LOGGER.error(e);
                LOGGER.verbose("Exception打印完成，错误继续。");
                throw e;
            }
        } else {
            File[] fs = f.listFiles(); // Directory.
            if (fs == null) return;
            for (File file :
                    fs) {
                loadClassByPath(root, file.getPath(), list, loader);
            }
        }
    }
}

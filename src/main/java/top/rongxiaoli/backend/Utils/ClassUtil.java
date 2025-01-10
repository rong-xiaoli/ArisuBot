package top.rongxiaoli.backend.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {
    public static List<Class<?>> scan(@NotNull Package pkg) {
        String pName = pkg.getName();
        return getClasses(pName);
    }
    public static List<Class<?>> scan(@NotNull String pkg) {
        return getClasses(pkg);
    }
    public static List<Class<?>> scan(Package pkg, ClassLoader loader) {
        String pName = pkg.getName();
        return getClasses(pName, loader);
    }
    private static List<Class<?>> getClasses(String packageName) {
        return getClasses(packageName, Thread.currentThread().getContextClassLoader());
    }
    private static List<Class<?>> getClasses(String packageName, ClassLoader loader) {
        return getClasses(packageName, loader, true);
    }
    private static List<Class<?>> getClasses(String packageName,ClassLoader loader, final boolean recursive) {
        List<Class<?>> out = new ArrayList<>();
        String packDirName = packageName.replace(".", "/");
        Enumeration<URL> dirs;
        try {
            dirs = loader.getResources(packDirName);
            scanClassMainLoop(dirs, packageName, recursive, out, packDirName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    private static void scanClassMainLoop(Enumeration<URL> dirs, String packageName, final boolean recursive, List<Class<?>> classes, String packageDirName) throws IOException {
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if (protocol.equals("file")) {
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findClassesByFile(packageName, filePath, recursive, classes);
            } else if (protocol.equals("jar")) {
                getClassByJarFile(url, packageDirName, packageName, recursive, classes);
            }
        }
    }

    private static void processClassFile(File singleFile, List<Class<?>> classes, String packageName) {
        String className = singleFile.getName().substring(0, singleFile.getName().length() - 6);
        try {
            classes.add(Class.forName(packageName + "." + className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static void getClassByJarFile(URL url, String packageDirName, String packageName, boolean recursive, List<Class<?>> classes) {
        try (JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile()) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.charAt(0) == '/') name = name.substring(1);
                if (name.startsWith(packageDirName)) processJarFile(name, packageName, recursive, entry, classes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void processJarFile(String name, String packageName, boolean recursive, JarEntry entry, List<Class<?>> classes) {
        int idx = name.lastIndexOf('/');
        if (idx != -1) {
            packageName = name.substring(0,idx).replace('/', '.');
        }
        if ((idx != -1) || recursive) {
            judgeIfClass(name, packageName, entry, classes);
        }
    }
    private static void judgeIfClass(String name, String packageName, JarEntry entry, List<Class<?>> classes) {
        if (name.endsWith(".class") && !entry.isDirectory()) {
            String className = name.substring(packageName.length() + 1, name.length() - 6);
            try {
                classes.add(Class.forName(packageName + '.' + className));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void findClassesByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles(f -> (recursive && f.isDirectory()) || (f.getName().endsWith(".class")));
        for (File singleFile :
                files != null ? files : new File[0]) {
            if (singleFile.isDirectory()) {
                findClassesByFile(packageName + "." + singleFile.getName(), singleFile.getAbsolutePath(), recursive, classes);
            } else {
                processClassFile(singleFile, classes, packageName);
            }
        }
    }
}

package com.yin.util;
/**
 * @author 17694
 * @date 2022/01/05
 **/


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * 类加载过程 加载 链接 初始化
 *
 * 什么时候初始化  遇到new、getstatic、putstatic或invokestatic这四条字节码指令时，
 * 如果类型没有进行过初始 化，则需要先触发其初始化阶段。有
 */

/**
 * @ClassName : ClassUtil 
 * @Description : 自定义的类加载器   加载类
 */
public final class ClassUtil {

    /**
     * 测试 类加载器
     * @param args
     */
    public static void main(String[] args) throws IOException {
        //sun.misc.Launcher$AppClassLoader@18b4aac2  系统类加载器
        ClassLoader classLoader = getClassLoader();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Enumeration<URL> resources = systemClassLoader.getResources("com/yin");
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            String s = url.getPath().replaceAll("%20", "");
            String protocol = url.getProtocol();
            System.out.println(protocol);
            System.out.println(s);
        }

        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = classLoader.getResourceAsStream("test.properties");
            properties.load(resourceAsStream);
            System.out.println(properties);
            resourceAsStream.close();
        } catch (Exception e) {
            log.error("can not find file");
            e.printStackTrace();
        }
        System.out.println(getClassLoader());
    }

    private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        //log.info(contextClassLoader.loadClass());
        return contextClassLoader;
    }

    /**
     * 加载类
     */
        public static Class<?> loadClass(String className,boolean isInitialized){
            Class<?> cla;
            try{
                log.info("load class success");
                //using the given class loader 使用当前线程的类加载器
                cla = Class.forName(className,isInitialized,getClassLoader());
                log.info(cla.getName());
            }catch (ClassNotFoundException e){
                log.error("load class failure" , e);
                throw new RuntimeException(e);
            }
            return cla;
        }

    /**
     * 获取指定包名下的所有类
     */
    public  static Set<Class<?>>  getClassSet(String packageName){
                // 本质是一个Hashmap
                Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()){
               URL url = urls.nextElement();
               if(url != null){
                   String protocol = url.getProtocol();
                   if("file".equals(protocol)){
                       String packagePath = url.getPath().replaceAll("%20", "");
                       addClass(classSet,packagePath,packageName);
                   }else if("jar".equals(protocol)){
                       JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                       if(jarURLConnection != null){
                           JarFile jarFile = jarURLConnection.getJarFile();
                           if(jarFile != null){
                               Enumeration<JarEntry> entries = jarFile.entries();
                               while (entries.hasMoreElements()){
                                   JarEntry jarEntry = entries.nextElement();
                                   String jarEntryName = jarEntry.getName();
                                                    if(jarEntryName.endsWith(".class")){
                                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", "");
                                                        doAddClass(classSet,className);
                                                    }
                               }
                           }
                       }

                   }

               }
            }
        }catch (Exception e){
            log.error("get class set failure",e);
            throw new RuntimeException(e);
        }

                return classSet;
    }

    private static void doAddClass(Set<Class<?>> classeSet, String className) {
            Class<?> cla = loadClass(className,false);
            classeSet.add(cla);
    }

    private static void addClass(Set<Class<?>> classeSet, String packagePath, String packageName) {
            File[] files  = new File(packagePath).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.isFile() && file.getName().endsWith(".class"))||file.isDirectory();
                }
            });
            for (File file : files) {
                    String filename = file.getName();
                    if(file.isFile()){
                        String className = filename.substring(0, filename.lastIndexOf("."));
                        if (StringUtils.isNotEmpty(packageName)) {
                                className = packageName + "." + className;
                        }
                        doAddClass(classeSet,className);
                    }else {
                            String subPackagePath = filename;
                            if(StringUtils.isNotEmpty(packagePath)){
                                subPackagePath = packagePath + "/" + subPackagePath;
                            }
                            String subPackageName = filename;
                        if(StringUtils.isNotEmpty(packageName)){
                            subPackageName = packageName + "." + subPackageName;
                        }
                        addClass(classeSet , subPackagePath , subPackageName);
                    }
            }
    }

    /**
     * 默认加载参数
     * @param name
     */
    public static void loadClass(String name) {
        loadClass(name,false);
    }
}

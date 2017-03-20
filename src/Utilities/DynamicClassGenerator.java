package Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;


/**
 * Created by Jimmy Ramos on 03-Mar-17.
 */
public class DynamicClassGenerator {
    public static void main(String[] args) throws Exception {

        File tempfldr = new File("./src/");
        // tempfldr.mkdirs();
        // create an empty source file
        // File sourceFile = File.createTempFile("Hello",".java",tempfldr);

        File sourceFile = new File("./src/hello.java");
        //  sourceFile.deleteOnExit();

        // generate the source code, using the source filename as the class name
        String classname = sourceFile.getName().split("\\.")[0];
        String sourceCode = "public class " + classname + "{ public void hello() { System.out.print(\"Hello world\");}}";

        // write the source code into the source file
        FileWriter writer = new FileWriter(sourceFile);
        writer.write(sourceCode);
        writer.close();

        // compile the source file
       /* JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        File parentDirectory = sourceFile.getParentFile();
        //File parentDirectory = new File("./src/");
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parentDirectory));
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();

        // load the compiled class
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { parentDirectory.toURI().toURL() });
        Class<?> helloClass = classLoader.loadClass(classname);

        // call a method on the loaded class
        Method helloMethod = helloClass.getDeclaredMethod("hello");
        helloMethod.invoke(helloClass.newInstance());*/
    }

    public static void createNewClass(String directoryAndClassName, String code) throws IOException {
        File sourceFile = new File(directoryAndClassName);

        // generate the source code, using the source filename as the class name
        String classname = sourceFile.getName().split("\\.")[0];
        String sourceCode = "package Automaton.Parser;" +
                "\n import com.google.gson.Gson; " +
                "\n import Automaton.Automaton.*;" +
                "\n import java_cup.runtime.Symbol;" +
                "\n import java_cup.runtime.Scanner;" +
                "\n import java.util.Stack;" +
                "\n import java.util.ArrayList;" +
                "\n import java.util.stream.Collectors;" +
                "\n  import java.util.List;  " +
                " import java.io.BufferedReader;\n" +
                " import java.io.FileReader;\n" +
                " import java.io.IOException;" +
                " import Utilities.DynamicClassGenerator;"+
                "\n public class " + classname + code;

        // write the source code into the source file
        FileWriter writer = new FileWriter(sourceFile);
        writer.write(sourceCode);
        writer.close();
    }

    public static void createClassAndExecuteCode(String code) throws Exception {
        // create an empty source file
        File sourceFile = File.createTempFile("Hello", ".java");
        sourceFile.deleteOnExit();

        // generate the source code, using the source filename as the class name
        String classname = sourceFile.getName().split("\\.")[0];
        String sourceCode = "public class " + classname + "{ public void hello() { "+ code +"}}";

        // write the source code into the source file
        FileWriter writer = new FileWriter(sourceFile);
        writer.write(sourceCode);
        writer.close();

        // compile the source file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        File parentDirectory = sourceFile.getParentFile();
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parentDirectory));
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();

        // load the compiled class
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { parentDirectory.toURI().toURL() });
        Class<?> helloClass = classLoader.loadClass(classname);

        // call a method on the loaded class
        Method helloMethod = helloClass.getDeclaredMethod("hello");
        helloMethod.invoke(helloClass.newInstance());
    }
}

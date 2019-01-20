package compilerUtil;
 
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
 

/**
 * @author     : jialin
 * @group      : THS_JAVA_PLATFORM
 * @Date       : 2014-10-25 上午10:43:42
 * @Comments   : 原理：
 * 在Java SE6中最好的方法是使用StandardJavaFileManager类。
 * 这个类可以很好地控制输入、输出，并且可以通过DiagnosticListener得到诊断信息，
 * 而DiagnosticCollector类就是listener的实现。
 * 使用StandardJavaFileManager需要两步。
 * 首先建立一个DiagnosticCollector实例以及通过JavaCompiler的getStandardFileManager()方法得到一个StandardFileManager对象。
 * 最后通过CompilationTask中的call方法编译源程序。
 * @Version    : 1.0.0
 */
public class DynamicEngine {
    //单例
    private static DynamicEngine ourInstance = new DynamicEngine();
 
    public static DynamicEngine getInstance() {
        return ourInstance;
    }
    private URLClassLoader parentClassLoader;
    private String classpath;
    private DynamicEngine() {
        //获取类加载器
        this.parentClassLoader = (URLClassLoader) this.getClass().getClassLoader();
        
        //创建classpath
        this.buildClassPath();
    }
    
   
    /**
     * @MethodName	: 创建classpath
     * @Description	: TODO
     */
    private void buildClassPath() {
        this.classpath = null;
        StringBuilder sb = new StringBuilder();
        for (URL url : this.parentClassLoader.getURLs()) {
            String p = url.getFile();
            sb.append(p).append(File.pathSeparator);
        }
        this.classpath = sb.toString();
    }
    
    /**
     * @MethodName	: 编译java代码到Object
     * @Description	: TODO
     * @param fullClassName   类名
     * @param javaCode  类代码
     * @return Object
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object javaCodeToObject(String fullClassName, String javaCode) throws IllegalAccessException, InstantiationException {
        long start = System.currentTimeMillis(); //记录开始编译时间
        Object instance = null;
        //获取系统编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); 
        // 建立DiagnosticCollector对象
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        
         // 建立用于保存被编译文件名的对象
         // 每个文件被保存在一个从JavaFileObject继承的类中
        ClassFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(diagnostics, null, null));
 
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new CharSequenceJavaFileObject(fullClassName, javaCode));
 
        //使用编译选项可以改变默认编译行为。编译选项是一个元素为String类型的Iterable集合
        List<String> options = new ArrayList<String>();
        options.add("-encoding");
        options.add("UTF-8");
        options.add("-classpath");
        options.add(this.classpath);
 
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, jfiles);
        // 编译源程序
        boolean success = task.call();
        
        ////////
        Map<String,Object> map = new HashMap<String,Object>();
        ////////
        if (success) {
            //如果编译成功，用类加载器加载该类
            JavaClassObject jco = fileManager.getJavaClassObject();
            DynamicClassLoader dynamicClassLoader = new DynamicClassLoader(this.parentClassLoader);
            Class<?> clazz = dynamicClassLoader.loadClass(fullClassName,jco);
            instance = clazz.newInstance();
      ////////
            try {
				Method method = clazz.getMethod("solution", String[].class);
				map.put("method", method);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
            map.put("instance", instance);
      ////////
        } else {
            //如果想得到具体的编译错误，可以对Diagnostics进行扫描
            String error = "";
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                error = error + compilePrint(diagnostic);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("javaCodeToObject use:"+(end-start)+"ms");
        return map;
    }
 
    /**
     * @MethodName	: compilePrint
     * @Description	: 输出编译错误信息
     * @param diagnostic
     * @return
     */
    private String compilePrint(Diagnostic diagnostic) {
        System.out.println("Code:" + diagnostic.getCode());
        System.out.println("Kind:" + diagnostic.getKind());
        System.out.println("Position:" + diagnostic.getPosition());
        System.out.println("Start Position:" + diagnostic.getStartPosition());
        System.out.println("End Position:" + diagnostic.getEndPosition());
        System.out.println("Source:" + diagnostic.getSource());
        System.out.println("Message:" + diagnostic.getMessage(null));
        System.out.println("LineNumber:" + diagnostic.getLineNumber());
        System.out.println("ColumnNumber:" + diagnostic.getColumnNumber());
        StringBuffer res = new StringBuffer();
        res.append("Code:[" + diagnostic.getCode() + "]\n");
        res.append("Kind:[" + diagnostic.getKind() + "]\n");
        res.append("Position:[" + diagnostic.getPosition() + "]\n");
        res.append("Start Position:[" + diagnostic.getStartPosition() + "]\n");
        res.append("End Position:[" + diagnostic.getEndPosition() + "]\n");
        res.append("Source:[" + diagnostic.getSource() + "]\n");
        res.append("Message:[" + diagnostic.getMessage(null) + "]\n");
        res.append("LineNumber:[" + diagnostic.getLineNumber() + "]\n");
        res.append("ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n");
        return res.toString();
    }
}
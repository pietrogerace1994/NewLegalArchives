package velocity;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class Main {
	public static void main(String[] args) throws Throwable {
		Velocity.init();

		VelocityContext ctx = new VelocityContext();

		List<FormObject> listaAttributi = new ArrayList<FormObject>();

		generateJspFormEntity(listaAttributi);

		ctx.put("listaAttributi", listaAttributi);

	}

	@SuppressWarnings("rawtypes")
	private static void generateJspFormEntity(List<FormObject> listaAttributi) throws Throwable {
		String packageName = "eng.la.model";
		List<Class> classes = getAllClasses(packageName);
		if( classes != null && classes.size() >  0 ){
			for( Class clazz : classes){
				generateJsp(clazz);
			}
		}
		
	}

	@SuppressWarnings("rawtypes")
	private static void generateJsp(Class clazz) {
		String className = clazz.getName();
		String titoloPagina = toUserFriendlyString(className);
		
		PageObject page = new PageObject();
		page.setTitolo(titoloPagina);
		page.setPageClass(className.toLowerCase()+"Class");
		page.setPageId(className.toLowerCase()+"PageId");
		page.setFormId(className.toLowerCase()+"FormId");
		
		List<FormObject> formObjects = new ArrayList<FormObject>();
		
	    Field[] allFields = clazz.getDeclaredFields();
	    for (Field field : allFields) {
	        if (Modifier.isPrivate(field.getModifiers())) {
	            Class classField = field.getType();
	            FormObject formObject = new FormObject();
	            formObject.setLabel(toUserFriendlyString(field.getName()));
	            formObject.setName(field.getName());
	            if( classField.equals(String.class) ){
	            	formObject.setType("text");
	            }
	            formObjects.add(formObject);
	        }
	    }
	}

	private static String toUserFriendlyString(String string) {
		String[] words = string.split("(?<!^)(?=[A-Z])");
		String returnValue = "";
		for( String word : words ){
			returnValue += word + " ";
		} 
		returnValue = returnValue.trim();
		return returnValue ;
	}

	@SuppressWarnings("rawtypes")
	private static List<Class> getAllClasses(String packageName) throws Throwable {
		ClassLoader classLoader = Main.class.getClassLoader(); 
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 *
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(
						Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
}

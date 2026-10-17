package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Dynamic loader: reads FQCNs from config.txt (classpath), instantiates, injects via reflection. */
public class Presentation2 {

    public static void main(String[] args) {
        try {
            // 1. Read class names (line 1 = DAO, line 2 = Metier)
            List<String> names = loadClassNames("/config.txt");
            if (names.size() < 2) {
                throw new IllegalStateException("config.txt must contain 2 class names.");
            }

            // 2. Instantiate DAO
            IDao dao = (IDao) instantiate(names.get(0));
            System.out.println("[CONTAINER] DAO instantiated: " + dao.getClass().getName());

            // 3. Instantiate Metier
            IMetier metier = (IMetier) instantiate(names.get(1));
            System.out.println("[CONTAINER] Metier instantiated: " + metier.getClass().getName());

            // 4. Inject DAO into Metier via reflection
            inject(metier, "setDao", dao);

            // 5. Run
            System.out.println("Result = " + metier.calcul());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Reads non-empty, trimmed, non-comment lines from a classpath resource. */
    private static List<String> loadClassNames(String res) throws IOException {
        List<String> out = new ArrayList<>();
        try (InputStream is = Presentation2.class.getResourceAsStream(res)) {
            if (is == null) {
                throw new IOException("Resource not found on classpath: " + res);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty() && !line.startsWith("#")) {
                        out.add(line);
                    }
                }
            }
        }
        return out;
    }

    /** Loads a class by FQCN and calls its no-arg constructor. */
    private static Object instantiate(String cn) throws Exception {
        Class<?> cls = Class.forName(cn);
        return cls.getDeclaredConstructor().newInstance();
    }

    /** Finds a public 1-param setter by name whose param type accepts the dependency. */
    private static void inject(Object target, String setter, Object dep) throws Exception {
        for (Method m : target.getClass().getMethods()) {
            if (m.getName().equals(setter)
                    && m.getParameterCount() == 1
                    && m.getParameterTypes()[0].isAssignableFrom(dep.getClass())) {
                m.invoke(target, dep);
                return;
            }
        }
        throw new NoSuchMethodException(setter + " not found on " + target.getClass().getName());
    }
}

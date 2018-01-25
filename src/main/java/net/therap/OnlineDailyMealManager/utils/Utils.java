package net.therap.OnlineDailyMealManager.utils;

import net.therap.OnlineDailyMealManager.app.Main;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;


/**
 * @author anwar
 * @since 1/23/18
 */
public class Utils {

    private final int iterationsForHashing = 20 * 1000;
    private final int saltLength = 32;
    private final int desiredKeyLength = 256;

    public String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength);
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    public boolean isPasswordMatched(String password, String stored) throws Exception {
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored password should have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    private String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("Empty passwords are not supported.");
        }
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                        password.toCharArray(), salt, iterationsForHashing, desiredKeyLength)
        );
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * this method is used for generating manual insertion for
     * user password and admin password
     * since user registration feature is not available
     *
     * @throws Exception
     */

    public void generatePassForUserAndAdmin() throws Exception {
        System.out.println("For user, password should be saved as: " + getSaltedHash("user"));
        System.out.println("For admin, password should be saved as: " + getSaltedHash("admin"));
    }

    public void startTomcat(final int PORT) throws LifecycleException, ServletException, IOException {
        Tomcat tomcat = new Tomcat();
        Path tempPath = Files.createTempDirectory("tomcat-base-dir");
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = Integer.toString(PORT);
        }
        tomcat.setBaseDir(tempPath.toString());
        tomcat.setPort(Integer.valueOf(webPort));
        File webContentFolder = new File("./", "src/main/webapp/");
        if (!webContentFolder.exists()) {
            webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
        }
        StandardContext context = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
        context.setParentClassLoader(Main.class.getClassLoader());
        System.out.println("configuring app with basedir: " + webContentFolder.getAbsolutePath());
        File additionWebInfClassesFolder = new File("/", "target/classes");
        WebResourceRoot resources = new StandardRoot(context);
        WebResourceSet resourceSet;
        if (additionWebInfClassesFolder.exists()) {
            resourceSet = new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClassesFolder.getAbsolutePath(), "/");
            System.out.println("loading WEB-INF resources from as '" + additionWebInfClassesFolder.getAbsolutePath() + "'");
        } else {
            resourceSet = new EmptyResourceSet(resources);
        }
        resources.addPreResources(resourceSet);
        context.setResources(resources);
        tomcat.start();
        tomcat.getServer().await();
    }

    public void forwardReqAndResBasedOnInput(HttpServletRequest request, HttpServletResponse response,
                                             Constants.METHODS method, String defaultUrl, String createViewUrl, String updateViewUrl) throws ServletException, IOException {
        if (method == null) {
            request.getRequestDispatcher(defaultUrl).forward(request, response);
            return;
        }
        switch (method) {
            case POST: {
                request.getRequestDispatcher(createViewUrl).forward(request, response);
                break;
            }
            case PUT: {
                request.getRequestDispatcher(updateViewUrl).forward(request, response);
                break;
            }
            default: {
                request.getRequestDispatcher(defaultUrl).forward(request, response);
                break;
            }
        }
    }
}
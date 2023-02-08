import java.io.InputStreamReader;
import java.util.Enumeration;
import java.lang.annotation.Annotation;
import javax.servlet.http.HttpServlet;
import java.net.URLClassLoader;
import java.net.URL;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;


public class ManagementConsole extends Thread
{
    private MyHttpServer serv;
    
    ManagementConsole(final MyHttpServer serv) {
        this.serv = serv;
    }
    
    String firstWord(final String command) {
        if (command.contains(" ")) {
            final int index = command.indexOf(" ");
            return command.substring(0, index);
        }
        return command;
    }
    
    String secondWord(final String command) {
        if (command.contains(" ")) {
            final int index = command.indexOf(" ");
            return command.substring(index + 1, command.length());
        }
        return null;
    }
    
    void executeUnload(final String servletInternalName) {
        if (!ServletHashTable.contains(servletInternalName)) {
            System.out.println("Servlet " + servletInternalName + " not in the servlet repository");
        }
        else {
            ServletHashTable.remove(servletInternalName);
            System.out.println("Servlet " + servletInternalName + " removed");
        }
    }
    
    void executeLoad(final String servletInternalName) {
        if (ServletHashTable.contains(servletInternalName)) {
            System.out.println("Servlet " + servletInternalName + " already in the servlet repository");
        }
        else {
            String servletClassName = null;
            final String servletRepository = new String("./servletrepository");//si mette nella cartella servletrepository
            final String servletDir = new String(String.valueOf(servletRepository) + "/" + servletInternalName);
            final File f = new File(servletDir);
            if (!f.exists() || !f.isDirectory()) {
                System.out.println("Directory " + servletDir + " does not exists");
                return;
            }
            try {//external repository, quindi prende il nome dal metadata.txt
                final String metadataFile = String.valueOf(servletDir) + File.separator + "metadata.txt";
                final BufferedReader reader = new BufferedReader(new FileReader(metadataFile));
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    if (line.contains("=")) {
                        final int index = line.indexOf("=");//dopo l uguale
                        servletClassName = line.substring(index + 1, line.length());//prende il nome del servlet dal metadata.txt
                    }
                }
                reader.close();
            }
            catch (FileNotFoundException fe) {
                System.out.println("File not found");
                return;
            }
            catch (IOException e) {
                System.out.println("Error");
                Logger.logToFile("Management Console:\t" + e.toString());
                return;
            }
            URLClassLoader loader = null;
            try {
                final URL[] urls = { new URL("file:" + servletDir + File.separator + "class" + File.separator) };
                loader = new URLClassLoader(urls);
            }
            catch (IOException e2) {
                System.out.println("Error");
                Logger.logToFile("Management Console:\t" + e2.toString());
                return;
            }
            Class myClass = null;
            try {
                myClass = loader.loadClass(servletClassName);
            }
            catch (ClassNotFoundException e3) {
                System.out.println("Class not found:" + servletClassName);
                Logger.logToFile("Management Console:\t" + e3.toString());
                return;
            }
            HttpServlet servlet = null;
            try {
                servlet=(HttpServlet)myClass.newInstance();
            }
            catch (Exception e4) {
                System.out.println("Error");
                Logger.logToFile("Management Console:\t" + e4.toString());
                return;
            }
            ServletHashTable.put(servletInternalName, servlet);//mette il servlet nella hashtable
            System.out.println("Servlet " + servletInternalName + " added");
            try {
                loader.close();
            }
            catch (IOException e5) {
                System.out.println("Error");
                Logger.logToFile("Management Console:\t" + e5.toString());
            }
        }
    }
    
    void executeLoadWithAnnotations(final String servletInternalName) {
        if (ServletHashTable.contains(servletInternalName)) {//vede se la hashtable contiene gia il nome del servlet che andiamo a inserire
            System.out.println("Servlet " + servletInternalName + " already in the servlet repository");
        }
        else {
            final String annotatedClassDir = new String("./servletrepository/class");
            final File folder = new File(annotatedClassDir);
            final File[] listOfFiles = folder.listFiles();
            Class myClass = null;
            boolean found = false;
            for (int i = 0; i < listOfFiles.length; ++i) {
                if (listOfFiles[i].getName().endsWith(".class")) {
                    URLClassLoader loader = null;//crea un ClassLoader
                    try {
                        final URL[] urls = { new URL("file:" + annotatedClassDir + File.separator) };
                        loader = new URLClassLoader(urls);//carica tramite URL
                    }
                    catch (IOException e) {
                        System.out.println("Error");
                        Logger.logToFile("Management Console:\t" + e.toString());
                        return;
                    }
                    try {
                        myClass = loader.loadClass(listOfFiles[i].getName().replace(".class", ""));//sostituisco il .class con nulla, perchè prende solo il nome della classe
                    }
                    catch (ClassNotFoundException e2) {
                        System.out.println("Class not found:" + listOfFiles[i].getName());
                        Logger.logToFile("Management Console:\t" + e2.toString());
                        return;
                    }
                    final Annotation[] annotationList = myClass.getAnnotations();
                    MyAnnotation a = null;
                    if (annotationList.length != 0) {
                        for (int j = 0; j < annotationList.length; ++j) {
                            try {
                                annotationList[j].annotationType();
                                a = (MyAnnotation)annotationList[j];
                            }
                            catch (Exception e4) {
                                continue;
                            }
                            if (a.name().equals("URLServletName") && a.value().equals(servletInternalName)) {
                                found = true;//trova la annotation
                                break;
                            }
                        }
                        if (found) {
                            break;
                        }
                    }
                }
            }
            if (!found) {
                System.out.println("Servlet " + servletInternalName + " not found");
                return;
            }
            HttpServlet servlet = null;
            try {
                servlet=(HttpServlet)myClass.newInstance();
            }
            catch (Exception e3) {
                System.out.println("Error" + e3.toString());
                Logger.logToFile("Management Console:\t" + e3.toString());
                return;
            }
            ServletHashTable.put(servletInternalName, servlet);//mette il servlet nella hashtable
            System.out.println("Servlet " + servletInternalName + " added");
        }
    }
    
    void executeList() {
        final Enumeration<String> enu = (Enumeration<String>)ServletHashTable.enu();
        System.out.println("Tutti i Servlets");
        while (enu.hasMoreElements()) {
            System.out.println(enu.nextElement());
        }
        System.out.println("--------------");
    }
    
    void executeCommand(final String command) {
        if (this.firstWord(command).equals("load")) {
            if (this.secondWord(command) == null) { //vuole 2 argomenti
                System.out.println("Wrong command!");
                return;
            }
            this.executeLoad(this.secondWord(command));//chiama metodo
        }
        else if (this.firstWord(command).equals("remove")) {
            if (this.secondWord(command) == null) {
                System.out.println("Wrong command!");
                return;
            }
            this.executeUnload(this.secondWord(command));
        }
        else if (this.firstWord(command).contentEquals("ls")) {
            if (this.secondWord(command) != null) {//vuole un solo argomento
                System.out.println("Wrong command!");
                return;
            }
            this.executeList();
        }
        else {
            if (!this.firstWord(command).contentEquals("load-with-annotations")) {
                return;
            }
            if (this.secondWord(command) == null) {
                System.out.println("Wrong command!");
                return;
            }
            this.executeLoadWithAnnotations(this.secondWord(command));
        }
    }//fine excecutecommand
    
    @Override
    public void run() {
        Thread.currentThread().setName("ManagementConsole");
        String command = null;
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Command: ");
        
            try {
                command = bufferedReader.readLine();//legge da riga di comando
            }
            catch (IOException e) {
                System.out.println("Error. Try Again.");
                Logger.logToFile("Management Console:\t" + e.toString());
                System.exit(1);
            }
            finally {
                this.executeCommand(command);//eseguito quando il try esiste. Eseguo il comando
            }
            
        
        System.out.println();
        //se il commando è diverso da quit allora richede di inserire il comando di nuovo
        while (!command.equals("quit") && !Shutdown.flag) {
            System.out.print("Command: ");
            
                try {
                    command = bufferedReader.readLine();
                }
                catch (IOException e) {
                    System.out.println("Error. Try Again.");
                    Logger.logToFile("Management Console:\t" + e.toString());
                    System.exit(1);
                }
                finally {
                    this.executeCommand(command);
                }
                
            
            System.out.println();
        }
        //se il comando è quit esce
        Shutdown.flag = true;
        this.serv.setShutdown(true);
        try {
            this.serv.getServerSocket().close();
        }
        catch (Exception e2) {
            System.out.println("Management Console:\t" + e2.toString());
        }
    }
}

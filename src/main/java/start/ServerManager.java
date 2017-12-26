package start;

import settings.DBSetting;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import settings.ServiceSetting;

import java.util.List;

public class ServerManager {
    private static ServerManager instance = new ServerManager();
    public static  ServerManager getInstance() {
        return  instance;
    }
    private ServerManager() {

    }

    public void loadDatabase(Element database) throws DataConversionException {
        List<Element> elements = database.getChildren();
        for (Element element : elements) {
            String name = element.getAttributeValue("name");
            int type = element.getAttribute("type").getIntValue();
            String filename = element.getAttributeValue("filename");
            DBSetting.getInstance().loadDatabase(name, type, filename);
        }
    }


    public void loadServices() {
        ServiceSetting.getInstance().startServices();
    }
}

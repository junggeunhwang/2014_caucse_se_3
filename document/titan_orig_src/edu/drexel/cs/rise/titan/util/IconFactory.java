package edu.drexel.cs.rise.titan.util;

import edu.drexel.cs.rise.util.SoftHashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class IconFactory
{
  private static Map<String, Icon> cache = new SoftHashMap();

  public static synchronized Icon load(String paramString)
  {
    if (!cache.containsKey(paramString))
      cache.put(paramString, new ImageIcon(IconFactory.class.getResource(paramString)));
    return (Icon)cache.get(paramString);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.util.IconFactory
 * JD-Core Version:    0.6.2
 */
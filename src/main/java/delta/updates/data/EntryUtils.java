package delta.updates.data;

/**
 * Utility methods related to directoy entries.
 * @author DAM
 */
public class EntryUtils
{
  /**
   * Get a path for the given entry.
   * @param entry Entry to use.
   * @return A path.
   */
  public static String getPath(DirectoryEntryDescription entry)
  {
    DirectoryDescription parent=entry.getParent();
    if (parent==null)
    {
      return entry.getName();
    }
    String parentPath=getPath(parent);
    return parentPath+"/"+entry.getName();
  }
}
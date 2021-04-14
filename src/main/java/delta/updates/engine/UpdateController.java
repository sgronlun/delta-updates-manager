package delta.updates.engine;

import java.util.List;

import delta.downloads.Downloader;
import delta.updates.data.SoftwareDescription;
import delta.updates.data.SoftwarePackageUsage;
import delta.updates.ui.UpdateUI;

/**
 * Update controller.
 * <br>
 * Manages:
 * <ul>
 * <li>the update engine,
 * <li>the update UI.
 * </ul>
 * @author DAM
 */
public class UpdateController
{
  private UpdateEngine2 _engine;

  /**
   * Constructor.
   */
  public UpdateController()
  {
    Downloader downloader=new Downloader();
    _engine=new UpdateEngine2(downloader);
  }

  /**
   * Perform update.
   */
  public void doIt()
  {
    SoftwareDescription remoteSoftware=_engine.lookForUpdate();
    if (remoteSoftware==null)
    {
      return;
    }
    List<SoftwarePackageUsage> neededPackages=_engine.getNeededPackages(remoteSoftware);
    LocalDataManager local=_engine.getLocalDataManager();
    SoftwareDescription localSofware=local.getSoftware();
    ResourcesAssessment assessment=_engine.assessResources(neededPackages);
    boolean updateAllowed=UpdateUI.askForUpdate(localSofware,remoteSoftware,assessment);
    if (!updateAllowed)
    {
      return;
    }
    for(SoftwarePackageUsage packageUsage : neededPackages)
    {
      _engine.handlePackage(packageUsage);
    }
  }
}
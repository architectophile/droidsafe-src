package droidsafe.eclipse.plugin.core.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import droidsafe.eclipse.plugin.core.runner.DroidsafeAnalysisRunner;

public class GenerateSecuritySpecHandler extends AbstractHandler {
  private final static Logger logger = LoggerFactory.getLogger(GenerateSecuritySpecHandler.class);


  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    ISelection selection =
        HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
    if (selection != null & selection instanceof IStructuredSelection) {
      IStructuredSelection structuredSelection = (IStructuredSelection) selection;
      Object firstSelection = structuredSelection.getFirstElement();
      IProject projectAux = null;
      if (firstSelection instanceof IJavaProject) {
        projectAux = ((IJavaProject) firstSelection).getProject();
      } else if (firstSelection instanceof IProject) {
        projectAux = (IProject) firstSelection;
      }
      if (projectAux != null) {
        final IProject project = projectAux;
        final IPath path = project.getLocation();
        logger.info("Project = " + project + " path = " + path);

        Job job = new Job("Droidsafe") {
          @Override
          protected IStatus run(IProgressMonitor monitor) {
            logger.info("Project = " + project + " path = " + path);
            try {
              monitor.beginTask("Droidsafe spec dump", 16);
              DroidsafeAnalysisRunner droidsafeAnalysisRunner =
                  new DroidsafeAnalysisRunner(project);
              monitor.worked(1);
              droidsafeAnalysisRunner.run(monitor);
            } finally {
              monitor.done();
            }
            return Status.OK_STATUS;
          }
        };
        job.setUser(true);
        job.schedule();
      }
    }
    return null;
  }

}

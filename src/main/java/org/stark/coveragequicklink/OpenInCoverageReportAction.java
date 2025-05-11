package org.stark.coveragequicklink;

import com.intellij.coverage.*;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class OpenInCoverageReportAction extends AnAction {

    private static final String NOTIFICATION_GROUP = "CoverageQuickLink.Notifications";
    private static final Map<String, String> EXTENSION_TO_RUNNER = Map.of(
            "ic", "IDEA",
            "exec", "JaCoCo",
            "xml", "JaCoCo"
    );

    @Override
    public void update(@NotNull AnActionEvent event) {
        VirtualFile file = event.getData(CommonDataKeys.VIRTUAL_FILE);
        event.getPresentation().setEnabledAndVisible(isCoverageFile(file));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        VirtualFile file = event.getData(CommonDataKeys.VIRTUAL_FILE);
        if (project == null || file == null) return;

        try {
            CoverageRunner runner = getCoverageRunner(file);
            if (runner == null) {
                showNotification(project, "Unsupported coverage format", NotificationType.ERROR);
                return;
            }

            CoverageDataManager manager = CoverageDataManager.getInstance(project);
            CoverageSuitesBundle bundle = new CoverageSuitesBundle(
                    manager.addExternalCoverageSuite(
                            file.getPath(),
                            System.currentTimeMillis(),
                            runner,
                            new DefaultCoverageFileProvider(file.getPath())
                    )
            );
            manager.chooseSuitesBundle(bundle);

            ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Coverage");
            if (toolWindow == null) {
                showNotification(project, "Coverage tool window not found", NotificationType.ERROR);
                return;
            }
            toolWindow.show();
        } catch (Exception e) {
            showNotification(project, "Failed to load coverage file: " + e.getMessage(), NotificationType.ERROR);
        }
    }

    private @Nullable CoverageRunner getCoverageRunner(@NotNull VirtualFile file) {
        String extension = file.getExtension();
        if (extension == null) return null;

        String runnerName = EXTENSION_TO_RUNNER.get(extension.toLowerCase());
        if (runnerName == null) return null;

        return CoverageRunner.EP_NAME.getExtensionList().stream()
                .filter(runner -> runnerName.equals(runner.getPresentableName()))
                .findFirst().orElse(null);
    }

    private void showNotification(@Nullable Project project, String message, NotificationType type) {
        Notifications.Bus.notify(
                new Notification(NOTIFICATION_GROUP, "CoverageQuickLink", message, type), project
        );
    }

    private static boolean isCoverageFile(@Nullable VirtualFile file) {
        if (file == null || file.isDirectory() || !file.exists() || !file.isValid()) return false;
        String extension = file.getExtension();
        return extension != null && EXTENSION_TO_RUNNER.containsKey(extension.toLowerCase());
    }
}
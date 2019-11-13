package eu.yals.ui;

import com.github.appreciated.app.layout.component.applayout.LeftLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftHeaderItem;
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.github.appreciated.app.layout.entity.Section;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import eu.yals.services.GitService;
import eu.yals.utils.AppUtils;

@SpringComponent
@UIScope
@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@PWA(name = "Yet another link shortener", shortName = "yals",
        offlinePath = "offline-page.html",
        offlineResources = {"images/logo.png"},
        description = "Yet another link shortener for friends")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class AppView extends AppLayoutRouterLayout<LeftLayouts.LeftHybrid> implements PageConfigurator {
    private final AppUtils appUtils;
    private final GitService gitService;

    public AppView(AppUtils appUtils, GitService gitService) {
        this.appUtils = appUtils;
        this.gitService = gitService;

        AppLayoutBuilder<LeftLayouts.LeftHybrid> builder = AppLayoutBuilder
                .get(LeftLayouts.LeftHybrid.class)
                .withTitle("YALS");

        LeftAppMenuBuilder menuBuilder = LeftAppMenuBuilder.get();

        //title
        if (appUtils.isMobile(VaadinSession.getCurrent())) {
            menuBuilder.addToSection(Section.HEADER,
                    new LeftHeaderItem("Yet another link shortener",
                            String.format("Version %s", gitService.getGitInfoSource().getLatestTag()),
                            "/images/logo.png"));
        }

        //items
        menuBuilder.add(new LeftNavigationItem(HomeView.class));
        menuBuilder.add(new LeftNavigationItem(SampleView.class));

        builder.withAppMenu(menuBuilder.build());

        LeftLayouts.LeftHybrid layout = builder.build();
        init(layout);
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addFavIcon("icon", "/images/logo.png", "512x512");
    }
}
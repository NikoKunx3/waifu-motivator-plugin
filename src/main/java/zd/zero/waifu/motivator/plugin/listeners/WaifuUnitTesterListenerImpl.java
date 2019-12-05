package zd.zero.waifu.motivator.plugin.listeners;

import com.intellij.openapi.project.Project;
import zd.zero.waifu.motivator.plugin.alert.WaifuMotivatorAlert;
import zd.zero.waifu.motivator.plugin.alert.WaifuMotivatorAlertFactory;
import zd.zero.waifu.motivator.plugin.alert.WaifuMotivatorAsset;
import zd.zero.waifu.motivator.plugin.alert.WaifuMotivatorAssetType;
import zd.zero.waifu.motivator.plugin.alert.notification.AlertConfiguration;
import zd.zero.waifu.motivator.plugin.settings.WaifuMotivatorPluginSettings;
import zd.zero.waifu.motivator.plugin.settings.WaifuMotivatorState;

import java.util.Objects;


public class WaifuUnitTesterListenerImpl implements WaifuUnitTester.Listener {

    private final Project project;

    private WaifuMotivatorState pluginState;

    public WaifuUnitTesterListenerImpl( Project project ) {
        this.project = project;
        this.pluginState = WaifuMotivatorPluginSettings.getInstance().getState();
    }

    @Override
    public void onUnitTestPassed() {
        WaifuMotivatorAlert failedAlert = WaifuMotivatorAlertFactory.createAlert( project,
                WaifuMotivatorAsset.getRandomAssetByType( WaifuMotivatorAssetType.POSITIVE ), getUnitTesterConfiguration() );
        failedAlert.alert();
    }

    @Override
    public void onUnitTestFailed() {
        WaifuMotivatorAlert failedAlert = WaifuMotivatorAlertFactory.createAlert( project,
                WaifuMotivatorAsset.getRandomAssetByType( WaifuMotivatorAssetType.NEGATIVE ), getUnitTesterConfiguration() );
        failedAlert.alert();
    }

    private AlertConfiguration getUnitTesterConfiguration() {
        return AlertConfiguration.builder()
                .isAlertEnabled( Objects.requireNonNull( pluginState ).isUnitTesterMotivationEnabled() )
                .isDisplayNotificationEnabled( true )
                .isSoundAlertEnabled( true )
                .build();
    }
}

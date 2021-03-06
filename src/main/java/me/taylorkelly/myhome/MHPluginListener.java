package me.taylorkelly.myhome;

import com.nijikokun.register.payment.Methods;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class MHPluginListener extends ServerListener {
	private Methods Methods = null;
	private Plugin plugin;

	public MHPluginListener(Plugin plugin) {
		this.plugin = plugin;
		this.Methods = new Methods();
	}

	@Override
	public void onPluginEnable(PluginEnableEvent event) {
		//Economy support
		if(!this.Methods.hasMethod()){
			if(this.Methods.setMethod(event.getPlugin())){
				HomeEconomy.economy = this.Methods.getMethod();
				HomeLogger.info("[MYHOME] " + HomeEconomy.economy.getName() + " version " + HomeEconomy.economy.getVersion() + " loaded.");
			}
		}	
	}

	@Override
	public void onPluginDisable(PluginDisableEvent event) {
		if (this.Methods != null && this.Methods.hasMethod()) {
			Boolean check = this.Methods.checkDisabled(event.getPlugin());
			if(check) {
				this.Methods = null;
				HomeLogger.info("[MYHOME] Payment method was disabled. No longer accepting payments.");
			}
		}
	}
}
/*
 * Copyright (c) 2026, Perry
 * All rights reserved.
 *
 * This work is licensed under the terms of the
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE, Version 2.
 * See the LICENSE file for the full license text.
 */
package com.nomisclickveng;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.ScriptID;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "No Misclick Veng",
	description = "Blocks recasting Vengeance while your current Vengeance is still active",
	tags = {"vengeance", "lunar", "misclick", "pvm"}
)
public class NoMisclickVengPlugin extends Plugin
{
	// clientscript [magic_spellbook_redraw] — recreates the spell icons
	// (cc_deleteall) on rune/stat changes; no ScriptID or gameval constant
	// exists for it
	private static final int MAGIC_SPELLBOOK_REDRAW = 2610;

	// same opacity the game's own spellbook script (proc 2614) uses to dim
	// Vengeance while its 30s cooldown varbit is set — we extend the exact
	// native visual to cover the unprocced-rebound window
	private static final int NATIVE_COOLDOWN_OPACITY = 150;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private NoMisclickVengConfig config;

	// non-null if this plugin currently owns the icon's opacity; holds the
	// value to restore. Cannot go stale: any script-driven change to spell
	// icons goes through the rebuild scripts, whose handler nulls this so the
	// next apply re-captures the widget's current opacity.
	private Integer previousOpacity;

	@Override
	protected void startUp()
	{
		clientThread.invoke(this::updateIcon);
	}

	@Override
	protected void shutDown()
	{
		clientThread.invoke(() -> dimVengeanceIcon(false));
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		MenuAction action = event.getMenuAction();
		if (action != MenuAction.CC_OP && action != MenuAction.CC_OP_LOW_PRIORITY)
		{
			return;
		}

		if (event.getParam1() != InterfaceID.MagicSpellbook.VENGEANCE)
		{
			return;
		}

		if (isVengeanceActive())
		{
			event.consume();

			if (config.showBlockedMessage())
			{
				client.addChatMessage(ChatMessageType.GAMEMESSAGE, "",
					"Vengeance recast blocked - still active.", null);
			}
		}
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		if (event.getVarbitId() == VarbitID.VENGEANCE_REBOUND)
		{
			dimVengeanceIcon(event.getValue() == 1);
		}
	}

	@Subscribe
	public void onScriptPostFired(ScriptPostFired event)
	{
		int scriptId = event.getScriptId();
		if (scriptId == ScriptID.MAGIC_SPELLBOOK_INITIALISESPELLS || scriptId == MAGIC_SPELLBOOK_REDRAW)
		{
			// the script just rebuilt the spell icons with fresh state; our
			// previous write (if any) is gone - drop ownership so we
			// re-apply if needed and never clear state we no longer own
			previousOpacity = null;
			updateIcon();
		}
	}

	private void updateIcon()
	{
		dimVengeanceIcon(isVengeanceActive());
	}

	private boolean isVengeanceActive()
	{
		return client.getVarbitValue(VarbitID.VENGEANCE_REBOUND) == 1;
	}

	private void dimVengeanceIcon(boolean dim)
	{
		if (dim == (previousOpacity != null))
		{
			return;
		}

		Widget w = client.getWidget(InterfaceID.MagicSpellbook.VENGEANCE);
		if (w == null)
		{
			// leave previousOpacity as-is so this apply or clear retries
			// the next time any icon-update path runs
			return;
		}

		if (dim)
		{
			previousOpacity = w.getOpacity();
			w.setOpacity(NATIVE_COOLDOWN_OPACITY);
		}
		else
		{
			w.setOpacity(previousOpacity);
			previousOpacity = null;
		}
	}

	@Provides
	NoMisclickVengConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NoMisclickVengConfig.class);
	}
}
